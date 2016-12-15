package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.adapter.ZiliaoAdapter;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.model.CateDetailModel;
import com.lh16808.app.lhys.other.MyProgressDialog;
import com.lh16808.app.lhys.other.ShowBannerInfo;
import com.lh16808.app.lhys.utils.AppLog;
import com.lh16808.app.lhys.utils.ToastUtil;
import com.lh16808.app.lhys.utils.http.H;
import com.lh16808.app.lhys.widget.MyLinearLayoutManager;
import com.lh16808.app.lhys.widget.RecyclerViewDecoration;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NatureActivity extends BaseActivity {

    private View view;
    private int arg0;
    private RecyclerView mRecyclerView;
    private int star;
    private ArrayList<CateDetailModel> list = new ArrayList<>();
    private ArrayList<CateDetailModel> morelist = new ArrayList<>();
    private ZiliaoAdapter mAdapter;
    private MyLinearLayoutManager mLayoutManager;
    private FragmentActivity mActivity;
    private boolean isLoad;
    private ShowBannerInfo mShowBannerInfo;

    @Override
    protected void initLoadData() {

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, NatureActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initVariables() {
        View rlBanner = findViewById(R.id.rl_nature_banner);
        ViewPager vpBanner = (ViewPager) findViewById(R.id.vp_nature_banner);
        mShowBannerInfo = new ShowBannerInfo(this, rlBanner, vpBanner);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_nature);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_nature_list);
        mActivity = this;
        initAdapter();
    }


    private void initAdapter() {
        mLayoutManager = new MyLinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerViewDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mAdapter = new ZiliaoAdapter(NatureActivity.this, list, new ZiliaoAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mActivity, ZiliaoDetailActivity.class);
                intent.putExtra("CateDetailModel", list.get(position).getUrl());
                mActivity.startActivity(intent);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(-1)) {
                    Log.e("-------onScrolled", "onScrolledToTop()");
                } else if (!recyclerView.canScrollVertically(1)) {
                    Log.e("-------onScrolled", "onScrolledToBottom()");
//                    ToastUtil.toastShow(mActivity, "上拉可加载更多...");
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }

    private void getData() {
        MyProgressDialog.dialogShow(this);
        H.ZILIAO(7, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String json = new String(responseBody);
                AppLog.redLog("x32", "" + json);
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    int length = jsonArray.length();
                    if (length > 0) {
                        for (int i = 0; i < length; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String url = jsonObject.getString("sid");
                            String title = jsonObject.getString("title");
                            String newstime = jsonObject.getString("newstime");
                            CateDetailModel cateModel = new CateDetailModel(title, url, newstime);
                            list.add(cateModel);
                        }
                    } else {
                        ToastUtil.toastShow(NatureActivity.this, "数据已结束");
                    }
                } catch (JSONException e) {
                    ToastUtil.toastShow(NatureActivity.this, "加载失败~");
                    e.printStackTrace();
                }
                ToastUtil.toastShow(NatureActivity.this, "加载完成~");
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
                ToastUtil.toastShow(NatureActivity.this,getString(R.string.net_error));
                findViewById(R.id.data).setVisibility(View.GONE);
                findViewById(R.id.ll_error).setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void loadData() {
        getData();
    }
}
