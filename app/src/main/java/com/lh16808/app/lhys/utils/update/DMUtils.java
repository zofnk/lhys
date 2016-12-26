package com.lh16808.app.lhys.utils.update;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;


import com.lh16808.app.lhys.utils.SharedPreUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/8/22.
 */
public class DMUtils {
    public static void Download(Context context, String fileUrl, String apkname) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(fileUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        //设置允许使用的网络类型，这里是移动网络和wifi都可以
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        //禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
        //request.setShowRunningNotification(false);
        // 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, apkname);
        // 表示下载进行中和下载完成的通知栏是否显示。默认只显示下载中通知。
        // VISIBILITY_VISIBLE_NOTIFY_COMPLETED表示下载完成后显示通知栏提示。
        // VISIBILITY_HIDDEN表示不显示任何通知栏提示，这个需要在AndroidMainfest中添加权限android.permission.DOWNLOAD_WITHOUT_NOTIFICATION.
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //不显示下载界面
        request.setVisibleInDownloadsUi(true);
         /*设置下载后文件存放的位置,如果sdcard不可用，那么设置这个将报错，因此最好不设置如果sdcard可用，下载后的文件        在/mnt/sdcard/Android/data/packageName/files目录下面，如果sdcard不可用,设置了下面这个将报错，不设置，下载后的文件在/cache这个  目录下面*/
        //request.setDestinationInExternalFilesDir(this, null, "tar.apk");
//        long id = downloadManager.enqueue(request);
//        if (Constants.DownloadAPPName.equals(apkname))
//            //TODO 把id保存好，在接收者里面要用，最好保存在Preferences里面
//            SharedPreUtils.putLong("dmKey", id);
    }

    public static void startActivityTO(Context context, String pathName) {
        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        intent1.setDataAndType(Uri.fromFile(new File(pathName)), "application/vnd.android.package-archive");
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }

    public static int VersionComparison(Context context) {
        String archiveFilePath = ApkPath(context);
        int version = -1;
        if (!TextUtils.isEmpty(archiveFilePath)) {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);
            if (info != null) {
//                ApplicationInfo appInfo = info.applicationInfo;
                version = info.versionCode;       //得到版本信息
            }
        }
        return version;
    }

    public static String ApkPath(Context context) {
        String archiveFilePath = "";
        long id = SharedPreUtils.getLong("dmKey");
        String path = QueryUitls.queryId(context, id);
        archiveFilePath = path;
        return archiveFilePath;
    }

    /*
     * check the app is installed
     */
    public static boolean isAppInstalled(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            // System.out.println("没有安装");
            return false;
        } else {
            // System.out.println("已经安装");
            return true;
        }
    }

}
