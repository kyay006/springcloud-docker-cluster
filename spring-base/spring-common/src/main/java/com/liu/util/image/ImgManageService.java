package com.liu.util.image;

import com.liu.util.date.DateUtils;
import com.liu.util.image.mapper.ImgManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by lang.liu on 2017/2/18.
 * 图片管理service
 */

@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class ImgManageService
{

    @Value("${customConfig.image.resourceAbsolutePath:''}")
    String resourceAbsolutePath;

    @Value("${customConfig.image.resourceUrlPath:''}")
    String resourceUrlPath;

    @Autowired(required = false)
    ImgManageMapper imgManageMapper;

    /**
     * 保存图片,为了方便维护，必须用统一的图片保存
     * @param userId 上传此图片的用户id
     * @param file 文件对象
     * @param funName 功能名称-作为文件夹存的 Status里有定义
     * @param imgType 图片类型
     * @throws IOException
     */
    public WaterImgManage saveImg(Long userId, MultipartFile file, String funName , String imgType) throws IOException
    {
        if(file == null)
            return new WaterImgManage();
        StringBuffer path = new StringBuffer(resourceAbsolutePath);
        path.append(userId).append("/").append(funName).append("/").append(DateUtils.getShorDateformatNotLine()).append("/");
        String fileName = file.getOriginalFilename();
        String newFileName = UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."),fileName.length());
        File newFile = new File(path.toString());
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        newFile = new File(path.append(newFileName).toString());
        file.transferTo(newFile);

        StringBuffer urlPath = new StringBuffer(resourceUrlPath);
        urlPath.append(userId).append("/").append(funName).append("/").append(DateUtils.getShorDateformatNotLine()).append("/").append(newFileName);

        WaterImgManage waterImgManage = new WaterImgManage();
        waterImgManage.setAbsolutePath(path.toString());
        waterImgManage.setUrlPath(urlPath.toString());
        waterImgManage.setImgFileName(fileName);
        waterImgManage.setImgType(imgType.toCharArray()[0]);
        waterImgManage.setStatus("ACTIVE");
        waterImgManage.setUpdateTime(DateUtils.getNowDateTimestamp());
        waterImgManage.setUpdateUserId(userId);
        waterImgManage.setCreateTime(waterImgManage.getUpdateTime());
        waterImgManage.setCreateUserId(waterImgManage.getUpdateUserId());
        imgManageMapper.saveImg(waterImgManage);
        return waterImgManage;
    }

    /**
     * 根据用户id和类型数值获取图片列表,不分页
     * @param userId
     * @param imgType
     * @return
     */
    public List<WaterImgManage> getUserPhotoList(String userId, String imgType) {
        return imgManageMapper.getUserPhotoList(new HashMap<String,String>(){{
            put("userId", userId);
            put("imgType", imgType);
            put("status", "ACTIVE");
        }});
    }

    public void delUserImageById(Long userId, long id, String imgType) {
        imgManageMapper.delUserImageById(new HashMap<String,Object>(){{
            put("userId", userId);
            put("id", id);
            put("imgType", imgType);
            put("statusOld", "ACTIVE");
            put("statusNew", "DELETE");
            put("updateTime", new Date());
        }});
    }
}
