package com.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.boot.bean.R;

import com.boot.service.CategoryService;
import com.boot.entity.Category;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 通知公告类别 前端控制器
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    //新增或者更新
    @PostMapping
    public R save(@RequestBody Category category) {
        categoryService.saveOrUpdate(category);
        return R.ok();
    }

    //删除
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        categoryService.removeById(id);
        return R.ok();
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {//批量删除
        categoryService.removeByIds(ids);
        return R.ok();
    }

    //查询所有数据
    @GetMapping
    public R findAll() {
        return R.ok(categoryService.list());
    }

    @GetMapping("/{id}")
    public R findOne(@PathVariable Integer id) {
        return R.ok(categoryService.getById(id));
    }

    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, String name) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        return R.ok(categoryService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}

