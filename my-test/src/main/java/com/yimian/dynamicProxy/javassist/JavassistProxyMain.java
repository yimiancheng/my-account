package com.yimian.dynamicProxy.javassist;

import com.yimian.dynamicProxy.javassist.aop.MethodHandlerAop;
import com.yimian.dynamicProxy.service.impl.ServiceImpl;

/**
 * JavassistAopMain
 * chengshaohua
 *
 * @date 2020/6/15 17:25
 */
public class JavassistProxyMain {
    public static void main(String[] args) throws Exception {
        MethodHandlerAop aop = new MethodHandlerAop();
        ServiceImpl proxyService = JavassistProxyFactory.getProxy(ServiceImpl.class, aop);
        proxyService.helloHua("hello hua !");
    }
}
