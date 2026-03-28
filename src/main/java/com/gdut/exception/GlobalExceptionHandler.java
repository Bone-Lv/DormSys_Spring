package com.gdut.exception;

import com.gdut.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result handle(Exception e){
        log.error("服务器发生异常{}",e.getMessage());
        return Result.fail("服务器发生异常");
    }

    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e){
        String message = e.getMessage();
        log.error("服务器发生异常{}",message);
        int i = message.lastIndexOf("Duplicated entry");
        String errMsg = message.substring(i);
        return Result.fail(errMsg.split(" ")[2] + "已存在");

    }

}
