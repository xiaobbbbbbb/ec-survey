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
<script type="text/javascript" src="${ctx}/media/js/jquery.json-2.3.min.js"></script>
<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>
<script type="text/javascript">
	$(function() {
		$("#startTime,#endTime").datepickerStyle();
	});
	
	function showImage(car_report_id){
		if(car_report_id == null){
			return;
		}
		var url_="${ctx}/carReport/carReportImageUI?id="+car_report_id+"&time="+Math.random();		
		$.alertModal({
			title: '行车图片信息',
			width: '600px',
			url : url_
		}); 
	}
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
					<li class="active">车辆运行明细报告</li>
				</ul>
				<div class="btn-toolbar">
					<button class="btn" onfocus="this.blur()" onclick="href('${ctx}/carReport/download');">导出</button>
					<button class="btn" onfocus="this.blur()" onclick="history.go(-1)">返回</button>
				</div>
				<form class="well form-inline" style="text-align: center;">
					<input type="text" id="startTime" name="startTime" class="input-small" style="cursor: pointer;" readonly="readonly"
						placeholder="起始时间" value="${startTime}"> <input type="text" id="endTime" name="endTime"
						class="input-small" style="cursor: pointer;" readonly="readonly" placeholder="终止时间" value="${endTime}">
					<button type="submit" class="btn">搜索</button>
				</form>
				<div class="well">
					<table  id="list" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="40px">序号</th>
								<th>车牌</th>
								<th>开始时间</th>
								<th>结束时间</th>
								<th>查勘员</th>
								<th>里程（KM）</th>
								<th>出发地点</th>
								<th>结束地点</th>
								<th width="80px">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ECPage.list }" var="dto" varStatus="sn">
								<tr>
									<td>${sn.count}</td>
									<td>${dto.carNo}</td>
									<td><fmt:formatDate value="${dto.createTime}"
											pattern="yyyy-MM-dd HH:mm" /></td>
									<td><fmt:formatDate value="${dto.updateTime}"
											pattern="yyyy-MM-dd HH:mm" /></td>
									<td>${dto.suName}</td>
									<td>${dto.mileMeter/1000}</td>
									<td>${dto.startAddress}</td>
									<td>${dto.endAddress}</td>
									<td class="td_center">
									    <a onfocus="this.blur()" href="javascript:void(0)" onclick="showImage('${dto.id}');">照片</a>
										<a href="${ctx}/locate/orbit?carId=${dto.carId}&carSportId=${dto.id}">轨迹 </a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<form action="${ctx}/carReport/listReport" id="pageForm">
					<input name="startTime" value="${param.startTime }" type="hidden"/>
					<input name="endTime" value="${param.endTime }" type="hidden"/>
					<input name="carId" value="${param.carId }" type="hidden"/>
					<input name="month" value="${param.month }" type="hidden"/>
					<%@ include file="../../common/pager.jsp"%>
				</form>
			</div>
		</div>
	</div>
</body>
</html>