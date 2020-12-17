package com.lzn.system.controller;

import com.lzn.dto.ResponseDto;
import com.lzn.exception.BusinessException;
import com.lzn.exception.ValidatorException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一控制层异常处理器
 */
@ControllerAdvice
public class ControllerException {

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseDto businessExceptionHandle(BusinessException e) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        responseDto.setMessage(e.getCode().getDesc());
        return responseDto;
    }
}
