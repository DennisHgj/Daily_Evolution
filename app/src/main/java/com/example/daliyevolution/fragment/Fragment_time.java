package com.example.daliyevolution.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daliyevolution.R;
import com.example.daliyevolution.base.BaseFragment;

/***
 * Adapter_transaction
 * use to set adapter for listview
 * @author Guanjie Huang(initial build)
 * @ID u6532079
 */
public class Fragment_time extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        return view;
    }
}
