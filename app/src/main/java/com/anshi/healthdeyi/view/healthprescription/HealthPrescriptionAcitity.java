package com.anshi.healthdeyi.view.healthprescription;

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
 * Created by yulu on 2018/4/11.
 */

public class HealthPrescriptionAcitity extends BaseActivity implements ISendTypePresenter.SendTypeResult {
    private ViewPager mVp;
    private TabLayout mTabLayout;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private PrePagerAdapter adapter;
    private ISendTypePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        initView();
        presenter = new SendTypePresenter(this,this,Constants.METHOD_HEALTH_TYPE);
        presenter.getSendTypeResult();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
    }
    private void initView() {
        mVp = (ViewPager) findViewById(R.id.prescription_vp);
        mTabLayout = (TabLayout) findViewById(R.id.prescription_tab);
        adapter = new PrePagerAdapter(getSupportFragmentManager(),mFragments,mTitles);
        mVp.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mVp);
    }



    @Override
    public void sendTypeResult(TypeEntry typeEntry) {
        List<TypeEntry.DataBean> data = typeEntry.getData();
        for (TypeEntry.DataBean dataBean:data){
            mTitles.add(dataBean.getName());
            mFragments.add(CommonFrag.getInstance(dataBean.getId(), Constants.WEB_ACTION));
        }
        adapter.notifyDataSetChanged();
        mVp.setOffscreenPageLimit(data.size());
    }

    private class PrePagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;
        List<String> titles;
        PrePagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return null==fragments?0:fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null==titles?"":titles.get(position);
        }
    }

}
