package zym.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zym.dao.UsersMapper;
import zym.pojo.Users;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zym
 * *
 */
@Service
public class LoginService {

    @Autowired
    private UsersMapper userMapper;

    public String LoginTest(String account, String password, HttpSession session){
        Users user = new Users();
        user.setAccount(account);
        user.setPassword(password);
        user.setEnabled(1);
        List<Users> list = userMapper.selectByUsers(user);
        if (list != null && list.size()==1) {
            session.setAttribute("user", list.get(0));
            return JSONObject.toJSONString("find");
        } else {
            return JSONObject.toJSONString("empty");
        }
    }

    public String LoginMake(String account, HttpSession session){
        Users user = new Users();
        user.setAccount(account);
        List<Users> list = userMapper.selectByUsers(user);
        if (list != null && list.size()==1) {
            session.setAttribute("user", list.get(0));
            return "success";
        }else
           return "fail";
    }
}
