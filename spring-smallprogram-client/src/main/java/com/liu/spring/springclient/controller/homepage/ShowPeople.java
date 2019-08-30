package com.liu.spring.springclient.controller.homepage;

import com.github.pagehelper.PageInfo;
import com.liu.spring.domain.commoninfo.LoveSoundCharacter;
import com.liu.spring.domain.commoninfo.LoveSoundIndustry;
import com.liu.spring.domain.commoninfo.Status;
import com.liu.spring.domain.log.LoveSoundLogReadUser;
import com.liu.spring.domain.log.LoveSoundLogSearch;
import com.liu.spring.domain.myinfo.LoveSoundUserSubscribe;
import com.liu.spring.domain.smallprogram.WxUser;
import com.liu.spring.service.authorize.AuthorizeService;
import com.liu.spring.service.commoninfo.SelectInfoService;
import com.liu.spring.service.log.LogLoginService;
import com.liu.spring.service.myinfo.BaseInfoService;
import com.liu.spring.service.showpeople.ShowPeopleService;
import com.liu.spring.springclient.controller.BaseController;
import com.liu.util.date.DateUtils;
import com.liu.util.image.ImgManageService;
import com.liu.util.image.WaterImgManage;
import com.liu.util.ip.IPUtil;
import com.liu.util.mysql.PageBean;
import com.liu.util.object.HttpJsonResult;
import com.liu.util.redis.RedisTemplateService2;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("smallProgram/showPeople")
@Api(value = "smallProgram/showPeople", description = "显示人信息列表模块")
public class ShowPeople extends BaseController {


    @Autowired
    ShowPeopleService showPeopleService;
    @Autowired
    SelectInfoService selectInfoService;
    @Autowired
    AuthorizeService authorizeService;
    @Autowired
    BaseInfoService baseInfoService;
    @Autowired
    ImgManageService imgManageService;
    @Autowired
    private RedisTemplateService2 redisTemplate;

    @Autowired
    LogLoginService logLoginService;

