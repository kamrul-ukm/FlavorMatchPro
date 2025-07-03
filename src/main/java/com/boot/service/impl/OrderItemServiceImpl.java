package com.boot.service.impl;

import com.boot.entity.OrderItem;
import com.boot.mapper.OrderItemMapper;
import com.boot.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单项表 服务实现类
 * </p>
 *
 * @author
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
