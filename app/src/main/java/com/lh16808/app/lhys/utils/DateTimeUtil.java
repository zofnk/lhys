package com.lh16808.app.lhys.utils;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 公历农历转换，日期操作
 */
public class DateTimeUtil {
    // 今年
    public static int GetYear() {
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        return y;
    }

    //1976-今年
    public static String[] getYear() {
        int i = GetYear();
        int i2 = 1975;
        String[] provinceList = new String[i - i2];
        for (int j = 0; j < i - i2; j++) {
            provinceList[j] = (i - j + "");
        }
        return provinceList;
    }

    static String[] chineseMonth = {"壹月大", "二月小", "三月大", "四月小", "五月大", "六月小", "七月大",
            "八月大", "九月小", "十月大", "十壹月小", "十二月大"};
    static String[] englishMonth = {"January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December"};
    static String[] englishMonth2 = {"Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun.", "Jul.", "Aug.",
            "Sep.", "Oct.", "Nov.", "Dec."};

    /**
     * 获取中英文月
     * int   mMonth = cal.get(Calendar.MONTH) + 1;// 获取月份
     *
     * @param cal
     * @return
     */
    public static List<String> getMonth(Calendar cal) {
        List<String> list = new ArrayList<>();
        int mMonth = cal.get(Calendar.MONTH);
        String string = chineseMonth[mMonth];
        String string2 = englishMonth[mMonth];
        list.add(string);
        list.add(string2);
        return list;
    }

