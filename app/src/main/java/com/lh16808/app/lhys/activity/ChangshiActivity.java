package com.lh16808.app.lhys.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.fragment.BshmFragment;
import com.lh16808.app.lhys.fragment.SxhmFragment;
import com.lh16808.app.lhys.fragment.WxhmFragment;
import com.lh16808.app.lhys.utils.ConstantsUtil;


public class ChangshiActivity extends BaseActivity {
    private PagerSlidingTabStrip tabs;
    String[] classids = {"生肖號碼", "波色號碼", "五行號碼"};

    @Override
    protected void initLoadData() {

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.lhc_cs_activity);

        //标题栏
        setActionbar();

        ViewPager pager = (ViewPager) findViewById(R.id.vp_1);
        FragmentManager fm = getSupportFragmentManager();
        pager.setAdapter(new MyPageAdapter(fm));
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_1);
        tabs.setViewPager(pager);
    }

    @Override
    protected void loadData() {

    }

    private void setActionbar() {
        TextView toolBarTitle = (TextView) findViewById(R.id.tool_bar_title);
        if (toolBarTitle != null) {
            toolBarTitle.setText(getResources().getString(R.string.lhc_lhcs));
        }
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
            Fragment fragment = null;
            if (arg0 == 0) {
                fragment = new SxhmFragment();
            } else if (arg0 == 1) {
                fragment = new BshmFragment();
            } else if (arg0 == 2) {
                fragment = new WxhmFragment();
            }
            return fragment;
        }
    }
}
