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
	var oldPass = "";
	var oldPass2 = "";
	$(function() {
		oldPass = $("#password").val();
		oldPass2 = $("#reqPassword").val();

		$("#role_select").change(function() { //角色信息
			var checkValue = $("#role_select").val(); //获取Select选择的Value
			$("#roleIds").val(checkValue);
		});

		$("#org_select").change(function() { //服务机构信息
			var checkValue = $("#org_select").val(); //获取Select选择的Value
			$("#orgId").val(checkValue);
		});

		$("#city_select").change(function() { //城市信息
			var checkValue = $("#city_select").val(); //获取Select选择的Value
			$("#cityId").val(checkValue);
		});

	});

	function enter(which) {
		if (which == 1) {
			$("#password").val("");
		} else {
			$("#reqPassword").val("");
		}
	}

	function leave(which) {
		if (which == 1) {
			var newPass = $("#password").val();
			if (newPass == "") {
				$("#password").val(oldPass);
			} else {
				oldPass = newPass;
			}
		} else {
			var newPass2 = $("#reqPassword").val();
			if (newPass2 == "") {
				$("#reqPassword").val(oldPass2);
			} else {
				oldPass2 = newPass2;
			}
		}
	}

	function submitForm() {
		if ($.ec.validateType()) {
			var oldName = $.trim("${dto.loginName}"), $newName = $.trim($(
					"#loginName").val()), flag = false;
			if ($newName != oldName) //如果部门名称没有修改过则直接修改
			{
				var checkUrl = "${ctx}/ralasafe/user/check", param = {
					"loginName" : $newName
				};
				flag = $.ecAjax.getReturn({
					url : checkUrl,
					data : param
				});
			}
			if (!flag) {
				var url = "${ctx}/ralasafe/user/${dto.userId == null ? 'add' : 'update'}";
				$.ec.ajaxSubmitForm({
					url : url,
					callback : frameHref
				});
			} else {
				$.alertModal({
					content : '登陆名已经存在！'
				});
			}
		}
	}

	function frameHref() {
		window.location.href = "${ctx}/ralasafe/user/list";
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
					<li><a href="${ctx}/ralasafe/user/list">操作员管理</a> <span class="divider">/</span></li>
					<li class="active">${dto.userId == null ? '添加' : '修改'}</li>
				</ul>
				<div class="well">
					<form id="form" class="form-horizontal">
						<input type="hidden" id="oldName" value="${dto.loginName}" /> <input type="hidden"
							name="oldPassword" value="${dto.password}" /> <input type="hidden" name="userId"
							value="${dto.userId}" /> <input name="isManager" value="${dto.isManager }" type="hidden" />
						<table>
							<tr>
								<td>
									<div class="control-group">
										<label class="control-label"><span class="red">*</span>人员序号：</label>
										<div class="controls">
											<input id="level" name="level" type="text" value="${dto.level != null ?dto.level:'9999'}"
												maxlength="100" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><span class="red">*</span>用户名：</label>
										<div class="controls">
											<input id="name" name="name" type="text" validateType="notEmpty" tip="请输入用户名!"
												value="${dto.name}" maxlength="100" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><span class="red">*</span>登录名：</label>
										<div class="controls">
											<input id="loginName" name="loginName" type="text" validateType="notEmpty" tip="请输入登录名!"
												value="${dto.loginName}" maxlength="100" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><span class="red">*</span>密码：</label>
										<div class="controls">
											<input id="password" name="password" onfocus="enter(1)" onblur="leave(1)" type="password"
												validateType="notEmpty" tip="请输入密码!" value="${dto.password}" maxlength="100" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><span class="red">*</span>确认密码：</label>
										<div class="controls">
											<input id="reqPassword" type="password" onfocus="enter(2)" onblur="leave(2)"
												value="${dto.password}" validateType="notEmpty" tip="请输入确认密码!" maxlength="100" />
										</div>
									</div>
									<c:if test="${sessionScope.USER_SESSION.loginName=='system'}">
										<div class="control-group">
											<label class="control-label"><span class="red">*</span>选择所属服务机构：</label>
											<div class="controls">
												<input id="parentName_" style="cursor: pointer;" readonly="readonly"
													onclick="showMenu_();" type="text" value="${orgName}" maxlength="100" /> <input
													id="parentId_" name="orgId" validateType="notEmpty" tip="请选择服务机构!" type="hidden" value="${dto.orgId}" />
												<%@ include file="../org/select_org.jsp"%>
											</div>
										</div>
									</c:if>
									<div class="control-group">
										<label class="control-label"><span class="red">*</span>用户角色：</label>
										<div class="controls">
											<input id="roleIds" name="roleIds" type="hidden" validateType="notEmpty" tip="请选择用户角色!"
												value="${dto.roleIds}" maxlength="100" /> <select id="role_select">
												<option value="">请选择</option>
												<c:forEach items="${listRole}" var="role" varStatus="st">
													<option value="${role.roleId }"
														<c:if test="${role.roleId == dto.roleIds}">selected="selected"</c:if>>
														${role.name}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><span class="red">*</span>账号是否启用：</label>
										<div class="controls">
											<select name="disabled">
												<option value="1" <c:if test="${dto.disabled==1}">checked</c:if>>是</option>
												<option value="0" <c:if test="${dto.disabled==0}">checked</c:if>>否</option>
											</select>
										</div>
									</div>
								</td>
								<td valign="top">
									<div class="control-group">
										<label class="control-label">电子邮箱：</label>
										<div class="controls">
											<input id="email" name="email" type="text" value="${dto.email}" maxlength="100" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">联系电话：</label>
										<div class="controls">
											<input id="phone" name="phone" type="text" value="${dto.phone}" maxlength="100" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">备注：</label>
										<div class="controls">
											<textarea name="message">${dto.message}</textarea>
										</div>
									</div>
								</td>
							</tr>
						</table>
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