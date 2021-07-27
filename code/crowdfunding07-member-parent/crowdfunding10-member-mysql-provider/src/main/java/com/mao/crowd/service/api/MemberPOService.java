package com.mao.crowd.service.api;

import com.mao.crowd.entity.po.MemberPO;

/**
 * Author: Administrator
 * Date: 2021/7/20 21:34
 * Description:
 */
public interface MemberPOService {

    MemberPO getMemberPOByLoginAcct(String loginAcct);

    void saveMemberPO(MemberPO memberPO);
}
