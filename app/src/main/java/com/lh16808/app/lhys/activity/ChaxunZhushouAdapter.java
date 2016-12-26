package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.model.ZyBean;

import java.util.List;


public class ChaxunZhushouAdapter extends RecyclerView.Adapter<ChaxunZhushouAdapter.MyViewHolder> {

    public List<ZyBean> mDatas;
    public Context mContext;
    MyItemClickListener longClickListener;

    public ChaxunZhushouAdapter(Context mContext, List<ZyBean> mDatas, MyItemClickListener longClickListener) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.longClickListener = longClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lhc_cxzs_item, parent, false);
        MyViewHolder mVH = new MyViewHolder(view);
        return mVH;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_1.setText(mDatas.get(position).getTitle());
        holder.tv_2.setText(mDatas.get(position).getData());
        holder.iv_1.setImageResource(mDatas.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                longClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_1;
        private TextView tv_2;
        ImageView iv_1;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_1 = (TextView) itemView.findViewById(R.id.tv_1);
            tv_2 = (TextView) itemView.findViewById(R.id.tv_2);
            iv_1 = (ImageView) itemView.findViewById(R.id.iv_1);
        }
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }


}
