package com.lh16808.app.lhys.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.model.BannerData;
import com.lh16808.app.lhys.model.BannerModel;
import com.lh16808.app.lhys.model.BannerUrl;
import com.lh16808.app.lhys.utils.http.AsyncHttpClientUtils;
import com.lh16808.app.lhys.utils.http.ConnectionUtils;
import com.lh16808.app.lhys.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/9/20.
 */
public class ShowBanner {
    private ArrayList<BannerModel> listimg = new ArrayList<>();
    private ArrayList<View> dots = new ArrayList<View>(); // 图片标题正文的那些点
    private Context context;
    private View mRL;
    private ViewPager mViewPager;
    private LinearLayout mPointContainer;
    private int bannerMax = 100000;
    private int type;
    public boolean isRemove;

    public ArrayList<BannerModel> getListimg() {
        return listimg;
    }

    public void removeAction() {
        if (listimg.size() > 1) {
            handler.removeCallbacksAndMessages(null);
            isRemove = true;
        }
    }

    public void runAction() {
        if (listimg.size() > 1 && isRemove) {
            handler.sendEmptyMessageDelayed(ROLL_PAGE, TIME);
            isRemove = false;
        }
    }

    public ShowBanner show(Context context, int type, View rl, LinearLayout ll, ViewPager vp) {
        this.type = type;
        this.context = context;
        this.mRL = rl;
        this.mViewPager = vp;
        this.mPointContainer = ll;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mViewPager.getLayoutParams();
        layoutParams.height = ScreenUtils.getScreenWidth(context) * 170 / 410;
        mViewPager.setLayoutParams(layoutParams);
//        if (ConnectionUtils.isConnect(context)) {
//            getBannerInfo();
//        }
        return ShowBanner.this;
    }

    /**
     * 获取广告信息
     */
    public void getBannerInfo() {
        H.AD(type, new NetUtil.LoadMainADCallBack() {
            @Override
            public void onSuccess(ArrayList<BannerModel> bannerUrl) {
                listimg.clear();
                listimg.addAll(bannerUrl);
                if (listimg.size() > 0) {
                    showBannerInfo();
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void showBannerInfo() {
        if (mViewPager == null)
            return;
        fillPoint();
        mViewPager.addOnPageChangeListener(new AdvertPageChangeListener(listimg.size(), dots));
        if (listimg.size() == 2) {
            listimg.addAll(listimg);
        }
        if (listimg.size() == 1) {
            bannerMax = 1;
        }
        AdvertPagerAdapter pagerAdapter = new AdvertPagerAdapter(listimg, context);
        mViewPager.setAdapter(pagerAdapter);
//        mViewPager.setOnTouchListener(new PagerTouchListener(mViewPager));
        if (listimg.size() > 1) {
            handler.sendEmptyMessageDelayed(ROLL_PAGE, TIME);
            dots.get(0).setBackgroundResource(R.drawable.shape_dot_blue);
            mViewPager.setCurrentItem(bannerMax / 2);
        }
    }

    private void fillPoint() {
        for (int i = 0; i < listimg.size(); i++) {
            //动态的添加点设置每一个点的布局
            View view = new View(context);
            mPointContainer.addView(view);
            view.setBackgroundResource(R.drawable.shape_dot_white);
            LinearLayout.LayoutParams Params = (LinearLayout.LayoutParams) view.getLayoutParams();
            Params.height = 10;//圆点的大小为10约为5dp
            Params.width = 10;
            Params.setMargins(2, 0, 2, 0);//设置圆点的边距
            view.setLayoutParams(Params);
            dots.add(view);//把所有的圆点添加到dots中
        }
    }

    class AdvertPagerAdapter extends PagerAdapter {

        private ArrayList<BannerModel> list;
        private Context mContext;

        public AdvertPagerAdapter(ArrayList<BannerModel> imageList, Context context) {
            this.list = imageList;
            mContext = context;
        }

        @Override
        public int getCount() {
            return bannerMax;
        }

        //滑动切换的时候销毁当前的组件
        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView((View) object);
        }

        //每次滑动的时候生成的组件
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final int index = position % list.size();
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(imageView);

            Glide.with(mContext)
                    .load(list.get(index).getTitlepic())
                    .placeholder(R.mipmap.empty)
                    .error(R.mipmap.error)
                    .centerCrop()
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        // 调用浏览器
                        String title = list.get(index).getTitle();
                        if (!TextUtils.isEmpty(title) && title.length() != 1) {
                            Uri webViewUri = Uri.parse(title);
                            Intent intent = new Intent(Intent.ACTION_VIEW, webViewUri);
                            mContext.startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    private boolean isDragging;

    class AdvertPageChangeListener implements ViewPager.OnPageChangeListener {

        private int mAdvertBeans;
        private ArrayList<View> mDots;

        public AdvertPageChangeListener(int advertBeans_size, ArrayList<View> dots) {
            mAdvertBeans = advertBeans_size;
            mDots = dots;
        }

        @Override
        public void onPageSelected(int position) {
            if (mPointContainer != null) {
                for (int i = 0; i < mAdvertBeans; i++) {
                    if (position % mAdvertBeans == i) {
                        mDots.get(i).setBackgroundResource(R.drawable.shape_dot_blue);
                    } else {
                        mDots.get(i).setBackgroundResource(R.drawable.shape_dot_white);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    isDragging = true;
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    isDragging = false;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    isDragging = false;
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    private static final int ROLL_PAGE = 1;
    private static final int TIME = 3000;
    /**
     * 设置轮播自动滑动及速度
     */
    public Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if (msg.what == ROLL_PAGE) {
                if (mViewPager != null) {
                    if (!isDragging) {
                        int currentItem = mViewPager.getCurrentItem();
                        mViewPager.setCurrentItem(currentItem + 1);
                    }
                    sendEmptyMessageDelayed(ROLL_PAGE, TIME);
                }
            }
        }
    };
}
