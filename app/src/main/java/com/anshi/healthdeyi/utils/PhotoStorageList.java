package com.anshi.healthdeyi.utils;

import android.app.Activity;
import android.os.storage.StorageManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 获取存储卡路径
 * Created by yulu on 2017/9/8.
 */

public class PhotoStorageList {
    private Activity mActivity;
    private StorageManager mStorageManager;
    private Method mMethodGetPaths;

    public PhotoStorageList(Activity activity) {
        mActivity = activity;
        if (mActivity != null) {
            mStorageManager = (StorageManager)mActivity
                    .getSystemService(Activity.STORAGE_SERVICE);
            try {
                mMethodGetPaths = mStorageManager.getClass()
                        .getMethod("getVolumePaths");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public String[] getVolumePaths() {
        String[] paths = null;

        try {
            paths = (String[]) mMethodGetPaths.invoke(mStorageManager);
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return paths;
    }
}

