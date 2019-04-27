package com.greelee.sysmain;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {
        "com.greelee.sysmain",
        "com.greelee.log",
        "com.greelee.auth"
}, exclude = {
        SecurityAutoConfiguration.class,
        DataSourceAutoConfiguration.class
})
@EnableTransactionManagement
@EnableScheduling
@MapperScan(basePackages = {
        "com.greelee.sysmain.dao",
        "com.greelee.log.dao",
        "com.greelee.auth.dao",
})
public class SysMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysMainApplication.class, args);
    }

}
