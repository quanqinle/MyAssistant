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