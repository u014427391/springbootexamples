package com.example.validated.common.exception;


import com.example.validated.common.rest.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResultBean<?> exception(Exception e) {
        log.error("服务器错误,{}" , e);
        return ResultBean.serverError(e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResultBean<?> exception(MethodArgumentNotValidException e) {
        log.error("参数校验异常,{}" , e.getMessage());
        List<String> res = e.getBindingResult().getAllErrors().stream()
                .map(err -> String.format("%s->%s", ((FieldError) err).getField()  , err.getDefaultMessage()))
                .collect(Collectors.toList());
        return ResultBean.badRequest("参数校验异常" , res);
    }



}
