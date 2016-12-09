package com.lh16808.app.lhys.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.activity.CardActivity;
import com.lh16808.app.lhys.activity.LuckPanActivity;
import com.lh16808.app.lhys.activity.ShakeActivity;
import com.lh16808.app.lhys.activity.TypeActivity;
import com.lh16808.app.lhys.activity.XunBaoActivity;
import com.lh16808.app.lhys.laohuangli.LaohuangliActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {

    @Bind(R.id.rltyCaip)
    RelativeLayout mRltyCaip;
    @Bind(R.id.rltySsc)
    RelativeLayout mRltySsc;
    @Bind(R.id.rltyRound)
    RelativeLayout mRltyRound;
    @Bind(R.id.rltyYlc)
    RelativeLayout mRltyYlc;
    @Bind(R.id.rltyCard)
    RelativeLayout mRltyCard;
    @Bind(R.id.rltyHgw)
    RelativeLayout mRltyHgw;
    @Bind(R.id.rltyShake)
    RelativeLayout mRltyShake;
    @Bind(R.id.rltyLhl)
    RelativeLayout mRltyLhl;

    public static ResultFragment newInstance() {
        Bundle args = new Bundle();
        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.rltyCaip, R.id.rltySsc, R.id.rltyRound, R.id.rltyYlc, R.id.rltyCard, R.id.rltyHgw, R.id.rltyShake, R.id.rltyLhl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rltyCaip:
                TypeActivity.start(getContext());
                break;
            case R.id.rltyRound:
                LuckPanActivity.start(getContext());
                break;
            case R.id.rltyCard:
                CardActivity.start(getContext());
                break;
            case R.id.rltyShake:
                ShakeActivity.start(getContext());
                break;
            case R.id.rltyLhl:
                LaohuangliActivity.start(getContext());
                break;
            case R.id.rltySsc:
            case R.id.rltyYlc:
            case R.id.rltyHgw:
                XunBaoActivity.start(getContext());
                break;
        }
    }
}
