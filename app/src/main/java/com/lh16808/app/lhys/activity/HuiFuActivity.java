package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.model.User;
import com.lh16808.app.lhys.utils.AppLog;
import com.lh16808.app.lhys.utils.ToastUtil;
import com.lh16808.app.lhys.utils.http.H;
import com.lh16808.app.lhys.widget.EmoticonsEditText;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.util.InetAddressUtils;

public class HuiFuActivity extends BaseActivity {

    @Override
    protected void initLoadData() {

    }

    @Override
    protected void initVariables() {
        Intent intent = getIntent();
        final String title = intent.getStringExtra("title");
        final String sid = intent.getStringExtra("sid");
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = etContent.getText().toString().trim();
//                String localHostIp = getLocalHostIp();
//                    String[] split = localHocstIp.split(".");
                if (!TextUtils.isEmpty(text)) {
                    if (text.length() < 500)
                        sendhf(text, title, sid);
                    else
                        ToastUtil.toastShow(HuiFuActivity.this, "回复不能多于500字");
                }
            }
        });
    }

    private void sendhf(String text, String title, String sid) {
        RequestParams param = new RequestParams();
        param.put("enews", "AppMAddInfo");
        param.put("classid", "2");
        param.put("linkid", sid);
        param.put("title", title);
        param.put("text", text);
//        param.put("infoip", ip);
        param.put("userid", User.getUser().getUserid());
        param.put("username", User.getUser().getHym());
        param.put("rnd", User.getUser().getRnd());
        H.BIANJI(param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int zt = jsonObject.getInt("zt");
                    String ts = jsonObject.getString("ts");
                    ToastUtil.toastShow(HuiFuActivity.this, "" + ts);
                    if (zt == 1) {
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    EmoticonsEditText etContent;
    Button btnSend;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_hui_fu);
        etContent = (EmoticonsEditText) findViewById(R.id.et_chat);
        btnSend = (Button) findViewById(R.id.btn_send);
        etContent.setFocusable(true);
        etContent.setFocusableInTouchMode(true);
        etContent.requestFocus();
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    btnSend.setBackgroundResource(R.drawable.btn_send_bg);
                } else {
                    btnSend.setBackgroundResource(R.drawable.btn_send_bg_disable);
                }
            }
        });
        upkeyborke();
    }

    @Override
    protected void loadData() {
    }

    private void upkeyborke() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) etContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(etContent, 0);
                           }

                       },
                998);
    }

    public String getLocalHostIp() {
        String ipaddress = "";
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            // 遍历所用的网络接口
            while (en.hasMoreElements()) {
                NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
                Enumeration<InetAddress> inet = nif.getInetAddresses();
                // 遍历每一个接口绑定的所有ip
                while (inet.hasMoreElements()) {
                    InetAddress ip = inet.nextElement();
                    if (!ip.isLoopbackAddress()
                            && InetAddressUtils.isIPv4Address(ip
                            .getHostAddress())) {
                        return ipaddress = ip.getHostAddress();
                    }
                }

            }
        } catch (SocketException e) {
            AppLog.redLog("feige", "获取本地ip地址失败");
            e.printStackTrace();
        }
        return ipaddress;

    }

}
