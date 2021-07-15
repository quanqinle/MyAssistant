package com.quanqinle.mysecretary.entity;

/**
 * @author quanql
 * @version 2021/3/15
 */
public class PageInfo {
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
    private Long total;

    public PageInfo(Integer num, Integer limit, Long total) {
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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
