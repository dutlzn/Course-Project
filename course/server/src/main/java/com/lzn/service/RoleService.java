package com.lzn.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzn.domain.*;
import com.lzn.dto.ResourceDto;
import com.lzn.dto.RoleDto;
import com.lzn.dto.PageDto;
import com.lzn.mapper.ResourceMapper;
import com.lzn.mapper.RoleMapper;
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
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private ResourceMapper resourceMapper;

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


    /**
     * 按角色保存资源
     */
    public void saveResource(RoleDto roleDto) {
        String roleId = roleDto.getId();
        List<String> resourceIds = roleDto.getResourceIds();
        // 清空库中所有的当前角色下的记录
        RoleResourceExample example = new RoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleResourceMapper.deleteByExample(example);

        // 保存角色资源
        for (int i = 0; i < resourceIds.size(); i++) {
            RoleResource roleResource = new RoleResource();
            roleResource.setId(UuidUtil.getShortUuid());
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceIds.get(i));
            roleResourceMapper.insert(roleResource);
        }
    }

    /**
     * 按角色加载资源
     * @param roleId
     */
    // 找一个资源列表
    public List<ResourceDto> listResource(String roleId) {
        RoleResourceExample example = new RoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        // 每个角色 对应 资源的id
        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(example);
        List<ResourceDto> resourceIdList = new ArrayList<>();

        for (int i = 0, l = roleResourceList.size(); i < l; i++) {
            String id = roleResourceList.get(i).getResourceId();
            ResourceExample example2 = new ResourceExample();
            example2.createCriteria().andIdEqualTo(id);
            List<Resource> temp = resourceMapper.selectByExample(example2);
            List<ResourceDto> temp2 = CopyUtil.copyList(temp, ResourceDto.class);
            resourceIdList.add(temp2.get(0));
        }
        return resourceIdList;
    }
}
