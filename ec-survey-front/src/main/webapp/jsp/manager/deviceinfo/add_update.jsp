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
		var checkUrl = "${ctx}/deviceInfo/check";
		return flag = $.ecAjax.getReturn({
			url : checkUrl,
			data : param
		});
	}

	function submitForm() {
		if ($.ec.validateType()) {
			var oldName = $.trim("${dto.serialNo}"), $newName = $.trim($(
					"#serialNo").val()), oldCode = $.trim("${dto.code}"), $newCode = $
					.trim($("#code").val()), flag = false;
			if ($newName != oldName) //如果部门名称没有修改过则直接修改
			{
				var param = {
					"serialNo" : $newName,
					"code" : null
				};
				flag = checkData(param);
			}
			if ($newCode != oldCode) {
				var param = {
					"serialNo" : null,
					"code" : $newCode
				};
				flag = checkData(param);
			}
			if (!flag) {
				var url = "${ctx}/deviceInfo/${dto.id == null ? 'add' : 'update'}";
				$.ec.ajaxSubmitForm({
					url : url,
					callback : frameHref
				});
			} else {
				$.alertModal({
					content : '设备序列号或编号已经存在！'
				});
			}
		}
	}

	function frameHref() {
		window.location.href = "${ctx}/deviceInfo/list";
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
					<li><a href="${ctx}/deviceInfo/list">设备管理</a> <span class="divider">/</span></li>
					<li class="active">${dto.id == null ? '添加' : '修改'}</li>
				</ul>
				<div class="well">
					<form id="form" class="form-horizontal">
						<input name="id" value="${dto.id }" type="hidden" />
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>设备序列号:</label>
							<div class="controls">
								<input id="serialNo" name="serialNo" validateType="notEmpty" tip="请输入设备序列号!" type="text" value="${dto.serialNo}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>设备编号:</label>
							<div class="controls">
								<input id="code" name="code" type="text" validateType="notEmpty" tip="请输入设备编号!" value="${dto.code}" maxlength="100" />
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
