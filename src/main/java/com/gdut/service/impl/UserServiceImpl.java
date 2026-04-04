package com.gdut.service.impl;

import com.gdut.mapper.UserMapper;
import com.gdut.pojo.LoginInfo;
import com.gdut.pojo.PasswordQueryParam;
import com.gdut.pojo.User;
import com.gdut.service.UserService;
import com.gdut.util.JwtUtil;
import com.gdut.util.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     * @param user 用户
     * @return 登录信息
     */
    @Override
    public LoginInfo login(User user) {

        //返回是否有该用户
        User u =userMapper.selectByIdAndPassword(user);

        //判断是否登陆成功
        if (u != null) {
            //登陆成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", u.getId());
            claims.put("role", u.getRole());
            //添加原始密码
            String token = JwtUtil.generateToken(claims);
            return new LoginInfo(u.getId(), u.getRole(), token);

        }
        return null;
    }

    /**
     * 注册
     * @param user 用户
     * @return 注册成功与否
     */
    @Override
    public boolean register(User user) {

        User u =userMapper.selectById(user.getId());

        if(u ==null){
            //数据库没有该用户允许注册
                //填充基本数据

            //判断当前角色
            if(user.getId().startsWith("3225")||user.getId().startsWith("3125")){
                user.setRole("student");
            }else if(user.getId().startsWith("0025")){
                user.setRole("admin");
            }else{
                return false;
            }
            user.setCodePassword(user.getPassword());
            return userMapper.addUser(user) == 1;
        }
        //数据库中已有该角色
        return false;

    }

    /**
     * 修改密码
     * @param request 请求
     * @param passwordQueryParam 密码参数
     * @return 修改成功与否
     */
    @Override
    public boolean changePassword(HttpServletRequest request, PasswordQueryParam passwordQueryParam) {
        //获得当前用户id
        String id = JwtUtil.getCurrentUserId(request);
        User user = userMapper.selectById(id);

        //比对用户输入密码是否与原密码相同
        String OriginalPassword = user.getDecodePassword();
        String oldPassword = passwordQueryParam.getOldPassword();
        String newPassword = passwordQueryParam.getNewPassword();

        if(OriginalPassword.equals(oldPassword)){
            //密码相同，修改密码
            user.setCodePassword(newPassword);
            int success = userMapper.updatePassword(user);
            return success > 0;
        }
        //密码不同
        return false;
    }

    @Override
    public User getCurrentUserInfo(HttpServletRequest req) {
        //获取当前用户id
        String id = JwtUtil.getCurrentUserId(req);
        //获取当前用户 信息
        return userMapper.selectById(id);
    }

}
