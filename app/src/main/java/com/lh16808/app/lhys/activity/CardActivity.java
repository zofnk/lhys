package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.marco.Constants;
import com.lh16808.app.lhys.utils.AppLog;
import com.lh16808.app.lhys.utils.RandomUtils;
import com.lh16808.app.lhys.utils.SharedPreUtils;

import java.util.ArrayList;

public class CardActivity extends BaseActivity {
    private GridView mGridView;
    ArrayList<Integer> list = new ArrayList<>();
    ArrayList<Integer> list2 = new ArrayList<>();
    int index_size = 0;
    private CardAdapter mAdapter;
    private boolean isCanTurnCards;
    private int[] num = new int[3];
    private View view1;

    public static void start(Context context) {
        Intent starter = new Intent(context, CardActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initLoadData() {
        isCanTurnCards = SharedPreUtils.getBoolean(Constants.CAN_TURN_CARDS, true);
        if (isCanTurnCards) {
            num = getRandom();
            for (int i = 0; i < Constants.SXCard.length; i++) {
                list.add(Constants.SXCard[i]);
            }
            AppLog.redLog(CardActivity.class.getName() + "TRUE", "" + num[0] + "-" + num[1] + "-" + num[2]);
        } else {
            String str[] = SharedPreUtils.getString(Constants.TURN_CARDS_RESULT, "").split(",");
            num[0] = Integer.parseInt(str[0]);
            num[1] = Integer.parseInt(str[1]);
            num[2] = Integer.parseInt(str[2]);
            AppLog.redLog(CardActivity.class.getName() + "NOT", "" + num[0] + "-" + num[1] + "-" + num[2]);
        }
        if (!isCanTurnCards) {
            for (int i = 0; i < num.length; i++) {
                list.add(Constants.SXCard[num[i]]);
            }
        }
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_card);
        mGridView = (GridView) findViewById(R.id.gv_u_card);
        view1 = findViewById(R.id.tv_promt);
        if (!isCanTurnCards) {
            CardActivity.this.view1.setVisibility(View.VISIBLE);
        }
        mAdapter = new CardAdapter();
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isCanTurnCards) {
                    if (index_size < 3) {
                        ImageView iv = (ImageView) view.findViewById(R.id.img_card_1);
                        AnimationDrawable drawable = (AnimationDrawable) iv.getDrawable();
                        Integer id1 = list.get(num[index_size]);
                        Drawable drawable1 = CardActivity.this.getResources().getDrawable(id1);
                        drawable.addFrame(drawable1, 100);
                        drawable.start();
                        list2.add(id1);
                        if (list2.size() == 3) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String str = "";
                                    for (int i = 0; i < num.length; i++) {
                                        str = str + num[i] + ",";
                                    }
                                    SharedPreUtils.putString(Constants.TURN_CARDS_RESULT, str.substring(0, str.length()));
                                    SharedPreUtils.putBoolean(Constants.CAN_TURN_CARDS, false);
                                    list.clear();
                                    list.addAll(list2);
                                    mAdapter.notifyDataSetChanged();
                                    CardActivity.this.view1.setVisibility(View.VISIBLE);
                                }
                            }, 550);
                        }
                        index_size++;
                    }
                }
            }
        });
    }

    private int[] getRandom() {
        int[] num = new int[3];
        num[0] = RandomUtils.getRandom(12);
        while (true) {
            int v = RandomUtils.getRandom(12);
            if (num[0] != v) {
                num[1] = v;
                break;
            }

        }
        while (true) {
            int c = RandomUtils.getRandom(12);
            if (num[0] != c && num[1] != c) {
                num[2] = c;
                break;
            }
        }
        return num;
    }

    @Override
    protected void loadData() {

    }


    class CardAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(CardActivity.this).inflate(R.layout.item_layout_card, null);
                viewHolder.iv = (ImageView) convertView.findViewById(R.id.img_card_1);
                AnimationDrawable drawable = (AnimationDrawable) viewHolder.iv.getDrawable();
                drawable.stop();
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (list.size() == 3) {
                viewHolder.iv.setImageResource(list.get(position));
            }
            return convertView;
        }

        class ViewHolder {
            ImageView iv;
        }
    }
}
