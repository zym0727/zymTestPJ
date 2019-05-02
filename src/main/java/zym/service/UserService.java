package zym.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zym.dao.UsersMapper;
import zym.pojo.Users;
import zym.pojo.param.UserInfo;

import javax.servlet.http.HttpSession;

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
        usersMapper.updateByPrimaryKeySelective(users);
        return JSONObject.toJSONString("success");
    }
}
