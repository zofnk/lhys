package com.lh16808.app.lhys.base;

import android.app.Activity;
import android.app.Application;

import com.lh16808.app.lhys.marco.ApiConfig;
import com.lh16808.app.lhys.utils.SharedPreUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/11/12.
 */

public class BaseAPP extends Application {

    private static BaseAPP mContext;
    private List<Activity> activityList;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        activityList = new ArrayList<>();
        SharedPreUtils.init(this);
        ApiConfig.init(this);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        UMShareAPI.get(this);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setDebugMode(true);
        PlatformConfig.setWeixin("wx39b3d56dd2bae3eb", "e552434d90dde9e85b78060e705b2041");
        PlatformConfig.setQQZone("1105250483", "WtkL6Eq71TLnV3Dk");
    }

    public static BaseAPP getInstance() {
        return mContext;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void addActivity(Activity activity)   {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}
