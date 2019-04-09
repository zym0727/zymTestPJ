package zym.dao;

import zym.pojo.ItemBank;
import zym.pojo.param.ItemBankPage;

import java.util.List;

public interface ItemBankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemBank record);

    int insertSelective(ItemBank record);

    ItemBank selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemBank record);

    int updateByPrimaryKey(ItemBank record);

    List<ItemBank> selectItemBankList(ItemBankPage itemBankPage);

    int countAll(ItemBankPage itemBankPage);

    void batchDelete(List<String> list);
}