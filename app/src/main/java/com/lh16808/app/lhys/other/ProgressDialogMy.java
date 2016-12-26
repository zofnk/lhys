package com.lh16808.app.lhys.other;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.widget.LoadingView;


/**
 * 加载提示
 */
public class ProgressDialogMy {
    public Dialog dialog;
    private Activity mActivity;
    private LoadingView view;
    private View views;
    private boolean isShow = false; // loading框 是否正在showing

    public ProgressDialogMy(Activity mActivity) {
        this.mActivity = mActivity;
        initShow();
    }

    public void initShow() {
        views = LayoutInflater.from(mActivity).inflate(R.layout.my_loading, null);
        view = (LoadingView) views.findViewById(R.id.loading);
//        view.setLoadingText("加載中...");
        // 创建PopupWindow对象
        if (dialog != null && !dialog.isShowing()) {
            isShow = false;
        }
        if (!isShow) {
            if (mActivity instanceof Activity) {
                if (!(mActivity).isFinishing()) {
                    dialog = new Dialog(mActivity, R.style.my_pro_dialog);
                    dialog.setCancelable(true);// 不可以用“返回键”取消
                    dialog.setContentView(R.layout.my_loading);// 设置布局
                }
            }
        }
    }

    public void cancel() {
        try {
            if (dialog != null) {
                dialog.dismiss();
                isShow = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showpop() {
        try {
            System.out.println(this);
            if (!isShow && dialog != null) {
                dialog.show();
            }
            isShow = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
