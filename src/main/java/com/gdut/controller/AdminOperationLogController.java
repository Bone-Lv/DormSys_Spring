package com.gdut.controller;

import com.gdut.annotation.RequireRole;
import com.gdut.pojo.AdminLogQueryParam;
import com.gdut.pojo.AdminOperationLog;
import com.gdut.pojo.PageResult;
import com.gdut.pojo.Result;
import com.gdut.service.AdminLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RequestMapping("/admin/log")
@RestController
public class AdminOperationLogController {

    @Autowired
    private AdminLogService adminLogService;

    /**
     * 查看所有日志
     */
    @RequireRole("admin")
    @GetMapping
    public Result list(AdminLogQueryParam adminLogQueryParam) {
        log.info("查看所有日志");
        PageResult<AdminOperationLog> page = adminLogService.list(adminLogQueryParam);
        if(page == null){
            return Result.fail("没有找到相关日志");
        }
        return Result.success(page);
    }

    /**
     * 添加日志
     */
    @RequireRole("admin")
    @PostMapping
    public Result add(@RequestBody AdminOperationLog adminOperationLog) {
        log.info("添加日志");
        int success = adminLogService.insert(adminOperationLog);
        if(success > 0) return Result.success("日志添加成功");
        return Result.fail("日志添加失败");
    }

    /**
     * 删除日志
     */
    @RequireRole("admin")
    @DeleteMapping
    public Result delete(@RequestParam ArrayList<Integer> ids) {
        log.info("批量删除日志ids:{}",ids);
        int success = adminLogService.delete(ids);
        if(success > 0) return Result.success("日志删除成功");
        return Result.fail("日志删除失败");
    }
}
