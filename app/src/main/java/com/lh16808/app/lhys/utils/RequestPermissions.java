package com.lh16808.app.lhys.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;



/**
 * Created by admin on 2016/8/18.
 */
public class RequestPermissions {
    public static int REQUEST_EXTERNAL_STORAGE = 1;
    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * 读写权限申请 true 有权限  false 无权限
     *
     * @param mContext
     */
    public static void writeExternalStorage(final Context mContext, PermissionCallBack mCallBack) {

        int permission = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        (Activity) mContext,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            else {
                mCallBack.setOnPermissionListener(false);
            }

        } else {
            mCallBack.setOnPermissionListener(true);
        }
    }

    /**
     * 读写权限申请 true 有权限  false 无权限
     *
     * @param mContext
     */
    public static void writeExternalStorage(final Context mContext, PermissionCallBack mCallBack, int requestCode) {

        int permission = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                ActivityCompat.requestPermissions(
                        (Activity) mContext,
                        PERMISSIONS_STORAGE,
                        requestCode
                );
            else {
                mCallBack.setOnPermissionListener(false);
            }

        } else {
            mCallBack.setOnPermissionListener(true);
        }
    }

    /**
     * 权限申请回调，需要在你调用的Activity中onRequestPermissionsResult调用该方法   true 申请权限成功  false申请权限失败
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, PermissionCallBack mCallBack) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mCallBack.setOnPermissionListener(true);
            } else {
                mCallBack.setOnPermissionListener(false);
            }
            return;
        }
    }

    /**
     * 6.0以下申请打开权限管理
     *
     * @param mContext
     */
    public static void openPre(final Context mContext) {
        RemindDialogUtil.showRemindDialog(mContext, "當前無權限，將無法進行QQ分享和應用升級~,是否打开设置？", new RemindDialogUtil.DialogCallBack() {
            @Override
            public void clickYes() {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_SETTINGS);
                mContext.startActivity(intent);
                RemindDialogUtil.hideRemindDialog();
            }

            @Override
            public void clickCancel() {
                RemindDialogUtil.hideRemindDialog();
            }
        });
    }

    public interface PermissionCallBack {
        void setOnPermissionListener(Boolean bo);
    }


}
