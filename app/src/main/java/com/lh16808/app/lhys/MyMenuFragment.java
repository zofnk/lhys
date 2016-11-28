package com.lh16808.app.lhys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mxn.soul.flowingdrawer_core.MenuFragment;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyMenuFragment extends MenuFragment implements View.OnClickListener{

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
        View view1 = setupReveal(view);
        view1.setOnClickListener(this);
        return view1;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.drawer_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    public void onOpenMenu() {
        Toast.makeText(getActivity(), "onOpenMenu", Toast.LENGTH_SHORT).show();
    }

    public void onCloseMenu() {
        Toast.makeText(getActivity(), "onCloseMenu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_mine_ziLiao:
                Toast.makeText(getContext() , "ziliao" , Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
