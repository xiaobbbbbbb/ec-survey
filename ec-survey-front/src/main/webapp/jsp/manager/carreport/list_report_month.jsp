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


<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js" type="text/javascript"></script>

<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>
 
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../../common/head.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<div>
				<ul class="breadcrumb">
					<li><a href="#">统计报表</a> <span class="divider">/</span></li>
					<li class="active">车辆运行月报告</li>
				</ul>
				<div class="btn-toolbar">
					<button class="btn" onfocus="this.blur()" onclick="href('${ctx}/carReport/downloadCarReportMonth');">导出</button>
					<button class="btn" onfocus="this.blur()" onclick="history.go(-1)">返回</button>
				</div>
				<form class="well form-inline" style="text-align: center;">
					<%@ include file="select_year_month.jsp"%>
					<button type="submit" class="btn">搜索</button>
				</form>
				 
				<table class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="40px">序号</th>
							<th>车牌号</th>
							<th>查勘次数</th>
							<th>里程（KM）</th>
							<th>违章记录</th>
							<th>超速记录</th>
							<th>围栏告警记录</th>
							<th>月份</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${ECPage.list }" var="dto" varStatus="sn">
							<tr>
								<td>${sn.count}</td>
								<td>${dto.carNo}</td>
								<td>${dto.surveyNum}</td>
								<td>${dto.totalMonthMile}</td>
								<td>${dto.trafficNum}</td>
								<td>${dto.totalHypervelocity}</td>
								<td>${dto.totalFenceCounts}</td>
								<td><a href="${ctx}/carReport/listReport?month=${dto.month}&carId=${dto.carId}">${dto.month}</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			 	<form action="${ctx}/carReport/listReport" id="pageForm">
			 		<input name="year" value="${param.year }" type="hidden"/>
					<input name="month" value="${param.month }" type="hidden"/>
					<%@ include file="../../common/pager.jsp"%>
				</form>
			</div>
		</div>
	</div>
</body>
</html>