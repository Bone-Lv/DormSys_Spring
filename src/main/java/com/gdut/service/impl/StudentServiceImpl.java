package com.gdut.service.impl;

import com.gdut.mapper.RepairOrderMapper;
import com.gdut.mapper.UserMapper;
import com.gdut.enums.RepairOrderStatus;
import com.gdut.pojo.PageResult;
import com.gdut.pojo.RepairOrder;
import com.gdut.pojo.RepairOrderQueryParam;
import com.gdut.pojo.User;
import com.gdut.service.StudentService;
import com.gdut.util.AliyunOSSOperator;
import com.gdut.util.JwtUtil;
import com.gdut.util.PasswordUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RepairOrderMapper repairOrderMapper;

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    /**
     * 修改学生宿舍号
     * @param id 学号
     * @param dormNum 宿舍号
     * @return 修改成功与否
     */
    @Override
    public boolean changeDormNum(String id, String dormNum) {
        int success =userMapper.updateDormNum(id,dormNum);
        return success > 0;
    }

    /**
     * 更新报修单
     * @param repairOrder 报修单信息
     * @return 更新成功与否
     */
    @Override
    public boolean updateRepairOrder(RepairOrder repairOrder) {
        repairOrder.setUpdateTime(LocalDateTime.now());
        int success = repairOrderMapper.update(repairOrder);
        return success > 0;
    }
    /**
     * 创建报修单
     * @param repairOrder 报修单信息
     * @param request 前端请求
     * @return 创建成功与否
     */

    @Override
    public boolean createRepairOrder(RepairOrder repairOrder, HttpServletRequest request) {
        try {
            //获取当前角色 id
            String id = JwtUtil.getCurrentUserId(request);
            User user = userMapper.selectById(id);

            //填充基本数据
            repairOrder.setCreateTime(LocalDateTime.now());
            repairOrder.setUpdateTime(LocalDateTime.now());
            repairOrder.setStudentId(id);
            repairOrder.setDormNum(user.getDormNum());

            int success = repairOrderMapper.insert(repairOrder);
            return success > 0;
            
        } catch (Exception e) {
            log.error("创建报修单失败，需要清理 OSS 文件", e);
            
            // 清理已上传的 OSS 文件
            if (repairOrder.getImage() != null && !repairOrder.getImage().isEmpty()) {
                try {
                    String objectKey = aliyunOSSOperator.extractObjectKeyFromUrl(repairOrder.getImage());
                    aliyunOSSOperator.deleteFile(objectKey);
                    log.info("已删除 OSS 文件：{}", objectKey);
                } catch (Exception ex) {
                    log.error("删除 OSS 文件失败：{}", ex.getMessage());
                }
            }
            return false;
        }
    }



    /**
     * 查看学生报修记录
     * @param studentId 学号
     * @param repairOrderQueryParam 查询参数
     * @return 报修记录
     */
    @Override
    public PageResult<RepairOrder> listByStudentId(String studentId, RepairOrderQueryParam repairOrderQueryParam) {
        log.info("查看学生报修记录，学号：{}", studentId);
        int page = repairOrderQueryParam.getPage();
        int pageSize = repairOrderQueryParam.getPageSize();
        PageHelper.startPage(page,pageSize);//设置分页参数
        String status = repairOrderQueryParam.getStatus();
        LocalDate startTime = repairOrderQueryParam.getStartTime();
        LocalDate endTime = repairOrderQueryParam.getEndTime();
        String dormNum = repairOrderQueryParam.getDormNum();
        log.info("{}",repairOrderQueryParam);

        Page<RepairOrder> repairOrderPage = (Page<RepairOrder>) repairOrderMapper
                .listByStudentId(studentId, status, startTime, endTime, dormNum);
        return new PageResult<>(repairOrderPage.getTotal(),repairOrderPage.getResult());

    }

}
