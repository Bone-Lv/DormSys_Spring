package com.gdut.service;

import com.gdut.pojo.AdminLogQueryParam;
import com.gdut.pojo.AdminOperationLog;
import com.gdut.pojo.PageResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface AdminLogService {
    /**
     * 插入日志
     * @param log 日志
     * @return 影响行数
     */
    int insert(AdminOperationLog log);

    /**
     * 查询日志
     * @param queryParam 查询参数
     * @return 日志列表
     */
    PageResult<AdminOperationLog> list(AdminLogQueryParam queryParam);
    /**
     * 删除日志
     * @param ids 日志id
     * @return 影响行数
     */
    int delete(ArrayList<Integer> ids);
}
