package com.lh16808.app.lhys.fragment;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lh16808.app.lhys.MainActivity;
import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.activity.ForumActivity;
import com.lh16808.app.lhys.activity.HistoryRecordActivity;
import com.lh16808.app.lhys.activity.KJVideoActivity;
import com.lh16808.app.lhys.activity.LottoActivity;
import com.lh16808.app.lhys.activity.MysteryActivity;
import com.lh16808.app.lhys.activity.TuKuActivity;
import com.lh16808.app.lhys.activity.ZiliaoActivity;
import com.lh16808.app.lhys.engine.OnTouchAnim;
import com.lh16808.app.lhys.marco.Constants;
import com.lh16808.app.lhys.model.BannerData;
import com.lh16808.app.lhys.model.BannerUrl;
import com.lh16808.app.lhys.model.Lottery;
import com.lh16808.app.lhys.other.KaiJianLoad;
import com.lh16808.app.lhys.other.MyProgressDialog;
import com.lh16808.app.lhys.other.ShowBannerInfo;
import com.lh16808.app.lhys.service.LottoService;
import com.lh16808.app.lhys.utils.AppLog;
import com.lh16808.app.lhys.utils.MyUtils;
import com.lh16808.app.lhys.utils.SharedPreUtils;
import com.lh16808.app.lhys.utils.dataUtils.DateFormatUtils;
import com.lh16808.app.lhys.utils.http.AsyncHttpClientUtils;
import com.lh16808.app.lhys.widget.RandomTextView;
import com.lh16808.app.lhys.widget.loopviewpager.AutoLoopViewPager;
import com.lh16808.app.lhys.widget.loopviewpager.CirclePageIndicator;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sunfusheng.marqueeview.MarqueeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.lh16808.app.lhys.R.id.imageView;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private AutoLoopViewPager pager;
    private CirclePageIndicator indicator;
    private GalleryPagerAdapter galleryAdapter;
    private List<BannerUrl> bannerList = new ArrayList<>();

    public static String TAG = HomeFragment.class.getName();

    Class[] classes = {HistoryRecordActivity.class, LottoActivity.class, ForumActivity.class, MysteryActivity.class, ZiliaoActivity.class, TuKuActivity.class};
    private View view;
    //    private RecyclerView mRecyclerView;
    private TextView mTvLotteryTimeDay;
    private TextView mTvLotteryTimeHour;
    private TextView mTvLotteryTimeMinute;
    private TextView mTvLotteryTimeSecond;
    private TextView mTvLotteryTime;
    private TextView tv_z1m;
    private TextView tv_z2m;
    private TextView tv_z3m;
    private TextView tv_z4m;
    private TextView tv_z5m;
    private TextView tv_z6m;
    private TextView tv_tm;
    private TextView tv_z1sx;
    private TextView tv_z2sx;
    private TextView tv_z3sx;
    private TextView tv_z4sx;
    private TextView tv_z5sx;
    private TextView tv_z6sx;
    private TextView tv_tmsx;
    private MediaPlayer mediaPlayer;

    private KaiJianLoad kaiJianLoad = new KaiJianLoad();
    private Handler handler = new Handler();
    private CountDownTimer mCountDownTimer;
    private ShowBannerInfo mShowBannerInfo;
    private View mLLBaoMa;
    private View mLLKaiJian;
    private TextView mTvBaoMa;
    public static Lottery mLottery;
    private Intent mService;
    LottoService.LottoBinder mBinder;
    private RandomTextView tvResultBQ;
    private List<String> MarqueeInfo = new ArrayList<>();

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
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            initVariables(view);
            loadData();
            load();
        }

