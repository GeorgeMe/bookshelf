package com.julia.bookshelf.ui.adapters;


/**
 * Created by Julia on 04.02.2015.
 */
public class DrawerMenuItem {
    private String action;
    private int img;

    public DrawerMenuItem(String action, int img) {
        this.action = action;
        this.img = img;
    }

    public String getAction() {
        return action;
    }

    public int getImg() {
        return img;
    }
}
