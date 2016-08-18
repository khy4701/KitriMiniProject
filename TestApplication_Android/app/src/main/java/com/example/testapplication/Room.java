package com.example.testapplication;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 한국정보기술 on 2016-08-17.
 */
public class Room implements Serializable {
    boolean isChecked;
    int roomNum;
    String roomName;
    String roomOwner;
    List<User> participantList;

    public Room() {
        this.isChecked = false;
    }

    public Room(int roomNum, String roomName, String roomOwner) {
        this.isChecked = false;
        this.roomNum = roomNum;
        this.roomName = roomName;
        this.roomOwner = roomOwner;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomOwner() {
        return roomOwner;
    }

    public void setRoomOwner(String roomOwner) {
        this.roomOwner = roomOwner;
    }

    public List<User> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<User> participantList) {
        this.participantList = participantList;
    }
}
