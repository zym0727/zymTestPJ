package zym.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zym.dao.RoleMapper;
import zym.pojo.Role;
import zym.pojo.param.Page;

import java.util.List;

/**
 * @author zym
 * *
 */
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public JSONObject getRoleList(Page page) {
        page.setOffset((page.getPageNumber() - 1) * page.getPageSize());
        JSONObject result = new JSONObject();
        result.put("total", roleMapper.countAll());
        result.put("rows", roleMapper.selectRoleList(page));
        return result;
    }

    public Role getRole(int id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public String deleteRole(int id) {
        roleMapper.deleteByPrimaryKey(id);
        return JSONObject.toJSONString("success");
    }

    public String saveRole(Role role) {
        if (checkRepeat(role))
            return JSONObject.toJSONString("repeat");
        roleMapper.insert(role);
        return JSONObject.toJSONString("success");
    }

    public String updateRole(Role role) {
        if (checkRepeat(role))
            return JSONObject.toJSONString("repeat");
        roleMapper.updateByPrimaryKeySelective(role);
        return JSONObject.toJSONString("success");
    }

    public String deleteBatch(String ids) {
        roleMapper.batchDelete(ids.split(","));
        return JSONObject.toJSONString("success");
    }

    public List<Role> getRoleList(){
        return roleMapper.selectRoleList(null);
    }

    private Boolean checkRepeat(Role role) {
        List<Role> roleList = roleMapper.selectRepeat(role);
        if (role.getId() != null) { //修改
            Role origin = roleMapper.selectByPrimaryKey(role.getId());
            //角色名称不能重复
            return roleList != null && roleList.size() > 0 &&
                    !roleList.get(0).getId().equals(origin.getId());
        } else  //添加
            return roleList != null && roleList.size() > 0;
    }
}
