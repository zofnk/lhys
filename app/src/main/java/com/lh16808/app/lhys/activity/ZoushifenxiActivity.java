package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.reflect.TypeToken;
import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.base.BaseFragment;
import com.lh16808.app.lhys.fragment.BszsFragment;
import com.lh16808.app.lhys.fragment.DszsFragment;
import com.lh16808.app.lhys.fragment.DwzsFragment;
import com.lh16808.app.lhys.fragment.SxzsFragment;
import com.lh16808.app.lhys.fragment.TszsFragment;
import com.lh16808.app.lhys.fragment.WszsFragment;
import com.lh16808.app.lhys.fragment.WxzsFragment;
import com.lh16808.app.lhys.model.HistoryRecordBean;
import com.lh16808.app.lhys.other.ProgressDialogMy;
import com.lh16808.app.lhys.other.ShowBannerInfo;
import com.lh16808.app.lhys.other.YearPopupWindow;
import com.lh16808.app.lhys.utils.AsyncHttpClientUtil;
import com.lh16808.app.lhys.utils.ConstantsUtil;
import com.lh16808.app.lhys.utils.DateTimeUtil;
import com.lh16808.app.lhys.utils.GsonUtil;
import com.lh16808.app.lhys.utils.ShowBanner;
import com.lh16808.app.lhys.utils.dataUtils.ToastUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.lh16808.app.lhys.MainActivity.mContext;
import static com.lh16808.app.lhys.R.id.view;


public class ZoushifenxiActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ZoushifenxiActivity.class);
        context.startActivity(starter);
    }

    private PagerSlidingTabStrip tabs;
    String[] classids = {"生肖走勢", "波色走勢", "單雙走勢", "段位走勢",
            "頭數走勢", "尾數走勢", "五行走勢"};
    MyPageAdapter myPageAdapter;
    private ShowBanner mShowBanner;
    public CardView cv_reload;
    public Button mBtnReload;

    List<Fragment> fragmentList = new ArrayList<>();
    //选择的Fragment
    int pos = 0;
    //查询数据状态
    public int chaxun = 0;
    public ProgressDialogMy mPro;
    //查询年份
    public int curyear = DateTimeUtil.GetYear();
    public List<HistoryRecordBean> mDatas = new ArrayList<>();


    YearPopupWindow yearPopupWindow;

    @Override
    protected void initLoadData() {

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.lhc_zsfx_activity);

        mPro = new ProgressDialogMy(this);
        yearPopupWindow = new YearPopupWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(yearPopupWindow.year);
            }
        });

        View rlBanner = findViewById(R.id.rl_zoushi_banner);
        ViewPager vpBanner = (ViewPager) findViewById(R.id.vp_zoushi_banner);
        new ShowBannerInfo(this, rlBanner, vpBanner, 1);

        //标题栏
        setActionbar();

        fragmentList.add(new SxzsFragment());
        fragmentList.add(new BszsFragment());
        fragmentList.add(new DszsFragment());
        fragmentList.add(new DwzsFragment());
        fragmentList.add(new TszsFragment());
        fragmentList.add(new WszsFragment());
        fragmentList.add(new WxzsFragment());

        ViewPager pager = (ViewPager) findViewById(R.id.vp_1);
        FragmentManager fm = getSupportFragmentManager();
        myPageAdapter = new MyPageAdapter(fm);
        pager.setAdapter(myPageAdapter);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_1);
        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //重新加载
        cv_reload = (CardView) findViewById(R.id.cv_reload);
        mBtnReload = (Button) findViewById(R.id.btn_reload);
        mBtnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(curyear);
            }
        });


        getData(curyear);
    }

    @Override
    protected void loadData() {

    }

    TextView tv_share;

    //标题栏
    private void setActionbar() {
        TextView toolBarTitle = (TextView) findViewById(R.id.tool_bar_title);
        if (toolBarTitle != null) {
            toolBarTitle.setText(getResources().getString(R.string.lhc_zsfx));
        }
        tv_share = (TextView) findViewById(R.id.tv_share);
        tv_share.setText("選擇年份");
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yearPopupWindow.showPopupWindow(curyear);
            }
        });
    }

    private final class MyPageAdapter extends FragmentPagerAdapter {
        private MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return classids[position];
        }

        @Override
        public int getCount() {
            return classids.length;
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragmentList.get(arg0);
        }
    }

    //按年查询开奖数据
    public void getData(final int year) {
        chaxun = 0;
        mPro.showpop();
        AsyncHttpClientUtil.getInstance().get(ConstantsUtil.HISTORY_RECORD(year + ""), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                curyear = year;
                tv_share.setText("選" + curyear + "年");
                String response = new String(responseBody);
                response = ConstantsUtil.replaceAll(response);
                // json转为带泛型的list
                List<HistoryRecordBean> mList = GsonUtil.buildGson().fromJson(response,
                        new TypeToken<List<HistoryRecordBean>>() {
                        }.getType());

                if (mList.size() > 0) {
                    mDatas.clear();
                    mDatas.addAll(mList);
                }
                mPro.cancel();
                chaxun = 1;
                if (mDatas.size() == 0) {
                    mBtnReload.setText(year + "年數據還沒有，點擊加載" + (year - 1) + "數據");
                    curyear = year - 1;
                    cv_reload.setVisibility(View.VISIBLE);
                } else {
                    cv_reload.setVisibility(View.GONE);
                    setData();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mPro.cancel();
                chaxun = 2;
                ToastUtil.onFailure(ZoushifenxiActivity.this, statusCode);
                if (statusCode == 404) {
                    if (mDatas.size() == 0) {
                        mBtnReload.setText(year + "年數據還沒有，點擊加載" + (year - 1) + "數據");
                        curyear = year - 1;
                        cv_reload.setVisibility(View.VISIBLE);
                    } else {
                        cv_reload.setVisibility(View.GONE);
                    }
                } else {
                    if (mDatas.size() == 0) {
                        mBtnReload.setText(year + "年數據加載失敗，點擊重新加載");
                        cv_reload.setVisibility(View.VISIBLE);
                    } else {
                        cv_reload.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    //查询历史开奖数据后设置数据
    public void setData() {
        //当前页
        ((BaseFragment) fragmentList.get(pos)).loadData();
        //后一页
        if (pos + 1 < fragmentList.size()) {
            ((BaseFragment) fragmentList.get(pos + 1)).loadData();
        }
        //前一页
        if (pos - 1 > -1) {
            ((BaseFragment) fragmentList.get(pos - 1)).loadData();
        }
    }

}
