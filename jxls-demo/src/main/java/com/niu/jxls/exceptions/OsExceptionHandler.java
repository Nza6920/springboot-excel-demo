package com.niu.jxls.exceptions;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 异常处理器
 *
 * @author [nza]
 * @version 1.0 [2021/06/04 13:34]
 * @createTime [2021/06/04 13:34]
 */
@ControllerAdvice(basePackages = "com.niu.jxls.controller", annotations = {RestController.class})
@Slf4j
public class OsExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> exceptionHandler(MethodArgumentNotValidException ex) {

        log.error("发生异常：", ex);

        Map<String, Object> res = Maps.newHashMap();
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        res.put("message", errors);
        return res;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> exceptionHandler(Exception ex) {

        log.error("发生异常：", ex);

        Map<String, String> res = Maps.newHashMap();
        res.put("message", ex.getMessage());

        return res;
    }
}
