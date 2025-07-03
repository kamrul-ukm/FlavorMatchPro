package com.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.boot.bean.R;

import com.boot.service.FoodService;
import com.boot.entity.Food;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 美食表 前端控制器
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/food")
public class FoodController {
    @Resource
    private FoodService foodService;
    @Autowired
    private CategoryService categoryService;

    //新增或者更新
    @PostMapping
    public R save(@RequestBody Food food) {
        foodService.saveOrUpdate(food);
        return R.ok();
    }

    //删除
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        foodService.removeById(id);
        return R.ok();
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {//批量删除
        foodService.removeByIds(ids);
        return R.ok();
    }

    //查询所有数据
    @GetMapping
    public R findAll() {
        List<Food> list = foodService.list();
        for (Food record : list) {
            record.setCategory(categoryService.getById(record.getCategoryId()));
        }
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R findOne(@PathVariable Integer id) {
        Food byId = foodService.getById(id);
        byId.setCategory(categoryService.getById(byId.getCategoryId()));
        return R.ok(byId);
    }

    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        IPage<Food> page = foodService.page(new Page<>(pageNum, pageSize), queryWrapper);
        for (Food record : page.getRecords()) {
            record.setCategory(categoryService.getById(record.getCategoryId()));
        }
        return R.ok(page);
    }
}

