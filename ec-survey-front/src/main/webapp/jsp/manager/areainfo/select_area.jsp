<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<link href="${ctx}/media/js/ztree3.5/css/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/media/js/ztree3.5/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
<script type="text/javascript">

	function zTreeOnClick(event, treeId, treeNode, clickFlag) {
	/* 	var check = (treeNode && !treeNode.isParent);
		if (!check)
		{
			$.alertModal({
				content : '只能选择区域！'
			});
		}
		else
		{ */
			var zTree = $.fn.zTree.getZTreeObj("treeDate"), 
			nodes = zTree.getSelectedNodes(),
			v = "",
			id = "";
			nodes.sort(function compare(a, b) {
				return a.id - b.id;
			});
			for ( var i = 0, l = nodes.length; i < l; i++) {
				v += nodes[i].name + ",";
				id += nodes[i].id + ",";
			}
			if (v.length > 0) {
				v = v.substring(0, v.length - 1);
				id = id.substring(0, id.length - 1);
				$("#areaName").val(v);
				$("#areaId").val(id);
				hideMenu();
			}
	/* 	} */
	}

	function showMenu() {
		var cityObj = $("#areaName");
		var cityOffset = $("#areaName").offset();
		$("#menuContent").css({
			left : cityOffset.left + "px",
			top : cityOffset.top + cityObj.outerHeight() + "px"
		}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}

	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}

	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
				event.target).parents("#menuContent").length > 0)) {
			hideMenu();
		}
	}

	//展开所有节点
	function onAsyncSuccess(event, treeId, treeNode, msg) {
		if (treeId == "treeDate") {
			expandAll();
		}
	}

	function zTreeOnDblClick(event, treeId, treeNode) {
		//空实现，目的是在别的页面可以共用这棵树
	};
</script>
<input id="areaName" style="cursor: pointer;" readonly="readonly"
	onclick="showMenu(); return false;" type="text" value="${areaName}" maxlength="100" />
<input id="areaId" name="areaId" type="hidden"  value="${dto.areaId}"
	<c:if test="${type==null }">
		validateType="notEmpty" tip="请选择所属区域!"
		value="${dto.areaId}"
	</c:if> />
<div id="menuContent" class="menuContent"
	style="display: none; position: absolute; border: 1px solid #ccc; background-color: white; min-width: 180px;">
	<%@ include file="tree.jsp"%>
</div>