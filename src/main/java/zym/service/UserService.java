package zym.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zym.dao.UsersMapper;
import zym.pojo.Users;
import zym.pojo.param.UserInfo;
import zym.pojo.param.UserPage;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zym
 * *
 */
@Service
public class UserService {

    @Autowired
    private UsersMapper usersMapper;

    public UserInfo getUserInfo(HttpSession httpSession) {
        Object user = httpSession.getAttribute("user");
        if (user == null)
            return null;
        Users users = (Users) user;
        return usersMapper.getUserInfoByPrimaryKey(users.getId());
    }

    public String updateUsers(Users users) {
        if (checkRepeat(users))
            return JSONObject.toJSONString("repeat");
        usersMapper.updateByPrimaryKeySelective(users);
        return JSONObject.toJSONString("success");
    }

    public List<Users> getTeacherList() {
        Users users = new Users();
        users.setRoleId(2);
        users.setEnabled(1);
        return usersMapper.selectByUsers(users);
    }

    public JSONObject getUserList(UserPage userPage) {
        userPage.setOffset((userPage.getPageNumber() - 1) * userPage.getPageSize());
        JSONObject result = new JSONObject();
        result.put("total", usersMapper.countAll(userPage));
        List<UserInfo> userInfoList = getSex(usersMapper.getUserList(userPage));
        result.put("rows", userInfoList != null ? userInfoList : 0);
        return result;
    }

    public Users getUsers(int id) {
        return usersMapper.getUserInfoByPrimaryKey(id);
    }

    public String deleteUsers(int id) {
        usersMapper.deleteByPrimaryKey(id);
        return JSONObject.toJSONString("success");
    }

    public String saveUsers(Users users) {
        if (checkRepeat(users))
            return JSONObject.toJSONString("repeat");
        usersMapper.insertSelective(users);
        return JSONObject.toJSONString("success");
    }

    public String deleteBatch(String ids) {
        usersMapper.batchDelete(ids.split(","));
        return JSONObject.toJSONString("success");
    }

    private Boolean checkRepeat(Users users) {
        List<Users> usersList = usersMapper.selectRepeat(users);
        if (users.getId() != null) { //修改
            Users origin = usersMapper.selectByPrimaryKey(users.getId());
            //班级编号不能重复
            return usersList != null && usersList.size() > 0 &&
                    !usersList.get(0).getId().equals(origin.getId());
        } else  //添加
            return usersList != null && usersList.size() > 0;
    }

    private List<UserInfo> getSex(List<UserInfo> userInfoList) {
        if (userInfoList == null)
            return null;
        for (UserInfo u : userInfoList) {
            if (u.getSex() != null)
                if (u.getSex() == 1)
                    u.setSexDetail("男");
                else if (u.getSex() == 0)
                    u.setSexDetail("女");
        }
        return userInfoList;
    }
}
