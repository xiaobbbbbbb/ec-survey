<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./../common/base.jsp"%>
<style type="text/css">
body {
	padding-top: 52px;
}

.sidebar-nav {
	padding: 9px 0;
}

@media ( max-width : 980px) { /* Enable use of floated navbar text */
	.navbar-text.pull-right {
		float: none;
		padding-left: 5px;
		padding-right: 5px;
	}
}

ul li {
	list-style: none;
}
</style>
<script type="text/javascript">
	function loginOut() {
		top.location.href = "${ctx}/ralasafe/login/loginOut";
	}

	function href(url) {
		top.location.href = url;
	}
</script>
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="brand" href="#">奥创科技-保易行</a>
			<div class="nav-collapse collapse">
				<p class="navbar-text pull-right">
					登录为 <a href="#" class="navbar-link">${sessionScope.USER_SESSION.name}</a>&nbsp;&nbsp; <a
						href="javascript:loginOut();" class="navbar-link">退出</a>
				</p>
				<ul class="nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">机构管理<b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="${ctx}/ralasafe/group/list">集团管理</a></li>
							<li><a href="${ctx}/ralasafe/org/list">服务机构管理</a></li>
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">权限管理<b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<!-- <li><a href="${ctx}/ralasafe/resource/list">权限菜单</a></li>-->
							<li><a href="${ctx}/ralasafe/role/list">角色管理</a></li>
							<li><a href="${ctx}/ralasafe/user/list">操作员管理</a></li>
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">系统设置<b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="${ctx}/systemLog/list">日志管理</a></li>
						</ul></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
</div>

<%@ include file="./../common/alert.jsp"%>