package com.anshi.healthdeyi.view.healthmethod;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.base.BaseApplication;
import com.anshi.healthdeyi.enty.MethodEnty;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.Utils;
import com.anshi.healthdeyi.view.self.EaseImageView;
import com.anshi.healthdeyi.view.web.WebActivity;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * Created by yulu on 2018/4/13.
 */

public class MethodFragOne extends Fragment {
    private Context mContext;
    private TwinklingRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<MethodEnty.OutBean> mList = new ArrayList<>();
    private CommonAdapter<MethodEnty.OutBean> commonAdapter;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public static MethodFragOne getInstance(){
        return new MethodFragOne();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_news_one,container,false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMethodEnty();
        addEventListener();
    }

    private void initView(View view) {
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        final SinaRefreshView headView = new SinaRefreshView(mContext);
        headView.setArrowResource(R.drawable.arrow_downgrey);
        headView.setTextColor(Color.BLACK);
        headView.setPullDownStr("下拉刷新");
        headView.setRefreshingStr("正在刷新");
        headView.setReleaseRefreshStr("释放刷新");
        mRefreshLayout.setHeaderView(headView);
        LoadingView loadingView = new LoadingView(mContext);
        mRefreshLayout.setBottomView(loadingView);
        mRecyclerView = view.findViewById(R.id.news_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        commonAdapter = new CommonAdapter<MethodEnty.OutBean>(mContext,R.layout.home_recycler_item,mList) {
            @Override
            protected void convert(ViewHolder holder, final MethodEnty.OutBean outBean, int position) {
                EaseImageView easeImageView =  holder.getView(R.id.recommend_img);
                TextView textView = holder.getView(R.id.recommend_name);
                Picasso.with(mContext).load(outBean.getImage_url()).into(easeImageView);
                textView.setText(outBean.getTitle());
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext,WebActivity.class);
                        intent.putExtra("url",outBean.getUrl());
                        mContext.startActivity(intent);
                    }
                });
            }
        };
        mRecyclerView.setAdapter(commonAdapter);
    }

    private void addEventListener(){
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                },2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                },2000);
            }
        });
    }


    private void getMethodEnty(){
        final OkHttpClient okHttpClient = BaseApplication.bulidOkHttpClient().build();
        final Request request = new Request.Builder()
                .url(Constants.HEALTH_METHOD)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String string = response.body().string();
                    if (Utils.isGoodJson(string)){
                        Gson gson = new Gson();
                        final MethodEnty methodEnty = gson.fromJson(string, MethodEnty.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mList.clear();
                                mList.addAll(methodEnty.getOut());
                                commonAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
