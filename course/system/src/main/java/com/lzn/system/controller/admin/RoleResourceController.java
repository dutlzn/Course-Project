package com.lzn.system.controller.admin;

import com.lzn.dto.RoleResourceDto;
import com.lzn.dto.PageDto;
import com.lzn.dto.ResponseDto;
import com.lzn.service.RoleResourceService;
import com.lzn.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/roleResource")
public class RoleResourceController {

    public static final String BUSINESS_NAME = "角色资源关联";

    @Autowired
    private RoleResourceService roleResourceService;

    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        roleResourceService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
    @PostMapping("/save")
    public ResponseDto save(@RequestBody RoleResourceDto roleResourceDto){
        // 保存校验
        ValidatorUtil.require(roleResourceDto.getRoleId(), "角色");
        ValidatorUtil.require(roleResourceDto.getResourceId(), "资源");


        ResponseDto responseDto = new ResponseDto();
        roleResourceService.save(roleResourceDto);
        responseDto.setContent(roleResourceDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        roleResourceService.delete(id);
        return responseDto;
    }
}
