package com.liu.spring.service.commoninfo.mapper;


import com.liu.spring.domain.commoninfo.LoveSoundCharacter;
import com.liu.spring.domain.commoninfo.LoveSoundIndustry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 微信小程序
 * @author lang.liu
 **/
@Mapper
public interface SelectInfoMapper {


    @Select("SELECT id,industryName,remark,createTime FROM lovesound_industry WHERE status = #{status} order by orderNumber")
    List<LoveSoundIndustry> getSelectIndustryList(String status);


    @Select("SELECT id,characterName,remark,createTime FROM lovesound_character WHERE status = #{status} order by orderNumber")
    List<LoveSoundCharacter> getSelectCharacterList(String status);
}