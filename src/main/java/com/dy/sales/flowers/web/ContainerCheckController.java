package com.dy.sales.flowers.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查
 *
 * @author chao.lan
 */
@Slf4j
@RestController
public class ContainerCheckController {

    @GetMapping(value = {"/ready", "/health"})
    public String ready() {
        return "success";
    }

}
