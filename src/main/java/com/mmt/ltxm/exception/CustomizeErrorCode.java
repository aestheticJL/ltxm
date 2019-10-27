package com.mmt.ltxm.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001, "你要找的问题不见了"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中问题"),
    NOT_LOGIN(2003, "未登录"),
    SYS_ERROR(2004, "未知错误"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在惹"),
    COMMENT_IS_EMPTY(2007, "回复不能为空"),
    ;

    private String message;
    private Integer code;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
