package com.gdut.mapper;

import com.gdut.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

   //添加用户
    @Insert("insert into user (id,password,role,dorm_num) values (#{id},#{password},#{role},#{dormNum})")
    int addUser(User user);

    //查询用户
    @Select("select * from user where id = #{id}")
    User selectById(@Param("id") String id);

    //修改用户
    @Update("update user set password = #{password} where id = #{id}")
    int updatePassword(User user);

    @Update("update user set dorm_num = #{dormNum} where id = #{id}")
    int updateDormNum(@Param("id") String id, @Param("dormNum") String dormNum);

    @Select("select * from user where id = #{id} and password = #{password}")
    User selectByIdAndPassword(User user);
}
