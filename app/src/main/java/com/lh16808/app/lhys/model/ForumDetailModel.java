package com.lh16808.app.lhys.model;

/**
 * Created by Administrator on 2016/11/11.
 */

public class ForumDetailModel {
    private int on;
    private String sid;
    private String newstime;
    private String newstext;
    private String title;
    private String userpic;
    private String groupname;
    private String username;
    private String userfen;

    public ForumDetailModel(int on, String sid, String newstime, String newstext, String title, String userpic, String groupname, String username, String userfen) {
        this.on = on;
        this.sid = sid;
        this.newstime = newstime;
        this.newstext = newstext;
        this.title = title;
        this.userpic = userpic;
        this.groupname = groupname;
        this.username = username;
        this.userfen = userfen;
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

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserfen() {
        return userfen;
    }

    public void setUserfen(String userfen) {
        this.userfen = userfen;
    }

    public int getOn() {
        return on;
    }

    public void setOn(int on) {
        this.on = on;
    }

    public String getNewstime() {
        return newstime;
    }

    public void setNewstime(String newstime) {
        this.newstime = newstime;
    }

    public String getNewstext() {
        return newstext;
    }

    public void setNewstext(String newstext) {
        this.newstext = newstext;
    }
}
