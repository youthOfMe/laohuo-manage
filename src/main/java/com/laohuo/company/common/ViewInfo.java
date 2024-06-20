package com.laohuo.company.common;

/**
 * 场景信息枚举
 */
public enum ViewInfo {

    HomeView(1, "首页"),
    MainView(2, "主要界面");

    /**
     * 场景编码
     */
    private Integer code;

    /**
     * 场景信息
     */
    private String message;

    ViewInfo(Integer code) {
        this.code = code;
    }

    ViewInfo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
