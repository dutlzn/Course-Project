package com.lzn.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzn.domain.Course;
import com.lzn.domain.CourseExample;
import com.lzn.domain.Test;
import com.lzn.domain.TestExample;
import com.lzn.dto.CourseDto;
import com.lzn.dto.PageDto;
import com.lzn.mapper.CourseMapper;
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
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;


    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseExample courseExample = new CourseExample();

        courseExample.setOrderByClause("sort asc");

        List<Course> courseList = courseMapper.selectByExample(courseExample);

        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        pageDto.setTotal(pageInfo.getTotal());

        List<CourseDto> courseDtoList = new ArrayList<>();

        for(int i = 0;i<courseList.size();++i){
            Course course = courseList.get(i);
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(course, courseDto);
            courseDtoList.add(courseDto);
        }
        pageDto.setList(courseDtoList);

    }

    public void save(CourseDto courseDto){
        Course course = CopyUtil.copy(courseDto, Course.class);
        if(StringUtils.isEmpty(courseDto.getId())){
            this.insert(course);
        } else {
            this.update(course);
        }
    }

    private void insert(Course course){
            Date now = new Date();
            course.setCreatedAt(now);
            course.setUpdatedAt(now);
        course.setId(UuidUtil.getShortUuid());
        courseMapper.insert(course);
    }


    private void update(Course course){
        course.setUpdatedAt(new Date());
        courseMapper.updateByPrimaryKey(course);
    }

    public void delete(String id) {
        courseMapper.deleteByPrimaryKey(id);
    }
}
