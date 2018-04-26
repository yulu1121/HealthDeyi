package com.anshi.healthdeyi.enty;

import java.io.Serializable;
import java.util.List;

/**
 *
 * Created by yulu on 2018/4/11.
 */

public class PrescriptionEnty implements Serializable{

    /**
     * retCode : 0
     * retMsg : 请求成功
     * out : [{"class_id":"15776f2b31ff11e7b0820894ef159f97","class_level":2,"class_url":"","class_icon":"","parent_class":"eba54a6f144b11e7bb370894ef02dbe0","create_time":1494035790000,"create_user":"88762c0faa1811e6bf240894ef159f97","notes":"疾病百科","class_name":"睡眠障碍"},{"class_id":"4577c232320511e7b0820894ef159f97","class_level":2,"class_url":"","class_icon":"","parent_class":"eba54a6f144b11e7bb370894ef02dbe0","create_time":1494038448000,"create_user":"07f283303ea011e688a02c56dc7bca34","notes":"心血管常见疾病\n","class_name":"补益方"},{"class_id":"69c57508320511e7b0820894ef159f97","class_level":2,"class_url":"","class_icon":"","parent_class":"eba54a6f144b11e7bb370894ef02dbe0","create_time":1494038509000,"create_user":"07f283303ea011e688a02c56dc7bca34","notes":"","class_name":"三高"},{"class_id":"3869df2fab2911e7af2c0894ef159f97","class_level":2,"class_url":"","class_icon":"","parent_class":"eba54a6f144b11e7bb370894ef02dbe0","create_time":1507357947000,"create_user":"88762c0faa1811e6bf240894ef159f97","notes":"","class_name":"心脑血管"},{"class_id":"405c9dc3ab2911e7af2c0894ef159f97","class_level":2,"class_url":"","class_icon":"","parent_class":"eba54a6f144b11e7bb370894ef02dbe0","create_time":1507357960000,"create_user":"88762c0faa1811e6bf240894ef159f97","notes":"","class_name":"便秘"},{"class_id":"4732168eab2911e7af2c0894ef159f97","class_level":2,"class_url":"","class_icon":"","parent_class":"eba54a6f144b11e7bb370894ef02dbe0","create_time":1507357971000,"create_user":"88762c0faa1811e6bf240894ef159f97","notes":"","class_name":"骨关节"},{"class_id":"4c7340f7ab2911e7af2c0894ef159f97","class_level":2,"class_url":"","class_icon":"","parent_class":"eba54a6f144b11e7bb370894ef02dbe0","create_time":1507357980000,"create_user":"88762c0faa1811e6bf240894ef159f97","notes":"","class_name":"胃病"},{"class_id":"5263ff3eab2911e7af2c0894ef159f97","class_level":2,"class_url":"","class_icon":"","parent_class":"eba54a6f144b11e7bb370894ef02dbe0","create_time":1507357990000,"create_user":"88762c0faa1811e6bf240894ef159f97","notes":"","class_name":"眼病"},{"class_id":"59b493e5ab2911e7af2c0894ef159f97","class_level":2,"class_url":"","class_icon":"","parent_class":"eba54a6f144b11e7bb370894ef02dbe0","create_time":1507358003000,"create_user":"88762c0faa1811e6bf240894ef159f97","notes":"","class_name":"呼吸系统"},{"class_id":"609d9d53ab2911e7af2c0894ef159f97","class_level":2,"class_url":"","class_icon":"","parent_class":"eba54a6f144b11e7bb370894ef02dbe0","create_time":1507358014000,"create_user":"88762c0faa1811e6bf240894ef159f97","notes":"","class_name":"生殖系统"}]
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

    public static class OutBean implements Serializable{
        /**
         * class_id : 15776f2b31ff11e7b0820894ef159f97
         * class_level : 2
         * class_url :
         * class_icon :
         * parent_class : eba54a6f144b11e7bb370894ef02dbe0
         * create_time : 1494035790000
         * create_user : 88762c0faa1811e6bf240894ef159f97
         * notes : 疾病百科
         * class_name : 睡眠障碍
         */

        private String class_id;
        private int class_level;
        private String class_url;
        private String class_icon;
        private String parent_class;
        private long create_time;
        private String create_user;
        private String notes;
        private String class_name;

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public int getClass_level() {
            return class_level;
        }

        public void setClass_level(int class_level) {
            this.class_level = class_level;
        }

        public String getClass_url() {
            return class_url;
        }

        public void setClass_url(String class_url) {
            this.class_url = class_url;
        }

        public String getClass_icon() {
            return class_icon;
        }

        public void setClass_icon(String class_icon) {
            this.class_icon = class_icon;
        }

        public String getParent_class() {
            return parent_class;
        }

        public void setParent_class(String parent_class) {
            this.parent_class = parent_class;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getCreate_user() {
            return create_user;
        }

        public void setCreate_user(String create_user) {
            this.create_user = create_user;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }
    }
}
