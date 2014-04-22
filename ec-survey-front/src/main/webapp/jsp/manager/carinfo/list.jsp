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
<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../../common/head.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<div>
				<ul class="breadcrumb">
					<li><a href="#">基本信息</a> <span class="divider">/</span></li>
					<li class="active">查勘车辆信息</li>
				</ul>
				<div class="btn-toolbar">
					<button class="btn btn-primary" onfocus="this.blur()" onclick="href('${ctx}/carInfo/addUI');">添加查勘车辆</button>
					<button class="btn" onfocus="this.blur()" onclick="history.go(-1)">返回</button>
					<div class="btn-group"></div>
				</div>
				<div class="well">
					<table id="list" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="40px">序号</th>
								<th width="60px">车牌号</th>
								<th>所属区域</th>
								<th width="90px">年检到期时间</th>
								<th width="110px">创建时间</th>
								<th width="60px">是否有效</th>
								<th width="60px">状态</th>
								<th width="40px">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ECPage.list }" var="dto" varStatus="sn">
								<tr>
									<td>${sn.count}</td>
									<td>${dto.carNo}</td>
									<td>${dto.areaName}</td>
									<td class="td_center"><fmt:formatDate value="${dto.inspectionTime}"
											pattern="yyyy-MM-dd" /></td>
									<td class="td_center"><fmt:formatDate value="${dto.createTime}"
											pattern="yyyy-MM-dd HH:mm" /></td>
									<td><c:choose>
											<c:when test="${dto.disabled==1}">有效</c:when>
											<c:otherwise>
												<span class="red">无效</span>
											</c:otherwise>
										</c:choose></td>
									<td id="sta">
										<c:choose>
											<c:when test="${dto.status==2}"><a href="javascript:update('${dto.id }','${dto.status}')">离线</a></c:when>
											<c:otherwise>
												<a href="javascript:update('${dto.id }','${dto.status}')" >在线</a>
											</c:otherwise>
										</c:choose>
									</td>
									<td class="td_center"><a href="${ctx}/carInfo/updateUI?id=${dto.id}"><i class="icon-pencil"></i></a> <a
										onfocus="this.blur()" role="button" href="javascript:void(0)"
										onclick="$.ec.deleteData({url : '${ctx}/carInfo/delete?deviceId=${dto.deviceId}',param : {'ids':${dto.id}}});">
											<i class="icon-remove"></i>
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<form action="${ctx}/carInfo/list" id="pageForm">
						<%@ include file="../../common/pager.jsp"%>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function update(id,status){
			status=status==2?0:2;
			var ctx='${ctx}';
			$.alertModal({
				content :'确定要修改查勘车的状态吗？',
				isConfirmBtn:true,
				confirmCallback :function (){
					$.ecAjax.getReturnJson({
						url:ctx+'/carInfo/updateStatus',
						data :{"id":id,"status":status}
					});
					window.location.reload();
				}
			});
		}

</script>
</html>