package com.greelee.log.advise;

import com.greelee.log.manager.ExceptionLogRequest;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.component.response.ResCode;
import com.greelee.tool.component.response.ResResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    private ExceptionLogRequest exceptionLogRequest;

    @Autowired
    public ExceptionControllerAdvice(ExceptionLogRequest exceptionLogRequest) {
        this.exceptionLogRequest = exceptionLogRequest;
    }

    @ExceptionHandler({Exception.class, Throwable.class})
    @ResponseBody
    public String handleException(HttpServletRequest request, HttpServletResponse response, Throwable e) {
        /*
         * 返回异常信息给前端
         */
        // 保存异常信息
        try {
            exceptionLogRequest.saveExceptionLog(request, e);
        } catch (ServiceException e1) {
            e1.printStackTrace();
        }
        // 返回 json 错误信息
        ResResult result = getResultByException(ResResult.fail(ResCode.HANDLE_EXCEPTION), e);
        return result.getStr();
    }

    /**
     * 获取返回结果
     */
    private ResResult getResultByException(ResResult result, Throwable e) {
        Class clazz = e.getClass();
        if (clazz.equals(MissingServletRequestParameterException.class)) {
            result.setMsg("请求参数不全");
            result.setResCode(ResCode.FAILED);
            return result;
        }
        // shiro 登录
        if (e instanceof AuthenticationException) {
            result.setResCode(ResCode.INCORRECT_LOGIN_INFO);
            if (clazz.equals(UnknownAccountException.class)) {
                result.setMsg("用户名密码错误");
            } else if (clazz.equals(IncorrectCredentialsException.class)) {
                result.setMsg("用户名密码错误");
            } else {
                result.setMsg("登录错误");
            }
            return result;
        }
        // shiro 权限
        if (e instanceof AuthorizationException) {
            result.setMsg("权限异常");
            result.setResCode(ResCode.NO_AUTH);
            return result;
        }
        if (clazz.equals(SQLException.class)) {
            result.setMsg("操作数据库异常");
            return result;
        }
        if (clazz.equals(UncategorizedSQLException.class)) {
            result.setMsg("操作数据库异常");
            return result;
        }
        if (clazz.equals(NullPointerException.class)) {
            result.setMsg("调用了未经初始化的对象或者是不存在的对象");
            return result;
        }
        if (clazz.equals(IOException.class)) {
            result.setMsg("IO异常");
            return result;
        }
        if (clazz.equals(ClassNotFoundException.class)) {
            result.setMsg("指定的类不存在");
            return result;
        }
        if (clazz.equals(ArithmeticException.class)) {
            result.setMsg("数学运算异常");
            return result;
        }
        if (clazz.equals(ArrayIndexOutOfBoundsException.class)) {
            result.setMsg("数组下标越界");
            return result;
        }
        if (clazz.equals(IllegalArgumentException.class)) {
            result.setMsg("方法的参数错误");
            return result;
        }
        if (clazz.equals(ClassCastException.class)) {
            result.setMsg("类型强制转换错误");
            return result;
        }
        if (clazz.equals(SecurityException.class)) {
            result.setMsg("违背安全原则异常");
            return result;
        }
        if (clazz.equals(NoSuchMethodError.class)) {
            result.setMsg("方法末找到异常");
            return result;
        }
        if (clazz.equals(InternalError.class)) {
            result.setMsg("Java虚拟机发生了内部错误");
            return result;
        }
        return result;
    }
}
