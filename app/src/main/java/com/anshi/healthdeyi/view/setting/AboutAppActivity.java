package com.anshi.healthdeyi.view.setting;

import android.os.Bundle;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.utils.StatubarUtils;
import com.anshi.healthdeyi.utils.StatusBarUtils;
import com.anshi.healthdeyi.view.base.BaseActivity;

/**
 *
 * Created by yulu on 2018/4/25.
 */

public class AboutAppActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
    }
}
