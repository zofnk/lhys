package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.marco.Constants;
import com.lh16808.app.lhys.other.ShowBannerInfo;
import com.lh16808.app.lhys.utils.MyUtils;


public class TypeActivity extends BaseActivity {

    private ShowBannerInfo mShowBannerInfo;
    private RecyclerView mRecyclerView;
    private String[] caiUrl;
    private TypeAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, TypeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initLoadData() {
        caiUrl = MyUtils.getType();

    }

    @Override
    protected void initVariables() {
        View rlBanner = findViewById(R.id.rl_type_banner);
        ViewPager vpBanner = (ViewPager) findViewById(R.id.vp_type_banner);
        mShowBannerInfo = new ShowBannerInfo(this, rlBanner, vpBanner);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_type);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new TypeAdapter(this, new TypeAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent s = new Intent(TypeActivity.this, LottoActivity.class);
                s.putExtra("web_title", Constants.caipiaotitle[position]);
                s.putExtra("web_key", caiUrl[position]);
                startActivity(s);

            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_type);
    }

    @Override
    protected void loadData() {

    }
}
