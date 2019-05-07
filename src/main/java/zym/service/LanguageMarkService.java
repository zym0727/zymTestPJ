package zym.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zym.dao.LanguageMarkMapper;
import zym.pojo.LanguageMark;
import zym.pojo.param.Page;

import java.util.List;

/**
 * @author zym
 * *
 */
@Service
public class LanguageMarkService {

    @Autowired
    private LanguageMarkMapper languageMarkMapper;

    public JSONObject getLanguageMarkList(Page page) {
        page.setOffset((page.getPageNumber() - 1) * page.getPageSize());
        JSONObject result = new JSONObject();
        result.put("total", languageMarkMapper.countAll());
        result.put("rows", languageMarkMapper.selectLanguageMarkList(page));
        return result;
    }

    public LanguageMark getLanguageMark(int id) {
        return languageMarkMapper.selectByPrimaryKey(id);
    }

    public String deleteLanguageMark(int id) {
        languageMarkMapper.deleteByPrimaryKey(id);
        return JSONObject.toJSONString("success");
    }

    public String saveLanguageMark(LanguageMark languageMark) {
        if (checkRepeat(languageMark))
            return JSONObject.toJSONString("repeat");
        languageMarkMapper.insert(languageMark);
        return JSONObject.toJSONString("success");
    }

    public String updateLanguageMark(LanguageMark languageMark) {
        if (checkRepeat(languageMark))
            return JSONObject.toJSONString("repeat");
        languageMarkMapper.updateByPrimaryKeySelective(languageMark);
        return JSONObject.toJSONString("success");
    }

    public String deleteBatch(String ids) {
        languageMarkMapper.batchDelete(ids.split(","));
        return JSONObject.toJSONString("success");
    }

    private Boolean checkRepeat(LanguageMark languageMark) {
        List<LanguageMark> markList = languageMarkMapper.selectRepeat(languageMark);
        if (languageMark.getId() != null) { //修改
            LanguageMark origin = languageMarkMapper.selectByPrimaryKey(languageMark.getId());
            //标记语言名称不能重复
            return markList != null && markList.size() > 0 &&
                    !markList.get(0).getId().equals(origin.getId());
        } else  //添加
            return markList != null && markList.size() > 0;
    }
}
