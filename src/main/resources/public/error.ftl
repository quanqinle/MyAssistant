<#compress single_line=true>
<#escape x as x?html>
<#include "./common/macro.ftl">
<!DOCTYPE html>
<html lang="zh">
<head>
    <@meta/>
    <title>错误</title>
</head>
<body>
<%
%>
<h2>ErrorCode:${status}</h2>
<h5>Message:${errorMessage}</h5>
<h5>Message:${message}</h5>

<i>此错误信息来自 error.ftl文件，通过ErrorController.java统一处理</i>
</body>
</html>
</#escape>
</#compress>