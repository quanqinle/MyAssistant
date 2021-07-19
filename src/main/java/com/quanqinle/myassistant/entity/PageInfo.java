package com.quanqinle.myassistant.entity;

import org.springframework.data.domain.Page;

import java.io.Serializable;

/**
 * @author quanql
 * @version 2021/3/15
 */
public class PageInfo  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总元素数
     */
    private long totalElements;
    /**
     * 总页数
     */
    private int totalPages;
    /**
     * 每页元素数。类似的变量名 limit
     */
    private int pageSize;
    /**
     * 当前第几页，从0开始
     */
    private int pageNumber;
    /**
     * 当前页第1条的index，based on 0
     */
    private long offset;
    /**
     * 当前页元素数
     */
    private int numberOfElements;

    /*
     * others, to be add: first, last, empty
     */

    public PageInfo() {
    }

    /**
     * Constructor by {org.springframework.data.domain.Page}
     * @param page
     */
    public PageInfo(Page page) {
        this(page.getTotalElements(),
                page.getTotalPages(),
                page.getPageable().getPageSize(),
                page.getPageable().getPageNumber(),
                page.getPageable().getOffset(),
                page.getNumberOfElements());
    }

    /**
     *
     * @param totalElements 总元素数
     * @param totalPages 总页数
     * @param pageSize 每页元素数
     * @param pageNumber 当前第几页，从0开始
     * @param offset 前页第1条的index，based on 0
     * @param numberOfElements 当前页元素数
     */
    public PageInfo(long totalElements, int totalPages, int pageSize, int pageNumber, long offset, int numberOfElements) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.offset = offset;
        this.numberOfElements = numberOfElements;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
}
