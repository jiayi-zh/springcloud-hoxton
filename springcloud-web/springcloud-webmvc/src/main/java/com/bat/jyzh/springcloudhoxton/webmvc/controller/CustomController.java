package com.bat.jyzh.springcloudhoxton.webmvc.controller;

import com.bat.jyzh.springcloudhoxton.webmvc.vo.CustomReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 * 测试 {@link org.springframework.web.servlet.FrameworkServlet#doService(HttpServletRequest, HttpServletResponse)}
 *
 * @author ZhengYu
 * @version 1.0 2021/5/18 15:35
 **/
@Slf4j
@RestController
@RequestMapping("/webmvc")
public class CustomController {

    @GetMapping("/get")
    public String testGet(@RequestParam(value = "projectId") Long projectId, CustomReq customReq) {
        log.info("{} --> {} {}", CustomController.class.getName(), projectId, customReq);
        return "success";
    }

    // https://www.cnblogs.com/hujunzheng/p/12570921.html @Validated 原理解析
    @PostMapping("/post")
    public Long testPost(@RequestParam(value = "projectId") Long projectId, @RequestBody @Validated CustomReq customReq) {
        customReq.setId(new Random().nextLong());
        log.info("{} --> {} {}", CustomController.class.getName(), projectId, customReq);
        return customReq.getId();
    }
}
