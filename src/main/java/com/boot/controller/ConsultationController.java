package com.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.entity.User;
import com.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.boot.bean.R;

import com.boot.service.ConsultationService;
import com.boot.entity.Consultation;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 咨询表 前端控制器
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/consultation")
public class ConsultationController {
    @Resource
    private ConsultationService consultationService;
    @Autowired
    private UserService userService;

    //新增或者更新
    @PostMapping
    public R save(@RequestBody Consultation consultation) {
        consultationService.saveOrUpdate(consultation);
        return R.ok();
    }

    //删除
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        consultationService.removeById(id);
        return R.ok();
    }

    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {//批量删除
        consultationService.removeByIds(ids);
        return R.ok();
    }

    //查询所有数据
    @GetMapping
    public R findAll(@RequestAttribute Integer userId) {
        QueryWrapper<Consultation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Consultation> consultationList = consultationService.list(queryWrapper);
        for (Consultation record : consultationList) {
            User user = userService.getById(record.getUserId());
            record.setUser(user);
        }
        return R.ok(consultationList);
    }

    @GetMapping("/{userId}")
    public R findOne(@PathVariable Integer userId) {
        QueryWrapper<Consultation> q = new QueryWrapper<>();
        q.eq("user_id", userId);
        List<Consultation> list = consultationService.list(q);
        return R.ok(list);
    }

    @GetMapping("/page")
    public R findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Consultation> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id").groupBy("user_id");
        IPage<Consultation> page = consultationService.page(new Page<>(pageNum, pageSize), queryWrapper);
        for (Consultation record : page.getRecords()) {
            User user = userService.getById(record.getUserId());
            record.setUser(user);
            QueryWrapper<Consultation> q = new QueryWrapper<>();
            q.eq("user_id", user.getId());
            List<Consultation> list = consultationService.list(q);
            record.setConsultations(list);
            if ((list.get(list.size() - 1).getQuestion() != null && !list.get(list.size() - 1).getQuestion().equals(""))
                    || list.get(list.size() - 1).getRaw() != null) {
                record.setStatus("NEEDS REPLY");
                record.setQuestion(list.get(list.size() - 1).getQuestion());
            } else {
                record.setStatus("ANSWERED");
            }
        }
        return R.ok(page);
    }
}

