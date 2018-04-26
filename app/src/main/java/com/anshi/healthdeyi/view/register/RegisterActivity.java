package com.anshi.healthdeyi.view.register;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.base.BaseApplication;
import com.anshi.healthdeyi.enty.RegisterEntry;
import com.anshi.healthdeyi.utils.CallUtil;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.DialogBuild;
import com.anshi.healthdeyi.utils.StatubarUtils;
import com.anshi.healthdeyi.utils.StatusBarUtils;
import com.anshi.healthdeyi.utils.ToastUtils;
import com.anshi.healthdeyi.utils.Utility;
import com.anshi.healthdeyi.utils.Utils;
import com.anshi.healthdeyi.view.base.BaseActivity;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 *
 * Created by yulu on 2018/4/3.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG ="xxx" ;
    private EditText mPhoneEdit;
    private EditText mPassEdit;
    private EditText mPassConfirmEdit;
    private EditText mVerifyEdit;
    private RadioButton mVerifyRb;
    private Button mRegisterBtn;
    private boolean hasVerify;

    private int i=60;
    //控制按钮样式是否改变
    private boolean tag = true;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 0:
                    //客户端验证成功，可以进行注册,返回校验的手机和国家代码phone/country
                    try {
                        registerMember(mPhoneEdit.getText().toString(),mPassEdit.getText().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    break;
                case 1:
                    //获取验证码成功
                    ToastUtils.showShortToast(RegisterActivity.this,getString(R.string.forget_has_verify_success));
                    break;
                case 2:
                    //返回支持发送验证码的国家列表
                    Log.e("xxx",msg.obj.toString());
                    break;
                case 3:
                    ToastUtils.showShortToast(RegisterActivity.this,getString(R.string.forget_verify_fail));
                    break;


            }}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        initView();
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Message msg = new Message();
                        msg.arg1 = 0;
                        msg.obj = data;
                        handler.sendMessage(msg);

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //提交验证码成功
                        Message msg = new Message();
                        //获取验证码成功
                        msg.arg1 = 1;
                        msg.obj = R.string.forget_has_verify_success;
                        handler.sendMessage(msg);


                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        Message msg = new Message();
                        //返回支持发送验证码的国家列表
                        msg.arg1 = 2;
                        msg.obj = R.string.verify_callback_country;
                        handler.sendMessage(msg);
                        Log.d(TAG, getString(R.string.verify_callback_country));
                    }
                } else {
                    Message msg = new Message();
                    //返回支持发送验证码的国家列表
                    msg.arg1 = 3;
                    msg.obj = R.string.forget_verify_fail;
                    handler.sendMessage(msg);
                    Log.d(TAG, getString(R.string.forget_verify_fail));
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    private void initView() {
        mPhoneEdit = (EditText) findViewById(R.id.register_phone_input);
        mPassEdit = (EditText) findViewById(R.id.register_pass_input);
        mPassConfirmEdit = (EditText) findViewById(R.id.register_confirm_pass_input);
        mVerifyEdit = (EditText) findViewById(R.id.register_verify_input);
        mVerifyRb = (RadioButton) findViewById(R.id.has_verify_rb);
        mRegisterBtn = (Button) findViewById(R.id.register_btn);
        mVerifyRb.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.has_verify_rb:
                String phone = mPhoneEdit.getText().toString();
                if(phone.equals("")){
                    ToastUtils.showShortToast(RegisterActivity.this,getString(R.string.phone_is_not_empty));
                    return;
                }else{
                    //填写了手机号码
                    if(CallUtil.isMobileNO(phone)){
                        Log.e("xxx",phone);
                        //如果手机号码无误，则发送验证请求
                        mVerifyRb.setClickable(true);
                        changeBtnGetCode(mVerifyRb);
                        SMSSDK.getSupportedCountries();
                        SMSSDK.getVerificationCode("86", phone);
                        hasVerify = true;
                    }else{
                        //手机号格式有误
                        ToastUtils.showShortToast(RegisterActivity.this,getString(R.string.phone_is_wrong));
                    }
                }
                break;

            case R.id.register_btn:
                if (TextUtils.isEmpty(mPhoneEdit.getText())||TextUtils.isEmpty(mPassEdit.getText())||TextUtils.isEmpty(mVerifyEdit.getText())
                        ||TextUtils.isEmpty(mPassConfirmEdit.getText())){
                    ToastUtils.showShortToast(mContext,"请确保数据填写完整");
                    return;
                }
                if (!hasVerify){
                    ToastUtils.showShortToast(mContext,"请先获取验证码");
                    return;
                }
                if (!CallUtil.isMobileNO(mPhoneEdit.getText().toString())){
                    ToastUtils.showShortToast(mContext,"电话号码格式错误");
                    return;
                }
                if (!Utility.isPassword(mPassEdit.getText().toString())){
                    ToastUtils.showShortToast(mContext,"请输入6-12位密码");
                    return;
                }
                if (!mPassEdit.getText().toString().equals(mPassConfirmEdit.getText().toString())){
                    ToastUtils.showShortToast(mContext,"密码不一致");
                    return;
                }
                SMSSDK.submitVerificationCode("86",mPhoneEdit.getText().toString(), mVerifyEdit.getText().toString());
                break;

        }
    }

    /*
  * 改变按钮样式
  * */
    private void changeBtnGetCode(final RadioButton radioButton) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        //如果活动为空
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                radioButton.setText("获取验证码(" + i + ")");
                                radioButton.setClickable(false);
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    tag = false;
                }
                i = 60;
                tag = true;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        radioButton.setText(getString(R.string.please_have_verify));
                        radioButton.setClickable(true);
                    }
                });
            }
        };
        thread.start();
    }
    //注册会员
    private void registerMember(String phone,String pass){
        final KProgressHUD commonLoadDialog = DialogBuild.getBuild().createCommonLoadDialog(this, "正在注册...");
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
                .url(Constants.COMMON_HEADER+Constants.REGISTER)
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
                                RegisterEntry registerEntry = gson.fromJson(string, RegisterEntry.class);
                                if (registerEntry.isCode()){
                                    ToastUtils.showShortToast(mContext,"注册成功");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            overridePendingTransition(R.anim.in_left,R.anim.in_right);
                                            finish();
                                        }
                                    },1000);
                                }else {
                                    ToastUtils.showShortToast(mContext,registerEntry.getMsg());
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

}
