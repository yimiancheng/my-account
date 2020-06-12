package com.yimian.dynamicProxy.jdk;

import com.yimian.dynamicProxy.service.HuaService;
import com.yimian.dynamicProxy.service.TingService;
import com.yimian.dynamicProxy.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Proxy;

/**
 * JdkMain
 * chengshaohua
 *
 * @date 2020/6/12 16:08
 */
@Slf4j
public class JdkMain {
    public static void main(String[] args) {
        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        ServiceImpl target = new ServiceImpl();
        ClassLoader classLoader = JdkMain.class.getClassLoader();
        log.info("classLoader = {}", classLoader.getClass().getSimpleName());

        JdkProxyHandle proxyHandle = new JdkProxyHandle(target);
        Class[] proxyInterface = new Class[] {HuaService.class, TingService.class};

        HuaService huaService = (HuaService) Proxy.newProxyInstance(classLoader, proxyInterface, proxyHandle);
        log.info("huaService class = {}", huaService.getClass().getSimpleName());

        String result = huaService.helloTing("nihao, ting!");
        log.info("result {}", result);
    }
}
