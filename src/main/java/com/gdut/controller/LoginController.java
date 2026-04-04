package com.gdut.controller;

import com.gdut.annotation.RequireRole;
import com.gdut.pojo.LoginInfo;
import com.gdut.pojo.PasswordQueryParam;
import com.gdut.pojo.Result;
import com.gdut.pojo.User;
import com.gdut.service.UserService;
import com.gdut.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        log.debug("用户{}登录", user.getId());

        //加密用户密码
        user.setCodePassword(user.getPassword());
        // 调用Service层进行登录
        LoginInfo info = userService.login(user);

        //判断是否成功查询到该用户
        if (info != null) return Result.success(info);  // 登录成功
        return Result.fail("用户名或密码错误");     // 登录失败

    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        log.debug("用户{}注册", user.getId());

        //调用Service层进行注册
        boolean success = userService.register(user);

        return success? Result.success("注册成功！请返回登录界面登录。") : Result.fail("该用户已注册！");
    }


    /**
     * 修改密码
     */
    @RequireRole
    @PutMapping("/changePassword")
    public Result changePassword(HttpServletRequest req, @RequestBody PasswordQueryParam p) {
        log.info("修改密码");
        if(userService.changePassword(req,p)){
            return Result.success("修改成功");
        }
        return Result.fail("修改失败");
    }

    /**
     * 获取当前用户信息
     */
    @RequireRole
    @GetMapping("/getCurrentUserInfo")
    public Result getCurrentUserInfo(HttpServletRequest req) {
        log.info("获取当前用户信息");
        User user = userService.getCurrentUserInfo(req);
        return Result.success(user);
    }

}
