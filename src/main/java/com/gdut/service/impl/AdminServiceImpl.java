package com.gdut.service.impl;

import com.gdut.dao.RepairOrderMapper;
import com.gdut.dao.UserMapper;
import com.gdut.enums.RepairOrderStatus;
import com.gdut.pojo.RepairOrder;
import com.gdut.pojo.User;
import com.gdut.service.AdminService;
import com.gdut.util.PasswordUtil;
import com.gdut.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;

public class AdminServiceImpl implements AdminService {
    //获取factory对象
    private final SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    private User currentUser;

    public AdminServiceImpl() {
    }

    public AdminServiceImpl(User user) {
        this.currentUser = user;
    }




    @Override
    public ArrayList<RepairOrder> viewAllRepairOrders() {
        //获取SqlSession对象
         SqlSession sqlSession = factory.openSession();
         RepairOrderMapper mapper = sqlSession.getMapper(RepairOrderMapper.class);
        ArrayList<RepairOrder> repairOrders = mapper.selectAll();
        //释放资源
         sqlSession.close();

        return repairOrders;
    }

    @Override
    public RepairOrder viewRepairOrderDetail(int repairId) {
        //获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        RepairOrderMapper mapper = sqlSession.getMapper(RepairOrderMapper.class);
        RepairOrder repairOrder = mapper.selectById(repairId);

        //释放资源
        sqlSession.close();

        return repairOrder;
    }

    @Override
    public boolean updateRepairOrderStatus(int repairId, String status) {
        //获取sqlsession对象
        SqlSession sqlSession = factory.openSession();
        RepairOrderMapper mapper = sqlSession.getMapper(RepairOrderMapper.class);

        //判断有无该订单
        RepairOrder repairOrder1 = mapper.selectById(repairId);
        if (repairOrder1 == null)return false;

        if (RepairOrderStatus.fromDescription(status) == null) {
            System.out.println("❌ 无效的状态值：" + status);
            return false;
        }

        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setId(repairId);
        repairOrder.setStatus(status);
        repairOrder.setAdminId(currentUser.getId());

        int result = mapper.update(repairOrder);
        if(result == 1){
            sqlSession.commit();
        }else{
            sqlSession.rollback();
        }
        sqlSession.close();
        return result == 1;
    }

    @Override
    public boolean deleteRepairOrder(int repairId) {
        //获取sqlsession对象
        SqlSession sqlSession = factory.openSession();
        RepairOrderMapper mapper = sqlSession.getMapper(RepairOrderMapper.class);

        int result = mapper.delete(repairId);
        if(result == 1){
            sqlSession.commit();
        }else{
            sqlSession.rollback();
        }
        sqlSession.close();

        return result == 1;
    }

    @Override
    public boolean changePassword(String adminId, String oldPassword, String newPassword) {
        return false;
    }
}
