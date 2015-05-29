package com.julia.bookshelf.ui.adapters;

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
