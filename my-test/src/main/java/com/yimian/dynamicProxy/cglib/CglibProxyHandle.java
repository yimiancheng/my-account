package com.yimian.dynamicProxy.cglib;

import com.yimian.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

/**
 * CglibProxyHandle
 * chengshaohua
 *
 * @date 2020/6/12 16:37
 */
@Slf4j
public class CglibProxyHandle implements MethodInterceptor {
    public <T> T getInstance(Class<T> targetClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        log.info("target = {} | method = {} | methodProxy = {}", target.getClass(),
            method, methodProxy);
        Object result = methodProxy.invokeSuper(target, args);
        log.info("args = {} | result = {}", JsonUtils.toJson(args), JsonUtils.toJson(result));
        return result;
    }
}
