package com.anshi.healthdeyi.enty;

import java.util.List;

/**
 *
 * Created by yulu on 2018/4/13.
 */

public class MethodVideoEnty {

    /**
     * retCode : 0
     * retMsg : 请求成功
     * out : [{"is_comment":0,"keywords":"","digest":"","contentType":1,"class_name":"功法视频","recommend":"5","url":"http://171.221.208.2:8090/api/content/getContent?content_id=8a87de68aa37425da5ad092d6a34e2ad","class_id":"88787c01410f11e7b0820894ef159f97","title":"仁青益寿-气血平衡术","content_id":"8a87de68aa37425da5ad092d6a34e2ad","source":"","image_url":"http://171.221.208.2:8090/res/content/icon/20171215/655b545450274f9c88107ee6f487cb56.jpeg","is_ad":0,"is_top":0,"special_id":"","is_headline":0,"create_user":"88762c0faa1811e6bf240894ef159f97","pv":0,"praise":0,"is_special":0}]
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
         * contentType : 1
         * class_name : 功法视频
         * recommend : 5
         * url : http://171.221.208.2:8090/api/content/getContent?content_id=8a87de68aa37425da5ad092d6a34e2ad
         * class_id : 88787c01410f11e7b0820894ef159f97
         * title : 仁青益寿-气血平衡术
         * content_id : 8a87de68aa37425da5ad092d6a34e2ad
         * source :
         * image_url : http://171.221.208.2:8090/res/content/icon/20171215/655b545450274f9c88107ee6f487cb56.jpeg
         * is_ad : 0
         * is_top : 0
         * special_id :
         * is_headline : 0
         * create_user : 88762c0faa1811e6bf240894ef159f97
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
