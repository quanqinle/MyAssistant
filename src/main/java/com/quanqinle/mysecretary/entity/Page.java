package com.quanqinle.mysecretary.entity;

/**
 * @author quanql
 * @version 2021/3/15
 */
public class Page {
    /**
     * current page number
     */
    private Integer num;
    /**
     * data number on one page
     */
    private Integer limit;
    /**
     * total pages
     */
    private Integer total;

    public Page(Integer num, Integer limit, Integer total) {
        this.num = num;
        this.limit = limit;
        this.total = total;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
