
package com.gdut.controller;


import com.gdut.enums.RepairOrderStatus;
import com.gdut.pojo.RepairOrder;
import com.gdut.pojo.User;
import com.gdut.service.StudentService;
import com.gdut.service.impl.StudentServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentUI {

    private final StudentService studentService;
    private final User currentUser;
    private Scanner scanner;

    public StudentUI(User currentUser) {
        this.currentUser = currentUser;
        this.studentService = new StudentServiceImpl(currentUser);
        this.scanner = new Scanner(System.in);
    }

    public void showStudentMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n========================================");
            System.out.println("👨🎓 学生操作界面");
            System.out.println("========================================");
            System.out.println("1. 绑定/修改宿舍");
            System.out.println("2. 创建报修单");
            System.out.println("3. 查看我的报修记录");
            System.out.println("4. 取消报修单");
            System.out.println("5. 修改密码");
            System.out.println("6. 退出登录");
            System.out.print("请选择操作（输入 1-6）：");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    // 绑定/修改宿舍
                    bindOrModifyDormitory();
                    break;
                case 2:
                    // 创建报修单
                    createRepairOrder();
                    break;
                case 3:
                    // 查看我的报修记录
                    viewMyRepairRecords();
                    break;
                case 4:
                    // 取消报修单
                    cancelRepairOrder();
                    break;
                case 5:
                    // 修改密码
                    changePassword();
                    break;
                case 6:
                    // 退出登录
                    System.out.println("退出登录，返回主界面...");
                    running = false;
                    break;
                default:
                    System.out.println("输入无效，请重新选择");
            }
        }
    }

    /**
     * 绑定/修改宿舍
     */
    private void bindOrModifyDormitory() {
        System.out.println("\n===== 绑定/修改宿舍 =====");
        System.out.print("请输入宿舍楼栋号（如：西一栋）：");
        String buildingNum = scanner.nextLine();

        System.out.print("请输入房间号（如：301）：");
        String roomNum = scanner.nextLine();

        String dormitoryInfo = buildingNum + "栋" + roomNum + "室";

        // 调用 Service 层处理
        boolean success = studentService.bindOrModifyDormitory(currentUser.getId(), dormitoryInfo);

        if (success) {
            System.out.println("宿舍绑定/修改成功！");
        } else {
            System.out.println("宿舍绑定/修改失败！");
        }
    }

    /**
     * 创建报修单
     */
    private void createRepairOrder() {
        System.out.println("\n===== 创建报修单 =====");

        System.out.print("请输入设备类型（如：空调、灯具、水管）：");
        String deviceType = scanner.nextLine();

        System.out.print("请输入问题描述：");
        String description = scanner.nextLine();

        System.out.print("请输入报修状态（默认：" + RepairOrderStatus.PENDING.getDescription() + "）：");
        String status = scanner.nextLine();
        
        if (status.isEmpty()) {
            status = RepairOrderStatus.PENDING.getDescription();
        } else {
            RepairOrderStatus inputStatus = RepairOrderStatus.fromDescription(status);
            if (inputStatus == null) {
                System.out.println("❌ 无效的状态值！状态必须是以下之一：");
                for (RepairOrderStatus s : RepairOrderStatus.values()) {
                    System.out.println("   - " + s.getDescription());
                }
                System.out.println("已自动设置为默认状态：" + RepairOrderStatus.PENDING.getDescription());
                status = RepairOrderStatus.PENDING.getDescription();
            } else if (inputStatus != RepairOrderStatus.PENDING) {
                System.out.println("⚠️  提示：学生创建报修单时状态只能为\"" + 
                    RepairOrderStatus.PENDING.getDescription() + "\"，已为您自动设置");
                status = RepairOrderStatus.PENDING.getDescription();
            }
        }

        //创建保修单
        LocalDateTime createTime = LocalDateTime.now();
        String studentId = currentUser.getId();
        String dormNum = currentUser.getDormNum();
        String adminId = null;

        RepairOrder repairOrder = new RepairOrder(description, deviceType, status, createTime, createTime, studentId, dormNum, adminId);


        // 调用 Service 层处理
        boolean success = studentService.createRepairOrder(
                repairOrder
        );

        if (success) {
            System.out.println("报修单创建成功！");
        } else {
            System.out.println("报修单创建失败！");
        }
    }

    /**
     * 查看我的报修记录
     */
    private void viewMyRepairRecords() {
        System.out.println("\n===== 查看我的报修记录 =====");

        ArrayList<RepairOrder> repairOrderList = studentService.viewMyRepairRecords(currentUser.getId());

        if (repairOrderList.isEmpty()) {
            System.out.println("💨 暂无报修记录！");
        } else {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("📋 我的报修记录");
            System.out.println("=".repeat(60));

            for (RepairOrder order : repairOrderList) {
                System.out.println("\n" + "-".repeat(60));
                System.out.println(String.format("%-20s %s", "📝 订单编号:", order.getId()));
                System.out.println(String.format("%-20s %s", "🏠 宿舍号:", order.getDormNum() != null ? order.getDormNum() : "未分配"));
                System.out.println(String.format("%-20s %s", "🔧 设备类型:", order.getDeviceType() != null ? order.getDeviceType() : "未知"));
                System.out.println(String.format("%-20s %s", "📄 问题描述:", order.getDescription() != null ? order.getDescription() : "无"));
                System.out.println(String.format("%-20s %s", "📊 当前状态:", order.getStatus() != null ? order.getStatus() : "未知"));

                String createTimeStr = order.getCreateTime() != null ?
                        order.getCreateTime().toString().replace("T", "  ") : "未知";
                String updateTimeStr = order.getUpdateTime() != null ?
                        order.getUpdateTime().toString().replace("T", "  ") : "未知";

                System.out.println(String.format("%-20s %s", "⏰ 创建时间:", createTimeStr));
                System.out.println(String.format("%-20s %s", "🔄 更新时间:", updateTimeStr));
                System.out.println(String.format("%-20s %s", "👤 学生 ID:", order.getStudentId() != null ? order.getStudentId() : "未知"));
                System.out.println(String.format("%-20s %s", "👨‍ 处理管理员:", order.getAdminId() != null ? order.getAdminId() : "未分配"));

                String status = order.getStatus();
                if (RepairOrderStatus.PENDING.getDescription().equals(status)) {
                    System.out.println("💡 提示：该报修单等待处理，维修人员会尽快处理");
                } else if (RepairOrderStatus.IN_PROGRESS.getDescription().equals(status)) {
                    System.out.println("💡 提示：维修人员正在处理中，请耐心等待");
                } else if (RepairOrderStatus.COMPLETED.getDescription().equals(status)) {
                    System.out.println("💡 提示：该报修单已完成，请确认维修结果");
                } else if (RepairOrderStatus.CANCELLED.getDescription().equals(status)) {
                    System.out.println("💡 提示：该报修单已取消");
                }
            }

            System.out.println("\n" + "-".repeat(60));
            System.out.println("共 " + repairOrderList.size() + " 条报修记录");
            System.out.println("=".repeat(60));
            System.out.println();
        }
    }

    /**
     * 取消报修单
     */
    private void cancelRepairOrder() {
        System.out.println("\n===== 取消报修单 =====");

        System.out.print("请输入要取消的报修单 ID：");
        int repairId = scanner.nextInt();
        scanner.nextLine(); // 消耗换行符

        // 调用 Service 层处理
        boolean success = studentService.cancelRepairOrder(repairId, currentUser.getId());

        if (success) {
            System.out.println("报修单取消成功！");
        } else {
            System.out.println("报修单取消失败！");
        }
    }

    /**
     * 修改密码
     */
    private void changePassword() {
        System.out.println("\n===== 修改密码 =====");

        System.out.print("请输入原密码：");
        String oldPassword = scanner.nextLine();

        System.out.print("请输入新密码：");
        String newPassword = scanner.nextLine();

        System.out.print("请确认新密码：");
        String confirmPassword = scanner.nextLine();

        // 验证两次新密码是否一致
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("两次输入的新密码不一致！");
            return;
        }

        // 调用 Service 层处理
        boolean success = studentService.changePassword(currentUser.getId(), oldPassword, newPassword);

        if (success) {
            System.out.println("密码修改成功！请重新登录。");

        } else {
            System.out.println("密码修改失败！原密码错误或网络异常。");
        }
    }
}

