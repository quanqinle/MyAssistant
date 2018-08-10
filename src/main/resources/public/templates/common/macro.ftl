<#macro meta title="">
    <#if title=="">
    <title>我的私人世界</title>
    <#else>
    <title>${title}</title>
    </#if>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="format-detection" content="telephone=no">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</#macro>


<#-- 浏览器兼容性验证 -->
<#macro init>
<script>
    window["g_browser"] = (function (navigator) {
        var userAgent = navigator.userAgent,
                temp,
                match = userAgent.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || [],
                match2 = userAgent.match(/Android|BlackBerry|iPhone|iPad|iPod|IEMobile/);

        if (/trident/i.test(match[1])) {
            temp = /\brv[ :]+(\d+)/g.exec(userAgent) || [];
            return {
                browser: 'IE',
                version: temp[1] || ''
            };
        }

        if (match[1] === 'Chrome') {
            temp = userAgent.match(/\bOPR\/(\d+)/);
            if (temp !== null) {
                return {
                    browser: 'Opera',
                    version: temp[1] || ''
                };
            }
        }

        /**针对 Edge**/
        if (match[1] === 'Chrome') {
            temp = userAgent.match(/\bedge\/(\d+)/i);
            if (temp !== null) {
                return {
                    browser: 'IE',
                    version: temp[1] || ''
                };
            }
        }

        match = match[2] ? [match[1], match[2]] : [navigator.appName, navigator.appVersion, '-?'];

        if ((temp = userAgent.match(/version\/(\d+)/i)) !== null) {
            match.splice(1, 1, temp[1]);
        }

        return {
            browser: match[0] === 'MSIE' ? 'IE' : match[0],
            version: match[1],
            mobile: match2 && match2[0]
        };
    })(navigator);

    /* 浏览器不支持 */
    var isLtIE9 = window["g_browser"].browser == "IE" && window["g_browser"].version <= 9;
    if (/support/.test(window.location.href)) {
        if (!isLtIE9)
            window.location.href = "/";
    }
    else if (isLtIE9) {
        window.location.href = "/browser/support";
    }

    /* 适配移动端浏览器 */
    if (window["g_browser"].mobile) {
        setTimeout(function () {
            var oMeta = document.createElement('meta');
            oMeta.name = "viewport";
            oMeta.content = "width=" + window.innerWidth + "px";
            document.getElementsByTagName('head')[0].appendChild(oMeta);
        }, 0);
    }

    /* 数据初始化 */
    window["g_pageData"] = ${pageData!'{}'};
    window["g_userinfo"] = ${userJson!'{}'};
    window["g_config"] = {env: '{{env.NODE_ENV}}'};
</script>
</#macro>


