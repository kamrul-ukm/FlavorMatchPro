package com.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.boot.bean.R;

import com.boot.service.ConfigService;
import com.boot.entity.Config;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 配置表
 前端控制器
 * </p>
 *
 * @author 
 * @since 2025-07-01
 */
@RestController
@RequestMapping("/config")
    public class ConfigController {
@Resource
private ConfigService configService;

//新增或者更新
@PostMapping
public R save(@RequestBody Config config) {
    configService.saveOrUpdate(config);
        return R.ok();
        }


//查询所有数据
@GetMapping
public R findAll() {
        List<Config> list =  configService.list();
        return R.ok(list);
        }

@GetMapping("/{id}")
public R findOne(@PathVariable Integer id) {
        return R.ok(configService.getById(id));
        }

@GetMapping("/page")
public R findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Config> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        IPage<Config> page = configService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return R.ok(page);
        }
        }

