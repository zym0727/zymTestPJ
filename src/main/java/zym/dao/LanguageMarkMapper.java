package zym.dao;

import zym.pojo.LanguageMark;

import java.util.List;

public interface LanguageMarkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LanguageMark record);

    int insertSelective(LanguageMark record);

    LanguageMark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LanguageMark record);

    int updateByPrimaryKey(LanguageMark record);

    List<LanguageMark> getListByQuestionId(Integer QuestionId);
}