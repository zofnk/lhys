package com.lh16808.app.lhys.model;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/9/20.
 */

public abstract class BannerData {
    /**
     * 获取Banner外层布局
     *
     * @return
     */
    public abstract RelativeLayout getRelativeLayout();

    /**
     * 获取viewpager
     *
     * @return
     */
    public abstract ViewPager getViewPager();

    /**
     * 获取小圆点的布局
     *
     * @return
     */
    public abstract LinearLayout getLinearLayout();

    public abstract Context getContextX();

    /**
     * 获取广告数据接口
     *
     * @return
     */
    public abstract String getUrl();
}