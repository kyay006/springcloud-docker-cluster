package com.liu.spring.springclient.controller.article;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.liu.spring.domain.article.LoveSoundArticle;
import com.liu.spring.domain.article.WaterArticleRead;
import com.liu.spring.domain.article.WaterArticleZan;
import com.liu.spring.domain.article.WaterMessageBoard;
import com.liu.spring.domain.commoninfo.LoveSoundCharacter;
import com.liu.spring.domain.commoninfo.LoveSoundIndustry;
import com.liu.spring.domain.commoninfo.LoveSoundReport;
import com.liu.spring.domain.commoninfo.Status;
import com.liu.spring.domain.smallprogram.WxUser;
import com.liu.spring.service.article.ArticleService;
import com.liu.spring.service.authorize.AuthorizeService;
import com.liu.spring.service.commoninfo.SelectInfoService;
import com.liu.spring.springclient.controller.BaseController;
import com.liu.util.blackkey.BlackListManager;
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
@RequestMapping("smallProgram/article")
@Api(value = "smallProgram/article", description = "文章模块")
public class Article extends BaseController {


    @Autowired
    ImgManageService imgManageService;

    @Autowired
    ArticleService articleService;

    @Autowired
    AuthorizeService authorizeService;

    @Autowired
    SelectInfoService selectInfoService;

    @Autowired
//    private RedisTemplateService2 redisTemplate;
    private RedisConfig redisTemplate;


    /**
     * 上传图片 - 这里是用户文章的图片,因为微信不支持多个文件上传，所以这里单独上传
     * @return
     */
    @ApiOperation(value = "上传图片 用户文章", httpMethod = "POST", notes = "上传图片 用户文章")
    @ResponseBody
    @RequestMapping(value = "articlePhotosUpload", method = RequestMethod.POST)
    public HttpJsonResult articlePhotosUpload(HttpServletRequest request,
                                              @ApiParam(required = true, name = "articlePhoto", value = "图片文件")
                                              @RequestParam(value ="articlePhoto") MultipartFile articlePhoto,
                                              @ApiParam(required = true, name = "openId", value = "openId")String openId) throws IOException {
        if(articlePhoto == null){
            return HttpJsonResult.FAIL("参数错误");
        }
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }

