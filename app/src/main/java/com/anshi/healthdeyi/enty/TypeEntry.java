package com.anshi.healthdeyi.enty;

import java.util.List;

/**
 *
 * Created by yulu on 2018/4/26.
 */

public class TypeEntry {
    /**
     * msg : 成功
     * code : true
     * data : [{"id":"b2fc615a678f41dfa4a50625f6d39370","isNewRecord":false,"createDate":"2018-04-20 11:13:32","updateDate":"2018-04-20 11:13:32","pid":"1","name":"光数之声","isEnable":"1","remark":"","pname":"福寿之声"},{"id":"1e728a47d752457ea076a734e6617fd1","isNewRecord":false,"createDate":"2018-04-20 11:13:02","updateDate":"2018-04-20 11:13:02","pid":"1","name":"健康百秒","isEnable":"1","remark":"","pname":"福寿之声"},{"id":"9077b4f598c54da38f86af086f30ddbf","isNewRecord":false,"createDate":"2018-04-20 11:04:14","updateDate":"2018-04-20 11:04:14","pid":"1","name":"长寿之道","isEnable":"1","remark":"","pname":"福寿之声"}]
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
         * id : b2fc615a678f41dfa4a50625f6d39370
         * isNewRecord : false
         * createDate : 2018-04-20 11:13:32
         * updateDate : 2018-04-20 11:13:32
         * pid : 1
         * name : 光数之声
         * isEnable : 1
         * remark :
         * pname : 福寿之声
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String pid;
        private String name;
        private String isEnable;
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
