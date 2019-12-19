package com.ruihe.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FIND(2001, "问题不存在"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NOT_LOGIN(2003, "未登录，请先登录"),
    SYS_ERROR(2004, "服务器冒烟了"),
    TYPE_PARAM_WRONG(2005, "评论类型错误" ),
    COMMENT_NOT_FOUND(2006, "评论不存在" ),
    CONTENT_IS_EMPTY(2007, "输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008, "读别人的信息？"),
    NOTIFICATION_NOT_FOUND(2009, "消息不见了"),
    ;


    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

}
