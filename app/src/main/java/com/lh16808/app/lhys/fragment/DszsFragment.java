package com.lh16808.app.lhys.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.activity.ZoushifenxiActivity;
import com.lh16808.app.lhys.base.BaseFragment;
import com.lh16808.app.lhys.model.TableDataBean;
import com.lh16808.app.lhys.other.ProgressDialogMy;
import com.lh16808.app.lhys.utils.LiuheUtil;
import com.lh16808.app.lhys.widget.MyHScrollView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DszsFragment extends BaseFragment {
    private View view;
    private ZoushifenxiActivity mActivity;
    public ProgressDialogMy mPro;

    public RecyclerView mRecyclerView;
    MyAdapter myAdapter;
    LinearLayout mHead;
    MyHScrollView headSrcrollView;
    TableRow tr_1, tr_2, tr_3;
    List<List<String>> mDatas = new ArrayList<>();

    //查询年份
    int curyear = 0;


    //总列数
    int column_all;
    //固定列数
    int column_gd;
    //非固定列数
    int column_fgd;
    //表列宽
    int[] widths;
    //选中行号
    int tableRow_datas_id = -1;
    //表数据
    TableDataBean tableData;
    //分页加载状态
    int jzzt = 0;
    //第一页加载数量
    int fistpagenum = 50;
    //第一页以后每页加载数量
    int pagenum = 100;

    @Override
    protected void initVariables() {

    }

    @Override
    protected View initViews(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.lhc_zsfx_fragment_all, container, false);
            mActivity = (ZoushifenxiActivity) getActivity();

            mHead = (LinearLayout) view.findViewById(R.id.head);
            mHead.addView(inflater.inflate(R.layout.lhc_zsfx_table_title_ds, container, false));
            headSrcrollView = (MyHScrollView) mHead.findViewById(R.id.horizontalScrollView1);
            mHead.setFocusable(true);
            mHead.setClickable(true);
            mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());


            tr_1 = (TableRow) view.findViewById(R.id.tr_1);
            tr_2 = (TableRow) view.findViewById(R.id.tr_2);
            tr_3 = (TableRow) view.findViewById(R.id.tr_3);

            //总列数
            column_all = tr_1.getChildCount() + tr_2.getChildCount();
            //固定列数
            column_gd = tr_1.getChildCount();
            //非固定列数
            column_fgd = tr_2.getChildCount();
            widths = new int[column_all];


            mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
            mRecyclerView.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

            myAdapter = new MyAdapter(mActivity, mDatas, new MyItemClickListener() {
                @Override
                public void onItemClick(View view, int postion) {
                    //点击
                    System.out.println(postion + "sssssssssssss");
                }
            });
            RecyclerView.LayoutManager lm = new LinearLayoutManager(mActivity);
            mRecyclerView.setLayoutManager(lm);
            mRecyclerView.setAdapter(myAdapter);

            mPro = new ProgressDialogMy(mActivity);
        }
        return view;
    }

    @Override
    public void loadData() {
        if (mActivity.chaxun == 1) {
            if (curyear != mActivity.curyear) {
                curyear = mActivity.curyear;
                tableData();
            }
        }
    }



    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {


        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            // 当在列头 和 listView控件上touch时，将这个touch的事件分发给 ScrollView
            headSrcrollView.onTouchEvent(arg1);
            return false;
        }
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        public List<List<String>> mDatas;
        public Context mContext;
        MyItemClickListener onClickListener;

        public MyAdapter(Context mContext, List<List<String>> mDatas, MyItemClickListener onClickListener) {
            this.mContext = mContext;
            this.mDatas = mDatas;
            this.onClickListener = onClickListener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.lhc_zsfx_table_data_ds, parent, false);
            MyViewHolder mVH = new MyViewHolder(view);
            return mVH;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            List<String> str = mDatas.get(position);

            int bsid = LiuheUtil.isRBG(str.get(1));
            int dsid = (Integer.parseInt(str.get(1)) + 1) % 2;
            int tdsid = (Integer.parseInt(str.get(1)) / 10 + 1) % 2;
            int hdsid = (Integer.parseInt(str.get(1)) / 10 +
                    Integer.parseInt(str.get(1)) % 10 + 1) % 2;
            for (int i = 0; i < holder.textViews.length; i++) {
                TextView textview = holder.textViews[i];
                if (i == 0) {
                    textview.setText(str.get(i));
                    textview.setBackgroundColor(0x00000000);
                    holder.lins[i].setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                } else if (i == 1) {
                    textview.setText(str.get(i));
                    if (bsid == 0) {
                        textview.setBackgroundResource(R.drawable.my_table_data_shape_fang_red);
                    } else if (bsid == 1) {
                        textview.setBackgroundResource(R.drawable.my_table_data_shape_fang_blue);
                    } else if (bsid == 2) {
                        textview.setBackgroundResource(R.drawable.my_table_data_shape_fang_green);
                    }
                    holder.lins[i].setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                } else if ((dsid == 0 && dsid + 2 == i) || (dsid == 1 && dsid + 2 == i)) {
                    textview.setText(dsid == 0 ? "單" : "雙");
                    if (bsid == 0) {
                        textview.setBackgroundResource(R.drawable.my_table_data_shape_yuan_red);
                    } else if (bsid == 1) {
                        textview.setBackgroundResource(R.drawable.my_table_data_shape_yuan_blue);
                    } else if (bsid == 2) {
                        textview.setBackgroundResource(R.drawable.my_table_data_shape_yuan_green);
                    }
                    holder.lins[i].setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                } else if ((tdsid == 0 && tdsid + 4 == i) || (tdsid == 1 && tdsid + 4 == i)) {
                    textview.setText(tdsid == 0 ? "單" : "雙");
                    if (bsid == 0) {
                        textview.setBackgroundResource(R.drawable.my_table_data_shape_yuan_red);
                    } else if (bsid == 1) {
                        textview.setBackgroundResource(R.drawable.my_table_data_shape_yuan_blue);
                    } else if (bsid == 2) {
                        textview.setBackgroundResource(R.drawable.my_table_data_shape_yuan_green);
                    }
                    holder.lins[i].setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                } else if ((hdsid == 0 && hdsid + 6 == i) || (hdsid == 1 && hdsid + 6 == i)) {
                    textview.setText(hdsid == 0 ? "單" : "雙");
                    if (bsid == 0) {
                        textview.setBackgroundResource(R.drawable.my_table_data_shape_yuan_red);
                    } else if (bsid == 1) {
                        textview.setBackgroundResource(R.drawable.my_table_data_shape_yuan_blue);
                    } else if (bsid == 2) {
                        textview.setBackgroundResource(R.drawable.my_table_data_shape_yuan_green);
                    }
                    holder.lins[i].setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                } else {
                    textview.setBackgroundColor(0x00000000);
                    textview.setText(" ");
                }
            }

            setWidth(holder);

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onClickListener.onItemClick(v, position);
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView[] textViews = new TextView[8];
            LinearLayout[] lins = new LinearLayout[8];
            MyHScrollView scrollView;

            public MyViewHolder(View itemView) {
                super(itemView);
                scrollView = (MyHScrollView) itemView.findViewById(R.id.horizontalScrollView1);

                textViews[0] = (TextView) itemView.findViewById(R.id.tv_1);
                textViews[1] = (TextView) itemView.findViewById(R.id.tv_2);
                textViews[2] = (TextView) itemView.findViewById(R.id.tv_3);
                textViews[3] = (TextView) itemView.findViewById(R.id.tv_4);
                textViews[4] = (TextView) itemView.findViewById(R.id.tv_5);
                textViews[5] = (TextView) itemView.findViewById(R.id.tv_6);
                textViews[6] = (TextView) itemView.findViewById(R.id.tv_7);
                textViews[7] = (TextView) itemView.findViewById(R.id.tv_8);


                lins[0] = (LinearLayout) itemView.findViewById(R.id.lin_1);
                lins[1] = (LinearLayout) itemView.findViewById(R.id.lin_2);
                lins[2] = (LinearLayout) itemView.findViewById(R.id.lin_3);
                lins[3] = (LinearLayout) itemView.findViewById(R.id.lin_4);
                lins[4] = (LinearLayout) itemView.findViewById(R.id.lin_5);
                lins[5] = (LinearLayout) itemView.findViewById(R.id.lin_6);
                lins[6] = (LinearLayout) itemView.findViewById(R.id.lin_7);
                lins[7] = (LinearLayout) itemView.findViewById(R.id.lin_8);

                headSrcrollView.AddOnScrollChangedListener(new OnScrollChangedListenerImp(
                        scrollView));
            }
        }

        class OnScrollChangedListenerImp implements MyHScrollView.OnScrollChangedListener {
            MyHScrollView mScrollViewArg;

            public OnScrollChangedListenerImp(MyHScrollView scrollViewar) {
                mScrollViewArg = scrollViewar;
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                mScrollViewArg.smoothScrollTo(l, t);
            }
        }
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int postion);
    }





    public void tableData() {
        TableDataBean tableDataBean = new TableDataBean();
        List<String> str_title = new ArrayList<>();
        for (int i = 0; i < column_fgd; i++) {
            str_title.add("0");
        }
        List<List<String>> str_data = new ArrayList<>();
        for (int i = 0; i < mActivity.mDatas.size(); i++) {
            List<String> list = new ArrayList<>();
            list.add(mActivity.mDatas.get(i).getBq() + "");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(mActivity.mDatas.get(i).getNewstime()) * 1000L);
            list.add(mActivity.mDatas.get(i).getTm());

            int dsid = (Integer.parseInt(mActivity.mDatas.get(i).getTm()) + 1) % 2;
            str_title.set(dsid, (Integer.parseInt(str_title.get(dsid)) + 1) + "");
            int tdsid = (Integer.parseInt(mActivity.mDatas.get(i).getTm()) / 10 + 1) % 2;
            str_title.set(tdsid + 2, (Integer.parseInt(str_title.get(tdsid + 2)) + 1) + "");
            int hdsid = (Integer.parseInt(mActivity.mDatas.get(i).getTm()) / 10 +
                    Integer.parseInt(mActivity.mDatas.get(i).getTm()) % 10 + 1) % 2;
            str_title.set(hdsid + 4, (Integer.parseInt(str_title.get(hdsid + 4)) + 1) + "");

            str_data.add(list);
        }
        tableDataBean.setStr_title(str_title);
        tableDataBean.setStr_data(str_data);
        table(tableDataBean);
    }

    public void table(TableDataBean tableDataBean) {
        tableData = tableDataBean;
        setTableTitle(tableData.getStr_title());

        mDatas.clear();
        mDatas.addAll(tableDataBean.getStr_data());
        myAdapter.notifyDataSetChanged();
    }


    private void setWidth(final MyAdapter.MyViewHolder holder) {
        if (mDatas.size() > 0) {
            holder.itemView.post(new Runnable() {
                @Override
                public void run() {
                    int screenWidth = mActivity.getWindowManager().getDefaultDisplay().getWidth();
                    double wid = screenWidth *0.95- Math.max(tr_1.getWidth() + tr_2.getWidth(), holder.itemView.getWidth());
                    if (wid > 0) {
                        for (int i = 0; i < tr_1.getChildCount() + tr_2.getChildCount() && i < holder.lins.length; i++) {
                            // 获取数据行中的子元素
                            View view1 = holder.lins[i];
                            // 获取标题行中的子元素
                            View view2;
                            if (i < tr_1.getChildCount()) {
                                view2 = tr_1.getChildAt(i);
                            } else {
                                view2 = tr_2.getChildAt(i - tr_1.getChildCount());
                            }
                            // 获取子元素的宽度
                            int wo = view1.getWidth();
                            int wo2 = view2.getWidth();
                            widths[i] = Math.max(Math.max(wo, wo2), widths[i]);
                            int endw = (int) (widths[i] + wid / holder.lins.length);
                            view1.setLayoutParams(new TableRow.LayoutParams(endw,
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                            view2.setLayoutParams(new TableRow.LayoutParams(endw,
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                        }
                    } else {
                        // 遍历标题行的子元素
                        for (int i = 0; i < tr_1.getChildCount() + tr_2.getChildCount() && i < holder.lins.length; i++) {
                            // 获取数据行中的子元素
                            View view1 = holder.lins[i];
                            // 获取标题行中的子元素
                            View view2;
                            if (i < tr_1.getChildCount()) {
                                view2 = tr_1.getChildAt(i);
                            } else {
                                view2 = tr_2.getChildAt(i - tr_1.getChildCount());
                            }
                            // 获取子元素的宽度
                            int wo = view1.getWidth();
                            int wo2 = view2.getWidth();
                            widths[i] = Math.max(Math.max(wo, wo2), widths[i]);
                            view1.setLayoutParams(new TableRow.LayoutParams(widths[i],
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                            view2.setLayoutParams(new TableRow.LayoutParams(widths[i],
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                        }
                    }
                    setTableRowWidth();
                }
            });
        }
    }

    boolean iswidth = false;

    private void setTableRowWidth() {
        if (!iswidth) {
            iswidth = true;
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (iswidth) {
                                iswidth = false;
                                if (mDatas.size() > 0) {
                                    mRecyclerView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            for (int k = 0; k < mRecyclerView.getChildCount(); k++) {
                                                View view = mRecyclerView.getChildAt(k);
                                                if (null != mRecyclerView.getChildViewHolder(view)) {
                                                    final MyAdapter.MyViewHolder holder = (MyAdapter.MyViewHolder) mRecyclerView.getChildViewHolder(view);
                                                    for (int i = 0; i < tr_1.getChildCount() + tr_2.getChildCount() && i < holder.lins.length; i++) {
                                                        // 获取数据行中的子元素
                                                        View view1 = holder.lins[i];
                                                        // 获取标题行中的子元素
                                                        View view2;
                                                        if (i < tr_1.getChildCount()) {
                                                            view2 = tr_1.getChildAt(i);
                                                        } else {
                                                            view2 = tr_2.getChildAt(i - tr_1.getChildCount());
                                                        }
                                                        // 获取子元素的宽度
                                                        int wo = view1.getWidth();
                                                        int wo2 = view2.getWidth();
                                                        // 设置子元素的宽度
                                                        widths[i] = Math.max(Math.max(wo, wo2), widths[i]);
                                                        view1.setLayoutParams(new TableRow.LayoutParams(widths[i],
                                                                ViewGroup.LayoutParams.MATCH_PARENT));
                                                        view2.setLayoutParams(new TableRow.LayoutParams(widths[i],
                                                                ViewGroup.LayoutParams.MATCH_PARENT));
                                                    }
                                                    holder.itemView.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            holder.scrollView.scrollTo(headSrcrollView.getScrollX(), headSrcrollView.getScrollY());
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }).start();
        }
    }

//    Handler handler1 = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    jzzt = 0;
////                    mPro.cancel();
//                    break;
//                case 3:
//                    int k = tableRow_datas.size();
//                    for (int i = k; i < k + pagenum
//                            && i < tableData.getStr_data().size(); i++) {
//                        setTableData(tableData.getStr_data().get(i), i);
//                    }
//                    setTableRowWidth();
//                    Message message = new Message();
//                    message.what = 1;
//                    handler1.sendMessage(message);
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    };


    public void setTableTitle(List<String> str) {
        for (int i = 0; i < str.size(); i++) {
            TextView textview = (TextView) tr_3.getChildAt(i);
            textview.setText(str.get(i));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
