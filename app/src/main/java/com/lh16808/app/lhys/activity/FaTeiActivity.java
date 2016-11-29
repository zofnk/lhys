package com.lh16808.app.lhys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.marco.ApiConfig;
import com.lh16808.app.lhys.model.User;
import com.lh16808.app.lhys.other.MyProgressDialog;
import com.lh16808.app.lhys.utils.AppLog;
import com.lh16808.app.lhys.utils.MyUtils;
import com.lh16808.app.lhys.utils.ToastUtil;
import com.lh16808.app.lhys.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FaTeiActivity extends BaseActivity {

    private EditText mEdttitle;
    private EditText mEdtContent;
    private int showType;
    private String sid;
    private String linkid;
    private String nr;
    private String title;

    @Override
    protected void initVariables() {
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fa_tei);
        Intent intent = getIntent();
        showType = intent.getIntExtra("showType", -1);
        if (showType != -1) {
            sid = intent.getStringExtra("sid");
            title = intent.getStringExtra("title");
            initData(showType);
        }
        mEdtContent = (EditText) findViewById(R.id.edt_fatie_content);
        mEdttitle = (EditText) findViewById(R.id.edt_fatie_title);
        if (!TextUtils.isEmpty(title)) {
            mEdttitle.setText(title);
        }
        findViewById(R.id.btn_faTie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyUtils.isFastDoubleClick()) {
                    return;
                }
                fatie(showType);
            }
        });
    }

    private void initData(int i) {
        User user = User.getUser();
//        Map<String, String> params = new HashMap<>();
        RequestParams params = new RequestParams();
        params.put("enews", ApiConfig.MYFAVA3[i]);
        params.put("uid", user.getUserid());
        params.put("uname", user.getHym());
        params.put("rnd", user.getRnd());
        params.put("sid", sid);
        MyProgressDialog.dialogShow(this);
        H.ForumData(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String arg0 = new String(responseBody);
                AppLog.redLog("ssssssx", arg0);
                try {
                    JSONObject jsonObject = new JSONObject(arg0);
                    nr = jsonObject.getString("nr");
                    if (showType == 2) {
                        linkid = jsonObject.getString("linkid");
                    }
                    mEdtContent.setText(nr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
            }
        });
    }

    private void fatie(int showType) {
        String content = mEdtContent.getText().toString();
        String title = mEdttitle.getText().toString();
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(title)) {
            ToastUtil.toastShow(FaTeiActivity.this, "不能為空");
            return;
        }
        RequestParams params = new RequestParams();
        if (showType == 2) {
            params.put("classid", "2");
            params.put("text", content);
            params.put("linkid", linkid);
        } else {
            params.put("classid", "1");
            params.put("newstext", content);
        }
        if (!TextUtils.isEmpty(sid)) {
            params.put("id", sid);
        }
        if (showType != -1) {
            params.put("enews", "AppMEditInfo");
        } else {
            params.put("enews", "AppMAddInfo");
        }
        params.put("title", title);
        params.put("uid", User.getUser().getUserid());
        params.put("uname", User.getUser().getHym());
        params.put("rnd", User.getUser().getRnd());
        MyProgressDialog.dialogShow(this);
        H.BIANJI(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String arg0 = new String(responseBody);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(arg0);
                    int zt = jsonObject.getInt("zt");
                    String ts = jsonObject.getString("ts");
                    Toast.makeText(FaTeiActivity.this, ts, Toast.LENGTH_SHORT).show();
                    if (zt == 1) {
                        finish();
                    } else if (zt == 2) {
                        MyUtils.outLogin(FaTeiActivity.this);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
