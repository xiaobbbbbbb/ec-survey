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
		$("#group_select").change(function() { //服务机构信息
			var checkValue = $("#group_select").val(); //获取Select选择的Value
			$("#groupId").val(checkValue);
		});
	});

	function checkData(param) {
		var checkUrl = "${ctx}/ralasafe/org/check";
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
				var url = "${ctx}/ralasafe/org/${dto.orgId == null ? 'add' : 'update'}";
				$.ec.ajaxSubmitForm({
					url : url,
					callback : frameHref
				});
			} else {
				$.alertModal({
					content : '服务机构名称或编号已经存在！'
				});
			}
		}
	}

	function frameHref() {
		window.location.href = "${ctx}/ralasafe/org/list";
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
					<li><a href="${ctx}/ralasafe/org/list">服务机构管理</a> <span class="divider">/</span></li>
					<li class="active">${dto.orgId == null ? '添加' : '修改'}</li>
				</ul>
				<div class="well">
					<form id="form" class="form-horizontal">
						<input id="orgId" name="orgId" value="${dto.orgId }" type="hidden" />
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>选择所属公司：</label>
							<div class="controls">
							    <input id="parentName" style="cursor: pointer;" readonly="readonly" onclick="showMenu();" type="text" value="" maxlength="100" />
                                <input id="parentId" validateType="notEmpty" tip="请选择所属公司!" name="groupId" type="hidden" value="${dto.groupId}" />
							    <%@ include file="../group/select_group.jsp"%>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>服务机构名称：</label>
							<div class="controls">
								<input id="name" name="name" validateType="notEmpty" tip="请输入服务机构名称!" type="text" value="${dto.name}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>服务机构编码：</label>
							<div class="controls">
								<input id="code" name="code" validateType="notEmpty" tip="请输入服务机构编码!" type="text" value="${dto.code}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">选择所属服务机构：</label>
							<div class="controls">
							    <input id="parentName_" style="cursor: pointer;" readonly="readonly" onclick="showMenu_();" type="text" value="${dto.parentName}" maxlength="100" />
                                <input id="parentId_" name="parentId" type="hidden" value="${dto.parentId}" />
							    <%@ include file="select_org.jsp"%>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>选择所属城市：</label>
							<div class="controls">
							    <input id="cityName" style="cursor: pointer;" readonly="readonly" onclick="showCity();" type="text" value="${cityName}" maxlength="100" />
                                <input id="cityId" name="cityId" type="hidden" value="${dto.cityId}" />
							    <%@ include file="select_city.jsp"%>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">备注：</label>
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