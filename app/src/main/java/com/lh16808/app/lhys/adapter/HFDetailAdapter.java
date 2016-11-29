package com.lh16808.app.lhys.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.model.HFModel;
import com.lh16808.app.lhys.utils.dataUtils.DateFormatUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/24.
 */

public class HFDetailAdapter extends RecyclerView.Adapter<HFDetailAdapter.MyViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;
    private ArrayList<HFModel> mDatas = new ArrayList<>();

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void addDatas(ArrayList<HFModel> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new MyViewHolder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hf_layout, parent, false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);
        HFModel hfModel = mDatas.get(pos);
        holder.tvName.setText(hfModel.getHfusername());
        holder.tvNewtime.setText(DateFormatUtils.formatWithMDHm(Long.parseLong(hfModel.getHfnewstime()) * 1000));
        holder.tvText.setText(hfModel.getHfText());
        holder.tvSum.setText(pos + 2 + "#");
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSum;
        private TextView tvName;
        private TextView tvNewtime;
        private TextView tvText;

        public MyViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) return;
            tvName = (TextView) itemView.findViewById(R.id.tv_Name);
            tvNewtime = (TextView) itemView.findViewById(R.id.tv_newstime);
            tvText = (TextView) itemView.findViewById(R.id.tv_text);
            tvSum = (TextView) itemView.findViewById(R.id.tv_sum);
        }
    }
}
