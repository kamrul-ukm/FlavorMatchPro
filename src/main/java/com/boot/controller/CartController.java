package com.boot.controller;

import com.alipay.api.domain.Car;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.service.FoodService;
import com.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import com.boot.bean.R;

import com.boot.service.CartService;
import com.boot.entity.Cart;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 购物车表 前端控制器
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;

    //新增或者更新
    @PostMapping
    public R save(@RequestBody Cart cart, @RequestAttribute Integer userId) {
        if (cart.getId() == null) {
            cart.setUserId(userId);
            cart.setCreatedAt(new Date());
            QueryWrapper<Cart> queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_id", userId);
            queryWrapper.eq("food_id", cart.getFoodId());
            Cart one = cartService.getOne(queryWrapper);
            if (one != null) {
                cart.setId(one.getId());
                cart.setQuantity(one.getQuantity() + cart.getQuantity());
                cartService.updateById(cart);
            } else {
                cartService.save(cart);
            }
        } else {
            cartService.updateById(cart);
        }

        return R.ok();
    }

    //删除
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        cartService.removeById(id);
        return R.ok();
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {//批量删除
        cartService.removeByIds(ids);
        return R.ok();
    }

    //查询所有数据
    @GetMapping
    public R findAll(@RequestAttribute Integer userId) {
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("user_id", userId);
        List<Cart> list = cartService.list(cartQueryWrapper);
        for (Cart record : list) {
            record.setUser(userService.getById(record.getUserId()));
            record.setFood(foodService.getById(record.getFoodId()));
        }
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R findOne(@PathVariable Integer id) {
        return R.ok(cartService.getById(id));
    }

    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        IPage<Cart> page = cartService.page(new Page<>(pageNum, pageSize), queryWrapper);
        for (Cart record : page.getRecords()) {
            record.setUser(userService.getById(record.getUserId()));
            record.setFood(foodService.getById(record.getFoodId()));
        }
        return R.ok(page);
    }
}

