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
</head>
<body>
	<!-- 页面头部 -->
	<c:choose>
		<c:when test="${loginName=='system'}">
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
					<li><a href="#">系统设置</a> <span class="divider">/</span></li>
					<li class="active">日志管理</li>
				</ul>
				<div class="btn-toolbar">
					<button class="btn" onfocus="this.blur()">导出</button>
				</div>
				<div class="well">
					<table id="list" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="40px">序号</th>
								<th>内容</th>
								<th>类型</th>
								<th width="100px">IP</th>
								<th width="130px">操作用户</th>
								<th width="130px">操作时间</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ECPage.list }" var="dto" varStatus="sn">
								<tr>
									<td>${sn.count}</td>
									<td>${dto.action}</td>
									<td>${dto.type}</td>
									<td>${dto.ip}</td>
									<td>${dto.userName}</td>
									<td class="td_center"><fmt:formatDate value="${dto.createTime}"
											pattern="yyyy-MM-dd HH:mm" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<form action="${ctx}/systemLog/list" id="pageForm">
						<%@ include file="../../common/pager.jsp"%>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>