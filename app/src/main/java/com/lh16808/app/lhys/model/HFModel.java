package com.lh16808.app.lhys.model;

/**
 * Created by Administrator on 2016/11/24.
 */

public class HFModel {
    String hfid;
    String hfusername;
    String hfnewstime;
    String hfText;

    public HFModel(String hfid, String hfusername, String hfnewstime, String hfText) {
        this.hfid = hfid;
        this.hfusername = hfusername;
        this.hfnewstime = hfnewstime;
        this.hfText = hfText;
    }

    public String getHfid() {
        return hfid;
    }

    public void setHfid(String hfid) {
        this.hfid = hfid;
    }

    public String getHfusername() {
        return hfusername;
    }

    public void setHfusername(String hfusername) {
        this.hfusername = hfusername;
    }

    public String getHfnewstime() {
        return hfnewstime;
    }

    public void setHfnewstime(String hfnewstime) {
        this.hfnewstime = hfnewstime;
    }

    public String getHfText() {
        return hfText;
    }

    public void setHfText(String hfText) {
        this.hfText = hfText;
    }
}
