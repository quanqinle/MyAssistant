package com.quanqinle.myworld.entity.po;

import lombok.Data;

import javax.persistence.*;

/**
 * @author quanqinle
 */
@Entity
@Table(name = "estate_secondhand_house")
@Data
public class EstateSecondHandHouse {
	/**
	 * Constructor for jpa
	 */
	public EstateSecondHandHouse() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * 房源核验统一编码
	 */
	private String houseUniqueId;
	/**
	 * 建筑面积
	 */
	private String coveredArea;
	/**
	 * 市辖区（县级行政区）
	 */
	private String district;
	/**
	 * 房产证书
	 */
	private String housePropertyOwnershipCertificate;
	/**
	 * 小区id
	 */
	private String communityId;
	/**
	 * 小区名称
	 */
	private String communityName;
	/**
	 * 行政区划编码
	 */
	private String cityCode;
	/**
	 * 行政区划名称（地区级行政区）
	 */
	private String cityName;
}
