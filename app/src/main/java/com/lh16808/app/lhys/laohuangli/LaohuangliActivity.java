package com.lh16808.app.lhys.laohuangli;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.laohuangli.page.MagicBookView;
import com.lh16808.app.lhys.laohuangli.page.PageContainer;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class LaohuangliActivity extends BaseActivity {
    MagicBookView mBookView;
    View view1;
    View view2;
    View view3;
    //开始日期
    Calendar startCalendar;
    //结束日期
    Calendar endCalendar;
    int dayCount;
    int curPage;
    PageContainer.IPageContainer pre, cur, next;

    public static void start(Context context) {
        Intent starter = new Intent(context, LaohuangliActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initLoadData() {

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.laohuangli_activity);
        //标题栏
        setActionbar();
        // 广告子fragment
//        CycleViewPager mCycleViewPager = (CycleViewPager) getSupportFragmentManager().findFragmentById(R.id.cycleViewPager1);
        //获取广告
//        HttpUtils.loadAD(this, mCycleViewPager, ConstantsUtil.AD_CPZQ);

        //执行导入.db文件
        LhlSQLitedb.getSQLiteDatabase(LaohuangliActivity.this);

        mBookView = (MagicBookView) findViewById(R.id.bookview);
        final LayoutInflater inflater = LayoutInflater.from(this);
        view1 = inflater.inflate(R.layout.laohuangli_item, null);
        view2 = inflater.inflate(R.layout.laohuangli_item, null);
        view3 = inflater.inflate(R.layout.laohuangli_item, null);
        pre = new PageContainer.IPageContainer() {
            /*初始化回调函数
             * page 初始化时需要加载的页号
             * container 该页面的容器
             */
            @Override
            public void onInit(int page, PageContainer container) {
                // TODO Auto-generated method stub
                setData(view1, page);
                container.setBackgroundColor(Color.WHITE);
                container.setContent(view1, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.START));
            }

            /* 翻页回调函数
             * isTurnBack 是否为回翻
             * currentPage 当前翻到的页号
             * needReloadPage 该页面需要重新加载的页号。
             * container 该页面的容器
             */
            @Override
            public void onTurnReload(boolean isTurnBack, int currentPage, int needReloadPage,
                                     PageContainer container) {
                // TODO Auto-generated method stub
                setData(view1, needReloadPage);
            }

            /* 设置当前页回调函数
             * page 需要加载的页号
             * container 该页面的容器
             */
            @Override
            public void onSetPage(int page, PageContainer container) {
                // TODO Auto-generated method stub

            }

        };

        cur = new PageContainer.IPageContainer() {

            @Override
            public void onInit(int page, PageContainer container) {
                // TODO Auto-generated method stub
                setData(view2, page);
                container.setBackgroundColor(Color.WHITE);
                container.setContent(view2, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.START));
            }

            @Override
            public void onTurnReload(boolean isTurnBack, int currentPage, int needReloadPage,
                                     PageContainer container) {
                // TODO Auto-generated method stub
                setData(view2, needReloadPage);
            }

            @Override
            public void onSetPage(int page, PageContainer container) {
                // TODO Auto-generated method stub

            }

        };

        next = new PageContainer.IPageContainer() {

            @Override
            public void onInit(int page, PageContainer container) {
                // TODO Auto-generated method stub
                setData(view3, page);
                container.setBackgroundColor(Color.WHITE);
                container.setContent(view3, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.START));
            }

            @Override
            public void onTurnReload(boolean isTurnBack, int currentPage, int needReloadPage,
                                     PageContainer container) {
                // TODO Auto-generated method stub
                setData(view3, needReloadPage);
            }

            @Override
            public void onSetPage(int page, PageContainer container) {
                // TODO Auto-generated method stub

            }

        };

        startCalendar = new GregorianCalendar();
        startCalendar.set(1901, Calendar.JANUARY, 1);
        endCalendar = new GregorianCalendar();
        endCalendar.set(2099, Calendar.DECEMBER, 31, 23, 59, 59);
        dayCount = (int) ((endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis())
                / (1000 * 3600 * 24));//从间隔毫秒变成间隔天数
        Calendar calendar = Calendar.getInstance();
        curPage = (int) ((calendar.getTimeInMillis() - startCalendar.getTimeInMillis())
                / (1000 * 3600 * 24));//从间隔毫秒变成间隔天数
        //页面
        mBookView.initBookView(dayCount + 1, curPage, pre, cur, next);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setActionbar() {
        //标题
        TextView toolBarTitle = (TextView) findViewById(R.id.tool_bar_title);
        if (toolBarTitle != null) {
            toolBarTitle.setText(getResources().getString(R.string.lhl));
        }
        //选择日期
        TextView tv_share = (TextView) findViewById(R.id.tv_share);
        if (tv_share != null) {
            tv_share.setText(getResources().getString(R.string.lhl_xzrq));
            tv_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DateDialog dateDialog = new DateDialog(LaohuangliActivity.this, startCalendar, Calendar.getInstance(), endCalendar);
                    dateDialog.setOnQuedingListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar calendar = new GregorianCalendar();
                            calendar.set(dateDialog.datePicker.getYear(), dateDialog.datePicker.getMonth(), dateDialog.datePicker.getDayOfMonth());
                            mBookView.setCurrentPage((int) ((calendar.getTimeInMillis() - startCalendar.getTimeInMillis())
                                    / (1000 * 3600 * 24)));//从间隔毫秒变成间隔天数
                            dateDialog.dismiss();
                        }
                    });
                    dateDialog.setOnQuxiaoListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dateDialog.dismiss();
                        }
                    });
                    dateDialog.show();
                }
            });
        }
    }


    public void setData(final View view, int page) {
        final Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(startCalendar.getTimeInMillis());
        calendar.add(Calendar.DATE, page);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);


        ImageView iv_left = (ImageView) view.findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                mBookView.doTurnIfNeed(true, false);
                mBookView.changeMode(true);
            }
        });
        ImageView iv_right = (ImageView) view.findViewById(R.id.iv_right);
        iv_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mBookView.doTurnIfNeed(false, true);
                mBookView.changeMode(true);
            }
        });
        LinearLayout lin_all = (LinearLayout) view.findViewById(R.id.lin_all);
        lin_all.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //让翻页能监听到
            }
        });

        final TextView tv_day = (TextView) view.findViewById(R.id.tv_day);
        final TextView tv_month_ch = (TextView) view.findViewById(R.id.tv_month_ch);
        TextView tv_monch_en = (TextView) view.findViewById(R.id.tv_monch_en);
        TextView tv_year = (TextView) view.findViewById(R.id.tv_year);

        TextView tv_week_en = (TextView) view.findViewById(R.id.tv_week_en);
        TextView tv_week_ch = (TextView) view.findViewById(R.id.tv_week_ch);
        TextView tv_nlny = (TextView) view.findViewById(R.id.tv_nlny);
        TextView tv_nlday = (TextView) view.findViewById(R.id.tv_nlday);


        tv_day.setText(calendar.get(Calendar.DATE) + "");
        tv_day.post(new Runnable() {
            @Override
            public void run() {
                float fontScale = getResources().getDisplayMetrics().scaledDensity;
                float size = (int) ((Math.min(tv_day.getHeight() * 0.8f, tv_day.getWidth() * 0.5f) / fontScale + 0.5f) / 5) * 5f;
                tv_day.setTextSize(size);
                Rect bounds = new Rect();
                TextPaint paint = tv_day.getPaint();
                paint.getTextBounds(tv_day.getText().toString(), 0, tv_day.getText().toString().length(), bounds);
                int width = bounds.width();
                if (width > tv_day.getWidth() * 0.9) {
                    size = (int) ((tv_day.getWidth() * 0.9f / width * size) / 5) * 5f;
                    tv_day.setTextSize(size);
                }
                if (size < 20) {
                    tv_day.setText("");
                    tv_month_ch.setText(tv_month_ch.getText() + "," + calendar.get(Calendar.DATE) + "日");
                }
            }
        });
        tv_month_ch.setText(DateTimeUtil.getMonth(calendar).get(0));
        tv_monch_en.setText(DateTimeUtil.getMonth(calendar).get(1));
        tv_year.setText(calendar.get(Calendar.YEAR) + "年");


        tv_week_en.setText(DateTimeUtil.getWeek(calendar).get(1));
        tv_week_ch.setText(DateTimeUtil.getWeek(calendar).get(0));
        int[] solarToLunar = DateTimeUtil.solarToLunar(year, month, day);
        List<String> nllist = DateTimeUtil.getNongliMonth(solarToLunar[0], solarToLunar[1], solarToLunar[2],
                solarToLunar[3], solarToLunar[4]);
        tv_nlny.setText(nllist.get(0) + " " + nllist.get(1));
        tv_nlday.setText(nllist.get(2));

        TextView tv_tgdz = (TextView) view.findViewById(R.id.tv_tgdz);
        TextView tv_y = (TextView) view.findViewById(R.id.tv_y);
        TextView tv_jsyq = (TextView) view.findViewById(R.id.tv_jsyq);
        TextView tv_lhjs = (TextView) view.findViewById(R.id.tv_lhjs);
        TextView tv_xsyj = (TextView) view.findViewById(R.id.tv_xsyj);
        TextView tv_j = (TextView) view.findViewById(R.id.tv_j);

        TextView tv_scjx = (TextView) view.findViewById(R.id.tv_scjx);
        TextView tv_chongsha = (TextView) view.findViewById(R.id.tv_chongsha);
        TextView tv_xs = (TextView) view.findViewById(R.id.tv_xs);
        TextView tv_fs = (TextView) view.findViewById(R.id.tv_fs);
        TextView tv_cs = (TextView) view.findViewById(R.id.tv_cs);
        TextView tv_bj = (TextView) view.findViewById(R.id.tv_bj);
        TextView tv_rwx = (TextView) view.findViewById(R.id.tv_rwx);

        String sky_earth_Branch_day = DateTimeUtil.sky_earth_Branch_day(year, month, day);
        tv_tgdz.setText(sky_earth_Branch_day);

        Cursor cursor = LhlSQLitedb.getSQLiteDatabase(this).rawQuery("select * from  base where yue='" + month + "' and tgdz='" + sky_earth_Branch_day + "';", null);
        if (cursor.moveToFirst()) {//判断游标是否为空
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.move(i);//移动到指定记录
                String jrts = cursor.getString(cursor.getColumnIndex("jrts"));
                String jsyq = cursor.getString(cursor.getColumnIndex("jsyq"));
                String y = cursor.getString(cursor.getColumnIndex("y"));
                String xsyj = cursor.getString(cursor.getColumnIndex("xsyj"));
                String j = cursor.getString(cursor.getColumnIndex("j"));
                tv_y.setText(y);
                tv_jsyq.setText(jsyq);
                tv_xsyj.setText(xsyj);
                tv_j.setText(j);
            }
        } else {
            tv_y.setText("暫無");
            tv_jsyq.setText("暫無");
            tv_xsyj.setText("暫無");
            tv_j.setText("暫無");
        }
        cursor.close();


        cursor = LhlSQLitedb.getSQLiteDatabase(this).rawQuery("select * from  ganzhi where tgdz='" + sky_earth_Branch_day + "';", null);
        if (cursor.moveToFirst()) {//判断游标是否为空
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.move(i);//移动到指定记录
                String bj = cursor.getString(cursor.getColumnIndex("bj"));
                String rwx = cursor.getString(cursor.getColumnIndex("rwx"));
                String chongsha = cursor.getString(cursor.getColumnIndex("chongsha"));
                String jrts = cursor.getString(cursor.getColumnIndex("jrts"));
                String xs = cursor.getString(cursor.getColumnIndex("xs"));
                String fs = cursor.getString(cursor.getColumnIndex("fs"));
                String cs = cursor.getString(cursor.getColumnIndex("cs"));
                String scjx = cursor.getString(cursor.getColumnIndex("scjx"));
                tv_bj.setText(bj);
                tv_rwx.setText(rwx);
                tv_chongsha.setText(chongsha);
//                tv_jrts.setText(jrts);
                tv_xs.setText(xs);
                tv_fs.setText(fs);
                tv_cs.setText(cs);
                tv_scjx.setText(scjx);
            }
        } else {
            tv_bj.setText("暫無");
            tv_rwx.setText("暫無");
            tv_chongsha.setText("暫無");
//                tv_jrts.setText(jrts);
            tv_xs.setText("暫無");
            tv_fs.setText("暫無");
            tv_cs.setText("暫無");
            tv_scjx.setText("暫無");
        }
        cursor.close();

        cursor = LhlSQLitedb.getSQLiteDatabase(this).rawQuery("select * from  lhjs where date='" + year + "-" + month + "-" + day + "';", null);
        if (cursor.moveToFirst()) {//判断游标是否为空
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.move(i);//移动到指定记录
                String no = cursor.getString(cursor.getColumnIndex("no"));
                String bs = cursor.getString(cursor.getColumnIndex("bs"));
                String num = cursor.getString(cursor.getColumnIndex("num"));
                String textone = cursor.getString(cursor.getColumnIndex("textone"));
                String texttwo = cursor.getString(cursor.getColumnIndex("texttwo"));
                tv_lhjs.setText(num + " " + bs);
            }
        } else {
            tv_lhjs.setText("暫無");
        }
        cursor.close();


        LinearLayout lin_week = (LinearLayout) view.findViewById(R.id.lin_week);
        int shengxiao1[]=new int[]{R.mipmap.laohuangli_sx_hou_1,R.mipmap.laohuangli_sx_ji_1,
                R.mipmap.laohuangli_sx_gou_1,R.mipmap.laohuangli_sx_zhu_1,R.mipmap.laohuangli_sx_shu_1,
                R.mipmap.laohuangli_sx_niu_1,R.mipmap.laohuangli_sx_hu_1,
                R.mipmap.laohuangli_sx_tu_1,R.mipmap.laohuangli_sx_long_1,R.mipmap.laohuangli_sx_she_1,
                R.mipmap.laohuangli_sx_ma_1,R.mipmap.laohuangli_sx_yang_1,};
        int shengxiao2[]=new int[]{R.mipmap.laohuangli_sx_hou_2,R.mipmap.laohuangli_sx_ji_2,
                R.mipmap.laohuangli_sx_gou_2,R.mipmap.laohuangli_sx_zhu_2,R.mipmap.laohuangli_sx_shu_2,
                R.mipmap.laohuangli_sx_niu_2,R.mipmap.laohuangli_sx_hu_2,
                R.mipmap.laohuangli_sx_tu_2,R.mipmap.laohuangli_sx_long_2,R.mipmap.laohuangli_sx_she_2,
                R.mipmap.laohuangli_sx_ma_2,R.mipmap.laohuangli_sx_yang_2,};
        int color = 0xffAD5800;
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            color = 0xffCA0204;
            lin_week.setBackgroundResource(shengxiao2[solarToLunar[0]% 12]);
        } else {
            color = 0xffAD5800;
            lin_week.setBackgroundResource(shengxiao1[solarToLunar[0]% 12]);
        }

        tv_day.setTextColor(color);
        tv_month_ch.setTextColor(color);
        tv_monch_en.setTextColor(color);
        tv_year.setTextColor(color);

        tv_nlny.setTextColor(color);
        tv_nlday.setTextColor(color);
        tv_week_en.setTextColor(color);
        tv_week_ch.setTextColor(color);

        tv_tgdz.setTextColor(color);

        tv_y.setTextColor(color);
        tv_jsyq.setTextColor(color);
        tv_xsyj.setTextColor(color);
        tv_j.setTextColor(color);
        tv_bj.setTextColor(color);
        tv_rwx.setTextColor(color);
        tv_chongsha.setTextColor(color);
        tv_xs.setTextColor(color);
        tv_fs.setTextColor(color);
        tv_cs.setTextColor(color);
        tv_scjx.setTextColor(color);
        tv_lhjs.setTextColor(color);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBookView.recycleBitmap();
    }
}