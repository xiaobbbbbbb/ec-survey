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
	function checkData(param) {
		var checkUrl = "${ctx}/ralasafe/group/check";
		return flag = $.ecAjax.getReturn({
			url : checkUrl,
			data : param
		});
	}

	function submitForm() {
		if ($.ec.validateType()) {
			var oldName = $.trim("${dto.name}"), $newName = $.trim($("#name")
					.val()), oldCode = $.trim("${dto.code}"), $newCode = $
					.trim($("#code").val()), flag = false;
			if ($newName != oldName) //如果部门名称没有修改过则直接修改
			{
				var param = {
					"name" : $newName,
					"code" : null
				};
				flag = checkData(param);
			}
			if ($newCode != oldCode) {
				var param = {
					"name" : null,
					"code" : $newCode
				};
				flag = checkData(param);
			}
			if (!flag) {
				var url = "${ctx}/ralasafe/group/${dto.groupId == null ? 'add' : 'update'}";
				$.ec.ajaxSubmitForm({
					url : url,
					callback : frameHref
				});
			} else {
				$.alertModal({
					content : '公司名称或编号已经存在！'
				});
			}
		}
	}

	function frameHref() {
		window.location.href = "${ctx}/ralasafe/group/list";
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
					<li><a href="${ctx}/ralasafe/group/list">公司管理</a> <span class="divider">/</span></li>
					<li class="active">${dto.groupId == null ? '添加' : '修改'}</li>
				</ul>
				<div class="well">
					<form id="form" class="form-horizontal">
						<input id="groupId" name="groupId" value="${dto.groupId }" type="hidden" />
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>公司名称:</label>
							<div class="controls">
								<input id="name" name="name" validateType="notEmpty" tip="请输入公司名称!" type="text" value="${dto.name}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>公司编码:</label>
							<div class="controls">
								<input id="code" name="code" validateType="notEmpty" tip="请输入公司编码!" type="text" value="${dto.code}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>选择所属公司：</label>
							<div class="controls">
							    <input id="parentName" style="cursor: pointer;" readonly="readonly" onclick="showMenu();" type="text" value="${dto.parentName}" maxlength="100" />
                                <input id="parentId" name="parentId" type="hidden" value="${dto.parentId}" />
							    <%@ include file="select_group.jsp"%>
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
</body>
</html>
