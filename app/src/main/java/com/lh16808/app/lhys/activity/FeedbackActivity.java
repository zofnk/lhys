package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.engine.OnTouchAnim;
import com.lh16808.app.lhys.model.User;
import com.lh16808.app.lhys.other.MyProgressDialog;
import com.lh16808.app.lhys.utils.RegexValidateUtil;
import com.lh16808.app.lhys.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FeedbackActivity extends BaseActivity {
    private User mUser;
    private EditText mName;
    private EditText mPhone;
    private EditText mContent;
    private View btn_feedback_Post;

    public static void start(Context context) {
        Intent starter = new Intent(context, FeedbackActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initLoadData() {

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_feedback);
        setTvTitle("意見反饋");
        mUser = User.getUser();
        initUI();
    }
    private void initUI() {
        mName = (EditText) findViewById(R.id.edt_feedback_title);
        mPhone = (EditText) findViewById(R.id.edt_feedback_phone);
        mContent = (EditText) findViewById(R.id.edt_feedback_content);
        btn_feedback_Post = findViewById(R.id.btn_feedback_Post);
        btn_feedback_Post.setOnTouchListener(new OnTouchAnim());
        btn_feedback_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = mPhone.getText().toString().trim();
                String name = mName.getText().toString().trim();
                String content = mContent.getText().toString().trim();
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(name) && TextUtils.isEmpty(content)) {
                    Toast.makeText(FeedbackActivity.this, "不能為空", Toast.LENGTH_SHORT).show();
                    return;
                }
                feedBack(name, phone, content);
            }
        });
        if (!TextUtils.isEmpty(mUser.getHym())) {
            mName.setText(mUser.getHym());
        }
    }
    @Override
    protected void loadData() {

    }

    private void feedBack(String name, String phone, String content) {
        if (!RegexValidateUtil.checkMobileNumber(phone)) {
            Toast.makeText(this, "請輸入正確的電話號碼！", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params = new RequestParams();
        if (mUser.getHym() != null) {
            params.put("userid", mUser.getUserid());
            params.put("username", mUser.getHym());
            params.put("rnd", mUser.getRnd());
        }
        params.put("enews", "ToFeedback");
        params.put("phone", phone);
        params.put("name", name);
        params.put("saytext", content);
        MyProgressDialog.dialogShow(this);
        H.FeedBackURL(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String arg0 = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(arg0);
                    int zt = jsonObject.getInt("zt");
                    String ts = jsonObject.getString("ts");
                    Toast.makeText(FeedbackActivity.this, ts, Toast.LENGTH_SHORT).show();
                    if (zt == 1) {
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
}
