package com.lzn.system.controller.admin;

import com.lzn.dto.ResourceDto;
import com.lzn.dto.PageDto;
import com.lzn.dto.ResponseDto;
import com.lzn.service.ResourceService;
import com.lzn.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/resource")
public class ResourceController {

    public static final String BUSINESS_NAME = "资源";

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        resourceService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
    @PostMapping("/save")
    public ResponseDto save(@RequestBody ResourceDto resourceDto){
        // 保存校验
        ValidatorUtil.require(resourceDto.getName(), "名称");
        ValidatorUtil.length(resourceDto.getName(), "名称", 1, 100);
        ValidatorUtil.length(resourceDto.getPage(), "页面", 1, 50);
        ValidatorUtil.length(resourceDto.getRequest(), "请求", 1, 200);


        ResponseDto responseDto = new ResponseDto();
        resourceService.save(resourceDto);
        responseDto.setContent(resourceDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        resourceService.delete(id);
        return responseDto;
    }
}
