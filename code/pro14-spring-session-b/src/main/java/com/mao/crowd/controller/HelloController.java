package com.mao.crowd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Author: Administrator
 * Date: 2021/7/21 21:41
 * Description:
 */
@RestController
public class HelloController {

    @RequestMapping("/test/spring/session/retrieve")
    public String testSession(HttpSession session) {

        String value = (String) session.getAttribute("king");

        return value;
    }
}
