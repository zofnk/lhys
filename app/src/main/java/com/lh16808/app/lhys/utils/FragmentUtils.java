package com.lh16808.app.lhys.utils;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.lh16808.app.lhys.R;


/**
 * Created by Administrator on 2016/6/28.
 */
public class FragmentUtils {

    public static void backStack(FragmentActivity activity) {
        FragmentManager manager = activity.getSupportFragmentManager();
        manager.popBackStack();
    }

    /**
     * @param fragmentManager 布局管理器
     * @param res             需要添加的viewGroup
     * @param fragment        需要添加的fragment
     */
    public static void addFragment(FragmentManager fragmentManager, @IdRes int res, Fragment fragment) {
        fragmentManager
                .beginTransaction()
//                .setCustomAnimations(R.anim.slide_open_enter, R.anim.slide_close_exit, R.anim.slide_open_enter, R.anim.slide_close_exit)
                .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom, R.anim.slide_in_bottom, R.anim.slide_out_bottom)
                .add(res, fragment)
                .commit();
    }

    /**
     * 切换fragment，若是在顶部显示 尽量使用replace
     *
     * @param fragmentManager
     * @param res
     * @param fragment
     */
    public static void replaceFragment(FragmentManager fragmentManager, @IdRes int res, Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(res, fragment)
                .commit();
    }
}
