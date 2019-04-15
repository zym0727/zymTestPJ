package zym.dao;

import zym.pojo.TestData;

import java.util.List;

public interface TestDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestData record);

    int insertSelective(TestData record);

    TestData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestData record);

    int updateByPrimaryKey(TestData record);

    List<TestData> getListByQuestionId(Integer QuestionId);
}