package zym.dao;

import org.apache.ibatis.annotations.Param;
import zym.pojo.MajorClass;
import zym.pojo.param.ClassPage;

import java.util.List;

public interface MajorClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MajorClass record);

    int insertSelective(MajorClass record);

    MajorClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MajorClass record);

    int updateByPrimaryKey(MajorClass record);

    List<MajorClass> getMajorClassList(MajorClass record);

    List<MajorClass> getMajorClassListByIds(@Param("classIds") String classIds);

    List<MajorClass> selectClassList(ClassPage classPage);

    List<MajorClass> selectRepeat(MajorClass majorClass);

    int countAll(ClassPage classPage);

    void batchDelete(String[] list);
}