package com.quanqinle.myworld.entity.po;

import java.util.List;

public class EstateSecondHandResp {
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
