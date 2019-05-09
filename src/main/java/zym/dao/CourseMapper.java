package zym.dao;

import zym.pojo.Course;
import zym.pojo.param.CourseDetail;
import zym.pojo.param.CoursePage;

import java.util.List;

public interface CourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    List<CourseDetail> selectCourseList(Course course);

    List<CourseDetail> selectCourseListByStudentId(Integer studentId);

    List<CourseDetail> getCourseList(CoursePage coursePage);

    int countAll(CoursePage coursePage);

    void batchDelete(String[] list);

    List<Course> selectRepeat(Course course);
}