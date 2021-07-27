package com.mao.crowd.service.Impl;

import com.mao.crowd.entity.po.AddressPO;
import com.mao.crowd.entity.po.AddressPOExample;
import com.mao.crowd.entity.po.OrderPO;
import com.mao.crowd.entity.po.OrderProjectPO;
import com.mao.crowd.entity.vo.AddressVO;
import com.mao.crowd.entity.vo.OrderProjectVO;
import com.mao.crowd.entity.vo.OrderVO;
import com.mao.crowd.mapper.AddressPOMapper;
import com.mao.crowd.mapper.OrderPOMapper;
import com.mao.crowd.mapper.OrderProjectPOMapper;
import com.mao.crowd.service.api.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/25 11:46
 * Description:
 */
@Transactional(readOnly = true)
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;

    @Autowired
    private OrderPOMapper orderPOMapper;

    @Autowired
    private AddressPOMapper addressPOMapper;

    /**
     * 根据 projectId returnId
     * 查询出项目信息，回报信息
     * 封装成 OrderProjectVO 的方法业务逻辑
     * @param returnId
     * @return
     */
    public OrderProjectVO getOrderProjectVO(Integer returnId) {

        // 1、根据 projectId，returnId 查询出 orderProject
        return orderProjectPOMapper.selectOrderProjectVOByReturnId(returnId);
    }

    /**
     * 获取用户地址信息
     * @param memberId
     * @return
     */
    public List<AddressVO> getAddressVOList(Integer memberId) {

        AddressPOExample example = new AddressPOExample();

        AddressPOExample.Criteria criteria = example.createCriteria();

        criteria.andMemberIdEqualTo(memberId);

        List<AddressPO> addressPOList = addressPOMapper.selectByExample(example);

        List<AddressVO> addressVOList = new ArrayList<>();

        AddressVO addressVO;

        for (AddressPO addressPO : addressPOList) {
            addressVO = new AddressVO();
            BeanUtils.copyProperties(addressPO, addressVO);
            addressVOList.add(addressVO);
        }
        return addressVOList;
    }

    /**
     * 保存用户与输入的地址信息
     * @param addressVO
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveAddress(AddressVO addressVO) {

        // 新建 addressPO
        AddressPO addressPO = new AddressPO();

        // 复制属性
        BeanUtils.copyProperties(addressVO, addressPO);

        // 执行保存
        addressPOMapper.insert(addressPO);
    }

    /**
     * 保存订单
     * @param orderVO
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveOrder(OrderVO orderVO) {

        OrderPO orderPO = new OrderPO();

        BeanUtils.copyProperties(orderVO, orderPO);

        OrderProjectPO orderProjectPO = new OrderProjectPO();

        BeanUtils.copyProperties(orderVO.getOrderProjectVO(), orderProjectPO);

        // 保存 orderPO 自动生成的主键是 orderProjectPO 所需要的外键
        orderPOMapper.insert(orderPO);

        // 从 orderPO 中获取 orderId
        Integer orderId = orderPO.getId();

        // 将 orderId 设置到 orderProjectPO 的外键 orderId 上
        orderProjectPO.setOrderId(orderId);

        // 保存 orderProjectPO
        orderProjectPOMapper.insert(orderProjectPO);


    }
}
