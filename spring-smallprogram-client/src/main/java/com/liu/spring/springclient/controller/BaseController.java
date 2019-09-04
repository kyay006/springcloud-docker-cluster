package com.liu.spring.springclient.controller;

import com.liu.spring.domain.commoninfo.Status;
import com.liu.util.object.HttpJsonResult;
import com.liu.util.rediscluster.RedisConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Controller
public class BaseController implements ErrorController {

    private final Logger logger = LoggerFactory.getLogger(BaseController.class);
    private static final String ERROR_PATH = "/error";

    /**
     * 从redis获取userId
     * @param redisTemplate
     * @param openId
     * @return
     */
//    protected String getUserIdByRedis(RedisTemplateService2 redisTemplate, String openId)
    protected String getUserIdByRedis(RedisConfig redisTemplate, String openId)
    {

//        Object userIds = redisTemplate.get(openId);
        Object userIds = redisTemplate.getJedisCluster().get(openId);
        if(userIds == null){
            return null;
        }
        String [] ids = userIds.toString().split(Status.AND.getValue());
        String userId = ids[3];
        if(com.liu.util.string.StringUtils.isBlank(userId)){
            return null;
        }
        return userId;
    }



    /**
     * 如果您请求的资源不再您的权限范围，则跳转到/403请求地址
     * @return
     */
//    @RequestMapping("/403")
//    public String unauthorizedRole()
//    {
//        return "user/common/unauthorized";
//    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String,Object> handleException(Exception ex, HttpServletRequest request) {
        /*logger*/
        logger.error("-------------------------------------------------------------------");
        logger.error("Error on processing request.");
        logger.error("URL: " + request.getRequestURL());
        logger.error("Method: " + request.getMethod());
        logger.error("Params: ");
        logger.error(dumpRequestParams(request));

        logger.error("Headers: ");
        logger.error(dumpRequestHeaders(request));

        logger.error("ErrMessage:" + ex.getMessage(), ex);
        logger.error("-------------------------------------------------------------------");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("resultCode", HttpJsonResult.ERROR);
        map.put("resultInfo",ex.getMessage());
        return map;
//		final String json = JSON.toJSONString(map);
//		return json;
    }

    private String dumpRequestHeaders(HttpServletRequest request)
    {
        Enumeration<String> names = request.getHeaderNames();
        List<String> headers = new ArrayList<String>();
        while(names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getHeader(name);
            String header = name + ": " + value;
            headers.add(header);
        }
        String sep = "\n\t--------------------------------------------------------------";
        return sep + "\n\t" + StringUtils.join(headers,"\n\t") + sep;
    }

    private String dumpRequestParams(HttpServletRequest request)
    {
        Map<String,String[]> paramMap = request.getParameterMap();
        List<String> params = new ArrayList<String>();
        for (String paramName : paramMap.keySet()) {
            String[] values = paramMap.get(paramName);
            if (values == null || values.length == 0) {
                // Do nothing, no values found at all.
                String param = paramName + "=";
                params.add(param);
            }
            else if (values.length > 1) {
                for (String value : values) {
                    String param = paramName + "=" + value;
                    params.add(param);
                }
            }
            else {
                String param = paramName + "=" + values[0];
                params.add(param);
            }
        }
        String sep = "\n\t--------------------------------------------------------------";
        return sep + "\n\t" + StringUtils.join(params, "\n\t") + sep;
    }







    @RequestMapping(value=ERROR_PATH)
    public String handleError(){
        return "403";
    }

    @Override
    public String getErrorPath() {
        // TODO Auto-generated method stub
        return ERROR_PATH;
    }

    @RequestMapping(value="/deny")
    public String handleDeny(){
        return "deny";
    }

    @RequestMapping("/tosignout")
    public String tosso() {
        return "tosignout";
    }

    @RequestMapping("/login")
    public String login() {
        return "redirect:/#/";
    }

    @RequestMapping("/")
    public String index(ModelMap model, Principal user) throws Exception{
        model.addAttribute("user", user);
        return "home";
    }

}
