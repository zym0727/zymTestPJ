package zym.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import zym.exception.MessageException;
import zym.pojo.Homework;
import zym.pojo.HomeworkScore;
import zym.pojo.MajorClass;
import zym.pojo.param.*;
import zym.service.CourseService;
import zym.service.HomeworkService;
import zym.service.ItemBankService;
import zym.service.QuestionService;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

/**
 * @author zym
 * *
 */
@Controller
@RequestMapping(path = {"/homework"})
public class HomeworkController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ItemBankService itemBankService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HomeworkService homeworkService;

    @RequestMapping(path = {"/teacher/assign/getPage"}, method = RequestMethod.GET)
    public String getItemBank(Model model, HttpSession session) {
        model.addAttribute("courseList", courseService.selectCourseList(session));
        model.addAttribute("itemBankList", itemBankService.getItemBankList());
        return "homework/assign";
    }

    @RequestMapping(path = {"/teacher/manage/getPage"}, method = RequestMethod.GET)
    public String getAssignHomework(Model model, HttpSession session) {
        model.addAttribute("courseList", courseService.selectCourseList(session));
        model.addAttribute("itemBankList", itemBankService.getItemBankList());
        return "homework/homework";
    }

    @RequestMapping(path = {"/teacher/see"}, method = RequestMethod.GET)
    public String getStudentHomework(Model model, HttpSession session) {
        model.addAttribute("courseList", courseService.selectCourseList(session));
        return "homework/teacherSee";
    }

    @RequestMapping(path = {"/student/see"}, method = RequestMethod.GET)
    public String getStudentSeePage(Model model, HttpSession session) {
        model.addAttribute("courseList", courseService.selectCourseList(session));
        return "homework/studentSee";
    }

    @RequestMapping(path = {"/teacher/checkHomework"}, method = RequestMethod.GET)
    public String getHomeworkScoreCheckPage(Model model, HttpSession session) {
        model.addAttribute("courseList", courseService.selectCourseList(session));
        model.addAttribute("homeworkMessageList", homeworkService.getHomeworkListByTeacherId(session));
        model.addAttribute("classList", homeworkService.getMajorClassList());
        return "homework/homeworkScore";
    }

    @RequestMapping(path = {"/student/submitHomework/{homeworkId}"}, method = RequestMethod.GET)
    public String getStudentHomeworkPage(@PathVariable Integer homeworkId, Model model,
                                         HttpSession session) {
        String s = homeworkService.getStudentHomework(homeworkId, model, session);
        if (s.equals("success"))
            return "homework/submit";
        else {
            model.addAttribute("courseList", courseService.selectCourseList(session));
            return "homework/studentSee";
        }
    }

    @RequestMapping(path = {"/student/score"}, method = RequestMethod.GET)
    public String getStudentHomeworkScorePage(Model model, HttpSession session) {
        model.addAttribute("courseList", courseService.selectCourseList(session));
        return "homework/studentScore";
    }

    @RequestMapping(path = {"/teacher/studentHomework/{homeworkScoreId}"}, method = RequestMethod.GET)
    public String getStudentHomeworkDetail(@PathVariable Integer homeworkScoreId, Model model,
                                           HttpSession session) {
        String s = homeworkService.getStudentHomeworkDetail(homeworkScoreId, model);
        if (s.equals("success"))
            return "homework/mark";
        else {
            model.addAttribute("courseList", courseService.selectCourseList(session));
            return "homework/teacherSee";
        }
    }

    @RequestMapping(path = {"/teacher/questionList/{itemBankId}"}, method = RequestMethod.GET)
    @ResponseBody
    public List<QuestionDetail> getQuestionDetailList(@PathVariable Integer itemBankId) {
        return questionService.getQuestionList(itemBankId);
    }

    @RequestMapping(path = {"/teacher/assign"}, method = RequestMethod.POST)
    @ResponseBody
    public String assignHomework(Homework homework) {
        if (StringUtils.isEmpty(homework) || StringUtils.isEmpty(homework.getTitle())
                || StringUtils.isEmpty(homework.getCourseId())
                || StringUtils.isEmpty(homework.getAssignTime())
                || StringUtils.isEmpty(homework.getDeadline())
                || StringUtils.isEmpty(homework.getIsAutomatic()))
            throw new MessageException("参数为空");
        return homeworkService.saveOrUpdateAssignHomework(homework, true);
    }

    @RequestMapping(path = {"/student/homeworkList", "/teacher/homeworkList"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getStudentHomeworkList(HttpSession httpSession,
                                             StudentHomeworkPage studentHomeworkPage) {
        if (StringUtils.isEmpty(studentHomeworkPage) || StringUtils.isEmpty(studentHomeworkPage.getStatus())
                || StringUtils.isEmpty(studentHomeworkPage.getPageNumber())
                || StringUtils.isEmpty(studentHomeworkPage.getPageSize()))
            throw new MessageException("参数为空");
        return homeworkService.getStudentHomeworkList(httpSession, studentHomeworkPage);
    }

    @RequestMapping(path = {"/student/count", "/teacher/count"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getStudentHomeworkCount(HttpSession httpSession, Integer courseId) {
        return homeworkService.getStudentHomeworkCount(httpSession, courseId);
    }

    @RequestMapping(path = {"/student/submit"}, method = RequestMethod.POST)
    @ResponseBody
    public String submitHomework(HomeworkScore homeworkScore) throws ParseException {
        return homeworkService.saveHomeworkScore(homeworkScore);
    }

    @RequestMapping(path = {"/student/AnswerList/get/{homeworkScoreId}",
            "/teacher/AnswerList/get/{homeworkScoreId}"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONArray getHomeworkScoreAnswer(@PathVariable Integer homeworkScoreId) {
        return homeworkService.getHomeworkScoreAnswer(homeworkScoreId);
    }

    @RequestMapping(path = {"/teacher/homeworkManageList"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getHomeworkManageList(HttpSession httpSession,
                                            HomeworkManagePage homeworkManagePage) {
        if (StringUtils.isEmpty(homeworkManagePage) || StringUtils.isEmpty(homeworkManagePage.getPageNumber())
                || StringUtils.isEmpty(homeworkManagePage.getPageSize()))
            throw new MessageException("参数为空");
        return homeworkService.getHomeworkMessageList(httpSession, homeworkManagePage);
    }

    @RequestMapping(path = {"/teacher/delete/{homeworkId}"}, method = RequestMethod.GET)
    @ResponseBody
    public String deleteHomework(@PathVariable Integer homeworkId) {
        return homeworkService.deleteHomework(homeworkId);
    }

    @RequestMapping(path = {"/teacher/batchDelete"}, method = RequestMethod.POST)
    @ResponseBody
    public String batchDeleteHomework(String ids) {
        if (StringUtils.isEmpty(ids))
            throw new MessageException("参数为空");
        return homeworkService.deleteHomeworkList(ids);
    }

    @RequestMapping(path = {"/teacher/update"}, method = RequestMethod.POST)
    @ResponseBody
    public String updateHomework(Homework homework) {
        if (StringUtils.isEmpty(homework) || StringUtils.isEmpty(homework.getTitle())
                || StringUtils.isEmpty(homework.getCourseId())
                || StringUtils.isEmpty(homework.getAssignTime())
                || StringUtils.isEmpty(homework.getDeadline())
                || StringUtils.isEmpty(homework.getIsAutomatic())
                || StringUtils.isEmpty(homework.getId()))
            throw new MessageException("参数为空");
        return homeworkService.saveOrUpdateAssignHomework(homework, false);
    }

    @RequestMapping(path = {"/teacher/get/{homeworkId}"}, method = RequestMethod.GET)
    @ResponseBody
    public Homework getHomework(@PathVariable Integer homeworkId) {
        return homeworkService.getHomework(homeworkId);
    }

    @RequestMapping(path = {"/teacher/homeworkScore/get/{homeworkScoreId}"}, method = RequestMethod.GET)
    @ResponseBody
    public HomeworkScore getHomeworkScore(@PathVariable Integer homeworkScoreId) {
        return homeworkService.getHomeworkScore(homeworkScoreId);
    }

    @RequestMapping(path = {"/teacher/mark"}, method = RequestMethod.POST)
    @ResponseBody
    public String markHomework(HomeworkScore homeworkScore) {
        if (StringUtils.isEmpty(homeworkScore) || StringUtils.isEmpty(homeworkScore.getId()) ||
                StringUtils.isEmpty(homeworkScore.getScore()))
            throw new MessageException("参数为空");
        return homeworkService.updateHomeworkScore(homeworkScore);
    }

    @RequestMapping(path = {"/teacher/homeworkSeeScore"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getHomeworkSeeScoreList(HttpSession httpSession,
                                              HomeworkSeeScorePage homeworkSeeScorePage) {
        if (StringUtils.isEmpty(homeworkSeeScorePage)
                || StringUtils.isEmpty(homeworkSeeScorePage.getPageNumber())
                || StringUtils.isEmpty(homeworkSeeScorePage.getPageSize()))
            throw new MessageException("参数为空");
        return homeworkService.getHomeworkSeeScoreList(httpSession, homeworkSeeScorePage);
    }

    @RequestMapping(path = {"/teacher/homeworkList/{courseId}"}, method = RequestMethod.GET)
    @ResponseBody
    public List<HomeworkMessage> getHomeworkList(@PathVariable Integer courseId,
                                                 HttpSession httpSession) {
        return homeworkService.getHomeworkList(courseId, httpSession);
    }

    @RequestMapping(path = {"/teacher/majorClassList/{homeworkId}"}, method = RequestMethod.GET)
    @ResponseBody
    public List<MajorClass> getMajorClassListByHomeworkId(@PathVariable Integer homeworkId) {
        return homeworkService.getMajorClassListByHomeworkId(homeworkId);
    }

    @RequestMapping(path = {"/student/homeworkScoreList"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getStudentScoreList(HttpSession httpSession,
                                          StudentHomeworkPage studentHomeworkPage) {
        if (StringUtils.isEmpty(studentHomeworkPage) || StringUtils.isEmpty(studentHomeworkPage.getPageNumber())
                || StringUtils.isEmpty(studentHomeworkPage.getPageSize()))
            throw new MessageException("参数为空");
        return homeworkService.getStudentScoreList(httpSession, studentHomeworkPage);
    }
}
