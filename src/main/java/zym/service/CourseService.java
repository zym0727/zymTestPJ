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
        Users teacher = (Users) session.getAttribute("user");
        Course course = new Course();
        course.setTeacherId(teacher.getId());
        List<Course> list = courseMapper.selectCourseList(course);
        for (Course c : list) {
            String[] ids = c.getClassIds().split(",");
            if (ids.length > 0) {
                StringBuilder stringBuilder = new StringBuilder("");
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
