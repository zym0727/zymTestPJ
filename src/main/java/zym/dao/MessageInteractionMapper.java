package zym.dao;

import zym.pojo.MessageInteraction;

public interface MessageInteractionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessageInteraction record);

    int insertSelective(MessageInteraction record);

    MessageInteraction selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessageInteraction record);

    int updateByPrimaryKey(MessageInteraction record);
}