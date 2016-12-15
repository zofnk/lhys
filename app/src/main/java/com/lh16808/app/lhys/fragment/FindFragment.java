package com.lh16808.app.lhys.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.activity.FaTeiActivity;
import com.lh16808.app.lhys.activity.ForumDetailActivity;
import com.lh16808.app.lhys.adapter.ForumAdapter;
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
import com.sunfusheng.marqueeview.MarqueeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment implements View.OnClickListener,SwipyRefreshLayout.OnRefreshListener{

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

    public static FindFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FindFragment fragment = new FindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FindFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        initRecycView(view);
        initRefresh(view);
        FloatingActionButton faButton = (FloatingActionButton) view.findViewById(R.id.fabFind);
        faButton.setOnClickListener(this);
        return view;
    }
    private void initRefresh(View view) {
        srlForum = (SwipyRefreshLayout) view.findViewById(R.id.srl_forum);
        srlForum.setOnRefreshListener(this);
        autoRefresh(true);
    }
    private void initRecycView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_forum);
        //设置布局管理器
        mRecyclerView.setLayoutManager(new MyLinearLayoutManager(getContext()));
        //设置adapter
        mAdapter = new ForumAdapter(getContext(), list);
        mAdapter.setOnItemClickLitener(new ForumAdapter.OnItemClickLitener() {

            @Override
            public void onItemClick(View view, int position) {
                if (list.size() > 0) {
                    Intent intent = new Intent(getActivity(), ForumDetailActivity.class);
                    ForumModel forumModel = list.get(position);
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
    private void    autoRefresh(boolean b) {
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

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP ? true : false) {
            yeShu = 30;
            list.clear();
            mAdapter.notifyDataSetChanged();
            initData1();
        } else {
            if (list.size() > 0) {
                yeShu += 20;
                initData();
                ToastUtil.toastShow(getContext(), "加载中...");
            }
        }
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
                        FindFragment.this.list.add(forumModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loading = false;
//                list.addAll(morelist);
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                ToastUtil.toastShow(getContext(), list.size() + "");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                loading = false;
                srlForum.setRefreshing(false);
                ToastUtil.toastShow(getContext(), "网络错误~");
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
                        FindFragment.this.list.add(forumModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loading = false;
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                AppLog.redLog("TTTTTTT", "list.size():" + list.size());
                ToastUtil.toastShow(getContext(), list.size() + "");
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabFind:
                if (MyUtils.isFastDoubleClick()) {
                    return;
                }
                if (MyUtils.getUser(getContext())) {
                    startActivity(new Intent(getActivity(), FaTeiActivity.class));
                }
                break;
        }
    }
}
