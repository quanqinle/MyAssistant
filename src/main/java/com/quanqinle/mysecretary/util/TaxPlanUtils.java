package com.quanqinle.mysecretary.util;

import com.quanqinle.mysecretary.entity.po.TaxRate;
import com.quanqinle.mysecretary.entity.vo.TaxPlan;
import com.quanqinle.mysecretary.service.TaxRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

/**
 * 薪酬税费筹划工具类
 *
 * @author quanql
 *
 */
@Component
public class TaxPlanUtils {
	private static TaxPlanUtils taxPlanUtils;
	@Autowired
	protected TaxRateService taxRateService;

	@PostConstruct
	public void init() {
		taxPlanUtils = this;
		taxPlanUtils.taxRateService = this.taxRateService;
	}

	/**
	 * 个税门槛（适用于居民、非居民）
	 * 2018.10.1之前3500
	 */
	private static double TAX_THRESHOLD = 5000;
	/**
	 * 算税过程中，除法运算精度
	 */
	private static final int DEF_DIV_SCALE = 2;
	/**
	 * 算税过程中，乘法运算精度
	 */
	private static final int DEF_MUL_SCALE = 2;

	/**
	 * 个税税率递进表
	 */
/*	public static final List<TaxRate> TAX_RATES = new ArrayList<TaxRate>() {
		private static final long serialVersionUID = 1L;
		{
			add(new TaxRate(0, 0, 0, 0, 0));
			add(new TaxRate(1, 0, 1500, 0.03, 0));
			add(new TaxRate(2, 1500, 4500, 0.1, 105));
			add(new TaxRate(3, 4500, 9000, 0.2, 555));
			add(new TaxRate(4, 9000, 35000, 0.25, 1005));
			add(new TaxRate(5, 35000, 55000, 0.30, 2755));
			add(new TaxRate(6, 55000, 80000, 0.35, 5505));
			add(new TaxRate(7, 80000, Double.MAX_VALUE, 0.45, 13505));
		}
	};*/


	/**
	 * for debug
	 */
	public static void main(String[] args) {

		double[][] argsFunc = {
				/* { 预计年收入总额, 已发总薪酬, 本年剩余月薪发放次数} */
				{ 40000.00, 0d, 12d },
				{ 42000.00, 0d, 12d },
				{ 58000.00, 0d, 12d },
				{ 60000.00, 0d, 12d },
				{ 74000.00, 0d, 12d },
				{ 78000.00, 0d, 12d },
				{ 130000.00, 0d, 12d },
				{ 150000.00, 0d, 12d },
				{ 200000.00, 0d, 12d },
				{ 300000.00, 0d, 12d },
				{ 400000.00, 0d, 12d },
				{ 500000.00, 0d, 12d },
				{ 600000.00, 0d, 12d },
		};
		System.out.println("年薪, 年终奖, 年终奖税率, 月薪, 月薪税率, 总纳税");
		for (double[] arg : argsFunc) {
			TaxPlan bestTaxPlan = calcBestTaxPlanQuickly(arg[0], arg[1], (int) arg[2]);
			System.out.printf("%.2f, %.2f, %.2f, %.2f, %.2f, %.2f\n", arg[0], bestTaxPlan.getPreTaxBonus(),
					bestTaxPlan.getBonusTaxRate().getRate(), bestTaxPlan.getPreTaxSalary(),
					bestTaxPlan.getSalaryTaxRate().getRate(), bestTaxPlan.getTotalTaxes());

			TaxPlan bestTaxPlan1 = calcBestTaxPlanPrecisely(arg[0], arg[1], (int) arg[2]);
			System.out.printf("穷举最优方案：\n%.2f, %.2f, %.2f, %.2f, %.2f, %.2f\n", arg[0], bestTaxPlan1.getPreTaxBonus(),
					bestTaxPlan1.getBonusTaxRate().getRate(), bestTaxPlan1.getPreTaxSalary(),
					bestTaxPlan1.getSalaryTaxRate().getRate(), bestTaxPlan1.getTotalTaxes());

			System.out.printf("总税差额: %.2f\n\n",
					DoubleUtils.sub(bestTaxPlan.getTotalTaxes(), bestTaxPlan1.getTotalTaxes()));
		}

//		TaxPlan bestTaxPlan = calcBestTaxPlanQuickly(50000d, 0d, 12);
//		System.out.println("区间法计算最优方案：" + bestTaxPlan.toString());

//		 TaxPlan bestTaxPlan1 = calcBestTaxPlanPrecisely(50000d, 0d, 12);
//		 System.out.println("穷举法计算最优方案：" + bestTaxPlan1.toString());
	}

