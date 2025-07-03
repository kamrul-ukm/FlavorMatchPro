package com.boot.bean;

import lombok.Data;

@Data
public class R<T> {

    private Integer code;

    private String msg;

    private T data;

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static R ok() {
        return ok("操作成功", null);
    }


    public static <T> R ok(T data) {
        return ok("操作成功!", data);
    }


    public static <T> R ok(String msg, T data) {
        R r = new R();
        r.setCode(200);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

