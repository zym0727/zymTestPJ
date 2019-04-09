package zym.dao;

import zym.pojo.HomeworkScore;

public interface HomeworkScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HomeworkScore record);

    int insertSelective(HomeworkScore record);

    HomeworkScore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HomeworkScore record);

    int updateByPrimaryKey(HomeworkScore record);


}