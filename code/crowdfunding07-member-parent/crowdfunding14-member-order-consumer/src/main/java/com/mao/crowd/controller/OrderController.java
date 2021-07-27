package com.mao.crowd.controller;

import com.mao.crowd.api.MySQLRemoteService;
import com.mao.crowd.constant.CrowdConstant;
import com.mao.crowd.entity.vo.AddressVO;
import com.mao.crowd.entity.vo.MemberLoginVO;
import com.mao.crowd.entity.vo.OrderProjectVO;
import com.mao.crowd.util.ResultEntity;
import org.apache.tomcat.jni.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/25 11:27
 * Description:
 */
@Controller
public class OrderController {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    /**
     * 根据回报 id
     * 查询出对象的项目，回报
     * 封装成 OrderProjectVO 返回给前端展示
     * @param returnId
     * @param session   为了能够在后续操作中保持 orderProjectVO 数据，将 orderProjectVO 保存在 session 中
     * @return
     */
    @GetMapping("/confirm/return/info/{returnId}")
    public String showReturnConfirmInfo(
            @PathVariable("returnId") Integer returnId,
            HttpSession session
    ) {

        ResultEntity<OrderProjectVO> resultEntity = mySQLRemoteService.getOrderProjectVORemote(returnId);

        if(ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            OrderProjectVO orderProjectVO = resultEntity.getData();

            session.setAttribute(CrowdConstant.ATTR_NAME_ORDER_PROJECT_VO, orderProjectVO);
        }
        return "confirm-return";
    }


    /**
     * 合并 session 域的 orderprojectVO 与 returnCount
     * @param returnCount
     * @param session
     * @return
     */
    @GetMapping("/confirm/order/{returnCount}")
    public String showConfirmOrderInfo(
            @PathVariable("returnCount") Integer returnCount,
            HttpSession session) {

        // 1、把接收到的回报数量合并到 session 域
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_ORDER_PROJECT_VO);

        orderProjectVO.setReturnCount(returnCount);

        // 将 session 存回 session，同步 redis 数据
        session.setAttribute(CrowdConstant.ATTR_NAME_ORDER_PROJECT_VO, orderProjectVO);

        // 2、获取当前登录用户的 id
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

        Integer memberId = memberLoginVO.getId();

        // 2、查询目前的收获地址数据
        ResultEntity<List<AddressVO>> resultEntity = mySQLRemoteService.getAddressVORemote(memberId);

        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            // 获取地址信息
            List<AddressVO> data = resultEntity.getData();
            // 存入 session 域
            session.setAttribute(CrowdConstant.ATTR_NAME_ADDRESSS_VO_LIST, data);
        }

        return "confirm-order";
    }


    /**
     * 保存用户输入的地址信息
     * @param addressVO
     * @param session
     * @return
     */
    @PostMapping("/save/address")
    public String saveAddress(AddressVO addressVO,
                              HttpSession session) {

        // 1、执行地址信息的保存
        ResultEntity<String> resultEntity = mySQLRemoteService.saveAddressRemote(addressVO);

        logger.debug("地址信息保存结果：{}",resultEntity.getResult());

        // 从 session 域获取 orderProjectVO 对象
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_ORDER_PROJECT_VO);

        // 获取 returnCount
        Integer returnCount = orderProjectVO.getReturnCount();

        // 重定向到这个方法，目的是将保存的地址信息也查询出来显示给前端
        return "redirect:/order/confirm/order/"+returnCount;
    }


}
