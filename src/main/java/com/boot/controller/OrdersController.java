package com.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.entity.OrderItem;
import com.boot.entity.param.CartOrders;
import com.boot.service.FoodService;
import com.boot.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.boot.bean.R;

import com.boot.service.OrdersService;
import com.boot.entity.Orders;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Resource
    private OrdersService ordersService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private FoodService foodService;
    //新增或者更新
    @PostMapping
    public R save(@RequestBody Orders orders) {
        ordersService.saveOrUpdate(orders);
        return R.ok();
    }

    @PostMapping("/buyCart")
    public R buyCart(@RequestBody CartOrders cartOrders, @RequestAttribute Integer userId) {
        ordersService.buyCart(cartOrders, userId);
        return R.ok();
    }

    //删除
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        ordersService.removeById(id);
        return R.ok();
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {//批量删除
        ordersService.removeByIds(ids);
        return R.ok();
    }

    //查询所有数据
    @GetMapping
    public R findAll(@RequestAttribute Integer userId) {
        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        ordersQueryWrapper.eq("user_id", userId);
        List<Orders> list = ordersService.list(ordersQueryWrapper);
        for (Orders record : list) {
            QueryWrapper<OrderItem> orderItemQueryWrapper = new QueryWrapper<>();
            orderItemQueryWrapper.eq("order_id", record.getId());
            List<OrderItem> list2 = orderItemService.list(orderItemQueryWrapper);
            for (OrderItem orderItem : list2) {
                orderItem.setFood(foodService.getById(orderItem.getFoodId()));
            }
            record.setOrderItemList(list2);
        }
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R findOne(@PathVariable Integer id) {
        return R.ok(ordersService.getById(id));
    }

    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        IPage<Orders> page = ordersService.page(new Page<>(pageNum, pageSize), queryWrapper);
        for (Orders record : page.getRecords()) {
            QueryWrapper<OrderItem> orderItemQueryWrapper = new QueryWrapper<>();
            orderItemQueryWrapper.eq("order_id", record.getId());
            List<OrderItem> list = orderItemService.list(orderItemQueryWrapper);
            for (OrderItem orderItem : list) {
                orderItem.setFood(foodService.getById(orderItem.getFoodId()));
            }
            record.setOrderItemList(list);
        }
        return R.ok(page);
    }
}

