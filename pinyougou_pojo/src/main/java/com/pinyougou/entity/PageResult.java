package com.pinyougou.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author ljh
 * @version 1.0
 * @description 描述
 * @title 标题
 * @package entity
 * @company www.itheima.com
 */
public class PageResult implements Serializable{
    private long total;//总记录数
    private List rows;//当前页结果
    public PageResult(long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
