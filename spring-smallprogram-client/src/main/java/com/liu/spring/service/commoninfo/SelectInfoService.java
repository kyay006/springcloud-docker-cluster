package com.liu.spring.service.commoninfo;


import com.liu.spring.domain.commoninfo.LoveSoundCharacter;
import com.liu.spring.domain.commoninfo.LoveSoundIndustry;
import com.liu.spring.domain.commoninfo.Status;
import com.liu.spring.service.commoninfo.mapper.SelectInfoMapper;
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
public class SelectInfoService {

    @Autowired
    SelectInfoMapper selectInfoMapper;


    public List<LoveSoundIndustry> getSelectIndustryList() {
        return selectInfoMapper.getSelectIndustryList(Status.ACTIVE.getValue());
    }

    public List<LoveSoundCharacter> getSelectCharacterList() {
        return selectInfoMapper.getSelectCharacterList(Status.ACTIVE.getValue());
    }
}
