package com.liu.spring.springoauth;

import com.liu.spring.springoauth.config.token.User;
import com.liu.spring.springoauth.domain.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.security.Principal;
import java.util.List;


@RestController
@EnableEurekaClient
@SpringBootApplication
@EnableRedisHttpSession //session共享
@ComponentScan(basePackages = "com.liu")
public class SpringOauthApplication  implements ServletContextInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringOauthApplication.class, args);
    }


//    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {
        servletContext.getSessionCookieConfig().setName("SESSIONID");
    }


    @Autowired
    private UserMapper userMapper;

    @ResponseBody
    @GetMapping("/oauthTest")
    public List<User> oauthTest(){
        List<User> userList =  userMapper.findAll();
        for (int i = 0; i < userList.size(); i++) {
            System.out.println(userList.get(i).getLoginName());
        }
        return userList;
    }


    @GetMapping("/user")
    public Principal user(Principal user){
        return user;
    }

}
