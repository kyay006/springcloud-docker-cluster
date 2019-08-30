package com.liu.spring.springclient.controller.appointment;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.liu.spring.domain.appointment.LoveSoundAppointment;
import com.liu.spring.domain.appointment.LoveSoundAppointmentSign;
import com.liu.spring.domain.commoninfo.LoveSoundCharacter;
import com.liu.spring.domain.commoninfo.LoveSoundIndustry;
import com.liu.spring.domain.commoninfo.Status;
import com.liu.spring.domain.smallprogram.WxUser;
import com.liu.spring.service.appointment.AppointmentService;
import com.liu.spring.service.authorize.AuthorizeService;
import com.liu.spring.service.commoninfo.SelectInfoService;
import com.liu.spring.springclient.controller.BaseController;
import com.liu.util.blackkey.BlackListManager;
import com.liu.util.date.DateUtils;
import com.liu.util.image.ImgManageService;
import com.liu.util.image.WaterImgManage;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("smallProgram/appointment")
@Api(value = "smallProgram/appointment", description = "约见模块")
public class Appointment extends BaseController {


    @Autowired
    ImgManageService imgManageService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    AuthorizeService authorizeService;

    @Autowired
    SelectInfoService selectInfoService;

    @Autowired
    private RedisTemplateService2 redisTemplate;


    /**
     * 上传图片 - 这里是用户文章的图片,因为微信不支持多个文件上传，所以这里单独上传
     * @return
     */
    @ApiOperation(value = "上传图片 约见", httpMethod = "POST", notes = "上传图片 约见")
    @ResponseBody
    @RequestMapping(value = "appointmentPhotosUpload", method = RequestMethod.POST)
    public HttpJsonResult appointmentPhotosUpload(HttpServletRequest request,
                                              @ApiParam(required = true, name = "appointmentPhoto", value = "图片文件")
                                              @RequestParam(value ="appointmentPhoto") MultipartFile appointmentPhoto,
                                              @ApiParam(required = true, name = "openId", value = "openId")String openId) throws IOException {
        if(appointmentPhoto == null){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }

        //保存一个动态图片
        WaterImgManage skillImgManage = imgManageService.saveImg(Long.valueOf(userId), appointmentPhoto ,Status.AppointmentImage.toString(), Status.AppointmentImage.getValue());
        if(skillImgManage.getId() != null){
            return HttpJsonResult.SUCCESS(new Object[]{skillImgManage.getId(), skillImgManage.getUrlPath()});
        }else{
            return HttpJsonResult.FAIL("上传失败,请重试");
        }
    }


