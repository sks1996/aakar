package com.example.a1405264.aakar;

/**
 * Created by 1405264 on 12/4/2016.
 */

public class Detail {

    private String title,desc,image;

    public Detail(){

    }

    public Detail(String title, String image, String desc) {
        this.title = title;
        this.image = image;
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
