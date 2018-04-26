package com.anshi.healthdeyi.enty;

import java.util.List;

/**
 *
 * Created by yulu on 2018/4/13.
 */

public class HealthTestEnty {

    /**
     * retCode : 0
     * retMsg : 请求成功
     * out : [{"is_comment":0,"keywords":"","digest":"","contentType":2,"class_name":"健康自测","recommend":"7","url":"http://171.221.208.2:8090/api/content/getContent?content_id=4ab95224b918434c9394657df0cde015","class_id":"c38a9a6f621f11e7b0820894ef159f97","title":"你的卵巢健康吗？测试一下","content_id":"4ab95224b918434c9394657df0cde015","source":"","image_url":"http://171.221.208.2:8090/res/content/icon/20180411/a0d2941d7a394ea0b329e063782f832a.jpeg","is_ad":0,"is_top":0,"special_id":"","is_headline":0,"create_user":"8dc01806aae311e6bf240894ef159f97","pv":0,"praise":0,"is_special":0},{"is_comment":0,"keywords":"","digest":"","contentType":2,"class_name":"健康自测","recommend":"2","url":"http://171.221.208.2:8090/api/content/getContent?content_id=6cceb9c9c0ab41d095db09c0873724e4","class_id":"c38a9a6f621f11e7b0820894ef159f97","title":"后背疼痛为哪般","content_id":"6cceb9c9c0ab41d095db09c0873724e4","source":"","image_url":"http://171.221.208.2:8090/res/content/icon/20180411/a5281974ff9a45ae900dceef0f2ef7b9.jpeg","is_ad":0,"is_top":0,"special_id":"","is_headline":0,"create_user":"8dc01806aae311e6bf240894ef159f97","pv":0,"praise":0,"is_special":0},{"is_comment":0,"keywords":"","digest":"","contentType":2,"class_name":"健康自测","recommend":"3","url":"http://171.221.208.2:8090/api/content/getContent?content_id=045f5dc3035f48db9278dd0de2d0825a","class_id":"c38a9a6f621f11e7b0820894ef159f97","title":"眼睑浮肿是心衰？肾病？还是喝水喝多了？","content_id":"045f5dc3035f48db9278dd0de2d0825a","source":"","image_url":"http://171.221.208.2:8090/res/content/icon/20180319/d5786dc172ea4ab49242618cbefd9794.jpeg","is_ad":0,"is_top":0,"special_id":"","is_headline":0,"create_user":"8dc01806aae311e6bf240894ef159f97","pv":0,"praise":0,"is_special":0},{"is_comment":0,"keywords":"","digest":"","contentType":2,"class_name":"健康自测","recommend":"9","url":"http://171.221.208.2:8090/api/content/getContent?content_id=dcd896fde25f47df9126c22b2cc3c821","class_id":"c38a9a6f621f11e7b0820894ef159f97","title":"你知道脱发，可你知道脱眉有多可怕吗？","content_id":"dcd896fde25f47df9126c22b2cc3c821","source":"","image_url":"http://171.221.208.2:8090/res/content/icon/20180109/77be287a4e884dc68508510d93fb27d4.jpeg","is_ad":0,"is_top":0,"special_id":"","is_headline":0,"create_user":"88762c0faa1811e6bf240894ef159f97","pv":0,"praise":0,"is_special":0},{"is_comment":0,"keywords":"","digest":"","contentType":2,"class_name":"健康自测","recommend":"3","url":"http://171.221.208.2:8090/api/content/getContent?content_id=4176b0cb45974b2f8b892cecfafd64c0","class_id":"c38a9a6f621f11e7b0820894ef159f97","title":"眉毛与健康","content_id":"4176b0cb45974b2f8b892cecfafd64c0","source":"","image_url":"http://171.221.208.2:8090/res/content/icon/20180102/ce224de18a92407ebfd9f41cb0d2d8b6.jpeg","is_ad":0,"is_top":0,"special_id":"","is_headline":0,"create_user":"88762c0faa1811e6bf240894ef159f97","pv":0,"praise":0,"is_special":0},{"is_comment":0,"keywords":"","digest":"","contentType":2,"class_name":"健康自测","recommend":"3","url":"http://171.221.208.2:8090/api/content/getContent?content_id=03d5ba60a6844d1db57763213dda813a","class_id":"c38a9a6f621f11e7b0820894ef159f97","title":"黑眼圈预示着肾虚？","content_id":"03d5ba60a6844d1db57763213dda813a","source":"","image_url":"http://171.221.208.2:8090/res/content/icon/20171225/fa6b1115541042d696f47f0d31a45397.jpeg","is_ad":0,"is_top":0,"special_id":"","is_headline":0,"create_user":"88762c0faa1811e6bf240894ef159f97","pv":0,"praise":0,"is_special":0},{"is_comment":0,"keywords":"","digest":"","contentType":0,"class_name":"健康自测","recommend":"6","url":"http://171.221.208.2:8090/api/content/getContent?content_id=5e581d566bda4d589fb92c1107881e76","class_id":"c38a9a6f621f11e7b0820894ef159f97","title":"健康自测-手诊自测 ","content_id":"5e581d566bda4d589fb92c1107881e76","source":"","image_url":"http://171.221.208.2:8090/res/content/icon/20170706/da3e63c2a888434e844181ea35df7f4e.jpeg","is_ad":0,"is_top":0,"special_id":"","is_headline":0,"create_user":"88762c0faa1811e6bf240894ef159f97","pv":0,"praise":0,"is_special":0}]
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
         * is_comment : 0
         * keywords :
         * digest :
         * contentType : 2
         * class_name : 健康自测
         * recommend : 7
         * url : http://171.221.208.2:8090/api/content/getContent?content_id=4ab95224b918434c9394657df0cde015
         * class_id : c38a9a6f621f11e7b0820894ef159f97
         * title : 你的卵巢健康吗？测试一下
         * content_id : 4ab95224b918434c9394657df0cde015
         * source :
         * image_url : http://171.221.208.2:8090/res/content/icon/20180411/a0d2941d7a394ea0b329e063782f832a.jpeg
         * is_ad : 0
         * is_top : 0
         * special_id :
         * is_headline : 0
         * create_user : 8dc01806aae311e6bf240894ef159f97
         * pv : 0
         * praise : 0
         * is_special : 0
         */

