package com.anshi.healthdeyi.view.healthprescription;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.base.BaseApplication;
import com.anshi.healthdeyi.enty.PrescriptionEnty;
import com.anshi.healthdeyi.enty.PrescriptionFormationEnty;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.Utils;
import com.anshi.healthdeyi.view.fragments.lazy.EaseChatGroupFrag;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * Created by yulu on 2018/4/11.
 */

public class HealthPrescriptionFragment extends EaseChatGroupFrag {
    private Context mContext;
    public static final String PARAM_TYPR = "pre_type";
    private ExecutorService mExecutorService;
    private PrescriptionEnty.OutBean outBean;
    private TwinklingRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private boolean hasLoad;
    private int currentIndex = 0;
    private CommonAdapter<PrescriptionFormationEnty.OutBean> mAdapter;
    private List<PrescriptionFormationEnty.OutBean> mList = new ArrayList<>();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mExecutorService = Executors.newCachedThreadPool();

    }
    public static HealthPrescriptionFragment getInstance(PrescriptionEnty.OutBean outBean){
        HealthPrescriptionFragment preFrag = new HealthPrescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PARAM_TYPR, outBean);
        preFrag.setArguments(bundle);
        return preFrag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        outBean = (PrescriptionEnty.OutBean) getArguments().getSerializable(PARAM_TYPR);
    }

    @Override
    protected void initView(View view) {
        mRefreshLayout = findViewById(R.id.refresh_layout);
        final SinaRefreshView headView = new SinaRefreshView(mContext);
        headView.setArrowResource(R.drawable.arrow_downgrey);
        headView.setTextColor(Color.BLACK);
        headView.setPullDownStr("下拉刷新");
        headView.setRefreshingStr("正在刷新");
        headView.setReleaseRefreshStr("释放刷新");
        mRefreshLayout.setHeaderView(headView);
        LoadingView loadingView = new LoadingView(mContext);
        mRefreshLayout.setBottomView(loadingView);
        mRecyclerView = findViewById(R.id.news_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mAdapter = new CommonAdapter<PrescriptionFormationEnty.OutBean>(mContext,R.layout.home_recycler_item,mList) {
            @Override
            protected void convert(ViewHolder holder, final PrescriptionFormationEnty.OutBean outBean, int position) {
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
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int setContentView() {
        return R.layout.frag_news_one;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addEventListener();
    }

    @Override
    protected void lazyLoad() {
        if (!hasLoad){
            if (outBean!=null){
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("class_id",outBean.getClass_id());
                    jsonObject.put("startIndex",currentIndex);
                    jsonObject.put("parent_class","");
                    jsonObject.put("row",10);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getPreFormation(jsonObject.toString());
                hasLoad = true;
            }
        }
    }



    private void addEventListener(){
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentIndex = 0;
                        refreshLayout.finishRefreshing();
                    }
                },2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentIndex ++;
                        refreshLayout.finishLoadmore();
                    }
                },2000);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hasLoad = false;

    }

    private void getPreFormation(String json){
        final OkHttpClient okHttpClient = BaseApplication.bulidOkHttpClient().build();
        String url = Constants.PRE_FORMATION+json;
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String string = response.body().string();
                    Log.e("xxx",string);
                    if (Utils.isGoodJson(string)) {
                        Gson gson = new Gson();
                        final PrescriptionFormationEnty prescriptionFormationEnty = gson.fromJson(string, PrescriptionFormationEnty.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mList.clear();
                                mList.addAll(prescriptionFormationEnty.getOut());
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mExecutorService.execute(runnable);
    }

}
