package com.liu.spring.springoauth.domain.user.mapper;


import com.liu.spring.springoauth.config.token.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from sys_user")
    List<User> findAll();
//
//    @Select("select * from users")
//    List<User> findAll();

//    @Select("select * from users where user_name=#{userName}")
//    @Results({ @Result(id = true, column = "userId", property = "user_id"),
//            @Result(column = "userName", property = "user_name"),
//            @Result(column = "userPwd", property = "user_pwd"),
//            @Result(column = "userType", property = "user_type"),
//            @Result(column = "userAccount", property = "user_account"),
//            @Result(column = "createDate", property = "createDate"),
//            @Result(column = "email", property = "email"),
//            @Result(column = "phone", property = "phone"),
//            @Result(column = "deptId", property = "deptId") })
//    User findUserByUserName(String userName);

    @Select("SELECT * FROM sys_user WHERE login_name=#{loginName}")
    @Results({ @Result(id = true, column = "user_id", property = "userId"),
            @Result(column = "dept_id", property = "deptId"),
            @Result(column = "login_name", property = "loginName"), //登录账号
            @Result(column = "user_name", property = "userName"), //用户昵称
            @Result(column = "user_type", property = "userType"), //用户类型（00系统用户）
            @Result(column = "email", property = "email"),
            @Result(column = "phonenumber", property = "phoneNumber"),
            @Result(column = "sex", property = "sex"), //用户性别（0男 1女 2未知）
            @Result(column = "avatar", property = "avatar"), //头像路径
            @Result(column = "password", property = "userPassword"),
            @Result(column = "salt", property = "salt"), //盐加密
            @Result(column = "status", property = "status"), //帐号状态（0正常 1停用）
            @Result(column = "del_flag", property = "delFlag"), //删除标志（0代表存在 2代表删除）
            @Result(column = "login_date", property = "loginDate"), //最后登陆时间
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "remark", property = "remark") })
    User findUserByUserName(String loginName);





    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Select("SELECT r.role_key FROM sys_user u INNER JOIN sys_user_role ur ON u.user_id = ur.user_id INNER JOIN sys_role r ON ur.role_id = r.role_id WHERE ur.user_id = #{userId}")
    @Results({ @Result(column = "role_key") })
    List<String> findRolesByUserId(int userId);



    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Select("SELECT distinct m.perms FROM sys_menu m LEFT JOIN sys_role_menu rm ON m.menu_id = rm.menu_id LEFT JOIN sys_user_role ur ON rm.role_id = ur.role_id WHERE ur.user_id = #{userId}")
    @Results({ @Result(column = "perms") })
    List<String> findPermsByUserId(int userId);








}
