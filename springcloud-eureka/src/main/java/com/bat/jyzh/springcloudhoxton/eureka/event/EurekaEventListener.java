package com.bat.jyzh.springcloudhoxton.eureka.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Eureka 事件监听
 *
 * @author ZhengYu
 * @version 1.0 2021/2/25 16:08
 **/
@Slf4j
@Component
public class EurekaEventListener {

    @EventListener
    public void handleEurekaRegistryAvailableEvent(EurekaRegistryAvailableEvent event) {
        log.info("EurekaRegistryAvailableEvent(Eureka注册可用事件) -> {}", event);
    }

    @EventListener
    public void handleEurekaServerStartedEvent(EurekaServerStartedEvent event) {
        log.info("EurekaServerStartedEvent(Eureka服务启动事件) -> {}", event);
    }

    @EventListener
    public void handleEurekaInstanceRegisteredEvent(EurekaInstanceRegisteredEvent event) {
        log.info("EurekaInstanceRegisteredEvent -> {}", event);
    }

    @EventListener
    public void handleEurekaInstanceRenewedEvent(EurekaInstanceRenewedEvent event) {
        log.info("EurekaInstanceRenewedEvent -> {}", event);
    }

    @EventListener
    public void handleEurekaInstanceCanceledEvent(EurekaInstanceCanceledEvent event) {
        log.info("EurekaInstanceCanceledEvent -> {}", event);
    }
}
