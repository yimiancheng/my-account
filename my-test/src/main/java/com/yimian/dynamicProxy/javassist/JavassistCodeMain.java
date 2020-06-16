package com.yimian.dynamicProxy.javassist;

import com.yimian.dynamicProxy.service.TingService;
import com.yimian.dynamicProxy.service.impl.ServiceImpl;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.util.proxy.ProxyFactory;
import lombok.extern.slf4j.Slf4j;
import javax.xml.ws.Service;

/**
 * JavassistCodeMain
 * chengshaohua
 *
 * @date 2020/6/12 17:38
 */
@Slf4j
public class JavassistCodeMain {
    public static void main(String[] args) throws Exception {
        // ServiceImpl service = new ServiceImpl();
        ClassPool pool = ClassPool.getDefault();
        //CtClass ctClass = pool.get(ServiceImpl.class.getName());
        CtClass ctClass = pool.get("com.yimian.dynamicProxy.service.impl.ServiceImpl");

        CtMethod ctMethod = ctClass.getDeclaredMethod("helloHua");

        ctMethod.insertBefore("log.info(\"input [{}]\", $$);");
        ctMethod.insertAfter("log.info(\"run over\");");

        TingService service = (TingService) ctClass.toClass().newInstance();
        service.helloHua("hello hua !");
    }
}
