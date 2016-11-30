package com.lh16808.app.lhys.model;

import java.io.Serializable;

/**
 * Created by wfz on 16-6-12.
 */
public class HttpDataEntity<T> implements Serializable {

    /**
     * 响应参数：
     * statuscode:
     * 400:没有请求数据，获取请求数据不全
     * 4002:手机格式不正确
     * 1002：该手机号码已注册
     * 4001：发送失败相关提示信息
     * 4000：不符合发送短信模块
     * 200:返回成功
     */
    private String statuscode;
    private String errormsg;
    private T data;
    private boolean success;

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