    /**
     * 主页 第一列表页
     * 查询最基本信息列表 分页
     * @return
     */
    @ApiOperation(value = "主页 第一列表页", httpMethod = "GET", notes = "查询最基本信息列表")
    @ResponseBody
    @RequestMapping(value = "getBaseUserInfoList", method = RequestMethod.GET)
    public HttpJsonResult getBaseUserInfoList(HttpServletRequest request,
                                              @ApiParam(required = true, name = "pageSize", value = "页大小")@RequestParam int pageSize,
                                              @ApiParam(required = true, name = "pageNum", value = "第几页")@RequestParam int pageNum,
                                              @ApiParam(required = true, name = "gender", value = "性别 0：未知、1：男、2：女")@RequestParam int gender,
                                              @ApiParam(required = true, name = "openId", value = "openId")String openId) {
        if(StringUtils.isBlank(openId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        if(gender == 1){
            gender = 2;
        }else if(gender == 2){
            gender = 1;
        }else if(gender != 0){
            return HttpJsonResult.FAIL("参数错误");
        }
        PageInfo pageInfo = showPeopleService.getBaseUserInfoList(gender,new PageBean(pageSize,pageNum));
        return HttpJsonResult.SUCCESS(pageInfo);
    }




    /**
     * 主页 第二列表页
     * 查询精准匹配列表 分页
     * 年龄 userAgeStart 20数值
     * 年龄 userAgeEnd 20数值
     * 身高 userHeightStart 150数值
     * 身高 userHeightEnd 150数值
     * 行业 userIndustry 1,2,3 字符串
     * 最低学历 userEducation 1 数值
     * 性格 userCharacter 1,2,3 字符串
     * 体重 userWeightStart 150数值
     * 体重 userWeightEnd 150数值
     * 婚否 userMarriage 0或者1
     * 性别 gender 1：男、2：女，默认取反
     * @return
     */
    @ApiOperation(value = "主页 第二列表页", httpMethod = "GET", notes = "查询精准匹配列表")
    @ResponseBody
    @RequestMapping(value = "getMatchUserInfoList", method = RequestMethod.GET)
    public HttpJsonResult getMatchUserInfoList(HttpServletRequest request,
                                              @ApiParam(required = true, name = "pageSize", value = "页大小")@RequestParam int pageSize,
                                              @ApiParam(required = true, name = "pageNum", value = "第几页")@RequestParam int pageNum,
                                              @ApiParam(required = true, name = "param", value = "所有参数集合")@RequestParam HashMap<String,Object> param,
                                              @ApiParam(required = true, name = "openId", value = "openId")String openId) {
        if(StringUtils.isBlank(openId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }

        //如果年龄不为空，则算年龄差数
        Object userAgeStart = param.get("userAgeStart");
        Object userAgeEnd = param.get("userAgeEnd");
        Object userIndustry = param.get("userIndustry");
        Object userCharacter = param.get("userCharacter");
        if(StringUtils.isNotBlank(userAgeStart)){
            param.put("userAgeStart", DateUtils.yearsBetween(DateUtils.parserShortDate(userAgeStart.toString()), new Date()));
        }
        if(StringUtils.isNotBlank(userAgeEnd)){
            param.put("userAgeEnd", DateUtils.yearsBetween(DateUtils.parserShortDate(userAgeEnd.toString()), new Date()));
        }
        if(StringUtils.isNotBlank(userIndustry)){
            String [] userIndustryArr = userIndustry.toString().split(",");
            StringBuilder sf = new StringBuilder(32);
            sf.append("(");
            for (int i = 0,iLen = userIndustryArr.length; i < iLen; i++) {
                if(sf.length() > 1){
                    sf.append("or ");
                }
                sf.append(" userIndustry like '%=").append(userIndustryArr[i]).append("=%'");
            }
            sf.append(")");
            param.put("userIndustry", sf.toString());
        }
        if(StringUtils.isNotBlank(userCharacter)){
            String [] userCharacterArr = userCharacter.toString().split(",");
            StringBuilder sf = new StringBuilder(32);
            sf.append("(");
            for (int i = 0,iLen = userCharacterArr.length; i < iLen; i++) {
                if(sf.length() > 1){
                    sf.append("or ");
                }
                sf.append(" userCharacter like '%=").append(userCharacterArr[i]).append("=%'");
            }
            sf.append(")");
            param.put("userCharacter", sf.toString());
        }

        boolean save = false;
        LoveSoundLogSearch loveSoundLogSearch = new LoveSoundLogSearch();
        if(StringUtils.isNotBlank(userAgeStart)){
            loveSoundLogSearch.setUserAgeStart(Integer.valueOf(param.get("userAgeStart").toString()));
            save = true;
        }
        if(StringUtils.isNotBlank(userAgeEnd)){
            loveSoundLogSearch.setUserAgeEnd(Integer.valueOf(param.get("userAgeEnd").toString()));
            save = true;
        }
        if(StringUtils.isNotBlank(param.get("userHeightStart"))){
            loveSoundLogSearch.setUserHeightStart(Integer.valueOf(param.get("userHeightStart").toString()));
            save = true;
        }else{
            param.put("userHeightStart", null);
        }
        if(StringUtils.isNotBlank(param.get("userHeightEnd"))){
            loveSoundLogSearch.setUserHeightEnd(Integer.valueOf(param.get("userHeightEnd").toString()));
            save = true;
        }else{
            param.put("userHeightEnd", null);
        }
        if(StringUtils.isNotBlank(userIndustry)){
            loveSoundLogSearch.setUserIndustry(userIndustry.toString());
            save = true;
        }
        if(StringUtils.isNotBlank(param.get("userEducation"))){
            loveSoundLogSearch.setUserEducation(Integer.valueOf(param.get("userEducation").toString()));
            save = true;
        }else{
            param.put("userEducation", null);
        }
        if(StringUtils.isNotBlank(userCharacter)){
            loveSoundLogSearch.setUserCharacter(userCharacter.toString());
            save = true;
        }
        if(StringUtils.isNotBlank(param.get("userWeightStart"))){
            loveSoundLogSearch.setUserWeightStart(Integer.valueOf(param.get("userWeightStart").toString()));
            save = true;
        }else{
            param.put("userWeightStart", null);
        }
        if(StringUtils.isNotBlank(param.get("userWeightEnd"))){
            loveSoundLogSearch.setUserWeightEnd(Integer.valueOf(param.get("userWeightEnd").toString()));
            save = true;
        }else{
            param.put("userWeightEnd", null);
        }
        if(StringUtils.isNotBlank(param.get("userMarriage"))){
            loveSoundLogSearch.setUserMarriage(Integer.valueOf(param.get("userMarriage").toString()));
            save = true;
        }else{
            param.put("userMarriage", null);
        }

        //如果参数都为空，就不存日志
        if(save){
            if(StringUtils.isNotBlank(param.get("gender"))){
                loveSoundLogSearch.setGender(Integer.valueOf(param.get("gender").toString()));
            }
            loveSoundLogSearch.setUserId(Long.valueOf(userId));
            loveSoundLogSearch.setCreateTime(DateUtils.getNowDateTimestamp());
            try {
                logLoginService.saveLogSearch(loveSoundLogSearch);
            } catch (Exception e) {
            }
        }
        PageInfo pageInfo = showPeopleService.getMatchUserInfoList(param,new PageBean(pageSize,pageNum));
        convIndustryValues(pageInfo);
        convCharacterValues(pageInfo);
        return HttpJsonResult.SUCCESS(pageInfo);
    }


    /**
     * 从redis获取行业数据
     * @return
     */
    private Map<String,String> getRedisValues(String redisName)
    {
        Object industryValues = redisTemplate.get(redisName);
        if(industryValues == null){
            //去数据库查一下
            Map<String,String> redisVal = new HashMap<>(16);
            if("industryValues".equals(redisName)){
                List<LoveSoundIndustry> temp =  selectInfoService.getSelectIndustryList();
                for (int i = 0, iLen = temp.size(); i < iLen; i++) {
                    redisVal.put(temp.get(i).getId().toString(), temp.get(i).getIndustryName());
                }
            }else if("characterValues".equals(redisName))
            {
                List<LoveSoundCharacter> temp =  selectInfoService.getSelectCharacterList();
                for (int i = 0, iLen = temp.size(); i < iLen; i++) {
                    redisVal.put(temp.get(i).getId().toString(), temp.get(i).getCharacterName());
                }
            }
            redisTemplate.set(redisName, redisVal, 60 * 60 * 24 * 7L);//7天
            industryValues = redisVal;
        }
        return (Map<String,String>)industryValues;
    }

    /**
     * 将行业转换成中文
     * @param pageInfo
     */
    private void convIndustryValues(PageInfo pageInfo)
    {
        if(pageInfo.getList() != null && pageInfo.getList().size() > 0){
            //从redis读出缓存的中文
            Map<String,String> comTemp = getRedisValues("industryValues");
            String userIndustryTemp = null;
            String[] userIndustryArrTemp = null;
            StringBuilder userIndustrysfTemp = new StringBuilder(32);
            for (int i = 0, iLen = pageInfo.getList().size(); i < iLen; i++)
            {
                userIndustryTemp = ((WxUser)(pageInfo.getList().get(i))).getUserIndustry();
                userIndustrysfTemp.setLength(0);
                if(StringUtils.isNotBlank(userIndustryTemp)){
                    userIndustryArrTemp = userIndustryTemp.split(",");
                    for (int j = 0, jLen = userIndustryArrTemp.length; j < jLen; j++)
                    {
                        if(StringUtils.isNotBlank(userIndustrysfTemp)){
                            userIndustrysfTemp.append(",");
                        }
                        userIndustrysfTemp.append(comTemp.get(userIndustryArrTemp[j].split("=")[1]));
                    }
                    ((WxUser)(pageInfo.getList().get(i))).setUserIndustry(userIndustrysfTemp.toString());
                }
            }
        }
    }

    /**
     * 将性格转换成中文
     * @param pageInfo
     */
    private void convCharacterValues(PageInfo pageInfo)
    {
        if(pageInfo.getList() != null && pageInfo.getList().size() > 0){
            Map<String,String> comTemp = getRedisValues("characterValues");
            String userCharacterTemp = null;
            String[] userCharacterArrTemp = null;
            StringBuilder userCharactersfTemp = new StringBuilder(32);
            for (int i = 0, iLen = pageInfo.getList().size(); i < iLen; i++)
            {
                userCharacterTemp = ((WxUser)(pageInfo.getList().get(i))).getUserCharacter();
                userCharactersfTemp.setLength(0);
                if(StringUtils.isNotBlank(userCharacterTemp)){
                    userCharacterArrTemp = userCharacterTemp.split(",");
                    for (int j = 0, jLen = userCharacterArrTemp.length; j < jLen; j++)
                    {
                        if(StringUtils.isNotBlank(userCharactersfTemp)){
                            userCharactersfTemp.append(",");
                        }
                        userCharactersfTemp.append(comTemp.get(userCharacterArrTemp[j].split("=")[1]));
                    }
                    ((WxUser)(pageInfo.getList().get(i))).setUserCharacter(userCharactersfTemp.toString());
                }
            }
        }
    }



    /**
     * 用户主页 加载关注数量和粉丝数量,读取此用户有多少条未读留言
     * @param request
     * @return
     */
    @ApiOperation(value = "用户主页 加载关注数量和粉丝数量", httpMethod = "GET", notes = "用户主页 加载关注数量和粉丝数量")
    @ResponseBody
    @RequestMapping(value = "getUserSubscribeInfo", method = RequestMethod.GET)
    public HttpJsonResult getUserSubscribeInfo(HttpServletRequest request,@ApiParam(required = true, name = "openId", value = "openId")String openId) {
        if(StringUtils.isBlank(openId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        int fansCount = baseInfoService.getUserFansCount(Long.valueOf(userId));
        int subscribeCount = baseInfoService.getUserSubscribeCount(Long.valueOf(userId));
        int unReadBoardCount = baseInfoService.getUnReadBoardByUserId(Long.valueOf(userId));
        return HttpJsonResult.SUCCESS(new HashMap<String, Integer>(){{
            put("fansCount", fansCount);
            put("subscribeCount", subscribeCount);
            put("unReadBoardCount", unReadBoardCount);
        }});
    }


    /*********************************************下面是用户详情展示页 start******************************************************/

    /**
     * 用户详情页 获取用户相册的图片地址 列表 不分页
     * @param request
     * @return
     */
    @ApiOperation(value = "用户详情页 获取用户相册的图片地址 列表 不分页", httpMethod = "GET", notes = "用户详情页 获取用户相册的图片地址 列表 不分页")
    @ResponseBody
    @RequestMapping(value = "getUserPhotoListByShow", method = RequestMethod.GET)
    public HttpJsonResult getUserPhotoListByShow(HttpServletRequest request,
                                           @ApiParam(required = true, name = "userId", value = "要查询的用户id")String userId,
                                           @ApiParam(required = true, name = "openId", value = "openId")String openId) {
        if(StringUtils.isBlank(openId) || StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userIdLogin = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userIdLogin)){
            return HttpJsonResult.FAIL("参数错误");
        }
        List<WaterImgManage> waterImgManageList = imgManageService.getUserPhotoList(userId , Status.PhotoImage.getValue());
        return HttpJsonResult.SUCCESS(waterImgManageList);
    }



    /**
     * 用户详情页 获取用户信息
     * @param request
     * @return
     */
    @ApiOperation(value = "用户详情页 获取用户信息", httpMethod = "GET", notes = "用户详情页 获取用户信息")
    @ResponseBody
    @RequestMapping(value = "getUserInfoDetailByShow", method = RequestMethod.GET)
    public HttpJsonResult getUserInfoDetailByShow(HttpServletRequest request,
                                                 @ApiParam(required = true, name = "userId", value = "要查询的用户id")long userId,
                                                 @ApiParam(required = true, name = "openId", value = "openId")String openId) {
        if(StringUtils.isBlank(openId) || userId == 0){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userIdLogin = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userIdLogin)){
            return HttpJsonResult.FAIL("参数错误");
        }
        WxUser wxUser = authorizeService.getWxUserById(userId);
        if(wxUser == null){
            return HttpJsonResult.FAIL("参数错误");
        }
        LoveSoundLogReadUser loveSoundLogReadUser = new LoveSoundLogReadUser();
        loveSoundLogReadUser.setReadUserId(userId);
        loveSoundLogReadUser.setCreateTime(DateUtils.getNowDateTimestamp());
        loveSoundLogReadUser.setCreateUserId(Long.valueOf(userIdLogin));
        loveSoundLogReadUser.setCreateUserIp(IPUtil.getRemoteAddr(request));
        try {
            logLoginService.saveLogReadUser(loveSoundLogReadUser);
        } catch (Exception e) {
        }
        //将行业从id转成中文
        Map<String,String> comTemp = getRedisValues("industryValues");
        String userIndustryTemp = null;
        String[] userIndustryArrTemp = null;
        StringBuilder userIndustrysfTemp = new StringBuilder(32);
        userIndustryTemp = wxUser.getUserIndustry();
        if(StringUtils.isNotBlank(userIndustryTemp)){
            userIndustryArrTemp = userIndustryTemp.split(",");
            for (int j = 0, jLen = userIndustryArrTemp.length; j < jLen; j++)
            {
                if(StringUtils.isNotBlank(userIndustrysfTemp)){
                    userIndustrysfTemp.append(",");
                }
                userIndustrysfTemp.append(comTemp.get(userIndustryArrTemp[j].split("=")[1]));
            }
            wxUser.setUserIndustry(userIndustrysfTemp.toString());
        }

        //将性格从id转成中文
        comTemp = getRedisValues("characterValues");
        String userCharacterTemp = null;
        String[] userCharacterArrTemp = null;
        StringBuilder userCharactersfTemp = new StringBuilder(32);

        userCharacterTemp = wxUser.getUserCharacter();
        userCharactersfTemp.setLength(0);
        if(StringUtils.isNotBlank(userCharacterTemp)){
            userCharacterArrTemp = userCharacterTemp.split(",");
            for (int j = 0, jLen = userCharacterArrTemp.length; j < jLen; j++)
            {
                if(StringUtils.isNotBlank(userCharactersfTemp)){
                    userCharactersfTemp.append(",");
                }
                userCharactersfTemp.append(comTemp.get(userCharacterArrTemp[j].split("=")[1]));
            }
            wxUser.setUserCharacter(userCharactersfTemp.toString());
        }

        return HttpJsonResult.SUCCESS(new HashMap<String, Object>(){{
            put("userNickname", wxUser.getUserNickname());
            put("gender", wxUser.getGender());
            put("userAvatarurl", wxUser.getUserAvatarurl());
            put("loveWords", wxUser.getLoveWords());
            put("userAge", wxUser.getUserAge());
            put("userHeight", wxUser.getUserHeight());
            put("userWeight", wxUser.getUserWeight());
            put("userEducation", wxUser.getUserEducation());
            put("userMarriage", wxUser.getUserMarriage());
            put("userIndustry", wxUser.getUserIndustry());
            put("userOccupation", wxUser.getUserOccupation());
            put("userCharacter", wxUser.getUserCharacter());
            put("userHobby", wxUser.getUserHobby());
            put("createDate", wxUser.getCreateDate());
            put("email", wxUser.getEmail());
        }});
    }

    /**
     * 用户详情页 获取用户 最新的一条动态
     * @param request
     * @return
     */
    @ApiOperation(value = "用户详情页 获取用户 最新的一条动态", httpMethod = "GET", notes = "用户详情页 获取用户 最新的一条动态")
    @ResponseBody
    @RequestMapping(value = "getUserLastOneDynamicByShow", method = RequestMethod.GET)
    public HttpJsonResult getUserLastOneDynamicByShow(HttpServletRequest request,
                                                  @ApiParam(required = true, name = "userId", value = "要查询的用户id")long userId,
                                                  @ApiParam(required = true, name = "openId", value = "openId")String openId) {
        if(StringUtils.isBlank(openId) || userId == 0){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userIdLogin = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userIdLogin)){
            return HttpJsonResult.FAIL("参数错误");
        }

        PageInfo pageInfo = baseInfoService.getUserDynamicList(userId,new PageBean(1,1));
        return HttpJsonResult.SUCCESS(pageInfo.getList());
    }


    /**
     * 用户详情页 判断用户是否关注了此用户
     * @param request
     * @return
     */
    @ApiOperation(value = "用户详情页 判断用户是否关注了此用户", httpMethod = "GET", notes = "用户详情页 判断用户是否关注了此用户")
    @ResponseBody
    @RequestMapping(value = "getUserSubscribeUserStatus", method = RequestMethod.GET)
    public HttpJsonResult getUserSubscribeUserStatus(HttpServletRequest request,
                                                      @ApiParam(required = true, name = "passiveUserId", value = "要查询的用户id")long passiveUserId,
                                                      @ApiParam(required = true, name = "openId", value = "openId")String openId) {
        if(StringUtils.isBlank(openId) || passiveUserId == 0){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userIdLogin = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userIdLogin)){
            return HttpJsonResult.FAIL("参数错误");
        }
        LoveSoundUserSubscribe loveSoundUserSubscribe = baseInfoService.getSubscribeServiceByUserId(userIdLogin, passiveUserId);
        return HttpJsonResult.SUCCESS(loveSoundUserSubscribe != null && loveSoundUserSubscribe.getSubscribeStatus() == Integer.valueOf(Status.关注.getValue()));
    }

    /*********************************************上面是用户详情展示页 end******************************************************/

}
