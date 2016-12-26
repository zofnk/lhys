package com.lh16808.app.lhys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.google.gson.reflect.TypeToken;
import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.model.HistoryRecordBean;
import com.lh16808.app.lhys.other.ProgressDialogMy;
import com.lh16808.app.lhys.other.ShowBannerInfo;
import com.lh16808.app.lhys.other.YearPopupWindow;
import com.lh16808.app.lhys.utils.AsyncHttpClientUtil;
import com.lh16808.app.lhys.utils.ConstantsUtil;
import com.lh16808.app.lhys.utils.DateFormatUtils;
import com.lh16808.app.lhys.utils.DateTimeUtil;
import com.lh16808.app.lhys.utils.GsonUtil;
import com.lh16808.app.lhys.utils.dataUtils.ToastUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by admin on 2016/10/14.
 */
public class LengReActivity extends BaseActivity {
    public RecyclerView mRecyclerView;
    public LengReAdapter mAdapter;
    public List<Map.Entry<String, Integer>> mDatas;
    public ProgressDialogMy mPro;
    public CardView cv_reload;
    public Button mBtnReload;
    int curyear = DateTimeUtil.GetYear();
    boolean lengreboolean = true;

    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    private RadioGroup mRadioGroup;
    YearPopupWindow yearPopupWindow;

    @Override
    protected void initLoadData() {

    }

