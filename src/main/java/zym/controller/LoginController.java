package zym.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import zym.exception.MessageException;
import zym.service.LoginService;

import javax.servlet.http.HttpSession;

/**
 * @author zym
 * *
 */
@Controller
public class LoginController {

    private static Logger log =  LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

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

    @RequestMapping(path = "/user/login/test", method = RequestMethod.POST)
    @ResponseBody
    public String loginTest(String account, String password, HttpSession session) {
        if (account == null || password == null)
            throw new MessageException("参数为空");
        return loginService.LoginTest(account, password, session);
    }

    private boolean isRememberMeAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && RememberMeAuthenticationToken.class.isAssignableFrom(
                authentication.getClass());
    }
}
