package zym.dao;

import org.apache.ibatis.annotations.Param;
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

    int countUnSubmitListStudent(Integer id);

    List<HomeworkMessage> getSubmitListByStudentId(StudentHomeworkPage studentHomeworkPage);

    int countSubmitListStudent(Integer id);

    List<HomeworkMessage> getUnMarkListByStudentId(StudentHomeworkPage studentHomeworkPage);

    int countUnMarkListStudent(Integer id);

    List<HomeworkMessage> getMarkListByStudentId(StudentHomeworkPage studentHomeworkPage);

    int countMarkListStudent(Integer id);

    List<Homework> getHomeworkList(Homework homework);

    List<Homework> getHomeworkListByIsAssign(Integer isAssign);

    List<Homework> getHomeworkListByIsAutomatic(Integer isAutomatic);

    List<HomeworkMessage> getHomeworkMessageList(HomeworkManagePage homeworkManagePage);

    int countHomeworkMessageList(HomeworkManagePage homeworkManagePage);
}