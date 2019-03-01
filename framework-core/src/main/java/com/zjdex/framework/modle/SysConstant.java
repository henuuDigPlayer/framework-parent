package com.zjdex.framework.modle;

import java.util.Date;

/**
 * @author: null
 * @date: 2019-02-26 18:13:13
 * @description:  
 */
public class SysConstant {
    /**
     * 
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 名称
     */
    private String name;

    /**
     * 
     */
    private Date modifyTime;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     *
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     *
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 标题
     *
     * @return title 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 内容
     *
     * @return content 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 名称
     *
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     *
     * @return modify_time 
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 
     *
     * @param modifyTime 
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 
     *
     * @return create_time 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     *
     * @param createTime 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}