	/**
	 * 获取税率表
	 * @return 税率表
	 */
	public static List<TaxRate> getTaxRateList() {
//		return TAX_RATES;
//		TaxRateService taxRateService = new TaxRateServiceImpl();
		return taxPlanUtils.taxRateService.getTaxRateTable();
	}


	/**
	 * 计算月薪VS年终奖避税组合最优解（使用预设区间，结果近似，但计算快）
	 *
	 * @param estimatedAnnualSalary
	 *            预计年收入总额（不含除五险一金，不含其他免税项目）
	 * @param alreadyPaidSalary
	 *            已发总薪酬
	 * @param remainingMonths
	 *            本年剩余月薪发放次数
	 * @return {@link com.quanqinle.mysecretary.entity.vo.TaxPlan TaxPlan} 个人薪酬、税费完整信息
	 *
	 * @author quanql
	 */
	public static TaxPlan calcBestTaxPlanQuickly(double estimatedAnnualSalary, double alreadyPaidSalary,
	                                             int remainingMonths) {

		// 总税金差距小于此值时，仍认为两数相等
		Double tolerance = 0.1d;

		// 根据税率表控制年终奖情况下，得到税率最优方案
		TaxPlan bestTaxPlanOfSalaryRate = new TaxPlan();
		// 根据税率表控制月薪情况下，得到税率最优方案
		TaxPlan bestTaxPlanOfBonusRate = new TaxPlan();

		bestTaxPlanOfSalaryRate.setTotalTaxes(Double.MAX_VALUE);
		bestTaxPlanOfBonusRate.setTotalTaxes(Double.MAX_VALUE);

		// 年终奖在税率节点时的方案（年终奖税额跳变点）
		for (TaxRate rate : getTaxRateList()) {
			if (Double.compare(rate.getRangeHighest(), Double.MAX_VALUE) >= 0) {
				continue;
			}
			double preTaxSalary = 0.0d;
			double preTaxBonus = DoubleUtils.mul(rate.getRangeHighest(), 12, DEF_MUL_SCALE);
			if (Double.compare(preTaxBonus, 0.0d) == 0) {
				// 税率区间取0值时，特殊处理。即，全部按月薪发放。另，采用舍位法，避免总年薪超发
				preTaxSalary = DoubleUtils.div(DoubleUtils.sub(estimatedAnnualSalary, alreadyPaidSalary),
						remainingMonths, DEF_DIV_SCALE, BigDecimal.ROUND_FLOOR);
			}
			TaxPlan tempTaxPlan = new TaxPlan(preTaxSalary, preTaxBonus);
			tempTaxPlan = calcTaxPlan(estimatedAnnualSalary, alreadyPaidSalary, remainingMonths, tempTaxPlan);
			if (null == tempTaxPlan) {
				continue;
			}
			if (TaxPlan.compare(tempTaxPlan, bestTaxPlanOfBonusRate) < 0) {
				bestTaxPlanOfBonusRate = (TaxPlan) tempTaxPlan.clone();
			}
		}

		// 月薪取税率节点时的方案
		for (TaxRate rate : getTaxRateList()) {
			if (Double.compare(rate.getRangeHighest(), Double.MAX_VALUE) >= 0) {
				continue;
			}
			double preTaxSalary = DoubleUtils.add(rate.getRangeHighest(), getTaxThreshold());
			double preTaxBonus = 0.0d;
			TaxPlan tempTaxPlan = new TaxPlan(preTaxSalary, preTaxBonus);
			tempTaxPlan = calcTaxPlan(estimatedAnnualSalary, alreadyPaidSalary, remainingMonths, tempTaxPlan);
			if (null == tempTaxPlan) {
				continue;
			}
			if (TaxPlan.compare(tempTaxPlan, bestTaxPlanOfSalaryRate) < 0) {
				bestTaxPlanOfSalaryRate = (TaxPlan) tempTaxPlan.clone();
			}
		}

		// 1.优先选总税低 2.总税额相差tolerance元以内时，优先选月薪最大
		if (TaxPlan.compare(bestTaxPlanOfSalaryRate, bestTaxPlanOfBonusRate, tolerance) < 0) {
			return bestTaxPlanOfSalaryRate;
		}
		return bestTaxPlanOfBonusRate;
	}

