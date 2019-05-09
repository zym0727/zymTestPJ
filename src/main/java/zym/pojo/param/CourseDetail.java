package zym.pojo.param;

import zym.pojo.Course;

/**
 * @author zym
 * *
 */
public class CourseDetail extends Course {

    private String teacherName;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
