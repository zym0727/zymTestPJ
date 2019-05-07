package zym.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import zym.exception.MessageException;
import zym.pojo.LanguageMark;
import zym.pojo.MajorClass;
import zym.pojo.Role;
import zym.pojo.param.ClassPage;
import zym.pojo.param.Page;
import zym.service.ClassService;
import zym.service.LanguageMarkService;
import zym.service.RoleService;

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

    @RequestMapping(path = {"/userManage"}, method = RequestMethod.GET)
    public String getUserManage() {
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
    public String getCourseManage() {
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
        if (StringUtils.isEmpty(majorClass) || StringUtils.isEmpty(majorClass.getId())
                || StringUtils.isEmpty(majorClass.getClassNumber())
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
}
