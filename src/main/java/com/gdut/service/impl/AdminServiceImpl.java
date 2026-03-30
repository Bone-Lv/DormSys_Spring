package com.gdut.service.impl;

import com.gdut.mapper.RepairOrderMapper;
import com.gdut.pojo.PageResult;
import com.gdut.pojo.RepairOrder;
import com.gdut.pojo.RepairOrderQueryParam;
import com.gdut.service.AdminService;
import com.gdut.util.JwtUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RepairOrderMapper repairOrderMapper;



    /**
     * 查看报修单详情
     */
    @Override
    public RepairOrder viewRepairOrderDetail(int repairId) {
        return repairOrderMapper.selectById(repairId);
    }

    /**
     * 更新报修单
     */
    @Override
    public boolean updateRepairOrder(RepairOrder repairOrder,HttpServletRequest req) {
        //填充基本信息
        repairOrder.setUpdateTime(LocalDateTime.now());
        repairOrder.setAdminId(JwtUtil.getCurrentUserId( req));
        //修改报修单
        int success = repairOrderMapper.update(repairOrder);
        return success == 1;
    }

    /**
     * 批量删除报修单
     */

    @Override
    public boolean deleteRepairOrder(ArrayList<Integer> ids) {
        return repairOrderMapper.delete(ids) >0;
    }

    /**
     * 查看符合条件的报修单
     */
    @Override
    public PageResult<RepairOrder> list(RepairOrderQueryParam repairOrderQueryParam) {

        //设置分页参数
        int page = repairOrderQueryParam.getPage();
        int pageSize = repairOrderQueryParam.getPageSize();
        PageHelper.startPage(page,pageSize);

        Page<RepairOrder> list = (Page<RepairOrder>) repairOrderMapper.list(repairOrderQueryParam);

        return new PageResult<>(list.getTotal(),list.getResult());

    }


}
