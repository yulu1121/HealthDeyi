package com.anshi.healthdeyi.utils;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.Toast;

import com.anshi.healthdeyi.R;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

;


/**
 *
 * Created by yulu on 2017/8/30.
 */

public class Utils {
    public static String getTime(Date date){
        String dateFormat = "yyyy-MM-dd HH:mm";
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return format.format(date);
    }

    /**
     *
     * @param
     * @param pixels
     * @return 圆角图片
     */
    public static Bitmap toRoundCornerImage(Bitmap bmp, int pixels) {
        Bitmap newBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),
                Bitmap.Config.ARGB_8888);
        // 得到画布
        Canvas canvas = new Canvas(newBmp);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // 第二个和第三个参数一样则画的是正圆的一角，否则是椭圆的一角
        canvas.drawRoundRect(rectF, pixels, pixels, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bmp, rect, rect, paint);

        return newBmp;

    }

    public static  String hidePhone4Number(String phone){
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static String hideCardLast4Number(String card){
        return card.replaceAll("(\\d{14}\\d{4})","$1****");
    }
    /**
     * 验证身份证号码
     */
    public static boolean personIdValidation(String text) {
        String regx = "[0-9]{17}[Xx]";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }

    /**
     * @param imageView imageView
     * @return bitmap
     */
    public static Bitmap imageView2BitMap(ImageView imageView){
        return ((BitmapDrawable)imageView.getDrawable()).getBitmap();
    }



    /**
     * @param context 上下文
     * @return 是否联网
     */
    public static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }



    public static int dpToPx(float dp, Resources resources){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }
    public static int getCurrentSeconds(){
        long timeMillis = System.currentTimeMillis();
        long totalSeconds = timeMillis / 1000;
        return (int) totalSeconds;
    }
    /**
     * @param path 路径
     * @return 正常图片
     */
    public static boolean isNormalImage(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        String type = options.outMimeType;
        if (TextUtils.isEmpty(type)) {
            type = "未能识别的图片";
        } else {
            type = type.substring(6, type.length());
        }
        return type.equals("png")||type.equals("jpg")||type.equals("jpeg");
    }

    public static void showToast(final Activity activity, final String word, final long time){
        activity.runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast = Toast.makeText(activity, word, Toast.LENGTH_LONG);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        toast.cancel();
                    }
                }, time);
            }
        });
    }


    public static boolean isGoodJson(String json) {

        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }

    public static int getMainTextColor(Context context){
        return  ContextCompat.getColor(context, R.color.health_text_color);
    }
    public static int getMainThemeColor(Context context){
        return ContextCompat.getColor(context, R.color.health_text_normal);
    }
    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        return bit != 'N' && cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }



    public static int getStatuBarsHeight(Context context){
        int statusBarHeight1 = -1;
//获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 =context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    public  static Boolean hasLogin(Context context){
        return SharedPreferenceUtils.getBoolean(context, "hasLogin");
    }
}
