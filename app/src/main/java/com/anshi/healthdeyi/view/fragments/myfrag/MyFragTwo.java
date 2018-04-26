package com.anshi.healthdeyi.view.fragments.myfrag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anshi.healthdeyi.R;

/**
 *
 * Created by yulu on 2018/4/2.
 */

public class MyFragTwo extends Fragment {
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
    public static MyFragTwo getInstance(){
        return new  MyFragTwo();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_my_one,container,false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        TextView noDataTv = view.findViewById(R.id.no_data_tv);
        noDataTv.setText("没有收藏的帖子");
    }
}
