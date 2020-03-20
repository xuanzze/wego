package com.wego.common.pojo;

import java.io.Serializable;

/**
 * 
 */
public class WegoResult implements Serializable{

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    //构建其他状态的wegoresult对象
    public static WegoResult build(Integer status, String msg, Object data) {
        return new WegoResult(status, msg, data);
    }

    public static WegoResult ok(Object data) {
        return new WegoResult(data);
    }

    public static WegoResult ok() {
        return new WegoResult(null);
    }

    public WegoResult() {

    }

    public static WegoResult build(Integer status, String msg) {
        return new WegoResult(status, msg, null);
    }

    public WegoResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public WegoResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
