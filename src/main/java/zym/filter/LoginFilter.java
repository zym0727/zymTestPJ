package zym.filter;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import zym.service.LoginService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

/**
 * @author zym
 * *
 */
public class LoginFilter implements Filter {

    private LoginService loginService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        ApplicationContext Context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        loginService = (LoginService) Context.getBean("loginService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        //如果是记住我功能进来的，存用户信息到httpSession
        if (isRememberMeAuthenticated()) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            Principal principal = request.getUserPrincipal();
            if (principal != null && principal.getName() != null) {
                HttpSession httpSession = request.getSession();
                Object user = httpSession.getAttribute("user");
                if (user == null)
                    loginService.LoginMake(principal.getName(), httpSession);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);    //继续执行
    }

    @Override
    public void destroy() {

    }

    private boolean isRememberMeAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && RememberMeAuthenticationToken.class.isAssignableFrom(
                authentication.getClass());
    }
}
