package com.mao.crowd.controller;

import com.mao.crowd.api.MySQLRemoteService;
import com.mao.crowd.constant.CrowdConstant;
import com.mao.crowd.entity.vo.PortalTypeVO;
import com.mao.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/20 23:27
 * Description:
 */
@Controller
public class PortalController {


    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    /**
     * 获取首页的数据
     * @return
     */
    @GetMapping("/")
    public String showPortalPage(Model model) {

        // 1、调用 MySQLRemoteService 提供的方法，查询首页要显示的数据
        ResultEntity<List<PortalTypeVO>> resultEntity = mySQLRemoteService.getPortalTypeProjectDataRemote();

        // 2、检查查询结果
        String result = resultEntity.getResult();
        if (ResultEntity.SUCCESS.equals(result)) {

            // 3、获取查询结果数据
            List<PortalTypeVO> data = resultEntity.getData();

            // 4、存入模型
            model.addAttribute(CrowdConstant.ATTR_NAME_PORTAL_DATA, data);
        }

        return "portal";
    }
}
