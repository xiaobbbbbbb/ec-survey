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
					<button type="submit" class="btn">搜索</button>
				</form>
				<div class="well">
					<ul class="nav nav-tabs" style="margin-bottom: 0px; border-bottom-width: 0px;">
						<li class="active"><a href="${ctx}/eventInfo/listNoScheduledCount">未调度</a></li>
						<li><a href="${ctx}/eventInfo/listScheduledCount">已调度</a></li>
					</ul>
					<table id="list" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>待调度</th>
								<th>已取消报案</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><a href="${ctx}/eventInfo/list?status=0">${stattus0>0?stattus0:0}</a></td>
								<td><a href="${ctx}/eventInfo/list?status=3">${stattus3>0?stattus3:0}</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>