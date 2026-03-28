package com.gdut.pojo;

import lombok.Data;

@Data
public class Result {
    private Integer code;//1:成功，0：失败
    private String msg;
    private Object data;

    public Result() {
    }


    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(1);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    public static Result success(String msg) {
        Result result = new Result();
        result.setCode(1);
        result.setMsg(msg);
        return result;
    }

    public static Result fail(String msg) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("操作失败");
        return result;
    }

    public static Result fail() {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("操作失败");
        return result;
    }
}