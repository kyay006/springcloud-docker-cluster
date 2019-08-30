package com.liu.spring.service.showpeople;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.spring.domain.smallprogram.WxUser;
import com.liu.spring.service.showpeople.mapper.ShowPeopleMapper;
import com.liu.util.mysql.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by iWave on 2019/01/21.
 * 微信小程序
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class ShowPeopleService {

    @Autowired
    ShowPeopleMapper showPeopleMapper;


    public PageInfo getBaseUserInfoList(int gender,PageBean pageBean) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<WxUser> list = showPeopleMapper.getBaseUserInfoList(gender);
        return new PageInfo<>(list);
    }

    public PageInfo getMatchUserInfoList(HashMap param, PageBean pageBean) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<WxUser> list = showPeopleMapper.getMatchUserInfoList(param);
        return new PageInfo<>(list);
    }
}
