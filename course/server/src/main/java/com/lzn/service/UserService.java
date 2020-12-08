package com.lzn.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzn.domain.User;
import com.lzn.domain.UserExample;
import com.lzn.domain.Test;
import com.lzn.domain.TestExample;
import com.lzn.dto.UserDto;
import com.lzn.dto.PageDto;
import com.lzn.mapper.UserMapper;
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
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        UserExample userExample = new UserExample();


        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        pageDto.setTotal(pageInfo.getTotal());

        List<UserDto> userDtoList = new ArrayList<>();

        for(int i = 0;i<userList.size();++i){
            User user = userList.get(i);
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDtoList.add(userDto);
        }
        pageDto.setList(userDtoList);

    }

    public void save(UserDto userDto){
        User user = CopyUtil.copy(userDto, User.class);
        if(StringUtils.isEmpty(userDto.getId())){
            this.insert(user);
        } else {
            this.update(user);
        }
    }

    private void insert(User user){
        user.setId(UuidUtil.getShortUuid());
        userMapper.insert(user);
    }


    private void update(User user){
        userMapper.updateByPrimaryKey(user);
    }

    public void delete(String id) {
        userMapper.deleteByPrimaryKey(id);
    }
}
