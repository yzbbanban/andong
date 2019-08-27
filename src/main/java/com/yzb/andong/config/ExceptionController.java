package com.yzb.andong.config;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @date 2018/8/7 13:57
 */
@RestControllerAdvice
public class ExceptionController {

    /**
     * 捕捉shiro的异常
     */
    @ExceptionHandler(ShiroException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultJson handle401(ShiroException e) {
        ResultJson resultJson = new ResultJson();
        resultJson.setStatus(ResultStatus.NO_AUTH);
        return resultJson;
    }

    /**
     * 捕捉UnauthorizedException
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResultJson handle401() {
        ResultJson resultJson = new ResultJson();
        resultJson.setStatus(ResultStatus.NO_AUTH);
        return resultJson;
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResultJson authExceptionHandler(Exception ex){
        ResultJson resultJson = new ResultJson();
        resultJson.setStatus(ResultStatus.NO_AUTH);
        return resultJson;
    }

}

