package com.lzn.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzn.domain.Chapter;
import com.lzn.domain.ChapterExample;
import com.lzn.domain.Test;
import com.lzn.domain.TestExample;
import com.lzn.dto.ChapterDto;
import com.lzn.dto.ChapterPageDto;
import com.lzn.dto.PageDto;
import com.lzn.mapper.ChapterMapper;
import com.lzn.mapper.TestMapper;
import com.lzn.util.CopyUtil;
import com.lzn.util.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Service
public class ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;


    public void list(ChapterPageDto chapterPageDto) {
        PageHelper.startPage(chapterPageDto.getPage(), chapterPageDto.getSize());
        ChapterExample chapterExample = new ChapterExample();
        ChapterExample.Criteria criteria = chapterExample.createCriteria();
        if (!StringUtils.isEmpty(chapterPageDto.getCourseId())) {
            criteria.andCourseIdEqualTo(chapterPageDto.getCourseId());
        }
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        chapterPageDto.setTotal(pageInfo.getTotal());
        List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, ChapterDto.class);
        chapterPageDto.setList(chapterDtoList);
    }

    public void save(ChapterDto chapterDto){
        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        if(StringUtils.isEmpty(chapterDto.getId())){
            // 新增
            this.insert(chapter);
        } else {
            // 更新
            this.update(chapter);
        }
    }

    // 新增大章
    private void insert(Chapter chapter){
        // 传进来没有id
        chapter.setId(UuidUtil.getShortUuid());
//        Chapter chapter = new Chapter();
//        BeanUtils.copyProperties(chapterDto, chapter);
//        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        chapterMapper.insert(chapter);
    }

    // 更新大章
    private void update(Chapter chapter){
//        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        chapterMapper.updateByPrimaryKey(chapter);
    }

    public void delete(String id) {
        chapterMapper.deleteByPrimaryKey(id);
    }
}
