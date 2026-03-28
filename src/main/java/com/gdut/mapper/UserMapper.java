package com.gdut.dao;

import com.gdut.pojo.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

   //添加用户
    @Insert("insert into user (id,password,role,dorm_num) values (#{id},#{password},#{role},#{dormNum})")
    int addUser(User user);

    //查询用户
    @Select("select * from user where id = #{id}")
//    @Results(id = "userMap", value = {
//            @Result(property = "dormNum", column = "dorm_num")
//    })
    User SelectById(@Param("id") String id);

    //修改用户
    @Insert("update user set password = #{password} where id = #{id}")
    int updatePassword(@Param("id") String id, @Param("password") String password);

    //修改用户宿舍号
    @Insert("update user set dorm_num = #{dormNum} where id = #{id}")
    int updateDormNum(@Param("id") String id, @Param("dormNum") String dormNum);


}
