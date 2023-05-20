package com.dy.sales.flowers;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SalesFlowersApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesFlowersApplication.class, args);
        log.info("==================包花系统启动成功==================");
    }

}
