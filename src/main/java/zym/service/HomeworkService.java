package zym.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import zym.dao.*;
import zym.pojo.*;
import zym.pojo.param.*;
import zym.util.DateUtil;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private MajorClassMapper majorClassMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private MessageInteractionMapper messageInteractionMapper;

    public String saveOrUpdateAssignHomework(Homework homework, Boolean isSave) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date assignTime = homework.getAssignTime();
        try {
            Date now = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            if (isSave) {
                if (now.getTime() > assignTime.getTime())
                    return JSONObject.toJSONString("time out");
                if (now.getTime() == assignTime.getTime()) {
                    homework.setIsAssign(1);
                    homeworkMapper.insertSelective(homework);
                    return JSONObject.toJSONString("success");
                }
            } else {
                if (now.getTime() > assignTime.getTime() || now.getTime() == assignTime.getTime()) {
                    homework.setIsAssign(1);
                    homeworkMapper.updateByPrimaryKeySelective(homework);
                    return JSONObject.toJSONString("success");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        homework.setIsAssign(0);
        if (isSave)
            homeworkMapper.insertSelective(homework);
        else
            homeworkMapper.updateByPrimaryKeySelective(homework);
        return JSONObject.toJSONString("success");
    }

    public JSONObject getStudentHomeworkList(HttpSession httpSession,
                                             StudentHomeworkPage studentHomeworkPage) {
        JSONObject result = new JSONObject();
        studentHomeworkPage.setOffset(
                (studentHomeworkPage.getPageNumber() - 1) * studentHomeworkPage.getPageSize());
        Object user = httpSession.getAttribute("user");
        int status = studentHomeworkPage.getStatus();
        if (user == null || status < 1 || status > 4) {
            result.put("total", 0);
            result.put("rows", new ArrayList<>());
            return result;
        }
        Users users = (Users) user;
        studentHomeworkPage.setId(users.getId());
        //当前用户是学生
        if (users.getRoleId() == 3) {
            if (status == 1) { //未提交作业
                result.put("total", homeworkMapper.countUnSubmitListStudent(studentHomeworkPage));
                result.put("rows", spiltToName(homeworkMapper.getUnSubmitListByStudentId(
                        studentHomeworkPage)));
            } else if (status == 2) { //已提交作业
                result.put("total", homeworkMapper.countSubmitListStudent(studentHomeworkPage));
                result.put("rows", spiltToName(homeworkMapper.getSubmitListByStudentId(
                        studentHomeworkPage)));
            } else if (status == 3) { //未批改作业
                result.put("total", homeworkMapper.countMarkListStudent(studentHomeworkPage));
                result.put("rows", spiltToName(homeworkMapper.getMarkListByStudentId(
                        studentHomeworkPage)));
            } else { //已批改作业   status == 4
                studentHomeworkPage.setScore(1);
                result.put("total", homeworkMapper.countMarkListStudent(studentHomeworkPage));
                result.put("rows", spiltToName(homeworkMapper.getMarkListByStudentId(
                        studentHomeworkPage)));
            }
        } else if (users.getRoleId() == 2) { //用户是老师
            if (status == 1) { //未手动批改作业
                studentHomeworkPage.setIsAutomatic(0);
                result.put("total", homeworkMapper.countSubmitHomeworkListTeacher(studentHomeworkPage));
                result.put("rows", spiltToName(homeworkMapper.getSubmitHomeworkListByTeacherId(
                        studentHomeworkPage)));
            } else if (status == 2) { //已手动批改作业
                studentHomeworkPage.setIsAutomatic(0);
                studentHomeworkPage.setScore(1);
                result.put("total", homeworkMapper.countSubmitHomeworkListTeacher(studentHomeworkPage));
                result.put("rows", spiltToName(homeworkMapper.getSubmitHomeworkListByTeacherId(
                        studentHomeworkPage)));
            } else if (status == 3) { //未自动批改作业
                studentHomeworkPage.setIsAutomatic(1);
                result.put("total", homeworkMapper.countSubmitHomeworkListTeacher(studentHomeworkPage));
                result.put("rows", spiltToName(homeworkMapper.getSubmitHomeworkListByTeacherId(
                        studentHomeworkPage)));
            } else { //已自动批改作业   status == 4
                studentHomeworkPage.setIsAutomatic(1);
                studentHomeworkPage.setScore(1);
                result.put("total", homeworkMapper.countSubmitHomeworkListTeacher(studentHomeworkPage));
                result.put("rows", spiltToName(homeworkMapper.getSubmitHomeworkListByTeacherId(
                        studentHomeworkPage)));
            }
        }
        return result;
    }

    private List<HomeworkMessage> spiltToName(List<HomeworkMessage> list) {
        for (HomeworkMessage h : list) {
            String[] ids = h.getQuestionIds().split(",");
            if (ids.length > 0) {
                StringBuilder questionName = new StringBuilder();
                for (String s : ids) {
                    Question question = questionMapper.selectByPrimaryKey(Integer.parseInt(s));
                    if (question != null)
                        questionName.append(question.getQuestionName()).append("，");
                }
                if (!questionName.toString().equals(""))
                    h.setQuestionName(questionName.toString().substring(0, questionName.length() - 1));
            }
        }
        return list;
    }

    public JSONObject getStudentHomeworkCount(HttpSession httpSession, Integer courseId) {
        JSONObject result = new JSONObject();
        Object user = httpSession.getAttribute("user");
        if (user == null) {
            result.put("paramOne", 0);
            result.put("paramTwo", 0);
            result.put("paramThree", 0);
            result.put("paramFour", 0);
            return result;
        }
        Users users = (Users) user;
        int id = users.getId();
        StudentHomeworkPage studentHomeworkPage = new StudentHomeworkPage();
        studentHomeworkPage.setId(id);
        studentHomeworkPage.setCourseId(courseId);
        //当前用户是学生
        if (users.getRoleId() == 3) {
            result.put("paramOne", homeworkMapper.countUnSubmitListStudent(studentHomeworkPage));//未提交作业
            result.put("paramTwo", homeworkMapper.countSubmitListStudent(studentHomeworkPage));//已提交作业
            result.put("paramThree", homeworkMapper.countMarkListStudent(studentHomeworkPage));//未批改作业
            studentHomeworkPage.setScore(1);//设置score值不为空，是已批改的作业
            result.put("paramFour", homeworkMapper.countMarkListStudent(studentHomeworkPage));//已批改作业
        } else if (users.getRoleId() == 2) { //用户是老师
            studentHomeworkPage.setIsAutomatic(0); //设置批改为手动
            //未手动批改作业
            result.put("paramOne", homeworkMapper.countSubmitHomeworkListTeacher(studentHomeworkPage));
            studentHomeworkPage.setScore(1);//设置score值不为空，是已批改的作业
            //已手动批改作业
            result.put("paramTwo", homeworkMapper.countSubmitHomeworkListTeacher(studentHomeworkPage));
            studentHomeworkPage.setIsAutomatic(1);//设置批改为自动
            studentHomeworkPage.setScore(null);//设置score值为空，是未批改的作业
            //未自动批改作业
            result.put("paramThree", homeworkMapper.countSubmitHomeworkListTeacher(studentHomeworkPage));
            studentHomeworkPage.setScore(1);//设置score值不为空，是已批改的作业
            //已自动批改作业
            result.put("paramFour", homeworkMapper.countSubmitHomeworkListTeacher(studentHomeworkPage));
        }

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
        model.addAttribute("questionNumber", questionIds.length);
//        StringBuilder homeworkDetail = new StringBuilder();
        List<Question> questionList = new ArrayList<>();
        for (int i = 0; i < questionIds.length; i++) {
            Question question = questionMapper.selectByPrimaryKey(Integer.parseInt(questionIds[i]));
            questionList.add(question);
//            homeworkDetail.append("第").append(i + 1).append("题：").append(question.getQuestionName())
//                    .append("\n\n")
//                    .append(question.getDescription());
//            if (i < questionIds.length - 1)
//                homeworkDetail.append("\n\n\n\n");
        }
        model.addAttribute("homeworkDetail", questionList);
        HomeworkScore homeworkScore = new HomeworkScore();
        Users user = (Users) session.getAttribute("user");
        homeworkScore.setStudentId(user.getId());
        homeworkScore.setHomeworkId(homeworkId);
        List<HomeworkScore> scoreList = homeworkScoreMapper.selectList(homeworkScore);
        if (scoreList != null && scoreList.size() > 0)
            model.addAttribute("homeworkScore", scoreList.get(0));
        return "success";
    }

    public String saveHomeworkScore(HomeworkScore homeworkScore) throws ParseException {
        HomeworkScore test = new HomeworkScore();
        test.setHomeworkId(homeworkScore.getHomeworkId());
        test.setStudentId(homeworkScore.getStudentId());
        List<HomeworkScore> scoreList = homeworkScoreMapper.selectList(test);
        Date now = DateUtil.getNow();
        if (scoreList != null && scoreList.size() > 0) {
            HomeworkScore haveOne = scoreList.get(0);
            haveOne.setAnswer(homeworkScore.getAnswer());
            haveOne.setFileName(homeworkScore.getFileName());
            haveOne.setFilePath(homeworkScore.getFilePath());
            haveOne.setSubmitTime(now);
            homeworkScoreMapper.updateByPrimaryKey(haveOne);
        } else {
            homeworkScore.setSubmitTime(now);
            homeworkScoreMapper.insert(homeworkScore);
        }
        return JSONObject.toJSONString("success");
    }

    public JSONArray getHomeworkScoreAnswer(Integer homeworkScoreId) {
        HomeworkScore homeworkScore = new HomeworkScore();
        homeworkScore.setId(homeworkScoreId);
        List<HomeworkScore> scoreList = homeworkScoreMapper.selectList(homeworkScore);
        JSONArray jsonArray = new JSONArray();
        if (scoreList != null && scoreList.size() > 0) {
            HomeworkScore findHomeworkScore = scoreList.get(0);
            if (findHomeworkScore.getAnswer() != null) {
                String[] answers = findHomeworkScore.getAnswer().split("div----------div");
                jsonArray.addAll(Arrays.asList(answers));
                return jsonArray;
            }
        }
        return jsonArray;
    }

    public JSONObject getHomeworkMessageList(HttpSession httpSession,
                                             HomeworkManagePage homeworkManagePage) {
        JSONObject result = new JSONObject();
        homeworkManagePage.setOffset(
                (homeworkManagePage.getPageNumber() - 1) * homeworkManagePage.getPageSize());
        Object user = httpSession.getAttribute("user");
        if (user == null) {
            result.put("total", 0);
            result.put("rows", new ArrayList<>());
            return result;
        }
        Users teacher = (Users) user;
        homeworkManagePage.setTeacherId(teacher.getId());
        result.put("total", homeworkMapper.countHomeworkMessageList(homeworkManagePage));
        result.put("rows", spiltToName(homeworkMapper.getHomeworkMessageList(homeworkManagePage)));
        return result;
    }

    public String deleteHomework(Integer homeworkId) {
        homeworkMapper.deleteByPrimaryKey(homeworkId);
        HomeworkScore homeworkScore = new HomeworkScore();
        homeworkScore.setHomeworkId(homeworkId);
        List<HomeworkScore> homeworkScoreList = homeworkScoreMapper.selectList(homeworkScore);
        if (homeworkScoreList != null && homeworkScoreList.size() > 0)
            homeworkScoreMapper.batchDelete(homeworkScoreList);
        return JSONObject.toJSONString("success");
    }

    public String deleteHomeworkList(String ids) {
        String[] listIds = ids.split(",");
        for (String id : listIds) {
            deleteHomework(Integer.valueOf(id));
        }
        return JSONObject.toJSONString("success");
    }

    public Homework getHomework(Integer homeworkId) {
        return homeworkMapper.selectByPrimaryKey(homeworkId);
    }

    public String getStudentHomeworkDetail(Integer homeworkScoreId, Model model) {
        HomeworkScore homeworkScore = homeworkScoreMapper.selectByPrimaryKey(homeworkScoreId);
        if (homeworkScore == null)
            return "null";
        model.addAttribute("homeworkScore", homeworkScore);
        Homework homework = homeworkMapper.selectByPrimaryKey(homeworkScore.getHomeworkId());
        model.addAttribute("courseId", homework.getCourseId());
        String[] questionIds = homework.getQuestionIds().split(",");
        model.addAttribute("questionNumber", questionIds.length);
        if (homeworkScore.getAnswer() == null)
            model.addAttribute("answer", "null");
        return "success";
    }

    public String updateHomeworkScore(HomeworkScore homeworkScore) {
        homeworkScoreMapper.updateByPrimaryKeySelective(homeworkScore);
        return JSONObject.toJSONString("success");
    }

    public List<HomeworkMessage> getHomeworkListByTeacherId(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null)
            return null;
        Users teacher = (Users) user;
        return spiltToName(homeworkMapper.getHomeworkListByTeacherId(teacher.getId()));
    }

    public List<MajorClass> getMajorClassList(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null)
            return null;
        Users teacher = (Users) user;
        Course course = new Course();
        course.setTeacherId(teacher.getId());
        List<CourseDetail> courseList = courseMapper.selectCourseList(course);
        Set<String> ids = new HashSet<>();
        if (courseList != null && courseList.size() > 0) {
            for (CourseDetail c : courseList) {
                String[] idArray = c.getClassIds().split(",");
                ids.addAll(Arrays.asList(idArray));
            }
        }
        StringBuilder res = new StringBuilder();
        for (String id : ids)
            res.append(id).append(",");
        if (res.length() > 0)
            res.delete(res.length() - 1, res.length());
        String s = res.toString();
        return s.equals("") ? null : majorClassMapper.getMajorClassListByIds(s);
    }

    public JSONObject getHomeworkSeeScoreList(HttpSession httpSession,
                                              HomeworkSeeScorePage homeworkSeeScorePage) {
        JSONObject result = new JSONObject();
        homeworkSeeScorePage.setOffset(
                (homeworkSeeScorePage.getPageNumber() - 1) * homeworkSeeScorePage.getPageSize());
        Object user = httpSession.getAttribute("user");
        if (user == null)
            return null;
        Users teacher = (Users) user;
        homeworkSeeScorePage.setTeacherId(teacher.getId());
        result.put("total", homeworkScoreMapper.countHomeworkScoreList(homeworkSeeScorePage));
        result.put("rows", spiltToName(homeworkScoreMapper.getHomeworkScoreList(homeworkSeeScorePage)));
        return result;
    }

    public List<HomeworkMessage> getHomeworkList(Integer courseId, HttpSession httpSession) {
        Object user = httpSession.getAttribute("user");
        if (user == null)
            return null;
        Users teacher = (Users) user;
        HomeworkManagePage homeworkManagePage = new HomeworkManagePage();
        homeworkManagePage.setTeacherId(teacher.getId());
        homeworkManagePage.setCourseId(courseId);
        return spiltToName(homeworkMapper.getHomeworkMessageList(homeworkManagePage));
    }

    public List<MajorClass> getMajorClassListByHomeworkId(Integer homeworkId) {
        HomeworkManagePage homeworkManagePage = new HomeworkManagePage();
        homeworkManagePage.setId(homeworkId);
        List<HomeworkMessage> list = homeworkMapper.getHomeworkMessageList(homeworkManagePage);
        if (list != null && list.size() > 0 && list.get(0).getClassIds() != null)
            return majorClassMapper.getMajorClassListByIds(list.get(0).getClassIds());
        else
            return null;
    }

    public HomeworkScore getHomeworkScore(Integer homeworkScoreId) {
        return homeworkScoreMapper.selectByPrimaryKey(homeworkScoreId);
    }

    public JSONObject getStudentScoreList(HttpSession httpSession,
                                          StudentHomeworkPage studentHomeworkPage) {
        JSONObject result = new JSONObject();
        studentHomeworkPage.setOffset(
                (studentHomeworkPage.getPageNumber() - 1) * studentHomeworkPage.getPageSize());
        Object user = httpSession.getAttribute("user");
        if (user == null)
            return null;
        Users student = (Users) user;
        studentHomeworkPage.setId(student.getId());
        studentHomeworkPage.setScore(1);
        result.put("total", homeworkMapper.countMarkListStudent(studentHomeworkPage));
        result.put("rows", spiltToName(homeworkMapper.getMarkListByStudentId(studentHomeworkPage)));
        return result;
    }

    public JSONArray getCountScoreByCourseId(Integer courseId) {
        Count count = new Count();
        count.setCourseId(courseId);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(homeworkScoreMapper.avgCount(count));
        int j = 7, i;
        for (i = 6; i < 10; ) {
            count.setLow((i++) * 10);
            count.setHigh((j++) * 10);
            jsonArray.add(homeworkScoreMapper.avgCountBetween(count));
        }
        return jsonArray;
    }

    public JSONArray getCountScore(Count count) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(homeworkScoreMapper.countLow(count));
        int j = 13, i;
        for (i = 12; i < 20; ) {
            count.setLow((i++) * 5);
            count.setHigh((j++) * 5);
            jsonArray.add(homeworkScoreMapper.countBetween(count));
        }
        return jsonArray;
    }

    public JSONObject getMessageReplyList(HttpSession httpSession, MessagePage messagePage) {
        JSONObject result = new JSONObject();
        messagePage.setOffset((messagePage.getPageNumber() - 1) * messagePage.getPageSize());
        messagePage = makeMessagePage(httpSession, messagePage);
        if (messagePage == null)
            return null;
        if (messagePage.getRoleId() == 3) {//学生
            result.put("total", messageInteractionMapper.countStudentMessageReply(messagePage));
            result.put("rows", messageInteractionMapper.getStudentMessageReplyList(messagePage));
        } else if (messagePage.getRoleId() == 2) {//老师
            result.put("total", messageInteractionMapper.countTeacherMessageReply(messagePage));
            result.put("rows", messageInteractionMapper.getTeacherMessageReplyList(messagePage));
        } else
            return null;
        return result;
    }

    public JSONObject getMessageReplyNumber(HttpSession httpSession, MessagePage messagePage) {
        JSONObject result = new JSONObject();
        messagePage.setIsNew(1);
        messagePage = makeMessagePage(httpSession, messagePage);
        if (messagePage == null) {
            result.put("paramOne", 0);
            result.put("paramTwo", 0);
            return result;
        }
        if (messagePage.getRoleId() == 3)//学生
            result.put("paramOne", messageInteractionMapper.countStudentMessageReply(messagePage));
        else if (messagePage.getRoleId() == 2)//老师
            result.put("paramOne", messageInteractionMapper.countTeacherMessageReply(messagePage));
        else
            result.put("paramTwo", 0);
        messagePage.setIsNew(0);
        messagePage = makeMessagePage(httpSession, messagePage);
        if (messagePage == null) {
            result.put("paramOne", 0);
            result.put("paramTwo", 0);
            return result;
        }
        if (messagePage.getRoleId() == 3)//学生
            result.put("paramTwo", messageInteractionMapper.countStudentMessageReply(messagePage));
        else if (messagePage.getRoleId() == 2)//老师
            result.put("paramTwo", messageInteractionMapper.countTeacherMessageReply(messagePage));
        else
            result.put("paramTwo", 0);
        return result;
    }

    public List<HomeworkMessage> getHomeworkListByStudentId(HttpSession httpSession,
                                                            StudentHomeworkPage studentHomeworkPage) {
        Object user = httpSession.getAttribute("user");
        if (user == null)
            return null;
        Users student = (Users) user;
        studentHomeworkPage.setId(student.getId());
        return homeworkMapper.getHomeworkListByStudentId(studentHomeworkPage);
    }


    public String saveMessageInteraction(HttpSession httpSession, MessageInteraction messageInteraction) {
        messageInteraction = mergeMessageParam(httpSession, messageInteraction);
        if (messageInteraction == null)
            return JSONObject.toJSONString("fail");
        messageInteraction.setIsReply(0);
        messageInteraction.setIsSee(0);
        MessageInteraction test = new MessageInteraction();
        test.setStudentId(messageInteraction.getStudentId());
        test.setHomeworkId(messageInteraction.getHomeworkId());
        List<MessageInteraction> list = messageInteractionMapper.getMessageList(test);
        if (list != null && list.size() > 0)
            return JSONObject.toJSONString("repeat");
        messageInteractionMapper.insert(messageInteraction);
        return JSONObject.toJSONString("success");
    }

    public MessageReply saveReply(HttpSession httpSession, MessageInteraction messageInteraction) {
        messageInteraction = mergeMessageParam(httpSession, messageInteraction);
        if (messageInteraction == null)
            return null;
        messageInteraction.setIsReply(1);
        messageInteraction.setIsSee(0);
        messageInteractionMapper.insert(messageInteraction);
        MessageReply messageReply = new MessageReply();
        messageReply.setMessageTime(messageInteraction.getMessageTime());
        messageReply.setUserName(((Users) httpSession.getAttribute("user")).getUserName());
        return messageReply;
    }

    public List<MessageReply> getAllMessageList(HttpSession httpSession, MessagePage messagePage) {
        Object user = httpSession.getAttribute("user");
        if (user == null)
            return null;
        Users users = (Users) user;
        if (users.getRoleId() == 3) { //当前用户是学生
            return messageInteractionMapper.getAllMessageList(messagePage);
        } else if (users.getRoleId() == 2) { //用户是老师
            return messageInteractionMapper.getAllTeacherMessageList(messagePage);
        }
        return null;
    }

    public String updateMessage(MessageInteraction messageInteraction) {
        messageInteractionMapper.updateByPrimaryKeySelective(messageInteraction);
        return JSONObject.toJSONString("success");
    }


    private MessagePage makeMessagePage(HttpSession httpSession, MessagePage messagePage) {
        Object user = httpSession.getAttribute("user");
        int isNew = messagePage.getIsNew();
        if (user == null || (isNew != 1 && isNew != 0)) {
            return null;
        }
        Users users = (Users) user;
        if (users.getRoleId() == 3) { //当前用户是学生
            messagePage.setStudentId(users.getId());
            messagePage.setRoleId(3);
        } else if (users.getRoleId() == 2) { //用户是老师
            messagePage.setTeacherId(users.getId());
            messagePage.setRoleId(2);
        }
        if (isNew == 1) {//查询未查看的留言回复信息
            messagePage.setIsSee(0);//未查看过
            if (users.getRoleId() == 3) { //当前用户是学生
                messagePage.setIsReply(1);//是回复
                messagePage.setIsNotStudent(1);//不是学生本身回复
            } else if (users.getRoleId() == 2) { //用户是老师
                messagePage.setIsNotTeacher(1);//不是教师本身回复
            }
        } else {//以前自己发过的留言
            messagePage.setIsReply(0);//是留言
            messagePage.setIsSee(null);
            messagePage.setIsNotStudent(null);
            messagePage.setIsNotTeacher(null);
        }
        return messagePage;
    }

    private MessageInteraction mergeMessageParam(HttpSession httpSession, MessageInteraction messageInteraction) {
        Object user = httpSession.getAttribute("user");
        if (user == null)
            return null;
        Users users = (Users) user;
        messageInteraction.setUserId(users.getId());
        if (users.getRoleId() == 3) //当前用户是学生
            messageInteraction.setStudentId(users.getId());
        try {
            messageInteraction.setMessageTime(DateUtil.getNow());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return messageInteraction;
    }
}
