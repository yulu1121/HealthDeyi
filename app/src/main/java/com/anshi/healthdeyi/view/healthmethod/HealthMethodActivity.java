package com.anshi.healthdeyi.view.healthmethod;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

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
 * Created by yulu on 2018/4/13.
 */

public class HealthMethodActivity extends BaseActivity implements ISendTypePresenter.SendTypeResult{
    private TabLayout mTab;
    private ViewPager mVp;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private MethodPagerAdapter adapter;
    private ISendTypePresenter sendTypePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_method);
        initView();
        sendTypePresenter = new SendTypePresenter(this,this,Constants.GONGFA_HEALTH_TYPE);
        sendTypePresenter.getSendTypeResult();
    }

    private void initView() {
        mTab = (TabLayout) findViewById(R.id.method_tab);
        mVp = (ViewPager) findViewById(R.id.method_vp);
        adapter = new MethodPagerAdapter(getSupportFragmentManager(),mTitles,fragments);
        mVp.setAdapter(adapter);
        mTab.setupWithViewPager(mVp);
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
            if (dataBean.getName().contains("资讯")){
                fragments.add(CommonFrag.getInstance(dataBean.getId(), Constants.WEB_ACTION));
                continue;
            }
            if (dataBean.getName().contains("视频")){
                fragments.add(CommonFrag.getInstance(dataBean.getId(), Constants.MOVIE_ACTION));
            }
        }
        adapter.notifyDataSetChanged();
        mVp.setOffscreenPageLimit(data.size());
    }


    private class MethodPagerAdapter extends FragmentPagerAdapter{

        private List<String> mTiles;
        private List<Fragment> mFragments;
        MethodPagerAdapter(FragmentManager fm,List<String> titles,List<Fragment> fragments) {
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
