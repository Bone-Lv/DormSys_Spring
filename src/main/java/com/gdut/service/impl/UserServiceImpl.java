package com.gdut.service.impl;

import com.gdut.dao.UserMapper;
import com.gdut.pojo.Admin;
import com.gdut.pojo.Student;
import com.gdut.pojo.User;
import com.gdut.service.UserService;
import com.gdut.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Scanner;

public class UserServiceImpl implements UserService {

    //获取sqlSessionFactory对象
    private final SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory() ;

    //当前用户信息
    private User currentUser;


    //登录
    @Override
    public boolean login(User user) {
        //创建 SqlSession
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        Scanner scanner = new Scanner(System.in);


        //查询数据库中的用户
        User user1 = mapper.SelectById(user.getId());

        if (user1 == null) {
            //用户不存在
            System.out.println("账号不存在！");
            sqlSession.close();
            return false;
        }


        //验证密码
        if (!user.getPassword().equals(user1.getPassword())) {

            //密码错误
            sqlSession.close();
            return false;
        }


        //判断是否为第一次登录
        if(user1.getDormNum() == null && user1.getRole().equals("学生")){
            System.out.print("用户第一次登录，请输入宿舍号：");
            String dormNum = scanner.nextLine();
            user.setDormNum(dormNum);

            //将宿舍信息上传至数据库
            int flag = mapper.updateDormNum(user1.getId(),dormNum);
            if(flag == 1){
                System.out.println("宿舍信息上传成功！");
                sqlSession.commit();
            }else{
                System.out.println("宿舍信息上传失败！");
            }
        }

        //释放资源
        sqlSession.close();
        this.setCurrentUser(user1);
        return true;

    }

    // 注册
    @Override
    public boolean register(User user) {

        //创建SqlSession
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        Scanner scanner = new Scanner(System.in);

        //查询数据库里是否有该用户
        User user1 = mapper.SelectById(user.getId());

        if(user1 ==null){
            //用户不存在
            if(mapper.addUser(user) == 1){
                sqlSession.commit();

            }
        }

        //释放资源
        sqlSession.close();
        return user1==null;
    }

    @Override
    public User getCurrentUser() {
        return  currentUser;
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

}
