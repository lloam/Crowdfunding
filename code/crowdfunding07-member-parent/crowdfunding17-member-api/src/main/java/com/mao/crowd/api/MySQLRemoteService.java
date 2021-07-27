package com.mao.crowd.api;

import com.mao.crowd.entity.po.MemberPO;
import com.mao.crowd.entity.vo.*;
import com.mao.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/20 21:24
 * Description: MySQL 对 feign 暴露的 controller 接口
 */
@FeignClient("mao-crowd-mysql")
public interface MySQLRemoteService {

    @GetMapping("/get/memberPO/by/loginAcct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginAcct") String loginAcct);

    @PostMapping("/save/memberPO/remote")
    public ResultEntity<String> saveMemberPO(@RequestBody MemberPO memberPO);

    @PostMapping("/save/projectPO/remote")
    ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId);

    /**
     * 获取首页项目的类型数据信息
     * @return
     */
    @GetMapping("/get/portal/type/project/data/remote")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote();

    /**
     * 根据项目 id 获取详细的项目信息
     * @param projectId
     * @return
     */
    @GetMapping("/get/project/detail/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId") Integer projectId);

    /**
     * 根据回报 id
     * 查询出对象的项目，回报
     * 封装成 OrderProjectVO
     * 提供 feign 远程调用接口
     * @param returnId
     * @return
     */
    @GetMapping("/get/order/projectVO/remote")
    ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("returnId") Integer returnId);

    /**
     * 根据 memberId 获取用户的收获地址信息
     * @param memberId
     * @return
     */
    @GetMapping("/get/addressVO/remote")
    ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId);


    @PostMapping("/save/address/remote")
    ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO);

    @PostMapping("/save/order/remote")
    ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO);
}
