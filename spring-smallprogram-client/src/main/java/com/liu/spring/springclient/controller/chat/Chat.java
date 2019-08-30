package com.liu.spring.springclient.controller.chat;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.liu.spring.domain.chat.LoveSoundChatList;
import com.liu.spring.domain.chat.LoveSoundChatMsg;
import com.liu.spring.domain.commoninfo.Status;
import com.liu.spring.domain.log.LoveSoundLogLogin;
import com.liu.spring.domain.log.LoveSoundTemplateMsg;
import com.liu.spring.domain.smallprogram.WxUser;
import com.liu.spring.service.authorize.AuthorizeService;
import com.liu.spring.service.chat.ChatService;
import com.liu.spring.service.log.LogLoginService;
import com.liu.spring.service.myinfo.BaseInfoService;
import com.liu.spring.springclient.controller.BaseController;
import com.liu.util.date.DateUtils;
import com.liu.util.image.ImgManageService;
import com.liu.util.image.WaterImgManage;
import com.liu.util.mysql.PageBean;
import com.liu.util.object.HttpJsonResult;
import com.liu.util.redis.RedisTemplateService2;
import com.liu.util.smallprogram.WXPayConstants;
import com.liu.util.smallprogram.WXUtils;
import com.liu.util.string.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("smallProgram/chat")
@Api(value = "smallProgram/chat", description = "聊天模块")
public class Chat extends BaseController {


    @Autowired
    WXUtils wxUtils;
    @Autowired
    ChatService chatService;
    @Autowired
    BaseInfoService baseInfoService;
    @Autowired
    ImgManageService imgManageService;
    @Autowired
    AuthorizeService authorizeService;
    @Autowired
    LogLoginService logLoginService;
    @Autowired
    private RedisTemplateService2 redisTemplate;

    /**
     * 获取聊天列表 不分页
     * @return
     */
    @ApiOperation(value = "获取聊天列表 不分页", httpMethod = "GET", notes = "获取聊天列表 不分页")
    @ResponseBody
    @RequestMapping(value = "getUserChatList", method = RequestMethod.GET)
    public HttpJsonResult getUserChatList(HttpServletRequest request,@ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId) {

        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        List<LoveSoundChatList> loveSoundChatList = chatService.getUserChatList(userId);
        return HttpJsonResult.SUCCESS(loveSoundChatList);
    }

