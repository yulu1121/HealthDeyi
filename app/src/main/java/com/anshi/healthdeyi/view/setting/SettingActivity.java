package com.anshi.healthdeyi.view.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.utils.DialogBuild;
import com.anshi.healthdeyi.utils.StatubarUtils;
import com.anshi.healthdeyi.utils.StatusBarUtils;
import com.anshi.healthdeyi.view.base.BaseActivity;

/**
 *
 * Created by yulu on 2018/4/25.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout mLogoutLayout;
    private RelativeLayout mAboutLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        mLogoutLayout = (RelativeLayout) findViewById(R.id.log_out_layout);
        mAboutLayout = (RelativeLayout) findViewById(R.id.about_layout);
        mLogoutLayout.setOnClickListener(this);
        mAboutLayout.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about_layout:
                Intent intent = new Intent(mContext,AboutAppActivity.class);
                startActivity(intent);
                break;
            case R.id.log_out_layout:
                DialogBuild.getBuild().createLogOutDialog(this).show();
                break;
        }
    }
}
