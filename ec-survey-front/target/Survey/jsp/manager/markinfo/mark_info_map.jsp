<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>	
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎使用保易行系统</title>  
<link href="${ctx}/media/css/admin/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/media/css/admin/theme.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/font-awesome/css/font-awesome.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/webjars/bootstrap/2.3.2/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/webjars/bootstrap/2.3.2/css/bootstrap-responsive.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
 
	
<style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
      }
      @media (max-width: 980px) {
        /* Enable use of floated navbar text */
        .navbar-text.pull-right {
          float: none;
          padding-left: 5px;
          padding-right: 5px;
        }
      }
</style>
<script type="text/javascript" src="${ctx}/webjars/jquery/1.9.1/jquery.min.js"></script>    
<script type="text/javascript" src="${ctx}/webjars/bootstrap/2.3.2/js/bootstrap.js"></script>

<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>

<!-- ztree -->
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.excheck-3.5.js"></script>

<!-- 百度地图 -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2&ak=F9e138a00e815825002d53430326cac7"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/LuShu/1.2/src/LuShu_min.js"></script>
<script type="text/javascript" >

	var map;
	$(function() {
	  initMapInfo();
	  var markInfos=${listMarkInfo};
	  if(markInfos.length>0){
		 initMarks(map,markInfos);
	  }
	});

	function initMapInfo() {
		// 百度地图API功能
		map= new BMap.Map("map_canvas");
		var point=new BMap.Point('${checkMarkInfo.baiduLongitude}', '${checkMarkInfo.baiduLatitude}');   //默认点击的标注点为中心
		map.centerAndZoom(point,15);
		map.enableScrollWheelZoom();
		map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
	};
	
	function initMarks(map,datas){
		for ( var i = 0; i < datas.length; i++) {
			var markinfo = datas[i];
			var point = new BMap.Point(markinfo.baiduLongitude, markinfo.baiduLatitude);
			addMarker(point,markinfo);
		}
	}
	
	function addMarker(point,markinfo){
		if('${checkMarkInfo.id}'!=markinfo.id){
		  var marker = new BMap.Marker(point);
		  var myIcon = new BMap.Icon('${ctx}/media/images/mark_image/'+markinfo.img+'.png',
					new BMap.Size(10, 10), {anchor: new BMap.Size(10,10),// 设置图片偏移
					}); 
		  marker.setIcon(myIcon);
		  map.addOverlay(marker);
		  marker.addEventListener("click",function() {
			clickMarke(this,markinfo);
		  }); 
		}else{  //进入直接弹出具体文字信息框
			var currentMaker = new BMap.Marker(point); 
			map.addOverlay(currentMaker);
			clickMarke(currentMaker,markinfo);
			currentMaker.addEventListener("click",function() {
				clickMarke(this,markinfo);
			  }); 
		}
	}
	
	function clickMarke(marker, markinfo) {
		content = "<p style='font-size:14px;'>所属类别："+markinfo.typeName+"</p>";
		content += "<p style='font-size:14px;'>标注名称："+markinfo.name+"</p>";
		content += "<p style='font-size:14px;'>标记点详细地址："+markinfo.address+"</p>";
		content += "<p style='font-size:14px;'>标注状态："+markinfo.statusType+"</p>";
		marker.openInfoWindow(new BMap.InfoWindow(content));
	}
</script>
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../../common/head.jsp"%>
	<ul class="breadcrumb" style="margin-left: 20px; margin-right: 20px;height: 30px;line-height: 30px;">
		<li><a href="#">统计报表</a> <span class="divider">/</span></li>
		<li><a href="${ctx}/markInfo/list">标注点信息管理</a> <span class="divider">/</span></li>
		<li class="active">标注点显示</li>
		
	</ul>
	<div class="container-fluid">
		<div class="row-fluid">
			<div>
				<div >
					<div class="well" style="overflow:hidden">
						<div id="map_canvas" style="min-height: 550px; min-width: 800px;"></div>
						<div id="result"></div>
					</div>
					<div class="row-fluid">
					
					</div>
					<!--/row-->
				</div>
			</div>
		</div>
	</div>
</body>
</html>
