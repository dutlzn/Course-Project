package com.lzn.business.controller.admin;


import com.lzn.dto.ChapterDto;
import com.lzn.dto.PageDto;
import com.lzn.dto.ResponseDto;
import com.lzn.service.ChapterService;
import com.lzn.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
// 该路径说明是用来供控台使用了
@RequestMapping("/admin/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @PostMapping("/list")
    // requestBody --- json 默认是表单
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        chapterService.list(pageDto);
        responseDto.setContent(pageDto);
//        return pageDto;
        return responseDto;
    }
    @PostMapping("/save")
    public ResponseDto save(@RequestBody ChapterDto chapterDto){

        // 保存验证
//        try {
            ValidatorUtil.require(chapterDto.getName(), "名称");
            ValidatorUtil.require(chapterDto.getCourseId(), "课程ID");
            ValidatorUtil.length(chapterDto.getCourseId(), "课程ID",
                    1, 8);
//        } catch (Exception e) {
//            ResponseDto responseDto = new ResponseDto();
//            responseDto.setSuccess(false);
//
//            return responseDto;
//        }

        ResponseDto responseDto = new ResponseDto();
        chapterService.save(chapterDto);
        responseDto.setContent(chapterDto);
//        return chapterDto;
        return responseDto;
    }




    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        // 根据id 找到 chapter
        ResponseDto responseDto = new ResponseDto();
        chapterService.delete(id);
        return responseDto;

    }


}
