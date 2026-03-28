package com.gdut.service.impl;

import com.gdut.dao.RepairOrderMapper;
import com.gdut.dao.UserMapper;
import com.gdut.enums.RepairOrderStatus;
import com.gdut.pojo.RepairOrder;
import com.gdut.pojo.User;
import com.gdut.service.StudentService;
import com.gdut.util.PasswordUtil;
import com.gdut.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;

public class StudentServiceImpl implements StudentService {

        //获取sqlSessionFactory对象
        private static final SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();


    //当前用户信息
    private User currentUser;

    public StudentServiceImpl() {
    }

    public StudentServiceImpl(User user) {
        this.currentUser = user;
    }

    @Override
    public boolean bindOrModifyDormitory(String studentId, String dormNum) {
        //获取sqlSession
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        //修改宿舍信息
        int flag = mapper.updateDormNum(studentId,dormNum);
        if(flag == 1){
            //成功
            sqlSession.commit();
        }else{
            //失败
            sqlSession.rollback();
        }
        sqlSession.close();
        return flag == 1;

    }

    @Override
    public boolean createRepairOrder(RepairOrder repairOrder) {

        SqlSession sqlSession = factory.openSession();
        RepairOrderMapper mapper = sqlSession.getMapper(RepairOrderMapper.class);

        if (repairOrder.getStatus() == null || RepairOrderStatus.fromDescription(repairOrder.getStatus()) == null) {
            System.out.println("❌ 无效的报修单状态！");
            sqlSession.rollback();
            return false;
        }

        //添加该用户
        int flag = mapper.add(repairOrder);

        if(flag == 1){
            sqlSession.commit();
        }else{
            sqlSession.rollback();
        }
        sqlSession.close();
        return flag == 1;
    }

    @Override
    public ArrayList<RepairOrder> viewMyRepairRecords(String studentId) {
        SqlSession sqlSession = factory.openSession();
        RepairOrderMapper mapper = sqlSession.getMapper(RepairOrderMapper.class);
        //释放资源
        ArrayList<RepairOrder> list = mapper.selectByStudentId(studentId);
        sqlSession.close();
        return list;

    }

    @Override
    public boolean changePassword(String studentId, String oldPassword, String newPassword) {
        SqlSession sqlSession = factory.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        String encodeNewPassword = PasswordUtil.encode(newPassword);
        boolean flag = currentUser.getPassword().equals(oldPassword) && mapper.updatePassword(studentId,encodeNewPassword) == 1;
        if(flag){
            //修改成功
            sqlSession.commit();
        }else{
            //修改失败
            sqlSession.rollback();
        }

        sqlSession.close();
        return flag;
    }

    @Override
    public boolean cancelRepairOrder(int repairId, String studentId) {
        //获取sqlSession
        SqlSession sqlSession = factory.openSession();
        RepairOrderMapper mapper = sqlSession.getMapper(RepairOrderMapper.class);
        //取消该报修单
        int success = mapper.cancel(repairId);
        if (success == 1){
            sqlSession.commit();
        }else{
            sqlSession.rollback();
        }
        sqlSession.close();
        return success == 1;
    }
}