    /**
     * 保存约见信息
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "保存约见信息", httpMethod = "POST", notes = "保存约见信息")
    @RequestMapping(value = "saveAppointment", method = RequestMethod.POST)
    public HttpJsonResult saveAppointment(HttpServletRequest request,
                                 @ApiParam(required = true, name = "loveSoundAppointment", value = "loveSoundAppointment")LoveSoundAppointment loveSoundAppointment,
                                 @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId){

        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        //验证数据有效性
        if(StringUtils.isBlank(loveSoundAppointment.getTitle()) ||
           StringUtils.isBlank(loveSoundAppointment.getCondition()) ||
           StringUtils.isBlank(loveSoundAppointment.getFormality()) ||
           StringUtils.isBlank(loveSoundAppointment.getSelfIntroduction()) ||
           StringUtils.isBlank(loveSoundAppointment.getImgUrls()) || loveSoundAppointment.getImg1() == 0){
            return HttpJsonResult.FAIL("参数错误");
        }
        if(loveSoundAppointment.getCondition().length() < 5 ||
           loveSoundAppointment.getSelfIntroduction().length() < 5){
            return HttpJsonResult.FAIL("参数错误");
        }
        String temp = BlackListManager.manager.filter(loveSoundAppointment.getSelfIntroduction());
        if(!temp.equals(loveSoundAppointment.getSelfIntroduction()))
        {/*处理敏感词*/
            loveSoundAppointment.setSelfIntroduction(temp);
        }
        temp = BlackListManager.manager.filter(loveSoundAppointment.getFormality());
        if(!temp.equals(loveSoundAppointment.getFormality()))
        {/*处理敏感词*/
            loveSoundAppointment.setFormality(temp);
        }
        temp = BlackListManager.manager.filter(loveSoundAppointment.getCondition());
        if(!temp.equals(loveSoundAppointment.getCondition()))
        {/*处理敏感词*/
            loveSoundAppointment.setCondition(temp);
        }
        WxUser wxUserOld = authorizeService.getWxUserById(Long.valueOf(userId));
        loveSoundAppointment.setProvince(wxUserOld.getProvince());
        loveSoundAppointment.setCity(wxUserOld.getCity());
        loveSoundAppointment.setHasTop(false);
        loveSoundAppointment.setStatus(Status.ACTIVE.getValue());
        loveSoundAppointment.setCreateTime(DateUtils.getNowDateTimestamp());
        loveSoundAppointment.setCreateUserId(userId);
        loveSoundAppointment.setUpdateTime(loveSoundAppointment.getCreateTime());
        loveSoundAppointment.setUpdateUserId(userId);
        //存储到数据库
        appointmentService.saveAppointment(loveSoundAppointment);
        return HttpJsonResult.SUCCESS();
    }


    /**
     * 修改约见信息
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "修改约见信息", httpMethod = "POST", notes = "修改约见信息")
    @RequestMapping(value = "updateAppointment", method = RequestMethod.POST)
    public HttpJsonResult updateAppointment(HttpServletRequest request,
                                        @ApiParam(required = true, name = "loveSoundAppointment", value = "loveSoundAppointment")LoveSoundAppointment loveSoundAppointment,
                                        @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId){

        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        //验证数据有效性
        if(StringUtils.isBlank(loveSoundAppointment.getTitle()) ||
            StringUtils.isBlank(loveSoundAppointment.getCondition()) ||
            StringUtils.isBlank(loveSoundAppointment.getFormality()) ||
            StringUtils.isBlank(loveSoundAppointment.getSelfIntroduction()) ||
            StringUtils.isBlank(loveSoundAppointment.getImgUrls()) || loveSoundAppointment.getImg1() == 0){
            return HttpJsonResult.FAIL("参数错误");
        }
        if(loveSoundAppointment.getCondition().length() < 5 ||
                loveSoundAppointment.getSelfIntroduction().length() < 5){
            return HttpJsonResult.FAIL("参数错误");
        }
        String temp = BlackListManager.manager.filter(loveSoundAppointment.getSelfIntroduction());
        if(!temp.equals(loveSoundAppointment.getSelfIntroduction()))
        {/*处理敏感词*/
            loveSoundAppointment.setSelfIntroduction(temp);
        }
        temp = BlackListManager.manager.filter(loveSoundAppointment.getFormality());
        if(!temp.equals(loveSoundAppointment.getFormality()))
        {/*处理敏感词*/
            loveSoundAppointment.setFormality(temp);
        }
        temp = BlackListManager.manager.filter(loveSoundAppointment.getCondition());
        if(!temp.equals(loveSoundAppointment.getCondition()))
        {/*处理敏感词*/
            loveSoundAppointment.setCondition(temp);
        }
        WxUser wxUserOld = authorizeService.getWxUserById(Long.valueOf(userId));
        loveSoundAppointment.setProvince(wxUserOld.getProvince());
        loveSoundAppointment.setCity(wxUserOld.getCity());
        loveSoundAppointment.setCreateUserId(userId);//这里不是为了修改，是为了sql去匹配是这个人发的
        loveSoundAppointment.setHasTop(false);
        loveSoundAppointment.setUpdateTime(DateUtils.getNowDateTimestamp());
        loveSoundAppointment.setUpdateUserId(userId);
        //修改到数据库
        appointmentService.updateAppointment(loveSoundAppointment);
        return HttpJsonResult.SUCCESS();
    }

    /**
     * 删除约见信息
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "删除约见信息", httpMethod = "POST", notes = "删除约见信息")
    @RequestMapping(value = "delAppointment", method = RequestMethod.POST)
    public HttpJsonResult delAppointment(HttpServletRequest request,
                                        @ApiParam(required = true, name = "loveSoundAppointment", value = "loveSoundAppointment")LoveSoundAppointment loveSoundAppointment,
                                        @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId){

        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        if(loveSoundAppointment.getId() == 0){
            return HttpJsonResult.FAIL("参数错误");
        }
        LoveSoundAppointment loveSoundArticleOld = appointmentService.getAppointmentById(loveSoundAppointment.getId());
        if(!loveSoundArticleOld.getCreateUserId().equals(userId)){
            return HttpJsonResult.FAIL("这条邀约不属于你,不可删除");
        }
        if(Status.DELETE.getValue().equals(loveSoundAppointment.getStatus()) || Status.DISABLED.getValue().equals(loveSoundAppointment.getStatus())){

        }else{
            return HttpJsonResult.FAIL("参数错误");
        }

//        loveSoundAppointment.setStatus(Status.DELETE.getValue());
        loveSoundAppointment.setCreateUserId(userId);//这里不是为了修改，是为了sql去匹配是这个人发的
        loveSoundAppointment.setUpdateTime(DateUtils.getNowDateTimestamp());
        loveSoundAppointment.setUpdateUserId(userId);
        //修改到数据库
        appointmentService.updateAppointment(loveSoundAppointment);
        return HttpJsonResult.SUCCESS();
    }




    /**
     * 获取约见信息列表 分页
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "获取约见信息列表 分页", httpMethod = "GET", notes = "获取约见信息列表 分页")
    @RequestMapping(value = "getAppointmentList", method = RequestMethod.GET)
    public HttpJsonResult getAppointmentList(HttpServletRequest request,
                                             @ApiParam(required = true, name = "pageSize", value = "页大小")@RequestParam int pageSize,
                                             @ApiParam(required = true, name = "pageNum", value = "第几页")@RequestParam int pageNum,
                                             @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId,
                                             @ApiParam(required = false, name = "selfUserId", value = "是否只看自己的")@RequestParam String selfUserId,
                                             @ApiParam(required = false, name = "selfUserMessage", value = "是否只看自己报名的")@RequestParam Boolean selfUserMessage,
                                             @ApiParam(required = false, name = "gender", value = "1：男、2：女")@RequestParam Integer gender) {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        //这里控制了下，如果不是自己，就置为空
        if(!userId.equals(selfUserId)){
            selfUserId = null;
        }
        PageInfo pageInfo = appointmentService.getAppointmentList(new PageBean(pageSize,pageNum), Status.ACTIVE.getValue(), gender, selfUserId, selfUserMessage);
        return HttpJsonResult.SUCCESS(pageInfo);
    }


    /**
     * 根据用户id获取约见信息列表 分页
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "根据用户id获取约见信息列表 分页", httpMethod = "GET", notes = "根据用户id获取约见信息列表 分页")
    @RequestMapping(value = "getAppointmentListByUserId", method = RequestMethod.GET)
    public HttpJsonResult getAppointmentListByUserId(HttpServletRequest request,
                                         @ApiParam(required = true, name = "pageSize", value = "页大小")@RequestParam int pageSize,
                                         @ApiParam(required = true, name = "pageNum", value = "第几页")@RequestParam int pageNum,
                                         @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId,
                                         @ApiParam(required = true, name = "selectUserId", value = "被查看的用户id")@RequestParam String selectUserId) {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        PageInfo pageInfo = appointmentService.getAppointmentListByUserId(new PageBean(pageSize,pageNum), Status.ACTIVE.getValue(), selectUserId);
        return HttpJsonResult.SUCCESS(pageInfo);
    }



    /**
     * 获取约见未读总数量
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "获取约见未读总数量", httpMethod = "GET")
    @RequestMapping(value = "getAppointmentWeiDuCount", method = RequestMethod.GET)
    public HttpJsonResult getAppointmentWeiDuCount(HttpServletRequest request,
                                               @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId) {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        Integer getArticleWeiDuCount = appointmentService.getArticleWeiDuCount(userId, Status.ACTIVE.getValue());
        return HttpJsonResult.SUCCESS(getArticleWeiDuCount);
    }


    /**
     * 根据id获取约见信息
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "根据id获取约见信息", httpMethod = "GET", notes = "根据id获取约见信息")
    @RequestMapping(value = "getAppointmentById", method = RequestMethod.GET)
    public HttpJsonResult getAppointmentById(HttpServletRequest request,
                                         @ApiParam(required = true, name = "articleId", value = "articleId")@RequestParam long articleId,
                                         @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId) {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }

        JSONObject jsonObject = new JSONObject();
        LoveSoundAppointment loveSoundArticle = appointmentService.getAppointmentById(articleId);


        //如果这个约见是自己创建的，就把留言变为已读
        if(userId.equals(String.valueOf(loveSoundArticle.getCreateUserId()))){
            appointmentService.updateReadStatus(loveSoundArticle.getId(), Status.HaveRead.getValue());
        }


        convIndustryValues(loveSoundArticle.getWxUser());
        convCharacterValues(loveSoundArticle.getWxUser());

        LoveSoundAppointmentSign loveSoundAppointmentSign = appointmentService.getMessageId(articleId, userId);
        jsonObject.put("loveSoundAppointmentSign", 0);
        if(loveSoundAppointmentSign != null){
            jsonObject.put("loveSoundAppointmentSign", 1);
        }
//        /*查询用户是否举报过该文章*/
//        if(!(userId).equals((String.valueOf(loveSoundArticle.getCreateUserId()))))
//        {
//            LoveSoundReport waterReport = appointmentService.getReportByArticleId(articleId, userId);
//            if(waterReport != null)
//            {
//                jsonObject.put("isReport", 1);/*举报过*/
//            }else{
//                jsonObject.put("isReport", 0);
//            }
//        }

        //显示这个约见总的报名数,可以在下面的获取接口显示

        jsonObject.put("loveSoundArticle", loveSoundArticle);
        return HttpJsonResult.SUCCESS(jsonObject);
    }


    /**
     * 举报文章
     * @ reportReason 举报原因
     * @ dataId 文章id
     * @ reportUserId 文章创建人id,是被举报者
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value = "save/report/article",method = RequestMethod.POST)
//    @ApiOperation(value = "举报文章", httpMethod = "POST", notes = "举报文章")
//    public HttpJsonResult reportArticle(LoveSoundReport waterReport,
//                                        @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId)
//    {
//        String userId = getUserIdByRedis(redisTemplate, openId);
//        if(StringUtils.isBlank(userId)){
//            return HttpJsonResult.FAIL("参数错误");
//        }
//        if((userId).equals(waterReport.getReportUserId()+""))
//        {
//            return HttpJsonResult.FAIL("不允许举报自己！");
//        }
//
//        /*查询以前是否举报过*/
//        LoveSoundReport waterReportOld = appointmentService.getReportByArticleId(Long.valueOf(waterReport.getDataId()), userId);
//        if(waterReportOld != null)
//        {
//            return HttpJsonResult.FAIL("已举报过");
//        }
//
//        /*然后判断今天之内的举报次数是否超过十次,防止接口被用户乱用*/
//        Integer dayReportCount = appointmentService.getdayReportCountByUserId(Status.ArticleReport.getValue() ,userId);
//        if(dayReportCount >= Integer.parseInt(Status.ArticleReportCount.getValue()))
//        {
//            return HttpJsonResult.FAIL("当天举报次数已达上限！");
//        }
//        waterReport.setFunctionId(Integer.parseInt(Status.ArticleReport.getValue()));
//        waterReport.setUpdateUserId(Long.valueOf(userId));
//        waterReport.setUpdateTime(DateUtils.getNowDateTimestamp());
//        waterReport.setCreateTime(waterReport.getUpdateTime());
//        waterReport.setCreateUserId(waterReport.getUpdateUserId());
//        appointmentService.reportArticle(waterReport);
//        return HttpJsonResult.SUCCESS();
//    }





    /**
     * 读取某个约见的报名列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getMessageListByAppointmentId", method = RequestMethod.GET)
    @ApiOperation(value = "读取某个约见的报名列表", httpMethod = "GET", notes = "读取某个约见的报名列表")
    public HttpJsonResult getMessageListByAppointmentId(@ApiParam(required = true, name = "messageUserId", value = "给这个用户报的名")Long messageUserId,
                                                    @ApiParam(required = true, name = "appointmentId", value = "约见id")Long appointmentId,
                                                    @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId)
    {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        List<LoveSoundAppointmentSign> waterMessageBoards = appointmentService.messageList(messageUserId, appointmentId);
        return HttpJsonResult.SUCCESS(waterMessageBoards);
    }





    /**
     * 报名描述 保存
     * messageUserId 给这个用户留的言
     * content 留言的内容
     * 这里已经用路径控制登录了，不用做登录判断了
     * */
    @ResponseBody
    @RequestMapping(value = "save/message", method = RequestMethod.POST)
    @ApiOperation(value = "报名描述 保存", httpMethod = "POST", notes = "报名描述 保存")
    public HttpJsonResult saveMessage(LoveSoundAppointmentSign loveSoundAppointmentSign,
                                      @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId)
    {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        if(StringUtils.isBlank(loveSoundAppointmentSign.getContent()) || StringUtils.isBlank(loveSoundAppointmentSign.getMessageUserId())|| StringUtils.isBlank(loveSoundAppointmentSign.getAppointmentId()))
        {
            return HttpJsonResult.FAIL("参数错误！");
        }
        if(loveSoundAppointmentSign.getContent().length() > 512)
        {
            return HttpJsonResult.FAIL("报名描述超过指定长度！");
        }

        loveSoundAppointmentSign.setReadStatus(Integer.valueOf(Status.UnRead.getValue()));
        loveSoundAppointmentSign.setStatus(Status.ACTIVE.getValue());
        loveSoundAppointmentSign.setCreateTime(DateUtils.getNowDateTimestamp());
        loveSoundAppointmentSign.setCreateUserId(Long.valueOf(userId));
        appointmentService.saveMessage(loveSoundAppointmentSign);

        return HttpJsonResult.SUCCESS();
    }

    /**
     * 删除一条报名
     * */
    @ResponseBody
    @RequestMapping(value = "del/message", method = RequestMethod.POST)
    public HttpJsonResult delMessage(String id,
                                     @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId)
    {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        if(StringUtils.isBlank(id))
        {
            return HttpJsonResult.FAIL("参数错误！");
        }

        appointmentService.delMessage(id, userId);
        return HttpJsonResult.SUCCESS();
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
     * @param wxUser
     */
    private void convIndustryValues(WxUser wxUser)
    {
        if(wxUser != null && StringUtils.isNotBlank(wxUser.getUserIndustry())){
            //从redis读出缓存的中文
            Map<String,String> comTemp = getRedisValues("industryValues");
            String userIndustryTemp = null;
            String[] userIndustryArrTemp = null;
            StringBuilder userIndustrysfTemp = new StringBuilder(32);
            userIndustryTemp = wxUser.getUserIndustry();
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
                wxUser.setUserIndustry(userIndustrysfTemp.toString());
            }
        }
    }

    /**
     * 将性格转换成中文
     * @param wxUser
     */
    private void convCharacterValues(WxUser wxUser)
    {
        if(wxUser != null && StringUtils.isNotBlank(wxUser.getUserCharacter())){
            Map<String,String> comTemp = getRedisValues("characterValues");
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
        }
    }



}
