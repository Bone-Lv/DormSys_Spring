package com.gdut.pojo;

import com.gdut.util.PasswordUtil;

public class User {

    //属性
    private String id;
    private String password;
    private String role;

    //学生特有属性
    private String dormNum = null;



    //构造方法
    public User() {
        super();
    }

    public User(String id, String password, String role , String dormNum) {
        super();
        this.id = id;
        this.password = password;
        this.role = role;
        this.dormNum = dormNum;
    }

    //getter和setter方法

    public String getDormNum() {
        return dormNum;
    }

    public void setDormNum(String dormNum) {
        this.dormNum = dormNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {

        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //获取解密后的密码
    public String getDecodePassword(){
        return PasswordUtil.decode(password);
    }

    public void setCodePassword(String password) {

        this.password = PasswordUtil.encode(password);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", dormNum='" + dormNum + '\'' +
                '}';
    }
}
