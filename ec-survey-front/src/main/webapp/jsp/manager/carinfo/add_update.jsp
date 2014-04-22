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

<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css"
	type="text/css" rel="stylesheet" />
<script src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"
	type="text/javascript"></script>

<script type="text/javascript">
	$(function() {
		$("#inspectionTime").datepickerStyle();

		$("#device_select").change(function() { //车辆信息
			var checkValue = $("#device_select").val(); //获取Select选择的Value
			$("#deviceId").val(checkValue);
		});
	});

	function submitForm() {
		if ($.ec.validateType()) {
			var oldName = $.trim("${dto.carNo}"), $newName = $.trim($("#carNo")
					.val()), flag = false;
			if ($newName != oldName) //如果部门名称没有修改过则直接修改
			{
				var checkUrl = "${ctx}/carInfo/check", param = {
					"carNo" : $newName
				}, flag = $.ecAjax.getReturn({
					url : checkUrl,
					data : param
				});
			}
			if (!flag) {
				var url = "${ctx}/carInfo/${dto.id == null ? 'add' : 'update'}";
				$.ec.ajaxSubmitForm({
					url : url,
					callback : frameHref
				});
			} else {
				$.alertModal({
					content : '车牌号已经存在！'
				});
			}
		}
	}

	function frameHref() {
		window.location.href = "${ctx}/carInfo/list";
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
					<li><a href="${ctx}/carInfo/list">查勘车辆信息</a> <span class="divider">/</span></li>
					<li class="active">${dto.id == null ? '添加' : '修改'}</li>
				</ul>
				<div class="well">
					<form id="form" class="form-horizontal">
						<input name="id" value="${dto.id }" type="hidden" /> <input name="status"
							value="${dto.status>0? dto.status:0 }" type="hidden" />
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>车牌号:</label>
							<div class="controls">
								<input id="carNo" name="carNo" type="text" validateType="notEmpty" tip="请输入车牌号!" value="${dto.carNo}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>选择设备:</label>
							<div class="controls">
								<input id="deviceId" name="deviceId" type="hidden" validateType="notEmpty" tip="请选择设备!" value="${dto.deviceId}" maxlength="100" />
								<select id="device_select">
									<option value="">请选择</option>
									<c:forEach items="${deviceList}" var="deviceInfo" varStatus="st">
										<c:choose>
											<c:when test="${deviceInfo.id == dto.deviceId}">
												<option value="${deviceInfo.id }" selected="selected">${deviceInfo.serialNo }</option>
											</c:when>
											<c:otherwise>
												<option value="${deviceInfo.id }">${deviceInfo.serialNo }</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
						</div>

<!-- 						<div class="control-group"> -->
<!-- 							<label class="control-label"><span class="red">*</span>选择所属区域:</label> -->
<!-- 							<div class="controls"> -->
<%-- 								<%@ include file="../areainfo/select_area.jsp"%> --%>
<!-- 							</div> -->
<!-- 						</div> -->
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>车型:</label>
							<div class="controls">
								<input id="carModel" name="carModel" validateType="notEmpty" tip="请输入车型!" type="text" value="${dto.carModel}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>车辆年检到期日:</label>
							<div class="controls">
								<input id="inspectionTime" validateType="notEmpty" tip="请选择车辆年检到期日!" style="cursor: pointer;" readonly="readonly"
									name="inspectionTime" type="text"
									value="<fmt:formatDate value="${dto.inspectionTime}"
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
