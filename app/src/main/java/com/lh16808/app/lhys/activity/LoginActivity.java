package com.lh16808.app.lhys.activity;

import android.os.Bundle;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.base.listener.OnLoginLinstener;
import com.lh16808.app.lhys.fragment.LoginFragment;
import com.lh16808.app.lhys.utils.FragmentUtils;


public class LoginActivity extends BaseActivity implements OnLoginLinstener {

    @Override
    protected void initLoadData() {

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        FragmentUtils.addFragment(getSupportFragmentManager(), R.id.layout_login, LoginFragment.newInstance());
    }

    @Override
    public void setCurrentUI(int position) {
    }

    @Override
    public void finishActivity() {

    }

    @Override
    public void setViewPagerGesture(boolean gesture) {

    }

    @Override
    public void registerSuccessCallback(String phone, String password) {

    }

    @Override
    protected void loadData() {

    }
}
