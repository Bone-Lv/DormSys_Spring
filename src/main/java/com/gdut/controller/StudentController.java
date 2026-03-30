package com.gdut.controller;

import com.gdut.annotation.RequireRole;
import com.gdut.pojo.*;
import com.gdut.service.StudentService;
import com.gdut.util.AliyunOSSOperator;
import com.gdut.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    
    /**
     * 修改学生宿舍号
     */
    @RequireRole("student")
    @PutMapping("/{dormNum}")
    public Result changeDormNum(HttpServletRequest req,@PathVariable  String dormNum) {
        String id = JwtUtil.getCurrentUserId( req);

        log.info("修改学生宿舍号，学号：{}，宿舍号：{}",id,dormNum);
        if(studentService.changeDormNum(id,dormNum)){
            return Result.success("修改成功");
        }
        return Result.fail("修改失败");
    }

    /**
     * 创建报修单
     */
    @RequireRole("student")
    @PostMapping
    public Result createRepairOrder(@RequestBody RepairOrder repairOrder, HttpServletRequest req) {

        log.info("创建报修单，报修单信息：{}",repairOrder);
        if(studentService.createRepairOrder(repairOrder,req)){
            return Result.success("报修成功");
        }
        return Result.fail("报修失败");
    }

    /**
     * 查看学生报修记录
     */
    @RequireRole("student")
    @GetMapping
    public Result viewMyRepairRecords(HttpServletRequest req, RepairOrderQueryParam repairOrderQueryParam) {
        String id = JwtUtil.getCurrentUserId(req);
        log.info("查看学生报修记录，学号：{}", id);
        PageResult<RepairOrder> pageResult = studentService.listByStudentId(id, repairOrderQueryParam);
        return Result.success(pageResult);
    }
    /**
     * 更新报修单
     */
    @RequireRole("student")
    @PutMapping
    public Result updateRepairOrder(@RequestBody RepairOrder repairOrder) {
        log.info("更新报修单id：{}", repairOrder.getId());
        if(studentService.updateRepairOrder(repairOrder)){
            return Result.success("修改成功");
        }
        return Result.fail("修改失败");
    }



}
