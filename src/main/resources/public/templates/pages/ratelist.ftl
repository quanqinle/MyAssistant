<@compress single_line=true>
<#escape x as x?html>
<#include "../common/macro.ftl">
<!DOCTYPE html>
<html lang="zh">
<head>
    <@meta/>
    <link rel="stylesheet" type="text/css" media="all" href="/dist/css/bootstrap.min.css"/>
    <script type="text/javascript" src="/dist/js/jquery.min.js"></script>
    <script type="text/javascript" src="/dist/js/bootstrap.min.js"></script>

    <style>
        th {
            background-color: #4CAF50;
            color: white;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>个人所得税税率</h2>

    <table  class="table table-bordered table-hover">
        <tr>
            <th>Id</th>
            <th>税率</th>
            <th>区间<=</th>
            <th>区间<</th>
            <th>速算数</th>
        </tr>
    <#list ratelist as rateobj>
    <tr>
        <td>${rateobj.id}</td>
        <td>${rateobj.rate}</td>
        <td>${rateobj.rangeLowest}</td>
        <td>
            <#if rateobj.rangeHighest == -1>
                max
            <#else>
                ${rateobj.rangeHighest}
            </#if>
        </td>
        <td>${rateobj.quickDeduction}</td>
    </tr>
    </#list>
    </table>
</div>
</body>
</html>
</#escape>
</@compress>