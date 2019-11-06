package com.mmt.ltxm.enums;

public enum  NotificationEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论"),
    ;
    private String name;
    private int type;

    public static String nameOfType(Integer type) {
        for (NotificationEnum notificationEnum : NotificationEnum.values()) {
            if (notificationEnum.getType() == type){
                return notificationEnum.getName();
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    NotificationEnum(int type, String name) {
        this.name = name;
        this.type = type;
    }
}
