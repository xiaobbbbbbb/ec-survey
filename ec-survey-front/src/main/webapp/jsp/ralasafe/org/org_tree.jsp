<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<link href="${ctx}/media/js/ztree3.5/css/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/media/js/ztree3.5/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>

<script type="text/javascript">
	function orgTree(groupId) {
		var tree = $("#orgTree").html();
		var oldGroupId = $("#oldGroupId").val();
		if (groupId != oldGroupId) {
			$("#oldGroupId").val(groupId);
			var setting_ = {
				check : {
					enable : true
				},
				async : {
					enable : true,
					type : "get",
					url : "${ctx}/ralasafe/org/tree?groupId=" + groupId
							+ "&showUrl=false&time=" + Math.random(),
					autoParam : [ "id" ],
					dataFilter : filter_
				},
				callback : {
					onClick : zTreeOnClick_,
					onDblClick : zTreeOnDblClick_,
					onAsyncSuccess : onAsyncSuccess_
				}
			};
			$.fn.zTree.init($("#orgTree"), setting_);
		}
	}

	function filter_(treeId, parentNode, childNodes) {
		if (!childNodes)
			return null;
		for ( var i = 0, l = childNodes.length; i < l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}

	//判断是否是父节点
	function getIsParent_() {
		var treeObj = $.fn.zTree.getZTreeObj("orgTree");
		var isParent = false;
		var sNodes = treeObj.getSelectedNodes();
		if (sNodes.length > 0) {
			isParent = sNodes[0].isParent;
		}
		return isParent;
	}

	//默认打开所有
	function expandAll_() {
		var zTree = $.fn.zTree.getZTreeObj("orgTree");
		expandNodes_(zTree.getNodes());
	}

	function expandNodes_(nodes) {
		if (!nodes)
			return;
		var zTree = $.fn.zTree.getZTreeObj("orgTree");
		for ( var i = 0, l = nodes.length; i < l; i++) {
			zTree.expandNode(nodes[i], true, false, false);
			if (nodes[i].isParent && nodes[i].zAsync) {
				expandNodes_(nodes[i].children);
			}
		}
	}
</script>
<input type="hidden" id="selectId_" />
<input type="hidden" id="selectName_" />
<input type="hidden" id="isParent_" />
<input type="hidden" id="oldGroupId" />
<ul id="orgTree" class="ztree" style="padding-top: 0px;"></ul>