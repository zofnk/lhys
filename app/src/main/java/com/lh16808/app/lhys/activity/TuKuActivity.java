package com.lh16808.app.lhys.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.fragment.CategoryFragment;
import com.lh16808.app.lhys.marco.Constants;
import com.lh16808.app.lhys.other.ShowBannerInfo;

public class TuKuActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ShowBannerInfo mShowBannerInfo;
    private PagerSlidingTabStrip tabs;
    String[] TITLES = Constants.TITLES_TUKU;

    @Override
    protected void initLoadData() {

    }

    @Override
    protected void initVariables() {
        View rlBanner = findViewById(R.id.rl_tuKu_banner);
        ViewPager vpBanner = (ViewPager) findViewById(R.id.vp_tuKu_banner);
        mShowBannerInfo = new ShowBannerInfo(this, rlBanner, vpBanner);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tu_ku);
        ViewPager pager = (ViewPager) findViewById(R.id.vp_tuKu);
        FragmentManager fm = getSupportFragmentManager();
        pager.setAdapter(new MyPageAdapter(fm));
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_tuKu);
        tabs.setViewPager(pager);
    }

    private final class MyPageAdapter extends FragmentPagerAdapter {
        private MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int arg0) {
            return new CategoryFragment(arg0);
        }
    }

    @Override
    protected void loadData() {

    }
}
