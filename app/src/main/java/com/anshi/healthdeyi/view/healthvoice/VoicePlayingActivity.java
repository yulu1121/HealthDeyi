package com.anshi.healthdeyi.view.healthvoice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.base.BaseApplication;
import com.anshi.healthdeyi.enty.CommonListEntry;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.StatubarUtils;
import com.anshi.healthdeyi.utils.StatusBarUtils;
import com.anshi.healthdeyi.utils.ToastUtils;
import com.anshi.healthdeyi.utils.Utils;
import com.anshi.healthdeyi.view.base.BaseActivity;
import com.anshi.healthdeyi.view.self.EaseImageView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 *
 * Created by yulu on 2018/4/3.
 */

public class VoicePlayingActivity extends BaseActivity  {
    private JZVideoPlayerStandard jzVideoPlayerStandard;
    private TextView mTitle;
    private String videoName;

    private RecyclerView mRecyclerView;
    private List<CommonListEntry.DataBean> mList = new ArrayList<>();
    private CommonAdapter<CommonListEntry.DataBean> commonAdapter;
    private TextView mNoData;
    private String parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_playing);

        initView();
        String contentId = getIntent().getStringExtra("contentId");
        videoName = getIntent().getStringExtra("videoName");
        parent = getIntent().getStringExtra("parent");
        setUpVideo(contentId);
        getCommonData(parent,true);
    }
    private void setUpVideo(String url){
        jzVideoPlayerStandard.setUp(Constants.COMMON_IMAGE_HEADER+url,JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,videoName);
        Picasso.with(mContext).load(R.drawable.logo_service).into(jzVideoPlayerStandard.thumbImageView);
        mTitle.setText(videoName);
    }


    private void initView() {
        jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        mTitle = (TextView) findViewById(R.id.video_title);
        mNoData = (TextView) findViewById(R.id.no_data_tv);
        mRecyclerView = (RecyclerView) findViewById(R.id.related_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        commonAdapter = new CommonAdapter<CommonListEntry.DataBean>(this,R.layout.home_recycler_item,mList) {
            @Override
            protected void convert(ViewHolder holder, final CommonListEntry.DataBean outBean, int position) {
                EaseImageView easeImageView =  holder.getView(R.id.recommend_img);
                TextView textView = holder.getView(R.id.recommend_name);
                Picasso.with(mContext).load(Constants.COMMON_HEADER+outBean.getImg()).into(easeImageView);
                String title = outBean.getTitle();
                textView.setText(title);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext,VoicePlayingActivity.class);
                        intent.putExtra("contentId",outBean.getVideo());
                        intent.putExtra("parent",parent);
                        String title1 = outBean.getTitle();
                        intent.putExtra("videoName",title1);
                        mContext.startActivity(intent);
                        finish();
                    }
                });
            }
        };
        mRecyclerView.setAdapter(commonAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
        JZVideoPlayerStandard.goOnPlayOnResume();
    }
    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }



    private void getCommonData(final String id, final boolean refresh){
        final OkHttpClient okHttpClient = BaseApplication.bulidOkHttpClient().build();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("subtype",id);
            jsonObject.put("pageNo",1);
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (commonListEntry.isCode()){
                                    if (commonListEntry.getData()!=null&&commonListEntry.getData().size()>0){
                                        mRecyclerView.setVisibility(View.VISIBLE);
                                        mNoData.setVisibility(View.GONE);
                                        if (refresh){
                                            mList.clear();
                                        }
                                        mList.addAll(commonListEntry.getData());
                                        commonAdapter.notifyDataSetChanged();
                                    }else {
                                        mRecyclerView.setVisibility(View.VISIBLE);
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
