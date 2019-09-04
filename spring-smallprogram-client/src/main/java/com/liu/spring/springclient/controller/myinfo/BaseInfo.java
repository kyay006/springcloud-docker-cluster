package com.liu.spring.springclient.controller.myinfo;


import com.github.pagehelper.PageInfo;
import com.liu.spring.domain.commoninfo.Status;
import com.liu.spring.domain.log.LoveSoundLogUserInfo;
import com.liu.spring.domain.myinfo.LoveSoundUserDynamic;
import com.liu.spring.domain.myinfo.LoveSoundUserSubscribe;
import com.liu.spring.domain.smallprogram.WxUser;
import com.liu.spring.service.log.LogLoginService;
import com.liu.spring.service.myinfo.BaseInfoService;
import com.liu.spring.springclient.controller.BaseController;
import com.liu.util.date.DateUtils;
import com.liu.util.image.ImgManageService;
import com.liu.util.image.WaterImgManage;
import com.liu.util.mysql.PageBean;
import com.liu.util.object.HttpJsonResult;
import com.liu.util.rediscluster.RedisConfig;
import com.liu.util.string.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("smallProgram/baseInfo")
@Api(value = "smallProgram/baseInfo", description = "基础信息模块")
public class BaseInfo extends BaseController {


    @Autowired
    BaseInfoService baseInfoService;

    @Autowired
    ImgManageService imgManageService;

    @Autowired
//    private RedisTemplateService2 redisTemplate;
    private RedisConfig redisTemplate;
    @Autowired
    LogLoginService logLoginService;

    /**
     * 保存基本信息
     * @return
     */
    @ApiOperation(value = "保存基本信息", httpMethod = "POST", notes = "保存基本信息 不分页")
    @ResponseBody
    @RequestMapping(value = "saveBaseInfo", method = RequestMethod.POST)
    public HttpJsonResult saveBaseInfo(HttpServletRequest request, @ApiParam(required = true, name = "wxUser", value = "用户基本信息") WxUser wxUser,
                                                                   @ApiParam(required = true, name = "openId", value = "openId")String openId) {
        if(wxUser.getId() == null
           || wxUser.getAgeFormat() == null || StringUtils.isBlank(wxUser.getUserHeight())
           || StringUtils.isBlank(wxUser.getUserWeight())){
            return HttpJsonResult.FAIL("参数错误");
        }
        if(openId == null){
            return HttpJsonResult.FAIL("参数错误");
        }
//        Object userIds = redisTemplate.get(openId);
        Object userIds = redisTemplate.getJedisCluster().get(openId);
        if(userIds == null){
            return HttpJsonResult.FAIL("参数错误");
        }
        String [] ids = userIds.toString().split(Status.AND.getValue());
        String userId = ids[3];
        if(StringUtils.isBlank(userId) || !userId.equals(String.valueOf(wxUser.getId()))){
            return HttpJsonResult.FAIL("参数错误");
        }

        //算年龄
        int year = DateUtils.yearsBetween(wxUser.getAgeFormat(), new Date());
        wxUser.setUserAge(year);
        wxUser.setUpdateDate(new Date());

        LoveSoundLogUserInfo loveSoundLogUserInfo = new LoveSoundLogUserInfo();
        BeanUtils.copyProperties(wxUser, loveSoundLogUserInfo);
        loveSoundLogUserInfo.setUserId(Long.valueOf(userId));
        loveSoundLogUserInfo.setCreateTime(DateUtils.getNowDateTimestamp());
        try {
            logLoginService.saveLogUserInfo(loveSoundLogUserInfo);
        } catch (Exception e) {
        }
        baseInfoService.saveBaseInfo(wxUser);
        return HttpJsonResult.SUCCESS();
    }




    /**
     * 获取用户基本信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getBaseInfo", method = RequestMethod.GET)
    public HttpJsonResult getBaseInfo(@ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId)
    {
        if(openId == null){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        WxUser wxUser = baseInfoService.getWxUserBaseInfo(userId);
        return HttpJsonResult.SUCCESS(wxUser);
    }


    /**
     * 上传头像
     * @return
     */
    @ApiOperation(value = "修改头像,上传头像", httpMethod = "POST", notes = "上传头像")
    @ResponseBody
    @RequestMapping(value = "saveUserAvatar", method = RequestMethod.POST)
    public HttpJsonResult saveUserAvatar(HttpServletRequest request,
                                         @ApiParam(required = true, name = "userAvatar", value = "用户头像文件")
                                         @RequestParam(value ="userAvatar") MultipartFile userAvatar,
                                         @ApiParam(required = true, name = "openId", value = "openId")String openId) throws IOException {
        if(userAvatar == null){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }

        WaterImgManage skillImgManage = imgManageService.saveImg(Long.valueOf(userId), userAvatar ,Status.HeadImage.toString(), Status.HeadImage.getValue());
        if(skillImgManage.getId() != null){
            //修改用户头像
            WxUser wxUser = new WxUser();
            wxUser.setId(Long.valueOf(userId));
            wxUser.setUserAvatarurl(skillImgManage.getUrlPath());
            wxUser.setUpdateDate(new Date());
            baseInfoService.saveBaseInfo(wxUser);

            return HttpJsonResult.SUCCESS(skillImgManage.getUrlPath());
        }else{
            return HttpJsonResult.FAIL("上传失败,请重试");
        }
    }


