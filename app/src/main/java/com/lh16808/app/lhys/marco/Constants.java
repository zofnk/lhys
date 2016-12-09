package com.lh16808.app.lhys.marco;


import com.lh16808.app.lhys.R;

public interface Constants {

    public static String MyCollect_title = "我的收藏";
    public static String MyBBs_title = "我的貼子";
    public static String MyRep_title = "我的回覆";
    String DownloadAPPName = "spkj.apk";
    // 配置文件
    String SHARED_PREFERENCE = "SharedPreferences";
    int[] music_sum = {R.raw.one_sum, R.raw.two_sum, R.raw.three_sum, R.raw.four_sum, R.raw.five_sum, R.raw.six_sum,
            R.raw.tm_sum};
    int[] music = {R.raw.one, R.raw.a1, R.raw.a2, R.raw.a3, R.raw.a4, R.raw.a5, R.raw.a6, R.raw.a7, R.raw.a8, R.raw.a9,
            R.raw.a10, R.raw.a11, R.raw.a12, R.raw.a13, R.raw.a14, R.raw.a15, R.raw.a16, R.raw.a17, R.raw.a18,
            R.raw.a19, R.raw.a20, R.raw.a21, R.raw.a22, R.raw.a23, R.raw.a24, R.raw.a25, R.raw.a26, R.raw.a27,
            R.raw.a28, R.raw.a29, R.raw.a30, R.raw.a31, R.raw.a32, R.raw.a33, R.raw.a34, R.raw.a35, R.raw.a36,
            R.raw.a37, R.raw.a38, R.raw.a39, R.raw.a40, R.raw.a41, R.raw.a42, R.raw.a43, R.raw.a44, R.raw.a45,
            R.raw.a46, R.raw.a47, R.raw.a48, R.raw.a49};
    int[] redbo = {1, 2, 7, 8, 12, 13, 18, 19, 23, 24, 29, 30, 34, 35, 40, 45, 46};
    int[] bulebo = {3, 4, 9, 10, 14, 15, 20, 25, 26, 31, 36, 37, 41, 42, 47, 48};
    int[] greenbo = {5, 6, 11, 16, 17, 21, 22, 27, 28, 32, 33, 38, 39, 43, 44, 49};
    int[] rbg = {R.drawable.ball_red_tool_lover, R.drawable.ball_blue_tool_lover, R.drawable.ball_green_tool_lover};
    int[] musicsx = {R.raw.shu, R.raw.niu, R.raw.hu, R.raw.tu, R.raw._long, R.raw.she, R.raw.ma, R.raw.yang, R.raw.hou,
            R.raw.ji, R.raw.gou, R.raw.zhu};
    String[] sxstr = new String[]{"鼠", "牛", "虎", "兔", "龍", "蛇", "馬", "羊", "猴", "雞", "狗", "豬"};
    String[] Sxstr2 = new String[]{"龙", "马", "鸡", "猪"};
    int[] Sxstr2L = new int[]{4, 6, 9, 11};
    int[] sa = {R.drawable.kj_sa1, R.drawable.kj_sa2, R.drawable.kj_sa3, R.drawable.kj_sa4, R.drawable.kj_sa5, R.drawable.kj_sa6,
            R.drawable.kj_sa7, R.drawable.kj_sa8, R.drawable.kj_sa9, R.drawable.kj_sa10, R.drawable.kj_sa11, R.drawable.kj_sa12,
            R.drawable.kj_sa13, R.drawable.kj_sa14, R.drawable.kj_sa15, R.drawable.kj_sa16, R.drawable.kj_sa17, R.drawable.kj_sa18,
            R.drawable.kj_sa19, R.drawable.kj_sa20, R.drawable.kj_sa21, R.drawable.kj_sa22, R.drawable.kj_sa23, R.drawable.kj_sa24,
            R.drawable.kj_sa25};
    int[] ca = {R.drawable.kj_ca1, R.drawable.kj_ca2, R.drawable.kj_ca3, R.drawable.kj_ca4, R.drawable.kj_ca5, R.drawable.kj_ca6,
            R.drawable.kj_ca7, R.drawable.kj_ca8, R.drawable.kj_ca9, R.drawable.kj_ca10, R.drawable.kj_ca11, R.drawable.kj_ca12,
            R.drawable.kj_ca13, R.drawable.kj_ca14, R.drawable.kj_ca15, R.drawable.kj_ca16, R.drawable.kj_ca17, R.drawable.kj_ca18,
            R.drawable.kj_ca19, R.drawable.kj_ca20, R.drawable.kj_ca21, R.drawable.kj_ca22, R.drawable.kj_ca23, R.drawable.kj_ca24,
            R.drawable.kj_ca25, R.drawable.kj_ca26, R.drawable.kj_ca27};
    int[] ed = {R.drawable.kj_ed1, R.drawable.kj_ed2, R.drawable.kj_ed3, R.drawable.kj_ed4, R.drawable.kj_ed5, R.drawable.kj_ed6,
            R.drawable.kj_ed7, R.drawable.kj_ed8, R.drawable.kj_ed9, R.drawable.kj_ed10, R.drawable.kj_ed11, R.drawable.kj_ed12,
            R.drawable.kj_ed13, R.drawable.kj_ed14, R.drawable.kj_ed15, R.drawable.kj_ed16, R.drawable.kj_ed17, R.drawable.kj_ed18,
            R.drawable.kj_ed19, R.drawable.kj_ed20, R.drawable.kj_ed21, R.drawable.kj_ed22, R.drawable.kj_ed23, R.drawable.kj_ed24,
            R.drawable.kj_ed25};
    String sendB = "XXXXyear";
    String IsPlay = "IsPlay";
    String URL_KEY = "enews";

