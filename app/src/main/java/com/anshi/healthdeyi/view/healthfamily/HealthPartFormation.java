package com.anshi.healthdeyi.view.healthfamily;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.enty.PartyEnty;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.StatubarUtils;
import com.anshi.healthdeyi.utils.StatusBarUtils;
import com.anshi.healthdeyi.utils.ToastUtils;
import com.anshi.healthdeyi.view.base.BaseActivity;

public class HealthPartFormation extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_part_formation);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
    }

    private void initView() {
        PartyEnty party = (PartyEnty) getIntent().getSerializableExtra("party");
        TextView mTitle = (TextView) findViewById(R.id.voice_title);
        mTitle.setText(party.getTitle());
        TextView textViewOne = (TextView) findViewById(R.id.party_time);
        String time = String.format(getString(R.string.party_formation_time), party.getTime());
        changeTextColor(textViewOne,time);
        TextView textViewTwo = (TextView) findViewById(R.id.party_gather_place);
        String gatherLocation = String.format(getString(R.string.party_formation_gather_location),party.getLocation());
        changeTextColor(textViewTwo,gatherLocation);
        TextView textViewThree = (TextView) findViewById(R.id.party_place);
        String location = String.format(getString(R.string.party_formation_location),party.getLocation());
        changeTextColor(textViewThree,location);
        //TextView textViewFour = (TextView) findViewById(R.id.part_notify);
        Button  joinBtn = (Button) findViewById(R.id.party_join_btn);
        switch (party.getCategory()){
            case Constants.PARTY_SCORE:
                joinBtn.setText("积分兑换");
                break;
            default:
                joinBtn.setText("参加");
                break;
        }


    }


    public void toParty(View view) {
        ToastUtils.showShortToast(this,"1期功能未做");
    }

    private void changeTextColor(TextView textView,String text){
        if (null==text){
            return;
        }
        int i = text.lastIndexOf(":");
        SpannableString span = new SpannableString(text);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,R.color.health_text_color)),i+1,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(span);
    }
}
