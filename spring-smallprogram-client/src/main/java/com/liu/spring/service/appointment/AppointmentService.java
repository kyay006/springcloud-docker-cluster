package com.liu.spring.service.appointment;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.spring.domain.appointment.LoveSoundAppointment;
import com.liu.spring.domain.appointment.LoveSoundAppointmentSign;
import com.liu.spring.domain.article.LoveSoundArticle;
import com.liu.spring.domain.commoninfo.Status;
import com.liu.spring.service.appointment.mapper.AppointmentMapper;
import com.liu.util.date.DateUtils;
import com.liu.util.mysql.PageBean;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by iWave on 2019/01/21.
 *
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class AppointmentService {

    @Autowired
    AppointmentMapper appointmentMapper;


    public void saveAppointment(LoveSoundAppointment loveSoundAppointment) {
        appointmentMapper.saveAppointment(loveSoundAppointment);
    }

    public PageInfo getAppointmentList(PageBean pageBean, String status, Integer gender, String selfUserId, Boolean selfUserMessage) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<LoveSoundAppointment> list = null;
        try {
            list = appointmentMapper.getAppointmentList(status, gender, selfUserId, selfUserMessage, DateUtils.format(DateUtils.getEarlyInTheDay(new Date())),DateUtils.format(DateUtils.getEarlyInTheDay(DateUtils.getNextDay(1))));
        } catch (ParseException e) {
            e.printStackTrace();
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(list);
    }

    public PageInfo getAppointmentListByUserId(PageBean pageBean, String status, String userId) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<LoveSoundArticle> list = appointmentMapper.getAppointmentListByUserId(status, userId);
        return new PageInfo<>(list);
    }

    public LoveSoundAppointment getAppointmentById(long id) {
        return appointmentMapper.getAppointmentById(id);
    }

    public void updateAppointment(LoveSoundAppointment loveSoundAppointment) {
        appointmentMapper.updateAppointment(loveSoundAppointment);
    }

    public LoveSoundAppointmentSign getMessageId(long appointmentId, String userId) {
        return appointmentMapper.getMessageId(appointmentId, userId);
    }

    public void saveMessage(LoveSoundAppointmentSign loveSoundAppointmentSign) {
        appointmentMapper.saveMessage(loveSoundAppointmentSign);
    }

    public void delMessage(String id, String userId) {
        appointmentMapper.delMessage(id, userId);
    }

    public List<LoveSoundAppointmentSign> messageList(Long messageUserId, Long articleId) {
        return appointmentMapper.messageList(messageUserId, articleId, Status.ACTIVE.getValue());
    }


    public void updateReadStatus(long id, String status) {
        appointmentMapper.updateReadStatus(id, status);
    }

    public Integer getArticleWeiDuCount(String userId, String status) {
        return appointmentMapper.getArticleWeiDuCount(userId, status);
    }
}
