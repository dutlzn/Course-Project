package com.lzn.file.controller.admin;


import com.lzn.dto.FileDto;
import com.lzn.dto.ResponseDto;
import com.lzn.enums.FileUseEnum;
import com.lzn.service.FileService;
import com.lzn.util.Base64ToMultipartFile;
import com.lzn.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


@RequestMapping("/admin")
@RestController
public class UploadController {

    @Value("${file.path}")
    private String FILE_PATH;
    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @Autowired
    private FileService fileService;

    private static final Logger LOG = LoggerFactory.getLogger(
            UploadController.class
    );


    public static final String BUSINESS_NAME = "文件上传";


    @RequestMapping("/upload")
    public ResponseDto upload(@RequestBody FileDto fileDto) throws IOException {

        LOG.info("上传文件开始");

        String use = fileDto.getUse();
        String key = fileDto.getKey();
        String suffix = fileDto.getSuffix();
        String shardBase64 = fileDto.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);

        // 保存文件到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(use);

        // 如果文件夹不存在则创建
        String dir = useEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if( !fullDir.exists()){
            fullDir.mkdir();
        }

        String path  = new StringBuffer(dir)
                .append(File.separator)
                .append(key)
                .append(".")
                .append(suffix)
                .append(".")
                .append(fileDto.getShardIndex())
                .toString();
        String fullPath = FILE_PATH + path;

        // 文件的路径应该是自动生成的
        File dest = new File(fullPath);// 生成目标位置
        shard.transferTo(dest);// 写道目标位置
        LOG.info(dest.getAbsolutePath());

        LOG.info("保存文件记录开始");
        fileDto.setPath(FILE_DOMAIN + path);
        fileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(fileDto);
        return responseDto;
    }

    // 合并 分片
    @GetMapping("/merge")
    public ResponseDto merge() throws Exception {
        File newFile = new File(FILE_PATH + "/course/test.mp4");
        FileOutputStream outputStream = new FileOutputStream(newFile, true);// 文件追加写入
        FileInputStream fileInputStream = null; // 分片文件
        byte[] byt = new byte[1024*1024*10];
        int len;

        try {
            // 读取第一个分片
            fileInputStream = new FileInputStream(new File(FILE_PATH + "/course/0mUkwqKQ.blob"));
            while( (len = fileInputStream.read(byt)) != -1) {
                outputStream.write(byt, 0, len);
            }
            fileInputStream = new FileInputStream(new File(FILE_PATH + "/course/FInyTgeZ.blob"));
            while( (len = fileInputStream.read(byt)) != -1) {
                outputStream.write(byt, 0, len);
            }

        } catch(IOException e){
            LOG.error("分片合并异常", e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                outputStream.close();
                LOG.info("IO流关闭");
            } catch (Exception e) {
                LOG.error("IO流关闭", e);
            }
        }

        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
