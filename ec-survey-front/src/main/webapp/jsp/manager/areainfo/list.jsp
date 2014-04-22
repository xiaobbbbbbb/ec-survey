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
<link href="${ctx}/media/js/ztree3.5/css/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/webjars/bootstrap/2.3.2/js/bootstrap.js"></script>
<script src="${ctx}/media/js/ztree3.5/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>
<script type="text/javascript">

	function updateUI($selectNodeid,$parentName) {
		if ($selectNodeid.length > 0) {
			href("${ctx}/areaInfo/updateUI?parentName="+$parentName+"&id=" + $selectNodeid);
		}
	}

	function del($selectNodeid) {
		$.ec.deleteData({
			url : "${ctx}/areaInfo/delete", //删除数据的地址
			param : {
				"ids" : $selectNodeid
			}
		});
			 
	}
</script>
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../../common/head.jsp"%>
	<ul class="breadcrumb" style="margin-left: 20px; margin-right: 20px; margin-bottom: 0px;">
		<li><a href="#">基本信息</a> <span class="divider">/</span></li>
		<li class="active">区域分组管理</li>
	</ul>

	<div class="container-fluid" style="min-width: 1100px;">
		<div class="btn-toolbar">
			<button class="btn btn-primary" onfocus="this.blur()" onclick="href('${ctx}/areaInfo/addUI')">添加区域</button>
			<!-- <button class="btn" onfocus="this.blur()" onclick="updateUI();">修改</button>
			<button class="btn" onfocus="this.blur()" onclick="del();">删除</button> -->
			<div class="btn-group"></div>
		</div>
		<div class="well">
			<table  id="list" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr> 
						<th width="40px">序号</th>
						<th>分组名称</th>
						<th>父区名称</th>
						<th>绑定车辆</th>
						<th width="80px">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ECPage.list }" var="dto" varStatus="sn">
						<tr>
							<td>${sn.count}</td>
							<td>${dto.name}</td>
							<td>${dto.parentName}</td>
							<td>
								<c:forEach items="${dto.carSet }" var="carNoList" varStatus="st"  >
									&nbsp;${carNoList}
									<c:if test="${fn:length(dto.carSet)!=st.count}">
									 ，
									</c:if>
								</c:forEach>
							</td>
							<td class="td_center">
								<a onfocus="this.blur()" onclick="updateUI('${dto.id}','${dto.parentName}');"><i class="icon-pencil"></i></a> 
								<a onfocus="this.blur()"  onclick="del('${dto.id}');"><i class="icon-remove"></i></a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<form action="${ctx}/areaInfo/list" id="pageForm">
				<input name="startTime" value="${param.startTime }" type="hidden"/>
				<input name="endTime" value="${param.endTime }" type="hidden"/>
				<input name="name" value="${ec:decode(param.name)}" type="hidden" />
				<input name="parentName" value="${ec:decode(param.parentName)}" type="hidden"/>
				<%@ include file="../../common/pager.jsp"%>
			</form>
			<!-- 树性 -->
		  	<%-- <input type="hidden" id="parentName" />
			<jsp:include page="tree.jsp" flush="true" /> --%>
		</div>
	</div>
</body>
</html>