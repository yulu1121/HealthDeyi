package com.anshi.healthdeyi.view.healthpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.base.BaseApplication;
import com.anshi.healthdeyi.enty.HealthPointParentFormation;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.StatubarUtils;
import com.anshi.healthdeyi.utils.StatusBarUtils;
import com.anshi.healthdeyi.utils.Utils;
import com.anshi.healthdeyi.view.base.BaseActivity;
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

public class HealthPointParentActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private List<HealthPointParentFormation.OutBean> mList = new ArrayList<>();
    private CommonAdapter<HealthPointParentFormation.OutBean> commonAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_parent);
        initView();
        String parent_id = getIntent().getStringExtra("parent_id");
        getPointParent(parent_id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
    }
    private void initView() {
        String title = getIntent().getStringExtra("title");
        TextView homeTitle = (TextView) findViewById(R.id.voice_title);
        homeTitle.setText(title);
        mRecyclerView = (RecyclerView) findViewById(R.id.point_parent_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        commonAdapter = new CommonAdapter<HealthPointParentFormation.OutBean>(this,R.layout.point_parent_recycler_item,mList) {
            @Override
            protected void convert(ViewHolder holder, final HealthPointParentFormation.OutBean outBean, int position) {
                TextView textView = holder.getView(R.id.point_parent_name);
                textView.setText(outBean.getPart_name());
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext,HealthPointPartActivity.class);
                        intent.putExtra("part_title",outBean.getPart_name());
                        intent.putExtra("part_id",outBean.getPart_id());
                        startActivity(intent);
                    }
                });
            }
        };
        mRecyclerView.setAdapter(commonAdapter);
    }


    private void getPointParent(String parentId){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("parent_id",parentId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = Constants.HEALTH_POINT_PARENT+jsonObject.toString();
        final OkHttpClient okHttpClient = BaseApplication.bulidOkHttpClient().build();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String string = response.body().string();
                    if (Utils.isGoodJson(string)){
                        Gson gson = new Gson();
                        final HealthPointParentFormation healthPointParentFormation = gson.fromJson(string, HealthPointParentFormation.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mList.clear();
                                mList.addAll(healthPointParentFormation.getOut());
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
