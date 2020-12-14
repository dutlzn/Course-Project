package com.lzn.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzn.domain.Resource;
import com.lzn.domain.ResourceExample;
import com.lzn.domain.Test;
import com.lzn.domain.TestExample;
import com.lzn.dto.ResourceDto;
import com.lzn.dto.PageDto;
import com.lzn.mapper.ResourceMapper;
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
public class ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;


    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ResourceExample resourceExample = new ResourceExample();


        List<Resource> resourceList = resourceMapper.selectByExample(resourceExample);

        PageInfo<Resource> pageInfo = new PageInfo<>(resourceList);
        pageDto.setTotal(pageInfo.getTotal());

        List<ResourceDto> resourceDtoList = new ArrayList<>();

        for(int i = 0;i<resourceList.size();++i){
            Resource resource = resourceList.get(i);
            ResourceDto resourceDto = new ResourceDto();
            BeanUtils.copyProperties(resource, resourceDto);
            resourceDtoList.add(resourceDto);
        }
        pageDto.setList(resourceDtoList);

    }

    public void save(ResourceDto resourceDto){
        Resource resource = CopyUtil.copy(resourceDto, Resource.class);
        if(StringUtils.isEmpty(resourceDto.getId())){
            this.insert(resource);
        } else {
            this.update(resource);
        }
    }

    private void insert(Resource resource){
        resource.setId(UuidUtil.getShortUuid());
        resourceMapper.insert(resource);
    }


    private void update(Resource resource){
        resourceMapper.updateByPrimaryKey(resource);
    }

    public void delete(String id) {
        resourceMapper.deleteByPrimaryKey(id);
    }
}
