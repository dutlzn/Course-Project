package com.lzn.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzn.domain.Teacher;
import com.lzn.domain.TeacherExample;
import com.lzn.domain.Test;
import com.lzn.domain.TestExample;
import com.lzn.dto.TeacherDto;
import com.lzn.dto.PageDto;
import com.lzn.mapper.TeacherMapper;
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
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;


    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        TeacherExample teacherExample = new TeacherExample();


        List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);

        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
        pageDto.setTotal(pageInfo.getTotal());

        List<TeacherDto> teacherDtoList = new ArrayList<>();

        for(int i = 0;i<teacherList.size();++i){
            Teacher teacher = teacherList.get(i);
            TeacherDto teacherDto = new TeacherDto();
            BeanUtils.copyProperties(teacher, teacherDto);
            teacherDtoList.add(teacherDto);
        }
        pageDto.setList(teacherDtoList);

    }

    public void save(TeacherDto teacherDto){
        Teacher teacher = CopyUtil.copy(teacherDto, Teacher.class);
        if(StringUtils.isEmpty(teacherDto.getId())){
            this.insert(teacher);
        } else {
            this.update(teacher);
        }
    }

    private void insert(Teacher teacher){
        teacher.setId(UuidUtil.getShortUuid());
        teacherMapper.insert(teacher);
    }


    private void update(Teacher teacher){
        teacherMapper.updateByPrimaryKey(teacher);
    }

    public void delete(String id) {
        teacherMapper.deleteByPrimaryKey(id);
    }
}
