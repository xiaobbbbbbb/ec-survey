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
<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>
<script type="text/javascript">
	$(function() {
		var $pid = "${dto.parentId}";
		if ($pid.length > 0) {
			$("#parentId").val($pid);
		}
	});

	function check() {
		return true;
	}

	function submitForm() {
		if (check()) {
			var url = "${ctx}/ralasafe/resource/${dto.resourceId == null ? 'add' : 'update'}";
			$.ec.ajaxSubmitForm({
				url : url,
				callback : frameHref
			});
		}
	}

	function frameHref() {
		window.parent.location.href = "${ctx}/ralasafe/resource/list";
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
					<li><a href="#">权限管理</a> <span class="divider">/</span></li>
					<li><a href="${ctx}/ralasafe/resource/list">权限菜单</a> <span class="divider">/</span></li>
					<li class="active">${dto.resourceId == null ? '添加' : '修改'}</li>
				</ul>
				<div class="well">
					<form id="form" class="form-horizontal">
						<input type="hidden" name="resourceId" value="${dto.resourceId}" /> <input type="hidden"
							id="parentId" name="parentId" value="${param.pid != null ?param.pid:'0'}" /> <input
							type="hidden" name="isLeaf" value="${dto.isLeaf}" />

						<div class="control-group">
							<label class="control-label"><span class="red">*</span>菜单名称:</label>
							<div class="controls">
								<input id="name" name="name" type="text" value="${dto.name}" maxlength="100" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label"><span class="red">*</span>地址:</label>
							<div class="controls">
								<input id="url" name="url" type="text" value="${dto.url}" maxlength="100" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label"><span class="red">*</span>排序号:</label>
							<div class="controls">
								<input id="level" name="level" type="text" value="${dto.level}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>图标:</label>
							<div class="controls">
								<input id="icon" name="icon" type="text" value="${dto.icon}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>资源类型:</label>
							<div class="controls">
								<select name="type">
									<option value="0" <c:if test="${dto.type==0}">selected="selected"</c:if>>系统资源</option>
									<option value="1" <c:if test="${dto.type==1}">selected="selected"</c:if>>用户资源</option>
									<option value="2" <c:if test="${dto.type==2 || dto.type==null}">selected="selected"</c:if>>系统和用户资源</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>备注:</label>
							<div class="controls">
								<textarea name="message">${dto.message}</textarea>
							</div>
						</div>


						<div class="form-actions">
							<input id="btnSubmit" class="btn btn-primary" type="button" onclick="submitForm();"
								value="保 存" />&nbsp; <input id="btnCancel" class="btn" type="button" value="返 回"
								onclick="history.go(-1)" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal small hide fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">系统提示</h3>
		</div>
		<div class="modal-body">

			<p class="error-text">
				<i class="icon-warning-sign modal-icon"></i>服务机构或编号已经存在！
			</p>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
		</div>
	</div>
</body>
</html>