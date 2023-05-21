package com.dy.sales.flowers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统启动入口
 *
 * @author chao.lan
 */
@Slf4j
@SpringBootApplication
public class SalesFlowersApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesFlowersApplication.class, args);
        log.info("==================鲜花管理系统启动成功==================");
    }

}
