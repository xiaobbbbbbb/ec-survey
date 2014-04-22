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
<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css"rel="stylesheet" />
<script src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2&ak=F9e138a00e815825002d53430326cac7"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/LuShu/1.2/src/LuShu_min.js"></script>
<script type="text/javascript">

	
	$(function() {
		$("#startTime,#endTime").datepickerStyle();
	});
	
	function showMarInfo(id){
		if(id == null){
			return;
		}
		var url_="${ctx}/markInfo/markInfoMapUI?id="+id;	
		
		$.alertModal({
			title: '标注点信息',
			height: '480px', 
			width: '800px',
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
					<li><a href="#">基本信息</a> <span class="divider">/</span></li>
					<li class="active">标注点信息管理</li>
				</ul>
				<div class="btn-toolbar">
					<button class="btn" onfocus="this.blur()" onclick="href('${ctx}/markInfo/download');">导出</button>
					<button class="btn" onfocus="this.blur()" onclick="history.go(-1)">返回</button>
				</div>
				<form class="well form-inline" style="text-align: center;" method="post">
					<input type="text" id="typeName" name="typeName" class="input-small" style="cursor: pointer;" placeholder="标注分类"
						value="${param.typeName}"> <input type="text" id="startTime" name="startTime" class="input-small"
						style="cursor: pointer;" readonly="readonly" placeholder="开始时间" value="${param.startTime}"> <input
						type="text" id="endTime" name="endTime" class="input-small" style="cursor: pointer;" readonly="readonly"
						placeholder="结束时间" value="${param.endTime}">
					<button type="submit" class="btn">搜索</button>

				</form>

				<div class="well">
					<table id="list" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="40px">序号</th>
								<th>标注名称</th>
								<th>标注类型</th>
								<th>标记点详细地址</th>
								<th width="90px">标注状态</th>
								<th width="120px">创建时间</th>
								<th width="80px">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ECPage.list }" var="dto" varStatus="sn">
								<tr>
									<td>${sn.count}</td>
									<td>${dto.name}</td>
									<td>${dto.typeName}</td>
									<td>${dto.address}</td>
									<td class="td_center">${dto.statusType}</td>
									<td class="td_center">${dto.createTimes}</td>
									<td class="td_center"><a onfocus="this.blur()" role="button" href="javascript:void(0)"
										onclick="$.ec.deleteData({url : '${ctx}/markInfo/delete',param : {'ids':${dto.id}}});"> <i
											class="icon-remove"></i></a>
											<a href="${ctx}/markInfo/markInfoMapUI?id=${dto.id}">查看地图</a>
											</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<form action="${ctx}/markInfo/list" id="pageForm">
						<input name="startTime" value="${param.startTime }" type="hidden" /> <input name="endTime"
							value="${param.endTime }" type="hidden" /> <input name="typeName" value="${ec:decode(param.typeName)}"
							type="hidden" />
						<%@ include file="../../common/pager.jsp"%>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>