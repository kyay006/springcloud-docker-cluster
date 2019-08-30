package com.liu.spring.springclient.controller.authorize;

import com.alibaba.fastjson.JSONObject;
import com.liu.spring.domain.commoninfo.Status;
import com.liu.spring.domain.log.LoveSoundLogLogin;
import com.liu.spring.domain.smallprogram.WxUser;
import com.liu.spring.service.authorize.AuthorizeService;
import com.liu.spring.service.log.LogLoginService;
import com.liu.spring.service.myinfo.BaseInfoService;
import com.liu.spring.springclient.controller.BaseController;
import com.liu.util.date.DateUtils;
import com.liu.util.object.HttpJsonResult;
import com.liu.util.redis.RedisTemplateService2;
import com.liu.util.smallprogram.AES;
import com.liu.util.smallprogram.WXPayConstants;
import com.liu.util.smallprogram.WXUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("smallProgram/authorize")
@Api(value = "smallProgram/authorize", description = "授权模块")
public class Authorize extends BaseController {


    @Autowired
    private RedisTemplateService2 redisTemplate;

    @Autowired
    AuthorizeService authorizeService;

    @Autowired
    LogLoginService logLoginService;

    @Resource
    private WXUtils wxUtils;

    @Autowired
    BaseInfoService baseInfoService;

    /**
     * 小程序登录
     * @return
     */
    @ApiOperation(value = "小程序登录", httpMethod = "POST", notes = "小程序登录")
    @ResponseBody
    @RequestMapping(value = "trans2OpenId", method = RequestMethod.POST)
    public HttpJsonResult trans2OpenId(HttpServletRequest request,
                                       @ApiParam(required = true, name = "appId", value = "数据id")@RequestParam String appId,
                                       @ApiParam(required = true, name = "code", value = "")@RequestParam String code,
                                       @ApiParam(required = true, name = "encryptedData", value = "用户信息")@RequestParam(required = false) String encryptedData,
                                       @ApiParam(required = true, name = "iv", value = "偏差位")@RequestParam String iv) {

        if(!WXPayConstants.APP_ID.equals(appId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        // 获取session_key和openid
        JSONObject jsonToken = wxUtils.getMiniBaseUserInfo(code, WXPayConstants.APP_ID, WXPayConstants.SECRET);
        if (null == jsonToken) {
            return HttpJsonResult.FAIL();
        }

        String openId = jsonToken.getString("openid");
        String session_key = jsonToken.getString("session_key");
        String sessionId = request.getSession().getId();
        StringBuilder sf = new StringBuilder(80);
        sf.append(openId).append(Status.AND.getValue());
        sf.append(session_key).append(Status.AND.getValue());
        sf.append(sessionId);
        WxUser wxUser = null;
        LoveSoundLogLogin loveSoundLogLogin = new LoveSoundLogLogin();
        if(StringUtils.isNotEmpty(encryptedData)){
            try {
                AES aes = new AES();
                byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(session_key), Base64.decodeBase64(iv));
                if(null != resultByte && resultByte.length > 0){
                    String userInfo = new String(resultByte, "UTF-8");
                    wxUser = JSONObject.parseObject(userInfo, WxUser.class);
                    if(StringUtils.isNotEmpty(wxUser.getOpenid())){
                        //根据openid查看用户是否存在，如果不存在，则存储
                        WxUser wxUserOld =authorizeService.getWxUserByOpenId(wxUser.getOpenid());
                        if(wxUserOld == null){
                            //存一下
                            wxUser.setCreateDate(new Date());
                            wxUser.setUserNickname(wxUser.getNickname());
                            wxUser.setUserAvatarurl(wxUser.getAvatarurl());
                            if(wxUser.getGender() == 0){
                                //默认是女生
                                wxUser.setGender(2);
                            }
                            authorizeService.saveWxUser(wxUser);
                            loveSoundLogLogin.setLoginType(Integer.valueOf(Status.授权.getValue()));
                        }//end fi
                        else {
                            //不用存，直接返回
                            wxUser = wxUserOld;
                            loveSoundLogLogin.setLoginType(Integer.valueOf(Status.登录.getValue()));
                        }
                    }//end fi
                    else{
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return HttpJsonResult.FAIL();
            }
        }
        sf.append(Status.AND.getValue()).append(wxUser.getId());
        //存一下redis,8小时
        redisTemplate.saveKeyVal(openId, sf.toString()).expire(openId, 60 * 60 * 8);
        loveSoundLogLogin.setUserId(wxUser.getId());
        loveSoundLogLogin.setOpenId(wxUser.getOpenid());
        loveSoundLogLogin.setCreateTime(DateUtils.getNowDateTimestamp());
        try {
            logLoginService.saveLogLogin(loveSoundLogLogin);
        } catch (Exception e) {
        }
        if(Status.DISABLED.getValue().equals(wxUser.getStatus())){
            return HttpJsonResult.FAIL("操作失败,用户已禁用");
        }
        if(Status.DELETE.getValue().equals(wxUser.getStatus())){
            return HttpJsonResult.FAIL("操作失败,用户不存在");
        }
        //第二个参数是，此用户是否填过详细信息了
        return HttpJsonResult.SUCCESS(new Object[]{wxUser.getId(), wxUser.getUserHeight()> 0 && wxUser.getUserEducation() != null,
                                                   openId,wxUser.getUserAvatarurl(),wxUser.getLoveWords(),wxUser.getUserNickname()});
    }





    /**
     * 保存模板需要的formid串
     * @return
     */
    @ApiOperation(value = "保存模板需要的formid串", httpMethod = "POST", notes = "保存模板需要的formid串")
    @ResponseBody
    @RequestMapping(value = "saveFormIds", method = RequestMethod.POST)
    public HttpJsonResult saveBaseInfo(HttpServletRequest request, @ApiParam(required = true, name = "formIds", value = "formid串") String formIds,
                                       @ApiParam(required = true, name = "openId", value = "openId")String openId) {
        if(com.liu.util.string.StringUtils.isBlank(formIds) || com.liu.util.string.StringUtils.isBlank(openId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(com.liu.util.string.StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        WxUser wxUser = new WxUser();
        wxUser.setId(Long.valueOf(userId));
        wxUser.setUpdateDate(new Date());
        wxUser.setFormIds(formIds);
        baseInfoService.saveBaseInfo(wxUser);
        return HttpJsonResult.SUCCESS();
    }






}
