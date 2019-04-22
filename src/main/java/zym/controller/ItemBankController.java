package zym.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import zym.exception.MessageException;
import zym.pojo.ItemBank;
import zym.pojo.Question;
import zym.pojo.TestData;
import zym.pojo.param.ItemBankPage;
import zym.pojo.param.QuestionPage;
import zym.service.ItemBankService;
import zym.service.QuestionService;

/**
 * @author zym
 * *
 */
@Controller
@RequestMapping(path = {"/itemBank"})
public class ItemBankController {

    @Autowired
    private ItemBankService itemBankService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping(path = {"/getPage"}, method = RequestMethod.GET)
    public String getItemBank() {
        return "itemBank/itemBank";
    }

    @RequestMapping(path = {"/question/getPage"}, method = RequestMethod.GET)
    public String getQuestion(Model model) {
        model.addAttribute("questionList", questionService.getQuestionList());
        model.addAttribute("itemBankList", itemBankService.getItemBankList());
        return "itemBank/question";
    }

    @RequestMapping(path = {"/programHomework/getPage"}, method = RequestMethod.GET)
    public String getProgramHomeworkSetting(Model model) {
        model.addAttribute("itemBankList", itemBankService.getItemBankList());
        return "itemBank/programSetting";
    }

    @RequestMapping(path = {"/testData/save"}, method = RequestMethod.POST)
    public String saveTestData(TestData testData, Model model) {
        if (StringUtils.isEmpty(testData) || StringUtils.isEmpty(testData.getQuestionId()) ||
                (StringUtils.isEmpty(testData.getInput()) && StringUtils.isEmpty(testData.getOutput())))
            throw new MessageException("参数为空");
        model = questionService.setModel(
                questionService.saveTestData(testData), model, testData.getQuestionId());
        model.addAttribute("itemBankList", itemBankService.getItemBankList());
        ItemBank itemBank = itemBankService.getItemBankByQuestionId(testData.getQuestionId());
        model.addAttribute("itemBankId", itemBank == null ? null : itemBank.getId());
        return "itemBank/programSetting";
    }

    @RequestMapping(path = {"/delete/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public String deleteItemBankById(@PathVariable Integer id) {
        return itemBankService.deleteItemBank(id);
    }

    @RequestMapping(path = {"/get/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public ItemBank getItemBankById(@PathVariable Integer id) {
        return itemBankService.getItemBank(id);
    }

    @RequestMapping(path = {"/update"}, method = RequestMethod.POST)
    @ResponseBody
    public String updateItemBank(ItemBank itemBank) {
        if (StringUtils.isEmpty(itemBank) || StringUtils.isEmpty(itemBank.getId()) ||
                StringUtils.isEmpty(itemBank.getItemName()))
            throw new MessageException("参数为空");
        return itemBankService.updateItemBank(itemBank);
    }

    @RequestMapping(path = {"/add"}, method = RequestMethod.POST)
    @ResponseBody
    public String addItemBank(ItemBank itemBank) {
        if (StringUtils.isEmpty(itemBank) || StringUtils.isEmpty(itemBank.getItemName()))
            throw new MessageException("参数为空");
        return itemBankService.insertItemBank(itemBank);
    }

    @RequestMapping(path = {"/batchDelete"}, method = RequestMethod.POST)
    @ResponseBody
    public String batchDeleteItemBank(String ids) {
        if (StringUtils.isEmpty(ids))
            throw new MessageException("参数为空");
        return itemBankService.batchDelete(ids);
    }

    @RequestMapping(path = {"/itemTable/list"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getItemBankList(ItemBankPage itemBankPage) {
        if (StringUtils.isEmpty(itemBankPage) || StringUtils.isEmpty(itemBankPage.getPageSize()) ||
                StringUtils.isEmpty(itemBankPage.getPageNumber()))
            throw new MessageException("参数为空");
        return itemBankService.getItemBankList(itemBankPage);
    }

    @RequestMapping(path = {"/question/delete/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public String deleteQuestionById(@PathVariable Integer id) {
        return questionService.deleteQuestion(id);
    }

    @RequestMapping(path = {"/question/get/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public Question getQuestionById(@PathVariable Integer id) {
        return questionService.getQuestion(id);
    }

    @RequestMapping(path = {"/question/update"}, method = RequestMethod.POST)
    @ResponseBody
    public String updateQuestion(Question question) {
        if (StringUtils.isEmpty(question) || StringUtils.isEmpty(question.getId()) ||
                StringUtils.isEmpty(question.getQuestionNumber())
                || StringUtils.isEmpty(question.getDescription())
                || StringUtils.isEmpty(question.getItemId()))
            throw new MessageException("参数为空");
        return questionService.updateQuestion(question);
    }

    @RequestMapping(path = {"/question/add"}, method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(Question question) {
        if (StringUtils.isEmpty(question) || StringUtils.isEmpty(question.getQuestionNumber())
                || StringUtils.isEmpty(question.getDescription())
                || StringUtils.isEmpty(question.getItemId()))
            throw new MessageException("参数为空");
        return questionService.insertQuestion(question);
    }

    @RequestMapping(path = {"/question/batchDelete"}, method = RequestMethod.POST)
    @ResponseBody
    public String batchDeleteQuestion(String ids) {
        if (StringUtils.isEmpty(ids))
            throw new MessageException("参数为空");
        return questionService.batchDelete(ids);
    }

    @RequestMapping(path = {"/questionTable/list"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getItemQuestion(QuestionPage questionPage) {
        if (StringUtils.isEmpty(questionPage) || StringUtils.isEmpty(questionPage.getPageSize()) ||
                StringUtils.isEmpty(questionPage.getPageNumber()))
            throw new MessageException("参数为空");
        return questionService.getQuestionList(questionPage);
    }

    @RequestMapping(path = {"/id/get"}, method = RequestMethod.GET)
    @ResponseBody
    public int getItemBankId(String ids) {
        if (StringUtils.isEmpty(ids))
            throw new MessageException("参数为空");
        return itemBankService.getItemBankId(ids);
    }

    @RequestMapping(path = {"/testData/batchDelete/{questionId}"}, method = RequestMethod.GET)
    @ResponseBody
    public String batchDeleteTestData(@PathVariable Integer questionId) {
        return questionService.deleteTestDataList(questionId);
    }

    @RequestMapping(path = {"/testData/getAll/{questionId}"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getAllTestData(@PathVariable Integer questionId) {
        return questionService.getTestDataByQuestionId(questionId);
    }
}
