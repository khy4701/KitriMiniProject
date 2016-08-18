package com.example.testapplication;

import android.util.Log;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 한국정보기술 on 2016-08-11.
 */
public class User implements Serializable{
    private String id;
    private String pwd;
    private String name;
    private List<User> freinds;  //친구 리스트

    // GPS 정보
    private double gps_latitude;
    private double gps_longitude;

    // 연결 여부
    private boolean isConnected = false;

    public User() {    }

    public User(String id, String pwd) {
        this.id = id;
        this.pwd = pwd;
    }

    public User(String name, String pwd, String id) {
        this.name = name;
        this.pwd = pwd;
        this.id = id;
    }

    synchronized public List<User> getFreinds() {
        return freinds;
    }

    synchronized public void setFreinds(List<User> freinds) {

            for(User user : freinds)
            {
                Log.e("User", "이전 List user Id : " +user.getId());
            }


            this.freinds = freinds;

            for(User user : this.freinds)
            {
                Log.e("User", "이후 List user Id : " +user.getId());
            }


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGps_latitude() {
        return gps_latitude;
    }

    public void setGps_latitude(double gps_latitude) {
        this.gps_latitude = gps_latitude;
    }

    public double getGps_longitude() {
        return gps_longitude;
    }

    public void setGps_longitude(double gps_longitude) {
        this.gps_longitude = gps_longitude;
    }
    public boolean isConnected() {
        return isConnected;
    }
    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
