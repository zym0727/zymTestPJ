package zym.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Homework {
    private Integer id;

    private Integer courseId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date assignTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date deadline;

    private String questionIds;

    private Integer isAutomatic;

    private Integer isAssign;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(String questionIds) {
        this.questionIds = questionIds == null ? null : questionIds.trim();
    }

    public Integer getIsAutomatic() {
        return isAutomatic;
    }

    public void setIsAutomatic(Integer isAutomatic) {
        this.isAutomatic = isAutomatic;
    }

    public Integer getIsAssign() {
        return isAssign;
    }

    public void setIsAssign(Integer isAssign) {
        this.isAssign = isAssign;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}