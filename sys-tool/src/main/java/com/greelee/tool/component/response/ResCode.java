package com.greelee.tool.component.response;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
public enum ResCode {
    /**
     * 成功请求
     */
    OK(1000, "请求成功"),
    /**
     * 未授权
     */
    NO_AUTH(1001, "请求未授权"),
    /**
     * 服务器处理异常
     */
    HANDLE_EXCEPTION(1002, "服务器处理异常"),
    /**
     * 找不到请求所需数据
     */
    NOT_FOUND(1003, "找不到请求所需数据"),
    /**
     * 登录信息错误
     */
    INCORRECT_LOGIN_INFO(1004, "登录信息错误"),
    /**
     * 未登录
     */
    NOT_LOGIN(1005, "未登录"),
    /**
     * 请求错误
     */
    FAILED(1006, "请求错误"),
    /**
     * 请求参数不合法
     */
    ILLEGAL_PARAM(1007, "请求参数不合法"),
    /**
     * 唯一参数已存在
     */
    UNIQUE_PARAM_EXISTS(1008, "唯一参数已存在");

    /**
     * 响应值
     */
    private int value;

    /**
     * 描述
     */
    private String description;

    ResCode(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.value + " " + name();
    }
}
