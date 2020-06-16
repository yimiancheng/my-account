package com.yimian.dynamicProxy.javassist;

import com.yimian.dynamicProxy.cglib.CglibMain;
import com.yimian.dynamicProxy.javassist.aop.MethodHandlerAop;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Method;

/**
 * JavassistProxyFactory
 * chengshaohua
 *
 * @date 2020/6/16 14:35
 */
@Slf4j
public class JavassistProxyFactory {
    public static <T> T getProxy(Class<T> targetClass, MethodHandlerAop aop) throws Exception {
        //代理工厂
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.writeDirectory = JavassistProxyFactory.class.getResource("/").getPath();
        log.info("writeDirectory = {}", proxyFactory.writeDirectory);
        proxyFactory.setSuperclass(targetClass);
        //方法过期
        // proxyFactory.setHandler();
        T obj = (T) proxyFactory.createClass().newInstance();
        ((ProxyObject) obj).setHandler(aop);
        return obj;
    }
}
