package com.gdut.service;

import com.gdut.pojo.User;

public interface UserService {
    //登陆
     boolean login(User user);

     //注册
     boolean register(User user);

     //获取当前登陆的用户
     User getCurrentUser();

     //设置当前登陆的用户
      void setCurrentUser(User user);
}
