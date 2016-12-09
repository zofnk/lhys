package com.lh16808.app.lhys.laohuangli;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;


import com.lh16808.app.lhys.R;

import java.util.Calendar;

public class DateDialog extends Dialog {
    public DatePicker datePicker;
    private Button btn_qx, btn_qd;
    private TextView title;
    Context context;
    Calendar startCalendar;
    Calendar calendar;
    Calendar endCalendar;

    public DateDialog(Context context, Calendar startCalendar, Calendar calendar, Calendar endCalendar) {
        super(context, R.style.lhl_dialog);
        this.context = context;
        this.startCalendar = startCalendar;
        this.calendar = calendar;
        this.endCalendar = endCalendar;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.laohuangli_date, null);
        datePicker = (DatePicker) mView.findViewById(R.id.datepicker);
        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
        datePicker.setMinDate(startCalendar.getTimeInMillis());
        datePicker.setMaxDate(endCalendar.getTimeInMillis());

        btn_qx = (Button) mView.findViewById(R.id.btn_qx);
        btn_qd = (Button) mView.findViewById(R.id.btn_qd);
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
}
