package com.anshi.healthdeyi.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.anshi.healthdeyi.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.kaopiz.kprogresshud.KProgressHUD;


public class DialogBuild {

    private static DialogBuild build;
    private NiftyDialogBuilder dialogBuilder;

    public interface OnConfirmListener {
        void onConfirm(Dialog dialog, boolean isConfirm);
    }

    private DialogBuild() {

    }

    public static DialogBuild getBuild() {
        if (build == null) {
            build = new DialogBuild();
        }
        return build;
    }

    // 照片选择对话框
    public NiftyDialogBuilder createPhotoDialog(final int codeOne, final int codeTwo, final Context context){
    final NiftyDialogBuilder   dialogBuilder = NiftyDialogBuilder.getInstance(context);
        View view = LayoutInflater.from(context).inflate(R.layout.select_pic_dialog_layout,null);
        TextView album = view.findViewById(R.id.select_from_album);
        TextView camera = view.findViewById(R.id.select_from_camera);
        dialogBuilder
                .isCancelable(true)
                .withMessage(null)
                .withTitle("选取照片")
                .withTitleColor("#FF9846")
                .withDialogColor("#FFFFFF")
                .withDividerColor("#C0C0C0")
                .withDuration(700)
                .withEffect(Effectstype.Slit)
                .withButton1Text("取消")
                .withIcon(R.mipmap.health_logo)
                .setCustomView(view,context)
                .withButtonDrawable(R.drawable.login_btn_selector)
                .setButton1Click(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                });
        album.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                PhotoUtils.getImageFromAlbum(codeOne, context);
            }
        });
        camera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                PhotoUtils.getImageFromCamera(codeTwo,context);
            }
        });
        return dialogBuilder;
    }



    public KProgressHUD createCommonLoadDialog(Activity activity, String message){
        return KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(message)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
}


    public NiftyDialogBuilder createNickDialog(Activity context,String title){
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
        View view = LayoutInflater.from(context).inflate(R.layout.nick_input_layout,null);
        dialogBuilder
                .isCancelable(true)
                .withMessage(null)
                .withTitle(title)
                .withTitleColor("#FF9846")
                .withDialogColor("#FFFFFF")
                .withDividerColor("#C0C0C0")
                .withDuration(700)
                .withEffect(Effectstype.Slit)
                .withButton2Text("确定")
                .withButton1Text("取消")
                .withIcon(R.mipmap.health_logo)
                .setCustomView(view,context)
                .withButtonDrawable(R.drawable.login_btn_selector)
                .setButton1Click(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                });

        return dialogBuilder;
    }

    public AlertDialog createLogOutDialog(final Context context){
        return new AlertDialog.Builder(context)
                .setTitle("退出")
                .setCancelable(false)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferenceUtils.clear(context);
                        dialogInterface.dismiss();
                        ((Activity)context).finishAffinity();
                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
    }

    public NiftyDialogBuilder createCropPhotoDialog(final int codeOne, final int codeTwo, final Context context){
        final NiftyDialogBuilder   dialogBuilder = NiftyDialogBuilder.getInstance(context);
        View view = LayoutInflater.from(context).inflate(R.layout.select_pic_dialog_layout,null);
        TextView album = view.findViewById(R.id.select_from_album);
        TextView camera = view.findViewById(R.id.select_from_camera);
        dialogBuilder
                .isCancelable(true)
                .withMessage(null)
                .withTitle("选取照片")
                .withTitleColor("#FF9846")
                .withDialogColor("#FFFFFF")
                .withDividerColor("#C0C0C0")
                .withDuration(700)
                .withEffect(Effectstype.Slit)
                .withButton1Text("取消")
                .withIcon(R.mipmap.health_logo)
                .setCustomView(view,context)
                .withButtonDrawable(R.drawable.login_btn_selector)
                .setButton1Click(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                });
        album.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                PhotoUtils.getCropFromAlbum(codeOne,context);
            }
        });
        camera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                PhotoUtils.cameraPic(codeTwo, (Activity) context);
            }
        });
        return dialogBuilder;
    }

}