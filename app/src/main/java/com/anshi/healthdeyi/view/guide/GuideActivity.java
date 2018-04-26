package com.anshi.healthdeyi.view.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.anshi.healthdeyi.MainActivity;
import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.utils.SharedPreferenceUtils;
import com.anshi.healthdeyi.utils.StatubarUtils;
import com.anshi.healthdeyi.utils.StatusBarUtils;
import com.anshi.healthdeyi.view.base.BaseActivity;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by yulu on 2018/4/4.
 */

public class GuideActivity extends BaseActivity {
    private Banner mBanner;
    private List<Integer> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_main);
        initView();
        addEventListener();
    }

    private void initView() {
        mBanner = (Banner) findViewById(R.id.guide_banner);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setImageLoader(new PicassoImageLoader());
        mBanner.setBannerAnimation(Transformer.DepthPage);
        initGuideData();
        mBanner.setImages(mList);
        mBanner.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
    }

    private void addEventListener(){
        mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==mList.size()-1){
                    findViewById(R.id.now_go).setVisibility(View.VISIBLE);
                }else {
                    findViewById(R.id.now_go).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void toMain(View view) {
        SharedPreferenceUtils.saveBoolean(this,"guide",true);
        Intent intent  = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private class PicassoImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            int realPath = (int) path;
            Picasso.with(context).load(realPath).into(imageView);
        }
    }
    private void initGuideData(){
        mList = new ArrayList<>();
        mList.add(R.drawable.splash);
        mList.add(R.drawable.splash_icon_1);
        mList.add(R.drawable.splash_icon_2);
        mList.add(R.drawable.splash_icon_3);
    }
}
