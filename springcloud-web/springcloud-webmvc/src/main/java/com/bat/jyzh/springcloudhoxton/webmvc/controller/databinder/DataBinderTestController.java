package com.bat.jyzh.springcloudhoxton.webmvc.controller.databinder;

import com.bat.jyzh.springcloudhoxton.webmvc.vo.CustomReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 数据绑定测试
 *
 * @author ZhengYu
 * @version 1.0 2021/5/19 12:03
 **/
@Slf4j
@Controller
@RequestMapping("/binder")
public class DataBinderTestController {

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter DF_SHOW = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ModelAttribute
    public void runBeforeHandle(HttpServletRequest request) {
        log.info("before handle ...");
    }

    @InitBinder
    public void initBinderDateToLocalDatetime(WebDataBinder wdb) {
        // 如果配置多个, 则后配置的覆盖前面的
        wdb.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public String getAsText() {
                final LocalDateTime localDateTime = (LocalDateTime) getValue();
                return localDateTime.format(DF_SHOW);
            }

            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                // 将 text 转换为 LocalDateTime
                if (StringUtils.isEmpty(text)) {
                    throw new RuntimeException("不能为空");
                }
                setValue(LocalDateTime.parse(text, DF));
            }
        });
    }

    /**
     * 将传参中的 String 类型的日期转换为 LocalDateTime
     *
     * @author ZhengYu
     */
    @ResponseBody
    @GetMapping("/date/to/datetime")
    public void testFormatData(LocalDateTime date, CustomReq customReq) {
        log.info("com.bat.jyzh.springcloudhoxton.webmvc.controller.databinder.DataBinderTestController.testFormatData: {} {}", date, customReq);
    }
}
