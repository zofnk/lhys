package com.lh16808.app.lhys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.adapter.HFDetailAdapter;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.model.ForumDetailModel;
import com.lh16808.app.lhys.model.HFModel;
import com.lh16808.app.lhys.other.MyProgressDialog;
import com.lh16808.app.lhys.utils.AppLog;
import com.lh16808.app.lhys.utils.ImageLoader;
import com.lh16808.app.lhys.utils.MyUtils;
import com.lh16808.app.lhys.utils.StatusBarCompat;
import com.lh16808.app.lhys.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ForumDetailActivity extends BaseActivity {

    private ForumDetailModel forumDetailModel;
    private TextView tvTime;
    private TextView tvFenShu;
    private TextView tvDengJi;
    private TextView tvName;
    private ImageView imgIco;
    private TextView tvTitle;
    private TextView tvText;
    private TextView tvHuifu;
    //    private FloatingActionButton mFloatingActionButton;
    private TextView tvCollect;
    private String forumModel_id;
    private static String TAG = ForumDetailActivity.class.getName();
    private ArrayList<HFModel> hfLists = new ArrayList<>();
    //    private WrapRecyclerView mRecyclerView;
//    private RecyclerView mRecyclerView;
    private HFDetailAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forum_detail);
        MyUtils.setStatusBarTranslucent(this);
        StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimary));
        iniUI();
        Intent intent = getIntent();
        if (intent != null) {
            forumModel_id = intent.getStringExtra("forumModel");
            if (!TextUtils.isEmpty(forumModel_id)) {
                initData();
            }
        }
    }

    @Override
    protected void loadData() {

    }


    private void iniUI() {
        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        findViewById(R.id.action_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyUtils.getUser(ForumDetailActivity.this)) {
                    Intent intent = new Intent(ForumDetailActivity.this, HuiFuActivity.class);
                    intent.putExtra("title", forumDetailModel.getTitle());
                    intent.putExtra("sid", forumDetailModel.getSid());
                    startActivity(intent);
                }
            }
        });
        findViewById(R.id.action_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtils.collect(ForumDetailActivity.this, forumModel_id, "1");
            }
        });
//        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
//        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//            }
//        });
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_forums_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ForumDetailActivity.this));
        mAdapter = new HFDetailAdapter();
        mRecyclerView.setAdapter(mAdapter);
        View itemView = getView();
        mAdapter.setHeaderView(itemView);
    }

    private void initData() {
        MyProgressDialog.dialogShow(this);
        RequestParams params = new RequestParams();
        params.add("enews", "bbsshow");
        params.add("sid", forumModel_id);
        H.ForumData(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String s = new String(responseBody);
                AppLog.redLog(TAG + "AAA", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
//                    int on = jsonObject.getInt("on");
                    int on = 0;
                    String sid = jsonObject.getString("sid");
                    String title = jsonObject.getString("title");
                    String userpic = jsonObject.getString("userpic");
                    String groupname = jsonObject.getString("groupname");
                    String w = jsonObject.getString("username");
                    String username = w.replaceAll("管家", "分析");
                    String userfen = jsonObject.getString("userfen");
                    String newstime = jsonObject.getString("newstime");
                    String ss = jsonObject.getString("newstext");
                    String newstext = ss.replaceAll("六合管家", "六合分析");
                    JSONArray hf = jsonObject.getJSONArray("hf");
                    for (int i = 0; i < hf.length(); i++) {
                        JSONObject jsonObject1 = hf.getJSONObject(i);
                        String hfid = jsonObject1.getString("hfid");
                        String hfusername = jsonObject1.getString("hfusername");
                        String hfnewstime = jsonObject1.getString("hfnewstime");
                        String hftext = jsonObject1.getString("hftext");
                        hfLists.add(new HFModel(hfid, hfusername, hfnewstime, hftext));
                    }
                    forumDetailModel = new ForumDetailModel(on, sid, newstime, newstext, title, userpic, groupname, username, userfen);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (forumDetailModel != null) {
                    tvText.setText(Html.fromHtml(forumDetailModel.getNewstext()));
                    tvName.setText(forumDetailModel.getUsername());
                    tvTime.setText(forumDetailModel.getNewstime());
                    if (!ForumDetailActivity.this.isFinishing())
                        ImageLoader.LoaderNetHead(ForumDetailActivity.this, forumDetailModel.getUserpic(), imgIco);
                    tvFenShu.setText(forumDetailModel.getUserfen());
                    tvDengJi.setText(forumDetailModel.getGroupname());
                    tvTitle.setText(forumDetailModel.getTitle());
                    tvHuifu.setText(hfLists.size() + "");
                    if (mAdapter != null) {
                        mAdapter.addDatas(hfLists);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
            }
        });
    }

    @NonNull
    private View getView() {
        View itemView = LayoutInflater.from(ForumDetailActivity.this).inflate(R.layout.content_main2, null, false);
        tvCollect = (TextView) itemView.findViewById(R.id.tv_forum_collect);
        tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtils.collect(ForumDetailActivity.this, forumModel_id, "1");
            }
        });
        tvTime = (TextView) itemView.findViewById(R.id.tv_forum_time);
        tvFenShu = (TextView) itemView.findViewById(R.id.tv_forum_fenShu);
        tvDengJi = (TextView) itemView.findViewById(R.id.tv_forum_dengJi);
        tvName = (TextView) itemView.findViewById(R.id.tv_forum_name);
        imgIco = (ImageView) itemView.findViewById(R.id.img_forum_ico);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_forumDetail_title);
        tvText = (TextView) itemView.findViewById(R.id.tv_forumDetail_text);
        tvHuifu = (TextView) itemView.findViewById(R.id.tv_forum_huiFu);
        return itemView;
    }
}
