package zym.pojo.param;

/**
 * @author zym
 * *
 */
public class HomeworkManagePage extends Page {
    private Integer courseId;

    private Integer teacherId;

    private Integer id;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
