package com.lh16808.app.lhys.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 常量数据
 *
 * @author Administrator
 */
public class ConstantsUtil {
    public static Boolean IS_LOG = true; // 是否开启Log打印
    /**
     * 网络参数
     */
    public static String JSONURL = getBaseUrl(U.U0, "e/extend/json/json.php");
    //开奖数据正式动态接口
//    public static String KjDataUrl = JSONURL + "?enews=IosKj";
    //开奖数据正式静态接口
    public static String KjDataUrl = getBaseUrl(U.U4_app, "kjbm.json");
    //开奖数据测试接口
//    public static final String KjDataUrl = "http://120.76.44.201:8090/ceshi.json";
    //    开奖测试修改,http://120.76.44.201:8090/ceshi.html
//    // 下一期开奖时间
//    public static String JSONURL_Xyqkjsj = JSONURL + "?enews=Kjtime";


    //设置：二维码、app更新
    //{"qrcode":"二维码图片"，"qrcode1":"暂时弃用"，"qrurl":"android下载地址"，"version":"当前版本号"，
    // "vo":"版本数"，"iosvo":"ios版本数"，"ipaurl":"ios下载地址"，"iosfeature":"ios更新内容"，
    // "feature":"android更新内容"，"sharetext":"分享内容"，"sharelink":"分享链接"，
    // "filelen":文件大小，"ioson":是否开启更多应用}
    public static String JSONURL_SZ = getBaseUrl(U.U0, "/e/extend/json/sz.php?enews=6hfx");
    // 删除收藏
    public static String DeleteSC = getBaseUrl(U.U0, "e/member/doaction.php");
    // 论坛贴子发布、论坛贴子回复
    public static String FatieURL = getBaseUrl(U.U0, "e/DoInfo/ecms.php");
    /**
     * wap
     * 意见反馈
     */
    public static String FeedBackURL = getBaseUrl(U.U0, "e/enews/index.php");
    // 登陸、註冊
    public static String LOGINURL = getBaseUrl(U.U0, "e/member/doaction.php");
    // 更多應用
    public static String DownloadAPP = getBaseUrl(U.U0, "json/moreapp.json");

    /**
     * wap
     * 历史数据
     * json/年份.json
     */
    public static String HISTORY_RECORD(String year) {
        return getBaseUrl(U.U0, "json/" + year + ".json");
    }


    // 走势分析web：
    public static String KaiJianFXURL = getBaseUrl(U.U0, "e/extend/trend/");
    // 开奖日期web：
    public static String KaiJianRQURL = getBaseUrl(U.U0, "appdate.php");
    // 足球比分web：
    public static String ZQBFURL = getBaseUrl(U.U0, "bifen.php");
    // 百家乐web：
    public static String BJLURL = getBaseUrl(U.U0, "baijiale.html");

