package zym.dao;

import zym.pojo.Role;
import zym.pojo.param.Page;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectRepeat(Role role);

    List<Role> selectRoleList(Page page);

    int countAll();

    void batchDelete(String[] list);

    List<Role> getRoleList(Role role);
}