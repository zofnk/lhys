package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.adapter.ListBBSAdapter;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.marco.ApiConfig;
import com.lh16808.app.lhys.model.CollectModel;
import com.lh16808.app.lhys.model.User;
import com.lh16808.app.lhys.other.MyProgressDialog;
import com.lh16808.app.lhys.utils.AppLog;
import com.lh16808.app.lhys.utils.ToastUtil;
import com.lh16808.app.lhys.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NoteListActivity extends BaseActivity {

    private User mUser;
    private RadioGroup mRPCollect;
    private RadioButton mRBCollect1;
    private RadioButton mRBCollect2;
    int checkedId = R.id.rb_note_se1;
    private RecyclerView mRecyclerView;
    String urlValue = "";
    ArrayList<CollectModel> listCollect = new ArrayList<>();
    private ListBBSAdapter mAdapter;
    private int showType;
    private View mNotData;
    private static final String KEY_TITLE = "title";

    @Override
    protected void initVariables() {

    }

    public static void start(Context context ,int title) {
        Intent starter = new Intent(context, NoteListActivity.class);
        starter.putExtra(KEY_TITLE,title);
        context.startActivity(starter);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_note_list);

        Intent intent = getIntent();
        showType = intent.getIntExtra("title", -1);


        mUser = User.getUser();

        mNotData = findViewById(R.id.tv_note_neirong);
        mRPCollect = (RadioGroup) findViewById(R.id.rp_note_se);
        mRBCollect1 = (RadioButton) findViewById(R.id.rb_note_se1);
        mRBCollect2 = (RadioButton) findViewById(R.id.rb_note_se2);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_note_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ListBBSAdapter(this, listCollect, new ListBBSAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                CollectModel collectModel = listCollect.get(position);
                if (checkedId == R.id.rb_note_se2) {
                    Intent intent = new Intent();
                    intent.setClass(NoteListActivity.this, ForumDetailActivity.class);
                    intent.putExtra("forumModel", collectModel.getTid());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(NoteListActivity.this, FaTeiActivity.class);
                    intent.putExtra("sid", collectModel.getSid());
                    intent.putExtra("title", collectModel.getBt());
                    intent.putExtra("showType", showType);
                    startActivity(intent);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRPCollect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                listCollect.clear();
                mAdapter.notifyDataSetChanged();
                NoteListActivity.this.checkedId = checkedId;
                initData(showType);
            }
        });
        initData(showType);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onStart() {
        if (TextUtils.isEmpty(User.getUser().getHym())) {
            finish();
        }
        super.onStart();
    }

    private void initData(int i) {
        if (checkedId == R.id.rb_note_se1) {
            //weishenhe
            urlValue = ApiConfig.MYFAVA2[i];
        } else {
            urlValue = ApiConfig.MYFAVA1[i];
        }
        if (TextUtils.isEmpty(urlValue)) {
            ToastUtil.toastShow(this, "出错~");
            return;
        }
        mRBCollect1.setEnabled(false);
        mRBCollect2.setEnabled(false);
        MyProgressDialog.dialogShow(this);
        RequestParams params = new RequestParams();
        params.put("enews", urlValue);
        params.put("uid", mUser.getUserid());
        params.put("uname", mUser.getHym());
        params.put("rnd", mUser.getRnd());
        H.ForumData(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (showType != 0) {
                    mRBCollect1.setEnabled(true);
                    mRBCollect2.setEnabled(true);
                }
                MyProgressDialog.dialogHide();
                String s = new String(responseBody);
                AppLog.redLog("TTTTT", s);
                String tid = "";
                if (s.length() > 10) {
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String sid = jsonObject.getString("sid");
                            String bt = jsonObject.getString("bt");
                            String lj = jsonObject.getString("lj");
                            if (showType != 1) {
                                tid = jsonObject.getString("tid");
                            }
                            String classid = jsonObject.getString("classid");
                            CollectModel collectModel = new CollectModel(sid, bt, lj, tid, classid);
                            listCollect.add(collectModel);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (listCollect.size() == 0) {
                    if (mNotData.getVisibility() != View.VISIBLE) {
                        mNotData.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }
                } else if (mAdapter != null) {
                    if (mRecyclerView.getVisibility() != View.VISIBLE) {
                        mNotData.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                    mAdapter.notifyDataSetChanged();
                }
                ToastUtil.toastShow(NoteListActivity.this, "" + listCollect.size());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (showType != 0) {
                    mRBCollect1.setEnabled(true);
                    mRBCollect2.setEnabled(true);
                }
                MyProgressDialog.dialogHide();
                boolean destroyed = NoteListActivity.this.isFinishing();
                ToastUtil.toastShow(NoteListActivity.this, "網絡錯誤~");
            }
        });
    }
}
