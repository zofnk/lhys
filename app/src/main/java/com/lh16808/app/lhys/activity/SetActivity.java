package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lh16808.app.lhys.R;

import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.engine.OnTouchAnim;
import com.lh16808.app.lhys.model.User;
import com.lh16808.app.lhys.other.MyProgressDialog;
import com.lh16808.app.lhys.utils.MyUtils;
import com.lh16808.app.lhys.utils.ToastUtil;


public class SetActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvCache;
    private View mBtnOut;

    public static void start(Context context) {
        Intent starter = new Intent(context, SetActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initVariables() {
        mBtnOut = findViewById(R.id.btn_outLogin);
        mBtnOut.setOnTouchListener(new OnTouchAnim());
        if (!TextUtils.isEmpty(User.getUser().getHym())) {
            mBtnOut.setVisibility(View.VISIBLE);
        }else {
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
                getShow();
                break;
            case R.id.ll_set_yaoqin:
                break;
            case R.id.ll_set_back:
                break;
            case R.id.ll_set_haopin:
                break;
            case R.id.ll_set_jiancha:
                break;
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

    private void getShow() {
        getConfirmDialog(this, "是否清空缓存?", new DialogInterface.OnClickListener
                () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearAppCache();
                tvCache.setText("0KB");
            }
        }).show();
    }

    private void clearAppCache() {

    }
}