    /**
     * 彩票专区：web：
     */
    // 大乐透：http://app.jizhou56.com:8090/daletou.php
    public static String Web_DaLeTou = getBaseUrl(U.U0, "daletou.php");
    // 福彩3D：http://app.jizhou56.com:8090/3d.php
    public static String Web_FuCai3D = getBaseUrl(U.U0, "3d.php");
    // 排列3：http://app.jizhou56.com:8090/pl3.php
    public static String Web_PaiLie3 = getBaseUrl(U.U0, "pl3.php");
    // 排列5：http://app.jizhou56.com:8090/pl5.php
    public static String Web_PaiLie5 = getBaseUrl(U.U0, "pl5.php");
    // 七乐彩：http://app.jizhou56.com:8090/qlc.php
    public static String Web_QiLeCai = getBaseUrl(U.U0, "qlc.php");
    // 七星彩：http://app.jizhou56.com:8090/qxc.php
    public static String Web_QiXingCai = getBaseUrl(U.U0, "qxc.php");
    // 双色球：http://app.jizhou56.com:8090/ssq.php
    public static String Web_ShuangSeQiu = getBaseUrl(U.U0, "ssq.php");
    // 体彩36选7：http://app.jizhou56.com:8090/fjtc36x7.php
    public static String Web_TiCai37X7 = getBaseUrl(U.U0, "fjtc36x7.php");
    // 重庆时时彩：http://app.jizhou56.com:8090/cqssc.php
    public static String Web_ShiShiCai_CQ = getBaseUrl(U.U0, "cqssc.php");
    // 黑龙江时时彩：http://app.jizhou56.com:8090/hljssc.php
    public static String Web_ShiShiCai_HLJ = getBaseUrl(U.U0, "hljssc.php");
    // 吉林时时彩：http://app.jizhou56.com:8090/jlssc.php
    public static String Web_ShiShiCai_JL = getBaseUrl(U.U0, "jlssc.php");
    // 天津时时彩：http://app.jizhou56.com:8090/tjssc.php
    public static String Web_ShiShiCai_TJ = getBaseUrl(U.U0, "tjssc.php");
    // 新疆时时彩：http://app.jizhou56.com:8090/xjssc.php
    public static String Web_ShiShiCai_XJ = getBaseUrl(U.U0, "xjssc.php");
    // 云南时时彩：http://app.jizhou56.com:8090/ynssc.php
    public static String Web_ShiShiCai_YN = getBaseUrl(U.U0, "ynssc.php");
    public static String Web_ShiShiCai_AW = getBaseUrl(U.U0, "ah11x5.php"); // 安徽11选5
    public static String Web_ShiShiCai_BJ = getBaseUrl(U.U0, "bj11x5.php");// 北京11选5
    public static String Web_ShiShiCai_FJ = getBaseUrl(U.U0, "fj11x5.php");// 福建11选5
    public static String Web_ShiShiCai_GD = getBaseUrl(U.U0, "gd11x5.php");// 广东11选5
    public static String Web_ShiShiCai_GS = getBaseUrl(U.U0, "gs11x5.php");// 甘肃11选5
    public static String Web_ShiShiCai_GX = getBaseUrl(U.U0, "gx11x5.php");// 广西11选5
    public static String Web_ShiShiCai_GZ = getBaseUrl(U.U0, "gz11x5.php");// 贵州11选5
    public static String Web_ShiShiCai_HB = getBaseUrl(U.U0, "heb11x5.php");// 河北11选5
    public static String Web_ShiShiCai_HN = getBaseUrl(U.U0, "hen11x5.php");// 河南11选5
    public static String Web_ShiShiCai_HLJ11x5 = getBaseUrl(U.U0, "hlj11x5.php");// 黑龙江11选5
    public static String Web_ShiShiCai_HuBbei = getBaseUrl(U.U0, "hub11x5.php");// 湖北11选5
    public static String Web_ShiShiCai_JiLin = getBaseUrl(U.U0, "jl11x5.php");// 吉林11选5
    public static String Web_ShiShiCai_JiangSu = getBaseUrl(U.U0, "js11x5.php");// 江苏11选5
    public static String Web_ShiShiCai_JiangXi = getBaseUrl(U.U0, "jx11x5.php");// 江西11选5
    public static String Web_ShiShiCai_LiaoLing = getBaseUrl(U.U0, "ln11x5.php");// 辽宁11选5
    public static String Web_ShiShiCai_NeiMengGu = getBaseUrl(U.U0, "nmg11x5.php");// 内蒙古11选5
    public static String Web_ShiShiCai_SiCuan = getBaseUrl(U.U0, "sc11x5.php");// 四川11选5
    public static String Web_ShiShiCai_ShanDong = getBaseUrl(U.U0, "sd11x5.php");// 山东11选5
    public static String Web_ShiShiCai_ShangHai = getBaseUrl(U.U0, "sh11x5.php");// 上海11选5
    public static String Web_ShiShiCai_ShangXi = getBaseUrl(U.U0, "sxl11x5.php");// 陕西11选5
    public static String Web_ShiShiCai_ShanXi = getBaseUrl(U.U0, "sxr11x5.php");// 山西11选5
    public static String Web_ShiShiCai_TianJin = getBaseUrl(U.U0, "tj11x5.php");// 天津11选5
    public static String Web_ShiShiCai_XinJiang = getBaseUrl(U.U0, "xj11x5.php");// 新疆11选5
    public static String Web_ShiShiCai_YunNan = getBaseUrl(U.U0, "yn11x5.php");// 云南11选5
    public static String Web_ShiShiCai_ZheJiang = getBaseUrl(U.U0, "zj11x5.php");// 浙江11选5


