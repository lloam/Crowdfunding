package com.mao.crowd.service.api;

import com.mao.crowd.entity.vo.AddressVO;
import com.mao.crowd.entity.vo.OrderProjectVO;
import com.mao.crowd.entity.vo.OrderVO;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/25 11:46
 * Description:
 */
public interface OrderService {
    OrderProjectVO getOrderProjectVO(Integer returnId);

    List<AddressVO> getAddressVOList(Integer memberId);

    void saveAddress(AddressVO addressVO);

    void saveOrder(OrderVO orderVO);
}
