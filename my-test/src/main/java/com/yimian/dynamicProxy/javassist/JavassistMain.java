package com.yimian.dynamicProxy.javassist;

import javassist.*;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Method;

/**
 * JavassistMain
 * chengshaohua
 *
 * @date 2020/6/12 17:27
 */
@Slf4j
public class JavassistMain {
    /**
     * Javassist是一个编辑字节码的框架
     * 它可以在运行期定义或修改Class
     * 使用Javassist实现AOP的原理是在字节码加载前直接修改需要切入的方法,这比使用cglib实现AOP更加高效
     */
    public static void main(String[] args) throws Exception {
        String jvmPath = JavassistMain.class.getResource("/").getPath();
        log.info("类路径 {}", jvmPath);

        Object hua = createClass(jvmPath);
        log.info("hua {}", hua.getClass());

        Class _class = Class.forName("com.yimian.model.Hua");
        log.info("_class {}", _class.getName());

        Method m = _class.getMethod("toJson", String.class);
        Object result = m.invoke(hua, new Object[] {"json"});
        log.info("result {}", result);
    }

    /**
     * 生成java对象
     */
    private static Object createClass(String jvmPath) throws Exception {
        //一个基于HashMap实现的CtClass对象容器，其中键是类名称，值是表示该类的CtClass对象
        //默认的ClassPool使用与底层JVM相同的类路径，因此在某些情况下，可能需要向ClassPool添加类路径或类字节
        ClassPool pool = ClassPool.getDefault();

        //CtClass 表示一个类，这些CtClass对象可以从ClassPool获得
        CtClass ctClass = pool.makeClass("com.yimian.model.Hua");

        CtClass ctClassUtil = pool.getCtClass("com.yimian.util.JsonUtils");

        //CtFields 字段
        CtField nameFild = new CtField(pool.getCtClass("java.lang.String"), "name", ctClass);
        nameFild.setModifiers(Modifier.PRIVATE);
        ctClass.addField(nameFild);

        //CtMethods 方法
        ctClass.addMethod(CtNewMethod.getter("getName", nameFild));
        ctClass.addMethod(CtNewMethod.setter("setName", nameFild));

        CtMethod ctMethod = new CtMethod(pool.getCtClass("java.lang.String"), "toJson",
            new CtClass[] {pool.getCtClass("com.yimian.util.JsonUtils")}, ctClass);
        ctMethod.setModifiers(Modifier.PUBLIC);
        StringBuffer buffer = new StringBuffer();
        buffer.append("{\n")
            .append("System.out.println(\"toJson = \" + JsonUtils.toJson($1));")
            .append("System.out.println(\"toJson => \" + $1);")
            .append("return $1;")
            .append("\n}");
        ctMethod.setBody(buffer.toString());
        ctClass.addMethod(ctMethod);

        //构造方法
        CtConstructor ctConstructor = new CtConstructor(new CtClass[] {}, ctClass);
        buffer = new StringBuffer();
        buffer.append("{\n")
            .append("System.out.println(\"默认构造方法执行\");")
            .append("\n}");
        ctConstructor.setBody(buffer.toString());
        ctClass.addConstructor(ctConstructor);

        //保存到磁盘
        //ctClass.writeFile();
        ctClass.debugWriteFile(jvmPath);
        String code = ctClass.toString();
        log.info("code {}", code);
        Class<?> _class = ctClass.toClass();
        Object obj = _class.newInstance();
        log.info("obj class = {}", obj.getClass());

        return obj;
    }
}
