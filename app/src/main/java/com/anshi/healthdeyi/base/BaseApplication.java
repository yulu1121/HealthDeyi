package com.anshi.healthdeyi.base;

import android.app.Application;

import com.mob.MobSDK;

import okhttp3.OkHttpClient;

/**
 *
 * Created by yulu on 2018/4/2.
 */

public class BaseApplication extends Application {
 private static OkHttpClient.Builder okHttpClient =null;
    public static OkHttpClient.Builder bulidOkHttpClient(){
        if(null==okHttpClient){
            return new OkHttpClient.Builder();
        }
        return okHttpClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bulidOkHttpClient();
        MobSDK.init(this);
    }
}
