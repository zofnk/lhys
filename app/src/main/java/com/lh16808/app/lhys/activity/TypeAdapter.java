package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.marco.Constants;


/**
 * Created by Administrator on 2016/12/1.
 */

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeHolder> {
    private Context context;

    public TypeAdapter(Context context, TypeAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
        this.context = context;
    }

    @Override
    public TypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TypeHolder(LayoutInflater.from(context).inflate(R.layout.item_caipiao, parent, false));
    }

    @Override
    public void onBindViewHolder(final TypeHolder holder, int position) {
        holder.tvTitle.setText(Constants.caipiaotitle[position]);
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return Constants.caipiaotitle.length;
    }

    class TypeHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public TypeHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_sy_grid);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private TypeAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(TypeAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