    /**
     * 修改昵称或者情话或者其他信息
     * @return
     */
    @ApiOperation(value = "修改昵称或者情话或者其他信息", httpMethod = "POST", notes = "修改昵称或者情话或者其他信息")
    @ResponseBody
    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    public HttpJsonResult updateUserInfo(HttpServletRequest request,
                                         @ApiParam(required = true, name = "wxUser", value = "用户信息")
                                         WxUser wxUser,
                                         @ApiParam(required = true, name = "openId", value = "openId")String openId) throws IOException {
        if(wxUser.getId() == null){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId) ||  !userId.equals(String.valueOf(wxUser.getId()))){
            return HttpJsonResult.FAIL("参数错误");
        }


        LoveSoundLogUserInfo loveSoundLogUserInfo = new LoveSoundLogUserInfo();
        BeanUtils.copyProperties(wxUser, loveSoundLogUserInfo);
        loveSoundLogUserInfo.setUserId(Long.valueOf(userId));
        loveSoundLogUserInfo.setCreateTime(DateUtils.getNowDateTimestamp());
        try {
            logLoginService.saveLogUserInfo(loveSoundLogUserInfo);
        } catch (Exception e) {
        }

        wxUser.setUpdateDate(new Date());
        baseInfoService.saveBaseInfo(wxUser);

