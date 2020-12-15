package com.lzn.service;

import com.alibaba.fastjson.JSON;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class ResourceService {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceService.class);
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

    /**
     * 新增ID 是自定义好的，不是自动生成的
     * @param resource
     */
    private void insert(Resource resource){
//        resource.setId(UuidUtil.getShortUuid());
        resourceMapper.insert(resource);
    }


    private void update(Resource resource){
        resourceMapper.updateByPrimaryKey(resource);
    }

    public void delete(String id) {
        resourceMapper.deleteByPrimaryKey(id);
    }

    /**
     * 保存资源树
     * @param json
     */
    @Transactional
    public void saveJson(String json) {
        List<ResourceDto> jsonList = JSON.parseArray(json, ResourceDto.class);
        List<ResourceDto> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jsonList)) {
            for (ResourceDto d: jsonList) {
                d.setParent("");
                add(list, d);
            }
        }
        LOG.info("共{}条", list.size());

        resourceMapper.deleteByExample(null);
        for (int i = 0; i < list.size(); i++) {
            this.insert(CopyUtil.copy(list.get(i), Resource.class));
        }
    }

    /**
     * 递归，将树型结构的节点全部取出来，放到list
     * @param list
     * @param dto
     */
    public void add(List<ResourceDto> list, ResourceDto dto) {
        list.add(dto);
        if (!CollectionUtils.isEmpty(dto.getChildren())) {
            for (ResourceDto d: dto.getChildren()) {
                d.setParent(dto.getId());
                add(list, d);
            }
        }
    }
}
