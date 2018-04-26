package com.anshi.healthdeyi.view.healthfamily;

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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.base.BaseApplication;
import com.anshi.healthdeyi.enty.CommonListEntry;
import com.anshi.healthdeyi.enty.TitleEnty;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.ToastUtils;
import com.anshi.healthdeyi.utils.Utils;
import com.anshi.healthdeyi.view.comment.CommentActivity;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
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
 * Created by yulu on 2018/4/13.
 */

public class HealthFragTwo extends Fragment {
    private Context mContext;
    private TwinklingRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private CommonAdapter<CommonListEntry.DataBean> commonAdapter;
    private List<CommonListEntry.DataBean> mList = new ArrayList<>();
    private String id;
    public static final String TITLE_TYPE = "title";
    private int p = 1;
    private TextView mNoData;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
    public static HealthFragTwo getInstance(String id){
        HealthFragTwo fragTwo = new HealthFragTwo();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_TYPE,id);
        fragTwo.setArguments(bundle);
        return fragTwo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id =  getArguments().getString(TITLE_TYPE);
        getCommonData(id,true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_health_title,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
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
        mRecyclerView = view.findViewById(R.id.frag_title_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        commonAdapter = new CommonAdapter<CommonListEntry.DataBean>(mContext,R.layout.frag_health_title_item,mList) {
            @Override
            protected void convert(ViewHolder holder, CommonListEntry.DataBean dataBean, int position) {
                final TextView mTextView = holder.getView(R.id.title_tv);
                mTextView.setText(dataBean.getTitle());
                LinearLayout container = holder.getView(R.id.click_layout);
                RadioButton commentRb = holder.getView(R.id.title_comment_rb);
                commentRb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, CommentActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext,HealthTitleFormationActivity.class);
                        intent.putExtra("title",mTextView.getText().toString());
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
        //initData();
    }

    private void initData(){
        List<TitleEnty> list = new ArrayList<>();
        list.add(new TitleEnty("张三","参加很好",R.drawable.banner_three));
        mRecyclerView.setAdapter(new CommonAdapter<TitleEnty>(mContext,R.layout.frag_health_title_item,list) {
            @Override
            protected void convert(ViewHolder holder, TitleEnty titleEnty, int position) {
                final TextView mTextView = holder.getView(R.id.title_tv);
                LinearLayout container = holder.getView(R.id.click_layout);
                RadioButton commentRb = holder.getView(R.id.title_comment_rb);
                commentRb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, CommentActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext,HealthTitleFormationActivity.class);
                        intent.putExtra("title",mTextView.getText().toString());
                        mContext.startActivity(intent);
                    }
                });
            }
        });
    }

    private void addEventListener(){
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        p = 1;
                        getCommonData(id,true);
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
                        getCommonData(id,false);
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
