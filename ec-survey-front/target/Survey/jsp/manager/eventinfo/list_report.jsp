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
	<%@ include file="../../common/head.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<div>
				<ul class="breadcrumb">
					<li><a href="#">统计报表</a> <span class="divider">/</span></li>
					<li class="active">报案统计</li>
				</ul>
				<div class="btn-toolbar">
					<button class="btn" onfocus="this.blur()" onclick="href('${ctx}/eventInfo/downloadCaseInfo');">导出</button>
				</div>
				<table class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="40px">序号</th>
							<th>区域分组名称</th>
							<th>车牌号</th>
				 			<th width="90px">月份</th>
							<th>案件数量</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${dtos }" var="dto" varStatus="sn">
							<tr>
								<td class="td_left">${sn.count}</td>
								<td class="td_center">${dto.name}</td>
								<td class="td_center">${dto.carNo}</td>
								<td class="td_center">${dto.months}</td>
								<td class="td_center"><a
									href="${ctx}/eventInfo/list?areaId=${dto.areaId}&startTime=${dto.months}-01&endTime=${dto.months}-31">${dto.count}</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>