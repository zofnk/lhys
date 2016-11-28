package com.lh16808.app.lhys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mxn.soul.flowingdrawer_core.MenuFragment;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyMenuFragment extends MenuFragment {

    private CircleImageView ivMenuUserProfilePhoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,
                false);
        ivMenuUserProfilePhoto = (CircleImageView) view.findViewById(R.id.ivMenuUserProfilePhoto);
        View id = view.findViewById(R.id.rl_mine_ziLiao);
        return  setupReveal(view) ;
    }

    public void onOpenMenu(){
        Toast.makeText(getActivity(),"onOpenMenu",Toast.LENGTH_SHORT).show();
    }
    public void onCloseMenu(){
        Toast.makeText(getActivity(),"onCloseMenu",Toast.LENGTH_SHORT).show();
    }
}
