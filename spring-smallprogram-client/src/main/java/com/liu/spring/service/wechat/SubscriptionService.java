package com.liu.spring.service.wechat;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.spring.domain.smallprogram.WxUser;
import com.liu.spring.domain.wechat.LoveSoundWechatUpwall;
import com.liu.spring.service.wechat.mapper.SubscriptionMapper;
import com.liu.util.mysql.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by iWave on 2019/01/21.
 * 微信订阅号
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class SubscriptionService {

    @Autowired
    SubscriptionMapper subscriptionMapper;


    public void saveUpWallInfo(LoveSoundWechatUpwall loveSoundWechatUpwall) {
        subscriptionMapper.saveUpWallInfo(loveSoundWechatUpwall);
    }

    public PageInfo getUpWallInfoList(PageBean pageBean, Integer examineStatus, String status) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<LoveSoundWechatUpwall> list = subscriptionMapper.getUpWallInfoList(examineStatus, status);
        return new PageInfo<>(list);
    }

    public LoveSoundWechatUpwall getUpWallExamineById(long id) {
        return subscriptionMapper.getUpWallExamineById(id);
    }

    public void updateUpWallExamine(long id, int examineStatus, String status) {
        subscriptionMapper.updateUpWallExamine(id, examineStatus, new Date(), status);
    }
}
