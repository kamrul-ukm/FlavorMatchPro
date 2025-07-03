package com.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.bean.R;
import com.boot.entity.User;
import com.boot.service.UserService;
import com.boot.util.AESUtil;
import com.boot.util.JwtUtils;
import com.boot.vo.LoginVO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * (User)表控制层
 */
@RestController
@Api(tags = "")
@CrossOrigin(origins = "*")
@RequestMapping("login")
public class LoginController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    @PostMapping("login")
    public R<String> login(@RequestBody LoginVO loginVO) throws Exception {
        loginVO.setPassword(AESUtil.encrypt(loginVO.getPassword()));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", loginVO.getAccount());
        queryWrapper.eq("password", loginVO.getPassword());
        queryWrapper.eq("role", loginVO.getRole());

        User user = userService.getOne(queryWrapper);
        if (user != null) {
            Map map = new HashMap();
            map.put("userId", user.getId().toString());
            map.put("account", user.getAccount());
            map.put("role", user.getRole());
            String token = JwtUtils.createToken(map);
            Map map2 = new HashMap();
            map2.put("token", token);
            map2.put("info", user);
            return R.ok(map2);
        }

        return R.error("账号或者密码错误！");
    }

    @PostMapping("register")
    public R<String> register(@RequestBody User user) throws Exception {
        user.setPassword(AESUtil.encrypt(user.getPassword()));
        user.setRole("user");
        userService.saveOrUpdate(user);
        return R.ok();

    }

    @GetMapping("info")
    public R info(HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("userId");
        User byId = userService.getById(id);

        return R.ok(byId);
    }

}