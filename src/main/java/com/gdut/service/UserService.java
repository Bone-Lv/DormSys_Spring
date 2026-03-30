package com.gdut.service;

import com.gdut.pojo.LoginInfo;
import com.gdut.pojo.PasswordQueryParam;
import com.gdut.pojo.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    /**
     * 登录
     * @param user 用户信息
     * @return 登录信息
     */
     LoginInfo login(User user);

    /**
     *  注册
     * @param user 用户信息
     * @return 操作结果
     */
     boolean register(User user);

    /**
     * 修改密码
     * @param request 请求
     * @param passwordQueryParam 新旧密码
     * @return 操作结果
     */
    boolean changePassword(HttpServletRequest request, PasswordQueryParam passwordQueryParam);

    User getCurrentUserInfo(HttpServletRequest req);
}
