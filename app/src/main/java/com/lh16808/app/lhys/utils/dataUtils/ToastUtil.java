package com.lh16808.app.lhys.utils.dataUtils;

import android.content.Context;
import android.widget.Toast;

import com.lh16808.app.lhys.utils.StausCodeUtil;

/**
 * ToastUtil 帮助类
 *
 * @author chenzhifeng
 * @version v1.0
 * @e-mail seven2729@126.com
 * @copyright 2010-2015
 * @create-time 2015年10月19日 下午2:19:56
 */
public class ToastUtil {
    private static Toast toast = null;

    private static Toast getToast(Context context) {
        if (context != null) {
            if (toast == null) {
                toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
            }
        }
        return toast;
    }

    // 弹窗信息
    public static void toastShow(Context context, int resId) {
        // if (!context.getString(resId).equals(""))
        if (context != null) {
            if (context.getString(resId) != null) {
                toastShow(context, context.getString(resId) + "");
            }
        }
    }

    public static void toastShow(Context context, CharSequence text) {
        toastShow(context, text + "");
    }

    // 弹窗信息
    public static void toastShow(Context context, String msg) {
        if (msg != null) {
            getToast(context);
            if (toast != null) {
                toast.setText(msg + "");
                toast.show();
            }
        }
    }

    // 弹窗信息
    public static void onFailure(Context context, int statusCode) {
        toastShow(context, StausCodeUtil.getStausCode(statusCode));
    }


}
