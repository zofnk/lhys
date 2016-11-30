package com.lh16808.app.lhys.widget.magnetonview;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.lh16808.app.lhys.R;


public class CustomTextView extends TextView implements View.OnClickListener {

    public CustomTextView(Context context) {
        super(context);
        initView();
    }

    public void setTextListener(TextClickaBle mTextClickaBle) {
        this.mTextClickaBle = mTextClickaBle;
    }

    TextClickaBle mTextClickaBle;

    //设置文字内容调用这个方法
    public void setViewText(String text) {
        this.setText("ID" + "\n" + text);
    }

    private void initView() {
        setTextColor(Color.parseColor("#F5511E"));
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        setGravity(Gravity.CENTER);
        this.setBackgroundResource(R.color.cardview_dark_background);
        this.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mTextClickaBle.onClickListen();
    }

    public interface TextClickaBle extends OnClickListener {
        void onClickListen();
    }
}
