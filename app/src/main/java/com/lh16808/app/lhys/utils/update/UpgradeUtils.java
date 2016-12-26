package com.lh16808.app.lhys.utils.update;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.lh16808.app.lhys.marco.Constants;
import com.lh16808.app.lhys.other.MyProgressDialog;
import com.lh16808.app.lhys.utils.SharedPreUtils;
import com.lh16808.app.lhys.utils.ToastUtil;
import com.lh16808.app.lhys.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.io.File;

import cz.msebera.android.httpclient.Header;

public class UpgradeUtils {
    public static final String APK_UPGRADE = Environment.getExternalStorageDirectory() + "/LHeGJ/upgrade.apk" ;
    private static Context mContext;
    private static UpdateInfo mInfo;
    private static int mVersionCode = -1;

    /**
     * 使用此方法，json格式参考assets/upgrade.txt文件格式
     * SharedPreUtils.putLong("dmKey", id);
     *
     * @param context
     */
    public static void checkUpgrade(Context context) {
        SharedPreUtils.init(context);
        mContext = context;
        H.UPGRUADE(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String arg0 = new String(responseBody);
                checkUpgrade(arg0);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (UpdateInfo.isSeting) {
                    Toast.makeText(mContext, "版本獲取失敗！", Toast.LENGTH_SHORT).show();
                }
                MyProgressDialog.dialogHide();
            }
        });
    }

    private static void checkUpgrade(String json) {
        try {
            mInfo = UpdateInfo.parse(json);
            SharedPreUtils.putString("qrcode1", mInfo.qrcode1);
            SharedPreUtils.putString(Constants.SHARETEXT, mInfo.sharetext);
            SharedPreUtils.putString(Constants.SHARELINK, mInfo.sharelink);
            SharedPreUtils.putString(Constants.QRURL, mInfo.url);
            SharedPreUtils.putInt(Constants.IOSON, mInfo.ioson);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        int version = VersionUtils.getCurrVersion(mContext);
        if (mInfo.versionCode > version) {
            new AlertDialog.Builder(mContext).setTitle("升級").setMessage(mInfo.updateContent)
                    .setPositiveButton("升級", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            String filePath = mContext.getExternalCacheDir().getPath() + "/lh.apk" ;
                            PackageManager packageManager = mContext.getPackageManager();
                            PackageInfo packageInfo = packageManager.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
                            if (packageInfo != null) {
                                mVersionCode = packageInfo.versionCode;
                            }
                            if (mVersionCode == mInfo.versionCode) {
                                File file = new File(filePath);
                                if (file.exists())
                                    UpdateUtil.install(mContext, file, false);
                                else
                                    ToastUtil.toastShow(mContext, "文件不存在~");
                            } else {
                                new UpdateAgent(mContext, mInfo).update();
                            }
                        }
                    }).setNegativeButton("取消", null).show();
        } else {
            if (mInfo.isSeting) {
                Toast.makeText(mContext, "已是最新版本！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
