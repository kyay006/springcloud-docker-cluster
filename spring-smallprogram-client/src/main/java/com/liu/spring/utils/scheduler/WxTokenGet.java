package com.liu.spring.utils.scheduler;

import com.liu.util.smallprogram.WXUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 微信小程序 后台token的获取
 */
@Component
public class WxTokenGet {

    @Autowired
    WXUtils wxUtils;

    @Lazy(false)
    @Scheduled(cron="0 */90 * * * ? ")   //每90分钟获取一下
//    @Scheduled(cron="0/5 * * * * ? ")   //每90分钟执行一次
    public void text1() {
//        wxUtils.getAccessToken(WXPayConstants.APP_ID);
    }



}
