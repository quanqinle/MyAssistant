<!-- freemarker macros have to be imported into a namespace. We strongly
recommend sticking to 'spring' -->
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" media="all" href="/dist/css/bootstrap.min.css"/>
<#--<script type="text/javascript" src="/dist/js/jquery.min.js"></script>-->
    <script type="text/javascript" src="/dist/js/bootstrap.min.js"></script>
    <meta charset="UTF-8">
    <title>Hello World!</title>
</head>
<body>
    <div id="header">
        <h2>个税计算器</h2>
    </div>
    <div id="content">
        <fieldset>
            <form name="incomeform" action="/tax/calc" method="post">
                月薪:<input type="text" name="income"/>
                <input type="submit" value="Calc"/>
            </form>
        </fieldset>
        <br/>
        <#if taxes?? >
            <p>收入：${income} 交税：${taxes}</p>
            <table class="datatable">
                <tr>
                    <th>税率</th>
                    <th>区间</th>
                    <th>速扣数</th>
                </tr>
                <tr>
                    <td>${taxrate.rate}</td>
                    <td>[${taxrate.rangeLowest}, ${taxrate.rangeHighest})</td>
                    <td>${taxrate.quickDeduction}</td>
                </tr>
            </table>
        <#else>
            <p>无</p>
        </#if>
    </div>

    <HR>
    <form name="tax_avoidance" action="/tax/plan" method="post">
        <p>预估总年薪: <input type="text" name="estimatedAnnualSalary" /></p>
        <p>已发薪酬: <input type="text" name="alreadyPaidSalary" /></p>
        <p>本年剩余月薪发放次数: <input type="text" name="remainingMonths" /></p>
        <p><input type="submit" value="Calc" /></p>
    </form>
    <div>
        <#if taxplan?? >
            <table class="datatable">
                <tr>
                    <th>税率</th>
                    <th>区间</th>
                    <th>速扣数</th>
                </tr>
                <tr>
                    <td>${taxplan.salaryTaxRate.rate}</td>
                    <td>[${taxplan.salaryTaxRate.rangeLowest}, ${taxplan.salaryTaxRate.rangeHighest})</td>
                    <td>${taxplan.salaryTaxRate.quickDeduction}</td>
                </tr>
            </table>
        </#if>
    </div>
</body>
</html>