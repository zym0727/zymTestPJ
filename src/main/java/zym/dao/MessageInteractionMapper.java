package zym.dao;

import zym.pojo.MessageInteraction;
import zym.pojo.param.MessagePage;
import zym.pojo.param.MessageReply;

import java.util.List;

public interface MessageInteractionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessageInteraction record);

    int insertSelective(MessageInteraction record);

    MessageInteraction selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessageInteraction record);

    int updateByPrimaryKey(MessageInteraction record);

    List<MessageReply> getStudentMessageReplyList(MessagePage messagePage);

    int countStudentMessageReply(MessagePage messagePage);
}