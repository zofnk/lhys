package com.lh16808.app.lhys.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lh16808.app.lhys.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment {

    public static FindFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FindFragment fragment = new FindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FindFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        return view;
    }

}
