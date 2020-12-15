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
    public ResponseDto save(@RequestBody String jsonStr){
        // 保存校验
        ValidatorUtil.require(jsonStr, "资源");

        ResponseDto responseDto = new ResponseDto();
        resourceService.saveJson(jsonStr);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        resourceService.delete(id);
        return responseDto;
    }
}