    static String[] chineseWeek = {"星期壹", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
    static String[] englishWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    /**
     * 获取星期几
     * int   mWay =cal.get(Calendar.DAY_OF_WEEK);// 获取日期号码星期
     *
     * @param cal
     * @return
     */
    public static List<String> getWeek(Calendar cal) {
        List<String> list = new ArrayList<>();
        int mWay = cal.get(Calendar.DAY_OF_WEEK);// 获取日期号码的星期几
        String string = "";
        String string2 = "";
        switch (mWay) {
            case 1:
                string = chineseWeek[6];
                string2 = englishWeek[6];
                break;
            case 2:
                string = chineseWeek[0];
                string2 = englishWeek[0];
                break;
            case 3:
                string = chineseWeek[1];
                string2 = englishWeek[1];
                break;
            case 4:
                string = chineseWeek[2];
                string2 = englishWeek[2];
                break;
            case 5:
                string = chineseWeek[3];
                string2 = englishWeek[3];
                break;
            case 6:
                string = chineseWeek[4];
                string2 = englishWeek[4];
                break;
            case 7:
                string = chineseWeek[5];
                string2 = englishWeek[5];
                break;
        }
        list.add(string);
        list.add(string2);
        return list;
    }

    //天干
    public static final String[] skyBranch = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    //地支
    public static final String[] earthBranch = new String[]{"子", "醜", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};

    /**
     * 干支纪年
     *
     * @param year 农历的年
     * @return
     */
    public static String sky_earth_Branch_year(int year) {
        if (year < 4) {
            return "不能小於4年";
        }
        int realYear = year - 4;
//        System.out.println("year" + year + " =[" + skyBranch[realYear % 10] + "][" + earthBranch[realYear % 12] + "]");
        return skyBranch[realYear % 10] + earthBranch[realYear % 12];
    }

    /**
     * 干支纪日
     * 公元纪日换算成干支纪日公式：
     * // G = 4C + [C / 4] + 5y + [y / 4] + [3 * (M + 1) / 5] + d - 3
     * // Z = 8C + [C / 4] + 5y + [y / 4] + [3 * (M + 1) / 5] + d + 7 + i
     * //其中 C 是世纪数减一，y 是年份后两位（若为1月、2月则当前年份减一），
     * // M 是月份（若为1月、2月则分别按13、14来计算），d 是日数。奇数月i=0，偶数月i=6。
     * // 计算时带[ ]的数表示舍去小数点后的数字取整。
     *
     * @param year
     * @param month 1-12
     * @param day
     * @return
     */
    public static String sky_earth_Branch_day(int year, int month, int day) {
        int C = year / 100;
        int y = year % 100;
        int M = month;
        if (M == 1 || M == 2) {
            M = month + 12;
            y = y - 1;
        }
        int d = day;
        int i = 0;
        if (M % 2 == 0) {
            i = 6;
        }
        int G = 4 * C + (int) (C / 4) + 5 * y + (int) (y / 4) + (int) (3 * (M + 1) / 5) + d - 3;
        int Z = 8 * C + (int) (C / 4) + 5 * y + (int) (y / 4) + (int) (3 * (M + 1) / 5) + d + 7 + i;
//        System.out.println("C" + C + "y" + y + "m" + M + "d" + d + "i" + i + "G" + G + "Z" + Z + "GG" + (int) (G % 10) + "ZZ" + (int) (Z % 12));
//        System.out.println("year" + year + month + day + " =[" + skyBranch[(G - 1) % 10] + "][" + earthBranch[(Z - 1) % 12] + "]");
        return skyBranch[(G - 1) % 10] + earthBranch[(Z - 1) % 12];
    }


    //农历月份
    static String[] nlMonth = {"正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十壹", "十二"};
    //农历日
    static String[] nlday = {"初壹", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十",
            "十壹", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十",
            "廿壹", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十"};

    /**
     * 农历月日
     *
     * @param nl_month
     * @param nl_day
     * @return
     */
    public static List<String> getNongliMonth(int nl_year, int nl_month, int nl_day, int nl_leap, int nl_dx) {
        List<String> list = new ArrayList<>();
        String string1 = sky_earth_Branch_year(nl_year) + "年";
        String string2 = (nl_leap == 0 ? "" : "閏") + nlMonth[nl_month - 1] + "月" + (nl_dx == 0 ? "小" : "大");
        String string3 = nlday[nl_day - 1];
        list.add(string1);
        list.add(string2);
        list.add(string3);
        return list;
    }

    /**
     * 支持转换的最小农历年份
     */
    public static final int MIN_YEAR = 1900;
    /**
     * 支持转换的最大农历年份
     */
    public static final int MAX_YEAR = 2099;

    /**
     * 用来表示1900年到2099年间农历年份的相关信息，共24位bit的16进制表示，其中：
     * 1. 前4位表示该年闰哪个月；
     * 2. 5-17位表示农历年份13个月的大小月分布，0表示小，1表示大；
     * 3. 最后7位表示农历年首（正月初一）对应的公历日期。
     * <p/>
     * 以2014年的数据0x955ABF为例说明：
     * 1001 0101 0101 1010 1011 1111
     * 闰九月  农历正月初一对应公历1月31号
     */
    private static final int LUNAR_INFO[] = {
            0x84B6BF,/*1900*/
            0x04AE53, 0x0A5748, 0x5526BD, 0x0D2650, 0x0D9544, 0x46AAB9, 0x056A4D, 0x09AD42, 0x24AEB6, 0x04AE4A,/*1901-1910*/
            0x6A4DBE, 0x0A4D52, 0x0D2546, 0x5D52BA, 0x0B544E, 0x0D6A43, 0x296D37, 0x095B4B, 0x749BC1, 0x049754,/*1911-1920*/
            0x0A4B48, 0x5B25BC, 0x06A550, 0x06D445, 0x4ADAB8, 0x02B64D, 0x095742, 0x2497B7, 0x04974A, 0x664B3E,/*1921-1930*/
            0x0D4A51, 0x0EA546, 0x56D4BA, 0x05AD4E, 0x02B644, 0x393738, 0x092E4B, 0x7C96BF, 0x0C9553, 0x0D4A48,/*1931-1940*/
            0x6DA53B, 0x0B554F, 0x056A45, 0x4AADB9, 0x025D4D, 0x092D42, 0x2C95B6, 0x0A954A, 0x7B4ABD, 0x06CA51,/*1941-1950*/
            0x0B5546, 0x555ABB, 0x04DA4E, 0x0A5B43, 0x352BB8, 0x052B4C, 0x8A953F, 0x0E9552, 0x06AA48, 0x6AD53C,/*1951-1960*/
            0x0AB54F, 0x04B645, 0x4A5739, 0x0A574D, 0x052642, 0x3E9335, 0x0D9549, 0x75AABE, 0x056A51, 0x096D46,/*1961-1970*/
            0x54AEBB, 0x04AD4F, 0x0A4D43, 0x4D26B7, 0x0D254B, 0x8D52BF, 0x0B5452, 0x0B6A47, 0x696D3C, 0x095B50,/*1971-1980*/
            0x049B45, 0x4A4BB9, 0x0A4B4D, 0xAB25C2, 0x06A554, 0x06D449, 0x6ADA3D, 0x0AB651, 0x095746, 0x5497BB,/*1981-1990*/
            0x04974F, 0x064B44, 0x36A537, 0x0EA54A, 0x86B2BF, 0x05AC53, 0x0AB647, 0x5936BC, 0x092E50, 0x0C9645,/*1991-2000*/
            0x4D4AB8, 0x0D4A4C, 0x0DA541, 0x25AAB6, 0x056A49, 0x7AADBD, 0x025D52, 0x092D47, 0x5C95BA, 0x0A954E,/*2001-2010*/
            0x0B4A43, 0x4B5537, 0x0AD54A, 0x955ABF, 0x04BA53, 0x0A5B48, 0x652BBC, 0x052B50, 0x0A9345, 0x474AB9,/*2011-2020*/
            0x06AA4C, 0x0AD541, 0x24DAB6, 0x04B64A, 0x6a573D, 0x0A4E51, 0x0D2646, 0x5E933A, 0x0D534D, 0x05AA43,/*2021-2030*/
            0x36B537, 0x096D4B, 0xB4AEBF, 0x04AD53, 0x0A4D48, 0x6D25BC, 0x0D254F, 0x0D5244, 0x5DAA38, 0x0B5A4C,/*2031-2040*/
            0x056D41, 0x24ADB6, 0x049B4A, 0x7A4BBE, 0x0A4B51, 0x0AA546, 0x5B52BA, 0x06D24E, 0x0ADA42, 0x355B37,/*2041-2050*/
            0x09374B, 0x8497C1, 0x049753, 0x064B48, 0x66A53C, 0x0EA54F, 0x06AA44, 0x4AB638, 0x0AAE4C, 0x092E42,/*2051-2060*/
            0x3C9735, 0x0C9649, 0x7D4ABD, 0x0D4A51, 0x0DA545, 0x55AABA, 0x056A4E, 0x0A6D43, 0x452EB7, 0x052D4B,/*2061-2070*/
            0x8A95BF, 0x0A9553, 0x0B4A47, 0x6B553B, 0x0AD54F, 0x055A45, 0x4A5D38, 0x0A5B4C, 0x052B42, 0x3A93B6,/*2071-2080*/
            0x069349, 0x7729BD, 0x06AA51, 0x0AD546, 0x54DABA, 0x04B64E, 0x0A5743, 0x452738, 0x0D264A, 0x8E933E,/*2081-2090*/
            0x0D5252, 0x0DAA47, 0x66B53B, 0x056D4F, 0x04AE45, 0x4A4EB9, 0x0A4D4C, 0x0D1541, 0x2D92B5          /*2091-2099*/
    };

    public static final int[] solarToLunar(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        return solarToLunar(year, month, day);
    }

    /**
     * 将公历日期转换为农历日期，且标识是否是闰月
     *
     * @param year
     * @param month    1-12
     * @param monthDay
     * @return 返回公历日期对应的农历日期，0：year，1：month，2：day，3：leap是否闰月，4:0小月，1大月
     */
    public static final int[] solarToLunar(int year, int month, int monthDay) {

        int[] lunarDate = new int[5];
        Date baseDate = new GregorianCalendar(1900, 0, 31).getTime();//获取1900年1月31日日期
        Date objDate = new GregorianCalendar(year, month - 1, monthDay).getTime();//传入的日期
        int offset = (int) ((objDate.getTime() - baseDate.getTime()) / 86400000L);
        // 用offset减去每农历年的天数计算当天是农历第几天
        // iYear最终结果是农历的年份, offset是当年的第几天
        int iYear, daysOfYear = 0;
        for (iYear = MIN_YEAR; iYear <= MAX_YEAR && offset > 0; iYear++) {
            daysOfYear = daysInLunarYear(iYear);
            offset -= daysOfYear;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
        }
        // 农历年份
        lunarDate[0] = iYear;

        int leapMonth = leapMonth(iYear); // 闰哪个月,1-12
        boolean isLeap = false;
        // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int iMonth, daysOfMonth = 0;
        for (iMonth = 1; iMonth <= 13 && offset > 0; iMonth++) {
            daysOfMonth = daysInLunarMonth(iYear, iMonth);
            offset -= daysOfMonth;
        }
        // offset小于0时，也要校正
        if (offset < 0) {
            offset += daysOfMonth;
            iMonth--;
        }
        // 当前月超过闰月，要校正
        if (leapMonth != 0 && iMonth > leapMonth) {
            iMonth--;
            if (iMonth == leapMonth) {
                isLeap = true;
            }
        }
        lunarDate[1] = iMonth;
        lunarDate[2] = offset + 1;
        lunarDate[3] = isLeap ? 1 : 0;
        lunarDate[4] = big_smallMonth(iYear, iMonth, leapMonth, isLeap);
//        System.out.println(lunarDate[0] + "年" + lunarDate[1] + "月" + lunarDate[2] + "日，" + (lunarDate[3] == 0 ? "不是闰月" : "闰月") + (lunarDate[4] == 0 ? "，小月" : "，大月"));
        return lunarDate;
    }

    /**
     * 传回农历月的大小
     *
     * @param iyear     农历的年份
     * @param iMonth    农历的月份
     * @param leapMonth 闰哪个月
     * @return 传回天数
     */
    public static int big_smallMonth(int iyear, int iMonth, int leapMonth, boolean isLeap) {
        int lunarDate = 0;
        if (iyear - MIN_YEAR > -1 && iyear - MIN_YEAR <= MAX_YEAR - MIN_YEAR) {
            int con = LUNAR_INFO[iyear - MIN_YEAR];
            String binaryString = Integer.toBinaryString(con).toString();//二进制
            while (binaryString.length() < 24) {
                binaryString = "0" + binaryString;
            }
            int jq = 0;
            if (leapMonth > 0 && isLeap && iMonth == leapMonth) {//是闰年并且是闰月
                jq = iMonth + 3 + 1;
            } else if (leapMonth > 0 && !isLeap && iMonth > leapMonth) {//是闰年并且大于闰月
                jq = iMonth + 3 + 1;
            } else {//
                jq = iMonth + 3;
            }
//            System.out.println(binaryString + "sss" + jq+"leapMonth="+leapMonth);
            lunarDate = Integer.parseInt(binaryString.substring(jq, jq + 1));
        }
        return lunarDate;
    }

    /**
     * 传回农历 year年的总天数
     *
     * @param year 将要计算的年份
     * @return 返回传入年份的总天数
     */
    private static int daysInLunarYear(int year) {
        int i, sum = 348;
        if (leapMonth(year) != 0) {
            sum = 377;
        }
        int monthInfo = LUNAR_INFO[year - MIN_YEAR] & 0x0FFF80;
        for (i = 0x80000; i > 0x7; i >>= 1) {
            if ((monthInfo & i) != 0) {
                sum += 1;
            }
        }
        return sum;
    }

    /**
     * 传回农历 year年month月的总天数，总共有13个月包括闰月
     *
     * @param year  将要计算的年份
     * @param month 将要计算的月份
     * @return 传回农历 year年month月的总天数
     */
    private static int daysInLunarMonth(int year, int month) {
        if ((LUNAR_INFO[year - MIN_YEAR] & (0x100000 >> month)) == 0) {
            return 29;
        } else {
            return 30;
        }
    }

    /**
     * 传回农历 year年闰哪个月 1-12 , 没闰传回 0
     *
     * @param year 将要计算的年份
     * @return 传回农历 year年闰哪个月1-12, 没闰传回 0
     */
    private static int leapMonth(int year) {
        return (int) ((LUNAR_INFO[year - MIN_YEAR] & 0xF00000)) >> 20;
    }


    /**
     * 公历每月前的天数
     */
    private static final int DAYS_BEFORE_MONTH[] = {0, 31, 59, 90, 120, 151, 181,
            212, 243, 273, 304, 334, 365};

    /**
     * 将农历日期转换为公历日期
     *
     * @param year        农历年份
     * @param month       农历月
     * @param monthDay    农历日
     * @param isLeapMonth 该月是否是闰月
     *                    [url=home.php?mod=space&uid=7300]@return[/url] 返回农历日期对应的公历日期，year0, month1, day2.
     */
    public static final int[] lunarToSolar(int year, int month, int monthDay,
                                           boolean isLeapMonth) {
        int dayOffset;
        int leapMonth;
        int i;
        if (year < MIN_YEAR || year > MAX_YEAR || month < 1 || month > 12
                || monthDay < 1 || monthDay > 30) {
            throw new IllegalArgumentException(
                    "提示：数据不准确，范围\n\t" +
                            "year : 1900~2099\n\t" +
                            "month : 1~12\n\t" +
                            "day : 1~30");
        }
        dayOffset = (LUNAR_INFO[year - MIN_YEAR] & 0x001F) - 1;
        if (((LUNAR_INFO[year - MIN_YEAR] & 0x0060) >> 5) == 2)
            dayOffset += 31;
        for (i = 1; i < month; i++) {
            if ((LUNAR_INFO[year - MIN_YEAR] & (0x80000 >> (i - 1))) == 0)
                dayOffset += 29;
            else
                dayOffset += 30;
        }
        dayOffset += monthDay;
        leapMonth = (LUNAR_INFO[year - MIN_YEAR] & 0xf00000) >> 20;
        // 这一年有闰月
        if (leapMonth != 0) {
            if (month > leapMonth || (month == leapMonth && isLeapMonth)) {
                if ((LUNAR_INFO[year - MIN_YEAR] & (0x80000 >> (month - 1))) == 0)
                    dayOffset += 29;
                else
                    dayOffset += 30;
            }
        }

        if (dayOffset > 366 || (year % 4 != 0 && dayOffset > 365)) {
            year += 1;
            if (year % 4 == 1)
                dayOffset -= 366;
            else
                dayOffset -= 365;
        }

        int[] solarInfo = new int[3];
        for (i = 1; i < 13; i++) {
            int iPos = DAYS_BEFORE_MONTH[i];
            if (year % 4 == 0 && i > 2) {
                iPos += 1;
            }

            if (year % 4 == 0 && i == 2 && iPos + 1 == dayOffset) {
                solarInfo[1] = i;
                solarInfo[2] = dayOffset - 31;
                break;
            }

            if (iPos >= dayOffset) {
                solarInfo[1] = i;
                iPos = DAYS_BEFORE_MONTH[i - 1];
                if (year % 4 == 0 && i > 2) {
                    iPos += 1;
                }
                if (dayOffset > iPos)
                    solarInfo[2] = dayOffset - iPos;
                else if (dayOffset == iPos) {
                    if (year % 4 == 0 && i == 2)
                        solarInfo[2] = DAYS_BEFORE_MONTH[i] - DAYS_BEFORE_MONTH[i - 1] + 1;
                    else
                        solarInfo[2] = DAYS_BEFORE_MONTH[i] - DAYS_BEFORE_MONTH[i - 1];

                } else
                    solarInfo[2] = dayOffset;
                break;
            }
        }
        solarInfo[0] = year;

        return solarInfo;
    }


    /**
     * 传回农历year年month月的总天数
     *
     * @param year  要计算的年份
     * @param month 要计算的月
     * @return 传回天数
     */
    final public static int daysInMonth(int year, int month) {
        return daysInMonth(year, month, false);
    }

    /**
     * 传回农历year年month月的总天数
     *
     * @param year  要计算的年份
     * @param month 要计算的月
     * @param leap  当月是否是闰月
     * @return 传回天数，如果闰月是错误的，返回0.
     */
    public static final int daysInMonth(int year, int month, boolean leap) {
        int leapMonth = leapMonth(year);
        int offset = 0;
        // 如果本年有闰月且month大于闰月时，需要校正
        if (leapMonth != 0 && month > leapMonth) {
            offset = 1;
        }
        // 不考虑闰月
        if (!leap) {
            return daysInLunarMonth(year, month + offset);
        } else {
            // 传入的闰月是正确的月份
            if (leapMonth != 0 && leapMonth == month) {
                return daysInLunarMonth(year, month + 1);
            }
        }
        return 0;
    }
}

