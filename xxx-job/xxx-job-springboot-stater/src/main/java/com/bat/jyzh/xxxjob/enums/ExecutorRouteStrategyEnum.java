package com.bat.jyzh.xxxjob.enums;

/**
 * 任务路由策略
 *
 * @author ZhengYu
 * @version 1.0 2021/5/16 1:38
 **/
public enum ExecutorRouteStrategyEnum {
    FIRST("jobconf_route_first", ""),
    LAST("jobconf_route_first", ""),
    ROUND("jobconf_route_first", ""),
    RANDOM("jobconf_route_first", ""),
    CONSISTENT_HASH("jobconf_route_first", ""),
    LEAST_FREQUENTLY_USED("jobconf_route_first", ""),
    LEAST_RECENTLY_USED("jobconf_route_first", ""),
    FAILOVER("jobconf_route_first", ""),
    BUSYOVER("jobconf_route_first", ""),
    SHARDING_BROADCAST("jobconf_route_first", ""),
    ;

    private String title;

    private String value;

    ExecutorRouteStrategyEnum(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
