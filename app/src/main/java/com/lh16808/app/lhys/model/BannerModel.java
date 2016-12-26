package com.lh16808.app.lhys.model;

/**
 * Created by Administrator on 2016/12/5.
 */

public class BannerModel {
    String titlepic;
    String title;

    public String getTitlepic() {
        return titlepic;
    }

    public void setTitlepic(String titlepic) {
        this.titlepic = titlepic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BannerModel{" +
                "titlepic='" + titlepic + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
