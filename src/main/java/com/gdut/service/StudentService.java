package com.gdut.service;

import com.gdut.pojo.PageResult;
import com.gdut.pojo.RepairOrder;
import com.gdut.pojo.RepairOrderQueryParam;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;

public interface StudentService {


    /**
     * 创建报修单
     * @param repairOrder 报修单信息
     * @return 操作结果
     */
    boolean createRepairOrder(RepairOrder repairOrder,HttpServletRequest req);



    /**
     * 修改宿舍号
     * @param id 学号
     * @param dormNum 宿舍号
     * @return 操作结果
     */
    boolean changeDormNum(String id, String dormNum);

    /**
     * 修改保修单
     * @param repairOrder 报修单信息
     * @return 操作结果
     */
    boolean updateRepairOrder(RepairOrder repairOrder);
    /**
     * 根据学生ID查询报修单
     * @param id 学号
     * @param repairOrderQueryParam 报修单查询参数
     * @return 报修单列表
     */
    PageResult<RepairOrder> listByStudentId(String id, RepairOrderQueryParam repairOrderQueryParam);
}



