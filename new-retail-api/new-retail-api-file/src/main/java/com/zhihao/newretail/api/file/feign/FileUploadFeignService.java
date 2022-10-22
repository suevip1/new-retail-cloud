package com.zhihao.newretail.api.file.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@FeignClient(name = "new-retail-file", path = "/file")
public interface FileUploadFeignService {

    @PostMapping(value = "/feign/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String getFileUrl(@RequestPart("file") MultipartFile file, @RequestParam("dir") String dir) throws IOException;

}
