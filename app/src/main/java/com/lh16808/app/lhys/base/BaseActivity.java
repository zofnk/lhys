package com.lh16808.app.lhys.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.other.MyDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;


/**
 * 基类Activity
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected MyDialog myDialog;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量
     */
    protected abstract void initLoadData();

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量
     */
    protected abstract void initVariables();

    /**
     * 加载layout布局文件，初始化控件，为控件挂上事件方法
     *
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 调用获取数据
     */
    protected abstract void loadData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BaseAPP.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setBarTin();
        initLoadData();
        initViews(savedInstanceState);
        initVariables();
        loadData();
    }

    protected void setBarTin() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    protected void setTvTitle(String title) {
        TextView tvTitle = (TextView) findViewById(R.id.tool_bar_title);
        if (tvTitle != null)
            tvTitle.setText(title);
    }

    public void onClickBack(View view) {
        finish();
    }


    public void onClickShare(View view) {
        myDialog = new MyDialog(this);
        myDialog.show(getSupportFragmentManager(), null);
    }

    /*public void onError(View view) {
        if (!CommonUtils.isNetConnectionAvailable(this)) {
            ToastUtil.toastShow(this, getString(R.string.net_error));
            return;
        }
        findViewById(R.id.data).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_error).setVisibility(View.GONE);
        loadData();
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        RequestPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, new RequestPermissions.PermissionCallBack() {
//            @Override
//            public void setOnPermissionListener(Boolean bo) {
//
//            }
//        });
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
    }
}