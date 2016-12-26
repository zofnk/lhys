package com.lh16808.app.lhys.model;

import android.content.Context;


import com.lh16808.app.lhys.utils.dataUtils.ToastUtil;

import java.io.Serializable;

/**
 * 基本
 */
public class BaseBean implements Serializable {
    //状态码
    int zt = 1;
    //提示
    String ts = "";

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public void toZtString(Context context) {
        ToastUtil.toastShow(context, ts + (zt == 1 ? "" : "[状态码:" + zt + "]"));
    }
}
