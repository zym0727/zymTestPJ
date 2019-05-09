package zym.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zym.dao.CourseMapper;
import zym.dao.MajorClassMapper;
import zym.pojo.Course;
import zym.pojo.MajorClass;
import zym.pojo.Users;
import zym.pojo.param.CourseDetail;
import zym.pojo.param.CoursePage;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zym
 * *
 */
@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private MajorClassMapper majorClassMapper;

    public List<CourseDetail> selectCourseList(HttpSession session) {
        if (session.getAttribute("user") == null)
            return null;
        Users users = (Users) session.getAttribute("user");
        if (users == null)
            return null;
        List<CourseDetail> list = null;
        if (users.getRoleId() == 3) { //当前用户是学生
            list = courseMapper.selectCourseListByStudentId(users.getId());
        } else if (users.getRoleId() == 2) { //当前用户是老师
            Course course = new Course();
            course.setTeacherId(users.getId());
            list = courseMapper.selectCourseList(course);
        }
        return splitToName(list);
    }

    public JSONObject getCourseList(CoursePage coursePage) {
        coursePage.setOffset((coursePage.getPageNumber() - 1) * coursePage.getPageSize());
        JSONObject result = new JSONObject();
        result.put("total", courseMapper.countAll(coursePage));
        result.put("rows", splitToName(courseMapper.getCourseList(coursePage)));
        return result;
    }

    public Course getCourse(int id) {
        return courseMapper.selectByPrimaryKey(id);
    }

    public String deleteCourse(int id) {
        courseMapper.deleteByPrimaryKey(id);
        return JSONObject.toJSONString("success");
    }

    public String saveCourse(Course course) {
        if (checkRepeat(course))
            return JSONObject.toJSONString("repeat");
        courseMapper.insert(course);
        return JSONObject.toJSONString("success");
    }

    public String updateCourse(Course course) {
        if (checkRepeat(course))
            return JSONObject.toJSONString("repeat");
        courseMapper.updateByPrimaryKeySelective(course);
        return JSONObject.toJSONString("success");
    }

    public String deleteBatch(String ids) {
        courseMapper.batchDelete(ids.split(","));
        return JSONObject.toJSONString("success");
    }

    private Boolean checkRepeat(Course course) {
        List<Course> courseList = courseMapper.selectRepeat(course);
        if (course.getId() != null) { //修改
            Course origin = courseMapper.selectByPrimaryKey(course.getId());
            //课程编号不能重复
            return courseList != null && courseList.size() > 0 &&
                    !courseList.get(0).getId().equals(origin.getId());
        } else  //添加
            return courseList != null && courseList.size() > 0;
    }

    private List<CourseDetail> splitToName(List<CourseDetail> courseList) {
        if (courseList != null && courseList.size() > 0) {
            for (Course c : courseList) {
                String[] ids = c.getClassIds().split(",");
                if (ids.length > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String id : ids) {
                        MajorClass majorClass = majorClassMapper.selectByPrimaryKey(Integer.parseInt(id));
                        if (majorClass != null)
                            stringBuilder.append(majorClass.getClassName()).append(" ");
                    }
                    if (!stringBuilder.toString().equals(""))
                        c.setClassIds(stringBuilder.toString());
                }
            }
            return courseList;
        } else
            return null;
    }
}
