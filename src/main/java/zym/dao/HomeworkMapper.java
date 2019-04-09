package zym.dao;

import zym.pojo.Homework;
import zym.pojo.param.HomeworkMessage;

import java.util.List;

public interface HomeworkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Homework record);

    int insertSelective(Homework record);

    Homework selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Homework record);

    int updateByPrimaryKey(Homework record);

    List<HomeworkMessage> getUnSubmitListByStudentId(Integer id);

    int countUnSubmitListStudent(Integer id);

    List<HomeworkMessage> getSubmitListByStudentId(Integer id);

    int countSubmitListStudent(Integer id);

    List<HomeworkMessage> getUnMarkListByStudentId(Integer id);

    int countUnMarkListStudent(Integer id);

    List<HomeworkMessage> getMarkListByStudentId(Integer id);

    int countMarkListStudent(Integer id);
}