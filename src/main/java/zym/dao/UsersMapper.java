package zym.dao;

import zym.pojo.Users;
import zym.pojo.param.UserInfo;

import java.util.List;

public interface UsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    List<Users> selectByUsers(Users users);

    UserInfo getUserInfoByPrimaryKey(Integer id);
}