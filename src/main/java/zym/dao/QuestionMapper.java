package zym.dao;

import zym.pojo.Question;
import zym.pojo.param.QuestionDetail;
import zym.pojo.param.QuestionPage;

import java.util.List;

public interface QuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);

    List<QuestionDetail> selectQuestionList(QuestionPage questionPage);

    List<Question> selectByItemId(Integer itemId);

    int countAll(QuestionPage questionPage);

    void batchDelete(List<String> list);
}