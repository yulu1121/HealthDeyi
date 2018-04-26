package com.anshi.healthdeyi.presenter;

import com.anshi.healthdeyi.enty.TypeEntry;

/**
 *
 * Created by yulu on 2018/4/26.
 */

public interface ISendTypePresenter {
    void getSendTypeResult();
    interface SendTypeResult{
        void sendTypeResult(TypeEntry typeEntry);
    }
}
