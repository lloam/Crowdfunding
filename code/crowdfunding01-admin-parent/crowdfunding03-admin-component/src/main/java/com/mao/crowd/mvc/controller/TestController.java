package com.mao.crowd.mvc.controller;

import com.mao.crowd.entity.Admin;
import com.mao.crowd.entity.ParamData;
import com.mao.crowd.entity.Student;
import com.mao.crowd.service.api.AdminService;
import com.mao.crowd.util.CrowdUtil;
import com.mao.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Author: Administrator
 * Date: 2021/7/11 13:25
 * Description:
 */
@Controller
public class TestController {

    @Autowired
    private AdminService adminService;
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/test/ssm.html")
    public String testSsm(Model model, HttpServletRequest request){
        boolean judgeRequestType = CrowdUtil.judgeRequestType(request);
        logger.info("judgeResult = " + judgeRequestType);
        List<Admin> adminList = adminService.getAll();
        model.addAttribute("adminList",adminList);
//        String a = null;
//        System.out.println(a.length());
        System.out.println(10/0);
        return "target";
    }

    @ResponseBody
    @RequestMapping("/send/array/one.html")
    public String testReceiverArrayOne(@RequestParam("array[]") List<Integer> array){
        for (Integer number : array) {
            System.out.println("number = " + number);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/array/two.html")
    public String testReceiverArrayTwo(ParamData paramData){
        List<Integer> array = paramData.getArray();
        for (Integer number : array) {
            System.out.println("number = " + number);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/array/three.html")
    public String testReceiverArrayThree(@RequestBody List<Integer> array){

        for (Integer number : array) {
            logger.info("number = " + number);
        }
        return "success";
    }


    @ResponseBody
    @RequestMapping("/send/compose/object.json")
    public ResultEntity<Student> testReceiveComposeObject(@RequestBody Student student,
                                                          HttpServletRequest request) {

        boolean judgeRequestType = CrowdUtil.judgeRequestType(request);
        logger.info("judgeResult = " + judgeRequestType);

        logger.info(student.toString());

        String a = null;
        System.out.println(a.length());

        // 将“查询”到的 Student 对象封装到 ResultEntity 中返回
        return ResultEntity.successWithData(student);
    }

    @ResponseBody
    @RequestMapping("/test/ajax/async.html")
    public String testAsync() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return "success";
    }
}
