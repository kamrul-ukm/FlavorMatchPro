package com.boot.service.impl;

import com.boot.entity.Cart;
import com.boot.entity.Food;
import com.boot.entity.OrderItem;
import com.boot.entity.Orders;
import com.boot.entity.param.CartOrders;
import com.boot.mapper.OrdersMapper;
import com.boot.service.CartService;
import com.boot.service.FoodService;
import com.boot.service.OrderItemService;
import com.boot.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private CartService cartService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private FoodService foodService;

    @Override
    public void buyCart(CartOrders cartOrders, Integer userId) {
        Collection<Cart> carts = cartService.listByIds(cartOrders.getCartIds());
        BigDecimal totalMount = new BigDecimal(0);
        List<OrderItem> orderItems = new ArrayList<>();
        for (Cart cart : carts) {
            Food food = foodService.getById(cart.getFoodId());
            totalMount = totalMount.add(food.getPrice().multiply(new BigDecimal(cart.getQuantity())));
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(food.getPrice());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setFoodId(food.getId());
            orderItems.add(orderItem);
        }
        Orders orders = new Orders();
        orders.setStatus("PAID");
        orders.setSendTime(cartOrders.getSendTime());
        orders.setRemark(cartOrders.getRemark());
        orders.setTotalPrice(totalMount);
        orders.setUserId(userId);
        orders.setPhone(cartOrders.getPhone());
        orders.setAddress(cartOrders.getAddress());
        orders.setNickname(cartOrders.getNickname());
        ordersService.save(orders);
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(orders.getId());
        }
        orderItemService.saveBatch(orderItems);
        cartService.removeByIds(cartOrders.getCartIds());
    }
}
