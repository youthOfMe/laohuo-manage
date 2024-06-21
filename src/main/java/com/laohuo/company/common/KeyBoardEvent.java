package com.laohuo.company.common;

/**
 * 按键事件
 */
public enum KeyBoardEvent {

    Login(101, "用户登录"),
    Register(102, "用户注册"),
    Exit(103, "用户退出"),
    PersonInfo(201, "查看个人信息"),
    UpdatePassword(202, "修改用户密码"),
    Report(203, "汇报工作/查看汇报"),
    GetSalary(204, "查看薪水");

    /**
     * 按键编码
     */
    private Integer code;

    /**
     * 按键信息
     */
    private String message;

    KeyBoardEvent(Integer code) {
        this.code = code;
    }

    KeyBoardEvent(Integer code, String message) {
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
