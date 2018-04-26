package com.anshi.healthdeyi.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.base.BaseApplication;
import com.anshi.healthdeyi.enty.LoginEntry;
import com.anshi.healthdeyi.utils.CallUtil;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.DialogBuild;
import com.anshi.healthdeyi.utils.SharedPreferenceUtils;
import com.anshi.healthdeyi.utils.StatubarUtils;
import com.anshi.healthdeyi.utils.StatusBarUtils;
import com.anshi.healthdeyi.utils.ToastUtils;
import com.anshi.healthdeyi.utils.Utility;
import com.anshi.healthdeyi.utils.Utils;
import com.anshi.healthdeyi.view.base.BaseActivity;
import com.anshi.healthdeyi.view.forgetpass.ForgetPassActivity;
import com.anshi.healthdeyi.view.register.RegisterActivity;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 *
 * Created by yulu on 2018/4/3.
 */

public class LoginActivity extends BaseActivity {
    private EditText mPhoneEdit;
    private EditText mPassEdit;
    private LocalBroadcastManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        initView();
        manager = LocalBroadcastManager.getInstance(this);
    }

    private void initView() {
        mPhoneEdit = (EditText) findViewById(R.id.login_input_phone);
        mPassEdit = (EditText) findViewById(R.id.login_pass_input);
    }

    public void toRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
    }

    public void toForgetPass(View view) {
        Intent intent = new Intent(this, ForgetPassActivity.class);
        startActivity(intent);
    }

    private void memberLogin(String phone,String pass){
        Log.e(Constants.TAG,phone+""+pass);
        final KProgressHUD commonLoadDialog = DialogBuild.getBuild().createCommonLoadDialog(this, "正在登录...");
        final OkHttpClient okHttpClient = BaseApplication.bulidOkHttpClient().build();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone",phone);
            jsonObject.put("password",pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
        final Request request = new Request.Builder()
                .url(Constants.COMMON_HEADER+Constants.LOGIN)
                .post(requestBody)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String string = okHttpClient.newCall(request).execute().body().string();
                    Log.e(Constants.TAG,string);
                    if (Utils.isGoodJson(string)){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                commonLoadDialog.dismiss();
                                Gson gson = new Gson();
                                LoginEntry loginEntry =  gson.fromJson(string,LoginEntry.class);
                                if (loginEntry.isCode()){
                                    ToastUtils.showShortToast(mContext,"登录成功");
                                    SharedPreferenceUtils.saveBoolean(mContext,"hasLogin",true);
                                    SharedPreferenceUtils.saveString(mContext,"userId",loginEntry.getData().getId());
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent();
                                            intent.setAction(Constants.HAS_LOGIN_ACTION);
                                            manager.sendBroadcast(intent);
                                            overridePendingTransition(R.anim.in_left,R.anim.in_right);
                                            finish();
                                        }
                                    },1000);
                                }else {
                                    ToastUtils.showShortToast(mContext,loginEntry.getMsg());
                                    SharedPreferenceUtils.saveBoolean(mContext,"hasLogin",false);
                                }
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void login(View view) {
        if (TextUtils.isEmpty(mPhoneEdit.getText())||TextUtils.isEmpty(mPassEdit.getText())){
            ToastUtils.showShortToast(this,"请填写手机号码或密码");
            return;
        }
        if (!CallUtil.isMobileNO(mPhoneEdit.getText().toString())){
            ToastUtils.showShortToast(this,R.string.phone_is_wrong);
            return;
        }
        if (!Utility.isPassword(mPassEdit.getText().toString())){
           ToastUtils.showShortToast(this,"请输入6-12位密码");
            return;
        }
        memberLogin(mPhoneEdit.getText().toString(),mPassEdit.getText().toString());
    }
}
