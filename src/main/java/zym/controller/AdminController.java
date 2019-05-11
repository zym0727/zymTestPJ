package zym.controller;

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
import zym.pojo.*;
import zym.pojo.param.ClassPage;
import zym.pojo.param.CoursePage;
import zym.pojo.param.Page;
import zym.pojo.param.UserPage;
import zym.service.*;

/**
 * @author zym
 * *
 */
@Controller
@RequestMapping(path = {"/admin"})
public class AdminController {

    @Autowired
    private LanguageMarkService languageMarkService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ClassService classService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @RequestMapping(path = {"/userManage"}, method = RequestMethod.GET)
    public String getUserManage(Model model) {
        model.addAttribute("roleList", roleService.getRoleList());
        model.addAttribute("classList", classService.getClassList());
        return "admin/userInfo";
    }

    @RequestMapping(path = {"/roleManage"}, method = RequestMethod.GET)
    public String getRoleManage() {
        return "admin/roleInfo";
    }

    @RequestMapping(path = {"/classManage"}, method = RequestMethod.GET)
    public String getClassManage() {
        return "admin/classInfo";
    }

    @RequestMapping(path = {"/courseManage"}, method = RequestMethod.GET)
    public String getCourseManage(Model model) {
        model.addAttribute("teacherList", userService.getTeacherList());
        model.addAttribute("classList", classService.getClassList());
        return "admin/courseInfo";
    }

    @RequestMapping(path = {"/languageManage"}, method = RequestMethod.GET)
    public String getLanguageManage() {
        return "admin/languageInfo";
    }

