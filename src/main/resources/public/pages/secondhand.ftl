<#compress>
    <#escape x as x?html>
        <#include "../common/macro.ftl">
<!-- freemarker macros have to be imported into a namespace. We strongly
recommend sticking to 'spring' -->
        <#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="zh">
<head>
    <@meta />

	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<link type="text/css" rel="stylesheet" media="all" href="/dist/css/bootstrap.min.css"/>
	<script src="/dist/js/bootstrap.min.js"></script>
	<script src="/dist/js/vue.js"></script>
	<script src="/dist/js/Chart.bundle.js"></script>

	<script type="text/javascript">
		function checkInput() {
			var text = $("#income").val();
			if (text == null || text === "") {
				$("#calcBtn1st").attr("disabled", "true");
			} else {
				$("#calcBtn1st").removeAttr("disabled");
			}
		}
	</script>
</head>
<body>
<div id="app" class="container">

	<div class="panel panel-info">
		<div class="panel-heading" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"
		     aria-expanded="true" aria-controls="collapseTwo">二手房挂牌查询
		</div>
		<div class="panel-body panel-collapse collapse in" id="collapseTwo" role="tabpanel"
		     aria-labelledby="headingTwo">
			<div class="form-horizontal" id="tax_avoidance">
				<div class="form-group">
					<label for="estimatedAnnualSalary" class="col-sm-2 control-label">总价</label>
					<div class="col-sm-4">
						<input type="number" min="0" step="0.01" class="form-control" id="estimatedAnnualSalary"
						       name="estimatedAnnualSalary" v-model="estimatedAnnualSalary">
					</div>
					<p class="col-sm-2">～</p>
					<div class="col-sm-4">
						<input type="number" min="0" step="0.01" class="form-control" id="alreadyPaidSalary"
						       name="alreadyPaidSalary" min="1" v-model="alreadyPaidSalary">
					</div>
				</div>
				<div class="form-group">
					<label for="alreadyPaidSalary" class="col-sm-2 control-label">面积</label>
					<div class="col-sm-4">
						<input type="number" min="0" step="0.01" class="form-control" id="alreadyPaidSalary"
						       name="alreadyPaidSalary" min="1" v-model="alreadyPaidSalary">
					</div>
					<p class="col-sm-2">～</p>
					<div class="col-sm-4">
						<input type="number" min="0" step="0.01" class="form-control" id="alreadyPaidSalary"
						       name="alreadyPaidSalary" min="1" v-model="alreadyPaidSalary">
					</div>
				</div>
				<div class="form-group">
					<label for="remainingMonths" class="col-sm-2 control-label">区域</label>
					<div class="col-sm-10">
						<input type="number" class="form-control" id="remainingMonths" name="remainingMonths"
						       min="0" max="12" v-model="remainingMonths">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-primary" @click="optTaxPlan" :disabled="isPlanBtnDisabled" id="planBtn">
							筹划
						</button>
					</div>
				</div>

				<div v-if="isPlanVisible">
					<br>
					<p>月薪（税前）：{{taxPlan.preTaxSalary}}</p>
					<p>月纳税额：{{taxPlan.salaryTaxes}}</p>
					<p>年终奖（税前）：{{taxPlan.preTaxBonus}}</p>
					<p>年终奖纳税额：{{taxPlan.bonusTaxes}}</p>
					<p>全年总纳税额：{{taxPlan.totalTaxes}}</p>
					<table class="table table-bordered table-hover">
						<caption>适用税率</caption>
						<tr>
							<th>税率</th>
							<th>区间</th>
							<th>速扣数</th>
						</tr>
						<tr>
							<td>{{taxPlan.salaryTaxRate.rate}}</td>
							<td>[{{taxPlan.salaryTaxRate.rangeLowest}}, {{taxPlan.salaryTaxRate.rangeHighest}})</td>
							<td>{{taxPlan.salaryTaxRate.quickDeduction}}</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>

</div>
</body>
<script type="text/javascript">
	new Vue({
		el: '#app',
		data: function () {
			return {
				result: '',
				income: '',
				incomeResult: '',
				isFirstVisible: false,
				taxes: '',
				taxRate: {
					id: 0,
					rate: 0,
					rangeLowest: 0,
					rangeHighest: 0,
					quickDeduction: 0
				},
				estimatedAnnualSalary: '',
				alreadyPaidSalary: '',
				remainingMonths: '',
				isPlanVisible: false,
				taxPlan: {
					preTaxSalary: 0,
					preTaxBonus: 0,
					taxableSalary: 0,
					taxableBonus: 0,
					salaryTaxRate: {
						id: 0,
						rate: 0,
						rangeLowest: 0,
						rangeHighest: 0,
						quickDeduction: 0
					},
					bonusTaxRate: {
						id: 0,
						rate: 0,
						rangeLowest: 0,
						rangeHighest: 0,
						quickDeduction: 0
					},
					salaryTaxes: 0,
					bonusTaxes: 0,
					totalTaxes: 0
				}
			}
		},
		computed: {
			isPlanBtnDisabled() {
				var self = this;
				return (self.estimatedAnnualSalary != '' &&
						self.alreadyPaidSalary != '' &&
						self.remainingMonths != '');
			}
		},
		methods: {
			calcPrivTax() {
				var self = this;
				$.ajax({
					type: "get",
					url: "/tax/calc_tax",
					data: {"income": self.income},
					success: function (result) {
						self.isFirstVisible = true;
						self.incomeResult = self.income;
						self.taxes = result.taxes;
						self.taxRate = result.taxrate;
					}
				});
			},
			optTaxPlan() {
				var self = this;
				$.ajax({
					type: "post",
					// contentType : "application/json",
					url: "/tax/opt_plan",
					data: {
						"estimatedAnnualSalary": self.estimatedAnnualSalary,
						"alreadyPaidSalary": self.alreadyPaidSalary,
						"remainingMonths": self.remainingMonths
					},
					success: function (result) {
						self.isPlanVisible = true;
						self.taxPlan = result.taxplan;
					},
					error: function (e) {
						console.log("ERROR: ", e);
						display(e);
					}
				});
			}
		},
		beforeMount() {
			// this.initConfigList();
		}
	});
</script>
</html>
    </#escape>
</#compress>