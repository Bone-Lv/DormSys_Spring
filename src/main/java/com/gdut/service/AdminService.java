package com.gdut.service;

import com.gdut.pojo.PageResult;
import com.gdut.pojo.RepairOrder;
import com.gdut.pojo.RepairOrderQueryParam;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;

public interface AdminService {


    /**
     * 查看报修单详情
     *
     * @param repairId 报修单 ID
     * @return 报修单信息
     */
    RepairOrder viewRepairOrderDetail(int repairId);

    /**
     * 更新报修单
     *
     */
    boolean updateRepairOrder(RepairOrder repairOrder, HttpServletRequest request);


    /**
     * 删除报修单
     *
     * @param repairIds 报修单 ID
     * @return 操作结果
     */
    boolean deleteRepairOrder(ArrayList<Integer> repairIds);

    PageResult<RepairOrder> list(RepairOrderQueryParam repairOrderQueryParam);


}



