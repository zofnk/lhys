package com.lh16808.app.lhys.marco;

import android.content.Context;


import com.lh16808.app.lhys.R;

import java.util.Random;


/**
 * 接口地址
 */
public class ApiConfig {
    private static final String TAG = "ApiConfig";
    private static final Random RANDOM = new Random();
    /**
     * 基础地址
     */
    public static String ALL; // ALL
    public static String BASE_URL; // 服务器
    public static String BASE_URL_IMG; // 服务器
    public static String BASE_URL_KJ; //历史开奖记录
    public static String BASE_URL_AD;//广告基地址
    public static String BASE_URL_ZL;// 文字资料url
    public static Boolean IS_LOG = false; // 是否开启Log打印

    public static void init(Context context) {
        ALL = context.getString(R.string.all_url);
        BASE_URL = context.getString(R.string.base_url);
        BASE_URL_AD = context.getString(R.string.base_url_ad);
        BASE_URL_IMG = context.getString(R.string.base_url_img);
        BASE_URL_KJ = context.getString(R.string.base_url_kj);
        BASE_URL_ZL = context.getString(R.string.base_url_zl);
    }

    /**
     * @param url 需要拼接的相对地址
     * @return 返回完整链接
     */
    public static String getBaseUrl(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }

        if ((url.contains("http://") || url.contains("https://"))) {
            return url;
        }
        if (!url.startsWith("/")) {
            return BASE_URL + "/" + url;
        }
        return BASE_URL + url;
    }


    /**
     * @param url 需要拼接的相对地址
     * @return 返回完整链接
     */
    public static String getBaseUrlKj(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }

        if ((url.contains("http://") || url.contains("https://"))) {
            return url;
        }
        if (!url.startsWith("/")) {
            return BASE_URL_KJ + "/" + url;
        }
        return BASE_URL_KJ + url;
    }

    /**
     * 广告地址拼接
     *
     * @param url 需要拼接的相对地址
     * @return 返回完整链接
     */
    public static String getBaseUrlAD(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }

        if ((url.contains("http://") || url.contains("https://"))) {
            return url;
        }
        if (!url.startsWith("/")) {
            return BASE_URL_AD + "/" + url;
        }
        return BASE_URL_AD + url;
    }

    /**
     * 图片
     *
     * @param url 需要拼接的相对地址
     * @return 返回完整链接
     */
    public static String getImageUrl(String url) {
//        if (url == null || url.length() == 0) {
//            return null;
//        }
//
//        if (url.contains("http://") || url.contains("https://")) {
//            return url;
//        }
//        if (!url.startsWith("/")) {
//            return IMAGE_URL + "/" + url;
//        }
//        IMAGE_URL +
        return url;
    }

    /**
     * kj
     * 開獎的數據
     * "http://120.76.44.201:8090/ceshi.xml";
     */
    public static final String KJXML = "http://120.76.44.201:8090/ceshi.xml";
    public static final String XmlKJBM = "kjbm.xml";
    /**
     * ad
     * 六合运势轮播
     */
    public static final String AD_BANNER = "appad/6hysad/index.json";

    // 六合运势寻宝
    public static String AD_XB = "appad/6hys_xb/index.json";
    /**
     * ad
     * 六合寻宝通栏:
     */
    public static final String AdKey = "appad/lhxb/index.json";
    /**
     * wap
     * 历史数据
     */
    public static final String HisData = "json/";
    /**
     * wap
     * 意见反馈
     */
    public static final String FeedBackURL = "e/enews/index.php";

    /**
     * wap
     * 公告信息
     */
    public static final String GGXXURL = "json/gmsg.json";

    /**
     * wap
     */
    public static final String JSONURL = "e/extend/json/kjjson.php";
    /**
     * wap
     * 更多应用
     */
    public static final String APK_DOWDLAN = "json/moreapp.json";
    /**
     * wap
     * 贴子
     */
    public static final String JSON = "e/extend/json/json.php";
    //      论坛
    public static String BBS = "json/list/bbs.json";
    //      心水资料
    public static String XINSHUI = "json/list/xinshui.json";
    //    文字资料
    public static String WENZI = "json/list/wenzi.json";
    //     高手资料
    public static String GAOSHUO = "json/list/gaoshuo.json";
    //     公式资料
    public static String GONGSHI = "json/list/gongshi.json";
    //     精品杀项
    public static String SHAXIANG = "json/list/shaxiang.json";
    //      香港挂牌
    public static String XIANGGANG = "json/list/xianggang.json";
    //     全年资料
    public static String QUANNIAN = "json/list/quannian.json";
    //      六合属性
    public static String SHUXING = "json/list/shuxing.json";


    /**
     * wap
     * 开奖2
     */
    public static final String KJ = "kjbm.json";
    public static final String JsonXML = "http://120.76.44.201:8090/ceshi.json";

    /**
     * wap
     * 图库
     * 六合图库,,,
     * 66,彩色图库,,
     * 89,玄机彩图,,
     * 92,黑白图库,,
     * 65,全年图库,,
     */
    /**
     * 曾道人
     * 白小姐
     */
    public static final String XianJi = "e/extend/json/xjjson.php";

    /**
     * wab
     * 开奖下方的web
     */
    public static String KJURL = "kaijiang.php";
    // 开奖日期：
    public static String KaiJianRQURL = "appdate.php";
    public static String KaiJianTime = "e/extend/json/json.php?enews=Kjtime";
    public static String KJTime = "http://7xpt79.com1.z0.glb.clouddn.com/time.json";
    // 走势分析：
    public static String KaiJianFXURL = "e/extend/trend/";
    /**
     * wap
     * 用户
     */
    public static String USER = "e/member/doaction.php";
    /**
     * wap
     */
    public static String[] MYFAVA1 = {"Myfava", "Mybbs", "Myrep"};
    public static String[] MYFAVA2 = {"", "Mybbs1", "Myrep1"};
    public static String[] MYFAVA3 = {"", "MybbsE1", "MyrepE1"};
    /**
     * wap
     * 帖子编辑
     */

    public static String BIANJI = "e/DoInfo/ecms.php";

    /**
     * wap
     * 检查更新
     */
    public static String UPGRUDE = "e/extend/json/sz.php?enews=6hys";

    /**
     * 彩票专区：
     */
    // 大乐透：http://app.jizhou56.com:8090/daletou.php
    public static String Web_DaLeTou = "daletou.php";
    // 福彩3D：http://app.jizhou56.com:8090/3d.php
    public static String Web_FuCai3D = "3d.php";
    // 排列3：http://app.jizhou56.com:8090/pl3.php
    public static String Web_PaiLie3 = "pl3.php";
    // 排列5：http://app.jizhou56.com:8090/pl5.php
    public static String Web_PaiLie5 = "pl5.php";
    // 七乐彩：http://app.jizhou56.com:8090/qlc.php
    public static String Web_QiLeCai = "qlc.php";
    // 七星彩：http://app.jizhou56.com:8090/qxc.php
    public static String Web_QiXingCai = "qxc.php";
    // 双色球：http://app.jizhou56.com:8090/ssq.php
    public static String Web_ShuangSeQiu = "ssq.php";
    // 体彩36选7：http://app.jizhou56.com:8090/fjtc36x7.php
    public static String Web_TiCai37X7 = "fjtc36x7.php";
    // 重庆时时彩：http://app.jizhou56.com:8090/cqssc.php
    public static String Web_ShiShiCai_CQ = "cqssc.php";
    // 黑龙江时时彩：http://app.jizhou56.com:8090/hljssc.php
    public static String Web_ShiShiCai_HLJ = "hljssc.php";
    // 吉林时时彩：http://app.jizhou56.com:8090/jlssc.php
    public static String Web_ShiShiCai_JL = "jlssc.php";
    // 天津时时彩：http://app.jizhou56.com:8090/tjssc.php
    public static String Web_ShiShiCai_TJ = "tjssc.php";
    // 新疆时时彩：http://app.jizhou56.com:8090/xjssc.php
    public static String Web_ShiShiCai_XJ = "xjssc.php";
    // 云南时时彩：http://app.jizhou56.com:8090/ynssc.php
    public static String Web_ShiShiCai_YN = "ynssc.php";
    public static String Web_ShiShiCai_AW = "ah11x5.php"; // 安徽11选5
    public static String Web_ShiShiCai_BJ = "bj11x5.php";// 北京11选5
    public static String Web_ShiShiCai_FJ = "fj11x5.php";// 福建11选5
    public static String Web_ShiShiCai_GD = "gd11x5.php";// 广东11选5
    public static String Web_ShiShiCai_GS = "gs11x5.php";// 甘肃11选5
    public static String Web_ShiShiCai_GX = "gx11x5.php";// 广西11选5
    public static String Web_ShiShiCai_GZ = "gz11x5.php";// 贵州11选5
    public static String Web_ShiShiCai_HB = "heb11x5.php";// 河北11选5
    public static String Web_ShiShiCai_HN = "hen11x5.php";// 河南11选5
    public static String Web_ShiShiCai_HLJ11x5 = "hlj11x5.php";// 黑龙江11选5
    public static String Web_ShiShiCai_HuBbei = "hub11x5.php";// 湖北11选5
    public static String Web_ShiShiCai_JiLin = "jl11x5.php";// 吉林11选5
    public static String Web_ShiShiCai_JiangSu = "js11x5.php ";// 江苏11选5
    public static String Web_ShiShiCai_JiangXi = "jx11x5.php ";// 江西11选5
    public static String Web_ShiShiCai_LiaoLing = "ln11x5.php";// 辽宁11选5
    public static String Web_ShiShiCai_NeiMengGu = "nmg11x5.php";// 内蒙古11选5
    public static String Web_ShiShiCai_SiCuan = "sc11x5.php";// 四川11选5
    public static String Web_ShiShiCai_ShanDong = "sd11x5.php";// 山东11选5
    public static String Web_ShiShiCai_ShangHai = "sh11x5.php";// 上海11选5
    public static String Web_ShiShiCai_ShangXi = "sxl11x5.php";// 陕西11选5
    public static String Web_ShiShiCai_ShanXi = "sxr11x5.php";// 山西11选5
    public static String Web_ShiShiCai_TianJin = "tj11x5.php";// 天津11选5
    public static String Web_ShiShiCai_XinJiang = "xj11x5.php";// 新疆11选5
    public static String Web_ShiShiCai_YunNan = "yn11x5.php";// 云南11选5
    public static String Web_ShiShiCai_ZheJiang = "zj11x5.php";// 浙江11选5

    // 足球比分
    public static String ZQBF = "bifen.php";

    /**
     * 广告轮播：
     */
    //主页轮播：
    public static String AD_HOME = "appad/indexad/index.json";
    // 视频开奖轮播:
    public static String AD_SPKJ = "appad/spkjad/index.json";
    // 六合大全轮播:
    public static String AD_LHDQ = "appad/lhdqad/index.json";
    // 彩票专区轮播:
    public static String AD_CPZQ = "appad/cpzqad/index.json";
    // 足球比分轮播:
    public static String AD_ZQBF = "appad/zqbfad/index.json";
    // 六合寻宝通栏:
    public static String AD_LHXB = "appad/lhxb/index.json";
    // 百家乐轮播:
    public static String AD_BJL = "appad/bjlad/index.json";
    // 娱乐城通栏:
    public static String AD_YLC = "appad/ylcbanner/index.json";
    // 皇冠网通栏:
    public static String AD_HGW = "appad/hgwbanner/index.json";
    // 时时彩通栏:
    public static String AD_SSCTL = "appad/sscbanner/index.json";
    public static String[] AD={AD_HOME,AD_SPKJ,AD_LHDQ,AD_CPZQ,AD_ZQBF,AD_LHXB,AD_BJL,AD_YLC,AD_HGW,AD_SSCTL};


}
