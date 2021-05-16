package com.bat.jyzh.xxxjob.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * XxxJob 属性配置
 *
 * @author ZhengYu
 * @version 1.0 2021/5/16 0:12
 **/
@Data
@ConfigurationProperties(prefix = "xxx.job.admin")
public class XxxJobProperties {

    private String host;

    private Integer port;

    private String prefix;

    @PostConstruct
    public void initDefaultValue() {
        if (!StringUtils.hasText(host)) {
            host = "127.0.0.1";
        }
        if (port == null || port == 0) {
            port = 8080;
        }
        if (!StringUtils.hasText(prefix)) {
            prefix = "xxl-job-admin";
        }
    }

    public String getAddress() {
        return String.format("http://%s:%d/%s", host, port, prefix);
    }
}
