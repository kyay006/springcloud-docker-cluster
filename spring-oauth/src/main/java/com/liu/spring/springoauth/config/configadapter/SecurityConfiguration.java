package com.liu.spring.springoauth.config.configadapter;

import com.liu.spring.springoauth.config.filter.MyLoginAuthenticationFilter;
import com.liu.spring.springoauth.config.provider.LoginSuccessHandler;
import com.liu.spring.springoauth.config.provider.MyAuthenticationProvider;
import com.liu.spring.springoauth.config.provider.MyCustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * 登录、退出、权限设置类
 */
@Configuration
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyCustomUserDetailsService customUserDetailsService;
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    //不定义没有password grant_type
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder()); //这里放到自定义的验证类里做，具体代码在下面几行

        //remember me
        auth.eraseCredentials(false);

        auth.authenticationProvider(myAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAt(getMyLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin().loginPage("/login").permitAll()//.successHandler(loginSuccessHandler())
                .and().authorizeRequests()
                .antMatchers("/images/**", "/checkcode", "/scripts/**", "/styles/**").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().exceptionHandling().accessDeniedPage("/deny")
                .and().rememberMe().tokenRepository(tokenRepository());

        http
                .logout()
                .logoutUrl("/logout")       //默认只接受post请求处理,需要携带csrf token
                .logoutSuccessUrl("/").permitAll()
                .invalidateHttpSession(true)    //清空 session，便于oauth 注销
                .clearAuthentication(true);


    }

    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){
        JdbcTokenRepositoryImpl jtr = new JdbcTokenRepositoryImpl();
        jtr.setDataSource(dataSource);
        return jtr;
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence encPass, String rawPass) {
                //比较密码
                return Objects.equals(encPass.toString(),rawPass);
//                return true;
            }

        };
    }


    /**
     * 自定义密码验证
     * @return
     */
    @Bean
    public MyAuthenticationProvider myAuthenticationProvider(){
        MyAuthenticationProvider provider = new MyAuthenticationProvider();
        // 设置userDetailsService
        provider.setUserDetailsService(customUserDetailsService).setPasswordEncoder(bCryptPasswordEncoder());
        // 禁止隐藏用户未找到异常
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }


    /**
     * 自定义登陆过滤器
     * @return
     */
    @Bean
    public MyLoginAuthenticationFilter getMyLoginAuthenticationFilter() {
        MyLoginAuthenticationFilter filter = new MyLoginAuthenticationFilter();
        try {
            filter.setAuthenticationManager(authenticationManagerBean());
            filter.setAuthenticationSuccessHandler(loginSuccessHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        filter.setAuthenticationSuccessHandler(new MyLoginAuthSuccessHandler());
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"));
        return filter;
    }
}
