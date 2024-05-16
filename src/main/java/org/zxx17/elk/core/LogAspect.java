package org.zxx17.elk.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author Xinxuan Zhuo
 * @version 2024/5/16
 * <p>
 * 自定义范围来收集日志
 * </p>
 */
@Aspect
@Component
@Slf4j
public class LogAspect {


    /**
     *     定义一个切点，匹配所有Controller下的方法执行
      */

    @Pointcut(value = "execution(public * *.*.*.*.*Controller.*(..))")
    public void controllerMethods() {
    }


    @Around("controllerMethods()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 记录请求信息
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        // 构建日志信息
        ObjectNode logObject = new ObjectMapper().createObjectNode();
        logObject.put("method", request.getMethod());
        logObject.put("class", method.toString());
        logObject.put("url", request.getRequestURL().toString());
        logObject.put("ip", request.getRemoteAddr());
        Object result = joinPoint.proceed();
        logObject.put("result", result.toString());
        log.info("{}", logObject);
        return result;
    }


}
