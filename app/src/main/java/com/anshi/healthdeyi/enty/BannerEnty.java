package com.anshi.healthdeyi.enty;

import java.util.List;

/**
 *
 * Created by yulu on 2018/4/2.
 */

public class BannerEnty {

    /**
     * retCode : 0
     * retMsg : 请求成功
     * out : {"channel_list":[{"content_param":"3d4afa05aaf711e6bf240894ef159f97","content_type":5,"content_url":"","id":"6fe8ec9baafa11e6bf240894ef159f97","image_url":"http://171.221.208.2:8090/res/channel/imageUrl/20171212/0239fe1582a64508b416a58873d22c26.jpeg","notes":"","release_time":1479190397000,"section_id":"d3b78e4b680611e6aed90894ef02dbe0","title":"仁青益寿天人相应"},{"content_param":"2b8fc8baaaf311e6bf240894ef159f97","content_type":5,"content_url":"","id":"8e80420faafa11e6bf240894ef159f97","image_url":"http://171.221.208.2:8090/res/channel/imageUrl/20171114/2969f21a577f44c3a07b38cdff51ac94.jpeg","notes":"","release_time":1479190448000,"section_id":"d3b78e4b680611e6aed90894ef02dbe0","title":"仁青益寿中医养生博物馆"},{"content_param":"2008e61eaa3b11e6bf240894ef159f97","content_type":7,"content_url":"","id":"baff0fc2ab0311e6bf240894ef159f97","image_url":"http://171.221.208.2:8090/res/channel/imageUrl/20171212/7d41cd95d9ff48bc9f5a39ed2947a0c7.jpeg","notes":"","release_time":1479194389000,"section_id":"d3b78e4b680611e6aed90894ef02dbe0","title":"仁青益寿九仙乌木"}]}
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
        private List<ChannelListBean> channel_list;

        public List<ChannelListBean> getChannel_list() {
            return channel_list;
        }

        public void setChannel_list(List<ChannelListBean> channel_list) {
            this.channel_list = channel_list;
        }

        public static class ChannelListBean {
            /**
             * content_param : 3d4afa05aaf711e6bf240894ef159f97
             * content_type : 5
             * content_url :
             * id : 6fe8ec9baafa11e6bf240894ef159f97
             * image_url : http://171.221.208.2:8090/res/channel/imageUrl/20171212/0239fe1582a64508b416a58873d22c26.jpeg
             * notes :
             * release_time : 1479190397000
             * section_id : d3b78e4b680611e6aed90894ef02dbe0
             * title : 仁青益寿天人相应
             */

            private String content_param;
            private int content_type;
            private String content_url;
            private String id;
            private String image_url;
            private String notes;
            private long release_time;
            private String section_id;
            private String title;

            public String getContent_param() {
                return content_param;
            }

            public void setContent_param(String content_param) {
                this.content_param = content_param;
            }

            public int getContent_type() {
                return content_type;
            }

            public void setContent_type(int content_type) {
                this.content_type = content_type;
            }

            public String getContent_url() {
                return content_url;
            }

            public void setContent_url(String content_url) {
                this.content_url = content_url;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public long getRelease_time() {
                return release_time;
            }

            public void setRelease_time(long release_time) {
                this.release_time = release_time;
            }

            public String getSection_id() {
                return section_id;
            }

            public void setSection_id(String section_id) {
                this.section_id = section_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
