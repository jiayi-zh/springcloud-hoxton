package com.bat.jyzh.springcloudhoxton.webmvc.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 请求结构体
 *
 * @author ZhengYu
 * @version 1.0 2021/5/18 15:37
 **/
@Data
public class CustomReq {

    @NotNull
    private Long id;

    private Integer age;

    private String name;
}