//        pager = (AutoLoopViewPager) view.findViewById(R.id.pager);
//        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
//        getBannerInfo();
        return view;
    }

    private void load() {
        MainActivity.getInstance().setLoadzt(new MainActivity.LoadZT() {
            @Override
            public void loadzt(int zt) {
                AppLog.redLog(TAG + "sendZT", "zt:" + zt);
                if (zt == 1) {
                    AppLog.redLog(TAG + "sendZT", "" + zt);
                } else if (zt == 2) {
                    playGG(R.raw.zero);
                    mTvBaoMa.setText(getResources().getString(R.string.kj_main_zbb_2));
                    AppLog.redLog(TAG + "sendZT", "" + zt);
                } else if (zt == 4) {
                    playGG(R.raw.ggz);
                    mTvBaoMa.setText(getResources().getString(R.string.kj_main_ggz_4));
                    AppLog.redLog(TAG + "sendZT", "" + zt);
                } else if (zt == 5) {
                    playGG(R.raw.zcrjhz);
                    mTvBaoMa.setText(getResources().getString(R.string.kj_main_zzk_5));
                    AppLog.redLog(TAG + "sendZT", "" + zt);
                } else if (zt == 3) {
                    mTvBaoMa.setText(getResources().getString(R.string.kj_main_zzk_3));
                    startActivity();
                    AppLog.redLog(TAG + "sendZT", "" + zt);
                }
                if (zt != 1) {
                    if (mLLBaoMa.getVisibility() != View.VISIBLE) {
                        mLLBaoMa.setVisibility(View.VISIBLE);
                        mLLKaiJian.setVisibility(View.GONE);
                        AppLog.redLog(TAG + "sendZT", "x2");
                    }
                } else {
                    if (mLLKaiJian.getVisibility() != View.VISIBLE) {
                        mLLBaoMa.setVisibility(View.GONE);
                        mLLKaiJian.setVisibility(View.VISIBLE);
                        loadData();
                        AppLog.redLog("KJ" + "sendZT", "x1");
                    }
                }
                AppLog.redLog(TAG + "sendZT", "" + zt + "---" + Lottery.getLottery().toString() + "---sxsj:" + Lottery.Time);

            }
        });
    }

    private void initVariables(View view) {
//        initRandomTextView();

        MarqueeView marqueeView = (MarqueeView) view.findViewById(R.id.marqueeView);
        MarqueeInfo.add("1. wwww.16808.com");
        MarqueeInfo.add("2. 欢迎大家关注六合运势");
        MarqueeInfo.add("3. 六合运势为您服务");
        marqueeView.startWithList(MarqueeInfo);

        View items1 = view.findViewById(R.id.ll_main_item1);
        items1.setOnTouchListener(new OnTouchAnim());
        items1.setOnClickListener(this);

        View items2 = view.findViewById(R.id.ll_main_item2);
        items2.setOnTouchListener(new OnTouchAnim());
        items2.setOnClickListener(this);

        View items4 = view.findViewById(R.id.ll_main_item4);
        items4.setOnTouchListener(new OnTouchAnim());
        items4.setOnClickListener(this);

        View items5 = view.findViewById(R.id.ll_main_item5);
        items5.setOnTouchListener(new OnTouchAnim());
        items5.setOnClickListener(this);

        View items6 = view.findViewById(R.id.ll_main_item6);
        items6.setOnTouchListener(new OnTouchAnim());
        items6.setOnClickListener(this);

        mTvLotteryTimeDay = (TextView) view.findViewById(R.id.tv_kjsj_tian);
        mTvLotteryTimeHour = (TextView) view.findViewById(R.id.tv_kjsj_shi);
        mTvLotteryTimeMinute = (TextView) view.findViewById(R.id.tv_kjsj_fen);
        mTvLotteryTimeSecond = (TextView) view.findViewById(R.id.tv_kjsj_miao);
        mTvLotteryTime = (TextView) view.findViewById(R.id.tv_kjsj);
        tv_z1m = (TextView) view.findViewById(R.id.tv_z1m);
        tv_z2m = (TextView) view.findViewById(R.id.tv_z2m);
        tv_z3m = (TextView) view.findViewById(R.id.tv_z3m);
        tv_z4m = (TextView) view.findViewById(R.id.tv_z4m);
        tv_z5m = (TextView) view.findViewById(R.id.tv_z5m);
        tv_z6m = (TextView) view.findViewById(R.id.tv_z6m);
        tv_tm = (TextView) view.findViewById(R.id.tv_tm);
        tv_z1sx = (TextView) view.findViewById(R.id.tv_z1sx);
        tv_z2sx = (TextView) view.findViewById(R.id.tv_z2sx);
        tv_z3sx = (TextView) view.findViewById(R.id.tv_z3sx);
        tv_z4sx = (TextView) view.findViewById(R.id.tv_z4sx);
        tv_z5sx = (TextView) view.findViewById(R.id.tv_z5sx);
        tv_z6sx = (TextView) view.findViewById(R.id.tv_z6sx);
        tv_tmsx = (TextView) view.findViewById(R.id.tv_tmsx);
        mLLBaoMa = view.findViewById(R.id.kj_main_ll_2);
        mLLKaiJian = view.findViewById(R.id.kj_main_ll_1);
        mTvBaoMa = (TextView) view.findViewById(R.id.tv_baoma_tx);
        mLLBaoMa.setOnClickListener(this);
//        mShowBannerInfo = new ShowBannerInfo(getActivity(), rlBanner, vpBanner);
        tvResultBQ = (RandomTextView) view.findViewById(R.id.tv_main_Result_bq);
    }

    /*private void initRandomTextView() {
        mRandomTextView.setText("123");
        mRandomTextView.setPianyilian(RandomTextView.FIRSTF_FIRST);
        mRandomTextView.start();
    }
*/

    @Override
    public void onClick(View v) {
        if (MyUtils.isFastDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.kj_main_ll_2:
                if ("3".equals(mLottery.zt)) {
                    startActivity();
                }
                break;
            case R.id.ll_main_item1:
                startActivity2(0);
                break;
            case R.id.ll_main_item2:
                startActivity2(1);
                break;
            case R.id.ll_main_item4:
                startActivity2(3);
                break;
            case R.id.ll_main_item5:
                startActivity2(4);
                break;
            case R.id.ll_main_item6:
                startActivity2(5);
                break;
        }
    }

    private void playGG(int a) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        if (!getActivity().isFinishing()) {
            mediaPlayer = MediaPlayer.create(getActivity(), a);
            mediaPlayer.start();
        }
    }

    protected void loadData() {
        MyProgressDialog.dialogShow(getActivity());
        mLottery = Lottery.getLottery();
        kaiJianLoad.setOnNetData(new KaiJianLoad.OnNetData() {
            @Override
            public void onScuss() {
                MyProgressDialog.dialogHide();
                Data();
                if ("1".equals(mLottery.zt)) {
                    mLLBaoMa.setVisibility(View.INVISIBLE);
                    mLLKaiJian.setVisibility(View.VISIBLE);
                } else {
                    mLLBaoMa.setVisibility(View.VISIBLE);
                    mLLKaiJian.setVisibility(View.INVISIBLE);
                }
                String upLotteryTime = SharedPreUtils.getString(Constants.LOTTERY_TIME, "0");
                String nowLotteryTime = Lottery.getLottery().bq;
                if (!upLotteryTime.equals(nowLotteryTime)) {
                    //保持開獎時間，新的一期能夠進行新的抽獎   String lotteryTime = getLotteryTime();
                    SharedPreUtils.putString(Constants.LOTTERY_TIME, nowLotteryTime);
                    SharedPreUtils.putBoolean(Constants.CAN_LUCK_PAN, true);
                    SharedPreUtils.putBoolean(Constants.CAN_TURN_CARDS, true);
                    SharedPreUtils.putBoolean(Constants.CAN_SHAKE, true);
                }
                if ("1".equals(mLottery.zt))
                    countDown();
            }

            @Override
            public void onError() {
                MyProgressDialog.dialogHide();
            }

            @Override
            public void onTime() {
                if ("1".equals(mLottery.zt))
                    countDown();
            }
        });
    }

    private void Data() {
        tvResultBQ.setText(mLottery.bq + "");
        tvResultBQ.setPianyilian(RandomTextView.FIRSTF_FIRST);
        tvResultBQ.start();
        mTvLotteryTime.setText("" + mLottery.xyqsx + "星期" + mLottery.xq);
        if (mLottery.zt.equals("1")) {
            tv_z1m.setBackgroundResource(MyUtils.isRBG(mLottery.z1m));
            tv_z2m.setBackgroundResource(MyUtils.isRBG(mLottery.z2m));
            tv_z3m.setBackgroundResource(MyUtils.isRBG(mLottery.z3m));
            tv_z4m.setBackgroundResource(MyUtils.isRBG(mLottery.z4m));
            tv_z5m.setBackgroundResource(MyUtils.isRBG(mLottery.z5m));
            tv_z6m.setBackgroundResource(MyUtils.isRBG(mLottery.z6m));
            tv_tm.setBackgroundResource(MyUtils.isRBG(mLottery.tm));
            tv_z1m.setText(mLottery.z1m);
            tv_z2m.setText(mLottery.z2m);
            tv_z3m.setText(mLottery.z3m);
            tv_z4m.setText(mLottery.z4m);
            tv_z5m.setText(mLottery.z5m);
            tv_z6m.setText(mLottery.z6m);
            tv_tm.setText(mLottery.tm);
            tv_z1sx.setText(mLottery.z1sx);
            tv_z2sx.setText(mLottery.z2sx);
            tv_z3sx.setText(mLottery.z3sx);
            tv_z4sx.setText(mLottery.z4sx);
            tv_z5sx.setText(mLottery.z5sx);
            tv_z6sx.setText(mLottery.z6sx);
            tv_tmsx.setText(mLottery.tmsx);
        }
    }

    /**
     * 倒計時
     */
    private void countDown() {
        AppLog.redLog(TAG, "countDown");
        mCountDownTimer = null;
        long totalTime = MyUtils.getTotalTime();
        int countDownInterval = 1000;
        mCountDownTimer = new CountDownTimer(totalTime, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                //將毫秒改成時間單位
                String times[] = DateFormatUtils.getTotalTime(millisUntilFinished);
                mTvLotteryTimeDay.setText(times[0]);
                mTvLotteryTimeHour.setText(times[1]);
                mTvLotteryTimeMinute.setText(times[2]);
                mTvLotteryTimeSecond.setText(times[3]);
            }

            @Override
            public void onFinish() {
                openLottery();
            }
        };
        mCountDownTimer.start();
    }

    private void openLottery() {
        AppLog.redLog(TAG, "countDown-->openLottery");
        if (mLLBaoMa.getVisibility() != View.VISIBLE) {
            mLLBaoMa.setVisibility(View.VISIBLE);
            mLLKaiJian.setVisibility(View.GONE);
            Lottery.Time = 5000;
        }
    }

    private void startActivity() {
//        ToastUtil.toastShow(mActivity, "xxxxxxxxxxxxxxxx");
        Intent intent = new Intent(getContext(), KJVideoActivity.class);
        getActivity().startActivityForResult(intent, 23);
    }

    private void startActivity2(final int i) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getActivity(), classes[i]));
            }
        }, 100);
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
        tvResultBQ.destroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        pager.stopAutoScroll();
        tvResultBQ.destroy();
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
