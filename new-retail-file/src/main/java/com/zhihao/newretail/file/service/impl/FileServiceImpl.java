package com.zhihao.newretail.file.service.impl;

import com.zhihao.newretail.core.util.MyUUIDUtil;
import com.zhihao.newretail.file.config.AliYunOSSConfig;
import com.zhihao.newretail.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private AliYunOSSConfig ossConfig;

    @Override
    public String upload(MultipartFile file, String dir) throws IOException {
        String filename = file.getOriginalFilename();       // 资源文件名
        String fileType = filename.substring(filename.lastIndexOf("."));    // 文件后缀名
        String uuid = MyUUIDUtil.getUUID();                 // 防止上传文件名重复
        String objectName = dir + uuid + fileType;          // 上传目录 + 文件名

        ossConfig.ossClient().putObject(ossConfig.getBucket(), objectName, file.getInputStream());
        ossConfig.ossClient().shutdown();
        return ossConfig.getBucketDomain() + "/" + objectName;  // 返回文件url
    }

}
