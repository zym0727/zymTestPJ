package zym.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import zym.dao.*;
import zym.pojo.*;
import zym.util.CppTest;
import zym.util.DateUtil;
import zym.util.JavaTest;
import zym.util.LanguageTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author zym
 * *
 */
@Service
public class TimeTaskService {

    private static Logger log = LoggerFactory.getLogger(TimeTaskService.class);

    @Autowired
    private HomeworkMapper homeworkMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HomeworkScoreMapper homeworkScoreMapper;

    @Autowired
    private TestDataMapper testDataMapper;

    @Autowired
    private LanguageMarkMapper languageMarkMapper;

    //发布作业，10分钟扫描一次(固定为10分整，如11:10)
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void updateAssignHomework() throws ParseException {
        List<Homework> homeworkList = homeworkMapper.getHomeworkListByIsAssign(0);
        if (homeworkList != null && homeworkList.size() > 0) {
            Date now = DateUtil.getNow();
            homeworkList.forEach(homework -> {
                if (now.getTime() == homework.getAssignTime().getTime() ||
                        now.getTime() > homework.getAssignTime().getTime()) {
                    homework.setIsAssign(1);
                    homeworkMapper.updateByPrimaryKeySelective(homework);
                }
            });
        }
    }

    //自动批改作业，10分钟扫描一次(固定为10分整，如11:10)
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void updateCorrectHomework() throws ParseException {
        //获取需要进行智能批改的作业列表
        List<Homework> homeworkList = homeworkMapper.getHomeworkListByIsAutomatic(1);
        if (homeworkList != null && homeworkList.size() > 0) {
            Date now = DateUtil.getNow();
            homeworkList.forEach(homework -> {
                if (now.getTime() == homework.getDeadline().getTime() ||
                        now.getTime() > homework.getDeadline().getTime()) {
                    if (homework.getQuestionIds() == null)
                        log.info("作业记录表中id为：" + homework.getId() + "的作业题目为空，出现问题");
                    else {
                        String[] ids = homework.getQuestionIds().split(",");
                        //获取当前作业下所有学生提交的作业
                        List<HomeworkScore> homeworkScoreList = homeworkScoreMapper.
                                selectListByHomeworkId(homework.getId());
                        if (homeworkScoreList != null && homeworkScoreList.size() > 0)
                            correctHomework(homeworkScoreList, ids);
                    }
                }
            });
        }
    }

    //批改作业
    private void correctHomework(List<HomeworkScore> homeworkScoreList, String[] ids) {
        homeworkScoreList.forEach(homeworkScore -> {
            System.out.println("homeworkScore" + homeworkScore.getId());
            String answer = homeworkScore.getAnswer();
            if (answer != null) {
                String[] answers = answer.split("div----------div");
                if (ids.length != answers.length) {
                    homeworkScore.setScore(0);
                    homeworkScore.setEvaluate("缺少题目上交？");
                } else {
                    //count正确的        all所有题目
                    int i, count = 0;
                    int all = answers.length;
                    for (i = 0; i < ids.length; i++) {
                        Question question = questionMapper.selectByPrimaryKey(
                                Integer.parseInt(ids[i]));
                        if (question != null) {
                            List<TestData> testDataList = testDataMapper.getListByQuestionId(
                                    question.getId());
                            //自动批改的程序作业是一定有测试数据的,非程序作业没有测试数据
                            if (testDataList != null && testDataList.size() > 0
                                    && question.getLanguageId() != null) {
                                LanguageMark languageMark = languageMarkMapper.selectByPrimaryKey(
                                        question.getLanguageId());//获取当前题目的标记语言，如java
                                if (languageMark != null) {
                                    if (languageMark.getMark() != null) {
                                        long createTime = System.currentTimeMillis();
                                        LanguageTest languageTest = null;
                                        switch (languageMark.getMark()) {
                                            case "java":
                                                languageTest = new JavaTest(homeworkScore.getId(),
                                                        question.getId(), answers[i], createTime);
                                                break;
                                            case "c++":
                                                languageTest = new CppTest(homeworkScore.getId(),
                                                        question.getId(), answers[i], createTime);
                                                break;
                                        }
                                        if (languageTest != null) {
                                            String result = null;
                                            try {
                                                result = languageTest.compile();//编译提交代码，获取结果
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            if (result == null) {
                                                try {
                                                    //编译正确，开始执行多组输入验证对应的输出
                                                    count += languageTest.execute(testDataList);
                                                    all += testDataList.size() - 1;//叠加测试数量
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                try {
                                                    //编译错误信息，其原始编码格式是cp936
                                                    result = new String(result.getBytes(
                                                            "cp936"), "utf-8");
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                                System.out.println("result:" + result);
                                            }
                                        } else
                                            log.info("标记语言表中id为：" + languageMark.getId() +
                                                    "的标记语言不匹配代码，请更新程序！");
                                    }
                                } else
                                    log.info("题目中id为：" + question.getId() + "的标记语言为空" +
                                            "而有测试数据，出现问题！");
                            } else {
                                if (question.getAnswer().trim().equals(answers[i].trim()))
                                    count++;
                            }
                        } else
                            log.info("作业布置中题目id为：" + ids[i] + "不存在！");
                    }
                    double correctRate = (double) count / (double) all;
                    correctRate = (double) Math.round(correctRate * 10000) / 100;
                    homeworkScore.setCorrectRate(correctRate);
                    homeworkScore = giveScoreAndEvaluate(homeworkScore, correctRate);
                    System.out.println("correctRate" + correctRate);
                }
            } else if (homeworkScore.getFileName() != null) {
                homeworkScore.setScore(0);
                homeworkScore.setEvaluate("请让老师手动进行批改评分！");
            } else {
                homeworkScore.setScore(0);
                homeworkScore.setEvaluate("作业答案是空的，没有提交作业！");
            }
            homeworkScoreMapper.updateByPrimaryKeySelective(homeworkScore);
        });
    }

    //评分以及评价
    private HomeworkScore giveScoreAndEvaluate(HomeworkScore homeworkScore, double correctRate) {
        homeworkScore.setScore((int) correctRate);
        if (correctRate == 100.00)
            homeworkScore.setEvaluate("完美地完成了作业");
        else if (correctRate > 90.00)
            homeworkScore.setEvaluate("很优秀，基本上全做对了");
        else if (correctRate > 80.00)
            homeworkScore.setEvaluate("此次作业做的还不错，除了一些小错误");
        else if (correctRate > 70.00)
            homeworkScore.setEvaluate("较好得完成了本次作业");
        else if (correctRate > 60.00)
            homeworkScore.setEvaluate("此次作业做的一般般");
        else if (correctRate > 50.00)
            homeworkScore.setEvaluate("此次作业做的不够认真");
        else if (correctRate > 40.00)
            homeworkScore.setEvaluate("此次作业做的不怎么样");
        else
            homeworkScore.setEvaluate("此次作业做的不好，请继续努力");
        return homeworkScore;
    }
}