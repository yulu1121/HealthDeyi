package com.anshi.healthdeyi.utils;

import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {


    /**
     * @param urlStr 需要验证的URL
     * @return 返回验证是否成功
     * @body 功能让描述
     * @author Make It
     * @QQ: 347357000
     */
    public static boolean verificaUrl(String urlStr) {
        if (urlStr == null || urlStr.length() == 0) return false;
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern patt = Pattern.compile(regex);
        Matcher matcher = patt.matcher(urlStr);
        boolean isMatch = matcher.matches();
        return isMatch;
    }
    private static final  String MAYBE_NUMBER = "maybe"+"[0-9]{0,7}$";
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,12}$";
    public static int length(String paramString) {
        int i = 0;
        for (int j = 0; j < paramString.length(); j++) {
            if (paramString.substring(j, j + 1).matches("[Α-￥]")) {
                i += 2;
            } else {
                i++;
            }
        }

        if (i % 2 > 0) {
            i = 1 + i / 2;
        } else {
            i = i / 2;
        }
        return i;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    public static int getNetType(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return networkInfo.getType();
        }
        return -1;
    }

    public static boolean isGprs(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() != ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSystemRinger(Context context) {
        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return manager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
    }

    public static boolean isJB() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean isJB1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    public static boolean isJB2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    public static boolean isL() {
        return Build.VERSION.SDK_INT >= 20;
    }

    public static boolean isKK() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static void closeInputMethod(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    //判断手机格式是否正确
    public static boolean isMobileNO(String mobiles) {
        return isMobileNO(mobiles, false);
    }
    private static final String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
    public static boolean isMobileNO(String mobiles, boolean isTEL) {
        String pattern = "";
        if (isTEL) {
            pattern = "(?:^0{0,1}1((3[0-9])|(4[57])|(7[078])|(5[0-35-9])|(8[0-35-9]))\\d{8}$)|(?:^0[1-9]{1,2}\\d{1}\\-{0,1}[2-9]\\d{6,7}$)|(?:^0[1-9]{1,2}\\d{1}\\-{0,1}[2-9]\\d{6,7}[\\-#]{1}\\d{1,5}$)";
        } else {
             pattern = PHONE_NUMBER_REG;
            // pattern = "^((13[0-9])|(14[57])|(17[078])|(15[^4,\\d])|(18[^4,\\d]))\\d{8}$";
        }
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    //判断email格式是否正确

    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    //判断是否全是数字

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 判断输入密码是否符合6-12位,且不包括特殊字符
     * @param password 密码
     * @return boolean
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }
    public static boolean isMaybeNumber(String number){
        return Pattern.matches(MAYBE_NUMBER,number);
    }
}