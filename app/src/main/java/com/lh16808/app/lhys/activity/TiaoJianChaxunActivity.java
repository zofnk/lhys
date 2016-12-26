package com.lh16808.app.lhys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.model.HistoryRecordBean;
import com.lh16808.app.lhys.model.TableDataBean;
import com.lh16808.app.lhys.other.ProgressDialogMy;
import com.lh16808.app.lhys.utils.AsyncHttpClientUtil;
import com.lh16808.app.lhys.utils.ConstantsUtil;
import com.lh16808.app.lhys.utils.DateFormatUtils;
import com.lh16808.app.lhys.utils.DateTimeUtil;
import com.lh16808.app.lhys.utils.GsonUtil;
import com.lh16808.app.lhys.utils.LiuheUtil;
import com.lh16808.app.lhys.utils.dataUtils.ToastUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by admin on 2016/10/14.
 */
public class TiaoJianChaxunActivity extends BaseActivity {
    public List<HistoryRecordBean> mDatas = new ArrayList<>();
    public ProgressDialogMy mPro;
    public CardView cv_reload;
    public Button mBtnReload;
    //查询年份
    int curyear;
    //查询关键字
    String sousuo = "01";
    //查询类型
    int type;
    TiaoJianDialog tiaojianDialog;
    TextView tv_cx;

    @Override
    protected void initLoadData() {

    }

    @Override
    protected void initVariables() {

    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.lhc_cx_tiaojian_activity);

        Intent intent = getIntent();
        sousuo = TextUtils.isEmpty(intent.getStringExtra("sousuo")) ? "01" : intent.getStringExtra("sousuo");
        curyear = intent.getIntExtra("year", DateTimeUtil.GetYear());


        tv_cx = (TextView) findViewById(R.id.tv_cx);
        tv_cx.setText("查詢年份：" + curyear + "，關鍵字：" + sousuo);

        mPro = new ProgressDialogMy(TiaoJianChaxunActivity.this);

