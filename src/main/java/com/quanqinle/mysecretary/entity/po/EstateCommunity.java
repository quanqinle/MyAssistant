package com.quanqinle.mysecretary.entity.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 小区信息
 * @author quanqinle
 */
@Entity
@Data
public class EstateCommunity {
	/**
	 * Constructor for jpa
	 */
	protected EstateCommunity() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "ID")
	private int id;

	/**
	 * 小区id
	 */
	@ApiModelProperty(value = "小区id")
	private String xqid;

	/**
	 * 小区名称
	 */
	@ApiModelProperty(value = "小区名称")
	private String xqmc;

}
