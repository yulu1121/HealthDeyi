package com.anshi.healthdeyi.view.healthfamily;

import android.content.Context;
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
import com.anshi.healthdeyi.enty.CommonListEntry;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.ToastUtils;
import com.anshi.healthdeyi.utils.Utils;
import com.anshi.healthdeyi.view.self.EaseImageView;
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
 * Created by yulu on 2018/4/13.
 */

public class HealthFragOne extends Fragment{
    private Context mContext;
    private RecyclerView mRecyclerView;
    private TwinklingRefreshLayout mRefreshLayout;
    private List<CommonListEntry.DataBean> mList = new ArrayList<>();
    public static final String PARTY_TYPE = "party";
    private String id;
    private CommonAdapter<CommonListEntry.DataBean> commonAdapter;
    private int p = 1;
    private TextView mNoData;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public static HealthFragOne getInstance(String id){
        HealthFragOne fragOne = new HealthFragOne();
        Bundle bundle = new Bundle();
        bundle.putString(PARTY_TYPE,id);
        fragOne.setArguments(bundle);
        return fragOne;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString(PARTY_TYPE);
        getCommonData(id,true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_health_party,container,false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addEventListener();
        //initData();
    }

//    private void initData(){
//        mList = new ArrayList<>();
//        mList.add(new PartyEnty("春季踏青去隆中",R.drawable.banner_one,"2018年4月14日10点", Constants.PARTY_PERSONAL_COMPANY,"隆中"));
//        mList.add(new PartyEnty("社区健康讲座",R.drawable.banner_two,"2018年4月15日8点",Constants.PARTY_FREE,"张湾社区"));
//        mList.add(new PartyEnty("民发广场舞友聚",R.drawable.splash,"2018年4月19日17点",Constants.PARTY_PERSONAL,"航空路民发广场"));
//        mList.add(new PartyEnty("名医线下听诊",R.drawable.banner_three,"2018年4月17日9点",Constants.PARTY_SCORE,"独活大药房XX店"));
//        mRecyclerView.setAdapter(new CommonAdapter<PartyEnty>(mContext,R.layout.party_recycler_item,mList) {
//            @Override
//            protected void convert(ViewHolder holder, final PartyEnty partyEnty, int position) {
////                if (position==mList.size()-1){
////                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.getConvertView().getLayoutParams();
////                    layoutParams.setMargins(0,0,0,30);
////                }
//
//                EaseImageView easeImageView = holder.getView(R.id.report_image);
//                easeImageView.setImageResource(partyEnty.getImageId());
//                TextView title = holder.getView(R.id.report_title);
//                title.setText(partyEnty.getTitle());
//                TextView time = holder.getView(R.id.report_time);
//                String format = getString(R.string.party_time);
//                String format1 = String.format(format, partyEnty.getTime());
//                time.setText(format1);
//                final TextView mCategory = holder.getView(R.id.party_category);
//                Button btn = holder.getView(R.id.report_btn);
//                String category ="";
//                switch (partyEnty.getCategory()){
//                    case Constants.PARTY_PERSONAL:
//                        category = String.format(getString(R.string.party_category),"个人活动");
//                        btn.setText("参加");
//                        mCategory.setText(category);
//                        break;
//                    case Constants.PARTY_PERSONAL_COMPANY:
//                        category = String.format(getString(R.string.party_category),"公司活动");
//                        btn.setText("参加");
//                        mCategory.setText(category);
//                        break;
//                    case Constants.PARTY_FREE:
//                        category = String.format(getString(R.string.party_category),"免费活动");
//                        btn.setText("参加");
//                        mCategory.setText(category);
//                        break;
//                    case Constants.PARTY_SCORE:
//                        category = String.format(getString(R.string.party_category),"积分活动");
//                        btn.setText("积分兑换");
//                        mCategory.setText(category);
//                        break;
//
//                }
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(mContext,HealthPartFormation.class);
//                        intent.putExtra("party",partyEnty);
//                        startActivity(intent);
//                    }
//                });
//                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(mContext,HealthPartFormation.class);
//                        intent.putExtra("party",partyEnty);
//                        startActivity(intent);
//
//                    }
//                });
//                holder.getView(R.id.comment_rb).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(mContext, CommentActivity.class);
//                        startActivity(intent);
//                    }
//                });
//            }
//        });
//    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.party_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
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
        commonAdapter = new CommonAdapter<CommonListEntry.DataBean>(mContext,R.layout.party_recycler_item,mList) {
            @Override
            protected void convert(ViewHolder holder, CommonListEntry.DataBean listBean, int position) {
                if (position==mList.size()-1){
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.getConvertView().getLayoutParams();
                    layoutParams.setMargins(0,0,0,30);
                }

                EaseImageView easeImageView = holder.getView(R.id.report_image);
                Picasso.with(mContext).load(Constants.COMMON_IMAGE_HEADER+listBean.getImg()).into(easeImageView);
                TextView title = holder.getView(R.id.report_title);
                title.setText(listBean.getTitle());
                TextView time = holder.getView(R.id.report_time);
                String format = getString(R.string.party_time);
                String format1 = String.format(format, listBean.getCreateDate());
                time.setText(format1);
                final TextView mCategory = holder.getView(R.id.party_category);

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
