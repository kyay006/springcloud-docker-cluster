package com.liu.spring.springoauth.controller;

import com.liu.spring.springoauth.utils.ImageCode;
import com.liu.util.redis.RedisTemplateService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

@Controller
public class LoginController {

    //oauth2的sessionid存到数据库为1的里面
    @Autowired
    private RedisTemplateService1 redisTemplateService1;

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @RequestMapping("/login")
    public String login(){
        /**
         * 可以在这里判断是手机wap还是pc登录
         */
        return "login";
    }

    @RequestMapping("/signout")
    public String signout(HttpServletRequest request,HttpServletResponse response,String access_token) throws Exception{

        if (consumerTokenServices.revokeToken(access_token)){
            System.out.println("注销成功");
        }else {
            System.out.println("注销失败");
        }

        //1.从redis删除session数据或者在数据库里标志已过期，然后再client加请求过滤器，来判断是否登录状态
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if(securityContextImpl != null && securityContextImpl.getAuthentication() != null){
            redisTemplateService1.set(securityContextImpl.getAuthentication().getName(), "false", Long.valueOf((60 * 30)));
        }

        // token can be revoked here if needed
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            request.getSession().invalidate();
            request.logout();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "tologin";
    }

    @RequestMapping("/")
    public String home(){
        return "index";
    }


    @RequestMapping(value = "/images/imagecode")
    public String imagecode(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        OutputStream os = response.getOutputStream();
        Map<String,Object> map = ImageCode.getImageCode(60, 20, os);

        String simpleCaptcha = "simpleCaptcha";
        request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
        request.getSession().setAttribute("codeTime",new Date().getTime());

        try {
            ImageIO.write((BufferedImage) map.get("image"), "JPEG", os);
        } catch (IOException e) {
            return "";
        }
        return null;
    }

    @RequestMapping(value = "/checkcode")
    @ResponseBody
    public String checkcode(HttpServletRequest request, HttpSession session)
            throws Exception {
        String checkCode = request.getParameter("checkCode");
        Object simple = session.getAttribute("simpleCaptcha") ; //验证码对象
        if(simple == null){
            request.setAttribute("errorMsg", "验证码已失效，请重新输入！");
            return "验证码已失效，请重新输入！";
        }

        String captcha = simple.toString();
        Date now = new Date();
        Long codeTime = Long.valueOf(session.getAttribute("codeTime")+"");
        if(StringUtils.isEmpty(checkCode) || captcha == null ||  !(checkCode.equalsIgnoreCase(captcha))){
            request.setAttribute("errorMsg", "验证码错误！");
            return "验证码错误！";
        }else if ((now.getTime()-codeTime)/1000/60>5){//验证码有效长度为5分钟
            request.setAttribute("errorMsg", "验证码已失效，请重新输入！");
            return "验证码已失效，请重新输入！";
        }else {
            session.removeAttribute("simpleCaptcha");
            return "1";
        }
    }
}