    int[] SXCard = {R.drawable.ico_card_chicken, R.drawable.ico_card_cow, R.drawable.ico_card_dog,
            R.drawable.ico_card_dragon, R.drawable.ico_card_horse, R.drawable.ico_card_monkey,
            R.drawable.ico_card_mouse, R.drawable.ico_card_pig, R.drawable.ico_card_rabbit,
            R.drawable.ico_card_sheep, R.drawable.ico_card_snake, R.drawable.ico_card_tiger};
    //能轉盤
    public static final String CAN_LUCK_PAN = "can_luakpan";
    //轉盤的結果
    public static final String LUCK_PAN_RESULT = "luck_pan_result";
    // 开奖时间
    public static final String LOTTERY_TIME = "lottery_time";

    // 主页轮播：
    String AD_HOME = "http://ad.jizhou56.com:8090/appad/indexad/index.json";
    //能翻牌
    public static final String CAN_TURN_CARDS = "can_turn_cards";
    //翻牌的結果
    public static final String TURN_CARDS_RESULT = "turn_cards_result";

    //能搖一搖
    public static final String CAN_SHAKE = "can_shake";
    //搖一搖的結果
    public static final String SHAKE_RESULT = "shake_result";

    String[] TITLES = {"心水资料", "文字资料", "高手资料", "公式资料", "精品杀项", "香港挂牌", "全年资料"};//, "六合属性"
    String[] classid = {"10", "11", "7", "12", "13", "67", "14", "83"};

    String[] classid_TUKU = {"66", "89", "92", "65"};
    String[] TITLES_TUKU = {"彩色图库", "玄机彩图", "黑白图库", "全年图库"};

    String[] caipiaotitle = {"重慶時時彩", "黑龍江時時彩", "吉林時時彩", "天津時時彩", "新疆時時彩", "雲南時時彩", "大樂透", "福彩3D", "排列3", "排列5",
            "七樂彩", "七星彩", "雙色球", "體彩36選7", "安徽11選5", "北京11選5", "福建11選5", "廣東11選5", "甘肅11選5", "廣西11選5", "貴州11選5",
            "河北11選5", "河南11選5", "黑龍江11選5", "湖北11選5", "吉林11選5", "江蘇11選5", "江西11選5", "遼寧11選5", "內蒙古11選5", "四川11選5",
            "山東11選5", "上海11選5", "陜西11選5", "山西11選5", "天津11選5", "新疆11選5", "雲南11選5", "浙江11選5"};
    String[] caipiaoUrl = {ApiConfig.Web_ShiShiCai_CQ, ApiConfig.Web_ShiShiCai_HLJ, ApiConfig.Web_ShiShiCai_JL,
            ApiConfig.Web_ShiShiCai_TJ, ApiConfig.Web_ShiShiCai_XJ, ApiConfig.Web_ShiShiCai_YN, ApiConfig.Web_DaLeTou,
            ApiConfig.Web_FuCai3D, ApiConfig.Web_PaiLie3, ApiConfig.Web_PaiLie5, ApiConfig.Web_QiLeCai,
            ApiConfig.Web_QiXingCai, ApiConfig.Web_ShuangSeQiu, ApiConfig.Web_TiCai37X7, ApiConfig.Web_ShiShiCai_AW,
            ApiConfig.Web_ShiShiCai_BJ, ApiConfig.Web_ShiShiCai_FJ, ApiConfig.Web_ShiShiCai_GD,
            ApiConfig.Web_ShiShiCai_GS, ApiConfig.Web_ShiShiCai_GX, ApiConfig.Web_ShiShiCai_GZ,
            ApiConfig.Web_ShiShiCai_HB, ApiConfig.Web_ShiShiCai_HN, ApiConfig.Web_ShiShiCai_HLJ11x5,
            ApiConfig.Web_ShiShiCai_HuBbei, ApiConfig.Web_ShiShiCai_JiLin, ApiConfig.Web_ShiShiCai_JiangSu,
            ApiConfig.Web_ShiShiCai_JiangXi, ApiConfig.Web_ShiShiCai_LiaoLing, ApiConfig.Web_ShiShiCai_NeiMengGu,
            ApiConfig.Web_ShiShiCai_SiCuan, ApiConfig.Web_ShiShiCai_ShanDong, ApiConfig.Web_ShiShiCai_ShangHai,
            ApiConfig.Web_ShiShiCai_ShangXi, ApiConfig.Web_ShiShiCai_ShanXi, ApiConfig.Web_ShiShiCai_TianJin,
            ApiConfig.Web_ShiShiCai_XinJiang, ApiConfig.Web_ShiShiCai_YunNan, ApiConfig.Web_ShiShiCai_ZheJiang};

    /**
     * 非网络参数
     */
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

}
