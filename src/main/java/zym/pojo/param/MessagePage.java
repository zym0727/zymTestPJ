package zym.pojo.param;

/**
 * @author zym
 * *
 */
public class MessagePage extends Page {

    private Integer studentId;

    private Integer courseId;

    private Integer isSee;

    private Integer isReply;

    private Integer isNew;

    private Integer isNotStudent;

    private Integer homeworkId;

    private Integer teacherId;

    private Integer isNotTeacher;

    private Integer roleId;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getIsSee() {
        return isSee;
    }

    public void setIsSee(Integer isSee) {
        this.isSee = isSee;
    }

    public Integer getIsReply() {
        return isReply;
    }

    public void setIsReply(Integer isReply) {
        this.isReply = isReply;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Integer getIsNotStudent() {
        return isNotStudent;
    }

    public void setIsNotStudent(Integer isNotStudent) {
        this.isNotStudent = isNotStudent;
    }

    public Integer getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Integer homeworkId) {
        this.homeworkId = homeworkId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getIsNotTeacher() {
        return isNotTeacher;
    }

    public void setIsNotTeacher(Integer isNotTeacher) {
        this.isNotTeacher = isNotTeacher;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