    @Override
    protected void initVariables() {

    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.lhc_cx_lengre_activity);
         yearPopupWindow = new YearPopupWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(yearPopupWindow.year);
            }
        });

        setActionbar();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        cv_reload = (CardView) findViewById(R.id.cv_reload);
        mBtnReload = (Button) findViewById(R.id.btn_reload);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_shenghe);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //获取变更后的选中项的ID
                int radioButtonId = group.getCheckedRadioButtonId();
                switch (radioButtonId) {
                    default:
                    case R.id.radio_1:
                        lengreboolean = true;
                        break;
                    case R.id.radio_2:
                        lengreboolean = false;
                        break;
                }
                sort(mDatas, lengreboolean);
                mAdapter.notifyDataSetChanged();
            }
        });
        mDatas = new ArrayList<>();
        mAdapter = new LengReAdapter(this, mDatas, new LengReAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent();
                intent.setClass(LengReActivity.this, TiaoJianChaxunActivity.class);
                intent.putExtra("sousuo", mDatas.get(postion).getKey());
                intent.putExtra("year", curyear);
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(mAdapter);
        mBtnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(curyear);
            }
        });
        getData(curyear);
    }

    TextView tv_share;

    private void setActionbar() {
        TextView toolBarTitle = (TextView) findViewById(R.id.tool_bar_title);
        if (toolBarTitle != null) {
            toolBarTitle.setText(getResources().getString(R.string.lhc_chaxun_lrhm));
        }

        tv_share = (TextView) findViewById(R.id.tv_share);
        tv_share.setText("選擇年份");
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yearPopupWindow.showPopupWindow(curyear);
            }
        });

    }


    @Override
    protected void loadData() {

    }

    public static List<Map.Entry<String, Integer>> sort(Map<String, Integer> map, boolean bo) {
        List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        return sort(infoIds, bo);
    }

    public static List<Map.Entry<String, Integer>> sort(List<Map.Entry<String, Integer>> infoIds, final boolean bo) {
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                int flag;
                if (bo) {
                    flag = o2.getValue().compareTo(o1.getValue());
                } else {
                    flag = o1.getValue().compareTo(o2.getValue());
                }
                if (flag == 0) {
                    return o1.getKey().compareTo(o2.getKey());
                } else {
                    return flag;
                }
            }
        }); //排序
        return infoIds;
    }

    public void getData(final int year) {
        mPro = new ProgressDialogMy(LengReActivity.this);
        mPro.showpop();
        AsyncHttpClientUtil.getInstance().get(ConstantsUtil.HISTORY_RECORD(year + ""), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                curyear =year;
                String response = new String(responseBody);
                response = ConstantsUtil.replaceAll(response);
                // json转为带泛型的list
                List<HistoryRecordBean> mList = GsonUtil.buildGson().fromJson(response,
                        new TypeToken<List<HistoryRecordBean>>() {
                        }.getType());

                if (mList.size() > 0) {
                    if (mList.get(0).getNewstime() == null || mList.get(0).getNewstime().isEmpty()) {
                        for (int k = 0; k < mList.size(); k++) {
                            mList.get(k).setNewstime(DateFormatUtils.parse(year + "", "yyyy") / 1000 + "");
                        }
                    }

                    Map<String, Integer> map = new HashMap<String, Integer>();
//                    1976年定立以36选6的六合彩
//                    1983年由36个数字增加至40个
//                    1988年增加至42个数字
//                    1990年增加至45个数字
//                    1996年增加至47个数字
//                    2002年增加至49个数字
                    int size = 49;
                    if (year < 1983) {
                        size = 36;
                    } else if (year < 1988) {
                        size = 40;
                    } else if (year < 1990) {
                        size = 42;
                    } else if (year < 1996) {
                        size = 45;
                    } else if (year < 2002) {
                        size = 47;
                    }
                    for (int i = 1; i <= size; i++) {
                        map.put(String.format("%02d", i), 0);
                    }
                    for (int i = 0; i < mList.size(); i++) {
                        map.put(mList.get(i).getZ1m(), (map.get(mList.get(i).getZ1m()) == null
                                ? 0 : map.get(mList.get(i).getZ1m())) + 1);
                        map.put(mList.get(i).getZ2m(), (map.get(mList.get(i).getZ2m()) == null
                                ? 0 : map.get(mList.get(i).getZ2m())) + 1);
                        map.put(mList.get(i).getZ3m(), (map.get(mList.get(i).getZ3m()) == null
                                ? 0 : map.get(mList.get(i).getZ3m())) + 1);
                        map.put(mList.get(i).getZ4m(), (map.get(mList.get(i).getZ4m()) == null
                                ? 0 : map.get(mList.get(i).getZ4m())) + 1);
                        map.put(mList.get(i).getZ5m(), (map.get(mList.get(i).getZ5m()) == null
                                ? 0 : map.get(mList.get(i).getZ5m())) + 1);
                        map.put(mList.get(i).getZ6m(), (map.get(mList.get(i).getZ6m()) == null
                                ? 0 : map.get(mList.get(i).getZ6m())) + 1);
                        map.put(mList.get(i).getTm(), (map.get(mList.get(i).getTm()) == null
                                ? 0 : map.get(mList.get(i).getTm())) + 1);
                    }
                    mDatas.clear();
                    mDatas.addAll(sort(map, lengreboolean));
                    mAdapter.notifyDataSetChanged();
                    tv_share.setText("選" + year + "年");
                }
                if (mDatas.size() == 0) {
                    mBtnReload.setText(year + "年數據還沒有，點擊加載" + (year - 1) + "數據");
                    curyear = year - 1;
                    cv_reload.setVisibility(View.VISIBLE);
                } else {
                    cv_reload.setVisibility(View.GONE);
                }
                mPro.cancel();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mPro.cancel();
                ToastUtil.onFailure(LengReActivity.this, statusCode);
                if (statusCode == 404) {
                    if (mDatas.size() == 0) {
                        mBtnReload.setText(year + "年數據還沒有，點擊加載" + (year - 1) + "數據");
                        curyear = year - 1;
                        cv_reload.setVisibility(View.VISIBLE);
                    } else {
                        cv_reload.setVisibility(View.GONE);
                    }
                } else {
                    if (mDatas.size() == 0) {
                        mBtnReload.setText(year + "年數據加載失敗，點擊重新加載");
                        cv_reload.setVisibility(View.VISIBLE);
                    } else {
                        cv_reload.setVisibility(View.GONE);
                    }
                }
            }
        });
    }


}
