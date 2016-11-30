package com.lh16808.app.lhys.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lh16808.app.lhys.MainActivity;
import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.marco.Constants;
import com.lh16808.app.lhys.model.BannerData;
import com.lh16808.app.lhys.model.BannerUrl;
import com.lh16808.app.lhys.utils.http.AsyncHttpClientUtils;
import com.lh16808.app.lhys.widget.RandomTextView;
import com.lh16808.app.lhys.widget.loopviewpager.AutoLoopViewPager;
import com.lh16808.app.lhys.widget.loopviewpager.CirclePageIndicator;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.lh16808.app.lhys.R.id.imageView;


public class HomeFragment extends Fragment {

    private AutoLoopViewPager pager;
    private CirclePageIndicator indicator;
    private GalleryPagerAdapter galleryAdapter;
    private List<BannerUrl> bannerList = new ArrayList<>();
    private RandomTextView mRandomTextView;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRandomTextView = (RandomTextView) view.findViewById(R.id.tv_main_Result_bq);
        initRandomTextView();

//        pager = (AutoLoopViewPager) view.findViewById(R.id.pager);
//        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
//        getBannerInfo();
        return view;
    }

    private void initRandomTextView() {
        mRandomTextView.setText("123");
        mRandomTextView.setPianyilian(RandomTextView.FIRSTF_FIRST);
        mRandomTextView.start();
    }


    /**
     * 获取广告信息
     */
    private void getBannerInfo() {
        AsyncHttpClientUtils.getInstance().get(Constants.AD_HOME, new AsyncHttpResponseHandler() {
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
                        bannerList.add(bannerUrl);
                        galleryAdapter = new GalleryPagerAdapter();
                        pager.setAdapter(galleryAdapter);
                        indicator.setViewPager(pager);
                        indicator.setPadding(5, 5, 10, 5);
                        pager.startAutoScroll();
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

    @Override
    public void onPause() {
        super.onPause();
//        pager.stopAutoScroll();
        mRandomTextView.destroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        pager.stopAutoScroll();
        mRandomTextView.destroy();

    }

    //轮播图适配器
    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return bannerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final BannerUrl bannerData = bannerList.get(position);
            ImageView item = new ImageView(getContext());
            Glide.with(getContext())
                    .load(bannerData.getTitlepic())
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(item);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            item.setLayoutParams(params);
            item.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(item);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bannerData.getTitlepic() != null && !bannerData.getTitlepic().equals("")) {
                        Uri webViewUri = Uri.parse(bannerData.getTitle());
                        startActivity(new Intent(Intent.ACTION_VIEW, webViewUri));
                    } else {
                        Toast.makeText(getContext(), "sdasda", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }

}
