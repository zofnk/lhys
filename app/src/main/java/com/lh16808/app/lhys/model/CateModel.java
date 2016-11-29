package com.lh16808.app.lhys.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/16.
 */

public class CateModel implements Serializable {

    String qishu;
    String url;
    String title;
    String type;
    String newstime;
    String sid;

    public CateModel(String qishu, String url, String title, String type, String newstime, String sid) {
        this.qishu = qishu;
        this.url = url;
        this.title = title;
        this.type = type;
        this.newstime = newstime;
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQishu() {
        return qishu;
    }

    public void setQishu(String qishu) {
        this.qishu = qishu;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNewstime() {
        return newstime;
    }

    public void setNewstime(String newstime) {
        this.newstime = newstime;
    }
}
