package com.gdut.service.impl;

import com.gdut.mapper.AdminOperationLogMapper;
import com.gdut.pojo.AdminLogQueryParam;
import com.gdut.pojo.AdminOperationLog;
import com.gdut.pojo.PageResult;
import com.gdut.service.AdminLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;


@Service
public class AdminLogServiceImpl implements AdminLogService {

    @Autowired
    private AdminOperationLogMapper adminOperationLogMapper;

    @Override
    public int insert(AdminOperationLog log) {
        //获取操作时间
        log.setOperationTime(LocalDateTime.now());
        return adminOperationLogMapper.insert(log);
    }

    @Override
    public PageResult<AdminOperationLog> list(AdminLogQueryParam queryParam) {
        //获取分页参数
        Integer page = queryParam.getPage();
        Integer pageSize = queryParam.getPageSize();
        //分页插件分页

        PageHelper.startPage(page,pageSize);
        Page<AdminOperationLog> list = (Page<AdminOperationLog>) adminOperationLogMapper
                .list(queryParam);

        //封装成PageResult对象并返回
        return new PageResult<>(list.getTotal(),list.getResult());
    }

    @Override
    public int delete(ArrayList<Integer> ids) {
        return adminOperationLogMapper.deleteByIds(ids) ;
    }
}
