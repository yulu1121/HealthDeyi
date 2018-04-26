package com.anshi.healthdeyi.view.fragments.common;

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
import com.anshi.healthdeyi.enty.CommonListEntry;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.ToastUtils;
import com.anshi.healthdeyi.utils.Utils;
import com.anshi.healthdeyi.view.fragments.lazy.EaseChatGroupFrag;
import com.anshi.healthdeyi.view.healthvoice.VoicePlayingActivity;
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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 *
 * Created by yulu on 2018/4/26.
 */

public class CommonFrag extends EaseChatGroupFrag {
    private Context mContext;
    public static final String TYPE = "ID";
    public static final String ACTION = "action";
    private TwinklingRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<CommonListEntry.DataBean> mList = new ArrayList<>();
    private String type;
    private CommonAdapter<CommonListEntry.DataBean> commonAdapter;
    private int p = 1;
    private TextView mNoData;
    private boolean hasLoad;
    private String action;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public static CommonFrag getInstance(String id,String action){
        CommonFrag commonFrag = new CommonFrag();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE,id);
        bundle.putString(ACTION,action);
        commonFrag.setArguments(bundle);
        return commonFrag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        type = arguments.getString(TYPE);
        action = arguments.getString(ACTION);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addEventListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hasLoad = false;
    }

    protected void initView(View view) {
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mNoData = view.findViewById(R.id.no_data_tv);
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
        commonAdapter = new CommonAdapter<CommonListEntry.DataBean>(mContext, R.layout.home_recycler_item, mList) {
            @Override
            protected void convert(ViewHolder holder, final CommonListEntry.DataBean outBean, int position) {
                EaseImageView easeImageView =  holder.getView(R.id.recommend_img);
                TextView textView = holder.getView(R.id.recommend_name);
                Picasso.with(mContext).load(Constants.COMMON_IMAGE_HEADER+outBean.getImg()).into(easeImageView);
                String title = outBean.getTitle();
                textView.setText(title);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (action.equals(Constants.WEB_ACTION)){
                            Intent intent = new Intent(mContext, WebActivity.class);
                            intent.setAction("self");
                            intent.putExtra("url",outBean.getId());
                            mContext.startActivity(intent);
                            return;
                        }
                        if (action.equals(Constants.MOVIE_ACTION)){
                            Intent intent = new Intent(mContext,VoicePlayingActivity.class);
                            intent.putExtra("parent",type);
                            intent.putExtra("contentId",outBean.getVideo());
                            String title1 = outBean.getTitle();
                            intent.putExtra("videoName", title1);
                            mContext.startActivity(intent);
                        }
                    }
                });
            }
        };
        mRecyclerView.setAdapter(commonAdapter);
    }

    @Override
    protected int setContentView() {
        return R.layout.frag_news_one;
    }

    @Override
    protected void lazyLoad() {
        if (!hasLoad){
            if (type!=null){
                getCommonData(type,true);
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
                        p = 1;
                        getCommonData(type,true);
                        refreshLayout.finishRefreshing();
                    }
                },2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        p++;
                        getCommonData(type,false);
                        refreshLayout.finishLoadmore();
                    }
                },2000);
            }
        });
    }

    private void getCommonData(final String id, final boolean refresh){
        final OkHttpClient okHttpClient = BaseApplication.bulidOkHttpClient().build();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("subtype",id);
            jsonObject.put("pageNo",p);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
        final Request request = new Request.Builder()
                .post(requestBody)
                .url(Constants.GET_INFO_URL)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String string = okHttpClient.newCall(request).execute().body().string();
                    Log.e("xxx",string);
                    if (Utils.isGoodJson(string)){
                        Gson gson = new Gson();
                        final CommonListEntry commonListEntry = gson.fromJson(string, CommonListEntry.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (commonListEntry.isCode()){
                                    if (commonListEntry.getData()!=null&&commonListEntry.getData().size()>0){
                                        mRefreshLayout.setVisibility(View.VISIBLE);
                                        mNoData.setVisibility(View.GONE);
                                        if (refresh){
                                            mList.clear();
                                        }
                                        mList.addAll(commonListEntry.getData());
                                        commonAdapter.notifyDataSetChanged();
                                    }else {
                                        mRefreshLayout.setVisibility(View.GONE);
                                        mNoData.setVisibility(View.VISIBLE);
                                    }
                                }else {
                                    ToastUtils.showShortToast(mContext,commonListEntry.getMsg());
                                }

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
