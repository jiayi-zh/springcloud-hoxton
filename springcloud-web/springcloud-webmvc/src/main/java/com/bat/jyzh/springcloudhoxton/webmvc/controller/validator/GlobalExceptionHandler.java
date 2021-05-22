package com.bat.jyzh.springcloudhoxton.webmvc.controller.validator;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author ZhengYu
 * @version 1.0 2021/3/9 9:23
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String PARAM_VALID_MSG = "parameter validation failed";

    @Data
    private static final class CommonResult {

        private int code;

        private String msg;

        private Object t;

        public CommonResult(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public static CommonResult build(int code, String msg) {
            return new CommonResult(code, msg);
        }
    }

    /**
     * 处理参数校验抛出的异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public CommonResult methodArgumentNotValidExceptionHandler(HttpServletRequest request, Exception exception) {
        String msg = PARAM_VALID_MSG;
        Optional<List<ObjectError>> validExceptionListOptional = Optional.ofNullable(exception)
                .map(e -> {
                    if (e instanceof MethodArgumentNotValidException) {
                        return ((MethodArgumentNotValidException) e).getBindingResult();
                    } else if (e instanceof BindException) {
                        return ((BindException) e).getBindingResult();
                    } else {
                        return null;
                    }
                })
                .map(BindingResult::getAllErrors);
        if (validExceptionListOptional.isPresent()) {
            msg = validExceptionListOptional.get().stream()
                    .map(objectError -> {
                        String unValidField = "";
                        Class<?> clazz = objectError.getClass();
                        try {
                            Field field = ReflectionUtils.findField(clazz, "field");
                            if (field != null) {
                                field.setAccessible(true);
                                unValidField = field.get(objectError).toString();
                            }
                        } catch (Exception e) {
                            log.error("参数校验异常处理 , 获取异常字段失败, 原因: {} {}", e.getMessage(), e);
                        }
                        return String.format("字段[%s]校验失败, 原因: %s", unValidField, objectError.getDefaultMessage());
                    })
                    .collect(Collectors.joining("\r\n"));
        }

        log.error("请求 {} 参数校验未通过, 原因: {}", extractRequestInfo(request), msg);
        return CommonResult.build(-1, msg);
    }

    /**
     * 提取参数用于日志打印
     *
     * @param request HttpServletRequest
     * @author ZhengYu
     */
    private Object extractRequestInfo(HttpServletRequest request) {
        return String.format("%s %s", request.getMethod(), request.getRequestURI());
    }

    /**
     * 处理其他未捕获异常
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult unExpectExceptionHandler(HttpServletRequest request, Exception exception) {
        log.error("请求 {} 异常, 原因: {}", extractRequestInfo(request), exception);
        return CommonResult.build(-1, exception.getMessage());
    }
}