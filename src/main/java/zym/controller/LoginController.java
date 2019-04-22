package zym.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import zym.exception.MessageException;
import zym.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

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
    public String index(HttpServletRequest request, HttpSession session) {
        Principal principal = request.getUserPrincipal();
        if (principal != null && principal.getName() != null) {
            if (loginService.LoginMake(principal.getName(), session).equals("success"))
                return "/main/main";
        }
        return "index";
    }


    @RequestMapping(path = "/firstSee", method = RequestMethod.GET)
    public String firstSee(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        log.info("账号名字:"+principal.getName());
        log.info("当前用户信息:"+principal.toString());
        return "/main/main";
    }

    @RequestMapping(path = "/user/login/test", method = RequestMethod.POST)
    @ResponseBody
    public String loginTest(String account, String password, HttpSession session) {
        if (account == null || password == null)
            throw new MessageException("参数为空");
        return loginService.LoginTest(account, password, session);
    }
}
