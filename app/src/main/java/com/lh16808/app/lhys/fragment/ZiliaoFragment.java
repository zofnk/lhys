package com.lh16808.app.lhys.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.activity.ZiliaoDetailActivity;
import com.lh16808.app.lhys.adapter.ZiliaoAdapter;
import com.lh16808.app.lhys.marco.Constants;
import com.lh16808.app.lhys.model.CateDetailModel;
import com.lh16808.app.lhys.utils.ToastUtil;
import com.lh16808.app.lhys.utils.http.H;
import com.lh16808.app.lhys.widget.MyLinearLayoutManager;
import com.lh16808.app.lhys.widget.RecyclerViewDecoration;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZiliaoFragment extends Fragment implements SwipyRefreshLayout.OnRefreshListener {

    String[] classid = Constants.classid;
    private View view;
    private int arg0;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private RecyclerView mRecyclerView;
    private int star;
    private ArrayList<CateDetailModel> list = new ArrayList<>();
    private ArrayList<CateDetailModel> morelist = new ArrayList<>();
    private ZiliaoAdapter mAdapter;
    private MyLinearLayoutManager mLayoutManager;
    private FragmentActivity mActivity;
    private boolean isLoad;

    @SuppressLint("ValidFragment")
    public ZiliaoFragment(int arg0) {
        this.arg0 = arg0;
    }

    @SuppressLint("ValidFragment")
    public ZiliaoFragment() {

    }

    private void autoRefresh(boolean b) {
        mSwipyRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipyRefreshLayout.setRefreshing(true);
            }
        });
        if (b) {
            this.onRefresh(SwipyRefreshLayoutDirection.TOP);
        } else {
            this.onRefresh(SwipyRefreshLayoutDirection.BOTTOM);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            mActivity = getActivity();
            view = inflater.inflate(R.layout.fragment_ziliao, container, false);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_zl_list);
            mSwipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.srl_zl_refresh);
            initAdapter();
//            getData();
            iniData();
        }
        return view;
    }

    private void getData() {
        H.ZILIAO(arg0, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                mSwipyRefreshLayout.setRefreshing(false);
                String json = new String(responseBody);
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
                            morelist.add(cateModel);
                        }
                    } else {
                        ToastUtil.toastShow(getActivity(), "数据已结束");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (morelist.size() > 20) {
                    list.addAll(morelist.subList(0, 20));
                } else {
                    list.addAll(morelist);
                }
                ToastUtil.toastShow(getActivity(), "" + morelist.size());
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mSwipyRefreshLayout.setRefreshing(false);
                ToastUtil.toastShow(getActivity(), "网络错误~");
            }
        });
    }

    private void initAdapter() {
        mSwipyRefreshLayout.setOnRefreshListener(this);
        autoRefresh(true);
        mLayoutManager = new MyLinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerViewDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mAdapter = new ZiliaoAdapter(getActivity(), list, new ZiliaoAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mActivity, ZiliaoDetailActivity.class);
                intent.putExtra("CateDetailModel", list.get(position).getUrl());
                intent.putExtra("classid", classid[ZiliaoFragment.this.arg0]);
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
                    ToastUtil.toastShow(mActivity, "上拉可加载更多...");
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }

    private void iniData() {
        isLoad = true;
        RequestParams params = new RequestParams();
        params.add("enews", "zhiliaolist");
        params.add("classid", classid[arg0]);
        params.add("star", String.valueOf(star));
        H.TuKu(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                mSwipyRefreshLayout.setRefreshing(false);
                String json = new String(responseBody);
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
                        ToastUtil.toastShow(getActivity(), "数据已结束");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mSwipyRefreshLayout.setRefreshing(false);
                ToastUtil.toastShow(getActivity(), "网络错误~");
            }
        });
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            ToastUtil.toastShow(mActivity, "加载中...");
            list.clear();
            star = 0;
            iniData();
//            add();
        } else {
            ToastUtil.toastShow(mActivity, "加载中...");
            star += 20;
            iniData();
//            add();s
        }
    }

//
//    private void add() {
//        int toIndex = star + 20;
//        if (morelist.size() != 0) {
//            if (toIndex < morelist.size()) {
//                final List<CateDetailModel> forumModels = morelist.subList(star, toIndex);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        list.addAll(forumModels);
//                        if (mAdapter != null) {
//                            mAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }, 1500);
//            } else {
//                ToastUtil.toastShow(getActivity(), "加载已到底部...");
//            }
//        }
//    }

}