        return HttpJsonResult.SUCCESS();
    }



    /**
     * 上传图片到自己的相册
     * @return
     */
    @ApiOperation(value = "上传图片到自己的相册", httpMethod = "POST", notes = "上传图片到自己的相册")
    @ResponseBody
    @RequestMapping(value = "photosUpload", method = RequestMethod.POST)
    public HttpJsonResult photosUpload(HttpServletRequest request,
                                         @ApiParam(required = true, name = "userPhoto", value = "图片文件")
                                         @RequestParam(value ="userPhoto") MultipartFile userPhoto,
                                         @ApiParam(required = true, name = "openId", value = "openId")String openId) throws IOException {
        if(userPhoto == null){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }

        //这个图片表的功能跟准备创建的用户相册表的功能一模一样，就不创建了，直接用这个表当相册表
        WaterImgManage skillImgManage = imgManageService.saveImg(Long.valueOf(userId), userPhoto ,Status.PhotoImage.toString(), Status.PhotoImage.getValue());
        if(skillImgManage.getId() != null){
            return HttpJsonResult.SUCCESS(skillImgManage.getUrlPath());
        }else{
            return HttpJsonResult.FAIL("上传失败,请重试");
        }
    }

    /**
     * 获取用户相册的图片地址 列表 不分页
     * @param request
     * @return
     */
    @ApiOperation(value = "获取用户相册的图片地址 列表 不分页", httpMethod = "GET", notes = "获取用户相册的图片地址 列表 不分页")
    @ResponseBody
    @RequestMapping(value = "getUserPhotoList", method = RequestMethod.GET)
    public HttpJsonResult getUserPhotoList(HttpServletRequest request,
                                              @ApiParam(required = true, name = "openId", value = "openId")String openId) {
        if(StringUtils.isBlank(openId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        List<WaterImgManage> waterImgManageList = imgManageService.getUserPhotoList(userId ,Status.PhotoImage.getValue());
        return HttpJsonResult.SUCCESS(waterImgManageList);
    }




    /**
     * 根据id删除一张图片
     * @return
     */
    @ApiOperation(value = "根据id删除一张图片", httpMethod = "POST", notes = "根据id删除一张图片")
    @ResponseBody
    @RequestMapping(value = "delUserImageById", method = RequestMethod.POST)
    public HttpJsonResult delUserImageById(HttpServletRequest request,
                                       @ApiParam(required = true, name = "id", value = "图片id")
                                       @RequestParam(value ="id") long id,
                                       @ApiParam(required = true, name = "openId", value = "openId")String openId) throws IOException {
        if(id == 0){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }

        //根据id使一张图片失效
        imgManageService.delUserImageById(Long.valueOf(userId), id ,Status.PhotoImage.getValue());
        return HttpJsonResult.SUCCESS();
    }




    /**
     * 上传图片 - 这里是用户动态的图片,因为微信不支持多个文件上传，所以这里单独上传
     * @return
     */
    @ApiOperation(value = "上传图片 用户动态", httpMethod = "POST", notes = "上传图片 用户动态")
    @ResponseBody
    @RequestMapping(value = "dynamicPhotosUpload", method = RequestMethod.POST)
    public HttpJsonResult dynamicPhotosUpload(HttpServletRequest request,
                                       @ApiParam(required = true, name = "userDynamicPhoto", value = "图片文件")
                                       @RequestParam(value ="userDynamicPhoto") MultipartFile userDynamicPhoto,
                                       @ApiParam(required = true, name = "openId", value = "openId")String openId) throws IOException {
        if(userDynamicPhoto == null){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }

        //保存一个动态图片
        WaterImgManage skillImgManage = imgManageService.saveImg(Long.valueOf(userId), userDynamicPhoto ,Status.DynamicImage.toString(), Status.DynamicImage.getValue());
        if(skillImgManage.getId() != null){
            return HttpJsonResult.SUCCESS(new Object[]{skillImgManage.getId(), skillImgManage.getUrlPath()});
        }else{
            return HttpJsonResult.FAIL("上传失败,请重试");
        }
    }



    /**
     * 保存一条用户动态
     * @return
     */
    @ApiOperation(value = "保存一条用户动态", httpMethod = "POST", notes = "保存一条用户动态")
    @ResponseBody
    @RequestMapping(value = "saveUserDynamic", method = RequestMethod.POST)
    public HttpJsonResult saveUserDynamic(HttpServletRequest request,
                                         @ApiParam(required = true, name = "wxUser", value = "用户动态信息")
                                         LoveSoundUserDynamic loveSoundUserDynamic,
                                         @ApiParam(required = true, name = "openId", value = "openId")String openId) throws IOException {
        if(loveSoundUserDynamic == null || StringUtils.isBlank(loveSoundUserDynamic.getDynamicContent())){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }

        loveSoundUserDynamic.setCreateTime(DateUtils.getNowDateTimestamp());
        loveSoundUserDynamic.setCreateUserId(Long.valueOf(userId));
        loveSoundUserDynamic.setUpdateTime(loveSoundUserDynamic.getCreateTime());
        loveSoundUserDynamic.setCreateUserId(loveSoundUserDynamic.getCreateUserId());
        baseInfoService.saveUserDynamic(loveSoundUserDynamic);
        return HttpJsonResult.SUCCESS();
    }


    /**
     * 删除一条用户动态
     * @return
     */
    @ApiOperation(value = "删除一条用户动态", httpMethod = "POST", notes = "删除一条用户动态")
    @ResponseBody
    @RequestMapping(value = "delUserDynamic", method = RequestMethod.POST)
    public HttpJsonResult delUserDynamic(HttpServletRequest request,
                                          @ApiParam(required = true, name = "id", value = "用户动态信息id") long id,
                                          @ApiParam(required = true, name = "openId", value = "openId")String openId) throws IOException {
        if(id == 0){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        LoveSoundUserDynamic loveSoundUserDynamic  = baseInfoService.getLoveSoundUserDynamicById(id);
        if(loveSoundUserDynamic == null || loveSoundUserDynamic.getCreateUserId() != Long.valueOf(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        loveSoundUserDynamic.setStatus(Status.DELETE.getValue());
        loveSoundUserDynamic.setUpdateTime(DateUtils.getNowDateTimestamp());
        loveSoundUserDynamic.setUpdateUserId(Long.valueOf(userId));
        baseInfoService.updateUserDynamic(loveSoundUserDynamic);
        return HttpJsonResult.SUCCESS();
    }


    /**
     * 获取一个用户动态列表 分页
     * @return
     */
    @ApiOperation(value = "获取一个用户动态列表 分页", httpMethod = "GET", notes = "获取一个用户动态列表 分页")
    @ResponseBody
    @RequestMapping(value = "getUserDynamicList", method = RequestMethod.GET)
    public HttpJsonResult getUserDynamicList(HttpServletRequest request,
                                              @ApiParam(required = true, name = "pageSize", value = "页大小")@RequestParam int pageSize,
                                              @ApiParam(required = true, name = "pageNum", value = "第几页")@RequestParam int pageNum,
                                              @ApiParam(required = true, name = "userId", value = "用户id")@RequestParam long userId,
                                              @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId) {
        if(userId == 0){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userIdLogin = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userIdLogin)){
            return HttpJsonResult.FAIL("参数错误");
        }
        PageInfo pageInfo = baseInfoService.getUserDynamicList(userId,new PageBean(pageSize,pageNum));
        return HttpJsonResult.SUCCESS(pageInfo);
    }




    /**
     * 用户关注其他用户
     * 注：严格来说，应该限制每天关注的数量，防刷
     * @param passiveUserId 被关注用户id
     * @return
     */
    @ResponseBody
    @RequestMapping("saveSubscribeUser")
    public HttpJsonResult subscribeUser(@ApiParam(required = true, name = "passiveUserId", value = "被关注用户id")@RequestParam long passiveUserId,
                                        @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId)
    {
        /*判断被关注用户id是否是登录用户本身*/
        if(passiveUserId == 0){
            return HttpJsonResult.FAIL("参数错误！");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        if(userId.equals(String.valueOf(passiveUserId))){
            return HttpJsonResult.FAIL("不允许关注自己！");
        }
        /*判断是否已有关注*/
        LoveSoundUserSubscribe loveSoundUserSubscribe = baseInfoService.getSubscribeServiceByUserId(userId, passiveUserId);
        if(loveSoundUserSubscribe != null && loveSoundUserSubscribe.getSubscribeStatus() == Integer.valueOf(Status.关注.getValue())){
            return HttpJsonResult.FAIL("已关注！");
        }
        if(loveSoundUserSubscribe == null){
            loveSoundUserSubscribe = new LoveSoundUserSubscribe();
            loveSoundUserSubscribe.setCreateTime(DateUtils.getNowDateTimestamp());
            loveSoundUserSubscribe.setCreateUserId(Long.valueOf(userId));
        }
        loveSoundUserSubscribe.setSubscribeStatus(Integer.valueOf(Status.关注.getValue()));
        loveSoundUserSubscribe.setPassiveUserId(passiveUserId);
        loveSoundUserSubscribe.setUpdateTime(DateUtils.getNowDateTimestamp());
        loveSoundUserSubscribe.setUpdateUserId(Long.valueOf(userId));
        baseInfoService.saveOrUpdateWaterUserSubscribe(loveSoundUserSubscribe);
        return HttpJsonResult.SUCCESS("关注成功");
    }


    /**
     * 用户取消关注其他用户
     * @param cancelPassiveUserId 被取消关注用户id
     * @return
     */
    @ResponseBody
    @RequestMapping("delCancelSubscribeUser")
    public HttpJsonResult cancelSubscribeUser(@ApiParam(required = true, name = "cancelPassiveUserId", value = "被取消关注用户id")@RequestParam long cancelPassiveUserId,
                                              @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId)
    {
        /*判断被取消关注用户id是否是登录用户本身*/
        if(cancelPassiveUserId == 0){
            return HttpJsonResult.FAIL("参数错误！");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        if(userId.equals(String.valueOf(cancelPassiveUserId))){
            return HttpJsonResult.FAIL("不允许取消关注自己！");
        }
        /*判断是否已有关注*/
        LoveSoundUserSubscribe loveSoundUserSubscribe = baseInfoService.getSubscribeServiceByUserId(userId, cancelPassiveUserId);
        if(loveSoundUserSubscribe == null){
            return HttpJsonResult.FAIL("参数错误！");
        }
        if(loveSoundUserSubscribe.getSubscribeStatus() == Integer.valueOf(Status.取消关注.getValue())){
            return HttpJsonResult.FAIL("已取消关注！");
        }
        loveSoundUserSubscribe.setSubscribeStatus(Integer.valueOf(Status.取消关注.getValue()));
        loveSoundUserSubscribe.setPassiveUserId(cancelPassiveUserId);
        loveSoundUserSubscribe.setUpdateTime(DateUtils.getNowDateTimestamp());
        loveSoundUserSubscribe.setUpdateUserId(Long.valueOf(userId));
        baseInfoService.saveOrUpdateWaterUserSubscribe(loveSoundUserSubscribe);
        return HttpJsonResult.SUCCESS("取消关注成功");
    }




    /**
     * 读取此用户的关注列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getSubscribeUserList", method = RequestMethod.GET)
    public HttpJsonResult getSubscribeUserList(@ApiParam(required = true, name = "pageSize", value = "页大小")@RequestParam int pageSize,
                                               @ApiParam(required = true, name = "pageNum", value = "第几页")@RequestParam int pageNum,
                                               @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId)
    {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        PageInfo pageInfo = baseInfoService.getSubscribeUserList(Long.valueOf(userId),new PageBean(pageSize,pageNum));
        return HttpJsonResult.SUCCESS(pageInfo);
    }


    /**
     * 读取此用户的粉丝列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getFansUserList", method = RequestMethod.GET)
    public HttpJsonResult getFansUserList(@ApiParam(required = true, name = "pageSize", value = "页大小")@RequestParam int pageSize,
                                          @ApiParam(required = true, name = "pageNum", value = "第几页")@RequestParam int pageNum,
                                          @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId)
    {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        PageInfo pageInfo = baseInfoService.getFansUserList(Long.valueOf(userId),new PageBean(pageSize,pageNum));
        return HttpJsonResult.SUCCESS(pageInfo);
    }



}
