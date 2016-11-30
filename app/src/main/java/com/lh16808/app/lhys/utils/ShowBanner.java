package com.lh16808.app.lhys.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.model.BannerData;
import com.lh16808.app.lhys.model.BannerUrl;
import com.lh16808.app.lhys.utils.http.AsyncHttpClientUtils;
import com.lh16808.app.lhys.utils.http.ConnectionUtils;
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
    private ArrayList<BannerUrl> listimg = new ArrayList<>();
    private ArrayList<ImageView> advertImgs = new ArrayList<>();
    private ArrayList<View> dots = new ArrayList<View>(); // 图片标题正文的那些点
    private Context context;

    private View vp_fragment__relat_banner;
    private ViewPager vp_banner;
    private LinearLayout ll_dot;
    private int bannerMax = 10000000;
    private String Url;
    public boolean isRemove;

    private ShowBanner() {

    }

    public static ShowBanner getShowBanner() {
        return new ShowBanner();
    }

    public void removeAction() {
        if (listimg.size() > 1) {
            vp_banner.removeCallbacks(action);
            isRemove = true;
        }
    }

    public void runAction() {
        if (listimg.size() > 1 && isRemove) {
            vp_banner.post(action);
            isRemove = false;
        }
    }

    /**
     * 设置轮播自动滑动及速度
     */
    private Runnable action = new Runnable() {
        @Override
        public void run() {
            int currentItem = vp_banner.getCurrentItem();
            vp_banner.setCurrentItem(currentItem + 1, true);
            vp_banner.postDelayed(action, 3000);
        }
    };

    public ShowBanner show(BannerData bannerData) {
        this.Url = bannerData.getUrl();
        this.context = bannerData.getContextX();
        this.vp_fragment__relat_banner = bannerData.getRelativeLayout();
        this.vp_banner = bannerData.getViewPager();
        this.ll_dot = bannerData.getLinearLayout();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) vp_banner.getLayoutParams();
        layoutParams.height = ScreenUtils.getScreenWidth(context) * 170 / 410;
        vp_banner.setLayoutParams(layoutParams);
        if (ConnectionUtils.isConnect(context)) {
            getBannerInfo();
        } else {
            for (int i = 0; i < 1; i++) {
                BannerUrl bannerUrl = new BannerUrl();
                bannerUrl.setTitle("");
                bannerUrl.setTitlepic("http://www.16808.com/");
                listimg.add(bannerUrl);
            }
            showBannerInfo();
        }
        return ShowBanner.this;
    }

    /**
     * 获取广告信息
     */
    private void getBannerInfo() {
        AsyncHttpClientUtils.getInstance().get(Url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String arg0 = new String(responseBody);
                try {
                    JSONArray jsonArray = new JSONArray(arg0);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        String titlepic = jsonObject.getString("titlepic");
                        BannerUrl bannerUrl = new BannerUrl();
                        bannerUrl.setTitle(title);
                        bannerUrl.setTitlepic(titlepic);
                        listimg.add(bannerUrl);
                    }
                    if (listimg.size() > 0) {
                        showBannerInfo();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void showBannerInfo() {
        if (vp_fragment__relat_banner != null) {
            if (listimg.size() > 0) {
                vp_fragment__relat_banner.setVisibility(View.VISIBLE);
            } else {
                vp_fragment__relat_banner.setVisibility(View.GONE);
            }
        }
        for (int i = 0; i < listimg.size(); i++) {
            //动态的添加点设置每一个点的布局
            View view = new View(context);
            ll_dot.addView(view);
            view.setBackgroundResource(R.drawable.shape_dot_white);
            LinearLayout.LayoutParams Params = (LinearLayout.LayoutParams) view.getLayoutParams();
            Params.height = 10;//圆点的大小为10约为5dp
            Params.width = 10;
            Params.setMargins(2, 0, 2, 0);//设置圆点的边距
            view.setLayoutParams(Params);
            dots.add(view);//把所有的圆点添加到dots中
        }
        vp_banner.addOnPageChangeListener(new AdvertPageChangeListener(listimg.size(), dots));
        if (listimg.size() == 2) {
            listimg.addAll(listimg);
        }
        for (int i = 0; i < listimg.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            final BannerUrl advertBean = listimg.get(i);
            if (!TextUtils.isEmpty(advertBean.getTitle())) {
                UILUtils.displayImage(context ,listimg.get(i).getTitlepic(), imageView);
            } else {
                imageView.setImageResource(R.drawable.bg_fx);
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        // 调用浏览器
                        Uri webViewUri = Uri.parse(advertBean.getTitle());
                        Intent intent = new Intent(Intent.ACTION_VIEW, webViewUri);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            advertImgs.add(imageView);
        }
        if (listimg.size() == 1) {
            bannerMax = 1;
        }
        AdvertPagerAdapter pagerAdapter = new AdvertPagerAdapter(advertImgs, listimg.size());
        vp_banner.setAdapter(pagerAdapter);
        vp_banner.setOnTouchListener(new PagerTouchListener(vp_banner));
        if (listimg.size() > 1) {
            if (vp_banner != null) {
                vp_banner.postDelayed(action, 3000);
                dots.get(0).setBackgroundResource(R.drawable.shape_dot_blue);
            }
            vp_banner.setCurrentItem(bannerMax / 2);

        }
    }

    class AdvertPagerAdapter extends PagerAdapter {

        private ArrayList<ImageView> mImageList;
        int size;

        public AdvertPagerAdapter(ArrayList<ImageView> imageList, int advertBeans) {
            this.mImageList = imageList;
            size = advertBeans;
        }

        @Override
        public int getCount() {
            return bannerMax;
        }

        //滑动切换的时候销毁当前的组件
        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
        }

        //每次滑动的时候生成的组件
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).removeView(mImageList.get(position % size));
            ((ViewPager) container).addView(mImageList.get(position % size));
            return mImageList.get(position % size);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    class AdvertPageChangeListener implements ViewPager.OnPageChangeListener {

        private int mAdvertBeans;
        private ArrayList<View> mDots;

        public AdvertPageChangeListener(int advertBeans_size, ArrayList<View> dots) {
            mAdvertBeans = advertBeans_size;
            mDots = dots;
        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < mAdvertBeans; i++) {
                if (position % mAdvertBeans == i) {
                    mDots.get(i).setBackgroundResource(R.drawable.shape_dot_blue);
                } else {
                    mDots.get(i).setBackgroundResource(R.drawable.shape_dot_white);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    class PagerTouchListener implements View.OnTouchListener {

        private ViewPager mAdViewPager;

        public PagerTouchListener(ViewPager adViewPager) {
            mAdViewPager = adViewPager;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mAdViewPager.removeCallbacks(action);
                    break;
                case MotionEvent.ACTION_UP:
                    if (mAdViewPager != null) {
                        mAdViewPager.removeCallbacks(action);
                        mAdViewPager.postDelayed(action, 3000);
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}
