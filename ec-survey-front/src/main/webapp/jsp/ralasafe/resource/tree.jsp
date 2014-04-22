<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
$(function(){
	var $showUrl=$("#showUrl").val();
	var $roleId=$("#roleId").val();
	var setting = {
		check: {
				enable: true
		},
		async: {
			enable: true,
			type: "get",
			url:"${ctx}/ralasafe/resource/tree?showUrl="+$showUrl+"&&roleId="+$roleId+"&&time="+Math.random(),
			autoParam:["id"],
		    dataFilter: filter  
		},
	    callback: {
		    onClick: zTreeOnClick,
		    onDblClick:zTreeOnDblClick,
			onAsyncSuccess: onAsyncSuccess
	    }
    };
	$.fn.zTree.init($("#treeDate"), setting);
});

function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}

//判断是否是父节点
function getIsParent() { 
     var treeObj = $.fn.zTree.getZTreeObj("treeDate");
     var isParent=false;
     var sNodes = treeObj.getSelectedNodes();
     if (sNodes.length > 0) {
         isParent = sNodes[0].isParent;
     }
     return isParent;
}


//默认打开所有
function expandAll() {
	var zTree = $.fn.zTree.getZTreeObj("treeDate");
	expandNodes(zTree.getNodes());
}

function expandNodes(nodes) {
	if (!nodes) return;
	var zTree = $.fn.zTree.getZTreeObj("treeDate");
	for (var i=0, l=nodes.length; i<l; i++) {
		zTree.expandNode(nodes[i], true, false, false);
		if (nodes[i].isParent && nodes[i].zAsync) {
			expandNodes(nodes[i].children);
		}
	}
}
</script>
<input type="hidden" id="selectId" />
<input type="hidden" id="selectName" />
<input type="hidden" id="isParent" />
<ul id="treeDate" class="ztree" style="padding-top: 0px;"></ul>