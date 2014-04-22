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
<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>

<link href="${ctx}/media/js/treeTable1.4.2/vsStyle/jquery.treeTable.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/treeTable1.4.2/jquery.treeTable.js"></script>
<script type="text/javascript">
	$(function() {
		var option = {
			theme : 'vsStyle',
			expandLevel : 1,
			beforeExpand : function($treeTable, id) {
				//判断id是否已经有了孩子节点，如果有了就不再加载，这样就可以起到缓存的作用
				if ($('.' + id, $treeTable).length) {
					return;
				}
				var json=$.ecAjax.getReturnJson({
					url:'${ctx}/ralasafe/group/treeTable?pid='+id
				});
				$.each(json,function(index,value){
					var hasChild="";
					if(value.isLeaf>0)
					{
						hasChild=' hasChild="true"';
					}
					var tr='<tr id="'+value.groupId+'" pId="'+value.parentId+'"'+hasChild+'><td>'+value.name+'</td><td>'+value.code+'</td><td class="td_center">'+value.createTimeStr+'</td><td class="td_center"><a onfocus="this.blur()"'+
						'href="${ctx}/ralasafe/group/updateUI?id='+value.groupId+'"><i class="icon-pencil"></i></a> <a'+
						' onfocus="this.blur()" role="button" href="javascript:void(0)"'+
						' onclick="del('+value.groupId+');"><i class="icon-remove"></i></a></td>'+
				        '</tr>"';
				        
					$treeTable.addChilds(tr);
		        });
			}
		};
		$('#treeTable').treeTable(option);
	});
	
	function del(id)
	{
		$.ec.deleteData({
			url : '${ctx}/ralasafe/group/delete',
			param : {'ids':id}
		});
	}
</script>
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../../admin/head.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<div>
				<ul class="breadcrumb">
					<li><a href="#">机构管理</a> <span class="divider">/</span></li>
					<li class="active">公司管理</li>
				</ul>
				<div class="btn-toolbar">
					<button class="btn btn-primary" onfocus="this.blur()"
						onclick="href('${ctx}/ralasafe/group/addUI');">添加公司</button>
				</div>
				<div class="well" id="list">
					<table id="treeTable" class="table table-striped table-bordered table-condensed">
						<tr>
							<td class="th_title">公司名称</td>
							<td class="th_title" width="150px">公司编码</td>
							<th width="130px">创建时间</th>
							<td class="th_title" width="50px">操作</td>
						</tr>
						<c:forEach items="${dtos}" var="dto" varStatus="sn">
							<tr id="${dto.groupId}" pId="${dto.parentId}"<c:if test="${dto.isLeaf>0}"> hasChild="true"</c:if>>
								<td>${dto.name}</td>
								<td>${dto.code}</td>
								<td class="td_center">${dto.createTimeStr}</td>
								<td class="td_center"><a onfocus="this.blur()"
									href="${ctx}/ralasafe/group/updateUI?id=${dto.groupId}"><i class="icon-pencil"></i></a> <a
									onfocus="this.blur()" role="button" href="javascript:void(0)"
									onclick="$.ec.deleteData({url : '${ctx}/ralasafe/group/delete',param : {'ids':${dto.groupId}}});">
										<i class="icon-remove"></i>
								</a></td>
							</tr>
						</c:forEach>
					</table>
					<%-- <table id="list" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="40px">序号</th>
								<th>公司名称</th>
								<th width="100px">公司编码</th>
								<th width="130px">更新时间</th>
								<th width="60px">是否有效</th>
								<th width="40px">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ECPage.list }" var="dto" varStatus="sn">
								<tr>
									<td>${sn.count}</td>
									<td>${dto.name}</td>
									<td>${dto.code}</td>
									<td class="td_center">${dto.updateTimeStr}</td>
									<td class="td_center"><c:choose>
											<c:when test="${dto.disabled==1}">有效</c:when>
											<c:otherwise>
												<span class="red">无效</span>
											</c:otherwise>
										</c:choose></td>
									<td class="td_center"><a onfocus="this.blur()"
										href="${ctx}/ralasafe/group/updateUI?id=${dto.groupId}"><i class="icon-pencil"></i></a> <a
										onfocus="this.blur()" role="button" href="javascript:void(0)"
										onclick="$.ec.deleteData({url : '${ctx}/ralasafe/group/delete',param : {'ids':${dto.groupId}}});">
											<i class="icon-remove"></i>
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table> --%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>