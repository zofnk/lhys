package com.lh16808.app.lhys.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.model.BannerUrl;
import com.lh16808.app.lhys.other.MyProgressDialog;
import com.lh16808.app.lhys.other.ShowBannerInfo;
import com.lh16808.app.lhys.utils.AppLog;
import com.lh16808.app.lhys.utils.MyUtils;
import com.lh16808.app.lhys.utils.ToastUtil;
import com.lh16808.app.lhys.utils.dataUtils.DateFormatUtils;
import com.lh16808.app.lhys.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ZiliaoDetailActivity extends BaseActivity {

    private String sid;
    private TextView mTvTitle;
    private TextView mTvTime;
    private TextView mTvContent;
    private RelativeLayout rl_health_viewpg;
    ArrayList<BannerUrl> listimg = new ArrayList<>();
    private String classid;

    @Override
    protected void initVariables() {
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ziliao_detail);
        findViewById(R.id.tv_share).setVisibility(View.GONE);
        View collect = findViewById(R.id.tv_collect);
        collect.setVisibility(View.VISIBLE);
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtils.collect(ZiliaoDetailActivity.this, sid, classid);
            }
        });

        sid = getIntent().getStringExtra("CateDetailModel");
        classid = getIntent().getStringExtra("classid");

        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvContent = (TextView) findViewById(R.id.tv_content);

        View viewById = findViewById(R.id.rl_zlInfo_banner);
        ViewPager viewById1 = (ViewPager) findViewById(R.id.vp_zlInfo_banner);
        new ShowBannerInfo(this, viewById, viewById1);
    }

    @Override
    protected void loadData() {
        MyProgressDialog.dialogShow(this);
        RequestParams p = new RequestParams();
        p.put("enews", "zhiliaoShow");
        p.put("sid", sid);
        H.TuKu(p, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String s = new String(responseBody);
                AppLog.redLog("ZiliaoDetailActivity.this", "" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int on = jsonObject.getInt("zt");
                    if (on == 1) {
                        String newstext = jsonObject.getString("newstext");
                        String newstime = jsonObject.getString("newstime");
                        String title = jsonObject.getString("title");
                        mTvContent.setText(Html.fromHtml(newstext));
                        mTvTitle.setText(title);
                        mTvTime.setText(DateFormatUtils.formatWithYMDHms(Long.parseLong(newstime) * 1000));
                    } else {
                        ToastUtil.toastShow(ZiliaoDetailActivity.this, "未找到内容~");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
                ToastUtil.toastShow(ZiliaoDetailActivity.this, "未找到内容~");
            }
        });
    }


}
