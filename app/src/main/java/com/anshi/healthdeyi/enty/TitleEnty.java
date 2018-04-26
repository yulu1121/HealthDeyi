package com.anshi.healthdeyi.enty;

/**
 *
 * Created by yulu on 2018/4/24.
 */

public class TitleEnty {
    private String name;
    private String content;
    private int imageId;

    public TitleEnty(String name, String content, int imageId) {
        this.name = name;
        this.content = content;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
