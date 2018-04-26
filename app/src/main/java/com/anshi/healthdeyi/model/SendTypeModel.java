package com.anshi.healthdeyi.model;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.anshi.healthdeyi.base.BaseApplication;
import com.anshi.healthdeyi.enty.TypeEntry;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.ToastUtils;
import com.anshi.healthdeyi.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *
 * Created by yulu on 2018/4/26.
 */

public class SendTypeModel implements ISendTypeModel {
    @Override
    public void getTypeData(final Context context, final SendTypeData data, String type) {
        final OkHttpClient okHttpClient = BaseApplication.bulidOkHttpClient().build();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pid",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
        final Request request = new Request.Builder()
                .url(Constants.GET_TYPE_URL)
                .post(requestBody)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String string = okHttpClient.newCall(request).execute().body().string();
                    if (Utils.isGoodJson(string)){
                        Gson gson = new Gson();
                        final TypeEntry typeEntry = gson.fromJson(string, TypeEntry.class);
                        Observable.just(typeEntry)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<TypeEntry>() {
                                    @Override
                                    public void call(TypeEntry typeEntry) {
                                        data.sendTypeData(typeEntry);
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        ToastUtils.showShortToast(context,typeEntry.getMsg());
                                        throwable.printStackTrace();
                                    }
                                });
                    }
                } catch (IOException e) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,"链接超时", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