    @RequestMapping(path = {"/languageMark/delete/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public String deleteLanguageMarkById(@PathVariable Integer id) {
        return languageMarkService.deleteLanguageMark(id);
    }

    @RequestMapping(path = {"/languageMark/get/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public LanguageMark getLanguageMarkById(@PathVariable Integer id) {
        return languageMarkService.getLanguageMark(id);
    }

    @RequestMapping(path = {"/languageMark/update"}, method = RequestMethod.POST)
    @ResponseBody
    public String updateLanguageMark(LanguageMark languageMark) {
        if (StringUtils.isEmpty(languageMark) || StringUtils.isEmpty(languageMark.getId()) ||
                StringUtils.isEmpty(languageMark.getMark()))
            throw new MessageException("参数为空");
        return languageMarkService.updateLanguageMark(languageMark);
    }

    @RequestMapping(path = {"/languageMark/add"}, method = RequestMethod.POST)
    @ResponseBody
    public String addLanguageMark(LanguageMark languageMark) {
        if (StringUtils.isEmpty(languageMark) || StringUtils.isEmpty(languageMark.getMark()))
            throw new MessageException("参数为空");
        return languageMarkService.saveLanguageMark(languageMark);
    }

    @RequestMapping(path = {"/languageMark/batchDelete"}, method = RequestMethod.POST)
    @ResponseBody
    public String batchDeleteItemBank(String ids) {
        if (StringUtils.isEmpty(ids))
            throw new MessageException("参数为空");
        return languageMarkService.deleteBatch(ids);
    }

    @RequestMapping(path = {"/languageTable/list"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getLanguageMarkList(Page page) {
        if (StringUtils.isEmpty(page) || StringUtils.isEmpty(page.getPageSize()) ||
                StringUtils.isEmpty(page.getPageNumber()))
            throw new MessageException("参数为空");
        return languageMarkService.getLanguageMarkList(page);
    }

    @RequestMapping(path = {"/role/delete/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public String deleteRoleById(@PathVariable Integer id) {
        return roleService.deleteRole(id);
    }

    @RequestMapping(path = {"/role/get/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public Role getRoleById(@PathVariable Integer id) {
        return roleService.getRole(id);
    }

    @RequestMapping(path = {"/role/update"}, method = RequestMethod.POST)
    @ResponseBody
    public String updateRole(Role role) {
        if (StringUtils.isEmpty(role) || StringUtils.isEmpty(role.getId()) ||
                StringUtils.isEmpty(role.getRoleName()))
            throw new MessageException("参数为空");
        return roleService.updateRole(role);
    }

    @RequestMapping(path = {"/role/add"}, method = RequestMethod.POST)
    @ResponseBody
    public String addRole(Role role) {
        if (StringUtils.isEmpty(role) || StringUtils.isEmpty(role.getRoleName()))
            throw new MessageException("参数为空");
        return roleService.saveRole(role);
    }

    @RequestMapping(path = {"/role/batchDelete"}, method = RequestMethod.POST)
    @ResponseBody
    public String batchDeleteRole(String ids) {
        if (StringUtils.isEmpty(ids))
            throw new MessageException("参数为空");
        return roleService.deleteBatch(ids);
    }

    @RequestMapping(path = {"/roleTable/list"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getRoleList(Page page) {
        if (StringUtils.isEmpty(page) || StringUtils.isEmpty(page.getPageSize()) ||
                StringUtils.isEmpty(page.getPageNumber()))
            throw new MessageException("参数为空");
        return roleService.getRoleList(page);
    }

    @RequestMapping(path = {"/majorClass/delete/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public String deleteMajorClassById(@PathVariable Integer id) {
        return classService.deleteMajorClass(id);
    }

    @RequestMapping(path = {"/majorClass/get/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public MajorClass getMajorClassById(@PathVariable Integer id) {
        return classService.getMajorClass(id);
    }

    @RequestMapping(path = {"/majorClass/update"}, method = RequestMethod.POST)
    @ResponseBody
    public String updateMajorClass(MajorClass majorClass) {
        if (StringUtils.isEmpty(majorClass) || StringUtils.isEmpty(majorClass.getId())
                || StringUtils.isEmpty(majorClass.getClassNumber())
                || StringUtils.isEmpty(majorClass.getClassName())
                || StringUtils.isEmpty(majorClass.getGrade()))
            throw new MessageException("参数为空");
        return classService.updateMajorClass(majorClass);
    }

    @RequestMapping(path = {"/majorClass/add"}, method = RequestMethod.POST)
    @ResponseBody
    public String addMajorClass(MajorClass majorClass) {
        if (StringUtils.isEmpty(majorClass) || StringUtils.isEmpty(majorClass.getClassNumber())
                || StringUtils.isEmpty(majorClass.getClassName())
                || StringUtils.isEmpty(majorClass.getGrade()))
            throw new MessageException("参数为空");
        return classService.saveMajorClass(majorClass);
    }

    @RequestMapping(path = {"/majorClass/batchDelete"}, method = RequestMethod.POST)
    @ResponseBody
    public String batchDeleteMajorClass(String ids) {
        if (StringUtils.isEmpty(ids))
            throw new MessageException("参数为空");
        return classService.deleteBatch(ids);
    }

    @RequestMapping(path = {"/classTable/list"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getMajorClassList(ClassPage classPage) {
        if (StringUtils.isEmpty(classPage) || StringUtils.isEmpty(classPage.getPageSize()) ||
                StringUtils.isEmpty(classPage.getPageNumber()))
            throw new MessageException("参数为空");
        return classService.getMajorClassList(classPage);
    }

    @RequestMapping(path = {"/course/delete/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public String deleteCourseById(@PathVariable Integer id) {
        return courseService.deleteCourse(id);
    }

    @RequestMapping(path = {"/course/get/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public Course getCourseById(@PathVariable Integer id) {
        return courseService.getCourse(id);
    }

    @RequestMapping(path = {"/course/update"}, method = RequestMethod.POST)
    @ResponseBody
    public String updateCourse(Course course) {
        if (StringUtils.isEmpty(course) || StringUtils.isEmpty(course.getId())
                || StringUtils.isEmpty(course.getCourseNumber())
                || StringUtils.isEmpty(course.getCourseName())
                || StringUtils.isEmpty(course.getClassIds())
                || StringUtils.isEmpty(course.getTeacherId())
                || StringUtils.isEmpty(course.getClassTime())
                || StringUtils.isEmpty(course.getSemester())
                || StringUtils.isEmpty(course.getCredit()))
            throw new MessageException("参数为空");
        return courseService.updateCourse(course);
    }

    @RequestMapping(path = {"/course/add"}, method = RequestMethod.POST)
    @ResponseBody
    public String addCourse(Course course) {
        if (StringUtils.isEmpty(course) || StringUtils.isEmpty(course.getCourseNumber())
                || StringUtils.isEmpty(course.getCourseName())
                || StringUtils.isEmpty(course.getClassIds())
                || StringUtils.isEmpty(course.getTeacherId())
                || StringUtils.isEmpty(course.getClassTime())
                || StringUtils.isEmpty(course.getSemester())
                || StringUtils.isEmpty(course.getCredit()))
            throw new MessageException("参数为空");
        return courseService.saveCourse(course);
    }

    @RequestMapping(path = {"/course/batchDelete"}, method = RequestMethod.POST)
    @ResponseBody
    public String batchDeleteCourse(String ids) {
        if (StringUtils.isEmpty(ids))
            throw new MessageException("参数为空");
        return courseService.deleteBatch(ids);
    }

    @RequestMapping(path = {"/courseTable/list"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getCourseList(CoursePage coursePage) {
        if (StringUtils.isEmpty(coursePage) || StringUtils.isEmpty(coursePage.getPageSize()) ||
                StringUtils.isEmpty(coursePage.getPageNumber()))
            throw new MessageException("参数为空");
        return courseService.getCourseList(coursePage);
    }

    @RequestMapping(path = {"/user/delete/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public String deleteUserById(@PathVariable Integer id) {
        return userService.deleteUsers(id);
    }

    @RequestMapping(path = {"/user/get/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public Users getUserById(@PathVariable Integer id) {
        return userService.getUsers(id);
    }

    @RequestMapping(path = {"/user/update"}, method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(Users users) {
        if (StringUtils.isEmpty(users) || StringUtils.isEmpty(users.getId())
                || StringUtils.isEmpty(users.getRoleId())
                || StringUtils.isEmpty(users.getAccount())
                || StringUtils.isEmpty(users.getPassword())
                || StringUtils.isEmpty(users.getEnabled()))
            throw new MessageException("参数为空");
        return userService.updateUsers(users);
    }

    @RequestMapping(path = {"/user/add"}, method = RequestMethod.POST)
    @ResponseBody
    public String addUser(Users users) {
        if (StringUtils.isEmpty(users) || StringUtils.isEmpty(users.getRoleId())
                || StringUtils.isEmpty(users.getAccount())
                || StringUtils.isEmpty(users.getPassword())
                || StringUtils.isEmpty(users.getEnabled()))
            throw new MessageException("参数为空");
        return userService.saveUsers(users);
    }

    @RequestMapping(path = {"/user/batchDelete"}, method = RequestMethod.POST)
    @ResponseBody
    public String batchDeleteUser(String ids) {
        if (StringUtils.isEmpty(ids))
            throw new MessageException("参数为空");
        return userService.deleteBatch(ids);
    }

    @RequestMapping(path = {"/userTable/list"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getUserList(UserPage userPage) {
        if (StringUtils.isEmpty(userPage) || StringUtils.isEmpty(userPage.getPageSize()) ||
                StringUtils.isEmpty(userPage.getPageNumber()))
            throw new MessageException("参数为空");
        return userService.getUserList(userPage);
    }
}
