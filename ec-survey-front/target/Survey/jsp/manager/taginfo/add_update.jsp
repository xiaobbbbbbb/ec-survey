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
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>
<script type="text/javascript">
	function submitForm() {
		if ($.ec.validateType()) {
			var oldName = $.trim("${dto.serialNo}"), 
			$newName = $.trim($("#serialNo").val()), 
			flag = false;
			if ($newName != oldName) //如果部门名称没有修改过则直接修改
			{
				var param = {"serialNo" : $newName},
				checkUrl = "${ctx}/tagInfo/check";
				flag = $.ecAjax.getReturn({
					url : checkUrl,
					data : param
				});
			}
			if (!flag) {
				var url = "${ctx}/tagInfo/${dto.id == null ? 'add' : 'update'}";
				$.ec.ajaxSubmitForm({
					url : url,
					callback : frameHref
				});
			} else {
				$.alertModal({
					content : '电子标签已经存在！'
				});
			}
		}
	}

	function frameHref() {
		window.location.href = "${ctx}/tagInfo/list";
	}
	
	$(function() {
		 $("#user_select").change(function(){  //查勘员信息
			 var checkValue=$("#user_select").val();  //获取Select选择的Value
			 $("#userId").val(checkValue);
		 });   
		 
		 $("#car_select").change(function(){  //车辆信息
			 var checkValue=$("#car_select").val();  //获取Select选择的Value
			 $("#carId").val(checkValue);
		 });  
	});
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
					<li><a href="${ctx}/tagInfo/list">身份识别卡信息</a> <span class="divider">/</span></li>
					<li class="active">${dto.id == null ? '添加' : '修改'}</li>
				</ul>
				<div class="well">
					<form id="form" class="form-horizontal">
						<input name="id" value="${dto.id }" type="hidden" />
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>电子标签号:</label>
							<div class="controls">
								<input id="serialNo" name="serialNo" validateType="notEmpty" tip="请输入电子标签号!" type="text" value="${dto.serialNo}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>选择查勘员:</label>
							<div class="controls">
								<input id="userId" name="userId" type="hidden" validateType="notEmpty" tip="请选择查勘员!" value="${dto.userId}" maxlength="100" />
								<select id="user_select">
								    <option value="">请选择</option>
									<c:forEach items="${surveyUserlist}" var="survey_user" varStatus="st">
										<c:choose>
										   <c:when test="${survey_user.id == dto.userId}">    
												<option value="${survey_user.id }" selected="selected">${survey_user.name }</option>
										   </c:when>
										   <c:otherwise> 
												<option value="${survey_user.id }">${survey_user.name }</option>
										   </c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>选择查勘车辆:</label>
							<div class="controls">
								<input id="carId" name="carId" type="hidden" validateType="notEmpty" tip="请选择查勘车辆!" value="${dto.carId}" maxlength="100" />
								<select id="car_select">
								    <option value="">请选择</option>
									<c:forEach items="${carList}" var="carInfo" varStatus="st">
										<c:choose>
										   <c:when test="${carInfo.id == dto.carId}">    
												<option value="${carInfo.id }" selected="selected">${carInfo.carNo }</option>
										   </c:when>
										   <c:otherwise> 
												<option value="${carInfo.id }">${carInfo.carNo }</option>
										   </c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
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
