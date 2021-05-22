package com.bat.jyzh.springcloudhoxton.webmvc.controller.validator.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义校验
 *
 * @author ZhengYu
 * @version 1.0 2021/5/22 19:27
 **/
public class CustomValidator implements ConstraintValidator<CustomValidateAno, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value < 2;
    }
}
