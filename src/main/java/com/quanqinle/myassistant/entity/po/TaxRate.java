package com.quanqinle.myassistant.entity.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author quanqinle
 *
 * note:
 * 默认的自动对应规则
 * Class(TaxRate) --> DB(tax_rate)
 * field(rangeHighest) --> DB(range_highest)
 */
@Entity
@Data
public class TaxRate {

	protected TaxRate() { //jpa必须的
		// no-args constructor required by JPA spec
		// this one is protected since it shouldn't be used directly
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "ID")
	private int id;
	/**
	 * 税率
	 */
	@ApiModelProperty(value = "税率")
	private double rate;
	/**
	 * 左区间
	 */
	@ApiModelProperty(value = "左区间")
	private double rangeLowest;
	/**
	 * 右区间
	 */
	@ApiModelProperty(value = "右区间")
	private double rangeHighest;
	/**
	 * 速算扣除数
	 */
	@ApiModelProperty(value = "速算扣除数")
	private double quickDeduction;
	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private int status;
	/**
	 * 生效日期
	 */
	@ApiModelProperty(value = "生效日期")
	private String effectiveDate;


	public TaxRate(double rangeLowest, double rangeHighest, double rate, double quickDeduction) {
		this.rate = rate;
		this.rangeLowest = rangeLowest;
		this.rangeHighest = rangeHighest;
		this.quickDeduction = quickDeduction;
	}

	public TaxRate(int id, double rangeLowest, double rangeHighest, double rate, double quickDeduction) {
		this(rangeLowest, rangeHighest, rate, quickDeduction);
		this.id = id;
	}

	/**
	 * 金额是否落入当前税率区间
	 *
	 * @param salary -
	 * @return -
	 */
	public boolean isInRange(double salary) {
		if (Double.compare(this.rangeLowest, salary) < 0 && Double.compare(salary, this.rangeHighest) <= 0) {
			return true;
		}
		return false;
	}

}
