package com.anshi.healthdeyi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.StatubarUtils;
import com.anshi.healthdeyi.utils.StatusBarUtils;
import com.anshi.healthdeyi.utils.ToastUtils;
import com.anshi.healthdeyi.utils.Utils;
import com.anshi.healthdeyi.view.fragments.HomeFragment;
import com.anshi.healthdeyi.view.fragments.MyFragment;
import com.anshi.healthdeyi.view.fragments.NewsFragment;
import com.anshi.healthdeyi.view.fragments.ServiceFragment;

import java.io.File;
import java.io.IOException;

import static com.anshi.healthdeyi.view.fragments.MyFragment.MY_HEAD_CAMERA;
import static com.anshi.healthdeyi.view.fragments.MyFragment.MY_HEAD_CODE;
import static com.anshi.healthdeyi.view.fragments.MyFragment.MY_REQUESTCODE_CUTTING;

public class MainActivity extends AppCompatActivity  {
    private int mNormalColor;
    private int mSelectColor;
    private RadioGroup mRadioGroup;
    private Fragment mCurrentFrag;
    private FragmentManager mFragManager;
    private HomeFragment mHomeFrag;
    private MyFragment mMyFrag;
    private NewsFragment mNewsFrag;
    private ServiceFragment mServiceFrag;
    private RadioButton mHomeRb;
    private RadioButton mNewsRb;
    private RadioButton mMyRb;
    private RadioButton mServiceRb;
    private  boolean isExit = false;
    private RelativeLayout mRootView;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    isExit = false;
                    break;
            }
        }
    };
    private Uri mCutUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loadFragments();
        initClick();
        addEventListener();
    }


    private void initView() {
        mSelectColor = Utils.getMainTextColor(this);
        mNormalColor = Utils.getMainThemeColor(this);
        mRadioGroup = (RadioGroup) findViewById(R.id.main_rg);
        mHomeRb = (RadioButton) findViewById(R.id.main_home_rb);
        mNewsRb = (RadioButton) findViewById(R.id.main_news_rb);
        mMyRb = (RadioButton) findViewById(R.id.main_my_rb);
        mServiceRb = (RadioButton) findViewById(R.id.main_service_rb);
        mRootView = (RelativeLayout) findViewById(R.id.main_rootview);
    }


    private void loadFragments(){
        mFragManager = getSupportFragmentManager();
        mHomeFrag = HomeFragment.getInstance();
        mMyFrag = MyFragment.getInstance();
        mNewsFrag = NewsFragment.getInstance();
        mServiceFrag = ServiceFragment.getInstance();
        mCurrentFrag = mHomeFrag;
    }


    private void initClick(){
        mRadioGroup.check(R.id.main_home_rb);
        mFragManager.beginTransaction().add(R.id.main_container,mHomeFrag).commit();
        mHomeRb.setTextColor(mSelectColor);
    }
    private void addEventListener(){
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.main_home_rb:
                        setFragment(mHomeFrag);
                        setColor();
                        mHomeRb.setTextColor(mSelectColor);
                        break;
                    case R.id.main_news_rb:
                        setFragment(mNewsFrag);
                        setColor();
                        mNewsRb.setTextColor(mSelectColor);
                        break;
                    case R.id.main_my_rb:
                        setFragment(mMyFrag);
                        setColor();
                        mMyRb.setTextColor(mSelectColor);
                        break;
                    case R.id.main_service_rb:
                        setFragment(mServiceFrag);
                        setColor();
                        mServiceRb.setTextColor(mSelectColor);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }
    //设置当前fragment
    private void setFragment(Fragment fragment){
        if(!fragment.isAdded()){
            mFragManager.beginTransaction().hide(mCurrentFrag).add(R.id.main_container,fragment).commitAllowingStateLoss();
        }else{
            mFragManager.beginTransaction().hide(mCurrentFrag).show(fragment).commitAllowingStateLoss();
        }
        mCurrentFrag =  fragment;
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
        final int heightPixels = getResources().getDisplayMetrics().heightPixels;
        onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect(); //该对象代表一个矩形（rectangle）
                mRootView.getWindowVisibleDisplayFrame(r); //将当前界面的尺寸传给Rect矩形
                int deltaHeight = heightPixels - r.bottom;  //弹起键盘时的变化高度，在该场景下其实就是键盘高度。
                if (deltaHeight>100){
                    //61.5f为底部导航栏的高度,此处为hardcode,actionBarSize为56dp
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRadioGroup.getLayoutParams();
                    layoutParams.height = 0;
                    mRadioGroup.setLayoutParams(layoutParams);
                    mRadioGroup.setVisibility(View.INVISIBLE);
                }else {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRadioGroup.getLayoutParams();
                    layoutParams.height = Utils.dpToPx(61.5f,getResources());
                    mRadioGroup.setLayoutParams(layoutParams);
                    mRadioGroup.setVisibility(View.VISIBLE);
                }
            }
        };
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    //还原默认字体颜色
    private void setColor(){
        mHomeRb.setTextColor(mNormalColor);
        mServiceRb.setTextColor(mNormalColor);
        mNewsRb.setTextColor(mNormalColor);
        mMyRb.setTextColor(mNormalColor);
    }
    /**
     * 退出应用程序
     */
    public void exit(){
        if (!isExit) {
            isExit = true;
            ToastUtils.showShortToast(this,"再按一次退出德益寿");
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finishAffinity();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRadioGroup.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case MY_HEAD_CODE: //从相册图片后返回的uri
                    //启动裁剪
                    startActivityForResult(CutForPhoto(data.getData()),MY_REQUESTCODE_CUTTING);
                    break;
                case MY_HEAD_CAMERA: //相机返回的 uri
                    //启动裁剪
                    try {
                        String path = getExternalCacheDir().getPath();
                        String name = "output.png";
                        startActivityForResult(CutForCamera(path,name),MY_REQUESTCODE_CUTTING);
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    break;
                case MY_REQUESTCODE_CUTTING:
                    mMyFrag.setHeadImage(mCutUri);
                    break;
            }
        }
    }

    /**
     * 拍照之后，启动裁剪
     * @param camerapath 路径
     * @param imgname img 的名字
     *
     */
    private Intent CutForCamera(String camerapath, String imgname) {
        try {

            //设置裁剪之后的图片路径文件
            File cutfile = new File(Environment.getExternalStorageDirectory().getPath(),
                    "cutcamera.png"); //随便命名一个
            if (cutfile.exists()){ //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
                cutfile.delete();
            }
            cutfile.createNewFile();
            //初始化 uri
            Uri imageUri ; //返回来的 uri
            Uri outputUri; //真实的 uri
            Intent intent = new Intent("com.android.camera.action.CROP");
            //拍照留下的图片
            File camerafile = new File(camerapath,imgname);
            if (Build.VERSION.SDK_INT >= 24) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                imageUri = FileProvider.getUriForFile(this,
                        "com.anshi.healthdeyi.fileprovider",
                        camerafile);
            } else {
                imageUri = Uri.fromFile(camerafile);
            }
            outputUri = Uri.fromFile(cutfile);
            //把这个 uri 提供出去，就可以解析成 bitmap了
            mCutUri = outputUri;
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop",true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX",1);
            intent.putExtra("aspectY",1);
            //设置要裁剪的宽高
            intent.putExtra("outputX",300);
            intent.putExtra("outputY",300);
            intent.putExtra("scale",true);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data",false);
            if (imageUri != null) {
                Log.e("xxx","fliepovider\t"+imageUri.getPath());
                intent.setDataAndType(imageUri, "image/*");
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true);
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            return intent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 图片裁剪
     * @param uri
     * @return
     */
    private Intent CutForPhoto(Uri uri) {
        try {
            //直接裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //设置裁剪之后的图片路径文件
            File cutfile = new File(Environment.getExternalStorageDirectory().getPath(),
                    "cutcamera.png"); //随便命名一个
            if (cutfile.exists()){ //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
                cutfile.delete();
            }
            cutfile.createNewFile();
            //初始化 uri
            Uri outputUri; //真实的 uri
            Log.d(Constants.TAG, "CutForPhoto: "+cutfile);
            outputUri = Uri.fromFile(cutfile);
            mCutUri = outputUri;
            Log.d(Constants.TAG, "mCameraUri: "+mCutUri);
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop",true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX",1);
            intent.putExtra("aspectY",1);
            //设置要裁剪的宽高
            intent.putExtra("outputX", 300); //200dp
            intent.putExtra("outputY",300);
            intent.putExtra("scale",true);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data",false);
            if (uri != null) {
                intent.setDataAndType(uri, "image/*");
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true);
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            return intent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
