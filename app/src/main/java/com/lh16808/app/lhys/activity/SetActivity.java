package com.lh16808.app.lhys.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lh16808.app.lhys.R;

import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.engine.OnTouchAnim;
import com.lh16808.app.lhys.marco.Constants;
import com.lh16808.app.lhys.model.User;
import com.lh16808.app.lhys.other.MyProgressDialog;
import com.lh16808.app.lhys.utils.DataCleanManager;
import com.lh16808.app.lhys.utils.MyUtils;
import com.lh16808.app.lhys.utils.PathInfo;
import com.lh16808.app.lhys.utils.RemindDialogUtil;
import com.lh16808.app.lhys.utils.SharedPreUtils;
import com.lh16808.app.lhys.utils.ToastUtil;
import com.lh16808.app.lhys.utils.update.UpdateInfo;
import com.lh16808.app.lhys.utils.update.UpgradeUtils;


public class SetActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvCache;
    private View mBtnOut;
    private String size;

    public static void start(Context context) {
        Intent starter = new Intent(context, SetActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initLoadData() {

    }

    @Override
    protected void initVariables() {
        mBtnOut = findViewById(R.id.btn_outLogin);
        mBtnOut.setOnTouchListener(new OnTouchAnim());
        if (!TextUtils.isEmpty(User.getUser().getHym())) {
            mBtnOut.setVisibility(View.VISIBLE);
        } else {
            mBtnOut.setVisibility(View.GONE);
        }


        mBtnOut.setOnClickListener(this);
        findViewById(R.id.ll_set_clean).setOnClickListener(this);
        findViewById(R.id.ll_set_yaoqin).setOnClickListener(this);
        findViewById(R.id.ll_set_back).setOnClickListener(this);
        findViewById(R.id.ll_set_haopin).setOnClickListener(this);
        findViewById(R.id.ll_set_jiancha).setOnClickListener(this);
        tvCache = (TextView) findViewById(R.id.tv_CleanCache);

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_set);
        setTvTitle("设置");
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        if (MyUtils.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_outLogin:
                if (!TextUtils.isEmpty(User.getUser().getHym())) {
                    MyProgressDialog.dialogShow(this);
                    MyUtils.outLogin(this);
                }
                break;
            case R.id.ll_set_clean:
                clearCache();
                break;
            case R.id.ll_set_yaoqin:
                String qrurl = SharedPreUtils.getString(Constants.QRURL);
                share(qrurl, this);
                break;
            case R.id.ll_set_back:
                FeedbackActivity.start(this);
                break;
            case R.id.ll_set_haopin:
                giveFavor();
                break;
            case R.id.ll_set_jiancha:
                MyProgressDialog.dialogShow(this);
                UpdateInfo.isSeting = true;
                UpgradeUtils.checkUpgrade(this);
                break;
        }
    }

    public void share(final String share, final Activity context) {
        {

            RemindDialogUtil.showRemindDialog(context, "是否分享六合运势APP下载地址?", new RemindDialogUtil.DialogCallBack() {

                @Override
                public void clickYes() {
                    ShareCompat.IntentBuilder.from(context)
                            .setType("text/plain")
                            .setText(share)
                            .startChooser();
                    RemindDialogUtil.hideRemindDialog();
                }

                @Override
                public void clickCancel() {
                    RemindDialogUtil.hideRemindDialog();
                }
            });
        }
    }

    /**
     * 赏个好评
     */
    private void giveFavor() {
        try {
            Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtil.toastShow(this, "無法打開應用市場");
        }
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }

    public static AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }

    /**
     * 清除缓存
     */
    private void clearCache() {
        RemindDialogUtil.showRemindDialog(this, "确定清除缓存吗？", new RemindDialogUtil.DialogCallBack() {
            @Override
            public void clickYes() {
                RemindDialogUtil.hideRemindDialog();
                MyProgressDialog.dialogShow(SetActivity.this);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.get(SetActivity.this).clearDiskCache();
                            }
                        }).start();
                        Glide.get(SetActivity.this).clearMemory();
                        try {
//                            FileUtils.delFilesFromPath(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
                            DataCleanManager.cleanInternalCache(SetActivity.this);
                            DataCleanManager.cleanExternalCache(SetActivity.this);
                            DataCleanManager.cleanDatabases(SetActivity.this);
                            DataCleanManager.cleanFiles(SetActivity.this);
                            DataCleanManager.cleanCustomCache(PathInfo.getRootPath());
                            PathInfo.createAllPath();
                            Toast.makeText(SetActivity.this, "缓存清除成功", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(SetActivity.this, "缓存清除失败", Toast.LENGTH_SHORT).show();
                        } finally {
                            MyProgressDialog.dialogHide();
//                            setBtnCache();
                            tvCache.setText(0.00 + "B");
                        }
                    }
                }, 1000);
            }

            @Override
            public void clickCancel() {
                RemindDialogUtil.hideRemindDialog();
            }
        });
    }
}
