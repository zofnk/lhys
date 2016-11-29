package com.lh16808.app.lhys;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lh16808.app.lhys.fragment.FindFragment;
import com.lh16808.app.lhys.fragment.HomeFragment;
import com.lh16808.app.lhys.fragment.ResultFragment;
import com.lh16808.app.lhys.widget.blurview.BlurView;
import com.lh16808.app.lhys.widget.blurview.RenderScriptBlur;
import com.mxn.soul.flowingdrawer_core.FlowingView;
import com.mxn.soul.flowingdrawer_core.LeftDrawerLayout;

import static com.lh16808.app.lhys.R.id.blurView;
import static com.lh16808.app.lhys.R.id.tabLayout;
import static com.lh16808.app.lhys.R.id.viewPager;


public class MainActivity extends AppCompatActivity {

    private LeftDrawerLayout mLeftDrawerLayout;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private BlurView mBlurView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();

        mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.id_drawerlayout);

        mViewPager = (ViewPager) findViewById(viewPager);
        mTabLayout = (TabLayout) findViewById(tabLayout);
        mBlurView = (BlurView) findViewById(blurView);
        setupViewPager();
        setupBlurView();

        FragmentManager fm = getSupportFragmentManager();
        MyMenuFragment mMenuFragment = (MyMenuFragment) fm.findFragmentById(R.id.id_container_menu);
        FlowingView mFlowingView = (FlowingView) findViewById(R.id.sv);
        if (mMenuFragment == null) {
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment = new MyMenuFragment()).commit();
        }
        mLeftDrawerLayout.setFluidView(mFlowingView);
        mLeftDrawerLayout.setMenuFragment(mMenuFragment);
        setupFeed();

    }

    private void setupViewPager() {
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupBlurView() {
        final float radius = 16f;

        final View decorView = getWindow().getDecorView();
        //Activity's root View. Can also be root View of your layout
        final View rootView = decorView.findViewById(android.R.id.content);
        //set background, if your root layout doesn't have one
        final Drawable windowBackground = decorView.getBackground();

        mBlurView.setupWith(rootView)
                .windowBackground(windowBackground)
                .blurAlgorithm(new RenderScriptBlur(this, true)) //Optional, enabled by default. User can have custom implementation
                .blurRadius(radius);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return Page.values()[position].getFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Page.values()[position].getTitle();
        }

        @Override
        public int getCount() {
            return Page.values().length;
        }
    }

    enum Page {
        FIRST("Tab1") {
            @Override
            Fragment getFragment() {
                return HomeFragment.newInstance();
            }
        },
        SECOND("Tab2") {
            @Override
            Fragment getFragment() {
                return FindFragment.newInstance();
            }
        },
        THIRD("Tab3") {
            @Override
            Fragment getFragment() {
                return ResultFragment.newInstance();
            }
        };

        private String title;

        Page(String title) {
            this.title = title;
        }

        String getTitle() {
            return title;
        }

        abstract Fragment getFragment();
    }

    protected void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftDrawerLayout.toggle();
            }
        });
    }


    private void setupFeed() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
    }

    @Override
    public void onBackPressed() {
        if (mLeftDrawerLayout.isShownMenu()) {
            mLeftDrawerLayout.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
