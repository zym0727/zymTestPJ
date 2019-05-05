package zym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zym.dao.CourseMapper;
import zym.dao.MajorClassMapper;
import zym.pojo.Course;
import zym.pojo.MajorClass;
import zym.pojo.Users;

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

    public List<Course> selectCourseList(HttpSession session) {
        if (session.getAttribute("user") == null)
            return null;
        Users users = (Users) session.getAttribute("user");
        if (users == null)
            return null;
        List<Course> list = null;
        if (users.getRoleId() == 3) { //当前用户是学生
            list = courseMapper.selectCourseListByStudentId(users.getId());
        } else if (users.getRoleId() == 2) { //当前用户是老师
            Course course = new Course();
            course.setTeacherId(users.getId());
            list = courseMapper.selectCourseList(course);
        }
        if (list != null && list.size() > 0)
            for (Course c : list) {
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
        return list;
    }
}
