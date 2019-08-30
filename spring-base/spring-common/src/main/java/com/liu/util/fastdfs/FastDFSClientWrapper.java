package com.liu.util.fastdfs;

//import com.github.tobato.fastdfs.domain.StorePath;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

//import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;

@Component
public class FastDFSClientWrapper {
    private final Logger logger = LoggerFactory.getLogger(FastDFSClientWrapper.class);
    //如果需要就在启动类上加import-具体去client项目找
    @Autowired(required = false)
    private FastFileStorageClient storageClient;
//    @Autowired
//    private AppConfig appConfig;   // 项目参数配置
 
    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        return getResAccessUrl(storePath);
    }
 
    /**
     * 将一段字符串生成一个文件上传
     * @param content 文件内容
     * @param fileExtension
     * @return
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream,buff.length, fileExtension,null);
        return getResAccessUrl(storePath);
    }
 
    // 封装图片完整URL地址
    private String getResAccessUrl(StorePath storePath) {
        String fileUrl = /*AppConstants.HTTP_PRODOCOL*/"" + /*appConfig.getResHost()*/"http://192.168.232.129"
                + ":" + /*appConfig.getFdfsStoragePort()*/"80" + "/" + storePath.getFullPath();
        return fileUrl;
    }
 
    /**
     * 删除文件
     * http://lang:8005/delImage?imgPath=group1/M00/00/00/wKjohFu90LKACfTCAABoVtIJ1vw233.jpg
     * @param fileUrl 文件访问地址
     * @return
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            logger.warn(e.getMessage());
        }
    }
 
    /**
     * 文件下载方式一
     * @param response
     */
    public void downloadFile1(HttpServletResponse response,String imgPath){
        StorePath storePath = StorePath.parseFromUrl(imgPath);
        byte[] b = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        try {
            String fileName1 = "aaa.jpg";
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName1);
            ServletOutputStream out = response.getOutputStream();
            byte[] content = new byte[1024];
            int length = -1;
            while ((length = bais.read(content)) != -1) {
                out.write(content, 0, length);
                out.flush();
            }
            out.close();
            bais.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 文件下载方式二：Controller直接返回这个ResponseEntity<byte[]>
     * @return
     */
    public ResponseEntity<byte[]> download2() {
        byte[] content = storageClient.downloadFile("group1", "M00/83/F7/wKiNgVplw4GAKqNnAANbI4wCqFY733.jpg", new DownloadByteArray());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment",  "aaa.jpg");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(content, headers, HttpStatus.CREATED);
    }

    /*

            // 上传文件，并添加文件元数据
            StorePath uploadFile(InputStream inputStream, long fileSize, String fileExtName, Set<MateData> metaDataSet);
            // 获取文件元数据
            Set<MateData> getMetadata(String groupName, String path);
            // 上传图片并同时生成一个缩略图
            StorePath uploadImageAndCrtThumbImage(InputStream inputStream, long fileSize, String fileExtName,
            Set<MateData> metaDataSet);
     */


}