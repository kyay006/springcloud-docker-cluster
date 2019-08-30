package com.liu.spring.springoauth.config.provider;

//import com.test.mysql.entity.User;
//import com.test.mysql.repository.UserRepository;

import com.liu.spring.springoauth.config.token.MyGrantedAuthority;
import com.liu.spring.springoauth.config.token.SecurityUser;
import com.liu.spring.springoauth.config.token.User;
import com.liu.spring.springoauth.domain.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 账号验证类
 */
@Component
public class MyCustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
//    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        // 这个地方可以通过username从数据库获取正确的用户信息，包括密码等。
        User user = userMapper.findUserByUserName(loginName);
        if (user == null) {
            throw new UsernameNotFoundException("loginName： " + loginName + " not found");
        }


          //多方式登录时，如果是手机登录, 还要根据是用密码还是验证码来登录，查出的密码凭证就不一样

//        // 手机验证码调用FeignClient根据电话号码查询用户
//        if(MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_PHONE.equals(parameter[0])){
//            ResponseData<BaseUser> baseUserResponseData = baseUserService.getUserByPhone(parameter[1]);
//            if(baseUserResponseData.getData() == null || !ResponseCode.SUCCESS.getCode().equals(baseUserResponseData.getCode())){
//                logger.error("找不到该用户，手机号码：" + parameter[1]);
//                throw new UsernameNotFoundException("找不到该用户，手机号码：" + parameter[1]);
//            }
//            baseUser = baseUserResponseData.getData();
//        } else if(MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_QR.equals(parameter[0])){
//            // 扫码登陆根据token从redis查询用户
//            baseUser = null;
//        } else {
//            // 账号密码登陆调用FeignClient根据用户名查询用户
//            ResponseData<BaseUser> baseUserResponseData = baseUserService.getUserByUserName(parameter[1]);
//            if(baseUserResponseData.getData() == null || !ResponseCode.SUCCESS.getCode().equals(baseUserResponseData.getCode())){
//                logger.error("找不到该用户，用户名：" + parameter[1]);
//                throw new UsernameNotFoundException("找不到该用户，用户名：" + parameter[1]);
//            }
//            baseUser = baseUserResponseData.getData();
//        }


        // 这个地方可以通过userId从数据库获取角色和权限等。
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        List<String> roles = userMapper.findRolesByUserId(user.getUserId());
        for (int i = 0, iLen = roles.size(); i < iLen; i++)
        {
            grantedAuthorityList.add(new SimpleGrantedAuthority(roles.get(i)));
        }

        List<String> perms = userMapper.findPermsByUserId(user.getUserId());
        for (int i = 0, iLen = perms.size(); i < iLen; i++)
        {
            grantedAuthorityList.add(new MyGrantedAuthority(perms.get(i)));
        }

//        grantedAuthorityList.add(new MyGrantedAuthority("MY_ROLE1", "MY_MENU1"));
//        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
//        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        grantedAuthorityList.add(new SimpleGrantedAuthority("admin"));
//        return new User(userName, "{noop}123456", grantedAuthorityList);
        return new SecurityUser(user, grantedAuthorityList);
    }
}
