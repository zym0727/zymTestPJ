package zym.dao;

import zym.pojo.Homework;
import zym.pojo.param.HomeworkManagePage;
import zym.pojo.param.HomeworkMessage;
import zym.pojo.param.StudentHomeworkPage;

import java.util.List;

public interface HomeworkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Homework record);

    int insertSelective(Homework record);

    Homework selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Homework record);

    int updateByPrimaryKey(Homework record);

    List<HomeworkMessage> getUnSubmitListByStudentId(StudentHomeworkPage studentHomeworkPage);

    int countUnSubmitListStudent(StudentHomeworkPage studentHomeworkPage);

    List<HomeworkMessage> getSubmitListByStudentId(StudentHomeworkPage studentHomeworkPage);

    int countSubmitListStudent(StudentHomeworkPage studentHomeworkPage);

    List<HomeworkMessage> getMarkListByStudentId(StudentHomeworkPage studentHomeworkPage);

    int countMarkListStudent(StudentHomeworkPage studentHomeworkPage);

    List<Homework> getHomeworkList(Homework homework);

    List<Homework> getHomeworkListByIsAssign(Integer isAssign);

    List<Homework> getHomeworkListByIsAutomatic(Integer isAutomatic);

    List<HomeworkMessage> getHomeworkMessageList(HomeworkManagePage homeworkManagePage);

    int countHomeworkMessageList(HomeworkManagePage homeworkManagePage);

    List<HomeworkMessage> getSubmitHomeworkListByTeacherId(StudentHomeworkPage studentHomeworkPage);

    int countSubmitHomeworkListTeacher(StudentHomeworkPage studentHomeworkPage);

    List<HomeworkMessage> getHomeworkListByTeacherId(Integer teacherId);
}