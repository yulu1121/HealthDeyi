package com.anshi.healthdeyi.enty;

/**
 *
 * Created by yulu on 2018/4/25.
 */

public class LoginEntry {

    /**
     * msg : 登录成功
     * code : true
     * data : {"id":"a14814bf35e9417b9a9d0f20e090fbfe","isNewRecord":false,"createDate":"2018-04-25 13:55:32","updateDate":"2018-04-25 13:55:32","account":"12345678968","password":"743114179b5575265ec92390c9d043557c7554e1cb0f114d5aebb1c2"}
     */

    private String msg;
    private boolean code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : a14814bf35e9417b9a9d0f20e090fbfe
         * isNewRecord : false
         * createDate : 2018-04-25 13:55:32
         * updateDate : 2018-04-25 13:55:32
         * account : 12345678968
         * password : 743114179b5575265ec92390c9d043557c7554e1cb0f114d5aebb1c2
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String account;
        private String password;

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

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
