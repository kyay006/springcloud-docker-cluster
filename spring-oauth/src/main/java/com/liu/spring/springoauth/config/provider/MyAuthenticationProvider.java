package com.liu.spring.springoauth.config.provider;

import com.liu.spring.springoauth.config.filter.MyLoginAuthenticationFilter;
import com.liu.spring.springoauth.config.token.MyAuthenticationToken;
import com.liu.spring.springoauth.config.token.SecurityUser;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;


/**
 * Created by fp295 on 2018/6/16.
 * 自定义密码验证
 * 实现自定义的 MyAbstractUserDetailsAuthenticationProvider 抽象类
 * 根据登陆的类型 执行不同的校验
 */
public class MyAuthenticationProvider extends MyAbstractUserDetailsAuthenticationProvider {

    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;

    public MyAuthenticationProvider() {
    }

    /**
     * 自定义验证
     * @param userDetails
     * @param authentication
     * @throws AuthenticationException
     */
    protected void additionalAuthenticationChecks(UserDetails userDetails, MyAuthenticationToken authentication) throws AuthenticationException {

        if(authentication.getCredentials() == null) {
            this.logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("MyAbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            String presentedPassword = authentication.getCredentials().toString();

            // 验证开始
            if(MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_PHONE.equals(authentication.getType())){
                // 验证码验证，调用公共服务查询 key 为authentication.getPrincipal()的value， 并判断其与验证码是否匹配
                if(!"1000".equals(presentedPassword)){
                    this.logger.debug("Authentication failed: verifyCode does not match stored value");
                    throw new BadCredentialsException(this.messages.getMessage("MyAbstractUserDetailsAuthenticationProvider.badCredentials", "Bad verifyCode"));
                }
            }else if(MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_QR.equals(authentication.getType())){
                // 二维码只需要根据 SPRING_SECURITY_RESTFUL_QR_CODE_KEY 查询到用户即可，所以此处无需验证
            }
            else {
                // 用户名密码验证
                SecurityUser user = (SecurityUser) userDetails;
                presentedPassword = DigestUtils.md5DigestAsHex(new StringBuilder(50).append(user.getLoginName()).append(presentedPassword).append(user.getSalt()).toString().getBytes());
                if(!this.passwordEncoder.matches(userDetails.getPassword(), presentedPassword)) {
                    this.logger.debug("Authentication failed: password does not match stored value");
                    throw new BadCredentialsException(this.messages.getMessage("MyAbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
                }
            }
        }
    }

    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    protected final UserDetails retrieveUser(String username, MyAuthenticationToken authentication) throws AuthenticationException {
        UserDetails loadedUser;
        try {
            // 调用loadUserByUsername时加入type前缀
//            loadedUser = this.getUserDetailsService().loadUserByUsername(authentication.getType() + "&:@" + username);    //这里是为了辨别是用什么方式登录，我这里只有密码，所以不需要加标识
            loadedUser = this.getUserDetailsService().loadUserByUsername(username);
        } catch (UsernameNotFoundException var6) {
            //用户名没找到
            throw var6;
        } catch (Exception var7) {
            throw new InternalAuthenticationServiceException(var7.getMessage(), var7);
        }

        if(loadedUser == null) {
            throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        } else {
            return loadedUser;
        }
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        this.passwordEncoder = passwordEncoder;

    }

    protected PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }

    public MyAuthenticationProvider setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        return this;
    }

    protected UserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }
}
