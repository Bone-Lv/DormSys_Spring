package com.gdut.controller;

import com.gdut.annotation.RequireRole;
import com.gdut.pojo.*;
import com.gdut.service.AdminService;
import com.gdut.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;


    /**
     * 查看所有报修单
     */
    @RequireRole("admin")
    @GetMapping
    private Result page(RepairOrderQueryParam repairOrderQueryParam) {
        log.info("查看所有报修单");
        PageResult<RepairOrder> page = adminService.list(repairOrderQueryParam);
        if(page == null){
            return Result.fail("没有报修单");
        }
        return Result.success(page);
    }


    /**
     * 查看报修单详情
     */
    @RequireRole
    @GetMapping("/{id}")
    private Result viewRepairOrderDetail(@PathVariable int id) {
        log.info("查看报修单详情，报修单编号：{}", id);
        RepairOrder repairOrder = adminService.viewRepairOrderDetail(id);
        if(repairOrder == null) return Result.fail("没有此报修单");
        return Result.success(repairOrder);
    }

    /**
     * 更新报修单
     */
    @RequireRole("admin")
    @PutMapping
    private Result updateRepairOrderStatus(@RequestBody RepairOrder repairOrder,HttpServletRequest req) {
        if(repairOrder.getStatus() == null)return Result.fail("请选择状态");
        log.info("更新报修单状态:{}",repairOrder.getStatus());
        if(adminService.updateRepairOrder(repairOrder,req)){
            return Result.success("更新成功");
        }
        return Result.fail("更新失败");
    }

    /**
     * 批量删除报修单
     */
    @RequireRole("admin")
    @DeleteMapping
    private Result deleteRepairOrder(@RequestParam ArrayList<Integer> ids) {
        log.info("批量删除报修单，报修单编号：{}", ids);
        if (adminService.deleteRepairOrder(ids)) {
            return Result.success("删除成功");
        }
        return Result.fail("删除失败");
    }


}
