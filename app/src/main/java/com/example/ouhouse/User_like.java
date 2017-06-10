package com.example.ouhouse;

/**
 * Created by Magot on 2017/6/6.
 */

public class User_like {
    private String User_like_name;
    private String User_like_number;
    private String User_like_val;
    private String User_like_img;

    public User_like(String user_like_name, String user_like_number, String user_like_img, String user_like_val) {
        this.User_like_name = user_like_name;
        this.User_like_number = user_like_number;
        this.User_like_img = user_like_img;
        this.User_like_val = user_like_val;
    }

    public String getUser_like_name() {
        return User_like_name;
    }

    public String getUser_like_val() {
        return User_like_val;
    }

    public String getUser_like_number() {
        return User_like_number;
    }

    public String getUser_like_img() {
        return User_like_img;
    }
}
