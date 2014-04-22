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

<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js" type="text/javascript"></script>

<script type="text/javascript">
	$(function() {
	    $("#driveToTime").datepickerStyle();
	});

	function submitForm() {
		if ($.ec.validateType()) {
			var oldName = $.trim("${dto.name}"), $newName = $.trim($("#name")
					.val()), flag = false;
			if ($newName != oldName) //如果部门名称没有修改过则直接修改
			{
				var checkUrl = "${ctx}/surveyUserInfo/check", param = {
					"name" : $newName
				};
				flag = $.ecAjax.getReturn({
					url : checkUrl,
					data : param
				});
			}
			if (!flag) {
				var url = "${ctx}/surveyUserInfo/${dto.id == null ? 'add' : 'update'}";
				$.ec.ajaxSubmitForm({
					url : url,
					callback : frameHref
				});
			} else {
				$.alertModal({
					content : '查勘员名称已经存在！'
				});
			}
		}
	}

	function frameHref() {
		window.location.href = "${ctx}/surveyUserInfo/list";
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
					<li><a href="${ctx}/surveyUserInfo/list">查勘员信息</a> <span class="divider">/</span></li>
					<li class="active">${dto.id == null ? '添加' : '修改'}</li>
				</ul>
				<div class="well">
					<form id="form" class="form-horizontal">
						<input name="id" value="${dto.id }" type="hidden" />
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>选择所属区域：</label>
							<div class="controls">
							    <%@ include file="../areainfo/select_area.jsp"%>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>查勘员名称：</label>
							<div class="controls">
								<input id="name" name="name" validateType="notEmpty" tip="请输入查勘员名称!" type="text" value="${dto.name}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>联系电话：</label>
							<div class="controls">
								<input id="phoneNo" name="phoneNo" validateType="notEmpty" tip="请输入查勘员联系电话!" type="text" value="${dto.phoneNo}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>驾驶证号：</label>
							<div class="controls">
								<input id="driveNo" name="driveNo" validateType="notEmpty" tip="请输入驾驶证号!" type="text" value="${dto.driveNo}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>驾驶证到期日：</label>
							<div class="controls">
								<input id="driveToTime" style="cursor: pointer;" readonly="readonly" validateType="notEmpty" tip="请输入驾驶证到期日!" name="driveToTime" type="text" value="<fmt:formatDate value="${dto.driveToTime}"
											pattern="yyyy-MM-dd" />"
									maxlength="100" />
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