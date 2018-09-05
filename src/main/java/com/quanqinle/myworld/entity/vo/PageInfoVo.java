package com.quanqinle.myworld.entity.vo;

/**
 * @author quanqinle
 */
public class PageInfoVo {

	/**
	 * 当前显示多少页
	 */
	private Integer limit;

	/**
	 * 总共多少页
	 */
	private Integer total;

	/**
	 * 当前第几页
	 */
	private Integer pageNum;

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

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

}

