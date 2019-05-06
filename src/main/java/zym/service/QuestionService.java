package zym.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import zym.dao.LanguageMarkMapper;
import zym.dao.QuestionMapper;
import zym.dao.TestDataMapper;
import zym.pojo.LanguageMark;
import zym.pojo.Question;
import zym.pojo.TestData;
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

    @Autowired
    private TestDataMapper testDataMapper;

    @Autowired
    private LanguageMarkMapper languageMarkMapper;

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
        return deleteTestData(id);
    }

    public String updateQuestion(Question question) {
        Question origin = questionMapper.selectByPrimaryKey(question.getId());
        if (origin.equals(question))
            return JSONObject.toJSONString("repeat");
        questionMapper.updateByPrimaryKeySelective(question);
        return JSONObject.toJSONString("success");
    }

    public String insertQuestion(Question question) {
        questionMapper.insertSelective(question);
        return JSONObject.toJSONString("success");
    }

    public Question getQuestion(int id) {
        return questionMapper.selectByPrimaryKey(id);
    }

    public String batchDelete(String ids) {
        String[] idArray = ids.split(",");
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(idArray));
        questionMapper.batchDelete(list);
        for(String id : list)
            deleteTestData(Integer.valueOf(id));
        return JSONObject.toJSONString("success");
    }

    public List<QuestionDetail> getQuestionList(int id) {
        QuestionPage questionPage = new QuestionPage();
        questionPage.setItemId(id);
        return questionMapper.selectQuestionList(questionPage);
    }

    public List<QuestionDetail> getQuestionList() {
        return questionMapper.selectQuestionList(
                null);
    }

    public String saveTestData(TestData testData) {
        testDataMapper.insertSelective(testData);
        return "success";
    }

    public Model setModel(String s, Model model, int questionId, int languageId) {
        if (s.equals("success")) {
            List<TestData> testDataList = testDataMapper.getListByQuestionId(questionId);
            if (testDataList != null && testDataList.size() > 0) {
                model.addAttribute("inputOutput", getTestData(testDataList));
                model.addAttribute("questionId", questionId);
            }
            Question question = new Question();
            question.setId(questionId);
            question.setLanguageId(languageId);
            questionMapper.updateByPrimaryKeySelective(question);
        } else {
            model.addAttribute("error", "error");
        }
        return model;
    }

    private String getTestData(List<TestData> testDataList) {
        int i = 1;
        StringBuilder stringBuilder = new StringBuilder();
        for (TestData testData : testDataList) {
            stringBuilder.append("第").append(i++).append("组数据：")
                    .append("\n").append("输入：");
            if (testData.getInput() == null)
                stringBuilder.append("没有输入");
            else
                stringBuilder.append("\n").append(testData.getInput());
            stringBuilder.append("\n").append("输出：");
            if (testData.getOutput() == null)
                stringBuilder.append("没有输出");
            else
                stringBuilder.append("\n").append(testData.getOutput());
            stringBuilder.append("\n\n");
        }

        if (stringBuilder.length() > 5)
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return stringBuilder.toString();
    }

    public JSONObject getTestDataByQuestionId(int questionId) {
        JSONObject result = new JSONObject();
        List<TestData> testDataList = testDataMapper.getListByQuestionId(questionId);
        if (testDataList != null && testDataList.size() > 0)
            result.put("testData", getTestData(testDataList));
        else
            result.put("testData", "null");
        return result;
    }

    public String deleteTestDataList(int questionId) {
        List<TestData> testDataList = testDataMapper.getListByQuestionId(questionId);
        if (testDataList != null && testDataList.size() > 0)
            testDataMapper.batchDelete(testDataList);
        else
            return JSONObject.toJSONString("fail");
        Question question = questionMapper.selectByPrimaryKey(questionId);
        if (question != null) {
            question.setLanguageId(null);
            questionMapper.updateByPrimaryKey(question);
        }
        return JSONObject.toJSONString("success");
    }

    public String deleteTestData(int questionId){
        List<TestData> testDataList = testDataMapper.getListByQuestionId(questionId);
        if (testDataList != null && testDataList.size() > 0)
            testDataMapper.batchDelete(testDataList);
        return JSONObject.toJSONString("success");
    }

    public List<LanguageMark> getLanguageMarkList(LanguageMark languageMark) {
        return languageMarkMapper.getLanguageMarkList(languageMark);
    }
}
