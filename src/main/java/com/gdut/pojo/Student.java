package com.gdut.pojo;

public class Student extends User {



    //构造方法
    public Student() {
        super();
    }

    public Student(String id, String password, String role ,String dormNum) {
        super(id, password, role, dormNum);
    }



    @Override
    public void setId(String id) {
        if (id == null) {
            System.out.println("学号不能为空");
           return ;
        }

        if (!id.startsWith("3125") && !id.startsWith("3225")) {
            System.out.println("学号前缀必须为 3125 或 3225");
            return ;
        }
        super.setId(id);

    }

}
