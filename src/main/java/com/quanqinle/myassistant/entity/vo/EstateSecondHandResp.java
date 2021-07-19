package com.quanqinle.myassistant.entity.vo;

import com.quanqinle.myassistant.entity.po.EstateSecondHandListing;

import java.util.List;

/**
 * 二手挂牌信息po
 *
 * @author quanqinle
 */
public class EstateSecondHandResp {
	/**
	 * Constructor for jpa
	 */
	protected EstateSecondHandResp() {}

	private boolean isover;
	private List<EstateSecondHandListing> list;

	public boolean isIsover() {
		return isover;
	}

	public void setIsover(boolean isover) {
		this.isover = isover;
	}

	public List<EstateSecondHandListing> getList() {
		return list;
	}

	public void setList(List<EstateSecondHandListing> list) {
		this.list = list;
	}
}
