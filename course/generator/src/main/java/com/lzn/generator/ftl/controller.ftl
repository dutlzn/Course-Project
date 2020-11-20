package com.lzn.business.controller.admin;

import com.lzn.dto.${Domain}Dto;
import com.lzn.dto.PageDto;
import com.lzn.dto.ResponseDto;
import com.lzn.service.${Domain}Service;
import com.lzn.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/${domain}")
public class ${Domain}Controller {

    public static final String BUSINESS_NAME = "大章";

    @Autowired
    private ${Domain}Service ${domain}Service;

    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        ${domain}Service.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
    @PostMapping("/save")
    public ResponseDto save(@RequestBody ${Domain}Dto ${domain}Dto){


            ValidatorUtil.require(${domain}Dto.getName(), "名称");
            ValidatorUtil.require(${domain}Dto.getCourseId(), "课程ID");
            ValidatorUtil.length(${domain}Dto.getCourseId(), "课程ID",
                    1, 8);


        ResponseDto responseDto = new ResponseDto();
        ${domain}Service.save(${domain}Dto);
        responseDto.setContent(${domain}Dto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        ${domain}Service.delete(id);
        return responseDto;
    }
}
