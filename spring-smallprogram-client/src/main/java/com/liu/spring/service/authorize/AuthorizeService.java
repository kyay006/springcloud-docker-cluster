package com.liu.spring.service.authorize;


import com.liu.spring.domain.smallprogram.WxUser;
import com.liu.spring.service.authorize.mapper.AuthorizeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by iWave on 2019/01/21.
 * 微信小程序
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class AuthorizeService {

    @Autowired
    AuthorizeMapper authorizeMapper;

    public WxUser getWxUserByOpenId(String openId) {
        return authorizeMapper.getWxUserByOpenId(openId);
    }

    public void saveWxUser(WxUser wxUser) {
        authorizeMapper.saveWxUser(wxUser);
    }

    public WxUser getWxUserById(long userId) {
        return authorizeMapper.getWxUserById(userId);
    }
}
