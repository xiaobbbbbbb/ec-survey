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
<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css"
	rel="stylesheet" />
<script src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js" type="text/javascript"></script>
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
		<div class="row-fluid">
			<div>
				<ul class="breadcrumb">
					<li><a href="#">统计报表</a> <span class="divider">/</span></li>
					<li class="active">围栏告警统计</li>
				</ul>
				<div class="btn-toolbar">
					<button class="btn" onfocus="this.blur()" onclick="href('${ctx}/fenceAlarmInfo/download');">导出</button>
					<button class="btn" onfocus="this.blur()" onclick="history.go(-1)">返回</button>
				</div>
				<form class="well form-inline" style="text-align: center;" method="post">
					<select id="typeName" name="typeName">
						<option value="">全部</option>
						<option value="1" <c:if test="${param.typeName == 1}"> selected='selected'
						</c:if>>进入报警</option>
						<option value="0" <c:if test="${param.typeName == 0}"> selected='selected'
						</c:if>>出围栏报警</option>
						<option value="2" <c:if test="${param.typeName == 2}"> selected='selected'
						</c:if>>进出报警</option>
					</select> 
					<input type="text" id="startTime" name="startTime" class="input-small" style="cursor: pointer;"
						readonly="readonly" placeholder="告警开始时间" value="${param.startTime}"> <input type="text" id="endTime"
						name="endTime" class="input-small" style="cursor: pointer;" readonly="readonly" placeholder="告警结束时间"
						value="${param.endTime}"> <input type="text" id="carNo" name="carNo" class="input-small"
						style="cursor: pointer;" placeholder="车牌" value="${param.carNo}"> <input type="text" id="surveyName"
						name="surveyName" class="input-small" style="cursor: pointer;" placeholder="查勘员" value="${param.surveyName}"> <input
						type="text" id="surveyTel" name="surveyTel" class="input-small" style="cursor: pointer;" placeholder="手机号"
						value="${param.surveyTel}">
					<button type="submit" class="btn">搜索</button>
				</form>
			</div>
		</div>
		<div class="well">
			<table id="list" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr id="th">
						<th width="40px">序号</th>
						<th width="120px">告警类型</th>
						<th width="180px">告警时间</th>
						<th width="90px">车牌号</th>
						<th width="120px">设备号</th>
						<th width="130px">查勘员</th>
						<th>手机号</th>
						<th>告警位置</th>
						<th width="40px">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ECPage.list }" var="dto" varStatus="sn">
						<tr>
							<td>${sn.count}</td>
							<td>${dto.typeStr}</td>
							<td>${dto.createTimes}</td>
							<td>${dto.carNo}</td>
							<td>${dto.code}</td>
							<td>${dto.surveyName}</td>
							<td>${dto.surveyTel}</td>
							<td>${dto.address}</td>
							<td class="td_center"><a href="${ctx}/locate/orbit?carId=${dto.carId}">轨迹</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<form action="${ctx}/fenceAlarmInfo/listReport" id="pageForm">
				<input name="startTime" value="${param.startTime }" type="hidden"/>
				<input name="endTime" value="${param.endTime }" type="hidden"/>
				<input name="carNo" value="${ec:decode(param.carNo)}" type="hidden" />
				<input name="surveyName" value="${ec:decode(param.surveyName)}" type="hidden"/>
				<input type="hidden" name="surveyTel" value="${ec:decode(param.surveyTel)}" />
				<input name="typeName" value="${ec:decode(param.typeName)}" type="hidden"/>
				<%@ include file="../../common/pager.jsp"%>
			</form>
		</div>
	</div>
</body>
</html>