package com.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.boot.bean.R;

import com.boot.service.OrderItemService;
import com.boot.entity.OrderItem;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单项表 前端控制器
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/orderItem")
public class OrderItemController {
    @Resource
    private OrderItemService orderItemService;

    //新增或者更新
    @PostMapping
    public R save(@RequestBody OrderItem orderItem) {
        orderItemService.saveOrUpdate(orderItem);
        return R.ok();
    }

    //删除
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        orderItemService.removeById(id);
        return R.ok();
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {//批量删除
        orderItemService.removeByIds(ids);
        return R.ok();
    }

    //查询所有数据
    @GetMapping
    public R findAll() {
        List<OrderItem> list = orderItemService.list();
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R findOne(@PathVariable Integer id) {
        return R.ok(orderItemService.getById(id));
    }

    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        IPage<OrderItem> page = orderItemService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return R.ok(page);
    }
}

