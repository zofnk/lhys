package com.lh16808.app.lhys.model;


import java.util.ArrayList;
import java.util.List;

/**
 * 表格数据
 */
public class TableDataBean {
    //表头
    private List<String> str_title;
    //表数据
    private List<List<String>> str_data=new ArrayList<>();

    public List<String> getStr_title() {
        return str_title;
    }

    public void setStr_title(List<String> str_title) {
        this.str_title = str_title;
    }

    public List<List<String>> getStr_data() {
        return str_data;
    }

    public void setStr_data(List<List<String>> str_data) {
        this.str_data = str_data;
    }
}
