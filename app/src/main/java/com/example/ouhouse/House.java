package com.example.ouhouse;

import android.content.Context;

/**
 * Created by Magot on 2017/5/16.
 */

public class House {
    private String name;
    private int imageId;

    public int getImageId() {
        return imageId;
    }

    public House(String name, int imageId) {
        this.imageId = imageId;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
