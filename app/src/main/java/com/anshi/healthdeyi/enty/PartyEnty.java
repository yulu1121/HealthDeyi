package com.anshi.healthdeyi.enty;

import java.io.Serializable;

/**
 *
 * Created by yulu on 2018/4/14.
 */

public class PartyEnty implements Serializable {
    private String title;
    private int imageId;
    private String time;
    private String category;
    private String location;

    public PartyEnty(String title, int imageId, String time, String category,String location) {
        this.title = title;
        this.imageId = imageId;
        this.time = time;
        this.category = category;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
