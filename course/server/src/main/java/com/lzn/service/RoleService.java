package com.lzn.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzn.domain.Role;
import com.lzn.domain.RoleExample;
import com.lzn.domain.Test;
import com.lzn.domain.TestExample;
import com.lzn.dto.RoleDto;
import com.lzn.dto.PageDto;
import com.lzn.mapper.RoleMapper;
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
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;


    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RoleExample roleExample = new RoleExample();


        List<Role> roleList = roleMapper.selectByExample(roleExample);

        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        pageDto.setTotal(pageInfo.getTotal());

        List<RoleDto> roleDtoList = new ArrayList<>();

        for(int i = 0;i<roleList.size();++i){
            Role role = roleList.get(i);
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            roleDtoList.add(roleDto);
        }
        pageDto.setList(roleDtoList);

    }

    public void save(RoleDto roleDto){
        Role role = CopyUtil.copy(roleDto, Role.class);
        if(StringUtils.isEmpty(roleDto.getId())){
            this.insert(role);
        } else {
            this.update(role);
        }
    }

    private void insert(Role role){
        role.setId(UuidUtil.getShortUuid());
        roleMapper.insert(role);
    }


    private void update(Role role){
        roleMapper.updateByPrimaryKey(role);
    }

    public void delete(String id) {
        roleMapper.deleteByPrimaryKey(id);
    }
}