    /**
     * 静态资料
     */
    //      论坛
    public static String JT_BBS = getBaseUrl(U.U0, "json/list/bbs.json");
    //      心水资料
    public static String JT_XINSHUI = getBaseUrl(U.U0, "json/list/xinshui.json");
    //    文字资料
    public static String JT_WENZI = getBaseUrl(U.U0, "json/list/wenzi.json");
    //     高手资料
    public static String JT_GAOSHUO = getBaseUrl(U.U0, "json/list/gaoshuo.json");
    //     公式资料
    public static String JT_GONGSHI = getBaseUrl(U.U0, "json/list/gongshi.json");
    //     精品杀项
    public static String JT_SHAXIANG = getBaseUrl(U.U0, "json/list/shaxiang.json");
    //      香港挂牌
    public static String JT_XIANGGANG = getBaseUrl(U.U0, "json/list/xianggang.json");
    //     全年资料
    public static String JT_QUANNIAN = getBaseUrl(U.U0, "json/list/quannian.json");
    //      六合属性
    public static String JT_SHUXING = getBaseUrl(U.U0, "json/list/shuxing.json");
    //    彩色图库：
    public static String JT_photocaise = getBaseUrl(U.U0, "json/list/photocaise.json");
    //    玄机图库：
    public static String JT_photoxianji = getBaseUrl(U.U0, "json/list/photoxianji.json");
    //    黑白图库：
    public static String JT_photoheibai = getBaseUrl(U.U0, "json/list/photoheibai.json");
    //    全年图库：
    public static String JT_photoqianlian = getBaseUrl(U.U0, "json/list/photoqianlian.json");
    //    玄机资料（白小姐）：
    public static String JT_xianjibxj = getBaseUrl(U.U0, "json/xianji/bxj.json");
    //    玄机资料（曾道人）：
    public static String JT_xianjizdr = getBaseUrl(U.U0, "json/xianji/zdr.json");
    /**
     * 玄机
     * 曾道人
     * 白小姐
     * 动态
     */
    public static final String XianJi = getBaseUrl(U.U0, "e/extend/json/xjjson.php");
    // 内部资料web
    public static String KJURL = getBaseUrl(U.U2_ad, "kaijiang.php");

    /**
     * 广告轮播：
     */
    //主页轮播：
    public static String AD_HOME = getBaseUrl(U.U2_ad, "appad/lhfxad/index.json");
    // 视频开奖轮播:
    public static String AD_SPKJ = getBaseUrl(U.U2_ad, "appad/lhfxad/index.json");
    // 历史记录轮播
    public static String AD_LSJL = getBaseUrl(U.U2_ad, "appad/lhfxad/index.json");
    // 论坛轮播:
    public static String AD_LT = getBaseUrl(U.U2_ad, "appad/lhfxad/index.json");
    // 走势分析轮播:
    public static String AD_ZSFX = getBaseUrl(U.U2_ad, "appad/lhfxad/index.json");
    // 六合屬性轮播:
    public static String AD_LHSX = getBaseUrl(U.U2_ad, "appad/lhfxad/index.json");
    // 攻略大全轮播:
    public static String AD_GLDQ = getBaseUrl(U.U2_ad, "appad/lhfxad/index.json");
    // 玄机轮播:
    public static String AD_XJ = getBaseUrl(U.U2_ad, "appad/lhfxad/index.json");
    // 开奖日期轮播:
    public static String AD_KJRQ = getBaseUrl(U.U2_ad, "appad/lhfxad/index.json");
    // 资料轮播:
    public static String AD_ZL = getBaseUrl(U.U2_ad, "appad/lhfxad/index.json");
    // 图库轮播:
    public static String AD_TK = getBaseUrl(U.U2_ad, "appad/lhfxad/index.json");
    // 彩票專區轮播:
    public static String AD_CPZQ = getBaseUrl(U.U2_ad, "appad/lhfxad/index.json");
    // 六合寻宝轮播:
    public static String AD_LHXB = getBaseUrl(U.U2_ad, "appad/lhfxad/index.json");
    // 六合寻宝栏目:
    public static String AD_LHXB2 = getBaseUrl(U.U2_ad, "appad/lhfx_xb/index.json");

    /**
     * 非网络参数
     */
    //加载失败web提示
    public static String errorHtml = "<html>\n" +
            "<head>\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
            "</head>\n" +
            "<body>\n" +
            "<style>body{background:#d8b87b}\n" +
            "h1{margin:50px auto;text-align:center}\n" +
            "</style>\n" +
            "<h1 style=\"text-align:center;\">\n" +
            "<span style=\"color:#000000;\">加載失敗！\n" +
            "</span>\n" +
            "</h1>\n" +
            "<h1 style=\"text-align:center;\"><span style=\"color:#000000;\">\n" +
            "<span style=\"color:#E53333;\">下拉頁面</span>重新加載</span>\n" +
            " </h1>\n" +
            "</body>\n" +
            "</html>";

    // 配置文件
    public static String SHARED_PREFERENCE = "SharedPreferences";
    // 开奖中属性
    public static String STR_M = "00";
    public static String URL_NAME = "enews";

    /**
     * 用户ID
     */
    public static String SHARE_USER_ID = "share_user_id";// String 用户id
    /**
     * 用户密码
     */
    public static String SHARE_PWD = "share_pwd";// String 密码
    /**
     * 用户头像
     */
    public static String SHARE_TX = "share_tx";
    // web Key
    public static String WebInKey = "Url";
    public static String WebInTiTle = "title";
    // Menu跳转标识
    public static String Set_PutValue1 = "關於六合分析";
    public static String Set_PutValue2 = "免責聲明";

