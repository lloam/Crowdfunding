package com.mao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Administrator
 * Date: 2021/7/18 14:55
 * Description:
 */
@RestController
public class HelloController {

    @GetMapping("/get/spring/boot/hello/message")
    public String getMessage() {
        return "first blood";
    }

}
