<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>	
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎使用保易行系统</title>  
<link href="${ctx}/media/css/admin/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/media/css/admin/theme.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/font-awesome/css/font-awesome.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/js/ztree3.5/css/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
	
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

<!-- MarkerTool js -->
<script type="text/javascript" src="http://api.map.baidu.com/library/MarkerTool/1.2/src/MarkerTool_min.js"></script>
<script type="text/javascript" src="${ctx}/js/marker.js"></script>

<script type="text/javascript" src="${ctx}/js/eventInfo.js"></script>
<script type="text/javascript" src="${ctx}/js/carInfo.js"></script>

<script type="text/javascript" >
	//百度地图API功能
	var ctx = '${ctx}';
	var map = null;
	var cityName = '${cityName}';
	var circle = null;
	//	map.centerAndZoom("深圳南山区",15);
	var eventInfos = ${eventInfos};
	var markTypes = eval('${markTypes}');
	
	function getFont(treeId, node) {
		return node.font ? node.font : {};
	}
	
	function beforeClick(treeId, treeNode, clickFlag) {
		return (treeNode.click != false);
	}
	
	function onClick(event, treeId, treeNode, clickFlag) {
		
		if(eventMarkerArrays[treeNode.id-4]) {
			clickEventMarker(eventMarkerArrays[treeNode.id-4],treeNode.id-4,map);
		}
		if(treeNode.isParent){
			if(treeNode.id==1){
				cancelMarkers(map,eventInfos,1);
				//加载车辆相关数据
				$.getJSON(ctx+'/locate/getCarLocateDetail?online=1',function(datas) {
					if(!eventInfos.length || eventInfos.length == 0 ) {
						alert('没有案件需要调度。');
						map.centerAndZoom(cityName,15);
						return false;
					} else {
						initCarInfos(map,datas);
						runRefreshCarInfo(ctx+'/locate/getCarLocateDetail?online=1',map);
					}
				});
			}
			if(treeNode.id==2){
				cancelMarkers(map,eventInfos,2);
				//加载车辆相关数据
				$.getJSON(ctx+'/locate/getCarLocateDetail?online=1',function(datas) {
					if(!eventInfos.length || eventInfos.length == 0 ) {
						alert('没有案件需要调度。');
						map.centerAndZoom(cityName,15);
						return false;
					} else {
						initCarInfos(map,datas);
						runRefreshCarInfo(ctx+'/locate/getCarLocateDetail?online=1',map);
					}
				});
			}
		}
	}
	
	$(function() {
		var setting = {
				data: {
					simpleData: {
						enable: true
					}
				},
				view: {
					fontCss: getFont,
					nameIsHTML: true
				},
				callback: {
					beforeClick: beforeClick,
					onClick: onClick
				}
			};
		
		var zNodes = ${tree};
		if(!zNodes.length) {
			alert('没有找到地区数据。');
		} else {
			$.fn.zTree.init($("#tree"), setting, zNodes);
		}
		map = new BMap.Map("map_canvas");
		map.enableScrollWheelZoom();
		map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
		
		//注册标注事件
		regMarker(map,markTypes,ctx);
		
		//加载车辆相关数据
		$.getJSON(ctx+'/locate/getCarLocateDetail?online=1',function(datas) {
			if(!eventInfos.length || eventInfos.length == 0 ) {
				alert('没有案件需要调度。');
				map.centerAndZoom(cityName,15);
				return false;
			} else {
				initCarInfos(map,datas);
				initEventInfos(map,eventInfos,'${eventInfoId}');
				runRefreshCarInfo(ctx+'/locate/getCarLocateDetail?online=1',map);
			}
		});
		
		regLoding(map);
		
	});
</script>
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../common/head.jsp"%>	
    <ul class="breadcrumb" style="margin-left: 20px; margin-right: 20px;">
		<li><a href="#">车辆位置信息</a> <span class="divider">/</span></li>
		<li class="active"><a href="${ctx}/locate/eventLocate">报案管理</a></li>
	</ul>
    <div class="container-fluid" >
      <div class="row-fluid" >
        <div class="span3">
          <div class="well sidebar-nav">
			<ul id="tree" class="ztree well"  style="background-color:#f5f5f5;border: 0px;overflow: hidden;margin-left:20px;min-height:480px;"></ul>
          </div><!--/.well -->
        </div><!--/span-->
        
        
        <div class="span9">
          <div class="well" style="overflow:hidden">
            	<div id="map_canvas" style="min-height: 480px;min-width: 800px;"></div> 
				<div id="result" ></div>
          </div>
        </div><!--/span-->
      </div><!--/row-->
      
      <!-- 页脚-->
      <%@ include file="../common/footer.jsp"%>	
    </div><!--/.fluid-container-->
</body>
</html>