    public static String AdKey = "value";
    //獲取我的收藏
    public static String MyCollect_Value = "Myfava";
    //    添加收藏：
    public static String MyCollect_AddFava = "AddFava";
    //  批量删除收藏：
    public static String MyCollect_DelFava_All = "DelFava_All";


    //獲取我的帖子
    public static String MyBBs_Value = "Mybbs";
    public static String MyRep_Value = "Myrep";
    public static String MyBBs_Value1 = "Mybbs1";
    public static String MyRep_Value1 = "Myrep1";
    public static String MyUID = "uid";
    public static String MyCollect_title = "我的收藏";
    public static String MyBBs_title = "我的貼子";
    public static String MyRep_title = "我的回覆";

    //升级下载名称
    public static String DownloadAPPName = "六合分析.apk";

    //能轉盤
    public static final String CAN_LUCK_PAN = "can_luakpan";
    //轉盤的結果
    public static final String LUCK_PAN_NUM = "luck_pan_num";

    //能翻牌
    public static final String CAN_TURN_CARDS = "can_turn_cards";
    //翻牌的結果
    public static final String TURN_CARDS_NUM = "turn_cards_num";

    //能搖一搖
    public static final String CAN_SHAKE = "can_shake";
    //搖一搖的結果
    public static final String SHAKE_NUM = "shake_num";

    // 开奖时间
    public static final String LOTTERY_TIME = "lottery_time";

    public static final String SP_NAME = "igene";

    // 极光推送Tabable
    public static String TAB_Title = "title";
    public static String TAB_Alert = "alert";
    public static String TAB_Time = "time";
    public static String TAB_NotifactionId = "notifactionId";
    public static String TAB_IsClick = "isclick";
    public static String TabName = "newslist";
    //极光推送开关设置
    public static String IsJPush = "IsJPush";

    public static String sendB = "XXXXyear";
    /**
     * 声音开关设置
     */
    public static String IsPlay = "IsPlay";

    //分享默认链接、文字、图片
    public static String share_url = "http://www.5899488.com/";
    public static String share_text = "六合分析是壹款集視頻開獎直播、數據分析、資料分享的六合手機應用";
    //
    public static String[] classid_titles = {"論壇帖子", "彩色圖庫", "玄機彩圖", "黑白圖庫", "全年圖庫",
            "心水資料", "文字資料", "高手資料", "公式資料", "精品殺項", "香港掛牌", "全年資料", "六合屬性",
            "曾道人", "白小姐"};
    public static String[] classids = {"1", "66", "89", "92", "65",
            "10", "11", "7", "12", "13", "67", "14", "83",
            "93", "94"};
    //静态资料
    public static String[] classid_URLs = {JT_BBS, JT_photocaise, JT_photoxianji, JT_photoheibai, JT_photoqianlian,
            JT_XINSHUI, JT_WENZI, JT_GAOSHUO, JT_GONGSHI, JT_SHAXIANG, JT_XIANGGANG, JT_QUANNIAN, JT_SHUXING,
            JT_xianjizdr, JT_xianjibxj};


    public static String classid_urls(String classid) {
        String string = "未知鏈接";
        for (int i = 0; i < classids.length; i++) {
            if (classid.equals(classids[i])) {
                if (i < classid_URLs.length) {
                    string = classid_URLs[i];
                }
                break;
            }
        }
        return string;
    }

    public static String classid_titles(String classid) {
        String string = "未知標題";
        for (int i = 0; i < classids.length; i++) {
            if (classid.equals(classids[i])) {
                if (i < classid_titles.length) {
                    string = classid_titles[i];
                }
                break;
            }
        }
        return string;
    }

    /**
     * 获取完整地址
     *
     * @param curl 主地址
     * @param file 需要拼接的相对地址
     * @return 返回完整链接
     */
    public static String getBaseUrl(String curl, String file) {
        String q = "";
        try {
            URL url = new URL(new URL(curl), file);
            q = url.toExternalForm();
        } catch (MalformedURLException e) {

        }
        if (q.indexOf("#") != -1) {
            q = q.replaceAll("^(.+?)#.*?$", "$1");
        }
        return q;
    }

    public static String joinUrl(String curl, String file) {
        URL url = null;
        String q = "";
        try {
            url = new URL(new URL(curl), file);
            q = url.toExternalForm();
        } catch (MalformedURLException e) {

        }
        if (q.indexOf("#") != -1) {
            q = q.replaceAll("^(.+?)#.*?$", "$1");
        }
        return q;
    }

    //替换六合管家为六合分析
    public static String replaceAll(String response) {
        String string = response.replaceAll("\\\\u516d\\\\u5408\\\\u7ba1\\\\u5bb6",
                "\\\\u516d\\\\u5408\\\\u5206\\\\u6790");
        return string;
    }


}
