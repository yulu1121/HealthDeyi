package com.anshi.healthdeyi.enty;

import java.util.List;

/**
 *
 * Created by yulu on 2018/4/12.
 */

public class HealthPointParentFormation {

    /**
     * retCode : 0
     * retMsg : 请求成功
     * out : [{"part_id":"001f733d467b11e7b0820894ef159f97","part_name":"高血压","part_icon":"","parent_id":"475a9ea21d8511e7bb370894ef02dbe0"},{"part_id":"09596225467b11e7b0820894ef159f97","part_name":"高血脂","part_icon":"","parent_id":"475a9ea21d8511e7bb370894ef02dbe0"},{"part_id":"119a708a467b11e7b0820894ef159f97","part_name":"冠心病","part_icon":"","parent_id":"475a9ea21d8511e7bb370894ef02dbe0"},{"part_id":"1fe629d0467b11e7b0820894ef159f97","part_name":"心梗","part_icon":"","parent_id":"475a9ea21d8511e7bb370894ef02dbe0"},{"part_id":"2fcfe165467b11e7b0820894ef159f97","part_name":"脑梗","part_icon":"","parent_id":"475a9ea21d8511e7bb370894ef02dbe0"}]
     */

    private int retCode;
    private String retMsg;
    private List<OutBean> out;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public List<OutBean> getOut() {
        return out;
    }

    public void setOut(List<OutBean> out) {
        this.out = out;
    }

    public static class OutBean {
        /**
         * part_id : 001f733d467b11e7b0820894ef159f97
         * part_name : 高血压
         * part_icon :
         * parent_id : 475a9ea21d8511e7bb370894ef02dbe0
         */

        private String part_id;
        private String part_name;
        private String part_icon;
        private String parent_id;

        public String getPart_id() {
            return part_id;
        }

        public void setPart_id(String part_id) {
            this.part_id = part_id;
        }

        public String getPart_name() {
            return part_name;
        }

        public void setPart_name(String part_name) {
            this.part_name = part_name;
        }

        public String getPart_icon() {
            return part_icon;
        }

        public void setPart_icon(String part_icon) {
            this.part_icon = part_icon;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }
    }
}