        //保存一个动态图片
        WaterImgManage skillImgManage = imgManageService.saveImg(Long.valueOf(userId), articlePhoto ,Status.ArticleImage.toString(), Status.ArticleImage.getValue());
        if(skillImgManage.getId() != null){
            return HttpJsonResult.SUCCESS(new Object[]{skillImgManage.getId(), skillImgManage.getUrlPath()});
        }else{
            return HttpJsonResult.FAIL("上传失败,请重试");
        }
    }


    /**
     * 保存文章信息
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "保存文章信息", httpMethod = "POST", notes = "保存文章信息")
    @RequestMapping(value = "saveArticle", method = RequestMethod.POST)
    public HttpJsonResult saveArticle(HttpServletRequest request,
                                 @ApiParam(required = true, name = "LoveSoundArticle", value = "LoveSoundArticle")LoveSoundArticle loveSoundArticle,
                                 @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId){

        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        //验证数据有效性
        if(StringUtils.isBlank(loveSoundArticle.getTitle()) ||
           StringUtils.isBlank(loveSoundArticle.getBaseInfo()) ||
           StringUtils.isBlank(loveSoundArticle.getInterest()) ||
           StringUtils.isBlank(loveSoundArticle.getSelfIntroduction()) ||
           StringUtils.isBlank(loveSoundArticle.getDemand()) ||
           StringUtils.isBlank(loveSoundArticle.getImgUrls()) ||
           loveSoundArticle.getImg1() == 0 || loveSoundArticle.getImg2() == 0 || loveSoundArticle.getImg3() == 0){
            return HttpJsonResult.FAIL("参数错误");
        }
        if(loveSoundArticle.getInterest().length() < 5 ||
           loveSoundArticle.getSelfIntroduction().length() < 5 ||
           loveSoundArticle.getDemand().length() < 5){
            return HttpJsonResult.FAIL("参数错误");
        }
        String temp = BlackListManager.manager.filter(loveSoundArticle.getBaseInfo());
        if(!temp.equals(loveSoundArticle.getBaseInfo()))
        {/*处理敏感词*/
            loveSoundArticle.setBaseInfo(temp);
        }
        temp = BlackListManager.manager.filter(loveSoundArticle.getTitle());
        if(!temp.equals(loveSoundArticle.getTitle()))
        {/*处理敏感词*/
            loveSoundArticle.setTitle(temp);
        }
        temp = BlackListManager.manager.filter(loveSoundArticle.getInterest());
        if(!temp.equals(loveSoundArticle.getInterest()))
        {/*处理敏感词*/
            loveSoundArticle.setInterest(temp);
        }
        temp = BlackListManager.manager.filter(loveSoundArticle.getSelfIntroduction());
        if(!temp.equals(loveSoundArticle.getSelfIntroduction()))
        {/*处理敏感词*/
            loveSoundArticle.setSelfIntroduction(temp);
        }
        temp = BlackListManager.manager.filter(loveSoundArticle.getDemand());
        if(!temp.equals(loveSoundArticle.getDemand()))
        {/*处理敏感词*/
            loveSoundArticle.setDemand(temp);
        }
        WxUser wxUserOld = authorizeService.getWxUserById(Long.valueOf(userId));
        loveSoundArticle.setProvince(wxUserOld.getProvince());
        loveSoundArticle.setCity(wxUserOld.getCity());
        loveSoundArticle.setHasTop(false);
        loveSoundArticle.setStatus(Status.ACTIVE.getValue());
        loveSoundArticle.setCreateTime(DateUtils.getNowDateTimestamp());
        loveSoundArticle.setCreateUserId(userId);
        loveSoundArticle.setUpdateTime(loveSoundArticle.getCreateTime());
        loveSoundArticle.setUpdateUserId(userId);
        //存储到数据库
        articleService.saveArticle(loveSoundArticle);
        return HttpJsonResult.SUCCESS();
    }


    /**
     * 修改文章信息
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "修改文章信息", httpMethod = "POST", notes = "修改文章信息")
    @RequestMapping(value = "updateArticle", method = RequestMethod.POST)
    public HttpJsonResult updateArticle(HttpServletRequest request,
                                        @ApiParam(required = true, name = "LoveSoundArticle", value = "LoveSoundArticle")LoveSoundArticle loveSoundArticle,
                                        @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId){

        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        //验证数据有效性
        if(StringUtils.isBlank(loveSoundArticle.getTitle()) ||
            StringUtils.isBlank(loveSoundArticle.getBaseInfo()) ||
            StringUtils.isBlank(loveSoundArticle.getInterest()) ||
            StringUtils.isBlank(loveSoundArticle.getSelfIntroduction()) ||
            StringUtils.isBlank(loveSoundArticle.getDemand()) ||
            StringUtils.isBlank(loveSoundArticle.getImgUrls()) ||
            loveSoundArticle.getImg1() == 0 || loveSoundArticle.getImg2() == 0 || loveSoundArticle.getImg3() == 0){
            return HttpJsonResult.FAIL("参数错误");
        }
        if(loveSoundArticle.getInterest().length() < 5 ||
                loveSoundArticle.getSelfIntroduction().length() < 5 ||
                loveSoundArticle.getDemand().length() < 5){
            return HttpJsonResult.FAIL("参数错误");
        }
        String temp = BlackListManager.manager.filter(loveSoundArticle.getBaseInfo());
        if(!temp.equals(loveSoundArticle.getBaseInfo()))
        {/*处理敏感词*/
            loveSoundArticle.setBaseInfo(temp);
        }
        temp = BlackListManager.manager.filter(loveSoundArticle.getTitle());
        if(!temp.equals(loveSoundArticle.getTitle()))
        {/*处理敏感词*/
            loveSoundArticle.setTitle(temp);
        }
        temp = BlackListManager.manager.filter(loveSoundArticle.getInterest());
        if(!temp.equals(loveSoundArticle.getInterest()))
        {/*处理敏感词*/
            loveSoundArticle.setInterest(temp);
        }
        temp = BlackListManager.manager.filter(loveSoundArticle.getSelfIntroduction());
        if(!temp.equals(loveSoundArticle.getSelfIntroduction()))
        {/*处理敏感词*/
            loveSoundArticle.setSelfIntroduction(temp);
        }
        temp = BlackListManager.manager.filter(loveSoundArticle.getDemand());
        if(!temp.equals(loveSoundArticle.getDemand()))
        {/*处理敏感词*/
            loveSoundArticle.setDemand(temp);
        }
        WxUser wxUserOld = authorizeService.getWxUserById(Long.valueOf(userId));
        loveSoundArticle.setProvince(wxUserOld.getProvince());
        loveSoundArticle.setCity(wxUserOld.getCity());
        loveSoundArticle.setCreateUserId(userId);//这里不是为了修改，是为了sql去匹配是这个人发的
        loveSoundArticle.setHasTop(false);
        loveSoundArticle.setUpdateTime(DateUtils.getNowDateTimestamp());
        loveSoundArticle.setUpdateUserId(userId);
        //修改到数据库
        articleService.updateArticle(loveSoundArticle);
        return HttpJsonResult.SUCCESS();
    }

    /**
     * 删除文章信息
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "删除文章信息", httpMethod = "POST", notes = "删除文章信息")
    @RequestMapping(value = "delArticle", method = RequestMethod.POST)
    public HttpJsonResult delArticle(HttpServletRequest request,
                                        @ApiParam(required = true, name = "LoveSoundArticle", value = "LoveSoundArticle")LoveSoundArticle loveSoundArticle,
                                        @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId){

        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        if(loveSoundArticle.getId() == 0){
            return HttpJsonResult.FAIL("参数错误");
        }
        LoveSoundArticle loveSoundArticleOld = articleService.getArticleById(loveSoundArticle.getId());
        if(!loveSoundArticleOld.getCreateUserId().equals(userId)){
            return HttpJsonResult.FAIL("这篇文章不属于你,不可删除");
        }

        loveSoundArticle.setStatus(Status.DELETE.getValue());
        loveSoundArticle.setCreateUserId(userId);//这里不是为了修改，是为了sql去匹配是这个人发的
        loveSoundArticle.setUpdateTime(DateUtils.getNowDateTimestamp());
        loveSoundArticle.setUpdateUserId(userId);
        //修改到数据库
        articleService.updateArticle(loveSoundArticle);
        return HttpJsonResult.SUCCESS();
    }




    /**
     * 获取文章信息列表 分页
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "获取文章信息列表 分页", httpMethod = "GET", notes = "获取文章信息列表 分页")
    @RequestMapping(value = "getArticleList", method = RequestMethod.GET)
    public HttpJsonResult getArticleList(HttpServletRequest request,
                                             @ApiParam(required = true, name = "pageSize", value = "页大小")@RequestParam int pageSize,
                                             @ApiParam(required = true, name = "pageNum", value = "第几页")@RequestParam int pageNum,
                                             @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId,
                                             @ApiParam(required = false, name = "selfUserId", value = "是否只看自己的")@RequestParam String selfUserId,
                                             @ApiParam(required = false, name = "gender", value = "1：男、2：女")@RequestParam Integer gender) {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        //这里控制了下，如果不是自己，就置为空
        if(!userId.equals(selfUserId)){
            selfUserId = null;
        }
        PageInfo pageInfo = articleService.getArticleList(new PageBean(pageSize,pageNum), Status.ACTIVE.getValue(), gender, selfUserId);
        return HttpJsonResult.SUCCESS(pageInfo);
    }


    /**
     * 获取文章未读总数量
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "获取文章未读总数量", httpMethod = "GET")
    @RequestMapping(value = "getArticleWeiDuCount", method = RequestMethod.GET)
    public HttpJsonResult getArticleWeiDuCount(HttpServletRequest request,
                                         @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId) {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        Integer getArticleWeiDuCount = articleService.getArticleWeiDuCount(userId, Status.ACTIVE.getValue());
        return HttpJsonResult.SUCCESS(getArticleWeiDuCount);
    }



    /**
     * 根据用户id获取文章信息列表 分页
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "根据用户id获取文章信息列表 分页", httpMethod = "GET", notes = "根据用户id获取文章信息列表 分页")
    @RequestMapping(value = "getArticleListByUserId", method = RequestMethod.GET)
    public HttpJsonResult getArticleListByUserId(HttpServletRequest request,
                                         @ApiParam(required = true, name = "pageSize", value = "页大小")@RequestParam int pageSize,
                                         @ApiParam(required = true, name = "pageNum", value = "第几页")@RequestParam int pageNum,
                                         @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId,
                                         @ApiParam(required = true, name = "selectUserId", value = "被查看的用户id")@RequestParam String selectUserId) {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        PageInfo pageInfo = articleService.getArticleListByUserId(new PageBean(pageSize,pageNum), Status.ACTIVE.getValue(), selectUserId);
        return HttpJsonResult.SUCCESS(pageInfo);
    }




    /**
     * 根据id获取文章信息
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "根据id获取文章信息", httpMethod = "GET", notes = "根据id获取文章信息")
    @RequestMapping(value = "getArticleById", method = RequestMethod.GET)
    public HttpJsonResult getArticleById(HttpServletRequest request,
                                         @ApiParam(required = true, name = "articleId", value = "articleId")@RequestParam long articleId,
                                         @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId) {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }

        /*判断当前登录用户对这篇文章是否点过赞或倒赞*/
        WaterArticleZan waterArticleZan = articleService.getArticleZanByArticleId(articleId, userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("zan", waterArticleZan);

        LoveSoundArticle loveSoundArticle = articleService.getArticleById(articleId);

        convIndustryValues(loveSoundArticle.getWxUser());
        convCharacterValues(loveSoundArticle.getWxUser());
        //如果这个文章是自己创建的，就把留言变为已读
        if(userId.equals(String.valueOf(loveSoundArticle.getCreateUserId()))){
            articleService.updateReadStatus(loveSoundArticle.getId(), Status.HaveRead.getValue());
        }

        /*查询用户是否举报过该文章*/
        if(!(userId).equals((String.valueOf(loveSoundArticle.getCreateUserId()))))
        {
            LoveSoundReport waterReport = articleService.getReportByArticleId(articleId, userId);
            if(waterReport != null)
            {
                jsonObject.put("isReport", 1);/*举报过*/
            }else{
                jsonObject.put("isReport", 0);
            }
        }

        //保存一条阅读记录
        WaterArticleRead waterArticleRead = new WaterArticleRead();
        waterArticleRead.setArticleId(articleId);
        waterArticleRead.setArticlecreateUserId(Long.valueOf(loveSoundArticle.getCreateUserId()));
        waterArticleRead.setCreateTime(DateUtils.getNowDateTimestamp());
        waterArticleRead.setCreateUserId(Long.valueOf(userId));
        articleService.saveArticleReadFlow(waterArticleRead);

        //获取这个文章总的阅读数量
        int countRead = articleService.getArticleReadFlowCount(articleId);
        //获取这个文章总的赞 喜欢 数量
        int countZan = articleService.getArticleZanCount(articleId);
        jsonObject.put("read", countRead);
        jsonObject.put("countZan", countZan);
        jsonObject.put("loveSoundArticle", loveSoundArticle);
        return HttpJsonResult.SUCCESS(jsonObject);
    }


    /*给文章点赞或倒赞*/
    @ResponseBody
    @RequestMapping(value = "update/operation/article",method = RequestMethod.POST)
    @ApiOperation(value = "给文章点赞或倒赞", httpMethod = "POST", notes = "给文章点赞或倒赞")
    public HttpJsonResult operationArticle(WaterArticleZan waterArticleZan,
                                           @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId)
    {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        /*查询以前是否点赞或倒赞过*/
        WaterArticleZan waterArticleZanOld = articleService.getArticleZanByArticleId(waterArticleZan.getArticleId(), userId);
        if(waterArticleZanOld != null)
        {
            if(waterArticleZanOld.getZanStatus() == waterArticleZan.getZanStatus())
            {
                if(waterArticleZan.getZanStatus() == Integer.parseInt(Status.Zan.getValue()))
                {
                    return HttpJsonResult.FAIL("已喜欢,请勿重复操作!");
                }else
                {
                    return HttpJsonResult.FAIL("已厌恶,请勿重复操作!");
                }
            }else
            {
                waterArticleZan.setId(waterArticleZanOld.getId());
                articleService.updateOperationArticle(waterArticleZan);
            }
        }else
        {
            waterArticleZan.setCreateTime(DateUtils.getNowDateTimestamp());
            waterArticleZan.setCreateUserId(Long.valueOf(userId));
            articleService.saveOperationArticle(waterArticleZan);
        }
        return HttpJsonResult.SUCCESS();
    }



    /**
     * 举报文章
     * @ reportReason 举报原因
     * @ dataId 文章id
     * @ reportUserId 文章创建人id,是被举报者
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "save/report/article",method = RequestMethod.POST)
    @ApiOperation(value = "举报文章", httpMethod = "POST", notes = "举报文章")
    public HttpJsonResult reportArticle(LoveSoundReport waterReport,
                                        @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId)
    {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        if((userId).equals(waterReport.getReportUserId()+""))
        {
            return HttpJsonResult.FAIL("不允许举报自己！");
        }

        /*查询以前是否举报过*/
        LoveSoundReport waterReportOld = articleService.getReportByArticleId(Long.valueOf(waterReport.getDataId()), userId);
        if(waterReportOld != null)
        {
            return HttpJsonResult.FAIL("已举报过");
        }

        /*然后判断今天之内的举报次数是否超过十次,防止接口被用户乱用*/
        Integer dayReportCount = articleService.getdayReportCountByUserId(Status.ArticleReport.getValue() ,userId);
        if(dayReportCount >= Integer.parseInt(Status.ArticleReportCount.getValue()))
        {
            return HttpJsonResult.FAIL("当天举报次数已达上限！");
        }
        waterReport.setFunctionId(Integer.parseInt(Status.ArticleReport.getValue()));
        waterReport.setUpdateUserId(Long.valueOf(userId));
        waterReport.setUpdateTime(DateUtils.getNowDateTimestamp());
        waterReport.setCreateTime(waterReport.getUpdateTime());
        waterReport.setCreateUserId(waterReport.getUpdateUserId());
        articleService.reportArticle(waterReport);
        return HttpJsonResult.SUCCESS();
    }



    /**
     * 读取某个文章的留言列表
     * @param messageUserId 给这个用户留的言
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getMessageListByArticleId", method = RequestMethod.GET)
    @ApiOperation(value = "读取某个文章的留言列表", httpMethod = "GET", notes = "读取某个文章的留言列表")
    public HttpJsonResult getMessageListByArticleId(@ApiParam(required = true, name = "messageUserId", value = "给这个用户留的言")Long messageUserId,
                                                    @ApiParam(required = true, name = "articleId", value = "文章id")Long articleId,
                                                    @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId)
    {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        List<WaterMessageBoard> waterMessageBoards = articleService.messageList(messageUserId, articleId);
        return HttpJsonResult.SUCCESS(waterMessageBoards);
    }





    /**
     * 留言或回复 保存
     * messageUserId 给这个用户留的言
     * content 留言的内容
     * replyMessageId 如果是回复的话就有值，对应回复的那条留言
     * floor 如果是回复的话就有值，对应回复的那条留言
     * 这里已经用路径控制登录了，不用做登录判断了
     * */
    @ResponseBody
    @RequestMapping(value = "save/message", method = RequestMethod.POST)
    @ApiOperation(value = "留言或回复 保存", httpMethod = "POST", notes = "留言或回复 保存")
    public HttpJsonResult saveMessage(WaterMessageBoard waterMessageBoard,
                                      @ApiParam(required = true, name = "openId", value = "openId")@RequestParam String openId)
    {
        String userId = getUserIdByRedis(redisTemplate, openId);
        if(StringUtils.isBlank(userId)){
            return HttpJsonResult.FAIL("参数错误");
        }
        if(StringUtils.isBlank(waterMessageBoard.getContent()) || StringUtils.isBlank(waterMessageBoard.getMessageUserId())|| StringUtils.isBlank(waterMessageBoard.getArticleId()))
        {
            return HttpJsonResult.FAIL("参数错误！");
        }
        if(waterMessageBoard.getContent().length() > 512)
        {
            return HttpJsonResult.FAIL("留言内容超过指定长度！");
        }

        /*判断数据库是否已有禁言数据*/
//        WaterMessageBoardBanspeech messageBoardBanspeech = messageBoardService.getBanspeechUser(getUser().getId()+"",waterMessageBoard.getMessageUserId() );
//        if(messageBoardBanspeech != null)
//        {
//            return HttpJsonResult.FAIL("您已被此用户禁言！");
//        }

        if(StringUtils.isBlank(waterMessageBoard.getReplyMessageId()))
        {/*不是回复，需要自己获取楼层 获取最高楼层+1 */
            waterMessageBoard.setFloor((articleService.getMaxFloorByUserId(waterMessageBoard.getArticleId()) + 1));
        }
        waterMessageBoard.setStatus(Status.ACTIVE.getValue());
        waterMessageBoard.setCreateTime(DateUtils.getNowDateTimestamp());
        waterMessageBoard.setCreateUserId(Long.valueOf(userId));
        articleService.saveMessage(waterMessageBoard);

        return HttpJsonResult.SUCCESS();
    }

    /**
     * 删除一条留言及其回复
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

        articleService.delMessage(id, userId);
        return HttpJsonResult.SUCCESS();
    }


    /**
     * 从redis获取行业数据
     * @return
     */
    private Map<String,String> getRedisValues(String redisName)
    {
//        Object industryValues = redisTemplate.get(redisName);
        Object industryValues = redisTemplate.getJedisCluster().get(redisName);
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
//            redisTemplate.set(redisName, redisVal, 60 * 60 * 24 * 7L);//7天
            redisTemplate.getJedisCluster().hmset(redisName, redisVal);//7天
            redisTemplate.getJedisCluster().expire(redisName, 60 * 60 * 24 * 7);
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
