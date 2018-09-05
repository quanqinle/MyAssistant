package com.quanqinle.myworld.entity.vo;

import com.quanqinle.myworld.entity.po.TaxRate;
import com.quanqinle.myworld.util.DoubleUtils;

/**
 * 税收筹划结果
 *
 * @author quanql
 *
 */
public class TaxPlan implements Cloneable {
	/**
	 * 总月薪。税前，但已扣五险一金
	 */
	private double preTaxSalary;
	/**
	 * 年终奖。税前
	 */
	private double preTaxBonus;
	/**
	 * 税前月薪中应纳税部分。月薪应纳税额=总月薪-个税起征点
	 */
	private double taxableSalary;
	/**
	 * 税前年终奖中应纳税部分。<BR>
	 * 当发年终奖当月的月薪低于个税起征点时，年终奖中有部分免税
	 */
	private double taxableBonus;
	/**
	 * 月薪税率
	 */
	private TaxRate salaryTaxRate;
	/**
	 * 年终奖税率
	 */
	private TaxRate bonusTaxRate;
	/**
	 * 月薪纳税额
	 */
	private double salaryTaxes;
	/**
	 * 年终奖纳税额 Year-End Bonus
	 */
	private double bonusTaxes;
	/**
	 * 年总纳税额
	 */
	private double totalTaxes;

	/**
	 *
	 */
	public TaxPlan() {
		// no-args constructor required by JPA spec
		// this one is protected since it shouldn't be used directly
	}

	/**
	 * @param preTaxSalary
	 * @param preTaxBonus
	 */
	public TaxPlan(double preTaxSalary, double preTaxBonus) {
		this.preTaxSalary = preTaxSalary;
		this.preTaxBonus = preTaxBonus;
	}

	/**
	 * @return the preTaxSalary
	 */
	public double getPreTaxSalary() {
		return preTaxSalary;
	}

	/**
	 * @param preTaxSalary
	 *            the preTaxSalary to set
	 */
	public void setPreTaxSalary(double preTaxSalary) {
		this.preTaxSalary = preTaxSalary;
	}

	/**
	 * @return the preTaxBonus
	 */
	public double getPreTaxBonus() {
		return preTaxBonus;
	}

	/**
	 * @param preTaxBonus
	 *            the preTaxBonus to set
	 */
	public void setPreTaxBonus(double preTaxBonus) {
		this.preTaxBonus = preTaxBonus;
	}

	/**
	 * @return the taxableSalary
	 */
	public double getTaxableSalary() {
		return taxableSalary;
	}

	/**
	 * @param taxableSalary
	 *            the taxableSalary to set
	 */
	public void setTaxableSalary(double taxableSalary) {
		this.taxableSalary = taxableSalary;
	}

	/**
	 * @return the taxableBonus
	 */
	public double getTaxableBonus() {
		return taxableBonus;
	}

	/**
	 * @param taxableBonus
	 *            the taxableBonus to set
	 */
	public void setTaxableBonus(double taxableBonus) {
		this.taxableBonus = taxableBonus;
	}

	/**
	 * @return the salaryTaxRate
	 */
	public TaxRate getSalaryTaxRate() {
		return salaryTaxRate;
	}

	/**
	 * @param salaryTaxRate
	 *            the salaryTaxRate to set
	 */
	public void setSalaryTaxRate(TaxRate salaryTaxRate) {
		this.salaryTaxRate = salaryTaxRate;
	}

	/**
	 * @return the bonusTaxRate
	 */
	public TaxRate getBonusTaxRate() {
		return bonusTaxRate;
	}

	/**
	 * @param bonusTaxRate
	 *            the bonusTaxRate to set
	 */
	public void setBonusTaxRate(TaxRate bonusTaxRate) {
		this.bonusTaxRate = bonusTaxRate;
	}

	/**
	 * @return the salaryTaxes
	 */
	public double getSalaryTaxes() {
		return salaryTaxes;
	}

	/**
	 * @param salaryTaxes
	 *            the salaryTaxes to set
	 */
	public void setSalaryTaxes(double salaryTaxes) {
		this.salaryTaxes = salaryTaxes;
	}