	/**
	 * 计算月薪VS年终奖避税组合最优解（使用穷举法，结果精确，但计算慢。生产中谨慎使用！）
	 *
	 * @param estimatedAnnualSalary
	 *            预计年收入总额（不含除五险一金，不含其他免税项目）
	 * @param alreadyPaidSalary
	 *            已发总薪酬
	 * @param remainingMonths
	 *            本年剩余月薪发放次数
	 * @return {@link com.quanqinle.mysecretary.entity.vo.TaxPlan TaxPlan} 个人薪酬、税费完整信息
	 *
	 * @author quanql
	 */
	public static TaxPlan calcBestTaxPlanPrecisely(double estimatedAnnualSalary, double alreadyPaidSalary,
	                                               int remainingMonths) {

		// 剩余应发金额
		double remaining = DoubleUtils.sub(estimatedAnnualSalary, alreadyPaidSalary);

		TaxPlan bestTaxPlan = new TaxPlan();
		bestTaxPlan.setTotalTaxes(Double.MAX_VALUE);

		for (double preTaxSalary = DoubleUtils.DOUBLE_ZERO; Double.compare(DoubleUtils.mul(preTaxSalary, remainingMonths),
				remaining) <= 0; preTaxSalary = DoubleUtils.add(preTaxSalary, 0.01d)) {

			TaxPlan tempTaxPlan = new TaxPlan(preTaxSalary, DoubleUtils.DOUBLE_ZERO);
			tempTaxPlan = calcTaxPlan(estimatedAnnualSalary, alreadyPaidSalary, remainingMonths, tempTaxPlan);
			if (null == tempTaxPlan) {
				continue;
			}

			if (TaxPlan.compare(tempTaxPlan, bestTaxPlan) < 0) {
				bestTaxPlan = (TaxPlan) tempTaxPlan.clone();
			}
		}

		return bestTaxPlan;
	}

