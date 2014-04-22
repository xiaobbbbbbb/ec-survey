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
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/webjars/bootstrap/2.3.2/js/bootstrap.js"></script>

<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css"
	type="text/css" rel="stylesheet" />
<script src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"
	type="text/javascript"></script>

<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>
<script type="text/javascript">
	$(function() {
		$("#startTime,#endTime").datepickerStyle();
	});
</script>
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../../common/head.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid" style="padding-right: 20px;">
			<div>
				<ul class="breadcrumb">
					<li><a href="#">报案管理</a> <span class="divider">/</span></li>
					<li class="active">案件统计</li>
				</ul>
				<form class="well form-inline" style="text-align: center;">
					选择报案时间段：<input type="text" id="startTime" name="startTime" class="input-small" style="cursor: pointer;"
						readonly="readonly" value="${param.startTime}" placeholder="开始时间"> <input type="text" id="endTime" name="endTime"
						class="input-small" style="cursor: pointer;" value="${param.endTime}" readonly="readonly" placeholder="结束时间">
					&nbsp;&nbsp;&nbsp;&nbsp;选择区域分组：<%@ include file="../areainfo/select_area.jsp"%>
					<button type="submit" class="btn">
					搜索</button>
				</form>
				<div class="well">
					<ul class="nav nav-tabs" style="margin-bottom: 0px; border-bottom-width: 0px;">
						<li><a href="${ctx}/eventInfo/listNoScheduledCount">未调度</a></li>
						<li class="active"><a href="${ctx}/eventInfo/listScheduledCount">已调度</a></li>
					</ul>
					<table id="list" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>区域分组名称</th>
								<th>车牌号</th>
								<th>已调度未完成</th>
								<th>已完成</th>
								<th>已终止</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${dtos }" var="dto" varStatus="sn">
								<tr>
									<td>${dto.areaName}</td>
									<td>${dto.carNo}</td>
									<td><a href="${ctx}/eventInfo/listScheduledList?carNo=${dto.carNo}&status=1">${dto.noComplete}</a></td>
									<td><a href="${ctx}/eventInfo/listScheduledList?carNo=${dto.carNo}&status=2">${dto.complete}</a></td>
									<td><a href="${ctx}/eventInfo/listScheduledList?carNo=${dto.carNo}&status=3">${dto.end}</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>