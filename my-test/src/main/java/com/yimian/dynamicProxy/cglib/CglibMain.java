package com.yimian.dynamicProxy.cglib;

import com.yimian.dynamicProxy.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.core.DebuggingClassWriter;

/**
 * CglibMain
 * chengshaohua
 *
 * @date 2020/6/12 16:22
 */
@Slf4j
public class CglibMain {
    public static void main(String[] args) {
        String path = CglibMain.class.getResource("/").getPath();
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, path);
        CglibProxyHandle cglibProxyHandle = new CglibProxyHandle();
        ServiceImpl service = cglibProxyHandle.getInstance(ServiceImpl.class);
        log.info("service class = {}", service.getClass());
        String result = service.helloHua("hello hua!");
        System.out.println(result);
    }
}
