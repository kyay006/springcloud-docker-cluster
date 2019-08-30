package com.liu.spring.springclient.controller.wechat;

import com.github.pagehelper.PageInfo;
import com.liu.spring.domain.commoninfo.Status;
import com.liu.spring.domain.wechat.LoveSoundWechatUpwall;
import com.liu.spring.service.wechat.SubscriptionService;
import com.liu.spring.springclient.controller.BaseController;
import com.liu.util.date.DateUtils;
import com.liu.util.image.ImgManageService;
import com.liu.util.image.WaterImgManage;
import com.liu.util.ip.IPUtil;
import com.liu.util.mysql.PageBean;
import com.liu.util.object.HttpJsonResult;
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

@Controller
@RequestMapping("wechat")
@Api(value = "wechat", description = "订阅号模块")
public class Subscription extends BaseController {


    @Autowired
    ImgManageService imgManageService;

    @Autowired
    SubscriptionService subscriptionService;

    /**
     * 返回上墙页面
     * @return
     */
    @ApiOperation(value = "返回上墙页面", httpMethod = "GET", notes = "返回上墙页面")
    @RequestMapping(value = "sub/getUpWallPage", method = RequestMethod.GET)
    public String getUpWallPage(HttpServletRequest request) {
        return "wechat/upwall";
    }

    /**
     * 保存上墙信息
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "保存上墙信息", httpMethod = "POST", notes = "保存上墙信息")
    @RequestMapping(value = "sub/saveUpWallInfo", method = RequestMethod.POST)
    public HttpJsonResult saveUpWallInfo(HttpServletRequest request,
                                 @ApiParam(required = true, name = "loveSoundWechatUpwall", value = "loveSoundWechatUpwall")LoveSoundWechatUpwall loveSoundWechatUpwall,
                                 @ApiParam(required = true, name = "img1File", value = "图片1") @RequestParam(value ="img1File") MultipartFile img1File,
                                 @ApiParam(required = true, name = "img2File", value = "图片2") @RequestParam(value ="img2File") MultipartFile img2File,
                                 @ApiParam(required = true, name = "img3File", value = "图片3") @RequestParam(value ="img3File") MultipartFile img3File,
                                 @ApiParam(required = false, name = "img4File", value = "图片4") @RequestParam(required = false, value ="img4File") MultipartFile img4File,
                                 @ApiParam(required = false, name = "img5File", value = "图片5") @RequestParam(required = false, value ="img5File") MultipartFile img5File) throws IOException {

        //验证数据有效性
        if(StringUtils.isBlank(loveSoundWechatUpwall.getName()) ||
           StringUtils.isBlank(loveSoundWechatUpwall.getWechat()) ||
           StringUtils.isBlank(loveSoundWechatUpwall.getBaseInfo()) ||
           StringUtils.isBlank(loveSoundWechatUpwall.getInterest()) ||
           StringUtils.isBlank(loveSoundWechatUpwall.getSelfIntroduction()) ||
           StringUtils.isBlank(loveSoundWechatUpwall.getDemand()) ||
           img1File == null || img2File == null || img3File == null){
            return HttpJsonResult.FAIL("参数错误");
        }
        if(loveSoundWechatUpwall.getInterest().length() < 5 ||
           loveSoundWechatUpwall.getSelfIntroduction().length() < 5 ||
           loveSoundWechatUpwall.getDemand().length() < 5){
            return HttpJsonResult.FAIL("参数错误");
        }
        StringBuilder imgUrls = new StringBuilder(300);
        //存储图片
        WaterImgManage imgManage = imgManageService.saveImg(Long.valueOf(loveSoundWechatUpwall.getSex()), img1File ,Status.UpWallImage.toString(), Status.UpWallImage.getValue());
        if(imgManage.getId() != null){
            imgUrls.append(imgManage.getUrlPath());
            loveSoundWechatUpwall.setImg1(imgManage.getId());
        }
        imgManage = imgManageService.saveImg(Long.valueOf(loveSoundWechatUpwall.getSex()), img2File ,Status.UpWallImage.toString(), Status.UpWallImage.getValue());
        if(imgManage.getId() != null){
            imgUrls.append(",").append(imgManage.getUrlPath());
            loveSoundWechatUpwall.setImg2(imgManage.getId());
        }
        imgManage = imgManageService.saveImg(Long.valueOf(loveSoundWechatUpwall.getSex()), img3File ,Status.UpWallImage.toString(), Status.UpWallImage.getValue());
        if(imgManage.getId() != null){
            imgUrls.append(",").append(imgManage.getUrlPath());
            loveSoundWechatUpwall.setImg3(imgManage.getId());
        }
        if(img4File != null){
            imgManage = imgManageService.saveImg(Long.valueOf(loveSoundWechatUpwall.getSex()), img4File ,Status.UpWallImage.toString(), Status.UpWallImage.getValue());
            if(imgManage.getId() != null){
                imgUrls.append(",").append(imgManage.getUrlPath());
                loveSoundWechatUpwall.setImg4(imgManage.getId());
            }
        }
        if(img5File != null){
            imgManage = imgManageService.saveImg(Long.valueOf(loveSoundWechatUpwall.getSex()), img5File ,Status.UpWallImage.toString(), Status.UpWallImage.getValue());
            if(imgManage.getId() != null){
                imgUrls.append(",").append(imgManage.getUrlPath());
                loveSoundWechatUpwall.setImg5(imgManage.getId());
            }
        }
        loveSoundWechatUpwall.setImgUrls(imgUrls.toString());
        loveSoundWechatUpwall.setExamineStatus(Integer.valueOf(Status.未审核.getValue()));
        loveSoundWechatUpwall.setCreateTime(DateUtils.getNowDateTimestamp());
        loveSoundWechatUpwall.setCreateUserIp(IPUtil.getRemoteAddr(request));
        //存储到数据库
        subscriptionService.saveUpWallInfo(loveSoundWechatUpwall);

        return HttpJsonResult.SUCCESS();
    }




    /**
     * 返回上墙审核页面
     * @return
     */
    @ApiOperation(value = "返回上墙审核页面", httpMethod = "GET", notes = "返回上墙审核页面")
    @RequestMapping(value = "getUpWallExaminePage", method = RequestMethod.GET)
    public String getUpWallExaminePage(HttpServletRequest request) {
        return "wechat/upwallExamine";
    }

