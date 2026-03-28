package com.gdut.dao;

import com.gdut.pojo.RepairOrder;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface RepairOrderMapper {

    //添加报修单
    @Insert("insert into repair_order (student_id, device_type, description, status, dorm_num, admin_id,update_time,create_time) " +
            "values (#{studentId}, #{deviceType}, #{description}, #{status}, #{dormNum}, #{adminId},#{updateTime},#{createTime})")
    int add(RepairOrder repairOrder);

    //查询所有报修单
    @Select("select * from repair_order")
    ArrayList<RepairOrder> selectAll();

    //查询指定学生报修单
    @Select("select * from repair_order where student_id = #{studentId}")
    ArrayList<RepairOrder> selectByStudentId(String studentId);

    //查询订单详情
    @Select("select * from repair_order where id = #{id} ")
    RepairOrder selectById(int id);

    //取消报修单
    @Update("update repair_order set status = '已取消',update_time = now() where id = #{id}")
    int cancel(int repairId);

    //更新报修单
    @Update("update repair_order set status = #{status},update_time =now() ,admin_id = #{adminId} where id = #{id}")
    int update(RepairOrder repairOrder);

//    //管理员更新报修单
//    @Update("update repair_order set status = #{status},update_time =now(), admin_id = #{adminId} where id = #{id}")
//    int adminUpdate(RepairOrder repairOrder);

    //删除报修单
    @Delete("delete from repair_order where id = #{id}")
    int delete(int repairId);


}
