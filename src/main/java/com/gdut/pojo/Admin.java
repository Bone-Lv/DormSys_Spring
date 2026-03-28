package com.gdut.pojo;

public class Admin extends User {


    public Admin() {
        super();
    }

    public Admin(String id, String password, String role , String dormNum) {
        super(id, password, role, dormNum);
    }

    @Override
    public void setId(String id) {
        if (id == null) {
            System.out.println("工号不能为空");
            return ;
        }

        if (!id.startsWith("0025")) {
            System.out.println("工号前缀必须为 0025");
            return  ;
        }
        super.setId(id);

    }
}
