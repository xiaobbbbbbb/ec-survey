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
		$("#disabled_select").change(function() { //服务机构信息
			var checkValue = $("#disabled_select").val(); //获取Select选择的Value
			$("#disabled").val(checkValue);
		});
	});

	function checkData(param) {
		var checkUrl = "${ctx}/markType/check";
		return flag = $.ecAjax.getReturn({
			url : checkUrl,
			data : param
		});
	}

	function submitForm() {
		if ($.ec.validateType()) {
 			if($("input[name=img]:checked").val()==null){
 				$.alertModal({
					content : '请选择标注类型的图片！'
				});
 				return ;
 			}
			var oldName = $.trim("${dto.name}"), $newName = $.trim($(
					"#name").val()),
					 flag = false;
			if ($newName != oldName) //如果部门名称没有修改过则直接修改
			{
				var param = {
					"name" : $newName,
					"img" :$("input[name=img]:checked").val(),
					"disabled" : $("#disabled_select").val()
				};
				flag = checkData(param);
			}
			if (!flag) {
				var url = "${ctx}/markType/${dto.id == null ? 'add' : 'update'}";
				$.ec.ajaxSubmitForm({
					url : url,
					callback : frameHref
				});
			} else {
				$.alertModal({
					content : '标注类型名称已经存在！'
				});
			}
		}
	}

	function frameHref() {
		window.location.href = "${ctx}/markType/list";
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
					<li><a href="${ctx}/deviceInfo/list">标注类别</a> <span class="divider">/</span></li>
					<li class="active">${dto.id == null ? '添加' : '修改'}</li>
				</ul>
				<div class="well">
					<form id="form" class="form-horizontal">
						<input name="id" value="${dto.id }" type="hidden" />
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>标注名称:</label>
							<div class="controls">
								<input id="name" name="name" validateType="notEmpty" tip="请输入标注名称!" type="text" value="${dto.name}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>标注点图片:</label>
							<div class="controls">
							<%-- 	<input id="img" name="img" type="text"  value="${dto.img}" maxlength="100" />  --%>
								<input type="radio" name="img" value="blue" <c:if test="${dto.img=='blue'}">checked="checked"</c:if>  />
								<img alt="" src="${ctx}/media/images/mark_image/blue.png">
								<input type="radio" name="img" value="green" <c:if test="${dto.img=='green'}">checked="checked"</c:if> style="margin-left: 60px;" />
								<img alt="" src="${ctx}/media/images/mark_image/green.png">
							</div>
							<div class="controls" style="margin-top: 10px">
								<input type="radio" name="img" value="red"  <c:if test="${dto.img=='red'}">checked="checked"</c:if> />
								<img alt="" src="${ctx}/media/images/mark_image/red.png">
								<input type="radio" name="img" value="yellow" <c:if test="${dto.img=='yellow'}">checked="checked"</c:if> style="margin-left: 60px;" />
								<img alt="" src="${ctx}/media/images/mark_image/yellow.png">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>是否有效:</label>
							<div class="controls">
								<input id="disabled" name="disabled" type="hidden" value="${dto.disabled}" maxlength="100" />
							    <select id="disabled_select">
									<c:choose>
									    <c:when test="${dto.disabled == 1}">
									     	<option value="1"  selected="selected">有效</option>
											<option value="0" >无效</option>
									    </c:when>
									    <c:when test="${dto.disabled == 0}">
									      	<option value="1" >有效</option>
											<option value="0"  selected="selected">无效</option>
									    </c:when>  
									   <c:otherwise>  
									       <option value="1"  selected="selected" >有效</option>
										   <option value="0" >无效</option>
									   </c:otherwise>
								    </c:choose>
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
