package com.lzn.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzn.domain.Section;
import com.lzn.domain.SectionExample;
import com.lzn.domain.Test;
import com.lzn.domain.TestExample;
import com.lzn.dto.SectionDto;
import com.lzn.dto.PageDto;
import com.lzn.dto.SectionPageDto;
import com.lzn.mapper.SectionMapper;
import com.lzn.mapper.TestMapper;
import com.lzn.util.CopyUtil;
import com.lzn.util.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

    import java.util.Date;

@Service
public class SectionService {

    @Autowired
    private SectionMapper sectionMapper;

    @Autowired
    private CourseService courseService;


    public void list(SectionPageDto sectionPageDto) {

        PageHelper.startPage(sectionPageDto.getPage(), sectionPageDto.getSize());
        SectionExample sectionExample = new SectionExample();
        SectionExample.Criteria criteria = sectionExample.createCriteria();
        if (!StringUtils.isEmpty(sectionPageDto.getCourseId())) {
            criteria.andCourseIdEqualTo(sectionPageDto.getCourseId());
        }
        if (!StringUtils.isEmpty(sectionPageDto.getChapterId())) {
            criteria.andChapterIdEqualTo(sectionPageDto.getChapterId());
        }

        sectionExample.setOrderByClause("sort asc");
        // 查找


        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);

        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        sectionPageDto.setTotal(pageInfo.getTotal());

        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList, SectionDto.class);
        sectionPageDto.setList(sectionDtoList);

    }

    public void save(SectionDto sectionDto){
        Section section = CopyUtil.copy(sectionDto, Section.class);
        if(StringUtils.isEmpty(sectionDto.getId())){
            this.insert(section);
        } else {
            this.update(section);
        }
        courseService.updateTime(sectionDto.getCourseId());
    }

    private void insert(Section section){
            Date now = new Date();
            section.setCreatedAt(now);
            section.setUpdatedAt(now);
        section.setId(UuidUtil.getShortUuid());
        sectionMapper.insert(section);
    }


    private void update(Section section){
        section.setUpdatedAt(new Date());
        sectionMapper.updateByPrimaryKey(section);
    }

    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey(id);
    }
}
