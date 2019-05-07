package zym.dao;

import zym.pojo.LanguageMark;
import zym.pojo.param.Page;

import java.util.List;

public interface LanguageMarkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LanguageMark record);

    int insertSelective(LanguageMark record);

    LanguageMark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LanguageMark record);

    int updateByPrimaryKey(LanguageMark record);

    List<LanguageMark> getLanguageMarkList(LanguageMark record);

    List<LanguageMark> selectRepeat(LanguageMark record);

    List<LanguageMark> selectLanguageMarkList(Page page);

    int countAll();

    void batchDelete(String[] list);
}