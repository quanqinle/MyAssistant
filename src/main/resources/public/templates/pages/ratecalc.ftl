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

    <script type="text/javascript" src="/dist/js/jquery.min.js"></script>
    <link type="text/css" rel="stylesheet" media="all" href="/dist/css/bootstrap.min.css"/>
    <script type="text/javascript" src="/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/dist/js/vue.js"></script>

    <script type="text/javascript">
        function checkInput() {
            var text = $("#income").val();
            if (text == null || text == "") {
                $("#calcBtn1st").attr("disabled", "true");
            } else {
                $("#calcBtn1st").removeAttr("disabled");
            }
        }
    </script>
</head>
<body>
<div id="app">
    <p></p>
    <div class="container">
        <div class="panel panel-info">
            <div class="panel-heading" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                 aria-expanded="true" aria-controls="collapseOne">个税计算器
            </div>
            <div class="panel-body" id="collapseOne" class="panel-collapse collapse in" role="tabpanel"
                 aria-labelledby="headingOne">
                <fieldset>
                    <form name="incomeform" action="/tax/calc" method="post" class="form-signin">
                        <div class="col-xs-4">
                            <input type="number" min="0" step="0.01" name="income" class="form-control"
                                   placeholder="月薪（不含免税项，如五险一金）" id="income" onmouseleave="checkInput()"
                                   oninput="checkInput()"/>
                        </div>
                        <input type="submit" value="计算" class="btn btn-primary" id="calcBtn1st" disabled/>
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
            <div class="panel-heading" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"
                 aria-expanded="true" aria-controls="collapseTwo">个税统筹
            </div>
            <div class="panel-body" id="collapseTwo" class="panel-collapse collapse in" role="tabpanel"
                 aria-labelledby="headingTwo">
                <form name="tax_avoidance" action="/tax/plan" method="post" class="form-horizontal" role="form">
                    <div class="form-group">
                        <label for="estimatedAnnualSalary" class="col-sm-2 control-label">预估总年薪</label>
                        <div class="col-sm-10">
                            <input type="number" min="0" step="0.01" class="form-control" id="estimatedAnnualSalary"
                                   name="estimatedAnnualSalary">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alreadyPaidSalary" class="col-sm-2 control-label">已发薪酬</label>
                        <div class="col-sm-10">
                            <input type="number" min="0" step="0.01" class="form-control" id="alreadyPaidSalary"
                                   name="alreadyPaidSalary" min="1">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="remainingMonths" class="col-sm-2 control-label">本年剩余月薪发放次数</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" id="remainingMonths" name="remainingMonths"
                                   min="0" max="12">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary btn-lg">计算</button>
                        </div>
                    </div>
                </form>

            <#if taxplan?? >
            <br>
            <p>月薪（税前）：${taxplan.preTaxSalary}</p>
            <p>月纳税额：${taxplan.salaryTaxes}</p>
            <p>年终奖（税前）：${taxplan.preTaxBonus}</p>
            <p>年终奖纳税额：${taxplan.bonusTaxes}</p>
            <p>全年总纳税额：${taxplan.totalTaxes}</p>
            <table class="table table-bordered table-hover">
                <caption>适用税率</caption>
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
    </div>
</body>
<script type="text/javascript">
new Vue({
        el: '#app',
        data: function () {
            return {
                step1Visible: true,
                configList:[],
                newSelectedList:[],
                tables:[],
                mybatisForm:{
                    id:0,
                    projectName:'',
                    targetRuntime:'MyBatis3',
                    suppressAllComments:false,
                    targetProject:'',
                    targetModelPackage:'org.test.dao.entity',
                    targetMapperPackage:'mapper',
                    targetMapperInterfacePackage:'org.test.mapper',
                    driverClass:'com.mysql.jdbc.Driver',
                    connectionURL:'jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8',
                    userId:'',
                    password:'',
                    overwrite:true,
                    removePrefix:'',
                    timestamp2Date:false
                }
            }
        },
        methods: {
            initConfigList() {
                var self = this;
                $.ajax({
                    type:"get",
                    url:"/getAllConfig",
                    success:function (result) {
                        self.configList = result;
                    }
                });
            },
            build(){
                var self = this;
                var tableNames = new Array();
                var entityNames = new Array();
                for(var i=0;i<self.newSelectedList.length;i++){
                    if(self.newSelectedList[i]){
                        var table = self.tables[i];
                        tableNames.push(table.tableName);
                        entityNames.push(table.entityName);
                    }
                }
                var tableList = tableNames.join(",");
                var entityList = entityNames.join(",");
                $.ajax({
                    type:"post",
                    url:"/build",
                    data:{"tableList":tableList,"entityList":entityList,"id":self.step1Form.id},
                    success:function(result){
                        self.showResult(result);
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