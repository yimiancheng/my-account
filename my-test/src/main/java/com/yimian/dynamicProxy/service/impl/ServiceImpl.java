package com.yimian.dynamicProxy.service.impl;

import com.yimian.dynamicProxy.service.HuaService;
import com.yimian.dynamicProxy.service.TingService;
import lombok.extern.slf4j.Slf4j;

/**
 * ServiceImpl
 * chengshaohua
 *
 * @date 2020/6/12 15:39
 */
@Slf4j
public class ServiceImpl implements HuaService, TingService {
    public ServiceImpl() {
        log.info("ServiceImpl 构造方法被执行！");
    }

    @Override
    public String helloTing(String say) {
        return this.getClass().getSimpleName() + " | " + say;
    }

    @Override
    public String helloHua(String say) {
        return this.getClass().getSimpleName() + " | " + say;
    }
}
