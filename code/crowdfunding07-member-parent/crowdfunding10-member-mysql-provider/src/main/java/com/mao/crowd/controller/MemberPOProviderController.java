package com.mao.crowd.controller;

import com.mao.crowd.constant.CrowdConstant;
import com.mao.crowd.entity.po.MemberPO;
import com.mao.crowd.service.api.MemberPOService;
import com.mao.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Administrator
 * Date: 2021/7/20 21:31
 * Description: MemberPO 数据库服务提供控制器
 */
@RestController
public class MemberPOProviderController {

    @Autowired
    private MemberPOService memberPOService;

    /**
     * 根据账号查询用户
     * @param loginAcct
     * @return
     */
    @GetMapping("/get/memberPO/by/loginAcct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginAcct") String loginAcct) {
        try {
            // 1、调用本地 Service 完成查询
            MemberPO memberPO = memberPOService.getMemberPOByLoginAcct(loginAcct);

            // 2、如果没有抛异常，那么就返回成功的结果
            return ResultEntity.successWithData(memberPO);
        } catch (Exception e) {
            e.printStackTrace();

            // 3、如果捕获到异常则返回失败的结果
            return ResultEntity.failed(e.getMessage());
        }
    }


    /**
     * post 请求保存用户
     * @param memberPO
     * @return
     */
    @PostMapping("/save/memberPO/remote")
    public ResultEntity<String> saveMemberPO(@RequestBody MemberPO memberPO) {
        try {

            memberPOService.saveMemberPO(memberPO);

            return ResultEntity.successWithoutData();

        } catch (Exception e) {

            if (e instanceof DuplicateKeyException) {
                return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }

            return ResultEntity.failed(e.getMessage());

        }
    }
}
