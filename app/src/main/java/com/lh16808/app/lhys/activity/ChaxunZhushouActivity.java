package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.model.ZyBean;
import com.lh16808.app.lhys.other.ShowBannerInfo;
import com.lh16808.app.lhys.utils.ConstantsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/10/14.
 */
public class ChaxunZhushouActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ChaxunZhushouActivity.class);
        context.startActivity(starter);
    }
    public RecyclerView mRecyclerView;
    public ChaxunZhushouAdapter mAdapter;
    List<ZyBean> mDatas = new ArrayList<>();
    @Override
    protected void initLoadData() {

    }
    @Override
    protected void initVariables() {

    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.lhc_cxzs_activity);

        View rlBanner = findViewById(R.id.rl_cxzs_banner);
        ViewPager vpBanner = (ViewPager) findViewById(R.id.vp_cxzs_banner);
        new ShowBannerInfo(this, rlBanner, vpBanner);

        setActionbar();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        ZyBean zyBean = new ZyBean();
        zyBean.setTitle("紀錄條件查詢");
        zyBean.setData("開獎紀錄按條件查询");
        zyBean.setImage(R.mipmap.lhc_tjcx);
        zyBean.setTzActivity(TiaoJianChaxunActivity.class);
        mDatas.add(zyBean);
        zyBean = new ZyBean();
        zyBean.setTitle("冷熱查詢");
        zyBean.setData("冷熱號碼查詢");
        zyBean.setImage(R.mipmap.lhc_lrhm);
        zyBean.setTzActivity(LengReActivity.class);
        mDatas.add(zyBean);
        zyBean = new ZyBean();
        zyBean.setTitle("六合常識");
        zyBean.setData("六合常識查詢");
        zyBean.setImage(R.mipmap.lhc_lhcs);
        zyBean.setTzActivity(ChangshiActivity.class);
        mDatas.add(zyBean);

        mAdapter = new ChaxunZhushouAdapter(this, mDatas, new ChaxunZhushouAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent();
                intent.setClass(ChaxunZhushouActivity.this, mDatas.get(postion).getTzActivity());
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager lm=new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setActionbar() {
        TextView toolBarTitle = (TextView) findViewById(R.id.tool_bar_title);
        if (toolBarTitle != null) {
            toolBarTitle.setText(getResources().getString(R.string.lhc_chaxunzhushou));
        }
    }


    @Override
    protected void loadData() {

    }
}
