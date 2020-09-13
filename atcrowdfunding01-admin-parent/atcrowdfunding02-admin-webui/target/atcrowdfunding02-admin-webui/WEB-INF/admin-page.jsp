<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="UTF-8">
<%@include file="include-head.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="css/pagination.css" />
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript">
    $(function(){// 调用专门的函数初始化分页导航条
        initPagination();
    });// 声明一个函数用于初始化 Pagination
    function initPagination() {// 获取分页数据中的总记录数
        var totalRecord = ${requestScope.pageInfo.total};// 声明 Pagination 设置属性的 JSON 对象
        var properties = {
            num_edge_entries: 3, // 边缘页数
            num_display_entries: 5, // 主体页数
            callback: pageSelectCallback, // 用户点击“翻页”按钮之后执行翻页操作的回调函数
            current_page: ${requestScope.pageInfo.pageNum-1}, // 当前页，pageNum 从 1 开始，必须-1 后才可以赋值
            prev_text: "上一页", next_text: "下一页",
            items_per_page:${requestScope.pageInfo.pageSize} // 每页显示 1 项
        };// 调用分页导航条对应的 jQuery 对象的 pagination()方法生成导航条
        $("#Pagination").pagination(totalRecord, properties);
    }// 翻页过程中执行的回调函数
    // 点击“上一页”、“下一页”或“数字页码”都会触发翻页动作，从而导致当前函数被调用
    // pageIndex 是用户在页面上点击的页码数值
    function pageSelectCallback(pageIndex, jQuery) {// pageIndex 是当前页页码的索引，相对于 pageNum 来说，pageIndex 比 pageNum 小 1
        var pageNum = pageIndex + 1;// 执行页面跳转也就是实现“翻页”
        window.location.href = "/admin/get/page.do?pageNum="+pageNum+"&keyword=${param.keyword}";// 取消当前超链接的默认行为
        return false;
    }
</script>
<body>

<%@include file="include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form action="admin/get/page.do" method="post" class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input name="keyword" value="${param.keyword}" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <%--<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='add.html'">
                        <i class="glyphicon glyphicon-plus"></i> 新增</button>--%>
                    <a href="admin/add/page.do" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</a>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope.pageInfo.list}">
                                <tr>
                                    <td colspan="6" align="center">抱歉！没有找到您要的数据</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.pageInfo.list}">
                                <c:forEach items="${requestScope.pageInfo.list}" var="admin" varStatus="myStatus">
                                    <tr>
                                        <td>${myStatus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <%--<button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>--%>
                                            <a href="assign/to/assign/role/page.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></a>
                                            <%--<button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>--%>
                                            <a href="admin/to/edit.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}"  class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></a>
                                            <%--<button type="button" class="btn btn-danger btn-xs">
                                                <i class=" glyphicon glyphicon-remove"></i></button>
                                            --%><a href="admin/remove/${admin.id}/${requestScope.pageInfo.pageNum}/${param.keyword}.do" class="btn btn-danger btn-xs">
                                                <i class=" glyphicon glyphicon-remove"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                   <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
