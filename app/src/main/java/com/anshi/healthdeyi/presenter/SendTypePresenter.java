package com.anshi.healthdeyi.presenter;

import android.content.Context;

import com.anshi.healthdeyi.enty.TypeEntry;
import com.anshi.healthdeyi.model.ISendTypeModel;
import com.anshi.healthdeyi.model.SendTypeModel;

/**
 *
 * Created by yulu on 2018/4/26.
 */

public class SendTypePresenter implements ISendTypePresenter,ISendTypeModel.SendTypeData {
    private Context context;
    private String type;
    private ISendTypeModel model;
    private SendTypeResult sendTypeResult;
    public SendTypePresenter(Context context,SendTypeResult result,String type){
        this.context = context;
        this.sendTypeResult = result;
        this.type = type;
        model = new SendTypeModel();
    }

    @Override
    public void getSendTypeResult() {
        model.getTypeData(context,this,type);
    }

    @Override
    public void sendTypeData(TypeEntry typeEntry) {
        sendTypeResult.sendTypeResult(typeEntry);
    }
}
