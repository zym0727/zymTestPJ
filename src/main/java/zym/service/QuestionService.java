package zym.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zym.dao.QuestionMapper;
import zym.pojo.Question;
import zym.pojo.param.QuestionDetail;
import zym.pojo.param.QuestionPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zym
 * *
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    public JSONObject getQuestionList(QuestionPage questionPage) {
        questionPage.setOffset((questionPage.getPageNumber() - 1) * questionPage.getPageSize());
        List<QuestionDetail> questionList = questionMapper.selectQuestionList(questionPage);
        int total = questionMapper.countAll(questionPage);
        JSONObject result = new JSONObject();
        result.put("total", total);
        result.put("rows", questionList);
        return result;
    }

    public String deleteQuestion(int id) {
        questionMapper.deleteByPrimaryKey(id);
        return JSONObject.toJSONString("success");
    }

    public String updateQuestion(Question question) {
        Question origin = questionMapper.selectByPrimaryKey(question.getId());
        if(origin.equals(question))
            return JSONObject.toJSONString("repeat");
        questionMapper.updateByPrimaryKeySelective(question);
        return JSONObject.toJSONString("success");
    }

    public String insertQuestion(Question question) {
        questionMapper.insertSelective(question);
        return JSONObject.toJSONString("success");
    }

    public Question getQuestion(int id){
        return questionMapper.selectByPrimaryKey(id);
    }

    public String batchDelete(String ids){
        String[] idArray = ids.split(",");
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(idArray));
        questionMapper.batchDelete(list);
        return JSONObject.toJSONString("success");
    }

    public List<QuestionDetail> getQuestionList(int id){
        QuestionPage questionPage = new QuestionPage();
        questionPage.setItemId(id);
        return questionMapper.selectQuestionList(questionPage);
    }
}
