package com.anshi.healthdeyi.enty;

/**
 *
 * Created by yulu on 2018/4/3.
 */

public class VideoEntry {

    /**
     * retCode : 0
     * retMsg : 请求成功
     * out : {"content_id":"a0ba788dde01402fb130c6ab57e92a9a","accessory_url":"http://171.221.208.2:8090/res/content/contentfile/20171114/1b3f3650ed1a47ee933a56e29bff43c6.mp4;","create_time":1510646336000,"accessory_name":"","notes":"","accessory_id":"9cbe18bac91111e7a7f80894ef159f97"}
     */

    private int retCode;
    private String retMsg;
    private OutBean out;

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

    public OutBean getOut() {
        return out;
    }

    public void setOut(OutBean out) {
        this.out = out;
    }

    public static class OutBean {
        /**
         * content_id : a0ba788dde01402fb130c6ab57e92a9a
         * accessory_url : http://171.221.208.2:8090/res/content/contentfile/20171114/1b3f3650ed1a47ee933a56e29bff43c6.mp4;
         * create_time : 1510646336000
         * accessory_name :
         * notes :
         * accessory_id : 9cbe18bac91111e7a7f80894ef159f97
         */

        private String content_id;
        private String accessory_url;
        private long create_time;
        private String accessory_name;
        private String notes;
        private String accessory_id;

        public String getContent_id() {
            return content_id;
        }

        public void setContent_id(String content_id) {
            this.content_id = content_id;
        }

        public String getAccessory_url() {
            return accessory_url;
        }

        public void setAccessory_url(String accessory_url) {
            this.accessory_url = accessory_url;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getAccessory_name() {
            return accessory_name;
        }

        public void setAccessory_name(String accessory_name) {
            this.accessory_name = accessory_name;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getAccessory_id() {
            return accessory_id;
        }

        public void setAccessory_id(String accessory_id) {
            this.accessory_id = accessory_id;
        }
    }
}