	/**
	 * @return the bonusTaxes
	 */
	public double getBonusTaxes() {
		return bonusTaxes;
	}

	/**
	 * @param bonusTaxes
	 *            the bonusTaxes to set
	 */
	public void setBonusTaxes(double bonusTaxes) {
		this.bonusTaxes = bonusTaxes;
	}

	/**
	 * @return the totalTaxes
	 */
	public double getTotalTaxes() {
		return totalTaxes;
	}

	/**
	 * @param totalTaxes
	 *            the totalTaxes to set
	 */
	public void setTotalTaxes(double totalTaxes) {
		this.totalTaxes = totalTaxes;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TaxPlan [preTaxSalary=" + preTaxSalary + ", preTaxBonus=" + preTaxBonus + ", taxableSalary="
				+ taxableSalary + ", taxableBonus=" + taxableBonus + ", salaryTaxRate=" + salaryTaxRate
				+ ", bonusTaxRate=" + bonusTaxRate + ", salaryTaxes=" + salaryTaxes + ", bonusTaxes=" + bonusTaxes
				+ ", totalTaxes=" + totalTaxes + "]";
	}

	/**
	 * 比较2个筹划方案{@code TaxPlan}<br>
	 * 1.总税低者优先 2.月薪高者优先
	 *
	 * @param p1
	 *            the first {@code TaxPlan} to compare
	 * @param p2
	 *            the second {@code TaxPlan} to compare
	 * @return 小于{@code 0}，{@code p1}优；
	 *         等于{@code 0}，{@code p1}与{@code p2}相等；
	 *         大于{@code 0}，{@code p2}优；
	 */
	public static int compare(TaxPlan p1, TaxPlan p2) {
		return compare(p1, p2, 0.0d);
	}

	/**
	 * 比较2个筹划方案{@code TaxPlan}<br>
	 * 1.总税低者优先 2.月薪高者优先
	 *
	 * @param p1
	 *            the first {@code TaxPlan} to compare
	 * @param p2
	 *            the second {@code TaxPlan} to compare
	 * @param tolerance
	 *            {@code p1}和{@code p2}的年总纳税额在此范围内，认为二者总税相等
	 * @return 小于{@code 0}，{@code p1}优；
	 *         等于{@code 0}，{@code p1}与{@code p2}相等；
	 *         大于{@code 0}，{@code p2}优；
	 */
	public static int compare(TaxPlan p1, TaxPlan p2, double tolerance) {
		/**
		 * 算法：
		 * 小于{@code 0}，如果{@code p1}总税<{@code p2}；
		 * 小于{@code 0}，如果{@code p1}总税={@code p2}，且{@code p1}月薪高；
		 * 小于{@code 0}，如果{@code p1}总税={@code p2}，且月薪相等；
		 * 大于{@code 0}，如果{@code p1}总税={@code p2}，且{@code p2}月薪高；
		 * 大于{@code 0}，如果{@code p1}总税>{@code p2}
		 */
		double diffTotalTaxes = DoubleUtils.sub(p1.getTotalTaxes(), p2.getTotalTaxes());

		if (Double.compare(diffTotalTaxes, -tolerance) >= 0 && Double.compare(diffTotalTaxes, tolerance) <= 0) {
			// 年总纳税额：p1==p2

			int isMoreSalary = Double.compare(p1.getPreTaxSalary(), p2.getPreTaxSalary());
			// 2.月薪高者优先
			if (isMoreSalary > 0) {
				return -1;
			} else if (isMoreSalary < 0) {
				return 1;
			} else {
				return 0;
			}
		} else if (Double.compare(diffTotalTaxes, DoubleUtils.DOUBLE_ZERO) < 0) {
			// 年总纳税额：p1<p2
			return -1;
		} else {
			// 年总纳税额：p1>p2
			return 1;
		}

	}

	@Override
	public Object clone() {
		TaxPlan tp = null;
		try {
			tp = (TaxPlan) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return tp;
	}
}