    /**
     * 返回上墙审核详情页
     * @return
     */
    @ApiOperation(value = "返回上墙审核详情页", httpMethod = "GET", notes = "返回上墙审核详情页")
    @RequestMapping(value = "getUpWallExamineDetailPage", method = RequestMethod.GET)
    public String getUpWallExamineDetailPage(HttpServletRequest request, long id) {
        LoveSoundWechatUpwall loveSoundWechatUpwall = subscriptionService.getUpWallExamineById(id);
        request.setAttribute("loveSoundWechatUpwall", loveSoundWechatUpwall);
        return "wechat/upwallShow";
    }


    /**
     * 获取上墙信息列表 分页
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "获取上墙信息列表 分页", httpMethod = "GET", notes = "获取上墙信息列表 分页")
    @RequestMapping(value = "getUpWallInfoList", method = RequestMethod.GET)
    public HttpJsonResult getUpWallInfoList(HttpServletRequest request,
                                             @ApiParam(required = true, name = "pageSize", value = "页大小")@RequestParam int pageSize,
                                             @ApiParam(required = true, name = "pageNum", value = "第几页")@RequestParam int pageNum,
                                             @ApiParam(required = true, name = "examineStatus", value = "审核状态,0表示未审核,1表示审核失败,2表示审核成功但未发布,3表示已发布")@RequestParam(required = false) Integer examineStatus,
                                             @ApiParam(required = true, name = "status", value = "状态,ACTIVE为正常活动状态,DELETE为删除状态")@RequestParam(required = false) String status) {
        PageInfo pageInfo = subscriptionService.getUpWallInfoList(new PageBean(pageSize,pageNum), examineStatus, status);
        return HttpJsonResult.SUCCESS(pageInfo);
    }


    /**
     * 审核上墙信息
     * 审核状态,0表示未审核,1表示审核失败,2表示审核成功但未发布,3表示已发布
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "返回上墙审核详情页", httpMethod = "POST", notes = "返回上墙审核详情页")
    @RequestMapping(value = "updateUpWallExamine", method = RequestMethod.POST)
    public HttpJsonResult updateUpWallExamine(HttpServletRequest request, long id, int examineStatus,@RequestParam(required = false) String status) {
        if(id == 0 || examineStatus == 0){
            return HttpJsonResult.FAIL("参数错误");
        }

        subscriptionService.updateUpWallExamine(id, examineStatus, status);
        return HttpJsonResult.SUCCESS();
    }

    /**
     * 根据id获取上墙信息
     * @return
     */
//    @ResponseBody
//    @ApiOperation(value = "根据id获取上墙信息", httpMethod = "GET", notes = "根据id获取上墙信息")
//    @RequestMapping(value = "getUpWallExamineById", method = RequestMethod.GET)
//    public HttpJsonResult getUpWallExamineById(long id) {
//        LoveSoundWechatUpwall loveSoundWechatUpwall = subscriptionService.getUpWallExamineById(id);
//        return HttpJsonResult.SUCCESS(loveSoundWechatUpwall);
//    }


}
