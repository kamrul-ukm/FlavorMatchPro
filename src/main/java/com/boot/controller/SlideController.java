package com.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.boot.bean.R;

import com.boot.service.SlideService;
import com.boot.entity.Slide;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 轮播图 前端控制器
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/slide")
public class SlideController {
    @Resource
    private SlideService slideService;

    //新增或者更新
    @PostMapping
    public R save(@RequestBody Slide slide) {
        slideService.saveOrUpdate(slide);
        return R.ok();
    }

    //删除
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        slideService.removeById(id);
        return R.ok();
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {//批量删除
        slideService.removeByIds(ids);
        return R.ok();
    }

    //查询所有数据
    @GetMapping
    public R findAll() {
        return R.ok(slideService.list());
    }

    @GetMapping("/{id}")
    public R findOne(@PathVariable Integer id) {
        return R.ok(slideService.getById(id));
    }

    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, String name) {
        QueryWrapper<Slide> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        return R.ok(slideService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}