	/**
	 * 根据入参，计算月薪VS年终奖组合税费详细信息
	 *
	 * @param estimatedAnnualSalary
	 *            预计年收入总额（不含除五险一金，不含其他免税项目）
	 * @param alreadyPaidSalary
	 *            已发总薪酬
	 * @param remainingMonths
	 *            本年剩余月薪发放次数
	 * @param taxPlan
	 *            {@link com.quanqinle.mysecretary.entity.vo.TaxPlan
	 *            TaxPlan}中税前月薪或税前年终奖，至少一个是正值
	 *
	 * @return null，如果参数不合法；否则，{@link com.quanqinle.mysecretary.entity.vo.TaxPlan
	 *         TaxPlan}个人薪酬、税费完整信息。
	 *
	 */
	private static TaxPlan calcTaxPlan(double estimatedAnnualSalary, double alreadyPaidSalary, int remainingMonths,
	                                   TaxPlan taxPlan) {

		// 剩余应发金额
		double remaining = DoubleUtils.sub(estimatedAnnualSalary, alreadyPaidSalary);
		double preTaxSalary = DoubleUtils.div(DoubleUtils.sub(remaining, taxPlan.getPreTaxBonus()), remainingMonths,
				DEF_DIV_SCALE);
		double preTaxBonus = DoubleUtils.sub(remaining,
				DoubleUtils.mul(taxPlan.getPreTaxSalary(), remainingMonths, DEF_MUL_SCALE));

		int isValidPreTaxSalary = Double.compare(taxPlan.getPreTaxSalary(), 0.0d);
		int isValidPreTaxBonus = Double.compare(taxPlan.getPreTaxBonus(), 0.0d);

		if (Double.compare(estimatedAnnualSalary, DoubleUtils.DOUBLE_ZERO) <= 0 || Double.compare(alreadyPaidSalary, DoubleUtils.DOUBLE_ZERO) < 0
				|| remainingMonths < 0) {
			// 入参不能是负
			return taxPlan = null;
		}
		if (isValidPreTaxBonus < 0 || isValidPreTaxSalary < 0) {
			// 入参（月薪、年终）不能是负
			return taxPlan = null;
		}
		if (Double.compare(remaining, DoubleUtils.DOUBLE_ZERO) < 0) {
			// 剩余应发金额不能是负
			return taxPlan = null;
		}
		if (Double.compare(preTaxSalary, DoubleUtils.DOUBLE_ZERO) < 0) {
			// 年终不能大于剩余应发
			return taxPlan = null;
		}
		if (Double.compare(preTaxBonus, DoubleUtils.DOUBLE_ZERO) < 0) {
			// 月薪总额不能大于剩余应发
			return taxPlan = null;
		}
		/*--以上参数校验--*/

		if (isValidPreTaxBonus > 0 && isValidPreTaxSalary == 0) {
			// 入参年终>0，给月薪赋值
			taxPlan.setPreTaxSalary(preTaxSalary);
		} else if (isValidPreTaxSalary > 0 && isValidPreTaxBonus == 0) {
			// 入参月薪>0，给年终赋值
			taxPlan.setPreTaxBonus(preTaxBonus);
		} else {
			// 入参（年终、月薪）都是0
			return taxPlan = null;
		}

		taxPlan.setTaxableSalary(calcTaxableSalary(taxPlan.getPreTaxSalary()));
		TaxRate salaryTaxRate = getTaxRate(taxPlan.getTaxableSalary());
		taxPlan.setSalaryTaxRate(salaryTaxRate);
		taxPlan.setSalaryTaxes(calcTaxes(taxPlan.getTaxableSalary()));

		taxPlan.setTaxableBonus(calcTaxableBonus(taxPlan.getPreTaxBonus(), taxPlan.getPreTaxSalary()));
		TaxRate bonusTaxRate = getTaxRateOfBonus(taxPlan.getTaxableBonus());
		taxPlan.setBonusTaxRate(bonusTaxRate);
		taxPlan.setBonusTaxes(calcTaxesOfBonus(taxPlan.getTaxableBonus()));

		taxPlan.setTotalTaxes(DoubleUtils.add(DoubleUtils.mul(taxPlan.getSalaryTaxes(), remainingMonths, DEF_MUL_SCALE),
				taxPlan.getBonusTaxes()));

		return taxPlan;
	}

	/**
	 * 获取应纳税所得。应纳税所得 = 月收入 - 个税起征点
	 *
	 * @param preTaxSalary
	 *            税前月薪
	 * @return
	 */
	public static double calcTaxableSalary(double preTaxSalary) {
		double diff = DoubleUtils.sub(preTaxSalary, getTaxThreshold());
		return Double.compare(diff, DoubleUtils.DOUBLE_ZERO) > 0 ? diff : DoubleUtils.DOUBLE_ZERO;
	}

	/**
	 * 获取个税起征点
	 *
	 * @return
	 */
	private static double getTaxThreshold() {
		// FIXME 起征点需要区分天朝人or上等人（老外）
		return TAX_THRESHOLD;
	}

