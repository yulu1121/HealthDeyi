package com.anshi.healthdeyi.model;

import android.content.Context;

import com.anshi.healthdeyi.enty.TypeEntry;

/**
 *
 * Created by yulu on 2018/4/26.
 */

public interface ISendTypeModel {
    void getTypeData(Context context,SendTypeData data, String type);

    interface SendTypeData{
        void sendTypeData(TypeEntry typeEntry);
    }

}
