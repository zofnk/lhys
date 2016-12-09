package com.lh16808.app.lhys.laohuangli;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;


import com.lh16808.app.lhys.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class LhlSQLitedb {
    private static final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "laohuangli.db";// 保存的数据库文件名
    static int version = 2; // 数据库版本
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath();
    // 在手机里存放数据库的位置(/data/data/com.develop.demo/reba.db)

    static SQLiteDatabase database;
    static Context context;

    public static SQLiteDatabase getSQLiteDatabase(Context cont) {
        context = cont;
        if (database == null) {
            openDatabase(DB_PATH + "/" + context.getPackageName() + "/" + DB_NAME);
            while (database == null) {
                try {
//                    Log.i("tag", "ssssssss获取中");
                    new Thread().sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
//        Log.i("tag", "ssssssss成功");
        return database;
    }

    /**
     * 导入打开数据库
     */
    private static void openDatabase(final String dbfile) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences("lhlsqlite", Activity.MODE_PRIVATE);
        int cont = sharedPreferences.getInt("lhlversion", 0);
        File file = new File(dbfile);
        //版本不同，并且数据库文件存在
        if (cont != version && file.exists()) {
            file.delete();
//            Log.i("tag", cont+"ssssssss"+version+"版本不同，并且数据库文件存在");
        }
        if (!(file.exists())) {
            // 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
            new Thread() {
                public void run() {
                    try {
                        InputStream is = context.getResources()
                                .openRawResource(R.raw.laohuangli); // 欲导入的数据库
                        FileOutputStream fos = new FileOutputStream(dbfile);
                        byte[] buffer = new byte[BUFFER_SIZE];
                        int count = 0;
                        while ((count = is.read(buffer)) > 0) {
                            fos.write(buffer, 0, count);
                        }
                        fos.close();
                        is.close();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("lhlversion", version);
                        editor.commit();
//                        Log.i("tag", sharedPreferences.getInt("lhlversion", 0)+"sssss");
                        database = SQLiteDatabase.openOrCreateDatabase(dbfile,
                                null);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else {
            database = SQLiteDatabase.openOrCreateDatabase(dbfile,
                    null);
        }
    }

    /**
     * 关闭数据库
     */
    public static void closeDatabase() {
        if (database != null) {
            database.close();
            database = null;
        }
    }
}


