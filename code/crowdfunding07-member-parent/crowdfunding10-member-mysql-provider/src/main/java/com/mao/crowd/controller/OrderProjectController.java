package com.mao.crowd.controller;

import com.mao.crowd.entity.vo.AddressVO;
import com.mao.crowd.entity.vo.OrderProjectVO;
import com.mao.crowd.entity.vo.OrderVO;
import com.mao.crowd.service.api.OrderService;
import com.mao.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/25 11:45
 * Description:
 */
@RestController
public class OrderProjectController {

    @Autowired
    private OrderService orderService;


    /**
     * 根据回报 id
     * 查询出对象的项目，回报
     * 封装成 OrderProjectVO
     * 给 feign 暴露方法接口
     * @param returnId
     * @return
     */
    @GetMapping("/get/order/projectVO/remote")
    ResultEntity<OrderProjectVO> getOrderProjectVORemote(
            @RequestParam("returnId") Integer returnId) {

        // 1、业务逻辑交给 service 处理
        try {
            OrderProjectVO orderProjectVO = orderService.getOrderProjectVO(returnId);

            // 成功则直接返回
            return ResultEntity.successWithData(orderProjectVO);
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed("服务器繁忙！请稍后重试！！");
        }
    }


    /**
     * 根据 memberId 获取用户的收获地址信息
     * @param memberId
     * @return
     */
    @GetMapping("/get/addressVO/remote")
    ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId) {

        try {
            List<AddressVO> addressVOList = orderService.getAddressVOList(memberId);

            return ResultEntity.successWithData(addressVOList);
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed("服务器繁忙！请稍后重试！");
        }
    }


    /**
     * 保存用户输入的地址信息
     * @param addressVO
     * @return
     */
    @PostMapping("/save/address/remote")
    ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO) {
        try {
            orderService.saveAddress(addressVO);

            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed("服务器繁忙！请稍后重试！");
        }
    }


    /**
     * 保存订单接口
     * @param orderVO
     * @return
     */
    @PostMapping("/save/order/remote")
    ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO) {
        try {
            orderService.saveOrder(orderVO);

            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed("服务器繁忙！请稍后重试！");
        }
    }
}
