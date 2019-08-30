package com.liu.util.image.mapper;


import com.liu.util.image.WaterImgManage;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lang.liu on 2017/2/12.
 */
public interface ImgManageMapper
{

    void saveImg(WaterImgManage waterImgManage);

    List<WaterImgManage> getUserPhotoList(HashMap param);

    void delUserImageById(HashMap param);
}
