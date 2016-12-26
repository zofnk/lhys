package com.lh16808.app.lhys.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

/**
 * 自定义数字文字选择
 */
public class NumberPickerView extends NumberPicker {

    public NumberPickerView(Context context) {
        super(context);
    }

    public NumberPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    /**
     * 设置字体颜色大小
     *
     * @param view
     */
    public void updateView(View view) {
        if (view instanceof EditText) {
            ((EditText) view).setTextColor(Color.parseColor("#000000"));
            ((EditText) view).setTextSize(16);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * 设置分割线颜�?
     *
     * @param
     */
    public void setNumberPickerDividerColor(int color) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    // 设置分割线的颜色
                    pf.set(this, new ColorDrawable(color));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