    /**
     * 获取与此用户的聊天的最后30条信息
     * @return
     */
    @ApiOperation(value = "获取与此用户的聊天的最后30条信息", httpMethod = "GET", notes = "获取与此用户的聊天的最后30条信息")
    @ResponseBody
    @RequestMapping(value = "getUserChatMsgLastList", method = RequestMethod.GET)
    public HttpJsonResult getUserChatMsgLastList(HttpServletRequest request,
                                                 @ApiParam(required = true, name = "userId", value = "userId与此用户的聊天的最后30条信息")@RequestParam long userId,
                                                 @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId) {

        if(userId == 0){
            return HttpJsonResult.FAIL("参数错误");
        }

        String userIdLogin = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userIdLogin)){
            return HttpJsonResult.FAIL("参数错误");
        }
        List<LoveSoundChatMsg> loveSoundChatList = chatService.getUserChatMsgLastList(userId,userIdLogin, new PageBean(30, 1));
        return HttpJsonResult.SUCCESS(loveSoundChatList);
    }

    /**
     * 获取与此用户的聊天的历史信息 -分页
     * @return
     */
    @ApiOperation(value = "获取与此用户的聊天的历史信息 -分页", httpMethod = "GET", notes = "获取与此用户的聊天的历史信息 -分页")
    @ResponseBody
    @RequestMapping(value = "getUserChatMsgHistoryList", method = RequestMethod.GET)
    public HttpJsonResult getUserChatMsgHistoryList(HttpServletRequest request,
                                                 @ApiParam(required = true, name = "pageSize", value = "页大小")@RequestParam int pageSize,
                                                 @ApiParam(required = true, name = "pageNum", value = "第几页")@RequestParam int pageNum,
                                                 @ApiParam(required = true, name = "userId", value = "userId与此用户的聊天的历史信息")@RequestParam long userId,
                                                 @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId) {

        if(userId == 0){
            return HttpJsonResult.FAIL("参数错误");
        }

        String userIdLogin = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userIdLogin)){
            return HttpJsonResult.FAIL("参数错误");
        }
        PageInfo pageInfo = chatService.getUserChatMsgHistoryList(userId,userIdLogin, new PageBean(pageSize ,pageNum));
        return HttpJsonResult.SUCCESS(pageInfo);
    }


    /**
     * 发一条消息 文字或图片
     * @return
     */
    @ApiOperation(value = "发一条消息 文字或图片", httpMethod = "GET", notes = "发一条消息 文字或图片")
    @ResponseBody
    @RequestMapping(value = "saveChatMsg", method = RequestMethod.POST)
    public HttpJsonResult saveChatMsg(HttpServletRequest request,
                                      @ApiParam(required = true, name = "userId", value = "userId,要发给的用户")@RequestParam long userId,
                                      @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId,
                                      @ApiParam(required = true, name = "content", value = "content,如果是图片的话就是图片两个字")@RequestParam String content,
                                      @ApiParam(required = true, name = "chatMsgPhoto", value = "图片文件")
                                      @RequestParam(value ="chatMsgPhoto", required = false) MultipartFile chatMsgPhoto) throws IOException {

        if(userId == 0){
            return HttpJsonResult.FAIL("参数错误");
        }
        if(chatMsgPhoto == null && StringUtils.isBlank(content)){
            return HttpJsonResult.FAIL("参数错误");
        }
        if(chatMsgPhoto != null && !"图片".equals(content)){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userIdLogin = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userIdLogin)){
            return HttpJsonResult.FAIL("参数错误");
        }
        LoveSoundChatMsg loveSoundChatMsg = new LoveSoundChatMsg();
        loveSoundChatMsg.setCreateTime(DateUtils.getNowDateTimestamp());
        loveSoundChatMsg.setCreateUserId(Long.valueOf(userIdLogin));
        loveSoundChatMsg.setSendUserId(loveSoundChatMsg.getCreateUserId());
        loveSoundChatMsg.setSendDate(loveSoundChatMsg.getCreateTime());
        loveSoundChatMsg.setReceiverUserId(userId);
        loveSoundChatMsg.setContent(content);
        loveSoundChatMsg.setStatus(Status.ACTIVE.getValue());
        loveSoundChatMsg.setHaveRead(false);
        loveSoundChatMsg.setUpdateTime(loveSoundChatMsg.getCreateTime());
        loveSoundChatMsg.setUpdateUserId(loveSoundChatMsg.getCreateUserId());
        loveSoundChatMsg.setMsgType(Integer.valueOf(Status.文字.getValue()));
        String sendTemplateMsgCount = content;
        if(sendTemplateMsgCount.length() > 10){
            sendTemplateMsgCount = sendTemplateMsgCount.substring(0, 10) + "...";
        }
        if(chatMsgPhoto != null){
            sendTemplateMsgCount = "收到一张图片";
            //发送的是图片
            //这个图片表的功能跟准备创建的用户相册表的功能一模一样，就不创建了，直接用这个表当相册表
            WaterImgManage skillImgManage = imgManageService.saveImg(Long.valueOf(userIdLogin), chatMsgPhoto , Status.ChatMsgImage.toString(), Status.ChatMsgImage.getValue());
            loveSoundChatMsg.setImage(skillImgManage.getUrlPath());
            loveSoundChatMsg.setImageId(skillImgManage.getId());
            loveSoundChatMsg.setMsgType(Integer.valueOf(Status.图片.getValue()));
            loveSoundChatMsg.setContent(skillImgManage.getUrlPath());
        }
        chatService.saveChatMsg(loveSoundChatMsg);

        //判断此用户最后一次请求是多久，如果是一小时以前，就发送模板消息,并且当天内没有发送过模板消息
        LoveSoundLogLogin loveSoundLogLogin = logLoginService.getLogLoginLast(userId);
        if(loveSoundLogLogin != null)
        {
            long s = DateUtils.subtractSecond(new Date(), loveSoundLogLogin.getCreateTime());
            if(s > 60 * 60 * 3)
            {
                //超过三小时没有上过
                //判断当天内是否发送过模板消息
                LoveSoundTemplateMsg loveSoundTemplateMsg = logLoginService.getLoveSoundTemplateMsgLastByUserId(userId);
                if(loveSoundTemplateMsg == null)
                {
                    //给这个用户发一个模板消息，如果发送失败，不论是form_id过期还是什么原因，后面不用管
                    WxUser wxUser = authorizeService.getWxUserById(userId);
                    if(StringUtils.isNotBlank(wxUser.getFormIds())){
                        String formId = wxUser.getFormIds().split(",")[0];

                        JSONObject data = new JSONObject();
                        JSONObject dataSub = new JSONObject();
                        dataSub.put("value", "您收到了一封私信");
                        data.put("keyword1", dataSub);
                        dataSub = new JSONObject();
                        dataSub.put("value", wxUser.getUserNickname());
                        data.put("keyword2", dataSub);
                        dataSub = new JSONObject();
                        dataSub.put("value", DateUtils.getNowDateString());
                        data.put("keyword3", dataSub);
                        dataSub = new JSONObject();
                        dataSub.put("value", sendTemplateMsgCount);
                        data.put("keyword4", dataSub);
                        wxUtils.sendTemplateMsg(WXPayConstants.APP_ID, wxUser.getOpenid(), "_TSvfT62-sic6c-EDu3f-AY0kmmFSL4riI9dWUTw0nY",
                                "pages/launch/launch",formId, data,"");

                        //将用掉的formId去掉
                        String formIdsNew = wxUser.getFormIds().substring(formId.length());
                        if(wxUser.getFormIds().length() > formId.length()){
                            formIdsNew = wxUser.getFormIds().substring(formId.length() + 1);
                        }
                        WxUser wxUserNew = new WxUser();
                        wxUserNew.setId(wxUser.getId());
                        wxUserNew.setFormIds(formIdsNew);
                        wxUserNew.setUpdateDate(new Date());
                        baseInfoService.saveBaseInfo(wxUserNew);

                        loveSoundTemplateMsg = new LoveSoundTemplateMsg();
                        loveSoundTemplateMsg.setUserId(userId);
                        loveSoundTemplateMsg.setContent(data.toJSONString());
                        loveSoundTemplateMsg.setCreateTime(DateUtils.getNowDateTimestamp());
                        loveSoundTemplateMsg.setCreateUserId(Long.valueOf(userIdLogin));
                        logLoginService.saveLoveSoundTemplateMsg(loveSoundTemplateMsg);

                        loveSoundChatMsg = null;
                        wxUser = null;
                        wxUserNew = null;
                        dataSub = null;
                        data = null;
                    }//end fi
                }//end fi
            }
        }

        return HttpJsonResult.SUCCESS();
    }


    /**
     * 删除与一个用户的聊天
     * @return
     */
    @ApiOperation(value = "删除与一个用户的聊天", httpMethod = "GET", notes = "删除与一个用户的聊天")
    @ResponseBody
    @RequestMapping(value = "delChat", method = RequestMethod.POST)
    public HttpJsonResult delChat(HttpServletRequest request,
                                                    @ApiParam(required = true, name = "userId", value = "userId与此用户的聊天")@RequestParam long userId,
                                                    @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId) {

        if(userId == 0){
            return HttpJsonResult.FAIL("参数错误");
        }

        String userIdLogin = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userIdLogin)){
            return HttpJsonResult.FAIL("参数错误");
        }
        LoveSoundChatList loveSoundChatList = chatService.getLoveSoundChatListByTowUserId(userId, Long.valueOf(userIdLogin));
        if(loveSoundChatList == null){
            return HttpJsonResult.FAIL("参数错误");
        }
        loveSoundChatList.setStatus(Status.DELETE.getValue());
        loveSoundChatList.setUpdateTime(loveSoundChatList.getCreateTime());
        loveSoundChatList.setUpdateUserId(loveSoundChatList.getUpdateUserId());
        chatService.updateLoveSoundChatList(loveSoundChatList);
        return HttpJsonResult.SUCCESS();
    }




}
