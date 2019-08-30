package com.liu.spring.springoauth.filter;

import com.liu.spring.springoauth.security.SecuritySettings;
import com.liu.util.redis.RedisTemplateService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableConfigurationProperties(SecuritySettings.class)
@WebFilter(filterName = "sessionFilter",urlPatterns = {"/*"})
public class SessionFilter implements Filter {

    /**
     * oauth2的sessionid
     */
    private static final String SESSIONID = "SESSIONID";

    //oauth2的sessionid存到数据库为1的里面
    @Autowired
    private RedisTemplateService1 redisTemplateService1;

    @Autowired
    private SecuritySettings settings;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String sessionId = null;
        String sessionIdOld = null;
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;


        boolean needValidLogout = true;
        String url = httpServletRequest.getRequestURI();
        String accessDeniedPage = settings.getDeniedPage();
        if(accessDeniedPage != null && !StringUtils.isEmpty(accessDeniedPage) && url.equals(accessDeniedPage)){
            needValidLogout = false;
        }else if(settings.getPermitAll() != null && !StringUtils.isEmpty(settings.getPermitAll()))
        {
            String [] permitArr = settings.getPermitAll().split(",");
            for (int i = 0, iLen = permitArr.length; i < iLen; i++)
            {
                if(url.equals(permitArr[i]) || url.equals(new StringBuilder(16).append(permitArr[i]).append("/").toString())
                   || (permitArr[i].startsWith("/"+url.split("/")[1]) && permitArr[i].endsWith("**")))
                {
                    needValidLogout = false;
                    break;
                }
            }
        }

        //判断请求是否完全不需要权限，如果需要权限才判断是否已退出，不需要权限的url就不用管登录退出了
        if(needValidLogout)
        {
            SecurityContextImpl securityContextImpl = (SecurityContextImpl) httpServletRequest.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
            // 登录名
            if(!StringUtils.isEmpty(securityContextImpl) && redisTemplateService1.exists(securityContextImpl.getAuthentication().getName())){
                sessionIdOld = (String)redisTemplateService1.get(securityContextImpl.getAuthentication().getName());
            }

            //判断认证服务器sessionid是空的或者是false，就是退出了
            if(StringUtils.isEmpty(sessionIdOld) || Boolean.FALSE.toString().equals(sessionIdOld))
            {
                //登录已失效
                System.out.println("登录已失效");
//            httpServletResponse.sendRedirect("http://localhost:8001/spring-oauth/login");
                httpServletResponse.sendRedirect("http://www.baidu.com?a=a");
                httpServletRequest.getSession().invalidate();
                SecurityContextHolder.clearContext(); //清空安全上下文
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}