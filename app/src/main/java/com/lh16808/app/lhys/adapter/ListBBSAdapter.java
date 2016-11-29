package com.lh16808.app.lhys.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.model.CollectModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/25.
 */

public class ListBBSAdapter extends RecyclerView.Adapter<ListBBSAdapter.MyHolder> {
    public ListBBSAdapter(Context context, List<CollectModel> lists, OnItemClickLitener l) {
        this.context = context;
        this.lists = lists;
        mOnItemClickLitener = l;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListBBSAdapter.MyHolder(LayoutInflater.from(context).inflate(R.layout.item_collect, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        CollectModel collectModel = lists.get(position);
        holder.tvTitle.setText(collectModel.getBt());

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

//            }
        }

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public MyHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_item_title);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private Context context;
    private List<CollectModel> lists = new ArrayList<>();
    private ListBBSAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(ListBBSAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
