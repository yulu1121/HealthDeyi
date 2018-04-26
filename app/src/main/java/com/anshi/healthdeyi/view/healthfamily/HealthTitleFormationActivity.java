package com.anshi.healthdeyi.view.healthfamily;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.enty.TitleEnty;
import com.anshi.healthdeyi.utils.StatubarUtils;
import com.anshi.healthdeyi.utils.StatusBarUtils;
import com.anshi.healthdeyi.view.base.BaseActivity;
import com.anshi.healthdeyi.view.comment.CommentActivity;
import com.anshi.healthdeyi.view.comment.CommentImageActivity;
import com.anshi.healthdeyi.view.self.EaseImageView;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by yulu on 2018/4/13.
 */

public class HealthTitleFormationActivity extends BaseActivity {
    private TextView mTitle;
    private TextView mTranslate;
    private TextView mRecommend;
//    private CommentDialog mCommentDiglog;
//    private LinearLayout mRootView;
    private RecyclerView mTitleRecycler;
    private List<TitleEnty> mList;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_title_formation);
        initView();
        initData();
        //addEventListener();
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(new TitleEnty("张三","参加很好",R.drawable.banner_three));
        mList.add(new TitleEnty("李四","参加很好",R.drawable.banner_three));
        mTitleRecycler.setAdapter(new CommonAdapter<TitleEnty>(this,R.layout.title_recycler_item,mList) {
            @Override
            protected void convert(ViewHolder holder, TitleEnty titleEnty, int position) {
                mRecommend = holder.getView(R.id.item_recommend);
                String recommend = title+"#"+titleEnty.getContent();
                SpannableString spannableString = new SpannableString(recommend);
                int i = recommend.lastIndexOf("#");
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.health_text_color)),0,i+1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
                mRecommend.setText(spannableString);
                EaseImageView easeImageView = holder.getView(R.id.comment_img);
                Picasso.with(mContext).load(titleEnty.getImageId()).into(easeImageView);
                TextView mName = holder.getView(R.id.comment_name);
                mName.setText(titleEnty.getName());
            }
        });
    }

//    private void addEventListener() {
//        final int heightPixels = getResources().getDisplayMetrics().heightPixels;
//        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Rect r = new Rect(); //该对象代表一个矩形（rectangle）
//                mRootView.getWindowVisibleDisplayFrame(r); //将当前界面的尺寸传给Rect矩形
//                int deltaHeight = heightPixels - r.bottom;  //弹起键盘时的变化高度，在该场景下其实就是键盘高度。
//                //小于等于0,说明键盘隐藏
//                if (deltaHeight<=0){
//                    mCommentDiglog.dismiss();
//                }
//            }
//        });
//    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_party);
       //mRootView = (LinearLayout) findViewById(R.id.titile_root_view);
        mTitleRecycler = (RecyclerView) findViewById(R.id.mTitle_recycler);
        mTitleRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        TextView voiceTitle = (TextView) findViewById(R.id.voice_title);
        mTranslate = (TextView) findViewById(R.id.title_translate);
        title = getIntent().getStringExtra("title");
//        mCommentDiglog = new CommentDialog(this);
//        mCommentDiglog.setOnSendListener(this);
//        mCommentDiglog.setCanceledOnTouchOutside(true);
        mTitle.setText(title);
        voiceTitle.setText(title);
        mTranslate.setText("此段为测试代码,后期有数据再来填充");
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
    }

    public void toRecommend(View view) {
        Intent intent = new Intent(this, CommentActivity.class);
        startActivity(intent);
    }

    public void recommendTitle(View view) {
        //此处还需判断用户是否登录。。。
        Intent intent = new Intent(mContext, CommentImageActivity.class);
        startActivity(intent);
    }
}
