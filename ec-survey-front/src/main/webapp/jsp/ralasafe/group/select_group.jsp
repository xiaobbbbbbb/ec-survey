<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
	function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		var zTree = $.fn.zTree.getZTreeObj("treeDate"), nodes = zTree
				.getSelectedNodes(), v = "", id = "";
		nodes.sort(function compare(a, b) {
			return a.id - b.id;
		});
		for ( var i = 0, l = nodes.length; i < l; i++) {
			v += nodes[i].name + ",";
			id += nodes[i].id + ",";
		}
		var $thisId = $("#groupId").val();
		if (parseInt($thisId) == parseInt(id)) {
			$.alertModal({
				content : '所属公司不允许是自己,请重新选择！'
			});
			return;
		}
		if (v.length > 0) {
			v = v.substring(0, v.length - 1);
			id = id.substring(0, id.length - 1);
			$("#parentName").val(v);
			$("#parentId").val(id);
			hideMenu();
		}
		
		$("#parentName_").val("");
		$("#parentId_").val("");
	}

	function showMenu() {
		var cityObj = $("#parentName");
		var cityOffset = $("#parentName").offset();
		var tree = $("#treeDate").html();
		if (tree.length > 0) {
			$("#menuContent").css({
				left : cityOffset.left + "px",
				top : cityOffset.top + cityObj.outerHeight() + "px"
			}).slideDown("fast");
			$("body").bind("mousedown", onBodyDown);
		}
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
<div id="menuContent" class="menuContent"
	style="display: none; position: absolute; border: 1px solid #ccc; background-color: white; min-width: 180px;">
	<%@ include file="group_tree.jsp"%>
</div>