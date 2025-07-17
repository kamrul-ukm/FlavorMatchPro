package com.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.util.AESUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.boot.bean.R;

import com.boot.service.UserService;
import com.boot.entity.User;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    //新增或者更新
    @PostMapping
    public R save(@RequestBody User user) throws Exception {
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(AESUtil.encrypt(user.getPassword()));
        }
        userService.saveOrUpdate(user);
        return R.ok();
    }


    //删除
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        userService.removeById(id);
        return R.ok();
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {//批量删除
        userService.removeByIds(ids);
        return R.ok();
    }

    //查询所有数据
    @GetMapping
    public R findAll() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.ne("role", "admin");
        List<User> list = userService.list(queryWrapper);
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R findOne(@PathVariable Integer id) {
        return R.ok(userService.getById(id));
    }

    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      String nickname, String role) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        if (StringUtils.isNoneBlank(nickname)) {
            queryWrapper.like("nickname", nickname);
        }
        if (StringUtils.isNoneBlank(role)) {
            queryWrapper.eq("role", role);
        }

        IPage<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);

        return R.ok(page);
    }

    //查询角色数据
    @GetMapping("getByRole")
    public R findAll(String role) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role", role);
        List<User> list = userService.list(queryWrapper);


        return R.ok(list);
    }
}

