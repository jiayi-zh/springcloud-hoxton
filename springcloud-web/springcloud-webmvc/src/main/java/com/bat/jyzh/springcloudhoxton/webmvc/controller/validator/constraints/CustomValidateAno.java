package com.bat.jyzh.springcloudhoxton.webmvc.controller.validator.constraints;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CustomValidator.class)
public @interface CustomValidateAno {

    String message() default "自定义注解 - 消息提示";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
