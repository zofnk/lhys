package com.lh16808.app.lhys.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lh16808.app.lhys.MainActivity;
import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.activity.ChaxunZhushouActivity;
import com.lh16808.app.lhys.activity.ForumActivity;
import com.lh16808.app.lhys.activity.HistoryRecordActivity;
import com.lh16808.app.lhys.activity.KJVideoActivity;
import com.lh16808.app.lhys.activity.LottoActivity;
import com.lh16808.app.lhys.activity.MysteryActivity;
import com.lh16808.app.lhys.activity.NatureActivity;
import com.lh16808.app.lhys.activity.TuKuActivity;
import com.lh16808.app.lhys.activity.ZiliaoActivity;
import com.lh16808.app.lhys.activity.ZoushifenxiActivity;
import com.lh16808.app.lhys.engine.OnTouchAnim;
import com.lh16808.app.lhys.marco.ApiConfig;
import com.lh16808.app.lhys.marco.Constants;
import com.lh16808.app.lhys.model.BannerUrl;
import com.lh16808.app.lhys.model.Lottery;
import com.lh16808.app.lhys.other.KaiJianLoad;
import com.lh16808.app.lhys.other.MyProgressDialog;
import com.lh16808.app.lhys.other.ShowBannerInfo;
import com.lh16808.app.lhys.service.LottoService;
import com.lh16808.app.lhys.utils.AppLog;
import com.lh16808.app.lhys.utils.DialogUtils;
import com.lh16808.app.lhys.utils.MyUtils;
import com.lh16808.app.lhys.utils.SharedPreUtils;
import com.lh16808.app.lhys.utils.ShowBanner;
import com.lh16808.app.lhys.utils.dataUtils.DateFormatUtils;
import com.lh16808.app.lhys.utils.http.AsyncHttpClientUtils;
import com.lh16808.app.lhys.utils.http.ConnectionUtils;
import com.lh16808.app.lhys.utils.http.H;
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

import static com.lh16808.app.lhys.MainActivity.mContext;
import static com.lh16808.app.lhys.R.id.marqueeView;


public class HomeFragment extends Fragment implements View.OnClickListener {


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
    private MarqueeView mMarqueeView;
    private ShowBanner mShowBanner;
    private NetworkReceiver myNetReceiver;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
    }

    /////////////监听网络状态变化的广播接收器
    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeInfo = manager.getActiveNetworkInfo();
            if (activeInfo != null) { //网络连接
                if (mShowBanner != null) {
                    int size = mShowBanner.getListimg().size();
                    if (size == 0) {
                        mShowBanner.getBannerInfo();
                    }
                }
            } else { //网络断开
                Log.e(TAG, "onReceive: 测试：网络断开");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            initBanner();
            initVariables(view);
            loadData();
            load();
            if (!ConnectionUtils.isConnected(mContext)) {
                DialogUtils.showNotNetWork(mContext);
                return view;
            }
        } else {
            mShowBanner.runAction();
        }
        //动态注册广播
        myNetReceiver = new NetworkReceiver();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mContext.registerReceiver(myNetReceiver, mFilter);
        return view;
    }

    @Override
    public void onDestroyView() {
        mShowBanner.removeAction();
        if (myNetReceiver != null) {
            mContext.unregisterReceiver(myNetReceiver);
        }
        super.onDestroyView();
    }

    private void initBanner() {
        LinearLayout ll_dot = (LinearLayout) view.findViewById(R.id.ll_fragment_homepage_banner_dot);
        ViewPager vp_banner = (ViewPager) view.findViewById(R.id.vp_fragment_homepage_banner);
        mShowBanner = new ShowBanner().show(mContext, 0, null, ll_dot, vp_banner);
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

    @Override
    public void onStart() {
        mShowBanner.runAction();
        super.onStart();
    }

    @Override
    public void onStop() {
        mShowBanner.removeAction();
        super.onStop();
    }

    private void initVariables(View view) {

        //跑马灯
        mMarqueeView = (MarqueeView) view.findViewById(marqueeView);
        mMarqueeView.setOnItemClickListener(new MarqueeListener());

        View items1 = view.findViewById(R.id.ll_main_item1);
        items1.setOnTouchListener(new OnTouchAnim());
        items1.setOnClickListener(this);

        View chaxunzhushou = view.findViewById(R.id.rltCxzs);
        chaxunzhushou.setOnTouchListener(new OnTouchAnim());
        chaxunzhushou.setOnClickListener(this);

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

        View mRlvScro = view.findViewById(R.id.rlvScro);
        mRlvScro.setOnTouchListener(new OnTouchAnim());
        mRlvScro.setOnClickListener(this);

        View mRlvBaijl = view.findViewById(R.id.rltBjl);
        mRlvBaijl.setOnTouchListener(new OnTouchAnim());
        mRlvBaijl.setOnClickListener(this);

        View mRltZous = view.findViewById(R.id.rlvZous);
        mRltZous.setOnTouchListener(new OnTouchAnim());
        mRltZous.setOnClickListener(this);

        View mRltShux = view.findViewById(R.id.rltShux);
        mRltShux.setOnTouchListener(new OnTouchAnim());
        mRltShux.setOnClickListener(this);

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
            case R.id.rltBjl:
                Intent intentBjl = new Intent();
                intentBjl.setClass(getContext(), LottoActivity.class);
                intentBjl.putExtra("web_title", "百家樂");
                intentBjl.putExtra("web_key", ApiConfig.getBaseUrl("baijiale.html"));
                getActivity().startActivity(intentBjl);
                break;
            case R.id.rlvScro:
                Intent intentScro = new Intent();
                intentScro.setClass(getContext(), LottoActivity.class);
                intentScro.putExtra("web_title", "足球比分");
                intentScro.putExtra("web_key", ApiConfig.getBaseUrl(ApiConfig.ZQBF));
                getActivity().startActivity(intentScro);
                break;
            case R.id.rlvZous:
                ZoushifenxiActivity.start(getContext());
                break;
            case R.id.rltCxzs:
                ChaxunZhushouActivity.start(getContext());
                break;
            case R.id.rltShux:
                NatureActivity.start(getContext());
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
        loadMarQueeData();
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

    private void loadMarQueeData() {
        H.GGXXUrl(null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                JSONArray array = null;
                try {
                    array = new JSONArray(json);
                    List<String> mMarqueeInfo = new ArrayList<>(array.length());
                    mMarqueeInfo.add("欢迎使用六合运势!!");
                    for (int i = 0; i < array.length(); i++) {
                        mMarqueeInfo.add(array.getString(i));
                    }
                    mMarqueeView.startWithList(mMarqueeInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("onFailure", "onFailure: " + responseBody);
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        tvResultBQ.destroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvResultBQ.destroy();
    }

    private class MarqueeListener implements MarqueeView.OnItemClickListener {
        @Override
        public void onItemClick(int position, TextView textView) {
            Uri uri = Uri.parse("http://www.6happ.com");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}
