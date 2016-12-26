package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.utils.LiuheUtil;

import java.util.List;
import java.util.Map;



public class LengReAdapter extends RecyclerView.Adapter<LengReAdapter.MyViewHolder> {

    public List<Map.Entry<String, Integer>> mDatas;
    public Context mContext;
    MyItemClickListener longClickListener;

    public LengReAdapter(Context mContext, List<Map.Entry<String, Integer>> mDatas, MyItemClickListener longClickListener) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.longClickListener = longClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lhc_cx_lengre_item, parent, false);
        MyViewHolder mVH = new MyViewHolder(view);
        return mVH;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Map.Entry<String, Integer> lottery = mDatas.get(position);

        holder.tv_1.setText(lottery.getKey());
        holder.tv_2.setText(lottery.getValue() + "");
        holder.tv_1.setBackgroundResource(LiuheUtil.getRBG_mipmap(lottery.getKey()));

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

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_1 = (TextView) itemView.findViewById(R.id.tv_1);
            tv_2 = (TextView) itemView.findViewById(R.id.tv_2);
        }
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }
}
