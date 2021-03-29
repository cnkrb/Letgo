package com.cenkkaraboa.myapplication.models;

import android.graphics.drawable.Drawable;


public class MenuModel {

    public String menuName, url;
    public boolean hasChildren, isGroup;
    public Drawable image;

    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, String url, Drawable image) {

        this.menuName = menuName;
        this.url = url;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
        this.image=image;
    }
}
