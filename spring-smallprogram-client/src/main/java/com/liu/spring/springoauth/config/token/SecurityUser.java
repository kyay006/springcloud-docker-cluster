package com.liu.spring.springoauth.config.token;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecurityUser extends User implements UserDetails
{

    private static final long serialVersionUID = 1L;

    private List<GrantedAuthority> grantedAuthorityList;

//    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, authorities);
//    }

    public SecurityUser(User user, List<GrantedAuthority> grantedAuthorityList) {
        if(user != null)
        {
            this.setUserId(user.getUserId());
            this.setDeptId(user.getDeptId());
            this.setLoginName(user.getLoginName());
            this.setUserName(user.getUserName());
            this.setUserType(user.getUserType());
            this.setAvatar(user.getAvatar());
            this.setUserPassword(user.getUserPassword());
            this.setStatus(user.isStatus());
            this.setSalt(user.getSalt());
            this.setDelFlag(user.getDelFlag());
            this.setCreateTime(user.getCreateTime());
            this.grantedAuthorityList = grantedAuthorityList;
        }
    }


    /**
     *用户所拥有的权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorityList;
    }


    /**
     * 用户名
     */
    @Override
    public String getUsername() {
        return this.getLoginName();
//        return this.getUserName();
    }

    @Override
    public String getPassword() {
        return this.getUserPassword();
    }


    /**
     * 用户的账号是否过期,过期的账号无法通过授权验证. true 账号未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用户的账户是否被锁定,被锁定的账户无法通过授权验证. true 账号未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return !this.isStatus(); //帐号状态（0正常 1停用）
//        return true;
    }

    /**
     * 用户的凭据(pasword) 是否过期,过期的凭据不能通过验证. true 没有过期,false 已过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用
     */
    @Override
    public boolean isEnabled() {
        //删除标志（0代表存在 2代表删除）
        return this.getDelFlag() == 0;
//        return true;
    }

}
