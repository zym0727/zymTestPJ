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
import zym.pojo.param.HomeworkManagePage;
import zym.pojo.param.QuestionDetail;
import zym.pojo.param.StudentHomeworkPage;
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
        return "homework/homework";
    }

    @RequestMapping(path = {"/student/see"}, method = RequestMethod.GET)
    public String getStudentSeePage() {
        return "homework/studentSee";
    }

    @RequestMapping(path = {"/student/submitHomework/{homeworkId}"}, method = RequestMethod.GET)
    public String getStudentHomeworkPage(@PathVariable Integer homeworkId, Model model,
                                         HttpSession session) {
        String s = homeworkService.getStudentHomework(homeworkId, model, session);
        if (s.equals("success"))
            return "homework/submit";
        else
            return "homework/studentSee";
    }

    @RequestMapping(path = {"/teacher/questionList/{itemBankId}"}, method = RequestMethod.GET)
    @ResponseBody
    public List<QuestionDetail> getQuestionDetailList(@PathVariable Integer itemBankId) {
        return questionService.getQuestionList(itemBankId);
    }

    @RequestMapping(path = {"/teacher/assign"}, method = RequestMethod.POST)
    @ResponseBody
    public String assignHomework(Homework homework) {
        if (StringUtils.isEmpty(homework) || StringUtils.isEmpty(homework.getCourseId()) ||
                StringUtils.isEmpty(homework.getAssignTime()) || StringUtils.isEmpty(homework.getDeadline())
                || StringUtils.isEmpty(homework.getIsAutomatic()))
            throw new MessageException("参数为空");
        return homeworkService.saveAssignHomework(homework);
    }

    @RequestMapping(path = {"/student/homeworkList"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getStudentHomeworkList(HttpSession httpSession,
                                             StudentHomeworkPage studentHomeworkPage) {
        if (StringUtils.isEmpty(studentHomeworkPage) || StringUtils.isEmpty(studentHomeworkPage.getStatus())
                || StringUtils.isEmpty(studentHomeworkPage.getPageNumber())
                || StringUtils.isEmpty(studentHomeworkPage.getPageSize()))
            throw new MessageException("参数为空");
        return homeworkService.getStudentHomeworkList(httpSession, studentHomeworkPage);
    }

    @RequestMapping(path = {"/student/count"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getStudentHomeworkCount(HttpSession httpSession) {
        return homeworkService.getStudentHomeworkCount(httpSession);
    }

    @RequestMapping(path = {"/student/submit"}, method = RequestMethod.POST)
    @ResponseBody
    public String submitHomework(HomeworkScore homeworkScore) throws ParseException {
        return homeworkService.saveHomework(homeworkScore);
    }

    @RequestMapping(path = {"/student/AnswerList/get/{homeworkScoreId}"}, method = RequestMethod.GET)
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
}
