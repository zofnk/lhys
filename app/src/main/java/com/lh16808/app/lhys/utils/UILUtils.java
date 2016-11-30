package com.lh16808.app.lhys.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * 图片请求
 *
 * @author Administrator
 */
public class UILUtils {

    private static RequestManager with;
    private static Context context;

    public static void clearCache() {
    }

    public static void displayImage(Context context , String imageUrls, ImageView mImageView) {
        if (context != null)
            Glide.with(context).load(imageUrls).into(mImageView);
    }

    public static void initOptions(Context context) {
        UILUtils.context = context;
    }
}
