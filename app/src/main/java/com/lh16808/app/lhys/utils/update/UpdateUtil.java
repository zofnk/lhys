package com.lh16808.app.lhys.utils.update;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import com.lh16808.app.lhys.other.MyProgressDialog;
import com.lh16808.app.lhys.utils.SharedPreUtils;
import com.lh16808.app.lhys.utils.ToastUtil;
import com.lh16808.app.lhys.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/12/16.
 */

public class UpdateUtil {
    private static final String TAG = "UpdateUtil";
    static boolean DEBUG = true;
    private static final String PREFS_UPDATE = "updatePath";
    private static final String PREFS_IGNORE = "updateIgnore";
    private static final String PREFS_ISDOWN = "update.isDown";

    public static void log(String content) {
        if (DEBUG) {
            Log.i(TAG, content);
        }
    }

    public static boolean checkNetwork(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        }
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * 使用此方法，json格式参考assets/upgrade.txt文件格式
     *
     * @param context
     */
    public static void checkUpgrade(final Context context) {
        H.UPGRUADE(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String arg0 = new String(responseBody);
                try {
                    UpdateInfo parse = UpdateInfo.parse(arg0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (UpdateInfo.isSeting) {
                    ToastUtil.toastShow(context, "版本獲取失敗！");
                }
                MyProgressDialog.dialogHide();
            }
        });
    }

    public static boolean checkWifi(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        }
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static void setUpdate(Context context, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return;
        }
        String old = SharedPreUtils.getString(PREFS_UPDATE, "");
        if (fileName.equals(old)) {
            return;
        }
        File oldFile = new File(context.getExternalCacheDir(), old);
        if (oldFile.exists()) {
            oldFile.delete();
        }
        SharedPreUtils.putString(PREFS_UPDATE, fileName);
        File file = new File(context.getExternalCacheDir(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setIgnore(String fileName) {
        SharedPreUtils.putString(PREFS_IGNORE, fileName);
    }

    public static boolean isIgnore(String fileName) {
        return !TextUtils.isEmpty(fileName) && fileName.equals(SharedPreUtils.getString(PREFS_IGNORE, ""));
    }

    public static void install(Context context, File file, boolean force) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        } else {
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".updatefileprovider", file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        if (force) {
            System.exit(0);
        }
    }
}
