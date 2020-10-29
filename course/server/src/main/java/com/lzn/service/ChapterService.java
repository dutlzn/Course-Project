package com.lzn.service;

import com.lzn.domain.Chapter;
import com.lzn.domain.ChapterExample;
import com.lzn.domain.Test;
import com.lzn.domain.TestExample;
import com.lzn.dto.ChapterDto;
import com.lzn.mapper.ChapterMapper;
import com.lzn.mapper.TestMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;


    public List<ChapterDto> list() {
        ChapterExample chapterExample = new ChapterExample();
//        chapterExample.createCriteria().andIdEqualTo("1");
//        chapterExample.setOrderByClause("id desc ");
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        List<ChapterDto> chapterDtoList = new ArrayList<>();

        for(int i = 0;i<chapterList.size();++i){
            Chapter chapter = chapterList.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter, chapterDto);
            chapterDtoList.add(chapterDto);
        }


        return chapterDtoList;


    }
}
