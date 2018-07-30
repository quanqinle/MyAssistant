package com.quanqinle.myworld.entity.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * note:
 * 默认的自动对应
 * Class(TaxRate) --> DB(tax_rate)
 * field(rangeHighest) --> DB(range_highest)
 */
@Entity
public class TaxRate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private double rate;

	private double rangeLowest;

	private double rangeHighest;

	private double quickDeduction;

	public TaxRate() { //jpa必须的
		// no-args constructor required by JPA spec
		// this one is protected since it shouldn't be used directly
	}

	public TaxRate(double rangeLowest, double rangeHighest, double rate, double quickDeduction) {
		this.rate = rate;
		this.rangeLowest = rangeLowest;
		this.rangeHighest = rangeHighest;
		this.quickDeduction = quickDeduction;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getRangeLowest() {
		return rangeLowest;
	}

	public void setRangeLowest(double rangeLowest) {
		this.rangeLowest = rangeLowest;
	}

	public double getRangeHighest() {
		return rangeHighest;
	}

	public void setRangeHighest(double rangeHighest) {
		this.rangeHighest = rangeHighest;
	}

	public double getQuickDeduction() {
		return quickDeduction;
	}

	public void setQuickDeduction(double quickDeduction) {
		this.quickDeduction = quickDeduction;
	}

	/**
	 * 金额是否落入当前税率区间
	 *
	 * @param salary
	 * @return
	 */
	public boolean isInRange(double salary) {
		if (Double.compare(this.rangeLowest, salary) < 0 && Double.compare(salary, this.rangeHighest) <= 0) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "TaxRate{" +
				"id=" + id +
				", rate=" + rate +
				", rangeLowest=" + rangeLowest +
				", rangeHighest=" + rangeHighest +
				", quickDeduction=" + quickDeduction +
				'}';
	}
}
