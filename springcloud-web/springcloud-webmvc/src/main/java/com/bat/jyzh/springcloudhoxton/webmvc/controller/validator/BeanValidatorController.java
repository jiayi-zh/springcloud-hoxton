package com.bat.jyzh.springcloudhoxton.webmvc.controller.validator;

import com.bat.jyzh.springcloudhoxton.webmvc.controller.validator.constraints.CustomValidateAno;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * Bean Validator
 *
 * @author ZhengYu
 * @version 1.0 2021/5/22 15:41
 **/
@Slf4j
@RestController
@RequestMapping("/validator")
public class BeanValidatorController {

    /**
     * 最常用的校验方式
     *
     * {
     *     "id": 1,
     *     "num": 1,
     *     "name": "zy",
     *     "inner": {
     *         "order": 1
     *     },
     *     "innerList": [
     *         {
     *             "order": 1
     *         }
     *     ],
     *     "sex": 1
     * }
     */
    @PostMapping("/normal")
    public String testNormalBeanValidator(@RequestBody @Validated(Put.class) Outer outer) {
        log.info("{}", outer);
        return "validator success";
    }

    private Validator validator;

    /**
     * 通过编程的方式校验
     */
    @PostMapping("/programming")
    public String testProgrammingBeanValidator(@RequestBody Outer outer) {
        Set<ConstraintViolation<Outer>> violations = validator.validate(outer, Put.class);
        if (violations.isEmpty()) {
            log.info("{}", outer);
        } else {
            violations.forEach(System.out::println);
        }
        return "validator success";
    }

    @Data
    public static class Outer {

        // group
        @NotNull(groups = Put.class)
        private Long id;

        @Min(value = 1, message = "num必须大于1", groups = {Post.class, Put.class})
        private Integer num;

        @Length(min = 2, message = "姓名长度不合法", groups = {Post.class, Put.class})
        private String name;

        @Valid
        private Inner inner;

        @Valid
        private List<Inner> innerList;

        @CustomValidateAno(groups = {Post.class, Put.class})
        private Integer sex;
    }

    @Data
    public static class Inner {

        @NotNull(groups = {Post.class, Put.class})
        private Integer order;
    }

    /**
     * 标记接口
     */
    public interface Post {
    }

    public interface Put {
    }

    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}