	/**
	 * 获取适用税率
	 *
	 * @param taxableSalary
	 *            应纳税所得。已扣除免税，已减去起征点
	 * @return
	 */
	public static TaxRate getTaxRate(double taxableSalary) {
		for (TaxRate taxRate : getTaxRateList()) {
			if (taxRate.isInRange(taxableSalary)) {
				return taxRate;
			}
		}

		return getTaxRateList().get(0);
	}

	/**
	 * 计算应纳税额
	 *
	 * @param taxableSalary
	 *            应纳税所得额。已扣除免税，已减去起征点
	 * @return
	 */
	public static double calcTaxes(double taxableSalary) {
		double tax = DoubleUtils.DOUBLE_ZERO;

		TaxRate taxRate = getTaxRate(taxableSalary);
		if (taxRate != null) {
			tax = DoubleUtils.sub(DoubleUtils.mul(taxableSalary, taxRate.getRate(), DEF_MUL_SCALE),
					taxRate.getQuickDeduction());
		}

		return tax;
	}

	/**
	 * 计算应纳税额
	 * @param preTaxSalary 税前月薪
	 *
	 * @return
	 */
	public static double calcTaxesByPreTaxSalary(double preTaxSalary) {
		double taxableSalary = calcTaxableSalary(preTaxSalary);
		return  calcTaxes(taxableSalary);
	}

	/**
	 * 获取年终奖应纳税额
	 *
	 * @param bonus
	 *            年终奖
	 * @param preTaxSalaryOfCurrentMonth
	 *            发放年终奖的当月，税前月薪（已扣减五险一金、未扣减个税起征）
	 * @return
	 */
	private static double calcTaxableBonus(double bonus, double preTaxSalaryOfCurrentMonth) {

		// 年终奖发放当月，月薪不低于个税起征点时
		if (Double.compare(getTaxThreshold(), preTaxSalaryOfCurrentMonth) < 0) {
			return bonus;
		}

		double diff = DoubleUtils.sub(bonus, DoubleUtils.sub(getTaxThreshold(), preTaxSalaryOfCurrentMonth));
		if (Double.compare(diff, DoubleUtils.DOUBLE_ZERO) > 0) {
			return diff;
		} else {
			return DoubleUtils.DOUBLE_ZERO;
		}
	}

	/**
	 * 获取年终奖税率
	 *
	 * @param taxableBonus
	 *            年终奖应纳税部分
	 * @return
	 */
	private static TaxRate getTaxRateOfBonus(double taxableBonus) {

		if (Double.compare(taxableBonus, DoubleUtils.DOUBLE_ZERO) >= 0) {
			TaxRate taxRate = getTaxRate(DoubleUtils.div(taxableBonus, 12.0d, DEF_DIV_SCALE));
			if (taxRate != null) {
				return taxRate;
			}
		}

		return getTaxRate(0.0d);
	}

	/**
	 * 计算年终奖交税额
	 *
	 * @param taxableBonus
	 *            年终奖应纳税部分
	 * @return
	 */
	private static double calcTaxesOfBonus(double taxableBonus) {

		double dTaxRate = DoubleUtils.DOUBLE_ZERO;
		double dQuickDeduction = DoubleUtils.DOUBLE_ZERO;
		double taxes = DoubleUtils.DOUBLE_ZERO;

		if (Double.compare(taxableBonus, DoubleUtils.DOUBLE_ZERO) <= 0) {
			return taxes;
		}

		TaxRate taxRate = getTaxRateOfBonus(taxableBonus);
		if (taxRate != null) {
			dTaxRate = taxRate.getRate();
			dQuickDeduction = taxRate.getQuickDeduction();

			taxes = DoubleUtils.sub(DoubleUtils.mul(taxableBonus, dTaxRate, DEF_MUL_SCALE), dQuickDeduction);
		}

		return taxes;
	}

}

