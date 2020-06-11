package com.yimian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Application
 * chengshaohua
 *
 * @date 2020/6/10 10:55
 */
@SpringBootApplication
@PropertySource(value = {"classpath:important.properties", "classpath:thymeleaf.properties"},
    ignoreResourceNotFound = false, encoding = "UTF-8")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
