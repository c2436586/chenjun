<%--
  Created by IntelliJ IDEA.
  User: 20302
  Date: 2020/7/7
  Time: 9:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@include file="include-head.jsp"%>
<body>
<script type="text/javascript">
    $(function(){
        $("button").click(function(){
           window.history.back();
        });
    });
</script>
<div class="container" style="text-align: center;">
    <h3>系统信息页面</h3>
    <h4>${requestScope.exception.message }</h4>
    <button style="width: 300px;margin: 0px auto 0px auto;" class="btn btn-lg btn-success btn-block">返回刚才页面</button>
</div>
</body>
</html>
