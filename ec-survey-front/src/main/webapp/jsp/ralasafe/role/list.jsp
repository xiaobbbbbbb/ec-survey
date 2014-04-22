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
					<li><a href="#">机构管理</a> <span class="divider">/</span></li>
					<li class="active">角色管理</li>
				</ul>
				<div class="btn-toolbar">
					<button class="btn btn-primary" onfocus="this.blur()"
						onclick="href('${ctx}/ralasafe/role/addUI');">添加角色</button>
					<div class="btn-group"></div>
				</div>
				<div class="well">
					<table id="list" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="40px">序号</th>
								<th>角色名称</th>
								<th width="40%">描述</th>
								<th width="40px" align="center">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ECPage.list }" var="dto" varStatus="sn">
								<tr>
									<td>${sn.count}</td>
									<td>${dto.name}</td>
									<td>${dto.message}</td>
									<td class="td_center"><a onfocus="this.blur()"
										href="${ctx}/ralasafe/role/updateUI?id=${dto.roleId}"><i class="icon-pencil"></i></a> <a
										onfocus="this.blur()" role="button" href="javascript:void(0)"
										onclick="$.ec.deleteData({url : '${ctx}/ralasafe/role/delete',param : {'ids':${dto.roleId}}});">
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