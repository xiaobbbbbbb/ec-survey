<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>欢迎使用保易行系统</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/media/css/admin/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/media/css/admin/theme.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/font-awesome/css/font-awesome.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/webjars/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/webjars/bootstrap/2.3.2/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>
</head>
<body>
	<!-- 页面头部 -->
	<c:choose>
	   <c:when test="${sessionScope.USER_SESSION.loginName=='system'}">
	       <%@ include file="../../admin/head.jsp"%>
	   </c:when>
	   <c:otherwise>
	       <%@ include file="../../common/head.jsp"%>
	   </c:otherwise>
	</c:choose>
	<div class="container-fluid">
		<div class="row-fluid">
			<div>
				<ul class="breadcrumb">
					<li><a href="#">权限管理</a> <span class="divider">/</span></li>
					<li class="active">操作员管理</li>
				</ul>
				<div class="btn-toolbar">
					<button class="btn btn-primary" onfocus="this.blur()"
						onclick="href('${ctx}/ralasafe/user/addUI');">添加操作员</button>
					<div class="btn-group"></div>
				</div>
				<div class="well">
					<table id="list" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>序号</th>
								<th>用户名</th>
								<th>登录名</th>
								<th>所属服务机构</th>
								<th>电子邮件</th>
								<th>联系电话</th>
								<th width="90px">是否启用</th>
								<th width="40px">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ECPage.list }" var="dto" varStatus="sn">
								<tr>
									<td>${dto.level}</td>
									<td>${dto.name}</td>
									<td>${dto.loginName}</td>
									<td>${dto.orgName}</td>
									<td>${dto.email}</td>
									<td>${dto.phone}</td>
									<td class="td_center"><c:choose>
											<c:when test="${dto.disabled==1}">有效</c:when>
											<c:otherwise>
												<span class="red">无效</span>
											</c:otherwise>
										</c:choose></td>
									<td class="td_center">
									<a onfocus="this.blur()"
										href="${ctx}/ralasafe/user/updateUI?id=${dto.userId}"><i class="icon-pencil"></i></a> <a
										onfocus="this.blur()" role="button" href="javascript:void(0)"
										onclick="$.ec.deleteData({url : '${ctx}/ralasafe/user/delete',param : {'ids':${dto.userId}}});">
											<i class="icon-remove"></i>
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<%@ include file="../../common/pager.jsp"%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>