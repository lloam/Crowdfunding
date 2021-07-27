package com.mao.crowd.service.Impl;

import com.mao.crowd.entity.po.MemberPO;
import com.mao.crowd.entity.po.MemberPOExample;
import com.mao.crowd.mapper.MemberPOMapper;
import com.mao.crowd.service.api.MemberPOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/20 21:35
 * Description:
 */
// 声明式事务
@Transactional(readOnly = true)
@Service
public class MemberPOServiceImpl implements MemberPOService {

    @Autowired
    private MemberPOMapper memberPOMapper;

    /**
     * 根据账号查询用户
     * @param loginAcct
     * @return
     */
    public MemberPO getMemberPOByLoginAcct(String loginAcct) {

        // 1、创建 MemberPOExample 对象
        MemberPOExample memberPOExample = new MemberPOExample();

        // 2、创建 Criteria 对象
        MemberPOExample.Criteria criteria = memberPOExample.createCriteria();

        // 3、封装查询条件
        criteria.andLoginacctEqualTo(loginAcct);

        // 4、执行查询
        List<MemberPO> list = memberPOMapper.selectByExample(memberPOExample);

        // 5、获取结果

        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 保存用户
     * @param memberPO
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveMemberPO(MemberPO memberPO) {
        memberPOMapper.insertSelective(memberPO);
    }
}
