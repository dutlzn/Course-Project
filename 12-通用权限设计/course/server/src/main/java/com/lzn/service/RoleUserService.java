package com.lzn.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzn.domain.RoleUser;
import com.lzn.domain.RoleUserExample;
import com.lzn.domain.Test;
import com.lzn.domain.TestExample;
import com.lzn.dto.RoleUserDto;
import com.lzn.dto.PageDto;
import com.lzn.mapper.RoleUserMapper;
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
public class RoleUserService {

    @Autowired
    private RoleUserMapper roleUserMapper;


    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RoleUserExample roleUserExample = new RoleUserExample();


        List<RoleUser> roleUserList = roleUserMapper.selectByExample(roleUserExample);

        PageInfo<RoleUser> pageInfo = new PageInfo<>(roleUserList);
        pageDto.setTotal(pageInfo.getTotal());

        List<RoleUserDto> roleUserDtoList = new ArrayList<>();

        for(int i = 0;i<roleUserList.size();++i){
            RoleUser roleUser = roleUserList.get(i);
            RoleUserDto roleUserDto = new RoleUserDto();
            BeanUtils.copyProperties(roleUser, roleUserDto);
            roleUserDtoList.add(roleUserDto);
        }
        pageDto.setList(roleUserDtoList);

    }

    public void save(RoleUserDto roleUserDto){
        RoleUser roleUser = CopyUtil.copy(roleUserDto, RoleUser.class);
        if(StringUtils.isEmpty(roleUserDto.getId())){
            this.insert(roleUser);
        } else {
            this.update(roleUser);
        }
    }

    private void insert(RoleUser roleUser){
        roleUser.setId(UuidUtil.getShortUuid());
        roleUserMapper.insert(roleUser);
    }


    private void update(RoleUser roleUser){
        roleUserMapper.updateByPrimaryKey(roleUser);
    }

    public void delete(String id) {
        roleUserMapper.deleteByPrimaryKey(id);
    }
}
