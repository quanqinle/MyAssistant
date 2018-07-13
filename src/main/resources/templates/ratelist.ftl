<!DOCTYPE html>
<html lang="en">
<head>
    <title>Hello World!</title>
</head>
<body>
<h2>个人所得税税率</h2>

<table>
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
        <td>${rateobj.rangeHighest}</td>
        <td>${rateobj.quickDeduction}
        <td>
    </tr>
    </#list>
</table>

</body>
</html>