package com.anshi.healthdeyi.view.healthmovie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.base.BaseApplication;
import com.anshi.healthdeyi.enty.VoiceEnty;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.Utils;
import com.anshi.healthdeyi.view.fragments.lazy.EaseChatGroupFrag;
import com.anshi.healthdeyi.view.healthvoice.VoicePlayingActivity;
import com.anshi.healthdeyi.view.self.EaseImageView;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * Created by yulu on 2018/4/3.
 */

public class HealthMovieFrag extends EaseChatGroupFrag {
    private Context mContext;
    public static final String PARAM_TYPR = "movie_type";
    private ExecutorService mExecutorService;
    private String type;
    private TwinklingRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private boolean hasLoad;
    private List<VoiceEnty.OutBean> mList = new ArrayList<>();
    private CommonAdapter<VoiceEnty.OutBean> commonAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mExecutorService = Executors.newCachedThreadPool();

    }
    public static HealthMovieFrag getInstance(String type){
        HealthMovieFrag voiceFrag = new HealthMovieFrag();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_TYPR,type);
        voiceFrag.setArguments(bundle);
        return voiceFrag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString(PARAM_TYPR);
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
        commonAdapter = new CommonAdapter<VoiceEnty.OutBean>(mContext, R.layout.home_recycler_item, mList) {

            @Override
            protected void convert(ViewHolder holder, final VoiceEnty.OutBean outBean, int position) {
                EaseImageView easeImageView =  holder.getView(R.id.recommend_img);
                TextView textView = holder.getView(R.id.recommend_name);
                Picasso.with(mContext).load(R.drawable.logo_service).into(easeImageView);
                String title = outBean.getTitle();
                if (title.contains("仁青益寿")){
                    title = title.replace("仁青益寿","德益寿");
                }
                textView.setText(title);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext,VoicePlayingActivity.class);
                        intent.putExtra("contentId",outBean.getContent_id());
                        intent.putExtra("classId",outBean.getClass_id());
                        intent.setAction(Constants.VIDEO_ACTION);
                        intent.putExtra("image",outBean.getImage_url());
                        String title1 = outBean.getTitle();
                        if (title1.contains("仁青益寿")){
                            title1 = title1.replace("仁青益寿","德益寿");
                        }
                        intent.putExtra("videoName", title1);
                        mContext.startActivity(intent);
                    }
                });
            }
        };
        mRecyclerView.setAdapter(commonAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addEventListener();
    }

    @Override
    protected int setContentView() {
        return R.layout.frag_news_one;
    }

    @Override
    protected void lazyLoad() {
        if (!hasLoad){
            if (type!=null){
                switch (type){
                    case Constants.MOVIE_TEACHER_CLASS_ROOM:
                        getTeacherMovie();
                        break;
                    case Constants.MOVIE_LONG_CLASS_ROOM:
                        getLongMovie();
                        break;
                    case Constants.MOVIE_PUBLIC_CLASS_ROOM:
                        getPublicMovie();
                        break;
                }
            }
            hasLoad = true;
        }
    }

    private void getTeacherMovie(){
        final OkHttpClient okHttpClient = BaseApplication.bulidOkHttpClient().build();
        final Request request = new Request.Builder()
                .get()
                .url(Constants.MOVIE_TEACHER)
                .build();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String string = response.body().string();
                    if (Utils.isGoodJson(string)){
                        Gson gson = new Gson();
                        final VoiceEnty voiceEnty = gson.fromJson(string, VoiceEnty.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mList.clear();
                                mList.addAll(voiceEnty.getOut());
                                commonAdapter.notifyDataSetChanged();
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

    private void getPublicMovie(){
        final OkHttpClient okHttpClient = BaseApplication.bulidOkHttpClient().build();
        final Request request = new Request.Builder()
                .get()
                .url(Constants.MOVIE_PUBLIC)
                .build();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String string = response.body().string();
                    if (Utils.isGoodJson(string)){
                        Gson gson = new Gson();
                        final VoiceEnty voiceEnty = gson.fromJson(string, VoiceEnty.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mList.clear();
                                mList.addAll(voiceEnty.getOut());
                                commonAdapter.notifyDataSetChanged();

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

    private void getLongMovie(){
        final OkHttpClient okHttpClient = BaseApplication.bulidOkHttpClient().build();
        final Request request = new Request.Builder()
                .get()
                .url(Constants.MOVIE_LONG)
                .build();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String string = response.body().string();
                    if (Utils.isGoodJson(string)){
                        Gson gson = new Gson();
                        final VoiceEnty voiceEnty = gson.fromJson(string, VoiceEnty.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mList.clear();
                                mList.addAll(voiceEnty.getOut());
                                commonAdapter.notifyDataSetChanged();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        hasLoad = false;
    }
}
