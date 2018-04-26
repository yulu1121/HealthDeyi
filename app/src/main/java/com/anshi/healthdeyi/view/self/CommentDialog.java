package com.anshi.healthdeyi.view.self;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anshi.healthdeyi.R;

/**
 *
 * Created by yulu on 2018/4/24.
 */


public class  CommentDialog extends Dialog implements View.OnClickListener {
        public interface OnSendListener {
            void sendComment(String content);
        }
        private Context mContext;
        private  EditText mEdittext;
        private TextView mTvCancel;
        private TextView mTvSend;

    private OnSendListener onSendListener;

        public void setOnSendListener(OnSendListener onSendListener) {
            this.onSendListener = onSendListener;
        }

        public CommentDialog(Context context) {
            super(context, R.style.Dialog);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.mContext = context;
        }

    private Handler handler = new Handler();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            View v = (ViewGroup) View.inflate(mContext,
                    R.layout.comment_edittext_dialoglayout, null);

            initView(v);
            setContentView(v);
            setLayout();
            setOnShowListener(new OnShowListener() {



                @Override
                public void onShow(DialogInterface dialog) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showKeyboard();
                            }
                        },200);
                }
            });
        }

        private void initView(View v) {
            mEdittext = (EditText) v.findViewById(R.id.et_comment);
            mTvCancel = (TextView) v.findViewById(R.id.tv_cancel);
            mTvSend = (TextView) v.findViewById(R.id.tv_send);
            mTvCancel.setOnClickListener(this);
            mTvSend.setOnClickListener(this);
        }

        private void setLayout() {
            getWindow().setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams p = getWindow().getAttributes();
            p.width = WindowManager.LayoutParams.MATCH_PARENT;
            p.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(p);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_cancel:
                    dismiss();
                    break;
                case R.id.tv_send:
                    String content = mEdittext.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        Toast.makeText(mContext, "请输入", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dismiss();
                    if (onSendListener != null) {
                        onSendListener.sendComment(content);
                    }
                    break;
                default:
                    break;
            }
        }
    private void showKeyboard() {
        if(mEdittext!=null){
            //设置可获得焦点
            mEdittext.setFocusable(true);
            mEdittext.setFocusableInTouchMode(true);
            //请求获得焦点
            mEdittext.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) mEdittext
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(mEdittext, 0);
        }
    }
}

