package com.lzn.business.controller;

import com.lzn.dto.ResponseDto;
import com.lzn.exception.ValidatorException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一控制层异常处理器
 */
@ControllerAdvice
public class ControllerException {

    @ExceptionHandler(value = ValidatorException.class)
    @ResponseBody
    public ResponseDto validatorExceptionHandle(ValidatorException e) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        responseDto.setMessage("请求参数异常！");
        return responseDto;
    }
}
