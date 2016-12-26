package com.lh16808.app.lhys.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.utils.LiuheUtil;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;


public class BshmFragment extends Fragment implements SwipyRefreshLayout.OnRefreshListener {
    private View view;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<String> list = new ArrayList<>();
    private ChangshiAdapter mAdapter;
    private FragmentActivity mActivity;

    private void autoRefresh(boolean b) {
        mSwipyRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipyRefreshLayout.setRefreshing(true);
            }
        });
        if (b) {
            this.onRefresh(SwipyRefreshLayoutDirection.TOP);
        } else {
            this.onRefresh(SwipyRefreshLayoutDirection.BOTTOM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.lhc_cs_fragment, container, false);

            mActivity = getActivity();

            mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_zl_list);
            mSwipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.srl_zl_refresh);
            initAdapter();
        }
        return view;
    }


    private void initAdapter() {
        mSwipyRefreshLayout.setOnRefreshListener(this);
        autoRefresh(true);

        //使RecyclerView保持固定的大小,这样会提高RecyclerView的性能。
        mRecyclerView.setHasFixedSize(true);
// * 设置布局管理器，listview风格则设置为LinearLayoutManager
// * gridview风格则设置为GridLayoutManager
// * pu瀑布流风格的设置为StaggeredGridLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        // 设置item分割线
//        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                super.onDraw(c, parent, state);
//            }
//        });
//        mRecyclerView.addItemDecoration(new RecyclerViewDecoration(mActivity, LinearLayoutManager.VERTICAL));
        list.add("紅波");
        list.add("藍波");
        list.add("綠波");
        mAdapter = new ChangshiAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
    }

//    private void iniData() {
//        mSwipyRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                mSwipyRefreshLayout.setRefreshing(false);
//            }
//        });
//        List<List<String>> retList = LiuheUtil.getShengxiaohaoma(nlyear);
//        list.addAll(retList);
//        if (mAdapter != null) {
//            mAdapter.notifyDataSetChanged();
//        }
//    }

    int nlyear;

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        mSwipyRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipyRefreshLayout.setRefreshing(false);
            }
        });
//        if (direction == SwipyRefreshLayoutDirection.TOP) {
//            ToastUtil.toastShow(mActivity, "刷新中...");
//            list.clear();
//            Calendar calendar = Calendar.getInstance();
//            nlyear = DateTimeUtil.solarToLunar(calendar)[0];
//            iniData();
//        } else {
//            ToastUtil.toastShow(mActivity, "加載中...");
//            nlyear--;
//            iniData();
//        }
    }


    public static class ChangshiAdapter extends RecyclerView.Adapter {
        public interface OnRecyclerViewListener {
            void onItemClick(int position);

            boolean onItemLongClick(int position);
        }

        private OnRecyclerViewListener onRecyclerViewListener;

        public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
            this.onRecyclerViewListener = onRecyclerViewListener;
        }

        private List<String> list;

        public ChangshiAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lhc_cs_item_bs, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new PersonViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            PersonViewHolder holder = (PersonViewHolder) viewHolder;
            holder.position = i;
            TextView[] textViews = {holder.tv_1, holder.tv_2, holder.tv_3,
                    holder.tv_4, holder.tv_5, holder.tv_6, holder.tv_7, holder.tv_8,
                    holder.tv_9, holder.tv_10, holder.tv_11, holder.tv_12, holder.tv_13,
                    holder.tv_14, holder.tv_15, holder.tv_16, holder.tv_17};
            holder.tv_name.setText(list.get(i));
            int[] ints = new int[0];
            if (i == 0) {
                ints = LiuheUtil.redbo;
            } else if (i == 1) {
                ints = LiuheUtil.bulebo;
            } else if (i == 2) {
                ints = LiuheUtil.greenbo;
            }
            for (int j = 0; j < textViews.length; j++) {
                if (j < ints.length) {
                    textViews[j].setVisibility(View.VISIBLE);
                    textViews[j].setText(String.format("%02d", ints[j]));
                    textViews[j].setBackgroundResource(LiuheUtil.getRBG_mipmap(String.valueOf(ints[j])));
                } else {
                    textViews[j].setVisibility(View.GONE);
                }
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
            public View rootView;
            public TextView tv_name, tv_1, tv_2, tv_3, tv_4, tv_5, tv_6,
                    tv_7, tv_8, tv_9, tv_10, tv_11, tv_12, tv_13, tv_14, tv_15, tv_16, tv_17;
            public int position;

            public PersonViewHolder(View itemView) {
                super(itemView);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_1 = (TextView) itemView.findViewById(R.id.tv_1);
                tv_2 = (TextView) itemView.findViewById(R.id.tv_2);
                tv_3 = (TextView) itemView.findViewById(R.id.tv_3);
                tv_4 = (TextView) itemView.findViewById(R.id.tv_4);
                tv_5 = (TextView) itemView.findViewById(R.id.tv_5);
                tv_6 = (TextView) itemView.findViewById(R.id.tv_6);
                tv_7 = (TextView) itemView.findViewById(R.id.tv_7);
                tv_8 = (TextView) itemView.findViewById(R.id.tv_8);
                tv_9 = (TextView) itemView.findViewById(R.id.tv_9);
                tv_10 = (TextView) itemView.findViewById(R.id.tv_10);
                tv_11 = (TextView) itemView.findViewById(R.id.tv_11);
                tv_12 = (TextView) itemView.findViewById(R.id.tv_12);
                tv_13 = (TextView) itemView.findViewById(R.id.tv_13);
                tv_14 = (TextView) itemView.findViewById(R.id.tv_14);
                tv_15 = (TextView) itemView.findViewById(R.id.tv_15);
                tv_16 = (TextView) itemView.findViewById(R.id.tv_16);
                tv_17 = (TextView) itemView.findViewById(R.id.tv_17);

                rootView = itemView.findViewById(R.id.lin_1);
                rootView.setOnClickListener(this);
                rootView.setOnLongClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (null != onRecyclerViewListener) {
                    onRecyclerViewListener.onItemClick(position);
                }
            }

            @Override
            public boolean onLongClick(View v) {
                if (null != onRecyclerViewListener) {
                    return onRecyclerViewListener.onItemLongClick(position);
                }
                return false;
            }
        }

    }


}
