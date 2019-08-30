package com.liu.spring.service.myinfo;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.spring.domain.commoninfo.Status;
import com.liu.spring.domain.myinfo.LoveSoundUserDynamic;
import com.liu.spring.domain.myinfo.LoveSoundUserSubscribe;
import com.liu.spring.domain.smallprogram.WxUser;
import com.liu.spring.service.myinfo.mapper.BaseInfoMapper;
import com.liu.util.mysql.PageBean;
import com.liu.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by iWave on 2019/01/21.
 * 微信小程序
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class BaseInfoService {

    @Autowired
    BaseInfoMapper baseInfoMapper;



    public void saveBaseInfo(WxUser wxUser) {
        baseInfoMapper.saveBaseInfo(wxUser);
    }

    public void saveUserDynamic(LoveSoundUserDynamic loveSoundUserDynamic) {
        baseInfoMapper.saveUserDynamic(loveSoundUserDynamic);
    }

    public PageInfo getUserDynamicList(long userId, PageBean pageBean) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<LoveSoundUserDynamic> list = baseInfoMapper.getUserDynamicList(userId, Status.ACTIVE.getValue());
        return new PageInfo<>(list);
    }

    public LoveSoundUserDynamic getLoveSoundUserDynamicById(long id) {
        return baseInfoMapper.getLoveSoundUserDynamicById(id);
    }

    public void updateUserDynamic(LoveSoundUserDynamic loveSoundUserDynamic) {
        baseInfoMapper.updateUserDynamic(loveSoundUserDynamic);
    }

    public LoveSoundUserSubscribe getSubscribeServiceByUserId(String userId, long passiveUserId) {
        return baseInfoMapper.getSubscribeServiceByUserId( userId,  passiveUserId);
    }

    public void saveOrUpdateWaterUserSubscribe(LoveSoundUserSubscribe loveSoundUserSubscribe) {
        if(StringUtils.isBlank(loveSoundUserSubscribe.getId())){
            baseInfoMapper.saveWaterUserSubscribe(loveSoundUserSubscribe);
        }else{
            baseInfoMapper.updateWaterUserSubscribe(loveSoundUserSubscribe);
        }
    }


    /**
     * 获取这个人的粉丝数量
     * @param userId
     * @return
     */
    public int getUserFansCount(long userId) {
        return baseInfoMapper.getUserFansCount(userId,Status.关注.getValue());
    }

    /**
     * 获取这个人的关注数量
     * @param userId
     * @return
     */
    public int getUserSubscribeCount(long userId) {
        return baseInfoMapper.getUserSubscribeCount(userId, Status.关注.getValue());
    }

    /**
     * 读取此用户有多少条未读留言
     * @param userId
     * @return
     */
    public int getUnReadBoardByUserId(Long userId) {
        return baseInfoMapper.getUnReadBoardByUserId(userId, Status.UnRead.getValue(), Status.ACTIVE.getValue());
    }



    /**
     * 分页 读取此用户的关注列表
     * @param pageUserId
     * @param pageBean
     * @return
     */
    public PageInfo getSubscribeUserList(long pageUserId, PageBean pageBean) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<WxUser> list = baseInfoMapper.getSubscribeUserList(pageUserId, Status.关注.getValue());
        return new PageInfo<>(list);
    }

    /**
     * 分页 读取用户粉丝的其他用户列表
     * @param pageUserId
     * @param pageBean
     * @return
     */
    public PageInfo getFansUserList(long pageUserId, PageBean pageBean) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<WxUser> list = baseInfoMapper.getFansUserList(pageUserId, Status.关注.getValue());
        return new PageInfo<>(list);
    }

    /**
     * 获取用户基本信息
     * @param userId
     * @return
     */
    public WxUser getWxUserBaseInfo(String userId) {
        return baseInfoMapper.getWxUserBaseInfo(userId, Status.ACTIVE.getValue());
    }

}
