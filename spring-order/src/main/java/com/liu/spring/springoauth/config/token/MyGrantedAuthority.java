package com.liu.spring.springoauth.config.token;

import org.springframework.security.core.GrantedAuthority;

/**
 * 自定义权限
 */
public class MyGrantedAuthority implements GrantedAuthority {
    private String roleId;
    private String menuId;

    @Override
    public String getAuthority() {
        if(roleId == null && menuId == null){
            return null;
        }else if(roleId == null){
            return menuId;
        }else if(menuId == null){
            return roleId;
        }
        return roleId + "&" + menuId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public MyGrantedAuthority(String menuId) {
        this.menuId = menuId;
    }

    public MyGrantedAuthority(String roleId, String menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
}