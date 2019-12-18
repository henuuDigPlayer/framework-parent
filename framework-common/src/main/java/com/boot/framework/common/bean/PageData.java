package com.boot.framework.common.bean;

import java.util.List;

/**
 * @author: lindj
 * @date: 2018/6/15 17:09
 * @description: 分页返回实体
 */
public class PageData<T> {

    public long count;
    public List<T> list;

    public PageData()
    {
        this.count = 0L;
    }

    public long getCount()
    {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
