package zym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import zym.exception.MessageException;
import zym.pojo.Users;
import zym.pojo.param.UserInfo;
import zym.service.LoginService;
import zym.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * @author zym
 * *
 */
@Controller
public class LoginController {

    //private static Logger log =  LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = {"/", "/index"}, method = RequestMethod.GET)
    public String index() {
        //记住我功能登录
        if (isRememberMeAuthenticated())
            return "/main/main";
        else
            return "index";
    }

    @RequestMapping(path = "/firstSee", method = RequestMethod.GET)
    public String firstSee() {
        return "/main/main";
    }

    @RequestMapping(path = "/user/information", method = RequestMethod.GET)
    public String getInfoPage(){
        return "/user/information";
    }

    @RequestMapping(path = "/user/login/test", method = RequestMethod.POST)
    @ResponseBody
    public String loginTest(String account, String password, HttpSession session) {
        if (account == null || password == null)
            throw new MessageException("参数为空");
        return loginService.LoginTest(account, password, session);
    }

    @RequestMapping(path = "/user/info", method = RequestMethod.GET)
    @ResponseBody
    public UserInfo getUserInfo(HttpSession session) {
        return userService.getUserInfo(session);
    }

    @RequestMapping(path = "/user/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateUserInfo(Users users) {
        return userService.updateUsers(users);
    }

    //记住我功能登录验证
    private boolean isRememberMeAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && RememberMeAuthenticationToken.class.isAssignableFrom(
                authentication.getClass());
    }
}
