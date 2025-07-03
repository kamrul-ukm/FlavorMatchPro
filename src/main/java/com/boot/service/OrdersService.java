package com.boot.service;

import com.boot.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.entity.param.CartOrders;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author
 */
public interface OrdersService extends IService<Orders> {

    void buyCart(CartOrders cartOrders, Integer userId);
}
