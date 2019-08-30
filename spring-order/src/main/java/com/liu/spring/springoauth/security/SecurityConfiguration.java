package com.liu.spring.springoauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableOAuth2Sso
@EnableConfigurationProperties(SecuritySettings.class)
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private SecuritySettings settings;

    @Bean
    @Order(0)
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**").authorizeRequests()
                .antMatchers(settings.getPermitAll().split(",")).permitAll()
                .anyRequest().authenticated()
                .and().csrf().requireCsrfProtectionMatcher(csrfSecurityRequestMatcher())
                .csrfTokenRepository(csrfTokenRepository()).and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
                .logout().logoutUrl("/logout").permitAll()
                .logoutSuccessUrl(settings.getLogoutSuccssurl())
                .and()
                .exceptionHandling().accessDeniedPage(settings.getDeniedPage());
    }


    @Bean
    public CustomFilterSecurityInterceptor customFilter() throws Exception{
        CustomFilterSecurityInterceptor customFilter = new CustomFilterSecurityInterceptor();
        customFilter.setSecurityMetadataSource(securityMetadataSource());
        customFilter.setAccessDecisionManager(accessDecisionManager());
        customFilter.setAuthenticationManager(authenticationManagerBean());
//        customFilter.setAuthenticationManager(authenticationManager);
        return customFilter;
    }

    @Bean
    public CustomAccessDecisionManager accessDecisionManager() {
        return new CustomAccessDecisionManager();
    }

    @Bean
    public CustomSecurityMetadataSource securityMetadataSource() {
        return new CustomSecurityMetadataSource(settings.getUrlRoles());
    }


    private CsrfSecurityRequestMatcher csrfSecurityRequestMatcher(){
        CsrfSecurityRequestMatcher csrfSecurityRequestMatcher = new CsrfSecurityRequestMatcher();
        List<String> list = new ArrayList<String>();
        list.add("/zzzzzz");//这里配置post方法不配csrf拦截
        list.add("/rest/");
        list.add("/smallProgram/authorize/trans2OpenId");
        csrfSecurityRequestMatcher.setExecludeUrls(list);
        return csrfSecurityRequestMatcher;
    }

    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
                if (csrf != null) {
                    Cookie cookie = new Cookie("XSRF-TOKEN",
                            csrf.getToken());
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/resource/**", "/static/**", "/MP_verify_Lo5ZxMzKsqiAdzP2.txt", "/favicon.ico");
//        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html**",  "/webjars/**");
        // 忽略URL
//        web.ignoring().antMatchers("/**/*.js", "/lang/*.json", "/**/*.css", "/**/*.js", "/**/*.map", "/**/*.html","/**/*.png");
    }

}
