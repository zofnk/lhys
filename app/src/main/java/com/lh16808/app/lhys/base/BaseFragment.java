package com.lh16808.app.lhys.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基类Activity
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量
     */
    protected abstract void initVariables();

    /**
     * 加载layout布局文件，初始化控件，为控件挂上事件方法
     *
     * @param savedInstanceState
     */
    protected abstract View initViews(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 调用获取数据
     */
    public abstract void loadData();

    protected View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initVariables();
        view = initViews(inflater, container, savedInstanceState);
        loadData();
        return view;
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(getActivity().getClass().getName()); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(getActivity().getClass().getName());
    }

}
