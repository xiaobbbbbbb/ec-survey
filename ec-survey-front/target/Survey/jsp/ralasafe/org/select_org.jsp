<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
	function zTreeOnClick_(event, treeId, treeNode, clickFlag) {
		var zTree = $.fn.zTree.getZTreeObj("orgTree"), 
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
		var $thisId = $("#orgId").val();
		if (parseInt($thisId) == parseInt(id)) {
			$.alertModal({
				content : '所属服务机构不允许是自己,请重新选择！'
			});
			return;
		}
		if (v.length > 0) {
			v = v.substring(0, v.length - 1);
			id = id.substring(0, id.length - 1);
			$("#parentName_").val(v);
			$("#parentId_").val(id);
			hideMenu_();
		}
	}
	
	function menu_()
	{
		var cityObj = $("#parentName_");
		var cityOffset = $("#parentName_").offset();
		$("#menuContent_").css({
				left : cityOffset.left + "px",
				top : cityOffset.top + cityObj.outerHeight() + "px"
		}).slideDown("fast");
		$("body").bind("mousedown", onBodyDown_);
	}

	function showMenu_() {
		var group=$("#parentId");
		if(group.length>0)
		{
			var groupId=group.val();
			if(groupId>0)
			{
				orgTree(groupId);
				menu_();	
			}
			else
			{
				$.alertModal({
					content : '请先选择所属公司！'
				});
			}
		}
		else
		{
			orgTree(-1);
			menu_();
		}
	}

	function hideMenu_() {
		$("#menuContent_").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown_);
	}

	function onBodyDown_(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_" || $(
				event.target).parents("#menuContent_").length > 0)) {
			hideMenu_();
		}
	}

	//展开所有节点
	function onAsyncSuccess_(event, treeId, treeNode, msg) {
		if (treeId == "orgTree") {
			expandAll_();
		}
	}

	function zTreeOnDblClick_(event, treeId, treeNode) {
		//空实现，目的是在别的页面可以共用这棵树
	};
</script>
<div id="menuContent_" style="display: none; position: absolute; border: 1px solid #ccc; background-color: white; min-width: 180px;">
	<%@ include file="org_tree.jsp"%>
</div>