package com.boot.framework.bean;

/**
 * @author: lindj
 * @date: 2018/5/31 20:46
 * @description:
 */
public class BaseModel  extends Bean{
    public Integer id;
    public Integer status;
    public Long modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }
}
