package com.anshi.healthdeyi.view.healthfamily;

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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by yulu on 2018/4/13.
 */

public class HealthFamilyActivity extends BaseActivity implements ISendTypePresenter.SendTypeResult{
    private TabLayout mTabLayout;
    private ViewPager mVp;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private FamilyPagerAdapter adapter;
    private ISendTypePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_family);
        initView();
        presenter = new SendTypePresenter(mContext,this, Constants.FAMILY_HEALTH_TYPE);
        presenter.getSendTypeResult();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.family_tab);
        mVp = (ViewPager) findViewById(R.id.family_vp);
        adapter = new FamilyPagerAdapter(getSupportFragmentManager(),mTitles,mFragments);
        mVp.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mVp);
    }



    @Override
    public void sendTypeResult(TypeEntry typeEntry) {
        List<TypeEntry.DataBean> data = typeEntry.getData();
        for (TypeEntry.DataBean dataBean:data){
            mTitles.add(dataBean.getName());
            if (dataBean.getName().equals("活动")){
                mFragments.add(HealthFragOne.getInstance(dataBean.getId()));
                continue;
            }
            if (dataBean.getName().equals("帖子")){
                mFragments.add(HealthFragTwo.getInstance(dataBean.getId()));
            }
        }
        adapter.notifyDataSetChanged();
        mVp.setOffscreenPageLimit(data.size());
    }

    private class FamilyPagerAdapter extends FragmentPagerAdapter{
        private List<String> mTiles;
        private List<Fragment> mFragments;
        FamilyPagerAdapter(FragmentManager fm,List<String> titles,List<Fragment> fragments) {
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
