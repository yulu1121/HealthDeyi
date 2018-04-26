package com.anshi.healthdeyi.view.healthpoint;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.base.BaseApplication;
import com.anshi.healthdeyi.enty.ReleatedEntry;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.StatubarUtils;
import com.anshi.healthdeyi.utils.StatusBarUtils;
import com.anshi.healthdeyi.utils.ToastUtils;
import com.anshi.healthdeyi.utils.Utils;
import com.anshi.healthdeyi.view.base.BaseActivity;
import com.google.gson.Gson;
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
 * 经穴
 * Created by yulu on 2018/4/12.
 */

public class HealthPointActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private List<ReleatedEntry.DataBean> mList = new ArrayList<>();
    private CommonAdapter<ReleatedEntry.DataBean> commonAdapter;
    private TextView mNoData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_point);
        initView();
        getPointEntry();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
    }
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.health_point_recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mNoData = (TextView) findViewById(R.id.no_data_tv);
        final int heightPixels = getResources().getDisplayMetrics().heightPixels;
        final int statuBarsHeight = Utils.getStatuBarsHeight(this);
        commonAdapter = new CommonAdapter<ReleatedEntry.DataBean>(this,R.layout.point_recycler_item,mList) {
            @Override
            protected void convert(ViewHolder holder, final ReleatedEntry.DataBean outBean, int position) {
                RelativeLayout mRoot =  holder.getView(R.id.point_layout);
                mRoot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  (heightPixels-Utils.dpToPx(76+statuBarsHeight,getResources()))/3));
                ImageView mPointImage = holder.getView(R.id.point_image);
                Picasso.with(mContext).load(Constants.COMMON_IMAGE_HEADER+outBean.getSpare1()).into(mPointImage);
                TextView mPointTv = holder.getView(R.id.point_text);
                mPointTv.setText(outBean.getName());
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShortToast(mContext,"正在开发...");
                    }
                });
            }
        };
        mRecyclerView.setAdapter(commonAdapter);
    }



    private void getPointEntry(){
        final OkHttpClient okHttpClient = BaseApplication.bulidOkHttpClient().build();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pid",Constants.POINT_HEALTH_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
        final Request request = new Request.Builder()
                .post(requestBody)
                .url(Constants.GET_TYPE_URL)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String string = okHttpClient.newCall(request).execute().body().string();
                    if (Utils.isGoodJson(string)){
                        Gson gson = new Gson();
                        final ReleatedEntry releatedEntry = gson.fromJson(string, ReleatedEntry.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (releatedEntry.isCode()){
                                    if (releatedEntry.getData()!=null&&releatedEntry.getData().size()>0){
                                        mRecyclerView.setVisibility(View.VISIBLE);
                                        mNoData.setVisibility(View.GONE);
                                        mList.clear();
                                        mList.addAll(releatedEntry.getData());
                                        commonAdapter.notifyDataSetChanged();
                                    }else {
                                        mRecyclerView.setVisibility(View.GONE);
                                        mNoData.setVisibility(View.VISIBLE);
                                    }
                                }else {
                                    ToastUtils.showShortToast(mContext,releatedEntry.getMsg());
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
