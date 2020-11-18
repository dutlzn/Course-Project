package com.lzn.business.controller.admin;


import com.lzn.dto.ChapterDto;
import com.lzn.dto.PageDto;
import com.lzn.service.ChapterService;
import com.lzn.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
// 该路径说明是用来供控台使用了
@RequestMapping("/admin/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/list")
    // requestBody --- json 默认是表单
    public PageDto list(@RequestBody PageDto pageDto) {
        chapterService.list(pageDto);
        return pageDto;
    }


    @RequestMapping("/save")
    public ChapterDto save(@RequestBody ChapterDto chapterDto){
        chapterService.save(chapterDto);
        return chapterDto;
    }


}
