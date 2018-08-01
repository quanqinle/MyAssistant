<!DOCTYPE html>
<!-- freemarker macros have to be imported into a namespace. We strongly
recommend sticking to 'spring' -->
<#import "/spring.ftl" as spring/>
<html  lang="zh-CN">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <script type="text/javascript" src="/dist/js/jquery.min.js"></script>
    <link type="text/css" rel="stylesheet" media="all" href="/dist/css/bootstrap.min.css"/>
    <script type="text/javascript" src="/dist/js/bootstrap.min.js"></script>
    <title>Hello World!</title>
</head>
<body>
    <p></p>
    <div class="container">
        <div class="panel panel-info">
            <div class="panel-heading" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">个税计算器</div>
            <div class="panel-body" id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                <fieldset>
                    <form name="incomeform" action="/tax/calc" method="post" class="form-signin" >
                        <input type="text" name="income" class="form-control" placeholder="月薪（不含免税项，如五险一金）" />
                        <br>
                        <input type="submit" value="计算" class="btn btn-primary btn-lg"/>
                    </form>
                </fieldset>

                <#if taxes?? >
                    <br>
                    <p>收入：${income} 交税：${taxes}</p>
                    <table class="table table-bordered table-hover">
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
                </#if>
            </div>
        </div>


    <#--<HR>-->

        <div class="panel panel-info">
            <div class="panel-heading" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">个税统筹</div>
            <div class="panel-body" id="collapseTwo" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingTwo">
                <form name="tax_avoidance" action="/tax/plan" method="post" class="form-signin" >
                    <p>预估总年薪：<input type="text" name="estimatedAnnualSalary" class="form-control" /></p>
                    <p>已发薪酬：<input type="text" name="alreadyPaidSalary" class="form-control" /></p>
                    <p>本年剩余月薪发放次数：<input type="text" name="remainingMonths" class="form-control" /></p>
                    <input type="submit" value="计算" class="btn btn-primary btn-lg"/>
                </form>

            <#if taxplan?? >
            <br>
            <table class="table table-bordered table-hover">
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
    </div>
</body>
</html>