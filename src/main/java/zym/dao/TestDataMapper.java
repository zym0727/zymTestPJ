package zym.dao;

import zym.pojo.TestData;

public interface TestDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestData record);

    int insertSelective(TestData record);

    TestData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestData record);

    int updateByPrimaryKey(TestData record);
}