package com.lzn.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzn.domain.Chapter;
import com.lzn.domain.ChapterExample;
import com.lzn.domain.Test;
import com.lzn.domain.TestExample;
import com.lzn.dto.ChapterDto;
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


//    public List<ChapterDto> list(PageDto pageDto) {
    public void list(PageDto pageDto) {

//        PageHelper.startPage(1,1);
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ChapterExample chapterExample = new ChapterExample();
//        chapterExample.createCriteria().andIdEqualTo("1");
//        chapterExample.setOrderByClause("id desc ");
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);

        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        pageDto.setTotal(pageInfo.getTotal());

        List<ChapterDto> chapterDtoList = new ArrayList<>();

        for(int i = 0;i<chapterList.size();++i){
            Chapter chapter = chapterList.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter, chapterDto);
            chapterDtoList.add(chapterDto);
        }
        pageDto.setList(chapterDtoList);

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
