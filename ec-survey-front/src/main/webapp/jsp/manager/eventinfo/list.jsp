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
	//绑定车辆
	function bindCarInfoUI(fence_id) {
		var url_='${ctx}/locate/bindCarInfoUI?id='+fence_id;
		$.alertModal({
			title: '电子围栏绑定',
			url:url_,
			width : '650px',
			isConfirmBtn :true,
			confirmCallback: submitBind
		}); 
		
	}
	function cancel(id) {
		var eventInfoId=id;
		$.alertModal({
			title: '取消案件',
			width : '400px',
			isHtml :true,
			content : '<div>取消原因：<textarea type="text" id="cancelreason"/></div><div>取消人:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="cancelpeople" type="text"/></div>',
			isConfirmBtn :true,
			confirmCallback: function submitBind(){
				var status=3;
				var cancelreason=$("#cancelreason").val();
				var cancelpeople=$("#cancelpeople").val();
				var flag=true;
				if (cancelreason == 0) {
					alert("取消原因不能为空");
					flag=false;
					return;
				}
				if (cancelpeople == 0) {
					alert("取消人不能为空");
					flag=false;
					return;
				}
				if(flag){
					
					var url = "${ctx}/locate/modifyEventInfo", param = {
						"eventInfoId" : eventInfoId,
						"status" :status,
						"cancelreason" :cancelreason,
						"cancelpeople" :cancelpeople
					};
					$.ajax({
						url : url,
						type : 'POST',
						data : param,
						success : function(data) {
							if (data.status == 200) {
								alert('取消报案成功！');
							} else {
								alert('取消报案失败！');
							}
							document.location.reload();
						}
					}); 
				}
			}
		}); 
	}
	

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
					<li class="active">案件查询</li>
				</ul>
				<div class="btn-toolbar">
					<button class="btn" onfocus="this.blur()" onclick="href('${ctx}/eventInfo/download');">导出</button>
					<button class="btn" onfocus="this.blur()" onclick="history.go(-1)">返回</button>
				</div>
				<form class="well form-inline" style="text-align: center;">
					选择报案时间段：<input type="text" value="${param.startTime }" id="startTime" name="startTime"
						class="input-small" style="cursor: pointer;" readonly="readonly" placeholder="开始时间"> <input
						type="text" value="${param.endTime }" id="endTime" name="endTime" class="input-small"
						style="cursor: pointer;" readonly="readonly" placeholder="结束时间">
					<button type="submit" class="btn">搜索</button>
				</form>
				<div class="well">
					<ul class="nav nav-tabs" style="margin-bottom: 0px; border-bottom-width: 0px;">
						<li class="active"><a href="${ctx}/eventInfo/list">未调度</a></li>
						<li><a href="${ctx}/eventInfo/listScheduledList">已调度</a></li>
					</ul>
					<table id="list" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="4%">序号</th>
								<th width="10%">报案号</th>
								<th width="110px">报案时间</th>
								<th width="5%">车牌号</th>
								<th width="6%">联系电话</th>
								<th>区域</th>
								<th width="5%">录单员</th>
								<th width="5%">查勘员</th>
								<th>详细地址</th>
								<th width="5%">案件状态</th>
								<th width="100px">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ECPage.list }" var="dto" varStatus="sn">
								<tr>
									<td>${sn.count}</td>
									<td>${dto.no}</td>
									<td class="td_center"><fmt:formatDate value="${dto.createTime}"
											pattern="yyyy-MM-dd HH:mm" /></td>
									<td>${dto.carNo}</td>
									<td>${dto.phoneNo}</td>
									<td>${dto.areaInfoName}</td>
									<td>${dto.opUserName}</td>
									<td>${dto.surveyUserName}</td>
									<td>
									<c:choose>
										<c:when test="${dto.status==3}">${dto.address}</c:when>
											<c:otherwise><a href="${ctx}/locate/eventLocate?eventInfoId=${dto.id}">${dto.address}</a></c:otherwise>
									</c:choose>
									</td>
									<td class="td_center"><c:if test="${dto.status==0}">
											<span class="blue">待调度</span>
										</c:if> <c:if test="${dto.status==1}">已调度</c:if> <c:if test="${dto.status==2}">
											<span class="red">已完成</span>
										</c:if> <c:if test="${dto.status==3}">
											<span class="red" title="取消原因：${dto.cancelreason}取消人：${dto.cancelpeople}">已取消</span>
										</c:if> <c:if test="${dto.status==4}">
											<span class="red">已终结</span>
										</c:if></td>
									<td class="td_center"><c:if test="${dto.status!=1 && dto.status!=3 && dto.status!=4}">
											<a href="${ctx}/locate/eventLocate?eventInfoId=${dto.id}">调度</a>
										</c:if> <c:if test="${dto.status!=3 && dto.status!=4}">
											<a href="javascript:cancel('${dto.id}');">取消报案</a>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<form action="${ctx}/eventInfo/list" id="pageForm">
						<input name="startTime" value="${param.startTime }" type="hidden" /> <input name="endTime"
							value="${param.endTime }" type="hidden" />
						<%@ include file="../../common/pager.jsp"%>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>