package com.ruidi.niuniu.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Account implements Serializable{
    @SerializedName("uid")
    private long uid;
    @SerializedName("nick")
    private String nickname;
    @SerializedName("headimg")
    private String headimg;
    @SerializedName("sex")
    private int sex;
    @SerializedName("regTime")
    private long registerTime;
    private long lastLoginTime;
    private int agentid;
    @SerializedName("card")
    private long card;
    @SerializedName("ip")
    private String ip;
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }



    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getAgentid() {
        return agentid;
    }

    public void setAgentid(int agentid) {
        this.agentid = agentid;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
}
