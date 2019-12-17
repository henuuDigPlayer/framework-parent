package com.boot.framework.enums;

/**
 * @author: lirt
 * @date: 2019/3/21 0021 16:23
 * @description:
 */
public enum StatusEnum {
    /**
     * 正常
     */
    OK(1, "正常"),

    /**
     * 删除
     */
    DELETED(-1, "删除");

    private Integer code;
    private String title;

    StatusEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static StatusEnum of(Integer code){
        for(StatusEnum statusEnum: StatusEnum.values()){
            if(statusEnum.getCode().equals(code)){
                return statusEnum;
            }
        }
        return null;
    }
}
