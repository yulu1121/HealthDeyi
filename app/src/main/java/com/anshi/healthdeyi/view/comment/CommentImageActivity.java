package com.anshi.healthdeyi.view.comment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.utils.DialogBuild;
import com.anshi.healthdeyi.utils.PhotoStorageList;
import com.anshi.healthdeyi.utils.PhotoUtils;
import com.anshi.healthdeyi.utils.SharedPreferenceUtils;
import com.anshi.healthdeyi.utils.StatubarUtils;
import com.anshi.healthdeyi.utils.StatusBarUtils;
import com.anshi.healthdeyi.utils.ToastUtils;
import com.anshi.healthdeyi.utils.Utils;
import com.anshi.healthdeyi.view.base.BaseActivity;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 *
 * Created by yulu on 2018/4/25.
 */

public class CommentImageActivity extends BaseActivity {
    private static final int ONE_CODE =0;
    private static final int ONE_CODE_CAMERA = 1;
    private static final String KEY_ONE = "health_comment_image";
    private DialogBuild dialogBuild;
    private PhotoStorageList storageList;
    private NiftyDialogBuilder mSelectDialog;
    private ImageView imageView;
    private EditText mEdit;
    private TextView mExtraNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_comment_image);
        dialogBuild = DialogBuild.getBuild();
        storageList = new PhotoStorageList(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.easy_add_one);
        initListener(imageView,ONE_CODE,ONE_CODE_CAMERA,KEY_ONE);
        mEdit = (EditText) findViewById(R.id.easy_edit);
        mExtraNum = (TextView) findViewById(R.id.has_extra_num);
        String text = "还可输入20字";
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(Color.RED),4,text.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mExtraNum.setText(spannableString);
        mEdit.addTextChangedListener(new TagNameTextWatch(mEdit,20));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&data!=null){
            switch (requestCode){
                case ONE_CODE:
                    doPhoto(requestCode,data);
                    break;
                case ONE_CODE_CAMERA:
                    doPhoto(requestCode,data);
                    break;
            }
        }
    }

    private void doPhoto(int requestCode, Intent data) {
        switch (requestCode){
            case ONE_CODE:
                String picPathOne = PhotoUtils.getPath(data,this);
                if (!Utils.isNormalImage(picPathOne)){
                    ToastUtils.showShortToast(this,getString(R.string.photo_jpg_must));
                }else {
                    String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy"+System.currentTimeMillis()+".jpg";
                    String compressImage = PhotoUtils.compressImage(picPathOne, storePath, 80);
                    SharedPreferenceUtils.saveString(this,KEY_ONE, compressImage);
                    Picasso.with(this).load(new File(compressImage)).into(imageView);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                break;

            case ONE_CODE_CAMERA:
                String cameraImageOne = PhotoUtils.getCameraImage(imageView,data,storageList,this);
                SharedPreferenceUtils.saveString(this,KEY_ONE,cameraImageOne);
                Picasso.with(this).load(new File(cameraImageOne)).into(imageView);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;

        }
    }

    private  void initListener(final ImageView imageView, final int code, final int codeTwo, final String key) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectDialog = dialogBuild.createPhotoDialog(code, codeTwo, mContext);
                mSelectDialog.show();
            }
        });
    }

    public void onClickTransLate(View view) {

    }

    private class TagNameTextWatch implements TextWatcher {
        private EditText et;
        private int maxLenth;
        TagNameTextWatch(EditText et,int maxLenth) {
            this.maxLenth = maxLenth;
            this.et = et;    }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {

            Editable editable = et.getText();
            int len = editable.length();
            if(len > maxLenth) {
                mExtraNum.setText("输入超过20字了");
                mExtraNum.setTextColor(Color.RED);
                int selEndIndex = Selection.getSelectionEnd(editable);
                String str = editable.toString();
                //截取新字符串
                String newStr = str.substring(0,maxLenth);
                mEdit.setText(newStr);
                editable = mEdit.getText();

                //新字符串的长度
                int newLen = editable.length();
                //旧光标位置超过字符串长度
                if(selEndIndex > newLen)
                {
                    selEndIndex = editable.length();
                }
                //设置新光标所在的位置
                Selection.setSelection(editable, selEndIndex);

            }else {
                String text = "还可输入"+(maxLenth-s.length())+"字";
                SpannableString spannableString = new SpannableString(text);
                spannableString.setSpan(new ForegroundColorSpan(Color.RED),4,text.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mExtraNum.setText(spannableString);
            }
        }
    }
}
