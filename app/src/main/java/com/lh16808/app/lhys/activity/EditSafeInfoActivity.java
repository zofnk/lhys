package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.engine.OnTouchAnim;
import com.lh16808.app.lhys.marco.Constants;
import com.lh16808.app.lhys.model.User;
import com.lh16808.app.lhys.other.MyProgressDialog;
import com.lh16808.app.lhys.utils.RegexValidateUtil;
import com.lh16808.app.lhys.utils.SharedPreUtils;
import com.lh16808.app.lhys.utils.dataUtils.ToastUtil;
import com.lh16808.app.lhys.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class EditSafeInfoActivity extends BaseActivity {
    private EditText et_forget_account;
    private EditText et_forget_pwd_new_pwd;
    private EditText et_forget_pwd_confirm_pwd;
    private Button btn_forget_pwd_confirm;
    private User user;
    private View mRootView;

    public static void start(Context context) {
        Intent starter = new Intent(context, EditSafeInfoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initLoadData() {

    }

    @Override
    protected void initVariables() {
        user = User.getUser();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_safe_info);
        setTvTitle("密码管理");
        initUI();
    }

    @Override
    protected void loadData() {

    }

    private void hideSoftInput() {
        View view = getWindow().peekDecorView();
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void initUI() {
        mRootView = findViewById(R.id.activity_edit_safe_info);
        mRootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftInput();
                return false;
            }
        });
        et_forget_account = (EditText) findViewById(R.id.et_forget_oldpassword);
        et_forget_pwd_new_pwd = (EditText) findViewById(R.id.et_forget_pwd_new_pwd);
        et_forget_pwd_confirm_pwd = (EditText) findViewById(R.id.et_forget_pwd_confirm_pwd);
        btn_forget_pwd_confirm = (Button) findViewById(R.id.btn_forget_pwd_confirm);
        btn_forget_pwd_confirm.setOnTouchListener(new OnTouchAnim());
        btn_forget_pwd_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(user.getHym())) {
                    initRG();
                }
            }
        });
    }


    private void initRG() {
        String oldpassword = et_forget_account.getText().toString().trim();
        String userPWS1 = et_forget_pwd_new_pwd.getText().toString().trim();
        String userPWS2 = et_forget_pwd_confirm_pwd.getText().toString().trim();
        boolean setVali = setVali(oldpassword, userPWS1, userPWS2);
        if (!setVali) {
            return;
        }
        RequestParams params = new RequestParams();
        params.put("enews", "EditSafeInfo");
        params.put("oldpassword", oldpassword);
        params.put("password", userPWS1);
        params.put("repassword", userPWS2);
        params.put("userid", user.getUserid());
        params.put("username", user.getHym());
        params.put("rnd", user.getRnd());
        MyProgressDialog.dialogShow(this);
        H.USER(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String arg0 = new String(responseBody);
                MyProgressDialog.dialogHide();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(arg0);
                    int zt = jsonObject.getInt("zt");
                    String ts = jsonObject.getString("ts");
                    showTask(ts);
                    if (zt == 1) {
                        sendGB();
                        user = null;
                        User.getUser().cleanUser();
                        SharedPreUtils.putString(Constants.SHARE_PWD, "");
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(EditSafeInfoActivity.this, "連接失敗~", Toast.LENGTH_SHORT).show();
                MyProgressDialog.dialogHide();
            }
        });
    }


    private boolean setVali(String userName, String userPWS1, String userPWS2) {
        if (TextUtils.isEmpty(userName)) {
            showTask("请输入正确的密码！");
            return false;
        }
        if (TextUtils.isEmpty(userPWS1)) {
            showTask("请输入正确的密码！");
            return false;
        }
        if (!RegexValidateUtil.checkPassWork(userPWS1)) {
            showTask("请输入正确的密码！");
            return false;
        }
        if (!userPWS1.equals(userPWS2)) {
            showTask("密码输入不一致！！！");
            return false;
        }
        return true;
    }

    private void showTask(String string) {
        ToastUtil.toastShow(EditSafeInfoActivity.this, string + "");
    }

    private void sendGB() {
        //开启广播
        //创建一个意图对象
        Intent intent = new Intent();
        //指定发送广播的频道
        intent.setAction(Constants.GB);
        //发送广播的数据
        intent.putExtra(Constants.GB_KEY, 2);
        //发送
        sendBroadcast(intent);
    }
}
