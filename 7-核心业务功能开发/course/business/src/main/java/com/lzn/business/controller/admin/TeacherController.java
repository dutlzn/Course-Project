package com.lzn.business.controller.admin;

import com.lzn.dto.TeacherDto;
import com.lzn.dto.PageDto;
import com.lzn.dto.ResponseDto;
import com.lzn.service.TeacherService;
import com.lzn.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/teacher")
public class TeacherController {

    public static final String BUSINESS_NAME = "讲师";

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        teacherService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
    @PostMapping("/save")
    public ResponseDto save(@RequestBody TeacherDto teacherDto){
        // 保存校验
        ValidatorUtil.require(teacherDto.getName(), "姓名");
        ValidatorUtil.length(teacherDto.getName(), "姓名", 1, 50);
        ValidatorUtil.length(teacherDto.getNickname(), "昵称", 1, 50);
        ValidatorUtil.length(teacherDto.getImage(), "头像", 1, 100);
        ValidatorUtil.length(teacherDto.getPosition(), "职位", 1, 50);
        ValidatorUtil.length(teacherDto.getMotto(), "座右铭", 1, 50);
        ValidatorUtil.length(teacherDto.getIntro(), "简介", 1, 500);


        ResponseDto responseDto = new ResponseDto();
        teacherService.save(teacherDto);
        responseDto.setContent(teacherDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        teacherService.delete(id);
        return responseDto;
    }


    // 返回所有讲师
    @PostMapping("/all")
    public ResponseDto all() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(teacherService.all());
        return responseDto;
    }
}
