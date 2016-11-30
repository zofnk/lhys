package com.lh16808.app.lhys.widget.magnetonview;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseAPP;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/7/2
 */
public class MagnetonView extends FrameLayout {

    float mCenterR = 63.0f;
    float mCircleR = 26.0f;
    Random random = new Random();


    int mCenterX, mCenterY;
    int mOutsideMAX = DimensionUtil.dip2px(getContext(), 150); //定义最高偏移半径
    ArrayList<TextView> mCircles = new ArrayList<>();
    ArrayList<CircleViewPoint> mCirclesDatas = new ArrayList<>();
    CircleViewPoint mCenterPoint = new CircleViewPoint(0, 0, DimensionUtil.dip2px(getContext(), mCenterR));
    CustomTextView mCenterView = new CustomTextView(getContext());

    public MagnetonView(Context context) {
        super(context);
        initSetViews(null);
    }

    public MagnetonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSetViews(attrs);
    }

    public MagnetonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSetViews(attrs);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initSetViews(AttributeSet attrs) {
        mCenterView.setBackground(getResources().getDrawable(R.mipmap.ic_launcher));
        mCenterView.setEnabled(false);
        addObzData(mCenterPoint, mCenterView);
    }


    /**
     * when view pop ,to locate in somewhere ?  translate and floating
     *
     * @param circleViewPoint
     * @param view
     */
    public void locateAnimation(CircleViewPoint circleViewPoint, View view) {
        ObjectAnimator.ofFloat(view, "translationX", view.getX(), circleViewPoint.getTranslateX()).setDuration(1000).start();
        ObjectAnimator.ofFloat(view, "translationY", view.getY(), circleViewPoint.getTranslateY()).setDuration(1000).start();
        floatAnimation(view);
    }

    public void floatAnimation(View view) {
    }

    public void addSubView(CustomTextView circleView) {
        try {
            int _x = randomSke();
            int _y = randomSke();
            int i = 0;
            CircleViewPoint circle = new CircleViewPoint(_x, _y, DimensionUtil.dip2px(getContext(), mCircleR));
            for (CircleViewPoint tempcir : mCirclesDatas) {
                if (!MathUtils.measureDistance(circle, tempcir)) {
                    addSubView(circleView);
                    break;
                }
                i++;
            }
            if (i == mCirclesDatas.size()) {
                addObzData(circle, circleView);
            }

        } catch (StackOverflowError e) {

//            Toast.makeText(getContext(), "出不来了", Toast.LENGTH_SHORT).show();
//            mCircleR=mCircleR-2;
            Log.d("MagnetonView", "出不来了");
        }
    }

    public void addObzData(CircleViewPoint circle, CustomTextView centetView) {
        mCirclesDatas.add(circle);
//        MagneText view = new MagneText(getContext());
        LayoutParams lytp = new LayoutParams(circle.getCircleR() * 2, circle.getCircleR() * 2, Gravity.CENTER);
//        view.setText(circle.toString());
//        view.setGravity(Gravity.CENTER);
//        view.setTextSize(10);

        addView(centetView, lytp);
        locateAnimation(circle, centetView);
    }

    private int randomSke() {
        return random.nextInt(mOutsideMAX + mOutsideMAX) - mOutsideMAX;
    }

}