        private int is_comment;
        private String keywords;
        private String digest;
        private int contentType;
        private String class_name;
        private String recommend;
        private String url;
        private String class_id;
        private String title;
        private String content_id;
        private String source;
        private String image_url;
        private int is_ad;
        private int is_top;
        private String special_id;
        private int is_headline;
        private String create_user;
        private int pv;
        private int praise;
        private int is_special;

        public int getIs_comment() {
            return is_comment;
        }

        public void setIs_comment(int is_comment) {
            this.is_comment = is_comment;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public int getContentType() {
            return contentType;
        }

        public void setContentType(int contentType) {
            this.contentType = contentType;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getRecommend() {
            return recommend;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent_id() {
            return content_id;
        }

        public void setContent_id(String content_id) {
            this.content_id = content_id;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public int getIs_ad() {
            return is_ad;
        }

        public void setIs_ad(int is_ad) {
            this.is_ad = is_ad;
        }

        public int getIs_top() {
            return is_top;
        }

        public void setIs_top(int is_top) {
            this.is_top = is_top;
        }

        public String getSpecial_id() {
            return special_id;
        }

        public void setSpecial_id(String special_id) {
            this.special_id = special_id;
        }

        public int getIs_headline() {
            return is_headline;
        }

        public void setIs_headline(int is_headline) {
            this.is_headline = is_headline;
        }

        public String getCreate_user() {
            return create_user;
        }

        public void setCreate_user(String create_user) {
            this.create_user = create_user;
        }

        public int getPv() {
            return pv;
        }

        public void setPv(int pv) {
            this.pv = pv;
        }

        public int getPraise() {
            return praise;
        }

        public void setPraise(int praise) {
            this.praise = praise;
        }

        public int getIs_special() {
            return is_special;
        }

        public void setIs_special(int is_special) {
            this.is_special = is_special;
        }
    }
}
