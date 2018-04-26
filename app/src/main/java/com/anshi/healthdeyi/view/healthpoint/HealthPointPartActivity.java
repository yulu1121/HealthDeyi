package com.anshi.healthdeyi.view.healthpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.base.BaseApplication;
import com.anshi.healthdeyi.enty.HealthPointPartFormation;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.StatubarUtils;
import com.anshi.healthdeyi.utils.StatusBarUtils;
import com.anshi.healthdeyi.utils.Utils;
import com.anshi.healthdeyi.view.base.BaseActivity;
import com.anshi.healthdeyi.view.web.WebActivity;
import com.google.gson.Gson;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * Created by yulu on 2018/4/12.
 */

public class HealthPointPartActivity extends BaseActivity {
    private List<HealthPointPartFormation.OutBean> mList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CommonAdapter<HealthPointPartFormation.OutBean> commonAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_part);
        initView();
        String part_id = getIntent().getStringExtra("part_id");
        getPointPart(part_id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.point_part_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        final TextView mTitle = (TextView) findViewById(R.id.voice_title);
        mTitle.setText(getIntent().getStringExtra("part_title"));
        commonAdapter = new CommonAdapter<HealthPointPartFormation.OutBean>(this,R.layout.point_part_recycler_item,mList) {
            @Override
            protected void convert(ViewHolder holder, HealthPointPartFormation.OutBean outBean, int position) {
                   TextView textView =  holder.getView(R.id.point_part_name);
                   textView.setText(outBean.getAcupointclass_name());
                   RecyclerView recyclerView = holder.getView(R.id.point_child_recycler);
                   recyclerView.setLayoutManager(new GridLayoutManager(mContext,3){
                       @Override
                       public boolean canScrollVertically() {
                           return false;
                       }
                   });

                  recyclerView.setAdapter(new CommonAdapter<HealthPointPartFormation.OutBean.AcupointlistBean>(mContext,R.layout.point_part_child_item,outBean.getAcupointlist()) {

                      @Override
                      protected void convert(ViewHolder holder, final HealthPointPartFormation.OutBean.AcupointlistBean acupointlistBean, int position) {
                          TextView mText = holder.getView(R.id.point_child_part_name);
                          mText.setText(acupointlistBean.getAcupoint_name());
                          final TextView mPinyin = holder.getView(R.id.point_part_pingying);
                          mPinyin.setText(acupointlistBean.getAcupoint_pinyin());
                          holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  Intent intent = new Intent(mContext, WebActivity.class);
                                  intent.putExtra("url",acupointlistBean.getAcupoint_url());
                                  startActivity(intent);
                              }
                          });
                      }
                  });
            }
        };
        mRecyclerView.setAdapter(commonAdapter);
    }

     private void getPointPart(String partId){
         JSONObject jsonObject = new JSONObject();
         try {
             jsonObject.put("part_id",partId);
         } catch (JSONException e) {
             e.printStackTrace();
         }
         final OkHttpClient okHttpClient = BaseApplication.bulidOkHttpClient().build();
         String url = Constants.HEALTH_POINT_PART+jsonObject.toString();
         final Request request = new Request.Builder()
                 .url(url)
                 .build();
         new Thread(new Runnable() {
             @Override
             public void run() {
                 try {
                     Response response = okHttpClient.newCall(request).execute();
                     String string = response.body().string();
                     Log.e("xxx",string);
                     if (Utils.isGoodJson(string)){
                         Gson gson = new Gson();
                         final HealthPointPartFormation healthPointPartFormation = gson.fromJson(string, HealthPointPartFormation.class);
                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 mList.clear();
                                 mList.addAll(healthPointPartFormation.getOut());
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
