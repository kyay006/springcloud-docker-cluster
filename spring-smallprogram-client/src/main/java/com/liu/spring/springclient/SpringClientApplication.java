package com.liu.spring.springclient;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import com.github.tobato.fastdfs.FdfsClientConfig;
import com.liu.util.fastdfs.FastDFSClientWrapper;
import com.liu.util.rediscluster.RedisConfig;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.List;

//import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableSwagger2
@Controller
@EnableWebSecurity
@EnableEurekaClient
@ServletComponentScan
@SpringBootApplication
//@EnableFeignClients(basePackages = { "com.liu.spring.service"}) //??????Feign????????????????????????
@EnableRedisHttpSession //session??????
@EnableDistributedTransaction //???????????????lcn?????????????????????txmanager???client
@Import(FdfsClientConfig.class) //???Fdfs??????????????????
@ComponentScan(basePackages = "com.liu")
@MapperScan(basePackages="com.liu.spring.service.*.mapper")
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class SpringClientApplication {

    @Autowired
    private FastDFSClientWrapper dfsClient;



    @Autowired
    private RedisConfig redisTemplate;




    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(SpringClientApplication.class, args);



//        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream("MyProject-1234.json"))
//                .createScoped(Collections.singleton(SQLAdminScopes.SQLSERVICE_ADMIN));

        //www.googleapis.com/oauth2/v4/token


    }



    @ResponseBody
    @RequestMapping("getTest")
    public Object getTest(HttpServletRequest request) {
        String result = redisTemplate.getJedisCluster().set("wode","????????????");

//        System.out.println(request.getServerPort());
        return result;
    }




    /**
     * ??????????????????????????????????????????????????????
     * ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * @param request
     * @return
     */
    @Transactional
    @ResponseBody
    @RequestMapping("getHello")
    public Object getHello(HttpServletRequest request) {

        Object aaa = request.getSession().getAttribute("aaa");
        if(aaa == null){
            System.out.println(request.getServerPort() + "???????????????");
            request.getSession().setAttribute("aaa","??????");
            System.out.println(request.getServerPort() + "??????");
        }else{
            System.out.println(request.getServerPort() + "?????????????????????");
        }

//        System.out.println(request.getServerPort());
        return "upload";
    }


    @Transactional
    @LcnTransaction//????????????lcn??????
    @RequestMapping("testLcn")
    @ResponseBody
    public String testLcn(HttpServletRequest request){
        System.out.println("????????????...???????????????");

//        logger.info("????????????...???????????????");
        return "succ";
    }



    @LcnTransaction
    @Transactional
    @RequestMapping("getHe2")
    @ResponseBody
    public String getHe2(HttpServletRequest request){
//        LoveSoundLogLogin loveSoundLogLogin = new LoveSoundLogLogin();

//        loveSoundLogLogin.setUserId(1);
//        loveSoundLogLogin.setOpenId("222");
//        loveSoundLogLogin.setCreateTime(DateUtils.getNowDateTimestamp());

//        logLoginService.saveLogLogin(loveSoundLogLogin);

//        Map a = logLoginService.upLogLogin();
//        System.out.println(a.toString());
//
//        logLoginService.updateLogLogin();

//        logLoginService.updateLogSearch1();
        try {
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println(2222222 + ":::::" + request.getServerPort());

//        if(1 == 1){
//            throw new RuntimeException("");
//        }

        return "success";
    }









    @ResponseBody
    @ApiOperation(value = "????????????????????????", notes = "??????user????????????")
    @ApiImplicitParam(name = "user", value = "??????????????????", required = true, dataType = "String")
    @RequestMapping(value = "getHello1", method = RequestMethod.GET)
    public String getHello1(HttpServletRequest request, String param) {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa::::::::::::::" + param);
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        // ?????????
        System.out.println("Username:" + securityContextImpl.getAuthentication().getName());
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) securityContextImpl.getAuthentication().getDetails();
        // ??????????????????
        System.out.println("RemoteAddress:::   " + details.getRemoteAddress());
        // ??????sessionid
        System.out.println("SessionId:::   " + details.getSessionId());
        // ??????sessionid
        System.out.println("TokenValue:::   " + details.getTokenValue());
        // ????????????????????????????????????
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) securityContextImpl.getAuthentication().getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            System.out.println("Authority:::   " + grantedAuthority.getAuthority());
        }


        System.out.println(request.getServerPort());
        return request.getServerPort() + "getHello1";
    }


    @ResponseBody
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("getHello2")
    public String getHello2(HttpServletRequest request) {
        System.out.println(request.getServerPort());
        return request.getServerPort() + "getHello2";
    }

    @ResponseBody
    @RequestMapping("getHello3")
    public String getHello3(HttpServletRequest request) {
        System.out.println(request.getServerPort());
        return request.getServerPort() + "getHello3";
    }

    @ResponseBody
    @RequestMapping(value = "zzzzzz", method = RequestMethod.POST)
    public String upload(HttpServletRequest request, @RequestParam("photo") MultipartFile photo, @RequestHeader(value = "Content-Type") String type) {
        System.out.println("???????????????" + type);
        String imgUrl = null;
        try {
//            File dir = new File("D:\\img");
//            if (!dir.exists()) {
//                dir.mkdirs();  //????????????????????????
//            }
//            byte[] bytes = photo.getBytes();   //???????????????
//            File fileToSave = new File(dir.getAbsolutePath() + File.separator + photo.getOriginalFilename());
//            FileCopyUtils.copy(bytes, fileToSave);   //????????????

            imgUrl = dfsClient.uploadFile(photo);
            System.out.println(imgUrl);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        return request.getServerPort() + "upload";
        return imgUrl;
    }


    /**
     * http://lang:8005/delImage?imgPath=group1/M00/00/00/wKjohFu90LKACfTCAABoVtIJ1vw233.jpg
     * ????????????
     * http://192.168.232.130/purge/group1/M00/00/00/wKjohFu-wBmAbGoQAABoVtIJ1vw696.jpg
     * http://192.168.232.131/purge/group1/M00/00/00/wKjohFu-wBmAbGoQAABoVtIJ1vw696.jpg
     * @param request
     * @param imgPath
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delImage", method = RequestMethod.GET)
    public String delImage(HttpServletRequest request,String imgPath) {
        dfsClient.deleteFile(imgPath);
        return "success";
    }


    /**
     * ??????controller???????????????
     * @param request
     * @param response
     * @param imgPath
     */
    @RequestMapping(value = "downloadImage", method = RequestMethod.GET)
    public void downloadImage(HttpServletRequest request, HttpServletResponse response, String imgPath) {
        dfsClient.downloadFile1(response,imgPath);
    }




}
