<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/base.jsp"%>	
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎使用保易行系统</title>  
<link href="${ctx}/webjars/bootstrap/2.3.2/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/webjars/bootstrap/2.3.2/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${ctx}/media/css/admin/theme.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/webjars/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/webjars/bootstrap/2.3.2/js/bootstrap.js"></script>
<script type="text/javascript">
	function checkForm() {
		var $loginName = $("#loginName"), 
		$password = $("#password");
		if ($.trim($loginName.val()).length == 0) {
			$loginName.focus();
			return false;
		} else if ($.trim($password.val()).length == 0) {
			$password.focus();
			return false;
		}
		return true;
	}
</script>
<style type="text/css">
.brand {
	font-family: georgia, serif;
}

.brand .first {
	color: #ccc;
	font-style: italic;
}

.brand .second {
	color: #fff;
	font-weight: bold;
}
</style>

</head>
<body>
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="brand"><span class="first">欢迎使用</span> <span class="second">保易行</span></a>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="dialog span4" style="margin-top: 12%;">
				<div class="block">
					<div class="block-heading">用户登陆</div>
					<div class="block-body">
						<form action="${ctx}/ralasafe/login/userLogin" onsubmit="return checkForm();" method="post">
							<label>用户名</label><input id="loginName" name="loginName" value="test1" type="text" class="span12" />
							<label>密码</label><input id="password" name="password" value="123456" type="password" class="span12" />
							<input type="submit" value="登陆" id="submitBtn" class="btn btn-primary pull-right" style="padding: 6px 18px;font-weight: bold;" />
							<div class="clearfix"></div>
						</form>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>