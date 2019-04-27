package com.greelee.tool.component.exception;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 逻辑服务异常类
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = -9040267815228660186L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

}
