package com.anshi.healthdeyi.enty;

import java.util.List;

/**
 *
 * Created by yulu on 2018/4/26.
 */

public class CommonEmptyEntry {

    /**
     * msg : 成功
     * code : true
     * data : []
     */

    private String msg;
    private boolean code;
    private List<?> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
