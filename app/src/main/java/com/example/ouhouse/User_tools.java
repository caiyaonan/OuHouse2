package com.example.ouhouse;

import android.util.Log;

/**
 * Created by Magot on 2017/6/6.
 */

public class User_tools {
    public static String username;
    public static String name;
    public static String sex;
    public static String number;
    public static String session_id="null";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public boolean Check_user(){
        String us=getUsername();
        String sid=getSession_id();
        Log.d("username",us);
        Log.d("session_id",sid);
        if(username.isEmpty()&&session_id.isEmpty()){
            return false;
        }else{
            return true;
        }
        }

}
