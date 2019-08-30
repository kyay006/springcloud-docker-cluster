package com.liu.lovesound.springorder;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import com.liu.util.date.DateUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Map;


@EnableSwagger2
@Controller
@EnableWebSecurity
@EnableEurekaClient
@ServletComponentScan
@EnableRedisHttpSession //session共享
@EnableFeignClients(basePackages = { "com.liu.lovesound.springorder"})
@EnableDistributedTransaction //分布式事务lcn，表明这是一个txmanager的client
//@Import(FdfsClientConfig.class) //将Fdfs配置引入项目
@ComponentScan(basePackages = "com.liu")
@MapperScan(basePackages="com.liu.lovesound.springorder.mapper")
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@SpringBootApplication
public class SpringOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringOrderApplication.class, args);
    }


    @Autowired
    LogLoginService logLoginService;

    @Autowired
    LogFeign logFeign;



    @LcnTransaction
    @Transactional
    @RequestMapping("getHe")
    @ResponseBody
    public String getHe(){
        LoveSoundLogLogin loveSoundLogLogin = new LoveSoundLogLogin();

        loveSoundLogLogin.setUserId(1);
        loveSoundLogLogin.setOpenId("222");
        loveSoundLogLogin.setCreateTime(DateUtils.getNowDateTimestamp());

//        logLoginService.saveLogLogin(loveSoundLogLogin);

        Map a = logLoginService.upLogLogin();
        System.out.println(a.toString());

        logLoginService.updateLogLogin();

        System.out.println(1111);

        String aaa = logFeign.getHe2();
        System.out.println(aaa);

        System.out.println(33333);

//        if(1 == 1){
//            throw new RuntimeException("");
//        }

        return "success";
    }

}
