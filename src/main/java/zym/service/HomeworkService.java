package zym.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import zym.dao.HomeworkMapper;
import zym.dao.HomeworkScoreMapper;
import zym.dao.QuestionMapper;
import zym.pojo.*;
import zym.pojo.param.HomeworkMessage;
import zym.util.DateUtil;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zym
 * *
 */
@Service
public class HomeworkService {

    @Autowired
    private HomeworkMapper homeworkMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HomeworkScoreMapper homeworkScoreMapper;

    public String saveAssignHomework(Homework homework) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date assignTime = homework.getAssignTime();
        try {
            Date now = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            if (now.getTime() > assignTime.getTime())
                return JSONObject.toJSONString("time out");
            if (now.getTime() == assignTime.getTime()) {
                homework.setIsAssign(1);
                homeworkMapper.insertSelective(homework);
                return JSONObject.toJSONString("success");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        homework.setIsAssign(0);
        homeworkMapper.insertSelective(homework);
        return JSONObject.toJSONString("success");
    }

    public JSONObject getStudentHomeworkList(HttpSession httpSession, Integer status) {
        JSONObject result = new JSONObject();
        Object user = httpSession.getAttribute("user");
        if (user == null || status < 1 || status > 4) {
            result.put("total", 0);
            result.put("rows", new ArrayList<>());
            return result;
        }
        Users student = (Users) user;
        int id = student.getId();
        if (status == 1) { //未提交作业
            result.put("total", homeworkMapper.countUnSubmitListStudent(id));
            result.put("rows", spiltToName(homeworkMapper.getUnSubmitListByStudentId(id)));
        } else if (status == 2) { //已提交作业
            result.put("total", homeworkMapper.countSubmitListStudent(id));
            result.put("rows", spiltToName(homeworkMapper.getSubmitListByStudentId(id)));
        } else if (status == 3) { //未批改作业
            result.put("total", homeworkMapper.countUnMarkListStudent(id));
            result.put("rows", spiltToName(homeworkMapper.getUnMarkListByStudentId(id)));
        } else if (status == 4) { //已批改作业
            result.put("total", homeworkMapper.countMarkListStudent(id));
            result.put("rows", spiltToName(homeworkMapper.getMarkListByStudentId(id)));
        }
        return result;
    }

    private List<HomeworkMessage> spiltToName(List<HomeworkMessage> list) {
        for (HomeworkMessage h : list) {
            String[] ids = h.getQuestionIds().split(",");
            if (ids.length > 0) {
                StringBuilder questionName = new StringBuilder("");
                for (String s : ids) {
                    Question question = questionMapper.selectByPrimaryKey(Integer.parseInt(s));
                    if (question != null)
                        questionName.append(question.getQuestionName()).append(",");
                }
                if (!questionName.toString().equals(""))
                    h.setQuestionName(questionName.toString().substring(0, questionName.length() - 1));
            }
        }
        return list;
    }

    public JSONObject getStudentHomeworkCount(HttpSession httpSession) {
        JSONObject result = new JSONObject();
        Object user = httpSession.getAttribute("user");
        if (user == null) {
            result.put("unSubmit", 0);
            result.put("submit", 0);
            result.put("unMark", 0);
            result.put("mark", 0);
            return result;
        }
        Users student = (Users) user;
        int id = student.getId();
        result.put("unSubmit", homeworkMapper.countUnSubmitListStudent(id));
        result.put("submitted", homeworkMapper.countSubmitListStudent(id));
        result.put("unMark", homeworkMapper.countUnMarkListStudent(id));
        result.put("mark", homeworkMapper.countMarkListStudent(id));
        return result;
    }

    public String getStudentHomework(Integer homeworkId, Model model, HttpSession session) {
        Homework homework = homeworkMapper.selectByPrimaryKey(homeworkId);
        if (homework == null)
            return "null";
        model.addAttribute("assignTime", DateUtil.dateFormat(homework.getAssignTime()));
        model.addAttribute("deadline", DateUtil.dateFormat(homework.getDeadline()));
        model.addAttribute("remark", homework.getRemark());
        model.addAttribute("courseId", homework.getCourseId());
        model.addAttribute("homeworkId", homework.getId());
        String[] questionIds = homework.getQuestionIds().split(",");
        StringBuilder homeworkDetail = new StringBuilder("");
        for (int i = 0; i < questionIds.length; i++) {
            Question question = questionMapper.selectByPrimaryKey(Integer.parseInt(questionIds[i]));
            homeworkDetail.append("第").append(i + 1).append("题：").append(question.getQuestionName()).append("\n\n")
                    .append(question.getDescription());
            if (i < questionIds.length - 1)
                homeworkDetail.append("\n\n\n\n");
        }
        model.addAttribute("homeworkDetail", homeworkDetail.toString());
        HomeworkScore homeworkScore = new HomeworkScore();
        Users user = (Users) session.getAttribute("user");
        homeworkScore.setStudentId(user.getId());
        homeworkScore.setHomeworkId(homeworkId);
        List<HomeworkScore> scoreList = homeworkScoreMapper.selectList(homeworkScore);
        if (scoreList != null && scoreList.size() > 0)
            model.addAttribute("homeworkScore", scoreList.get(0));
        return "success";
    }

    public String saveHomework(HomeworkScore homeworkScore) throws ParseException {
        HomeworkScore test = new HomeworkScore();
        test.setHomeworkId(homeworkScore.getHomeworkId());
        test.setStudentId(homeworkScore.getStudentId());
        List<HomeworkScore> scoreList = homeworkScoreMapper.selectList(test);
        Date now = DateUtil.getNow();
        if (scoreList != null && scoreList.size() > 0){
            HomeworkScore haveOne = scoreList.get(0);
            haveOne.setAnswer(homeworkScore.getAnswer());
            haveOne.setFileName(homeworkScore.getFileName());
            haveOne.setFilePath(homeworkScore.getFilePath());
            haveOne.setSubmitTime(now);
            homeworkScoreMapper.updateByPrimaryKey(haveOne);
        }else {
            homeworkScore.setSubmitTime(now);
            homeworkScoreMapper.insert(homeworkScore);
        }
        return JSONObject.toJSONString("success");
    }
}
