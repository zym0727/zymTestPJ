package zym.dao;

import zym.pojo.MajorClass;

public interface MajorClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MajorClass record);

    int insertSelective(MajorClass record);

    MajorClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MajorClass record);

    int updateByPrimaryKey(MajorClass record);
}