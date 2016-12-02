package com.lh16808.app.lhys;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v8.renderscript.Matrix2f;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.lh16808.app.lhys.fragment.FindFragment;
import com.lh16808.app.lhys.fragment.HomeFragment;
import com.lh16808.app.lhys.fragment.ResultFragment;
import com.lh16808.app.lhys.service.LottoService;
import com.lh16808.app.lhys.utils.AppLog;
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

    private HomeFragment mHomeFragment = HomeFragment.newInstance();
    private FindFragment mFindFragment = FindFragment.newInstance();
    private ResultFragment mResultFragment = ResultFragment.newInstance();
    private Fragment[] mFragments = new Fragment[]{mHomeFragment, mFindFragment, mResultFragment};
    private String[] mTitle = new String[]{"首页", "论坛", "娱乐"};
    private boolean[] fragmentsUpdateFlag = {false, false, false, false};
    private Intent mService;
    public static MainActivity mContext;

    public static MainActivity getInstance() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
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

        mService = new Intent(this, LottoService.class);
        startService(mService);
    }

    @Override
    protected void onStart() {
        mService = new Intent(this, LottoService.class);
        bindService(mService, mConn, Context.BIND_AUTO_CREATE);
        super.onStart();
    }

    private LottoService.LottoBinder mBinder;
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (LottoService.LottoBinder) service;
            mBinder.lunXun();
            mBinder.setOnVideoPlayPosition(new LottoService.OnVideoPlayPosition() {
                @Override
                public void sendPosition(int a, String zm, String sx) {
                    AppLog.redLog("Tag", "" + "a:" + a + "-zm:" + zm + "-sx:" + sx);
                }

                @Override
                public void sendZT(int zt) {
                    if (MainActivity.this.zt != null) {
                        MainActivity.this.zt.loadzt(zt);
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    LoadZT zt;

    public interface LoadZT {
        void loadzt(int zt);
    }

    public void setLoadzt(LoadZT zt) {
        this.zt = zt;
    }


    @Override
    protected void onStop() {
        unbindService(mConn);
        super.onStop();
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

    class ViewPagerAdapter extends FragmentPagerAdapter {

        FragmentManager fm;

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            fm = fragmentManager;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = mFragments[position % mFragments.length];
            Log.i("Tag", "getItem:position=" + position + ",fragment:"
                    + fragment.getClass().getName() + ",fragment.tag="
                    + fragment.getTag());
            return mFragments[position % mFragments.length];
//            return Page.values()[position].getFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //得到缓存的fragment
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            //得到tag，这点很重要
            String fragmentTag = fragment.getTag();
            if (fragmentsUpdateFlag[position % fragmentsUpdateFlag.length]) {
                //如果这个fragment需要更新
                FragmentTransaction ft = fm.beginTransaction();
                //移除旧的fragment
                ft.remove(fragment);
                //换成新的fragment
                fragment = mFragments[position % mFragments.length];
                //添加新fragment时必须用前面获得的tag，这点很重要
                ft.add(container.getId(), fragment, fragmentTag);
                ft.attach(fragment);
                ft.commit();
                //复位更新标志
                fragmentsUpdateFlag[position % fragmentsUpdateFlag.length] = false;
            }
            return fragment;
        }
    }

    enum Page {
        FIRST("首页") {
            @Override
            Fragment getFragment() {
                return HomeFragment.newInstance();
            }
        },
        SECOND("发现") {
            @Override
            Fragment getFragment() {
                return FindFragment.newInstance();
            }
        },
        THIRD("发奖") {
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
