package com.gdut.service;

import com.gdut.pojo.RepairOrder;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

public interface AdminService {

    /**
     * 查看所有报修单
     *
     * @return 报修单列表
     */
    ArrayList<RepairOrder> viewAllRepairOrders();

    /**
     * 查看报修单详情
     *
     * @param repairId 报修单 ID
     * @return 报修单信息
     */
    RepairOrder viewRepairOrderDetail(int repairId);

    /**
     * 更新报修单状态
     *
     * @param repairId 报修单 ID
     * @param status   新状态
     * @return 操作结果
     */
    boolean updateRepairOrderStatus(int repairId, String status);

    /**
     * 删除报修单
     *
     * @param repairId 报修单 ID
     * @return 操作结果
     */
    boolean deleteRepairOrder(int repairId);

    /**
     * 修改密码
     *
     * @param adminId     管理员 ID
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return 操作结果
     */
    boolean changePassword(String adminId, String oldPassword, String newPassword);

    /**
     * 接单
     *
     * @param repairId 报修单 ID
     * @param adminId  管理员 ID
     */
//    public boolean acceptRepairOrder(int repairId, String adminId);

}



