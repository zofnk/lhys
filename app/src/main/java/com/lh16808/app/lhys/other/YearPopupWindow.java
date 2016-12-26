package com.lh16808.app.lhys.other;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.utils.DateTimeUtil;
import com.lh16808.app.lhys.widget.NumberPickerView;


/**
 * 年份选择弹出窗口
 */
public class YearPopupWindow {
    PopupWindow popupWindow;
    int value;
    Context context;
    String[] getYear;
    public int year;
    View.OnClickListener listener;

    public YearPopupWindow(Context context, View.OnClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void showPopupWindow(int y) {
        showPopupWindow(y, ((Activity) context).getWindow());
    }

    public void showPopupWindow(int y, final Window window) {
        year = y;
        if (popupWindow == null) {
            getYear = DateTimeUtil.getYear();
            View contentView = ((Activity) context).getLayoutInflater().inflate(R.layout.my_popup_layout_year, null);
            final TextView tvTitle = (TextView) contentView.findViewById(R.id.tvTitle);
            final NumberPickerView np_province = (NumberPickerView) contentView.findViewById(R.id.np_edit_province);
            np_province.setNumberPickerDividerColor(0xffffb921);
            np_province.setDescendantFocusability(NumberPickerView.FOCUS_BLOCK_DESCENDANTS);
            np_province.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    tvTitle.setText("年份：" + Integer.parseInt(picker.getDisplayedValues()[picker.getValue()]));
                }
            });

            np_province.setMinValue(0);
            np_province.setMaxValue(0);
            np_province.setDisplayedValues(getYear);
            np_province.setMaxValue(getYear.length - 1);
            tvTitle.setText("年份：" + Integer.parseInt(np_province.getDisplayedValues()[np_province.getValue()]));

            contentView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    np_province.setValue(value);
                    tvTitle.setText("年份：" + np_province.getDisplayedValues()[np_province.getValue()]);
                    popupWindow.dismiss();
                }
            });
            contentView.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int ch = Integer.parseInt(np_province.getDisplayedValues()[np_province.getValue()]);
                    if (year != ch) {
                        value = np_province.getValue();
                        year = ch;
                        listener.onClick(view);
                    }
                    popupWindow.dismiss();
                }
            });
            popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.style.my_popwin_anim_style);
            ColorDrawable cd = new ColorDrawable(0x000000);
            popupWindow.setBackgroundDrawable(cd);

            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                // 在dismiss中恢复透明度
                public void onDismiss() {
                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            WindowManager.LayoutParams lp = window.getAttributes();
                            lp.alpha = 1f;
                            window.setAttributes(lp);
                        }
                    }, 300);
                }
            });
        }
        // 产生背景变暗效果
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.4f;
        window.setAttributes(lp);

        popupWindow.showAtLocation(window.peekDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 150, 0);

        popupWindow.update();
    }
}
