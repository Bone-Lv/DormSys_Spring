//
//package com.gdut.controller;
//
//import com.gdut.enums.RepairOrderStatus;
//import com.gdut.pojo.RepairOrder;
//import com.gdut.pojo.User;
//import com.gdut.service.AdminService;
//import com.gdut.service.StudentService;
//import com.gdut.service.impl.AdminServiceImpl;
//import com.gdut.service.impl.StudentServiceImpl;
//
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class AdminUI {
//
//    private final User currentUser;
//    private  final  Scanner scanner ;
//    private final AdminService adminService ;
//
//    public AdminUI(User currentUser) {
//        this.currentUser = currentUser;
//        this.scanner = new Scanner(System.in);
//        this.adminService = new AdminServiceImpl(currentUser);
//    }
//
//    public void showAdminMenu() {
//        boolean running = true;
//        while( running) {
//            System.out.println("========================================");
//            System.out.println("👨‍💼 管理员操作界面");
//            System.out.println("========================================");
//            System.out.println("1. 查看所有报修单");
//            System.out.println("2. 查看报修单详情");
//            System.out.println("3. 更新报修单状态");
//            System.out.println("4. 删除报修单");
//            System.out.println("5. 修改密码");
//            System.out.println("6. 退出登录");
//            System.out.print("请选择操作（输入 1-6）：");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (choice) {
//                case 1:
//                    viewAllRepairOrders();
//                    break;
//                case 2:
//                    viewRepairOrderDetail();
//                    break;
//                case 3:
//                    updateRepairOrderStatus();
//                    break;
//                case 4:
//                    deleteRepairOrder();
//                    break;
//                case 5:
//                    changePassword();
//                    break;
//                case 6:
//                    System.out.println("退出登录，返回主界面...");
//                    running = false;
//                    break;
//                default:
//                    System.out.println("输入无效，请重新选择");
//            }
//        }
//    }
//
//
//    private void viewAllRepairOrders() {
//        System.out.println("\n===== 查看所有报修单 =====");
//        ArrayList<RepairOrder> repairOrders = adminService.viewAllRepairOrders();
//
//        if (repairOrders.isEmpty()) {
//            System.out.println("暂无报修单！");
//        } else {
//            repairOrders.sort((o1, o2) -> {
//                int statusCompare = compareStatus(o1.getStatus(), o2.getStatus());
//                if (statusCompare != 0) {
//                    return statusCompare;
//                }
//                return o2.getCreateTime().compareTo(o1.getCreateTime());
//            });
//
//            System.out.printf("\n" + "%-10s %-15s %-25s %-15s", "订单号", "宿舍号", "创建时间", "状态");
//            System.out.println("\n"+"--------------------------------------------------------------------------------");
//
//            for (RepairOrder order : repairOrders) {
//                String createTimeStr =
//                        order.getCreateTime().toString().replace("T", " ") ;
//                System.out.printf("%-10s %-15s %-25s %-15s",
//                        order.getId(),
//                        order.getDormNum(),
//                        createTimeStr,
//                        order.getStatus());
//            }
//            System.out.println("\n"+"--------------------------------------------------------------------------------");
//            System.out.println("共 " + repairOrders.size() + " 条报修记录");
//        }
//    }
//
//        private int compareStatus(String status1, String status2) {
//            return RepairOrderStatus.compare(status1, status2);
//        }
//
//        private int getStatusPriority(String status) {
//            return RepairOrderStatus.getPriorityValue(status);
//        }
//
//
//    /**
//     * 查看报修单详情
//     */
//    private void viewRepairOrderDetail() {
//        System.out.println("\n===== 查看报修单详情 =====");
//
//        System.out.print("请输入要查看的报修单 ID：");
//        int repairId = scanner.nextInt();
//        scanner.nextLine();
//
//        RepairOrder repairOrder = adminService.viewRepairOrderDetail(repairId);
//        if (repairOrder != null) {
//            System.out.println("\n" + "=".repeat(60));
//            System.out.println("📋 报修单详细信息");
//            System.out.println("=".repeat(60));
//
//            System.out.println(String.format("%-20s %s", "📝 订单编号:", repairOrder.getId()));
//            System.out.println(String.format("%-20s %s", "🏠 宿舍号:", repairOrder.getDormNum() ));
//            System.out.println(String.format("%-20s %s", "🔧 设备类型:", repairOrder.getDeviceType()));
//            System.out.println(String.format("%-20s %s", "📄 问题描述:", repairOrder.getDescription()));
//            System.out.println(String.format("%-20s %s", "📊 当前状态:", repairOrder.getStatus() ));
//
//            String createTimeStr =
//                    repairOrder.getCreateTime().toString().replace("T", "  ") ;
//            String updateTimeStr =
//                    repairOrder.getUpdateTime().toString().replace("T", "  ") ;
//
//            System.out.println(String.format("%-20s %s", "⏰ 创建时间:", createTimeStr));
//            System.out.println(String.format("%-20s %s", "🔄 更新时间:", updateTimeStr));
//            System.out.println(String.format("%-20s %s", "👤 学生 ID:", repairOrder.getStudentId() ));
//            System.out.println(String.format("%-20s %s", "👨‍🔧 处理管理员:", repairOrder.getAdminId() != null ? repairOrder.getAdminId() : "未分配"));
//
//            System.out.println("=".repeat(60));
//
//            String status = repairOrder.getStatus();
//            if (RepairOrderStatus.PENDING.getDescription().equals(status)) {
//                System.out.println("💡 提示：该报修单等待处理，请及时安排维修人员");
//            } else if (RepairOrderStatus.IN_PROGRESS.getDescription().equals(status)) {
//                System.out.println("💡 提示：该报修单正在处理中，请跟进维修进度");
//            } else if (RepairOrderStatus.COMPLETED.getDescription().equals(status)) {
//                System.out.println("💡 提示：该报修单已完成，请确认维修结果");
//            } else if (RepairOrderStatus.CANCELLED.getDescription().equals(status)) {
//                System.out.println("💡 提示：该报修单已取消");
//            }
//            System.out.println();
//        } else {
//            System.out.println("❌ 未找到该报修单！");
//        }
//    }
//
//
//    /**
//     * 更新报修单状态
//     */
//    private void updateRepairOrderStatus() {
//        System.out.println("\n===== 更新报修单状态 =====");
//
//        System.out.print("请输入要更新的报修单 ID：");
//        int repairId = scanner.nextInt();
//        scanner.nextLine();
//
//        System.out.print("请输入新的状态（");
//        System.out.print("可选：");
//        for (int i = 0; i < RepairOrderStatus.values().length; i++) {
//            System.out.print(RepairOrderStatus.values()[i].getDescription());
//            if (i < RepairOrderStatus.values().length - 1) {
//                System.out.print("、");
//            }
//        }
//        System.out.print("）：");
//
//        String status = scanner.nextLine();
//
//        RepairOrderStatus inputStatus = RepairOrderStatus.fromDescription(status);
//        if (inputStatus == null) {
//            System.out.println("❌ 无效的状态值！状态必须是以下之一：");
//            for (RepairOrderStatus s : RepairOrderStatus.values()) {
//                System.out.println("   - " + s.getDescription());
//            }
//            return;
//        }
//
//        boolean result = adminService.updateRepairOrderStatus(repairId, status);
//
//        if (result) {
//            System.out.println("✅ 报修单状态更新成功！");
//        } else {
//            System.out.println("❌ 报修单状态更新失败！");
//        }
//
//    }
//
//    /**
//     * 删除报修单
//     */
//    private void deleteRepairOrder() {
//        System.out.println("\n===== 删除报修单 =====");
//
//        System.out.print("请输入要删除的报修单 ID：");
//        int repairId = scanner.nextInt();
//        scanner.nextLine();
//
//        System.out.print("确认删除该报修单吗？（输入 yes 确认）：");
//        String confirm = scanner.nextLine();
//
//        if (!"yes".equals(confirm)) {
//            System.out.println("已取消删除操作！");
//            return;
//        }
//
//        boolean result = adminService.deleteRepairOrder(repairId);
//
//        if (result ) {
//            System.out.println("报修单删除成功！");
//        } else {
//            System.out.println("报修单删除失败！");
//        }
//
//    }
//
//    /**
//     * 修改密码
//     */
//    private void changePassword() {
//        System.out.println("\n===== 修改密码 =====");
//
//        System.out.print("请输入原密码：");
//        String oldPassword = scanner.nextLine();
//
//        System.out.print("请输入新密码：");
//        String newPassword = scanner.nextLine();
//
//        System.out.print("请确认新密码：");
//        String confirmPassword = scanner.nextLine();
//
//        if (!newPassword.equals(confirmPassword)) {
//            System.out.println("两次输入的新密码不一致！");
//            return;
//        }
//        StudentService studentService = new StudentServiceImpl(currentUser);
//
//        boolean success = studentService.changePassword(currentUser.getId(), oldPassword, newPassword);
//
//        if (success) {
//            System.out.println("密码修改成功！请重新登录。");
//        } else {
//            System.out.println("密码修改失败！");
//        }
//
//    }
//}
