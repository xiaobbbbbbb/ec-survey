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
<link href="${ctx}/media/js/ztree3.5/css/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/webjars/bootstrap/2.3.2/js/bootstrap.js"></script>
<script src="${ctx}/media/js/ztree3.5/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>
<script type="text/javascript">
	function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		$("#selectId").val(treeNode.id);
		$("#selectName").val(treeNode.name);
		$("#isParent").val(getIsParent());
		var treeObj = $.fn.zTree.getZTreeObj("treeDate"), sNodes = treeObj
				.getSelectedNodes();
		if (sNodes.length > 0) {
			var node = sNodes[0].getParentNode(), pid = 0;
			if (node != null) {
				pid = node.id;
			}
			$("#parentId").val(pid);
		}
	}

	function onAsyncSuccess(event, treeId, treeNode, msg) {
		//空实现，目的是在别的页面可以共用这棵树
	}

	function zTreeOnDblClick(event, treeId, treeNode) {
		//空实现，目的是在别的页面可以共用这棵树
	};

	function addUI() {
		var $selectNodeid = $("#selectId").val();
		if ($selectNodeid.length > 0) {
			href("${ctx}/ralasafe/resource/addUI?pid="+$selectNodeid);
		} else {
			href("${ctx}/ralasafe/resource/addUI?pid=0");
		}
	}
	
	function updateUI()
	{
		var $selectNodeid = $("#selectId").val();
		if ($selectNodeid.length > 0) {
			href("${ctx}/ralasafe/resource/updateUI?id="+$selectNodeid);
		} 
	}
	
	function del()
	{
		var $selectNodeid=$("#selectId").val(),
		$selectNodeIsParent=$("#isParent").val();
		if($selectNodeid.length>0)
		{
			if($selectNodeIsParent=="true")
			{
				
			}
			else
			{
				$.ec.deleteData({
					url : "${ctx}/ralasafe/resource/delete",        //删除数据的地址
				    param : {"ids":$selectNodeid}
				});
			}
		}
		else
	    {
	    	
	    }
	}
</script>
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../../admin/head.jsp"%>
	<ul class="breadcrumb" style="margin-left: 20px; margin-right: 20px;margin-bottom: 0px;">
		<li><a href="#">权限管理</a> <span class="divider">/</span></li>
		<li class="active">权限菜单</li>
	</ul>
	
	<div class="container-fluid" style="min-width: 1100px;">
	<div class="btn-toolbar">
		<button class="btn btn-primary" onfocus="this.blur()" onclick="addUI();">添加菜单</button>
		<button class="btn" onfocus="this.blur()" onclick="updateUI();">修改</button>
		<button class="btn" onfocus="this.blur()" onclick="del();">删除</button>
		<div class="btn-group"></div>
	</div>
		<div class="well">
		    <input type="hidden" id="showUrl" value="false" />
			<input type="hidden" id="parentId" />
			<input type="hidden" id="roleId" value=""/>
			<jsp:include page="tree.jsp" flush="true" />
		</div>
	</div>
</body>
</html>