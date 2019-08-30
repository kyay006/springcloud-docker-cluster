package com.liu.spring.service.log;


import com.liu.spring.domain.log.*;
import com.liu.spring.service.log.mapper.LogLoginMapper;
import com.liu.util.date.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by iWave on 2019/01/21.
 * 微信小程序
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class LogLoginService {

    @Autowired
    LogLoginMapper logLoginMapper;

    public LoveSoundLogLogin getLogLoginLast(long userId) {
        return logLoginMapper.getLogLoginLast(userId);
    }

    public void saveLogLogin(LoveSoundLogLogin loveSoundLogLogin) {
        logLoginMapper.saveLogLogin(loveSoundLogLogin);
    }

    public void saveLogSearch(LoveSoundLogSearch loveSoundLogSearch) {
        logLoginMapper.saveLogSearch(loveSoundLogSearch);
    }

    public void updateLogSearch() {
        logLoginMapper.updateLogSearch();
    }

    public void updateLogSearch1() {
        logLoginMapper.updateLogSearch1();
    }

    public void saveLogUserInfo(LoveSoundLogUserInfo loveSoundLogUserInfo) {
        logLoginMapper.saveLogUserInfo(loveSoundLogUserInfo);
    }

    public void saveLogReadUser(LoveSoundLogReadUser loveSoundLogReadUser) {
        logLoginMapper.saveLogReadUser(loveSoundLogReadUser);
    }

    public LoveSoundTemplateMsg getLoveSoundTemplateMsgLastByUserId(long userId) {
        return logLoginMapper.getLoveSoundTemplateMsgLastByUserId(userId, DateUtils.getEarlyInTheDay(new Date()));
    }

    public void saveLoveSoundTemplateMsg(LoveSoundTemplateMsg loveSoundTemplateMsg) {
        logLoginMapper.saveLoveSoundTemplateMsg(loveSoundTemplateMsg);
    }
}
