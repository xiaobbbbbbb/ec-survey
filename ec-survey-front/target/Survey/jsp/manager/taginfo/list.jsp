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
					<li class="active">身份识别卡信息</li>
				</ul>
				<div class="btn-toolbar">
					<button class="btn btn-primary" onfocus="this.blur()" onclick="href('${ctx}/tagInfo/addUI');">
						添加身份识别卡</button>
					<button class="btn" onfocus="this.blur()" onclick="history.go(-1)">返回</button>
					<div class="btn-group"></div>
				</div>
				<div class="well">
					<table id="list" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="40px">序号</th>
								<th>电子标签号</th>
								<th width="100px">查勘员姓名</th>
								<th width="130px">车牌号</th>
								<th width="130px">更新时间</th>
								<th width="90px">是否有效</th>
								<th width="40px">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ECPage.list }" var="dto" varStatus="sn">
								<tr>
									<td>${sn.count}</td>
									<td>${dto.serialNo}</td>
									<td>${dto.surveyUserName}</td>
									<td>${dto.carNo}</td>
									<td class="td_center"><fmt:formatDate value="${dto.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
									<td class="td_center"><c:choose>
											<c:when test="${dto.disabled==1}">有效</c:when>
											<c:otherwise>
												<span>无效</span>
											</c:otherwise>
										</c:choose></td>
									<td class="td_center"><a href="${ctx}/tagInfo/updateUI?id=${dto.id}"><i class="icon-pencil"></i></a> <a
										onfocus="this.blur()" role="button" href="javascript:void(0)"
										onclick="$.ec.deleteData({url : '${ctx}/tagInfo/delete?userId=${dto.userId}&carId=${dto.carId}',param : {'ids':${dto.id}}});">
											<i class="icon-remove"></i>
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<form action="${ctx}/tagInfo/list" id="pageForm">
						<%@ include file="../../common/pager.jsp"%>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>