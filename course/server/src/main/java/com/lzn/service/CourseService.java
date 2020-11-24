package com.lzn.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzn.domain.*;
import com.lzn.dto.CourseContentDto;
import com.lzn.dto.CourseDto;
import com.lzn.dto.PageDto;
import com.lzn.dto.SortDto;
import com.lzn.mapper.CourseContentMapper;
import com.lzn.mapper.CourseMapper;
import com.lzn.mapper.TestMapper;
import com.lzn.mapper.my.MyCourseMapper;
import com.lzn.util.CopyUtil;
import com.lzn.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

    import java.util.Date;

@Service
public class CourseService {
    private static final Logger LOG = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private MyCourseMapper myCourseMapper;

    @Autowired
    private CourseCategoryService courseCategoryService;

    @Autowired
    private CourseContentMapper courseContentMapper;


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

    @Transactional
    public void save(CourseDto courseDto){
        Course course = CopyUtil.copy(courseDto, Course.class);
        if(StringUtils.isEmpty(courseDto.getId())){
            this.insert(course);
        } else {
            this.update(course);
        }

        // 批量保存课程分类
        courseCategoryService.saveBatch(course.getId(), courseDto.getCategorys());

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

    /**
     * 更新课程时长
     * @param courseId
     * @return
     */
    public void updateTime(String courseId) {
        LOG.info("更新课程时长：{}", courseId);
        myCourseMapper.updateTime(courseId);
    }

    /**
     * 查找课程内容
     */
    public CourseContentDto findContent(String id) {
        CourseContent content = courseContentMapper.selectByPrimaryKey(id);
        if (content == null) {
            return null;
        }
        return CopyUtil.copy(content, CourseContentDto.class);
    }

    /**
     * 保存课程内容，包含新增和修改
     */
    public int saveContent(CourseContentDto contentDto) {
        CourseContent content = CopyUtil.copy(contentDto, CourseContent.class);
        int i = courseContentMapper.updateByPrimaryKeyWithBLOBs(content);
        if (i == 0) {
            i = courseContentMapper.insert(content);
        }
        return i;
    }

    @Transactional
    public void sort(SortDto sortDto) {
        // 修改当前记录的排序值
        myCourseMapper.updateSort(sortDto);
        // 如果排序值变大
        if (sortDto.getNewSort() > sortDto.getOldSort()){
            myCourseMapper.moveSortsForward(sortDto);
        }

        // 如果排序值变小
        if (sortDto.getNewSort() < sortDto.getOldSort() ){
            myCourseMapper.moveSortsBackward(sortDto);
        }
    }
}
