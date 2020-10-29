package com.lzn.business.controller.admin;


import com.lzn.domain.Chapter;
import com.lzn.dto.ChapterDto;
import com.lzn.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
// 该路径说明是用来供控台使用了
@RequestMapping("/admin")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/chapter")
    public List<ChapterDto> chapter() {
        return chapterService.list();
    }
}
