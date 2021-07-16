<@compress single_line=true>
    <#escape x as x?html>
        <#include "../common/macro.ftl">
<!DOCTYPE html>
<html lang="zh">
<head>
    <@meta/>
	<link rel="stylesheet" type="text/css" media="all" href="/dist/css/bootstrap.min.css"/>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="/dist/js/bootstrap.min.js"></script>

	<style>
		th {
			background-color: #4CAF50;
			color: white;
			text-align: center;
		}

		td {
			text-align: right;
		}
	</style>
	<script type="text/javascript">
		function toDecimal2(val) {
			return new Number(val).toFixed(2);
		}
	</script>
</head>
<body>
<div class="container">
	<h2>个人所得税税率</h2>
	<table class="table table-bordered table-hover">
        <tr>
	        <th>ID</th>
	        <th>税率(%)</th>
	        <th>应纳税所得额（含税）</th>
	        <th>速算数</th>
        </tr>
    <#list ratelist as rateobj>
    <tr>
	    <td style="text-align: center">${rateobj.id}</td>
        <td>${rateobj.rate * 100}</td>
	    <td style="text-align: center">
            <#if rateobj.rangeHighest == -1>
	            超过 ${rateobj.rangeLowest} 的部分
            <#elseif rateobj.rangeLowest == 0>
                不超过 ${rateobj.rangeHighest} 的部分
            <#else>
                超过 ${rateobj.rangeLowest} ～ ${rateobj.rangeHighest} 元的部分
            </#if>
	    </td>
	    <td>${rateobj.quickDeduction}</td>
    </tr>
    </#list>
	</table>
	<p>月应纳个税 = 月应纳税所得额 × 税率 - 速算扣除数</p>
</div>
</body>
</html>
    </#escape>
</@compress>