package com.yimian.dynamicProxy.javassist.aop;

import com.yimian.util.JsonUtils;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import lombok.extern.slf4j.Slf4j;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

/**
 * MethodHandlerAop
 * chengshaohua
 *
 * @date 2020/6/16 14:44
 */
@Slf4j
public class MethodHandlerAop implements MethodHandler, MethodFilter {
    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        log.info("self class = {}", self.getClass().toString());
        log.info("thisMethod Method = {}", thisMethod.getDeclaringClass().getName() + " | " + thisMethod.getName());
        log.info("proceed Method = {}", proceed.getDeclaringClass().getName() + " | " + proceed.getName());
        log.info("args class = {}", JsonUtils.toJson(args));
        // 错误 thisMethod.invoke(self, args);
        Object result = proceed.invoke(self, args);
        log.info("aop {}", result);
        return result;
    }

    @Override
    public boolean isHandled(Method m) {
        log.info("isHandled Method = {}", m.getName());
        return false;
    }
}
