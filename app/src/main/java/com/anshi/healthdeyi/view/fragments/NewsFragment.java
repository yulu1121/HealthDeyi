package com.anshi.healthdeyi.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.enty.TypeEntry;
import com.anshi.healthdeyi.presenter.ISendTypePresenter;
import com.anshi.healthdeyi.presenter.SendTypePresenter;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.view.fragments.common.CommonFrag;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by yulu on 2018/3/31.
 */

public class NewsFragment extends Fragment implements ISendTypePresenter.SendTypeResult {
    private Context mContext;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    private NewsFragmentManager adapter;
    private ISendTypePresenter sendTypePresenter;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        sendTypePresenter = new SendTypePresenter(mContext,this,Constants.NEWS_TYPE);
        sendTypePresenter.getSendTypeResult();
    }

    public static  NewsFragment getInstance(){
        return new  NewsFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_news_main,container,false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        mTabLayout = view.findViewById(R.id.news_tab);
        mViewPager = view.findViewById(R.id.news_vp);
        adapter = new NewsFragmentManager(getChildFragmentManager(),fragmentList,mTitleList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void sendTypeResult(TypeEntry typeEntry) {
        List<TypeEntry.DataBean> data = typeEntry.getData();
        for (TypeEntry.DataBean dataBean:data){
            mTitleList.add(dataBean.getName());
            fragmentList.add(CommonFrag.getInstance(dataBean.getId(), Constants.WEB_ACTION));
        }
        adapter.notifyDataSetChanged();
        mViewPager.setOffscreenPageLimit(data.size());
    }

    private class NewsFragmentManager extends FragmentPagerAdapter{
        private List<Fragment> fragments;
        private List<String> mTitles;
        NewsFragmentManager(FragmentManager fm,List<Fragment> fragments,List<String> mTitles) {
            super(fm);
            this.fragments = fragments;
            this.mTitles = mTitles;
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
            return mTitles.get(position);
        }
    }
}
