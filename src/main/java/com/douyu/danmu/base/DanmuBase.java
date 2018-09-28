package com.douyu.danmu.base;

import java.util.Date;

/**
 * @program danmu
 * @description: 弹幕实体类
 * @author: luhx
 * @create: 2018/09/27 14:15
 */
public class DanmuBase {
    private String msgID;//弹幕唯一ID 主键
    private String userId; //发送弹幕的ID
    private String userName; //发送弹幕者名称
    private String userLevel; //发送弹幕的用户等级
    private String fansName; //发送弹幕的粉丝牌名称
    private String fansLevel; //发送弹幕的粉丝牌等级
    private String msg; //弹幕信息
    private String msgColor; //弹幕的颜色
    private String msgNoble; //是否为贵族弹幕
    private Date createTime; //发送时间


    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getFansName() {
        return fansName;
    }

    public void setFansName(String fansName) {
        this.fansName = fansName;
    }

    public String getFansLevel() {
        return fansLevel;
    }

    public void setFansLevel(String fansLevel) {
        this.fansLevel = fansLevel;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgColor() {
        return msgColor;
    }

    public void setMsgColor(String msgColor) {
        this.msgColor = msgColor;
    }

    public String getMsgNoble() {
        return msgNoble;
    }

    public void setMsgNoble(String msgNoble) {
        this.msgNoble = msgNoble;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DanmuBase{" +
                "msgID='" + msgID + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userLevel='" + userLevel + '\'' +
                ", fansName='" + fansName + '\'' +
                ", fansLevel='" + fansLevel + '\'' +
                ", msg='" + msg + '\'' +
                ", msgColor='" + msgColor + '\'' +
                ", msgNoble='" + msgNoble + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
