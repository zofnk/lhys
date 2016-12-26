package com.lh16808.app.lhys.utils;



import com.lh16808.app.lhys.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 六合常用工具
 */
public class LiuheUtil {
    public static int[] redbo = {1, 2, 7, 8, 12, 13, 18, 19, 23, 24, 29, 30, 34, 35, 40, 45, 46};
    public static int[] bulebo = {3, 4, 9, 10, 14, 15, 20, 25, 26, 31, 36, 37, 41, 42, 47, 48};
    public static int[] greenbo = {5, 6, 11, 16, 17, 21, 22, 27, 28, 32, 33, 38, 39, 43, 44, 49};
    public static int[] rbg = {R.mipmap.lhc_ball_red, R.mipmap.lhc_ball_blue, R.mipmap.lhc_ball_green};
    public static String[] rbgs = {"紅", "藍", "綠"};
    public static String[] shengxiaos = new String[]{"鼠", "牛", "虎", "兔", "龍", "蛇", "馬", "羊", "猴", "雞", "狗", "豬"};
    public static String[] shengxiaos2 = new String[]{"龙", "马", "鸡", "猪"};
    public static int[] shengxiaos2L = new int[]{4, 6, 9, 11};
    public static String[] shengxiao_chongs = new String[]{"沖馬", "沖羊", "沖猴", "沖雞",
            "沖狗", "沖豬", "沖鼠", "沖牛", "沖虎", "沖兔", "沖龍", "沖蛇"};

    //获取波色球图
    public static int getRBG_mipmap(String str) {
        return rbg[isRBG(str)];
    }

    //获取波色字
    public static String getRBG_string(String str) {
        return rbgs[isRBG(str)];
    }

    //获取波色id
    public static int isRBG(String str) {
        int parseInt = Integer.parseInt(str);
        for (int i = 0; i < redbo.length; i++) {
            if (parseInt == redbo[i]) {
                return 0;
            }
        }
        for (int i = 0; i < bulebo.length; i++) {
            if (parseInt == bulebo[i]) {
                return 1;
            }
        }
        for (int i = 0; i < greenbo.length; i++) {
            if (parseInt == greenbo[i]) {
                return 2;
            }
        }
        return 0;
    }

    //获取生肖id兼容简繁体
    public static int getSXid(String sx) {
        for (int i = 0; i < shengxiaos.length; i++) {
            if (sx.equals(shengxiaos[i])) {
                return i;
            }
        }
        for (int i = 0; i < shengxiaos2.length; i++) {
            if (sx.equals(shengxiaos2[i])) {
                return shengxiaos2L[i];
            }
        }
        return -1;
    }

    //生肖转繁体
    public static String getSX_ft(String sx) {
        int sxid = getSXid(sx);
        if (sxid != -1 && sxid < shengxiaos.length) {
            return shengxiaos[sxid];
        }
        return sx;
    }

    //    六十甲子纳音表
//    甲子乙丑海中金 丙寅丁卯炉中火 戊辰己巳大林木 庚午辛未路旁土 壬申癸酉剑锋金
//    甲戌乙亥山头火 丙子丁丑涧下水 戊寅己卯城头土 庚辰辛巳白腊金 壬午癸未杨柳木
//    甲申乙酉泉中水 丙戌丁亥屋上土 戊子己丑劈雳火 庚寅辛卯松柏木 壬辰癸巳长流水
//    甲午乙未沙中金 丙申丁酉山下火 戊戌己亥平地木 庚子辛丑壁上土 壬寅癸卯金箔金
//    甲辰乙巳佛灯火 丙午丁未天河水 戊申己酉大驿土 庚戌辛亥插环金 壬子癸丑桑枝木
//    甲寅乙卯大溪水 丙辰丁巳沙中土 戊午己未天上火 庚申辛酉石榴木 壬戌癸亥大海水
    public static String[] nayinList = new String[]{
            "金", "金", "火", "火", "木", "木", "土", "土", "金", "金",
            "火", "火", "水", "水", "土", "土", "金", "金", "木", "木",
            "水", "水", "土", "土", "火", "火", "木", "木", "水", "水",
            "金", "金", "火", "火", "木", "木", "土", "土", "金", "金",
            "火", "火", "水", "水", "土", "土", "金", "金", "木", "木",
            "水", "水", "土", "土", "火", "火", "木", "木", "水", "水",};
    public static String[] wuxings = new String[]{"金", "木", "水", "火", "土",};
    public static String[] wuxingxiaos = new String[]{"猴、雞", "虎、兔", "鼠、豬", "蛇、馬", "牛、龍、羊、狗",};

