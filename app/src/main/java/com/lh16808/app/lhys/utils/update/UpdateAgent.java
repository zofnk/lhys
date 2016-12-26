/*
 * Copyright 2016 czy1121
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lh16808.app.lhys.utils.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.NotificationCompat;


import com.lh16808.app.lhys.utils.ToastUtil;

import java.io.File;

public class UpdateAgent {

    public interface OnProgressListener {

        void onStart();

        void onProgress(int progress);

        void onFinish();
    }

    public interface OnFailureListener {
        void onFailure(UpdateError error);
    }

    private Context mContext;
    private String mUrl;
    private File mTmpFile;
    private File mApkFile;

    private UpdateInfo mInfo;
    private UpdateError mError = null;

    private OnFailureListener mOnFailureListener;
    private OnProgressListener mOnProgressListener;
    private OnProgressListener mOnNotificationListener;
    private OnProgressListener mOnDialogListener;

    public UpdateAgent(Context context, UpdateInfo info) {
        mContext = context;
        mInfo = info;
        mUrl = info.url;
        mOnFailureListener = new OnFailure(context);
    }

    public void setError(UpdateError error) {
        mError = error;
    }

    //更新
    public void update() {
        onDownload();
    }

    public void downloadStart() {
        if (mInfo.isSilent) {
            mOnNotificationListener.onStart();
        } else {
            mOnProgressListener.onStart();
        }
    }

    public void downloadProgress(int progress) {
        if (mInfo.isSilent) {
            mOnNotificationListener.onProgress(progress);
        } else {
            mOnProgressListener.onProgress(progress);
        }
    }

    public void downloadFinish() {
        if (mInfo.isSilent) {
            mOnNotificationListener.onFinish();
        } else {
            mOnProgressListener.onFinish();
        }
        if (mError != null) {
            mOnFailureListener.onFailure(mError);
        } else {
            mTmpFile.renameTo(mApkFile);
            if (mInfo.isAutoInstall) {
                onInstall();
            }
        }
    }

    protected void onDownload() {
        mTmpFile = new File(mContext.getExternalCacheDir(), mInfo.fileName);
        mApkFile = new File(mContext.getExternalCacheDir(), mInfo.fileName + ".apk");
        if (mOnNotificationListener == null) {
            mOnNotificationListener = new EmptyProgress();
        }
        if (mOnProgressListener == null) {
            mOnProgressListener = new NotificationProgress(mContext, 988);
        }
        new UpdateDownloader(this, mContext, mInfo.url, mTmpFile).execute();
    }

    //安装
    protected void onInstall() {
        UpdateUtil.install(mContext, mApkFile, mInfo.isForce);
    }


    private static class OnFailure implements OnFailureListener {
        private Context mContext;

        public OnFailure(Context context) {
            mContext = context;
        }

        @Override
        public void onFailure(UpdateError error) {
            UpdateUtil.log(error.toString());
            ToastUtil.toastShow(mContext, error.toString());
        }
    }

    public static class EmptyProgress implements UpdateAgent.OnProgressListener {
        @Override
        public void onStart() {

        }

        @Override
        public void onFinish() {

        }

        @Override
        public void onProgress(int progress) {

        }
    }

    public class DialogProgress implements UpdateAgent.OnProgressListener {
        private Context mContext;
        private ProgressDialog mDialog;

        public DialogProgress(Context context) {
            mContext = context;
        }

        @Override
        public void onStart() {
            mDialog = new ProgressDialog(mContext);
            mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mDialog.setMessage("下载中...");
            mDialog.setIndeterminate(false);
            mDialog.setCancelable(true);
            mDialog.show();
        }

        @Override
        public void onProgress(int i) {
            if (mDialog != null) {
                mDialog.setProgress(i);
            }
        }

        @Override
        public void onFinish() {
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }
        }
    }

    public class NotificationProgress implements UpdateAgent.OnProgressListener {
        private Context mContext;
        private int mNotifyId;
        private NotificationCompat.Builder mBuilder;

        public NotificationProgress(Context context, int notifyId) {
            mContext = context;
            mNotifyId = notifyId;
        }

        @Override
        public void onStart() {
            if (mBuilder == null) {//mContext.getString(mContext.getApplicationInfo().labelRes)
                String title = "下载中 - " + mInfo.name;
                mBuilder = new NotificationCompat.Builder(mContext);
                mBuilder.setOngoing(true)
                        .setAutoCancel(false)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setSmallIcon(mContext.getApplicationInfo().icon)
                        .setTicker(title)
                        .setContentTitle(title);
            }
            onProgress(0);
        }

        @Override
        public void onProgress(int progress) {
            if (mBuilder != null) {
                if (progress > 0) {
                    mBuilder.setPriority(Notification.PRIORITY_DEFAULT);
                    mBuilder.setDefaults(0);
                }
                mBuilder.setProgress(100, progress, false);

                NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                nm.notify(mNotifyId, mBuilder.build());
            }
        }

        @Override
        public void onFinish() {
            NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(mNotifyId);
        }
    }
}