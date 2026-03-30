package com.gdut.mapper;

import com.gdut.pojo.AdminLogQueryParam;
import com.gdut.pojo.AdminOperationLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AdminOperationLogMapper {

    /**
     * 插入日志
     * @param log 日志
     * @return 影响行数
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AdminOperationLog log);

    /**
     * 查询日志
     * @param adminLogQueryParam 查询参数
     * @return 日志列表
     */
    List<AdminOperationLog> list(AdminLogQueryParam adminLogQueryParam);


    /**
     * 批量删除日志
     * @param ids 日志id列表
     * @return 影响行数
     */
    int deleteByIds(ArrayList<Integer> ids);
}
