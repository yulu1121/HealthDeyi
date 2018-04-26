package com.anshi.healthdeyi.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.base.BaseApplication;
import com.anshi.healthdeyi.enty.BannerEnty;
import com.anshi.healthdeyi.enty.ReleatedEntry;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.ToastUtils;
import com.anshi.healthdeyi.utils.Utils;
import com.anshi.healthdeyi.view.healthfamily.HealthFamilyActivity;
import com.anshi.healthdeyi.view.healthmethod.HealthMethodActivity;
import com.anshi.healthdeyi.view.healthmovie.HealthMovieActivity;
import com.anshi.healthdeyi.view.healthpoint.HealthPointActivity;
import com.anshi.healthdeyi.view.healthprescription.HealthPrescriptionAcitity;
import com.anshi.healthdeyi.view.healthtest.HealthTestActivity;
import com.anshi.healthdeyi.view.healthvoice.HealthVoiceActivity;
import com.anshi.healthdeyi.view.self.EaseImageView;
import com.anshi.healthdeyi.view.sign.SignActivity;
import com.anshi.healthdeyi.view.web.WebActivity;
import com.gongwen.marqueen.MarqueeView;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.util.OnItemClickListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * Created by yulu on 2018/3/31.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{
    private Context mContext;
    private Banner mBanner;
    private RecyclerView mRecyclerView;
    private List<String> mBannerList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    private List<ReleatedEntry.DataBean> mRecommendList = new ArrayList<>();
    private CommonAdapter<ReleatedEntry.DataBean> commonAdapter;
    private ExecutorService mExecutorService;
    private List<Integer> mImageList;
    private TextView mSignTv;
    private MarqueeView<TextView,String> mMarqueeView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mExecutorService = Executors.newCachedThreadPool();
    }

    public static HomeFragment getInstance(){
        return new HomeFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home_main,container,false);
        initView(view);
        return view;
    }


    private void getNewsData(){
        List<String> goodFoodPaperList = new ArrayList<>();
        goodFoodPaperList.add("喝普洱茶会发出汗，这是为什么");
        goodFoodPaperList.add("西红柿的做法有很多种，你知道吗");
        SimpleMF<String> marqueeFactory = new SimpleMF<>(mContext);
        marqueeFactory.setData(goodFoodPaperList);
        mMarqueeView.setMarqueeFactory(marqueeFactory);
    }

    private void initView(View view) {
        mBanner = view.findViewById(R.id.banner);
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        mBanner.setImageLoader(new PicassoImageLoader());
        mBanner.setBannerAnimation(Transformer.Default);
        mSignTv = view.findViewById(R.id.toSign);
        mMarqueeView = view.findViewById(R.id.main_good_food_paper_marquee);
        LinearLayout mVoiceIb = view.findViewById(R.id.voice_health_ib);
        LinearLayout mMovieIb = view.findViewById(R.id.movie_health_ib);
        LinearLayout mFamilyLl = view.findViewById(R.id.health_family_layout);
        LinearLayout healthPre = view.findViewById(R.id.health_pre);
        LinearLayout healthMethod = view.findViewById(R.id.health_method_layout);
        LinearLayout healthTest = view.findViewById(R.id.health_test_layout);
        LinearLayout healthPoint = view.findViewById(R.id.health_point);
        healthPoint.setOnClickListener(this);
        mVoiceIb.setOnClickListener(this);
        healthMethod.setOnClickListener(this);
        mFamilyLl.setOnClickListener(this);
        healthPre.setOnClickListener(this);
        healthTest.setOnClickListener(this);
        mMovieIb.setOnClickListener(this);
        mSignTv.setOnClickListener(this);
        initBannerLocalData();
        //getBannerData();
        mRecyclerView = view.findViewById(R.id.home_recycler);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        commonAdapter = new CommonAdapter<ReleatedEntry.DataBean>(mContext, R.layout.home_recycler_item,mRecommendList){
            @Override
            protected void convert(ViewHolder holder, final ReleatedEntry.DataBean outBean, int position) {
                    EaseImageView mEaseImage = holder.getView(R.id.recommend_img);
                    TextView mName = holder.getView(R.id.recommend_name);
                    Picasso.with(mContext).load(Constants.COMMON_IMAGE_HEADER+outBean.getSpare1()).error(R.drawable.ease_default_image).into(mEaseImage);
                    mName.setText(outBean.getName());
                    holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebActivity.class);
                            intent.setAction("self");
                            intent.putExtra("url",outBean.getId());
                            startActivity(intent);
                        }
                    });
            }
        };
        mRecyclerView.setAdapter(commonAdapter);
    }

    private void addEventListener(){
       mMarqueeView.setOnItemClickListener(new OnItemClickListener<TextView,String>() {
            @Override
            public void onItemClickListener(TextView mView, String mData, int mPosition) {
                ToastUtils.showShortToast(mContext, ""+mPosition);
            }
        });
    }

    private void initBannerLocalData() {
        mImageList = new ArrayList<>();
        mImageList.add(R.drawable.banner_new);
        mTitleList.add("神清气爽");
        mImageList.add(R.drawable.banner_two);
        mTitleList.add("养生长寿");
        mImageList.add(R.drawable.banner_three);
        mTitleList.add("喝茶养生");
        mBanner.setImages(mImageList);
        mBanner.setBannerTitles(mTitleList);
        mBanner.start();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getRecommendData();
        getNewsData();
        addEventListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
        mMarqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
        mMarqueeView.stopFlipping();
    }

    private void getBannerData(){
        final OkHttpClient okHttpClient = BaseApplication.bulidOkHttpClient().build();
        final Request build = new Request.Builder()
                .get()
                .url(Constants.BANNER_URL)
                .build();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = okHttpClient.newCall(build).execute();
                    String string = response.body().string();
                    if (Utils.isGoodJson(string)) {
                        Gson gson = new Gson();
                        final BannerEnty bannerEnty = gson.fromJson(string, BannerEnty.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mBannerList.clear();
                                mTitleList.clear();
                                List<BannerEnty.OutBean.ChannelListBean> channel_list = bannerEnty.getOut().getChannel_list();
                                for (BannerEnty.OutBean.ChannelListBean bean : channel_list) {
                                    mBannerList.add(bean.getImage_url());
                                    mTitleList.add(bean.getTitle());
                                }
                                mBanner.setImages(mBannerList);
                                mBanner.setBannerTitles(mTitleList);
                                mBanner.start();
                            }
                        });
                    }
                } catch (IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showShortToast(mContext, "链接超时");
                        }
                    });
                    e.printStackTrace();
                }
            }
        };
        mExecutorService.execute(runnable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.voice_health_ib:
                Intent intent = new Intent(mContext, HealthVoiceActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.movie_health_ib:
                Intent intent2 = new Intent(mContext,HealthMovieActivity.class);
                mContext.startActivity(intent2);
                break;
            case R.id.toSign:
                Intent intent1 = new Intent(mContext, SignActivity.class);
                mContext.startActivity(intent1);
                break;
            case R.id.health_pre:
                Intent intent3 = new Intent(mContext, HealthPrescriptionAcitity.class);
                mContext.startActivity(intent3);
                break;
            case R.id.health_point:
                Intent intent4 = new Intent(mContext, HealthPointActivity.class);
                mContext.startActivity(intent4);
                break;
            case R.id.health_family_layout:
                Intent intent5 = new Intent(mContext, HealthFamilyActivity.class);
                mContext.startActivity(intent5);
                break;
            case R.id.health_method_layout:
                Intent intent6 = new Intent(mContext, HealthMethodActivity.class);
                mContext.startActivity(intent6);
                break;
            case R.id.health_test_layout:
                Intent intent7 = new Intent(mContext, HealthTestActivity.class);
                mContext.startActivity(intent7);
                break;
        }
    }

    private class PicassoImageLoader extends ImageLoader{

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
             int realPath = (int) path;
            Picasso.with(context).load(realPath).into(imageView);
        }
    }

    private void getRecommendData(){
        final OkHttpClient okHttpClient = BaseApplication.bulidOkHttpClient().build();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pid",Constants.RELATED_RECOMMEND_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());

        final Request build = new Request.Builder()
                .post(requestBody)
                .url(Constants.GET_TYPE_URL)
                .build();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = okHttpClient.newCall(build).execute();
                    String string = response.body().string();
                    if (Utils.isGoodJson(string)) {
                        Gson gson = new Gson();
                        final ReleatedEntry recommendEnty = gson.fromJson(string, ReleatedEntry.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (null!=mRecommendList){
                                    mRecommendList.clear();
                                    mRecommendList.addAll(recommendEnty.getData());
                                    commonAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                } catch (IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showShortToast(mContext, "链接超时");
                        }
                    });
                    e.printStackTrace();
                }
            }
        };
        mExecutorService.execute(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!mExecutorService.isShutdown()){
            mExecutorService.shutdown();
        }
    }
}
