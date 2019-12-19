package com.ruihe.community.enums;

/**
 * Created by seceretrui 2019/12/19/13:53
 */
public enum  NotificationStatusEnum {
    UNREAD(0), READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
