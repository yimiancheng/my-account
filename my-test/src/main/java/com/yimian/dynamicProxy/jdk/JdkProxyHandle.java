package com.yimian.dynamicProxy.jdk;

import com.yimian.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JdkProxyHandle
 * chengshaohua
 *
 * @date 2020/6/12 15:42
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JdkProxyHandle implements InvocationHandler {
    private Object target;

    @Override
    public Object invoke(Object proxyObject, Method method, Object[] args) throws Throwable {
        log.info("target proxy = {} | class = {} | method name = {}", proxyObject.getClass(),
            target.getClass().getSimpleName(), method.getName());
        Object result =  method.invoke(target, args);
        log.info("result {}", JsonUtils.toJson(result));
        return result;
    }
}
