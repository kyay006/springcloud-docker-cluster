package com.liu.lovesound.springorder;


import com.liu.lovesound.springorder.mapper.LogLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


/**
 * Created by iWave on 2019/01/21.
 * 微信小程序
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class LogLoginService {

    @Autowired
    LogLoginMapper logLoginMapper;

    public void saveLogLogin(LoveSoundLogLogin loveSoundLogLogin) {
        logLoginMapper.saveLogLogin(loveSoundLogLogin);
    }


    public Map upLogLogin() {
        return logLoginMapper.upLogLogin();
    }

    public void updateLogLogin() {
        logLoginMapper.updateLogLogin();
    }



}
