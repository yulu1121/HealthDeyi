package com.anshi.healthdeyi.view.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.DialogBuild;
import com.anshi.healthdeyi.utils.Utils;
import com.anshi.healthdeyi.view.fragments.myfrag.MyFragOne;
import com.anshi.healthdeyi.view.fragments.myfrag.MyFragThree;
import com.anshi.healthdeyi.view.fragments.myfrag.MyFragTwo;
import com.anshi.healthdeyi.view.login.LoginActivity;
import com.anshi.healthdeyi.view.self.EaseImageView;
import com.anshi.healthdeyi.view.setting.SettingActivity;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by yulu on 2018/3/31.
 */

public class MyFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private ViewPager mVp;
    private TabLayout mTabLayout;
    private List<Fragment> fragmentList;
    private List<String> mTitleList;
    private RelativeLayout mRelativeLayout;
    private TextView mNickText;
    private EaseImageView mHeadImage;
    private LocalBroadcastManager manager;
    public static final int MY_HEAD_CODE = 101;
    public static final int MY_HEAD_CAMERA = 102;
    public static final int  MY_REQUESTCODE_CUTTING = 10;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        manager = LocalBroadcastManager.getInstance(mContext);
    }

    public static  MyFragment getInstance(){
        return new  MyFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_my_main,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ImageButton settingIb = view.findViewById(R.id.to_setting);
        mVp = view.findViewById(R.id.my_vp);
        mTabLayout = view.findViewById(R.id.my_tab);
        mHeadImage = view.findViewById(R.id.my_header);
        mNickText = view.findViewById(R.id.my_nick_text);
        settingIb.setOnClickListener(this);
        mRelativeLayout = view.findViewById(R.id.personal_formation_layout);
        if (!Utils.hasLogin(mContext)){
            mRelativeLayout.setOnClickListener(this);
            mNickText.setText("您还未登录");
        }else {
            mHeadImage.setOnClickListener(this);
            mNickText.setOnClickListener(this);
            mNickText.setText("您还没有昵称");
        }
    }

    private void refresh(){
        mNickText.setText("您还没有昵称");
        mHeadImage.setOnClickListener(this);
        mNickText.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadFragMents();
        mVp.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mVp);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.HAS_LOGIN_ACTION);
        manager.registerReceiver(receiver,intentFilter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.HAS_LOGIN_ACTION)){
                    refresh();
                }
        }
    };

    private void loadFragMents(){
        fragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        fragmentList.add(MyFragOne.getInstance());
        fragmentList.add(MyFragTwo.getInstance());
        fragmentList.add(MyFragThree.getInstance());
        mTitleList.add("我的帖子");
        mTitleList.add("我的收藏");
        mTitleList.add("我的活动");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_formation_layout:
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                break;
            case R.id.my_header:
                if (Utils.hasLogin(mContext)){

                    NiftyDialogBuilder cropPhotoDialog = DialogBuild.getBuild().createCropPhotoDialog(MY_HEAD_CODE, MY_HEAD_CAMERA, mContext);
                    cropPhotoDialog.show();
                }

                break;
            case R.id.my_nick_text:
                if (Utils.hasLogin(mContext)){
                    final NiftyDialogBuilder nickDialog = DialogBuild.getBuild().createNickDialog(getActivity(),"修改昵称");
                    final EditText editText = nickDialog.findViewById(R.id.nick_edit);
                    nickDialog.setButton2Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nickDialog.dismiss();
                            String nickString = editText.getText().toString();
                            if (TextUtils.isEmpty(nickString)) {
                                Toast.makeText(mContext,"名字不能为空", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            updateRemoteNick(nickString);
                        }
                    });
                    nickDialog.show();
                }
                break;
            case R.id.to_setting:
                Intent intent1 = new Intent(mContext, SettingActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void updateRemoteNick(String nickString) {
        mNickText.setText(nickString);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter{

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return null==fragmentList?0:fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null==mTitleList?"":mTitleList.get(position);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.unregisterReceiver(receiver);
    }

    public void setHeadImage(Uri uri){
        String path = uri.getPath();
        File file = new File(path);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Picasso.with(mContext).load(file).into(mHeadImage);
    }
}
