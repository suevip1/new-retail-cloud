package com.zhihao.newretail.file.feign;

import com.zhihao.newretail.api.file.feign.FileUploadFeignService;
import com.zhihao.newretail.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
public class FileUploadFeignController implements FileUploadFeignService {

    @Autowired
    private FileService fileService;

    @Override
    public String getFileUrl(MultipartFile file, String dir) throws IOException {
        log.info("fileName: {}", file.getOriginalFilename());
        log.info("dir: {}", dir);
        return fileService.upload(file, dir);
    }

}
