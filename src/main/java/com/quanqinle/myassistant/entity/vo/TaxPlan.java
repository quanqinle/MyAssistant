package com.quanqinle.myassistant.entity.vo;

import com.quanqinle.myassistant.entity.po.TaxRate;
import com.quanqinle.myassistant.util.DoubleUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 税收筹划结果
 *
 * @author quanqinle
 *
 */
@Data
public class TaxPlan implements Cloneable {
	/**
	 * 总月薪。税前，但已扣五险一金
	 */
	@ApiModelProperty(value = "税前总月薪")
	private double preTaxSalary;
	/**
	 * 年终奖。税前
	 */
	@ApiModelProperty(value = "税前年终奖")
	private double preTaxBonus;
	/**
	 * 税前月薪中应纳税部分。月薪应纳税额=总月薪-个税起征点
	 */
	@ApiModelProperty(value = "税前月薪中应纳税部分")
	private double taxableSalary;
	/**
	 * 税前年终奖中应纳税部分。<BR>
	 * 当发年终奖当月的月薪低于个税起征点时，年终奖中有部分免税
	 */
	@ApiModelProperty(value = "税前年终奖中应纳税部分")
	private double taxableBonus;
	/**
	 * 月薪税率
	 */
	@ApiModelProperty(value = "月薪税率")
	private TaxRate salaryTaxRate;
	/**
	 * 年终奖税率
	 */
	@ApiModelProperty(value = "年终奖税率")
	private TaxRate bonusTaxRate;
	/**
	 * 月薪纳税额
	 */
	@ApiModelProperty(value = "月薪纳税额")
	private double salaryTaxes;
	/**
	 * 年终奖纳税额 Year-End Bonus
	 */
	@ApiModelProperty(value = "年终奖纳税额")
	private double bonusTaxes;
	/**
	 * 年总纳税额
	 */
	@ApiModelProperty(value = "年总纳税额")
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
