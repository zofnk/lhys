package com.lh16808.app.lhys.utils.update;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class QueryUitls {

    private static DownloadManager downloadManager;
    static List<String> lists = new ArrayList<>();
    static List<String> uriList = new ArrayList<>();
    static List<String> runList = new ArrayList<>();

    public static List<String> query(Context context) {
        lists.clear();
        DownloadManager.Query query = new DownloadManager.Query();
//        query.setFilterById(id);
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor cursor = downloadManager.query(query);
        int columnCount = cursor.getColumnCount();
        String path = null;                                                                                                                                       //TODO 这里把所有的列都打印一下，有什么需求，就怎么处理,文件的本地路径就是path
        String pathName = null;
        while (cursor.moveToNext()) {
            for (int j = 0; j < columnCount; j++) {
                String columnName = cursor.getColumnName(j);
                String string = cursor.getString(j);
                if (columnName.equals("local_uri")) {
                    path = string;
                }
                if (columnName.equals("local_filename")) {
                    pathName = string;
                    lists.add(string);
                    Log.e("DownloadManager:" + columnName, "" + string);
                }
                if (string != null) {
//                        System.out.println(columnName + ": " + string);
//                    Log.e("DownloadManager:" + columnName, "" + string);
                } else {
//                    Log.e("DownloadManager:" + columnName, "" + ": null");
//                        System.out.println(columnName + ": null");
                }
            }
        }
        cursor.close();
        //如果sdcard不可用时下载下来的文件，那么这里将是一个内容提供者的路径，这里打印出来，有什么需求就怎么样处理                                                   if(path.startsWith("content:")) {
        if (path != null) {
            cursor = context.getContentResolver().query(Uri.parse(path), null, null, null, null);
            if (cursor != null) {
                columnCount = cursor.getColumnCount();
                while (cursor.moveToNext()) {
                    for (int j = 0; j < columnCount; j++) {
                        String columnName = cursor.getColumnName(j);
                        String string = cursor.getString(j);
                        if (string != null) {
//                        System.out.println(columnName + ": " + string);
//                    Log.e("DownloadManager:" + columnName, "" + string);
                        } else {
//                    Log.e("DownloadManager:" + columnName, "" + ": null");
//                        System.out.println(columnName + ": null");
                        }
                    }
                }
                cursor.close();
            }
        }
        return lists;
    }
    public static String queryId(Context context, long id) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor cursor = downloadManager.query(query);
        int columnCount = cursor.getColumnCount();
        String path = null;                                                                                                                                       //TODO 这里把所有的列都打印一下，有什么需求，就怎么处理,文件的本地路径就是path
        String pathName = "";
        while (cursor.moveToNext()) {
            for (int j = 0; j < columnCount; j++) {
                String columnName = cursor.getColumnName(j);
                String string = cursor.getString(j);
                if (columnName.equals("local_uri")) {
                    path = string;
                }
//                if (columnName.equals("uri")) {
//                    pathName = string;
//                    uriList.add(string);
//                    Log.e("DownloadManager:" + columnName, "" + string);
//                }
                if (columnName.equals("local_filename")) {
                    pathName = string;
//                    lists.add(string);
                    Log.e("DownloadManager:" + columnName, "" + string);
                }
                if (string != null) {
//                        System.out.println(columnName + ": " + string);
//                    Log.e("DownloadManager:" + columnName, "" + string);
                } else {
//                    Log.e("DownloadManager:" + columnName, "" + ": null");
//                        System.out.println(columnName + ": null");
                }
            }
        }
        cursor.close();
        //如果sdcard不可用时下载下来的文件，那么这里将是一个内容提供者的路径，这里打印出来，有什么需求就怎么样处理                                                   if(path.startsWith("content:")) {
//        if (path != null) {
//            cursor = context.getContentResolver().query(Uri.parse(path), null, null, null, null);
//            if (cursor != null) {
//                columnCount = cursor.getColumnCount();
//                while (cursor.moveToNext()) {
//                    for (int j = 0; j < columnCount; j++) {
//                        String columnName = cursor.getColumnName(j);
//                        String string = cursor.getString(j);
//                        if (string != null) {
////                        System.out.println(columnName + ": " + string);
////                    Log.e("DownloadManager:" + columnName, "" + string);
//                        } else {
////                    Log.e("DownloadManager:" + columnName, "" + ": null");
////                        System.out.println(columnName + ": null");
//                        }
//                    }
//                }
//                cursor.close();
//            }
//        }
        return pathName;
    }

    public static List<String> queryRunning(Context context) {
        runList.clear();
        DownloadManager.Query query = new DownloadManager.Query();
//        query.setFilterById(id);
        query.setFilterByStatus(DownloadManager.STATUS_RUNNING);
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor cursor = downloadManager.query(query);
        int columnCount = cursor.getColumnCount();
        String path = null;                                                                                                                                       //TODO 这里把所有的列都打印一下，有什么需求，就怎么处理,文件的本地路径就是path
        String pathName = null;
        while (cursor.moveToNext()) {
            for (int j = 0; j < columnCount; j++) {
                String columnName = cursor.getColumnName(j);
                String string = cursor.getString(j);
                if (columnName.equals("local_uri")) {
                    path = string;
                }
                if (columnName.equals("uri")) {
                    pathName = string;
                    runList.add(string);
                    Log.e("DownloadManager:" + columnName, "" + string);
                }
                if (string != null) {
//                        System.out.println(columnName + ": " + string);
//                    Log.e("DownloadManager:" + columnName, "" + string);
                } else {
//                    Log.e("DownloadManager:" + columnName, "" + ": null");
//                        System.out.println(columnName + ": null");
                }
            }
        }
        cursor.close();
        //如果sdcard不可用时下载下来的文件，那么这里将是一个内容提供者的路径，这里打印出来，有什么需求就怎么样处理                                                   if(path.startsWith("content:")) {
        if (path != null) {
            cursor = context.getContentResolver().query(Uri.parse(path), null, null, null, null);
            if (cursor != null) {
                columnCount = cursor.getColumnCount();
                while (cursor.moveToNext()) {
                    for (int j = 0; j < columnCount; j++) {
                        String columnName = cursor.getColumnName(j);
                        String string = cursor.getString(j);
                        if (string != null) {
//                        System.out.println(columnName + ": " + string);
//                    Log.e("DownloadManager:" + columnName, "" + string);
                        } else {
//                    Log.e("DownloadManager:" + columnName, "" + ": null");
//                        System.out.println(columnName + ": null");
                        }
                    }
                }
                cursor.close();
            }
        }
        return runList;
    }
}
