package com.lh16808.app.lhys.activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.other.YearPopupWindow;


public class TiaoJianDialog extends Dialog implements RadioGroup.OnCheckedChangeListener {
    private Button btn_qx, btn_qd, btn_year;
    Context context;
    View mView;

    int year;
    int type = 0;
    YearPopupWindow yearPopupWindow;
    TextView tv_cx;
    RadioGroup[] radioGroups = new RadioGroup[12];

    public TiaoJianDialog(Context context, int year) {
        super(context, R.style.lhl_dialog);
        this.context = context;
        this.year = year;

        setCustomDialog();
        // fxDialog.setCancelable(false);

        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.height = LinearLayout.LayoutParams.MATCH_PARENT; // 高度设置为屏幕的0.6
        p.width = LinearLayout.LayoutParams.MATCH_PARENT; // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
    }

    private void setCustomDialog() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.lhc_cx_tiaojian_diaog_item, null);
        tv_cx = (TextView) mView.findViewById(R.id.tv_cx);
        tv_cx.setText("查詢年份：" + year + "，關鍵字：" + sousuo);
        radioGroups[0] = (RadioGroup) mView.findViewById(R.id.rg_hm_1);
        radioGroups[1] = (RadioGroup) mView.findViewById(R.id.rg_hm_2);
        radioGroups[2] = (RadioGroup) mView.findViewById(R.id.rg_hm_3);
        radioGroups[3] = (RadioGroup) mView.findViewById(R.id.rg_hm_4);
        radioGroups[4] = (RadioGroup) mView.findViewById(R.id.rg_hm_5);
        radioGroups[5] = (RadioGroup) mView.findViewById(R.id.rg_hm_6);
        radioGroups[6] = (RadioGroup) mView.findViewById(R.id.rg_hm_7);
        radioGroups[7] = (RadioGroup) mView.findViewById(R.id.rg_sx_1);
        radioGroups[8] = (RadioGroup) mView.findViewById(R.id.rg_sx_2);
        radioGroups[9] = (RadioGroup) mView.findViewById(R.id.rg_bs);
        radioGroups[10] = (RadioGroup) mView.findViewById(R.id.rg_wx);
        radioGroups[11] = (RadioGroup) mView.findViewById(R.id.rg_jy);
        for (int i = 0; i < radioGroups.length; i++) {
            radioGroups[i].setOnCheckedChangeListener(this);
        }
        type = 0;
        radioGroups[0].setVisibility(View.VISIBLE);
        radioGroups[1].setVisibility(View.VISIBLE);
        radioGroups[2].setVisibility(View.VISIBLE);
        radioGroups[3].setVisibility(View.VISIBLE);
        radioGroups[4].setVisibility(View.VISIBLE);
        radioGroups[5].setVisibility(View.VISIBLE);
        radioGroups[6].setVisibility(View.VISIBLE);
        radioGroups[7].setVisibility(View.GONE);
        radioGroups[8].setVisibility(View.GONE);
        radioGroups[9].setVisibility(View.GONE);
        radioGroups[10].setVisibility(View.GONE);
        radioGroups[11].setVisibility(View.GONE);
        RadioGroup rg_1 = (RadioGroup) mView.findViewById(R.id.rg_1);
        rg_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_1:
                        type = 0;
                        radioGroups[0].setVisibility(View.VISIBLE);
                        radioGroups[1].setVisibility(View.VISIBLE);
                        radioGroups[2].setVisibility(View.VISIBLE);
                        radioGroups[3].setVisibility(View.VISIBLE);
                        radioGroups[4].setVisibility(View.VISIBLE);
                        radioGroups[5].setVisibility(View.VISIBLE);
                        radioGroups[6].setVisibility(View.VISIBLE);
                        radioGroups[7].setVisibility(View.GONE);
                        radioGroups[8].setVisibility(View.GONE);
                        radioGroups[9].setVisibility(View.GONE);
                        radioGroups[10].setVisibility(View.GONE);
                        radioGroups[11].setVisibility(View.GONE);
                        break;
                    case R.id.rb_2:
                        type = 1;
                        radioGroups[0].setVisibility(View.GONE);
                        radioGroups[1].setVisibility(View.GONE);
                        radioGroups[2].setVisibility(View.GONE);
                        radioGroups[3].setVisibility(View.GONE);
                        radioGroups[4].setVisibility(View.GONE);
                        radioGroups[5].setVisibility(View.GONE);
                        radioGroups[6].setVisibility(View.GONE);
                        radioGroups[7].setVisibility(View.VISIBLE);
                        radioGroups[8].setVisibility(View.VISIBLE);
                        radioGroups[9].setVisibility(View.GONE);
                        radioGroups[10].setVisibility(View.GONE);
                        radioGroups[11].setVisibility(View.GONE);
                        break;
                    case R.id.rb_3:
                        type = 2;
                        radioGroups[0].setVisibility(View.GONE);
                        radioGroups[1].setVisibility(View.GONE);
                        radioGroups[2].setVisibility(View.GONE);
                        radioGroups[3].setVisibility(View.GONE);
                        radioGroups[4].setVisibility(View.GONE);
                        radioGroups[5].setVisibility(View.GONE);
                        radioGroups[6].setVisibility(View.GONE);
                        radioGroups[7].setVisibility(View.GONE);
                        radioGroups[8].setVisibility(View.GONE);
                        radioGroups[9].setVisibility(View.VISIBLE);
                        radioGroups[10].setVisibility(View.GONE);
                        radioGroups[11].setVisibility(View.GONE);
                        break;
                    case R.id.rb_4:
                        type = 3;
                        radioGroups[0].setVisibility(View.GONE);
                        radioGroups[1].setVisibility(View.GONE);
                        radioGroups[2].setVisibility(View.GONE);
                        radioGroups[3].setVisibility(View.GONE);
                        radioGroups[4].setVisibility(View.GONE);
                        radioGroups[5].setVisibility(View.GONE);
                        radioGroups[6].setVisibility(View.GONE);
                        radioGroups[7].setVisibility(View.GONE);
                        radioGroups[8].setVisibility(View.GONE);
                        radioGroups[9].setVisibility(View.GONE);
                        radioGroups[10].setVisibility(View.VISIBLE);
                        radioGroups[11].setVisibility(View.GONE);
                        break;
                    case R.id.rb_5:
                        type = 4;
                        radioGroups[0].setVisibility(View.GONE);
                        radioGroups[1].setVisibility(View.GONE);
                        radioGroups[2].setVisibility(View.GONE);
                        radioGroups[3].setVisibility(View.GONE);
                        radioGroups[4].setVisibility(View.GONE);
                        radioGroups[5].setVisibility(View.GONE);
                        radioGroups[6].setVisibility(View.GONE);
                        radioGroups[7].setVisibility(View.GONE);
                        radioGroups[8].setVisibility(View.GONE);
                        radioGroups[9].setVisibility(View.GONE);
                        radioGroups[10].setVisibility(View.GONE);
                        radioGroups[11].setVisibility(View.VISIBLE);
                        break;
                }

            }
        });
        btn_qx = (Button) mView.findViewById(R.id.btn_qx);
        btn_qd = (Button) mView.findViewById(R.id.btn_qd);
        btn_year = (Button) mView.findViewById(R.id.btn_year);
        yearPopupWindow = new YearPopupWindow(context, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = yearPopupWindow.year;
                tv_cx.setText("查詢年份：" + year + "，關鍵字：" + sousuo);
            }
        });
        btn_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yearPopupWindow.showPopupWindow(year, getWindow());
            }
        });
        super.setContentView(mView);
    }


    @Override
    public void setContentView(int layoutResID) {
    }


    @Override
    public void setContentView(View view) {
    }

    /**
     * 确定键监听器
     *
     * @param listener
     */
    public void setOnQuedingListener(View.OnClickListener listener) {
        btn_qd.setOnClickListener(listener);
    }

    /**
     * 取消键监听器
     *
     * @param listener
     */
    public void setOnQuxiaoListener(View.OnClickListener listener) {
        btn_qx.setOnClickListener(listener);
    }


    String sousuo = "01";
    Boolean changeedGroup = false;

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (!changeedGroup) {
            changeedGroup = true;

            for (int i = 0; i < radioGroups.length; i++) {
                if (group != radioGroups[i]) {
                    radioGroups[i].clearCheck();
                }
            }
            RadioButton rb = (RadioButton) mView.findViewById(checkedId);
            sousuo = rb.getText().toString().replaceAll("家禽", "家").replaceAll("野獸", "野");
            tv_cx.setText("查詢年份：" + year + "，關鍵字：" + sousuo);
            changeedGroup = false;
        }
    }
}
