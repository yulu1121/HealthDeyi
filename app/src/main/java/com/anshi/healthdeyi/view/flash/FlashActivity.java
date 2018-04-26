package com.anshi.healthdeyi.view.flash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.anshi.healthdeyi.MainActivity;
import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.utils.SharedPreferenceUtils;
import com.anshi.healthdeyi.utils.StatubarUtils;
import com.anshi.healthdeyi.utils.StatusBarUtils;
import com.anshi.healthdeyi.view.base.BaseActivity;
import com.anshi.healthdeyi.view.guide.GuideActivity;

/**
 *
 * Created by yulu on 2018/3/31.
 */

public class FlashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean guide = SharedPreferenceUtils.getBoolean(this, "guide");
        if (guide){
            setContentView(R.layout.activity_flash_main);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },3000);
        }else {
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
    }
}
