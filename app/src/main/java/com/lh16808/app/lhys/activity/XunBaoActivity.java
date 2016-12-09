package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.model.BannerUrl;
import com.lh16808.app.lhys.utils.ImageLoader;
import com.lh16808.app.lhys.utils.ScreenUtils;
import com.lh16808.app.lhys.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class XunBaoActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    ArrayList<BannerUrl> list = new ArrayList<>();
    private MyAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, XunBaoActivity.class);
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
        setContentView(R.layout.activity_xun_bao);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_xunBao);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        H.adBanner(1, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String arg0 = new String(responseBody);
                try {
                    JSONArray jsonArray = new JSONArray(arg0);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        String titlepic = jsonObject.getString("titlepic");
                        BannerUrl bannerUrl = new BannerUrl();
                        bannerUrl.setTitle(title);
                        bannerUrl.setTitlepic(titlepic);
                        list.add(bannerUrl);
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

            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

        @Override
        public MyAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new MyHolder(LayoutInflater.from(XunBaoActivity.this).inflate(R.layout.item_xunbao, parent, false));
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyHolder holder, final int position) {
            ImageLoader.LoaderNet2(XunBaoActivity.this, list.get(position).getTitlepic(), holder.iv);
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String titlepic = list.get(position).getTitle();
                    if (!TextUtils.isEmpty(titlepic) && !"#".equals(titlepic)) {
                        // 调用浏览器
                        Uri webViewUri = Uri.parse(titlepic);
                        Intent intent = new Intent(Intent.ACTION_VIEW, webViewUri);
                        XunBaoActivity.this.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            ImageView iv;

            public MyHolder(View itemView) {
                super(itemView);
                iv = (ImageView) itemView.findViewById(R.id.iv_xunBao_item);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv.getLayoutParams();
                layoutParams.height = ScreenUtils.getScreenWidth(XunBaoActivity.this) * 75 / 410;
                iv.setLayoutParams(layoutParams);
            }
        }
    }
}
