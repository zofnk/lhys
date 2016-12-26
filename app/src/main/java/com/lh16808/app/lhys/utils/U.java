package com.lh16808.app.lhys.utils;

/**
 * Created by Administrator on 2016/10/10.
 */

public class U {
    public static String ALL = "http://app.admin.mahuiapp.com:889/siteurl.json";
    //    public static String U0 = "http://wap.jizhou56.com:8090/";
    public static String U0 = "https://www.jizhou56.com:8099/";
    public static String U1_ziliao = "http://ziliao.jizhou56.com:8090/";
    public static String U2_ad = "http://ad.jizhou56.com:8090/";
    public static String U3_img = "http://img.jizhou56.com:8090/";
    public static String U4_app = "http://app.jizhou56.com:8090/";
    public static String UMV = "http://meinv.jizhou56.com:8090/";
    public static String IMAGE_URL = "http://joyhelp.img-cn-hangzhou.aliyuncs.com/Uploads";

    //web替換
    public static String webUrl(String url) {
        return url.replaceAll("https://www.jizhou56.com:8099/", "http://wap.jizhou56.com:8090/");
    }
}
