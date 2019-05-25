package com.abhay.sportsdemoapp;

import android.graphics.Bitmap;

public class ListItem {
    private String title;
    private String desc;
    private String imageUrl;
    private String url;


    public ListItem(String title, String desc,String imageUrl,String url) {
        this.title = title;
        this.desc = desc;
        this.imageUrl=imageUrl;
        this.url=url;

    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }
}
