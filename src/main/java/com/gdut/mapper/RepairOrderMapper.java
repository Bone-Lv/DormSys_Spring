package com.gdut.mapper;

import com.gdut.pojo.RepairOrder;
import com.gdut.pojo.RepairOrderQueryParam;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface RepairOrderMapper {

    //添加报修单
    @Insert("insert into repair_order (student_id, device_type, description, status, dorm_num, admin_id,update_time,create_time,image) " +
            "values (#{studentId}, #{deviceType}, #{description}, #{status}, #{dormNum}, #{adminId},#{updateTime},#{createTime},#{image})")
    int insert(RepairOrder repairOrder);

    //查询所有报修单
    ArrayList<RepairOrder> list(RepairOrderQueryParam repairOrderQueryParam);

    //查询历史报修单
    ArrayList<RepairOrder> listByStudentId(String studentId, String status, LocalDate startTime, LocalDate endTime, String dormNum);

    //查询订单详情
    @Select("select * from repair_order where id = #{id} ")
    RepairOrder selectById(int id);

    //取消报修单
    @Update("update repair_order set status = '已取消',update_time = now() where id = #{id}")
    int cancel(int repairId);

    //更新报修单
    int update(RepairOrder repairOrder);


    //删除报修单
    int delete(List<Integer> ids);

    /**
     * 取消报修单
     * @param repairId 报修单id
     * @return 影响的数据个数
     */
    @Update("update repair_order set status = '已取消',update_time = now() where id = #{repairId}")
    int cancelRepairOrder(int repairId);
}
