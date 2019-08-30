package com.liu.spring.springclient.controller.commoninfo;


import com.liu.spring.service.commoninfo.SelectInfoService;
import com.liu.spring.springclient.controller.BaseController;
import com.liu.util.object.HttpJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("smallProgram/selectInfo")
@Api(value = "smallProgram/selectInfo", description = "公用选择信息模块")
public class SelectInfo extends BaseController {


    @Autowired
    SelectInfoService selectInfoService;


    /**
     * 查询可选择行业列表 不分页
     * @return
     */
    @ApiOperation(value = "查询可选择行业列表", httpMethod = "GET", notes = "查询可选择行业列表 不分页")
    @ResponseBody
    @RequestMapping(value = "getSelectIndustryList", method = RequestMethod.GET)
    public HttpJsonResult getSelectIndustryList(HttpServletRequest request) {
        return HttpJsonResult.SUCCESS(selectInfoService.getSelectIndustryList());
    }


    /**
     * 查询可选择性格列表 不分页
     * @return
     */
    @ApiOperation(value = "查询可选择性格列表", httpMethod = "GET", notes = "查询可选择性格列表 不分页")
    @ResponseBody
    @RequestMapping(value = "getSelectCharacterList", method = RequestMethod.GET)
    public HttpJsonResult getSelectCharacterList(HttpServletRequest request) {
        return HttpJsonResult.SUCCESS(selectInfoService.getSelectCharacterList());
    }











}
