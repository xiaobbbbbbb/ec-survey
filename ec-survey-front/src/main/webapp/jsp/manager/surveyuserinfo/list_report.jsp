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
					<li class="active">查勘员工作报告</li>
				</ul>
				<div class="btn-toolbar">
					<button class="btn" onfocus="this.blur()" onclick="href('${ctx}/surveyUserInfo/download');">导出</button>
				</div>
				<div class="well">
					<table class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="40px" class="td_left th_line">序号</th>
								<th>查勘员</th>
								<th>行驶里程</th>
								<th>车牌号</th>
								<th>首次上车时间</th>
								<th>末次离车时间</th>
								<th>累计时长</th>
								<th>出车次数</th>
								<th>超速记录</th>
								<th>违章记录</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ECPage.list }" var="dto" varStatus="sn">
								<tr>
									<td>${sn.count}</td>
									<td>${dto.name}</td>
									<td>${dto.totalMileMeter}</td>
									<td>${dto.carNo}</td>
									<td>${dto.maxTime}</td>
									<td>${dto.minTime}</td>
									<td>${dto.totalTime}</td>
									<td>${dto.surveySum}</td>
									<td>${dto.totalHypervelocity}</td>
									<td>${dto.totalFence}</td>
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