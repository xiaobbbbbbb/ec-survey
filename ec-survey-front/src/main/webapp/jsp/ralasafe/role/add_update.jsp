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
<script type="text/javascript" src="${ctx}/media/js/ztree3.5/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ztree3.5/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>
<script type="text/javascript">
	function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		//空实现，目的是在别的页面可以共用这棵树
	}
	function zTreeOnDblClick(event, treeId, treeNode) {
		//空实现，目的是在别的页面可以共用这棵树
	};

	//异步成功后
	function onAsyncSuccess(event, treeId, treeNode, msg) {
		if (treeId == "treeDate") {
			expandAll();
		}
	}

	function loadIds() {
		//加载选中的资源
		var treeObj = $.fn.zTree.getZTreeObj("treeDate"), nodes = treeObj
				.getCheckedNodes(true), array = new Array(); //用于保存 选中的那一条数据的ID
		for ( var i = 0; i < nodes.length; i++) {
			array.push(nodes[i].id); //将选中的值 添加到 array中 
		}
		$("#ids").val(array);
	}

	function submitForm() {
		loadIds();
		if ($.ec.validateType()) {
			var oldName = $.trim("${dto.name}"), $newName = $.trim($("#name")
					.val()), flag = false;
			if ($newName != oldName) //如果部门名称没有修改过则直接修改
			{
				var checkUrl = "${ctx}/ralasafe/role/check", param = {
					"name" : $newName
				};
				flag = $.ecAjax.getReturn({
					url : checkUrl,
					data : param
				});
			}
			if (!flag) {
				var url = "${ctx}/ralasafe/role/${dto.roleId == null ? 'add' : 'update'}";
				$.ec.ajaxSubmitForm({
					url : url,
					callback : frameHref
				});
			} else {
				$.alertModal({
					content : '角色已经存在！'
				});
			}
		}
	}

	function frameHref() {
		window.location.href = "${ctx}/ralasafe/role/list";
	}
</script>
</head>
<body>
	<!-- 页面头部 -->
	<c:choose>
		<c:when test="${sessionScope.USER_SESSION.loginName=='system'}">
			<%@ include file="../../admin/head.jsp"%>
		</c:when>
		<c:otherwise>
			<%@ include file="../../common/head.jsp"%>
		</c:otherwise>
	</c:choose>
	<div class="container-fluid">
		<div class="row-fluid">
			<div>
				<ul class="breadcrumb">
					<li><a href="#">权限管理</a> <span class="divider">/</span></li>
					<li><a href="${ctx}/ralasafe/role/list">角色管理</a> <span class="divider">/</span></li>
					<li class="active">${dto.roleId == null ? '添加' : '修改'}</li>
				</ul>
				<div class="well">
					<form id="form" class="form-horizontal">
						<input id="ids" validateType="notEmpty" tip="请选择权限菜单!" type="hidden" name="ids" /> 
						<input type="hidden" id="oldName" value="${dto.name}" />
						<input type="hidden" id="roleId" value="${dto.roleId}" name="roleId" />
						<table>
							<tr>
								<td align="left" valign="top" width="35%">
									<div>
										角色名称：<br /> <input id="name" name="name" validateType="notEmpty" tip="请输入角色名称!" value="${dto.name}" class="formText" type="text"
											style="width: 252px;" />
									</div>
									<div style="vertical-align: top;">
										角色描述：<br />
										<textarea
											style="height: 70px; min-height: 70px; max-height: 70px; width: 252px; min-width: 252px; max-width: 252px;"
											name="message">${dto.message}</textarea>
									</div>
								</td>
								<td align="left" valign="top" width="75%">权限菜单列表:
									<div id="roleTree">
										<input type="hidden" id="showUrl" value="false" /> <input type="hidden" id="parentId" />
										<jsp:include page="../resource/tree.jsp" flush="true" />
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div class="form-actions">
										<input id="btnSubmit" class="btn btn-primary" type="button" onclick="submitForm();"
											value="保 存" />&nbsp; <input id="btnCancel" class="btn" type="button" value="返 回"
											onclick="history.go(-1)" />
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>