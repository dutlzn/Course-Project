package com.lzn.file.controller.admin;


import com.lzn.dto.ResponseDto;
import com.lzn.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@RequestMapping("/admin")
@RestController
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(
            UploadController.class
    );


    public static final String BUSINESS_NAME = "文件上传";


    @RequestMapping("/upload")
    public ResponseDto upload(
            @RequestParam MultipartFile file
            ) throws IOException {
//        debug


        LOG.info("上传文件开始:{}", file);
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        // 保存文件到本地
        String fileName=  file.getOriginalFilename();
        String key = UuidUtil.getShortUuid();
        String fullPath = "D:/code/course-project/file/teacher/" + key +"-" + fileName;
        File dest = new File(fullPath);// 生成目标位置
        file.transferTo(dest);// 写道目标位置
        LOG.info(dest.getAbsolutePath());

        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
