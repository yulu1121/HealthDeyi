package com.anshi.healthdeyi.enty;

import java.util.List;

/**
 *
 * Created by yulu on 2018/4/12.
 */

public class PointEnty {
    /**
     * retCode : 0
     * retMsg : 请求成功
     * out : [{"part_id":"475a9ea21d8511e7bb370894ef02dbe0","part_name":"心脑血管疾病","part_icon":"http://171.221.208.2:8090/res/bodypart/bodypartIcon/20170928/f2a929a749bb4cec81b1c20f27e7f71c.jpeg","parent_id":"0"},{"part_id":"4eaad7051d8511e7bb370894ef02dbe0","part_name":"内分泌系统疾病","part_icon":"http://171.221.208.2:8090/res/bodypart/bodypartIcon/20170928/ae2a83a54a184c4488d1f0c2330a29d7.jpeg","parent_id":"0"},{"part_id":"53f56b481d8511e7bb370894ef02dbe0","part_name":"神经系统疾病","part_icon":"http://171.221.208.2:8090/res/bodypart/bodypartIcon/20170928/331fece5e15b46f58a6c0e9c2dbe1c4c.jpeg","parent_id":"0"},{"part_id":"581572731d8511e7bb370894ef02dbe0","part_name":"骨关节疾病","part_icon":"http://171.221.208.2:8090/res/bodypart/bodypartIcon/20170928/4b1ee55f0ba548848937d6539b1eb132.jpeg","parent_id":"0"},{"part_id":"5bc8aa9f1d8511e7bb370894ef02dbe0","part_name":"消化与呼吸系统","part_icon":"http://171.221.208.2:8090/res/bodypart/bodypartIcon/20170928/8a5fde4e6650450483677d755950d019.jpeg","parent_id":"0"},{"part_id":"5f6270551d8511e7bb370894ef02dbe0","part_name":"泌尿与生殖系统","part_icon":"http://171.221.208.2:8090/res/bodypart/bodypartIcon/20170928/6648e6e5f37b444da5645ee1b8b8d9bb.jpeg","parent_id":"0"}]
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
         * part_id : 475a9ea21d8511e7bb370894ef02dbe0
         * part_name : 心脑血管疾病
         * part_icon : http://171.221.208.2:8090/res/bodypart/bodypartIcon/20170928/f2a929a749bb4cec81b1c20f27e7f71c.jpeg
         * parent_id : 0
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
