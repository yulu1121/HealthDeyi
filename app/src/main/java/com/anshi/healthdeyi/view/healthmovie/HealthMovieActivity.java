package com.anshi.healthdeyi.view.healthmovie;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.enty.TypeEntry;
import com.anshi.healthdeyi.presenter.ISendTypePresenter;
import com.anshi.healthdeyi.presenter.SendTypePresenter;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.StatubarUtils;
import com.anshi.healthdeyi.utils.StatusBarUtils;
import com.anshi.healthdeyi.view.base.BaseActivity;
import com.anshi.healthdeyi.view.fragments.common.CommonFrag;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by yulu on 2018/4/3.
 */

public class HealthMovieActivity extends BaseActivity implements ISendTypePresenter.SendTypeResult {
    private List<Fragment> fragments = new ArrayList<>();
    private ViewPager mVp;
    private TabLayout mTabLayout;
    private List<String> mTitles = new ArrayList<>();
    private VoicePagerAdapter adapter;
    private ISendTypePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_voice);
        initView();
        presenter = new SendTypePresenter(this,this,Constants.VIDEO_HEALTH_TYPE);
        presenter.getSendTypeResult();
        //loadFragments();
    }

    private void initView() {
        TextView mTitle = (TextView) findViewById(R.id.voice_title);
        mTitle.setText("眉寿之像");
        mVp = (ViewPager) findViewById(R.id.voice_vp);
        mTabLayout = (TabLayout) findViewById(R.id.voice_tab);
        adapter = new VoicePagerAdapter(getSupportFragmentManager(),mTitles,fragments);
        mVp.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mVp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
    }


    @Override
    public void sendTypeResult(TypeEntry typeEntry) {
        List<TypeEntry.DataBean> data = typeEntry.getData();
        for (TypeEntry.DataBean dataBean:data){
            mTitles.add(dataBean.getName());
            fragments.add(CommonFrag.getInstance(dataBean.getId(),Constants.MOVIE_ACTION));
        }
        adapter.notifyDataSetChanged();
        mVp.setOffscreenPageLimit(data.size());
    }


    private class VoicePagerAdapter extends FragmentPagerAdapter {
        private List<String> mTiles;
        private List<Fragment> mFragments;
        VoicePagerAdapter(FragmentManager fm,List<String> titles,List<Fragment> fragments) {
            super(fm);
            this.mTiles = titles;
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return null==mFragments?0:mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTiles.get(position);
        }
    }
}
