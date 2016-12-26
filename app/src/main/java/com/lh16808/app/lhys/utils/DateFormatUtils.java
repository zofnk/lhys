package com.lh16808.app.lhys.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式化类
 *
 * @author zhouyujing
 * @version v1.0
 * @e-mail 1032668839@qq.com
 * @create-time 2015年10月19日 下午4:19:34
 */

@SuppressLint("SimpleDateFormat")
public class DateFormatUtils {
    private static final String TAG = "DateFormatUtils";

    public static String format(String time, String string) {
        long ltime = 0;
        try {
            ltime = Long.parseLong(time);
        } catch (NumberFormatException e00) {
        }
        return format(ltime, string);
    }

    public static String format(Long time, String string) {
        SimpleDateFormat sdf = new SimpleDateFormat(string);
        return sdf.format(new Date(time * 1000L));
    }

    /**
     * 獲取當前時間
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new Date());
        return date;
    }

    /**
     * 返回时间差
     *
     * @param date
     * @return
     */
    public static String deltaT(long date) {
        long time = (System.currentTimeMillis() - date) / 1000;
        if (time < 60) {
            return "刚刚";
        } else if (time < 3600) {
            return time / 60 + "分钟前";
        } else if (time < 24 * 3600) {
            return time / 3600 + "小时前";
        } else {
            return time / 24 / 3600 + "天前";
        }
    }


    /**
     * 转为MM-dd HH:mm
     *
     * @param date
     * @return
     */
    public static String formatWithMDHm(long date) {
        if (date == 0) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(new Date(date));
    }

    /**
     * 转为 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatWithYMDHms(long date) {
        if (date == 0) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(date));
    }

    /**
     * 由yyyy-MM-dd'T'HH:mm:ssZ -> 时间戳
     *
     * @param datetime
     * @return
     */
    public static long parse(String datetime) {
        try {
            if (TextUtils.isEmpty(datetime)) {
                return System.currentTimeMillis();
            }
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ssZ");
            return sdf.parse(datetime).getTime();
        } catch (ParseException e) {
            AppLog.redLog(TAG, e + "");
        }
        return System.currentTimeMillis();
    }

    /**
     * 由yyyy-MM-dd'T'HH:mm\ -> 时间戳
     *
     * @param datetime
     * @return
     */
    public static long parseToLong(String datetime) {
        try {

            if (TextUtils.isEmpty(datetime)) {
                return System.currentTimeMillis();
            }
            // SimpleDateFormat sdf = new
            // SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.parse(datetime).getTime();
        } catch (ParseException e) {
            AppLog.redLog(TAG, e + "");
        }

        return System.currentTimeMillis();
    }

    public static Date parseToDate(String datetime) {
        try {

            if (TextUtils.isEmpty(datetime)) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(datetime);
        } catch (ParseException e) {
            AppLog.redLog(TAG, e + "");
        }

        return null;
    }

    /**
     * 将日期转化为年月日时分秒
     *
     * @param date
     * @return
     */
    public static String parseDateToString(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 由yyyy-MM-dd -> 时间戳
     *
     * @param datetime
     * @return
     */
    public static long parseToYMD(String datetime) {
        try {

            if (TextUtils.isEmpty(datetime)) {
                return System.currentTimeMillis();
            }
            // SimpleDateFormat sdf = new
            // SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(datetime).getTime();
        } catch (ParseException e) {
            AppLog.redLog(TAG, e + "");
        }

        return System.currentTimeMillis();
    }

    public static long parse(String datetime, String simple) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(simple);
            return sdf.parse(datetime).getTime();
        } catch (ParseException e) {
        }
        return 0;
    }

    /**
     * 时间转换成毫秒
     *
     * @param expireDate
     * @return
     */
    public static long getSecondsFromDate(String expireDate) {
        if (expireDate == null || expireDate.trim().equals(""))
            return 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(expireDate);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 转换成时间格式(类似音乐、视频计算时间)
     *
     * @param time
     * @return
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":"
                        + unitFormat(second);
            }
        }
        return timeStr;
    }

    /**
     * 之上的附属方法
     *
     * @param i
     * @return
     */
    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * 時間差
     *
     * @param nowTime 當前時間
     * @param oldTime 開獎時間
     * @return 返回毫秒
     */
    public static long getTimeDifference(String nowTime, String oldTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date d1 = df.parse(nowTime);
            Date d2 = df.parse(oldTime);

            long diff = d2.getTime() - d1.getTime();

            return diff;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;

    }

    /**
     * 時間差
     *
     * @param nowTime 當前時間
     * @param oldTime 開獎時間
     * @return 返回毫秒
     */
    public static long getTimeDifference2(String nowTime, String oldTime) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");

        try {
            Date d1 = df.parse(nowTime);
            Date d2 = df.parse(oldTime);

            long diff = d2.getTime() - d1.getTime();

            return diff;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;

    }

    public static String[] getTotalTime(long time) {
        long d = time / 3600000 / 24;
        long h = (time - d * 24 * 3600000) / 3600000;
        long m = (time - d * 24 * 3600000 - h * 3600000) / 60000;
        long s = (time - d * 24 * 3600000 - h * 3600000 - m * 60000) / 1000;
        String[] times = new String[4];
        times[0] = d > 9 ? (d + "") : ("0" + d);
        times[1] = h > 9 ? (h + "") : ("0" + h);
        times[2] = m > 9 ? (m + "") : ("0" + m);
        times[3] = s > 9 ? (s + "") : ("0" + s);
        return times;
    }
}
