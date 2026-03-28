package com.gdut.service;

import com.gdut.pojo.RepairOrder;

import java.util.ArrayList;
import java.util.List;

public interface StudentService {

    /**
     * 绑定或修改宿舍
     * @param studentId 学号
     * @param dormitoryInfo 宿舍信息
     * @return 操作结果
     */
    boolean bindOrModifyDormitory(String studentId, String dormitoryInfo);

    /**
     * 创建报修单
     * @param repairOrder 报修单信息
     * @return 操作结果
     */
    boolean createRepairOrder(RepairOrder repairOrder);

    /**
     * 查看报修记录
     * @param studentId 学号
     */
    ArrayList<RepairOrder> viewMyRepairRecords(String studentId);

    /**
     * 取消报修单
     * @param repairId 报修单 ID
     * @param studentId 学号
     * @return 操作结果
     */
    boolean cancelRepairOrder(int repairId, String studentId);

    /**
     * 修改密码
     * @param studentId 学号
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return 操作结果
     */
    boolean changePassword(String studentId, String oldPassword, String newPassword);
}



