<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>欢迎使用《保易行查勘服务系统V1.0》</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/media/css/common/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/common/frame.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/common/load.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>
<script type="text/javascript">
	(function(config) {
		config['lock'] = true;
		config['fixed'] = true;
		config['resize'] = false;
		config['opacity'] = '0.3';
		config['okVal'] = '确定';
		config['cancelVal'] = '取消';
		config['title'] = '系统提示';
	})(art.dialog.defaults);
</script>
</head>

<body>
	<div id="load_back" style="display: none;"></div>
	<div id="load" style="display: none;">
		<div id="load_con">数据加载中，请稍等...</div>
	</div>
	<input value="${ctx}" type="hidden" id="basePath" />
	<iframe frameborder="0" id="ecFrame" name="ecFrame" src="${ctx}/ec/framesetUI" width="100%" height="100%"></iframe>
</body>
</html>
