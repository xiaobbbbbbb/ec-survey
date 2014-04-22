<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="base.jsp"%>
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
<%@ include file="base.jsp"%>
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="brand" href="${ctx}/locate/carLocate">奥创科技-保易行</a>
			<div class="nav-collapse collapse">
				<p class="navbar-text pull-right">
					登录为 <a href="#" class="navbar-link">${sessionScope.USER_SESSION.name}</a>&nbsp;&nbsp; <a
						href="javascript:loginOut();" class="navbar-link">退出</a>
				</p>
				<ul class="nav">
				    <shiro:hasPermission name="carinfo">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"
						class="active">车辆位置信息<b class="caret"></b></a>
						<ul class="dropdown-menu">
						    <shiro:hasPermission name="/locate/carLocate">
							<li><a href="${ctx}/locate/carLocate">查勘车定位</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="/locate/addrLocate">
							<li><a href="${ctx}/locate/addrLocate">地点定位搜索</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="/locate/orbit">
							<li><a href="${ctx}/locate/orbit">查勘车轨迹回放</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="/locate/fence">
							<li><a href="${ctx}/locate/fence">电子围栏</a></li>
							</shiro:hasPermission>
						</ul>
					</li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="eventinfo">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">报案管理<b
							class="caret"></b></a>
						<ul class="dropdown-menu">
						    <shiro:hasPermission name="/locate/eventLocate">
							<li><a href="${ctx}/locate/eventLocate">报案车定位</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="/eventInfo/list">
							<li><a href="${ctx}/eventInfo/list">案件查询</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="/eventInfo/listNoScheduledCount">
							<li><a href="${ctx}/eventInfo/listNoScheduledCount">案件统计</a></li>
							</shiro:hasPermission>
						</ul>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="baseinfo">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">基本信息<b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<shiro:hasPermission name="/deviceInfo/">							
							<li><a href="${ctx}/deviceInfo/list">设备管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="/areaInfo/">							
							<li><a href="${ctx}/areaInfo/list">区域分组管理</a></li>
							</shiro:hasPermission>
						    <shiro:hasPermission name="/carInfo/">
							<li><a href="${ctx}/carInfo/list">查勘车辆信息</a></li>
							</shiro:hasPermission>
						    <shiro:hasPermission name="/surveyUserInfo/">
							<li><a href="${ctx}/surveyUserInfo/list">查勘员信息</a></li>
							</shiro:hasPermission>
						    <shiro:hasPermission name="/tagInfo/">							
							<li><a href="${ctx}/tagInfo/list">身份识别卡信息</a></li>
							</shiro:hasPermission>
						    <shiro:hasPermission name="/markType/">							
							<li><a href="${ctx}/markType/list">标注类别管理</a></li>
							</shiro:hasPermission>
						</ul>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="totalreport">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">统计报表<b
							class="caret"></b></a>
						<ul class="dropdown-menu">
						    <shiro:hasPermission name="baseinfo">
							<li><a href="${ctx}/eventInfo/listReport">报案统计</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="/carReport/listReport">
							<li><a href="${ctx}/carReport/listReport">车辆运行明细报告</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="/carReport/listReport">
							<li><a href="${ctx}/carReport/listReportMonth">车辆运行汇总报告</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="/surveyUserInfo/listReport">
							<li><a href="${ctx}/surveyUserInfo/listReport">查勘员工作时间报告</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="/fenceAlarmInfo/listReport">
							<li><a href="${ctx}/fenceAlarmInfo/listReport">围栏告警统计</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="/markInfo/">
							<li><a href="${ctx}/markInfo/list">标注点信息</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="baseinfo">
							<li><a href="${ctx}/locate/addressAnalyze">事故位置分析</a></li>
							</shiro:hasPermission>
						</ul>
					</li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="systemconfig">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">系统设置<b
							class="caret"></b></a>
						<ul class="dropdown-menu">
						    <shiro:hasPermission name="/ralasafe/user/">
							<li><a href="${ctx}/ralasafe/user/list">操作员管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="/ralasafe/role/">
							<li><a href="${ctx}/ralasafe/role/list">角色管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="/systemLog/list">
							<li><a href="${ctx}/systemLog/list">日志管理</a></li>
							</shiro:hasPermission>
						</ul>
					</li>
					</shiro:hasPermission>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
</div>

<!-- 弹出层的提示 -->
<%@ include file="alert.jsp"%>

<link href="${ctx}/media/css/common/load.css" type="text/css" rel="stylesheet" />
<!-- 弹出层的提示 -->
<%@ include file="load.jsp"%>


