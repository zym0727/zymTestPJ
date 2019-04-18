package zym.dao;

import zym.pojo.HomeworkScore;

import java.util.List;

public interface HomeworkScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HomeworkScore record);

    int insertSelective(HomeworkScore record);

    HomeworkScore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HomeworkScore record);

    int updateByPrimaryKey(HomeworkScore record);

    List<HomeworkScore> selectList(HomeworkScore homeworkScore);

    List<HomeworkScore> selectListByHomeworkId(Integer homeworkId);//通过作业id来找已批改作业成绩列表

    int batchDelete(List<HomeworkScore> homeworkScoreList);
}