package com.lh16808.app.lhys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.adapter.ForumAdapter;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.model.ForumModel;
import com.lh16808.app.lhys.other.ShowBannerInfo;
import com.lh16808.app.lhys.utils.AppLog;
import com.lh16808.app.lhys.utils.MyUtils;
import com.lh16808.app.lhys.utils.ToastUtil;
import com.lh16808.app.lhys.utils.http.H;
import com.lh16808.app.lhys.widget.MyLinearLayoutManager;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ForumActivity extends BaseActivity implements SwipyRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private ForumAdapter mAdapter;
    /**
     * 页数
     */
    private int yeShu = 30;
    private ArrayList<ForumModel> list = new ArrayList<ForumModel>();
//    private boolean oneload;
    /**
     * 正在加载中
     */
    private boolean loading;
    private boolean isFistStartActivity;
    private SwipyRefreshLayout srlForum;
    //    ArrayList<ForumModel> morelist = new ArrayList<>();
    private ShowBannerInfo mShowBannerInfo;

    @Override
    protected void initVariables() {
        View rlBanner = findViewById(R.id.rl_forum_banner);
        ViewPager vpBanner = (ViewPager) findViewById(R.id.vp_forum_banner);
        mShowBannerInfo = new ShowBannerInfo(this, rlBanner, vpBanner);
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyUtils.isFastDoubleClick()) {
                    return;
                }
                if (MyUtils.getUser(ForumActivity.this)) {
                    startActivity(new Intent(ForumActivity.this, FaTeiActivity.class));
                }
            }
        });
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forum);
//        initData1();
        initRecycView();
        initRefresh();
    }

    @Override
    protected void loadData() {

    }

    private void initRefresh() {
        srlForum = (SwipyRefreshLayout) findViewById(R.id.srl_forum);
        srlForum.setOnRefreshListener(this);
        autoRefresh(true);
    }

    private void initRecycView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_forum);
        //设置布局管理器
        mRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        //设置adapter
        mAdapter = new ForumAdapter(this, list);
        mAdapter.setOnItemClickLitener(new ForumAdapter.OnItemClickLitener() {

            @Override
            public void onItemClick(View view, int position) {
                if (list.size() > 0) {
                    Intent intent = new Intent(ForumActivity.this, ForumDetailActivity.class);
                    ForumModel forumModel = ForumActivity.this.list.get(position);
                    intent.putExtra("forumModel", forumModel.getId());
                    startActivity(intent);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(-1)) {
                    Log.e("-------onScrolled", "onScrolledToTop()");
                } else if (!recyclerView.canScrollVertically(1)) {
                    Log.e("-------onScrolled", "onScrolledToBottom()");
                    if (!loading && isFistStartActivity) {
                        autoRefresh(false);
                    }
                }
            }
        });
    }

    private void initData1() {
        loading = true;
        H.BBS(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                srlForum.setRefreshing(false);
                isFistStartActivity = true;
                String s = new String(responseBody);
                try {
                    JSONArray list = new JSONArray(s);
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonObject1 = list.getJSONObject(i);
                        String id = jsonObject1.getString("sid");
                        String x = jsonObject1.getString("title");
                        String title = x.replaceAll("管家", "分析");
                        String userpic = jsonObject1.getString("userpic");
                        String groupname = jsonObject1.getString("groupname");
                        String w = jsonObject1.getString("username");
                        String username = w.replaceAll("管家", "分析");
                        String userfen = jsonObject1.getString("userfen");
                        String newstime = jsonObject1.getString("newstime");
                        String onclick = jsonObject1.getString("onclick");
                        int rnum = jsonObject1.getInt("rnum");
                        ForumModel forumModel = new ForumModel(id, title, userpic, groupname, username, userfen, newstime, onclick, rnum);
                        ForumActivity.this.list.add(forumModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loading = false;
//                list.addAll(morelist);
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                ToastUtil.toastShow(ForumActivity.this, list.size() + "");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                loading = false;
                srlForum.setRefreshing(false);
                ToastUtil.toastShow(ForumActivity.this, "网络错误~");
            }
        });
    }

    private void initData() {
        loading = true;
        RequestParams params = new RequestParams();
        params.add("enews", "bbslist");
        params.add("star", String.valueOf(yeShu));
        H.ForumData(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                srlForum.setRefreshing(false);
                String s = new String(responseBody);
                try {
                    JSONArray list = new JSONArray(s);
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonObject1 = list.getJSONObject(i);
                        String id = jsonObject1.getString("sid");
                        String x = jsonObject1.getString("title");
                        String title = x.replaceAll("管家", "分析");
                        String userpic = jsonObject1.getString("userpic");
                        String groupname = jsonObject1.getString("groupname");
                        String w = jsonObject1.getString("username");
                        String username = w.replaceAll("管家", "分析");
                        String userfen = jsonObject1.getString("userfen");
                        String newstime = jsonObject1.getString("newstime");
                        String onclick = jsonObject1.getString("onclick");
                        int rnum = jsonObject1.getInt("rnum");
                        ForumModel forumModel = new ForumModel(id, title, userpic, groupname, username, userfen, newstime, onclick, rnum);
                        ForumActivity.this.list.add(forumModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loading = false;
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                AppLog.redLog("TTTTTTT", "list.size():" + list.size());
                ToastUtil.toastShow(ForumActivity.this, list.size() + "");
//                oneload = true;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                loading = false;
                srlForum.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP ? true : false) {
            yeShu = 30;
            ForumActivity.this.list.clear();
            mAdapter.notifyDataSetChanged();
            initData1();
        } else {
            if (list.size() > 0) {
                yeShu += 20;
                initData();
                ToastUtil.toastShow(ForumActivity.this, "加载中...");
            }
        }
    }

    private void autoRefresh(boolean b) {
        srlForum.post(new Runnable() {
            @Override
            public void run() {
                srlForum.setRefreshing(true);
            }
        });
        if (b) {
            this.onRefresh(SwipyRefreshLayoutDirection.TOP);
        } else {
            this.onRefresh(SwipyRefreshLayoutDirection.BOTTOM);
        }

    }
}
