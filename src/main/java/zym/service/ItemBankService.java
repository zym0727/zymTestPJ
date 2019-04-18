package zym.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zym.dao.ItemBankMapper;
import zym.dao.QuestionMapper;
import zym.pojo.ItemBank;
import zym.pojo.Question;
import zym.pojo.param.ItemBankPage;

import java.util.*;

/**
 * @author zym
 * *
 */
@Service
public class ItemBankService {

    @Autowired
    private ItemBankMapper itemBankMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public JSONObject getItemBankList(ItemBankPage itemBankPage) {
        itemBankPage.setOffset((itemBankPage.getPageNumber() - 1) * itemBankPage.getPageSize());
        List<ItemBank> itemBankList = itemBankMapper.selectItemBankList(itemBankPage);
        int total = itemBankMapper.countAll(itemBankPage);
        JSONObject result = new JSONObject();
        result.put("total", total);
        result.put("rows", itemBankList);
        return result;
    }

    public List<ItemBank> getItemBankList() {
        return itemBankMapper.selectItemBankList(null);
    }

    public String deleteItemBank(int id) {
        batchDeleteQuestion(id);
        itemBankMapper.deleteByPrimaryKey(id);
        return JSONObject.toJSONString("success");
    }

    public String updateItemBank(ItemBank itemBank) {
        ItemBank origin = itemBankMapper.selectByPrimaryKey(itemBank.getId());
        if(origin.equals(itemBank))
            return JSONObject.toJSONString("repeat");
        itemBankMapper.updateByPrimaryKeySelective(itemBank);
        return JSONObject.toJSONString("success");
    }

    public String insertItemBank(ItemBank itemBank) {
        itemBankMapper.insertSelective(itemBank);
        return JSONObject.toJSONString("success");
    }

    public ItemBank getItemBank(int id){
        return itemBankMapper.selectByPrimaryKey(id);
    }

    public String batchDelete(String ids){
        String[] idArray = ids.split(",");
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(idArray));
        for(String id : list){
            batchDeleteQuestion(Integer.parseInt(id));
        }
        itemBankMapper.batchDelete(list);
        return JSONObject.toJSONString("success");
    }

    public Integer getItemBankId(String ids){
        String[] listId = ids.split(",");
        Question question = questionMapper.selectByPrimaryKey(Integer.parseInt(listId[0]));
        return itemBankMapper.selectByPrimaryKey(question.getItemId()).getId();
    }

    private void batchDeleteQuestion(Integer id){
        List<Question> questionList = questionMapper.selectByItemId(id);
        if (questionList.size() > 0) {
            List<String> stringList = new ArrayList<>();
            for (Question question : questionList)
                stringList.add(String.valueOf(question.getId()));
            questionMapper.batchDelete(stringList);
        }
    }
}