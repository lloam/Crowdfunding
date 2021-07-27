package com.mao.crowd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Author: Administrator
 * Date: 2021/7/21 21:39
 * Description:
 */
@RestController
public class HelloController {

    @RequestMapping("/test/spring/session/save")
    public String testSession(HttpSession session) {

        session.setAttribute("king","hello-king");

        return "数据存入 Session 域";
    }
}
