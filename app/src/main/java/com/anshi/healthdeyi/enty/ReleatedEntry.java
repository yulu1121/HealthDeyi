package com.anshi.healthdeyi.enty;

import java.util.List;

/**
 *
 * Created by yulu on 2018/4/26.
 */

public class ReleatedEntry {

    /**
     * msg : 成功
     * code : true
     * data : [{"id":"8ae4743eac594e8a9ba3e90726e9dc2e","isNewRecord":false,"createDate":"2018-04-26 10:06:03","updateDate":"2018-04-26 10:06:03","pid":"9","name":"测试图片","isEnable":"0","spare1":"/userfiles/task/tbtype/img/1524708363798sort_asc_disabled.png","remark":"测试信息","pname":"相关推荐"}]
     */

    private String msg;
    private boolean code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 8ae4743eac594e8a9ba3e90726e9dc2e
         * isNewRecord : false
         * createDate : 2018-04-26 10:06:03
         * updateDate : 2018-04-26 10:06:03
         * pid : 9
         * name : 测试图片
         * isEnable : 0
         * spare1 : /userfiles/task/tbtype/img/1524708363798sort_asc_disabled.png
         * remark : 测试信息
         * pname : 相关推荐
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String pid;
        private String name;
        private String isEnable;
        private String spare1;
        private String remark;
        private String pname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIsEnable() {
            return isEnable;
        }

        public void setIsEnable(String isEnable) {
            this.isEnable = isEnable;
        }

        public String getSpare1() {
            return spare1;
        }

        public void setSpare1(String spare1) {
            this.spare1 = spare1;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }
    }
}
