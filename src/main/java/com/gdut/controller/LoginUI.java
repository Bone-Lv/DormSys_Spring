package com.gdut.controller;

import com.gdut.pojo.User;
import com.gdut.service.UserService;


import java.util.Scanner;

public class LoginUI {

    private final UserService userService;
    Scanner scanner = new Scanner(System.in);

    public LoginUI(UserService userService) {
        this.userService = userService;
    }

    public boolean showLoginMenu() {
        Scanner scanner = new Scanner(System.in);


        System.out.println("========================================");
        System.out.println("🏠 宿舍报修管理系统");
        System.out.println("========================================");
        System.out.println("1. 登录");
        System.out.println("2. 注册");
        System.out.println("3. 退出");
        System.out.print("请选择操作 (输入 1-3): ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                // 登录逻辑
                return showLoginMenu(scanner);


            case 2:
                // 注册逻辑
                return showRegisterMenu(scanner);

            case 3:
                // 退出逻辑
                System.out.println("退出系统");
                System.exit(0);
                return false;

            default:
                System.out.println("输入无效，请重新选择");
                return true;

        }
    }

    //登陆页面
    private boolean showLoginMenu(Scanner scanner) {

        System.out.println("\n===== 用户登录 =====");
        System.out.print("请输入账号：");
        String id = scanner.nextLine();


        System.out.print("请输入密码：");
        String password = scanner.nextLine();


        User user = new User();
        user.setId(id);
        user.setCodePassword(password);


        // 调用 Service 层进行登录
        boolean success = userService.login(user);

        if (success) {
            System.out.println("登录成功！欢迎您，" + user.getId());
            return false; // 登录成功，退出登录菜单循环
        } else {
            System.out.println("登录失败！账号或密码错误。");
            return true; // 登录失败，继续循环
        }
    }

    private boolean showRegisterMenu(Scanner scanner) {
        System.out.println("\n===== 用户注册 =====");

        // 角色选择
        System.out.println("请选择角色 (1-学生，2-维修人员):");
        System.out.print("> ");
        int roleChoice = scanner.nextInt();
        scanner.nextLine(); // 消耗换行符

        // 验证角色选择
        if (roleChoice != 1 && roleChoice != 2) {
            System.out.println("无效的角色选择！");
            return false;
        }

        // 输入账号（学号或工号）
        if (roleChoice == 1) {
            System.out.print("请输入学号 (前缀 3125 或 3225): ");
        } else {
            System.out.print("请输入工号 (前缀 0025): ");
        }
        String id = scanner.nextLine();

        // 输入密码
        System.out.print("请输入密码：");
        String password = scanner.nextLine();

        System.out.print("请确认密码：");
        String confirmPassword = scanner.nextLine();

        // 验证两次密码是否一致
        if (!password.equals(confirmPassword)) {
            System.out.println("两次输入的密码不一致，注册失败！");
            return false;
        }



        User user = new User();
        user.setId(id);
        user.setCodePassword(password);
        user.setRole(roleChoice == 1 ? "学生" : "维修人员");

        // 调用 Service 层进行注册
        boolean success = userService.register(user);

        if (success) {
            System.out.println("注册成功！请返回登录界面登录。");
        } else {
            System.out.println("注册失败！该用户已存在或输入信息有误。");
        }
        return success;
    }
}

