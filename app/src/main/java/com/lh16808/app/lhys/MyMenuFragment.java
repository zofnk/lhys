package com.lh16808.app.lhys;

import android.content.Context;
import android.content.Intent;
import android.media.audiofx.LoudnessEnhancer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lh16808.app.lhys.activity.AboutActivity;
import com.lh16808.app.lhys.activity.LoginActivity;
import com.lh16808.app.lhys.activity.MyCollectActivity;
import com.lh16808.app.lhys.activity.NoteListActivity;
import com.lh16808.app.lhys.activity.PeopleDataActivity;
import com.lh16808.app.lhys.activity.SetActivity;
import com.lh16808.app.lhys.model.User;
import com.lh16808.app.lhys.other.Login;
import com.lh16808.app.lhys.other.MessageEvent;
import com.lh16808.app.lhys.other.MyProgressDialog;
import com.lh16808.app.lhys.utils.ImageLoader;
import com.lh16808.app.lhys.utils.SharedPreUtils;
import com.lh16808.app.lhys.utils.ToastUtil;
import com.mxn.soul.flowingdrawer_core.MenuFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.lh16808.app.lhys.R.string.text4;


public class MyMenuFragment extends MenuFragment implements View.OnClickListener {

    private CircleImageView mIvPhoto;
    private TextView mTvName;
    private TextView mTvJiFen;
    private TextView mTvLv;

    private static final int HEADVIEW_POSITION = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        NavigationView naviView = (NavigationView) view.findViewById(R.id.vNavigation);
        View headerView = naviView.getHeaderView(HEADVIEW_POSITION);
        mIvPhoto = (CircleImageView) headerView.findViewById(R.id.iv_mine_photo);
        mIvPhoto.setOnClickListener(this);
        mTvName = (TextView) headerView.findViewById(R.id.tv_fragment_mine_nickname);
        mTvJiFen = (TextView) headerView.findViewById(R.id.tv_fragment_mine_jiFen);
        mTvLv = (TextView) headerView.findViewById(R.id.tv_fragment_mine_lv);
        naviView.setNavigationItemSelectedListener(new NavigationItemSelectedListener());
        return setupReveal(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageEvent event) {
        if (event.getType() == 1) {
            Login.OnLoginLoadPic onLoginSucceful = new Login.OnLoginLoadPic() {
                @Override
                public void onSucess(String userpic) {
//                    ImageLoader.LoaderNetHead(getContext(), userpic, mIvPhoto);
                    Glide.with(getContext())
                            .load(userpic)
                            .into(mIvPhoto)
                            .onStart();
                }

                @Override
                public void onError() {
                }
            };
            Login.loadPic(getActivity(), onLoginSucceful);
        } else if (event.getType() == 2) {
            if (!TextUtils.isEmpty(User.getUser().getHym())) {
                if (mTvJiFen.getVisibility() != View.VISIBLE) {
                    setUserInfo();
                }
            }
        }
    }

    private void setUserInfo() {
        mTvName.setText("昵称:" + User.getUser().getHym());
        mTvJiFen.setText("积分:" + User.getUser().getJs());
        mTvJiFen.setVisibility(View.VISIBLE);
        mTvLv.setText("等级:" + User.getUser().getDj());
        mTvLv.setVisibility(View.VISIBLE);
        Login.OnLoginLoadPic onLoginSucceful = new Login.OnLoginLoadPic() {
            @Override
            public void onSucess(String userpic) {
//                ImageLoader.LoaderNetHead(getContext(), userpic, mIvPhoto);
                Glide.with(getContext())
                        .load(userpic)
                        .into(mIvPhoto)
                        .onStart();
            }

            @Override
            public void onError() {

            }
        };
        Login.loadPic(getActivity(), onLoginSucceful);
    }

    private void startLogin(Intent intent) {
        if (!TextUtils.isEmpty(User.getUser().getHym())) {
            startActivity(intent);
        } else {
            ToastUtil.toastShow(getContext(), "你还未登录~");
        }
    }

    @Override
    public void onStart() {
        if (TextUtils.isEmpty(User.getUser().getHym())) {
            String userName = SharedPreUtils.getString("userName");
            String userPwd = SharedPreUtils.getString("userPwd");
            if (!TextUtils.isEmpty(userPwd)) {
                MyProgressDialog.dialogShow(getActivity());
                Login.login(getActivity(), userName, userPwd, new Login.OnLoginSucceful() {
                    @Override
                    public void onSucess() {
                        setUserInfo();
                    }

                    @Override
                    public void onError() {
                    }
                });
            } else {
                if (mTvJiFen.getVisibility() != View.GONE) {
                    mTvName.setText("请点击登录");
                    mTvJiFen.setVisibility(View.GONE);
                    mTvLv.setVisibility(View.GONE);
                    mIvPhoto.setImageResource(R.drawable.ico_mine_login_person);
                }
            }
        } else {
            if (mTvJiFen.getVisibility() != View.VISIBLE) {
                setUserInfo();
            }
        }
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_mine_photo:
                if (TextUtils.isEmpty(User.getUser().getHym()))
                    getActivity().startActivityForResult(new Intent(getContext(), LoginActivity.class), 1);
                else {
                    Intent intent = new Intent(getContext(), PeopleDataActivity.class);
                    startLogin(intent);
                }
                break;
        }
    }

    private class NavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.rl_mine_ziLiao:
                    //资料
                    if (!TextUtils.isEmpty(User.getUser().getHym())) {
                        PeopleDataActivity.start(getContext());
                    } else {
                        ToastUtil.toastShow(getContext(), "你还未登录~");
                    }
                    break;
                case R.id.rl_mine_collect:
                    //收藏
                    if (!TextUtils.isEmpty(User.getUser().getHym())) {
                        MyCollectActivity.start(getContext(), 0);
                    } else {
                        ToastUtil.toastShow(getContext(), "你还未登录~");
                    }
                    break;
                case R.id.textView2:
                    //发布
                    if (!TextUtils.isEmpty(User.getUser().getHym())) {
                        NoteListActivity.start(getContext(), 1);
                    } else {
                        ToastUtil.toastShow(getContext(), "你还未登录~");
                    }
                    break;
                case R.id.textView4:
                    //回复信息
                    if (!TextUtils.isEmpty(User.getUser().getHym())) {
                        NoteListActivity.start(getContext(), 2);
                    } else {
                        ToastUtil.toastShow(getContext(), "你还未登录~");
                    }
                    break;
                case R.id.rl_mine_sheZhi:
                    //设置
                    SetActivity.start(getContext());
                    break;
                case R.id.menu_about:
                    //关于
                    AboutActivity.start(getContext());
                    break;
            }
            return true;
        }
    }

    private void startLogin(Context context, Intent intent) {
        if (!TextUtils.isEmpty(User.getUser().getHym())) {
            startActivity(intent);
        } else {
            ToastUtil.toastShow(context, "你还未登录~");
        }
    }

    @Override
    public void onOpenMenu() {
        super.onOpenMenu();
        if (!TextUtils.isEmpty(User.getUser().getHym())) {
            //打开侧滑菜单更新用户信息
            setUserInfo();
        }
    }
}
