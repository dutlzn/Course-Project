package com.lzn.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzn.domain.RoleResource;
import com.lzn.domain.RoleResourceExample;
import com.lzn.domain.Test;
import com.lzn.domain.TestExample;
import com.lzn.dto.RoleResourceDto;
import com.lzn.dto.PageDto;
import com.lzn.mapper.RoleResourceMapper;
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
public class RoleResourceService {

    @Autowired
    private RoleResourceMapper roleResourceMapper;


    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RoleResourceExample roleResourceExample = new RoleResourceExample();


        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(roleResourceExample);

        PageInfo<RoleResource> pageInfo = new PageInfo<>(roleResourceList);
        pageDto.setTotal(pageInfo.getTotal());

        List<RoleResourceDto> roleResourceDtoList = new ArrayList<>();

        for(int i = 0;i<roleResourceList.size();++i){
            RoleResource roleResource = roleResourceList.get(i);
            RoleResourceDto roleResourceDto = new RoleResourceDto();
            BeanUtils.copyProperties(roleResource, roleResourceDto);
            roleResourceDtoList.add(roleResourceDto);
        }
        pageDto.setList(roleResourceDtoList);

    }

    public void save(RoleResourceDto roleResourceDto){
        RoleResource roleResource = CopyUtil.copy(roleResourceDto, RoleResource.class);
        if(StringUtils.isEmpty(roleResourceDto.getId())){
            this.insert(roleResource);
        } else {
            this.update(roleResource);
        }
    }

    private void insert(RoleResource roleResource){
        roleResource.setId(UuidUtil.getShortUuid());
        roleResourceMapper.insert(roleResource);
    }


    private void update(RoleResource roleResource){
        roleResourceMapper.updateByPrimaryKey(roleResource);
    }

    public void delete(String id) {
        roleResourceMapper.deleteByPrimaryKey(id);
    }
}
