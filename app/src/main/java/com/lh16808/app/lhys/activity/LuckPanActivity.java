package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.marco.Constants;
import com.lh16808.app.lhys.utils.RandomUtils;
import com.lh16808.app.lhys.utils.SharedPreUtils;
import com.lh16808.app.lhys.utils.ToastUtil;
import com.lh16808.app.lhys.widget.LuckyPanView;


public class LuckPanActivity extends BaseActivity implements View.OnClickListener {

    LuckyPanView lpv;
    private boolean isLucking = false;
    private int luckNum;
    ImageView mIvStart;
    TextView mTvPromt;
    private String[] mStrs = new String[]{"鼠", "牛", "虎", "兔", "龍", "蛇", "馬", "羊", "猴", "雞", "狗", "豬"};

    public static void start(Context context) {
        Intent starter = new Intent(context, LuckPanActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initLoadData() {

    }

    @Override
    protected void initVariables() {
        luckNum = RandomUtils.getRandom(12);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_luck_pan);
        setActionbar();
        lpv = (LuckyPanView) findViewById(R.id.lpv);
        mIvStart = (ImageView) findViewById(R.id.iv_start);
        mTvPromt = (TextView) findViewById(R.id.tv_promt);
        mIvStart.setOnClickListener(this);
        if (SharedPreUtils.getBoolean(Constants.CAN_LUCK_PAN, false)) {
            mTvPromt.setText("小提示：轉盤每一期衹能轉一次哦！");
        } else {
            lpv.luckyStart(SharedPreUtils.getInt(Constants.LUCK_PAN_RESULT, 0));
            lpv.luckyEnd();
            int anInt = SharedPreUtils.getInt(Constants.LUCK_PAN_RESULT, 0);
            String str = "<html><<body><p>小提示：您本期的幸运号码是" +
                    "<b>" + mStrs[anInt] + "</b>，請您下一期繼續！" +
                    "\n</p></body></html>";
            mTvPromt.setText(Html.fromHtml(str));
        }
    }

    @Override
    protected void loadData() {

    }


    private void setActionbar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        if (toolbar != null) {
//            toolbar.setTitle("");
//        }
//        setSupportActionBar(toolbar);
        TextView toolBarTitle = (TextView) findViewById(R.id.tool_bar_title);
        if (toolBarTitle != null) {
            toolBarTitle.setText("幸運轉盤");
        }
        findViewById(R.id.tv_share).setOnClickListener(this);
    }


    /**
     * 點擊開始，判斷是否正在轉盤中，如果不是進入抽獎，如果是停止抽獎並記錄抽獎號碼
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_share:
//                MyDialog my = new MyDialog(this);
//                my.show(getSupportFragmentManager(),null);
                break;
            case R.id.iv_start:
                if (!isLucking) {
                    //判斷本期是否已經抽過獎
                    if (SharedPreUtils.getBoolean(Constants.CAN_LUCK_PAN, true)) {
                        isLucking = true;
                        mIvStart.setImageResource(R.drawable.stop);
                        lpv.luckyStart(luckNum);
                    } else {
                        isLucking = false;
                        ToastUtil.toastShow(this, "本期您已經轉過轉盤，請下一期繼續！");
                    }
                } else {
                    mIvStart.setImageResource(R.drawable.start);
                    lpv.luckyEnd();
                    SharedPreUtils.putInt(Constants.LUCK_PAN_RESULT, luckNum);
                    SharedPreUtils.putBoolean(Constants.CAN_LUCK_PAN, false);
                    int anInt = SharedPreUtils.getInt(Constants.LUCK_PAN_RESULT, 0);
                    String str = "<html><<body><p>小提示：您本期的幸运号码是" +
                            "<b>" + mStrs[anInt] + "</b>，請您下一期繼續！" +
                            "\n</p></body></html>";
                    mTvPromt.setText(Html.fromHtml(str));
//                    mTvPromt.setText("小提示：您本期的幸运号码是" + mStrs[anInt] + "，請您下一期繼續！");
                    isLucking = false;
                }
                break;
        }
    }

}
