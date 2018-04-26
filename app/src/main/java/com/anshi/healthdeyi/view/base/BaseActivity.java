package com.anshi.healthdeyi.view.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.anshi.healthdeyi.utils.ToastUtils;

/**
 *
 * Created by yulu on 2018/3/13.
 */

public class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mContext = BaseActivity.this;

    }

    @Override
    protected void onPause() {
        super.onPause();
        ToastUtils.cancelToast();

    }
    public void back(View view){
        finish();
    }
}
