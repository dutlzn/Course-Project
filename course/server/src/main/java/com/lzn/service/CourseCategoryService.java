package com.lzn.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzn.domain.CourseCategory;
import com.lzn.domain.CourseCategoryExample;
import com.lzn.domain.Test;
import com.lzn.domain.TestExample;
import com.lzn.dto.CategoryDto;
import com.lzn.dto.CourseCategoryDto;
import com.lzn.dto.PageDto;
import com.lzn.mapper.CourseCategoryMapper;
import com.lzn.mapper.TestMapper;
import com.lzn.util.CopyUtil;
import com.lzn.util.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Service
public class CourseCategoryService {

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;


    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();


        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(courseCategoryExample);

        PageInfo<CourseCategory> pageInfo = new PageInfo<>(courseCategoryList);
        pageDto.setTotal(pageInfo.getTotal());

        List<CourseCategoryDto> courseCategoryDtoList = new ArrayList<>();

        for(int i = 0;i<courseCategoryList.size();++i){
            CourseCategory courseCategory = courseCategoryList.get(i);
            CourseCategoryDto courseCategoryDto = new CourseCategoryDto();
            BeanUtils.copyProperties(courseCategory, courseCategoryDto);
            courseCategoryDtoList.add(courseCategoryDto);
        }
        pageDto.setList(courseCategoryDtoList);

    }

    public void save(CourseCategoryDto courseCategoryDto){
        CourseCategory courseCategory = CopyUtil.copy(courseCategoryDto, CourseCategory.class);
        if(StringUtils.isEmpty(courseCategoryDto.getId())){
            this.insert(courseCategory);
        } else {
            this.update(courseCategory);
        }
    }

    private void insert(CourseCategory courseCategory){
        courseCategory.setId(UuidUtil.getShortUuid());
        courseCategoryMapper.insert(courseCategory);
    }


    private void update(CourseCategory courseCategory){
        courseCategoryMapper.updateByPrimaryKey(courseCategory);
    }

    public void delete(String id) {
        courseCategoryMapper.deleteByPrimaryKey(id);
    }


    /**
     * 根据某一课程，先清空课程分类，再保存课程分类
     * @param dtoList
     */
    @Transactional
    public void saveBatch(String courseId, List<CategoryDto> dtoList) {
        CourseCategoryExample example = new CourseCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        courseCategoryMapper.deleteByExample(example);
        for (int i = 0, l = dtoList.size(); i < l; i++) {
            CategoryDto categoryDto = dtoList.get(i);
            CourseCategory courseCategory = new CourseCategory();
            courseCategory.setId(UuidUtil.getShortUuid());
            courseCategory.setCourseId(courseId);
            courseCategory.setCategoryId(categoryDto.getId());
            insert(courseCategory);
        }
    }
}
