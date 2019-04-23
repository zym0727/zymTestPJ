package zym.pojo.param;

/**
 * @author zym
 * *
 */
public class HomeworkSeeScorePage extends HomeworkManagePage {

    private Integer classId;

    private Integer homeworkId;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Integer homeworkId) {
        this.homeworkId = homeworkId;
    }
}
