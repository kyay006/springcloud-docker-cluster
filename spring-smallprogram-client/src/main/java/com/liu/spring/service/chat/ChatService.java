package com.liu.spring.service.chat;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.spring.domain.chat.LoveSoundChatList;
import com.liu.spring.domain.chat.LoveSoundChatMsg;
import com.liu.spring.domain.commoninfo.Status;
import com.liu.spring.service.chat.mapper.ChatMapper;
import com.liu.util.mysql.PageBean;
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
public class ChatService {

    @Autowired
    ChatMapper chatMapper;


    public List<LoveSoundChatList> getUserChatList(String userId) {
        PageHelper.startPage(1, 999);
        return chatMapper.getUserChatList(userId, Status.ACTIVE.getValue());
    }

    public List<LoveSoundChatMsg> getUserChatMsgLastList(long userId, String userIdLogin, PageBean pageBean) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<LoveSoundChatMsg> temp = chatMapper.getUserChatMsgLastList(userId,userIdLogin, Status.ACTIVE.getValue());
        //如果有消息的话，就将列表的未读数量改下
        if(temp != null && temp.size() > 0){
            LoveSoundChatList loveSoundChatList = chatMapper.getLoveSoundChatListByTowUserId(userId, Long.valueOf(userIdLogin));
            if(loveSoundChatList != null){
                if(loveSoundChatList.getSendUserId() == Long.valueOf(userIdLogin)){
                    loveSoundChatList.setSendUnReadCount(0);
                }else{
                    loveSoundChatList.setReceiverUnReadCount(0);
                }
                loveSoundChatList.setStatus(Status.ACTIVE.getValue());
                loveSoundChatList.setUpdateTime(loveSoundChatList.getCreateTime());
                loveSoundChatList.setUpdateUserId(loveSoundChatList.getUpdateUserId());
                chatMapper.updateLoveSoundChatList(loveSoundChatList);
            }
        }

        return temp;
    }


    public PageInfo getUserChatMsgHistoryList(long userId, String userIdLogin, PageBean pageBean) {
        List<LoveSoundChatMsg> temp = getUserChatMsgLastList(userId, userIdLogin, pageBean);
        return new PageInfo<>(temp);
    }

    public void saveChatMsg(LoveSoundChatMsg loveSoundChatMsg) {
        //判断是否已经有聊天列表了，如果没有就创建，如果删除过就更新
        LoveSoundChatList loveSoundChatList = chatMapper.getLoveSoundChatListByTowUserId(loveSoundChatMsg.getSendUserId(), loveSoundChatMsg.getReceiverUserId());
        if(loveSoundChatList == null){
            loveSoundChatList = new LoveSoundChatList();
            loveSoundChatList.setSendUserId(loveSoundChatMsg.getSendUserId());
            loveSoundChatList.setReceiverUserId(loveSoundChatMsg.getReceiverUserId());
            loveSoundChatList.setSendUnReadCount(1);
            loveSoundChatList.setReceiverUnReadCount(0);
            loveSoundChatList.setCreateTime(loveSoundChatMsg.getCreateTime());
            loveSoundChatList.setCreateUserId(loveSoundChatMsg.getCreateUserId());
        }else{
            if(loveSoundChatList.getSendUserId() == loveSoundChatMsg.getCreateUserId()){
                loveSoundChatList.setReceiverUnReadCount(loveSoundChatList.getSendUnReadCount() + 1);
            }else{
                loveSoundChatList.setSendUnReadCount(loveSoundChatList.getReceiverUnReadCount() + 1);
            }
        }
        loveSoundChatList.setStatus(Status.ACTIVE.getValue());
        loveSoundChatList.setLastContent(loveSoundChatMsg.getContent());
        if(loveSoundChatMsg.getContent().length() > 10){
            loveSoundChatList.setLastContent(loveSoundChatMsg.getContent().substring(0, 10));
        }
        if(loveSoundChatMsg.getMsgType() == Integer.valueOf(Status.图片.getValue())){
            loveSoundChatList.setLastContent("[图片]");
        }
        loveSoundChatList.setLastSendDate(loveSoundChatMsg.getCreateTime());
        loveSoundChatList.setUpdateTime(loveSoundChatList.getCreateTime());
        loveSoundChatList.setUpdateUserId(loveSoundChatList.getUpdateUserId());
        if(loveSoundChatList.getId() == null){
            chatMapper.saveLoveSoundChatList(loveSoundChatList);
        }else{
            chatMapper.updateLoveSoundChatList(loveSoundChatList);
        }
        chatMapper.saveChatMsg(loveSoundChatMsg);
    }

    public LoveSoundChatList getLoveSoundChatListByTowUserId(long sendUserId, long receiverUserId){
        return chatMapper.getLoveSoundChatListByTowUserId(sendUserId, receiverUserId);
    }
    public void updateLoveSoundChatList(LoveSoundChatList loveSoundChatList){
        chatMapper.updateLoveSoundChatList(loveSoundChatList);
    }

}