        tiaojianDialog = new TiaoJianDialog(TiaoJianChaxunActivity.this, curyear);
        tiaojianDialog.setOnQuedingListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sousuo = tiaojianDialog.sousuo;
                type = tiaojianDialog.type;
                if (mDatas.size() == 0 || curyear != tiaojianDialog.year) {
                    getData(tiaojianDialog.year);
                } else {
                    tableData();
                }
                tiaojianDialog.dismiss();
            }
        });
        tiaojianDialog.setOnQuxiaoListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiaojianDialog.dismiss();
            }
        });

        setActionbar();


        scr_sheji = (HorizontalScrollView) findViewById(R.id.scr_sheji);
        scr_table = (ScrollView) findViewById(R.id.scr_table);
        table_title = (TableLayout) findViewById(R.id.table_title);
        table_data = (TableLayout) findViewById(R.id.table_data);

        cv_reload = (CardView) findViewById(R.id.cv_reload);
        mBtnReload = (Button) findViewById(R.id.btn_reload);


        mBtnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(curyear);
            }
        });
        getData(curyear);
    }


    private void setActionbar() {
        TextView toolBarTitle = (TextView) findViewById(R.id.tool_bar_title);
        if (toolBarTitle != null) {
            toolBarTitle.setText(getResources().getString(R.string.lhc_chaxun_tjcx));
        }
        TextView tv_share = (TextView) findViewById(R.id.tv_share);
        tv_share.setText(getResources().getString(R.string.lhc_chaxun_xztj));
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiaojianDialog.show();
            }
        });
    }


    @Override
    protected void loadData() {

    }

    HorizontalScrollView scr_sheji;
    ScrollView scr_table;
    TableLayout table_title;
    TableLayout table_data;
    //表头
    TableRow tableRow_title;
    //表内容
    List<TableRow> tableRow_datas = new ArrayList<>();
    //选中行号
    int tableRow_datas_id = -1;
    //表数据
    TableDataBean tableData;
    //分页加载状态
    int jzzt = 0;
    //第一页加载数量
    int fistpagenum = 200;
    //第一页以后每页加载数量
    int pagenum = 100;
    //表头
    String[] titles = new String[]{"日期/期數", "一", "二", "三", "四", "五", "六", "特碼"};


    public void tableData() {
        tv_cx.setText("查詢年份：" + curyear + "，關鍵字：" + sousuo);
        TableDataBean tableDataBean = new TableDataBean();
        tableDataBean.setStr_title(Arrays.asList(titles));
        List<List<String>> str_data = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            List<String> list = new ArrayList<>();
            list.add(DateFormatUtils.format(mDatas.get(i).getNewstime(), "MM-dd") + "/" + mDatas.get(i).getBq());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(mDatas.get(i).getNewstime()) * 1000L);
            if (type == 1) {
                list.add(LiuheUtil.getSX_ft(mDatas.get(i).getZ1sx()));
                list.add(LiuheUtil.getSX_ft(mDatas.get(i).getZ2sx()));
                list.add(LiuheUtil.getSX_ft(mDatas.get(i).getZ3sx()));
                list.add(LiuheUtil.getSX_ft(mDatas.get(i).getZ4sx()));
                list.add(LiuheUtil.getSX_ft(mDatas.get(i).getZ5sx()));
                list.add(LiuheUtil.getSX_ft(mDatas.get(i).getZ6sx()));
                list.add(LiuheUtil.getSX_ft(mDatas.get(i).getTmsx()));
            } else if (type == 2) {
                list.add(LiuheUtil.getRBG_string(mDatas.get(i).getZ1m()));
                list.add(LiuheUtil.getRBG_string(mDatas.get(i).getZ2m()));
                list.add(LiuheUtil.getRBG_string(mDatas.get(i).getZ3m()));
                list.add(LiuheUtil.getRBG_string(mDatas.get(i).getZ4m()));
                list.add(LiuheUtil.getRBG_string(mDatas.get(i).getZ5m()));
                list.add(LiuheUtil.getRBG_string(mDatas.get(i).getZ6m()));
                list.add(LiuheUtil.getRBG_string(mDatas.get(i).getTm()));
            } else if (type == 3) {
                list.add(LiuheUtil.getHaoma_WuXing(calendar, Integer.parseInt(mDatas.get(i).getZ1m())));
                list.add(LiuheUtil.getHaoma_WuXing(calendar, Integer.parseInt(mDatas.get(i).getZ2m())));
                list.add(LiuheUtil.getHaoma_WuXing(calendar, Integer.parseInt(mDatas.get(i).getZ3m())));
                list.add(LiuheUtil.getHaoma_WuXing(calendar, Integer.parseInt(mDatas.get(i).getZ4m())));
                list.add(LiuheUtil.getHaoma_WuXing(calendar, Integer.parseInt(mDatas.get(i).getZ5m())));
                list.add(LiuheUtil.getHaoma_WuXing(calendar, Integer.parseInt(mDatas.get(i).getZ6m())));
                list.add(LiuheUtil.getHaoma_WuXing(calendar, Integer.parseInt(mDatas.get(i).getTm())));
            } else if (type == 4) {
                list.add(LiuheUtil.getHaoma_JiaYe(mDatas.get(i).getZ1sx()));
                list.add(LiuheUtil.getHaoma_JiaYe(mDatas.get(i).getZ2sx()));
                list.add(LiuheUtil.getHaoma_JiaYe(mDatas.get(i).getZ3sx()));
                list.add(LiuheUtil.getHaoma_JiaYe(mDatas.get(i).getZ4sx()));
                list.add(LiuheUtil.getHaoma_JiaYe(mDatas.get(i).getZ5sx()));
                list.add(LiuheUtil.getHaoma_JiaYe(mDatas.get(i).getZ6sx()));
                list.add(LiuheUtil.getHaoma_JiaYe(mDatas.get(i).getTmsx()));
            } else {
                list.add(mDatas.get(i).getZ1m());
                list.add(mDatas.get(i).getZ2m());
                list.add(mDatas.get(i).getZ3m());
                list.add(mDatas.get(i).getZ4m());
                list.add(mDatas.get(i).getZ5m());
                list.add(mDatas.get(i).getZ6m());
                list.add(mDatas.get(i).getTm());
            }
            str_data.add(list);
        }
        tableDataBean.setStr_data(str_data);
        table(tableDataBean);
    }

    public void table(TableDataBean tableDataBean) {
        tableData = tableDataBean;
        tableclear();
        setTableTitle(tableData.getStr_title());
        for (int i = 0; i < fistpagenum && i < tableDataBean.getStr_data().size(); i++) {
            setTableData(tableDataBean.getStr_data().get(i), i);
        }
        scr_table.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO 自动生成的方法存根
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        break;
                    case MotionEvent.ACTION_MOVE:
                        int scrollY = v.getScrollY();
                        int height = v.getHeight();
                        int scrollViewMeasuredHeight = scr_table.getChildAt(0)
                                .getMeasuredHeight();
                        if (scrollY == 0) {
                            // System.out.println("滑动到了顶端 view.getScrollY()="
                            // + scrollY);
                        }

                        if (tableRow_datas.size() < tableData.getStr_data().size()
                                && jzzt == 0
                                && (scrollY + height) == scrollViewMeasuredHeight) {
                            mPro.showpop();
                            jzzt = 1;
                            new Thread() {
                                public void run() {
                                    try {
                                        sleep(1);
                                        Message message = new Message();
                                        message.what = 3;
                                        handler1.sendMessage(message);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.start();
                        }
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
        setTableRowWidth();
    }

    public void tableclear() {
        table_title.removeAllViews();
        table_data.removeAllViews();
        tableRow_datas.clear();
    }

    private void setTableRowWidth() {
        if (tableRow_datas.size() > 0) {
            table_data.post(new Runnable() {
                @Override
                public void run() {
                    int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
                    double wid = screenWidth * 0.9 - Math.max(tableRow_title.getWidth(), tableRow_datas.get(0).getWidth());
                    if (wid > 0) {
                        for (int i = 0; i < tableRow_title.getChildCount() && i < tableRow_datas.get(0).getChildCount(); i++) {
                            // 获取数据行中的子元素
                            View view1 = tableRow_datas.get(0).getChildAt(i);
                            // 获取标题行中的子元素
                            View view2 = tableRow_title.getChildAt(i);
                            // 获取子元素的宽度
                            int wo = view1.getWidth();
                            int wo2 = view2.getWidth();
                            int endw = (int) (Math.max(wo, wo2) + wid / tableRow_datas.get(0).getChildCount());
                            view1.setLayoutParams(new TableRow.LayoutParams(endw,
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                            view2.setLayoutParams(new TableRow.LayoutParams(endw,
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                        }
                    } else {
                        // 遍历标题行的子元素
                        for (int i = 0; i < tableRow_title.getChildCount() && i < tableRow_datas.get(0).getChildCount(); i++) {
                            // 获取数据行中的子元素
                            View view1 = tableRow_datas.get(0).getChildAt(i);
                            // 获取标题行中的子元素
                            View view2 = tableRow_title.getChildAt(i);
                            // 获取子元素的宽度
                            int wo = view1.getWidth();
                            int wo2 = view2.getWidth();
                            // 设置子元素的宽度
                            if (wo > wo2) {
                                view2.setLayoutParams(new TableRow.LayoutParams(wo,
                                        ViewGroup.LayoutParams.MATCH_PARENT));
                            } else if (wo < wo2) {
                                view1.setLayoutParams(new TableRow.LayoutParams(wo2,
                                        ViewGroup.LayoutParams.MATCH_PARENT));
                            }
                        }
                    }
                    mPro.cancel();
                }
            });
        }
    }

    Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    jzzt = 0;
                    mPro.cancel();
                    break;
                case 3:
                    int k = tableRow_datas.size();
                    for (int i = k; i < k + pagenum
                            && i < tableData.getStr_data().size(); i++) {
                        setTableData(tableData.getStr_data().get(i), i);
                    }
                    setTableRowWidth();
                    Message message = new Message();
                    message.what = 1;
                    handler1.sendMessage(message);
                    break;
            }
            super.handleMessage(msg);
        }
    };


    public void setTableTitle(List<String> str) {
        tableRow_title = new TableRow(this);
        for (int i = 0; i < str.size(); i++) {
            tableRow_title.addView(getView(str.get(i), R.drawable.my_table_title_shape));
        }
        table_title.addView(tableRow_title);
    }

    public void setTableData(List<String> str, final int con) {
        final TableRow tableRow_data = new TableRow(this);
        for (int i = 0; i < str.size(); i++) {
            tableRow_data.addView(getView(str.get(i),
                    R.drawable.my_table_data_shape_1));
        }
        tableRow_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tableRow_datas_id != -1
                        && tableRow_datas_id < tableRow_datas.size()) {
                    for (int i = 0; i < tableRow_datas.get(tableRow_datas_id)
                            .getChildCount(); i++) {
                        View view = tableRow_datas.get(tableRow_datas_id)
                                .getChildAt(i);
                        view.setBackgroundResource(R.drawable.my_table_data_shape_1);
                        TextView textView = (TextView) view.findViewById(R.id.tv_1);
                        textView.setTextColor(0xff000000);
                    }
                }
                tableRow_datas_id = con;
                for (int i = 0; i < tableRow_data.getChildCount(); i++) {
                    View view = tableRow_data.getChildAt(i);
                    view.setBackgroundResource(R.drawable.my_table_data_shape_2);
                    TextView textView = (TextView) view.findViewById(R.id.tv_1);
                    textView.setTextColor(0xffffffff);
                }
            }
        });
        table_data.addView(tableRow_data);
        tableRow_datas.add(tableRow_data);
    }

    public View getView(String str, int back) {
        View view = LayoutInflater.from(this).inflate(R.layout.lhc_cx_tiaojian_table_item, null);
        view.setBackgroundResource(back);
        TextView tv = (TextView) view.findViewById(R.id.tv_1);
        tv.setTextColor(0xff000000);
        tv.setText(str);
        if (str.equals(sousuo)) {
            tv.setBackgroundResource(R.drawable.lhc_shape_tv_yuan_1);
        }
        view.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    public void getData(final int year) {
        mPro.showpop();
        AsyncHttpClientUtil.getInstance().get(ConstantsUtil.HISTORY_RECORD(year + ""), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                curyear = year;
                String response = new String(responseBody);
                response = ConstantsUtil.replaceAll(response);
                // json转为带泛型的list
                List<HistoryRecordBean> mList = GsonUtil.buildGson().fromJson(response,
                        new TypeToken<List<HistoryRecordBean>>() {
                        }.getType());
                if (mList.size() > 0) {
                    mDatas.clear();
                    mDatas.addAll(mList);
                    tableData();
                } else {
                    mPro.cancel();
                }
                if (mDatas.size() == 0) {
                    mBtnReload.setText(year + "年數據還沒有，點擊加載" + (year - 1) + "數據");
                    curyear = year - 1;
                    cv_reload.setVisibility(View.VISIBLE);
                } else {
                    cv_reload.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mPro.cancel();
                ToastUtil.onFailure(TiaoJianChaxunActivity.this, statusCode);
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