    //获取生肖id兼容简繁体
    public static int getWXid(String wx) {
        for (int i = 0; i < wuxings.length; i++) {
            if (wx.equals(wuxings[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 号码转五行
     *
     * @param calendar 开奖时间
     * @param num      号码
     * @return
     */
    public static String getHaoma_WuXing(Calendar calendar, int num) {
        int nlyear = DateTimeUtil.solarToLunar(calendar)[0];//农历年
        return getHaoma_WuXing(nlyear, num);
    }
    /**
     * 号码转五行
     *
     * @param nlyear 开奖时间的农历年
     * @param num      号码
     * @return
     */
    public static String getHaoma_WuXing(int nlyear, int num) {
        int index = nlyear - 1922 - num - 1;
        return nayinList[(index % 60 + 60) % 60];
    }

    /**
     * 获取五行对应号码
     * @param calendar 当前时间
     * @return
     */
    public static List<List<String>> getWuXing_Haoma(Calendar calendar) {
        int nlyear = DateTimeUtil.solarToLunar(calendar)[0];//农历年
        return getWuXing_Haoma(nlyear);
    }

    public static List<List<String>> getWuXing_Haoma(int nlyear) {
        List<List<String>> lists = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add("农历" + nlyear + DateTimeUtil.sky_earth_Branch_year(nlyear) + "年");
        lists.add(list);
        for (int i = 0; i < wuxings.length; i++) {
            list = new ArrayList<>();
            list.add(wuxings[i]);
            list.add(wuxingxiaos[i]);
            lists.add(list);
        }
        for (int i = 1; i <= 49; i++) {
            String itemName = getHaoma_WuXing(nlyear, i);
            int con = 1;
            if (itemName.equals("金")) {
                con = 1;
            } else if (itemName.equals("木")) {
                con = 2;
            } else if (itemName.equals("水")) {
                con = 3;
            } else if (itemName.equals("火")) {
                con = 4;
            } else if (itemName.equals("土")) {
                con = 5;
            }
            lists.get(con).add(String.format("%02d", i));
        }
        return lists;
    }

    /**
     * 家禽野兽
     *
     * @param num 号码
     * @return
     */
    public static String getHaoma_JiaYe(Calendar calendar, int num) {
        String string = getHaoma_Shengxiao(calendar, num);
        return getHaoma_JiaYe(string);
    }

    public static String[] jiaye = new String[]{"家禽", "野獸"};

    //家禽:	牛、马、羊、鸡、狗、猪
    //野兽:	鼠、虎、兔、龙、蛇、猴
    public static String getHaoma_JiaYe(String string) {
        int sxid = getSXid(string);
        if (sxid == 1 || sxid == 6 || sxid == 7 ||
                sxid == 9 || sxid == 10 || sxid == 11) {
            return "家";
        }
        return "野";
    }


    /**
     * 生肖
     *
     * @param num 号码
     * @return
     */
    public static String getHaoma_Shengxiao(Calendar calendar, int num) {
        int nlyear = DateTimeUtil.solarToLunar(calendar)[0];//农历年
        int index = (nlyear - 4) - (num - 1);
        return shengxiaos[(index % 12 + 12) % 12];
    }

    public static List<List<String>> getShengxiaohaoma(Calendar calendar) {
        int nlyear = DateTimeUtil.solarToLunar(calendar)[0];//农历年
        return getShengxiaohaoma(nlyear);
    }

    public static List<List<String>> getShengxiaohaoma(int nlyear) {
        List<List<String>> lists = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add("农历" + nlyear + DateTimeUtil.sky_earth_Branch_year(nlyear) + "年");
        lists.add(list);
        for (int i = 0; i < shengxiaos.length; i++) {
            list = new ArrayList<>();
            list.add(shengxiaos[i]);
            list.add(shengxiao_chongs[i]);
            lists.add(list);
        }
        int con = (nlyear - 4) % 12 + 1;
        for (int i = 1; i <= 49; i++) {
            lists.get(con).add(String.format("%02d", i));
            con--;
            if (con < 1) {
                con = 12;
            }
        }
        return lists;
    }
}

