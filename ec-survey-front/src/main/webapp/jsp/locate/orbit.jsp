<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
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
<link rel="stylesheet" href="${ctx}/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link href="${ctx}/media/css/common/load.css" type="text/css" rel="stylesheet" />
	
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
<!-- ztree -->
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.excheck-3.5.js"></script>

<link rel="stylesheet" href="${ctx}/media/js/jquery-ui-1.9.2.custom/css/ui-lightness/jquery-ui-1.9.2.custom.css" />
<script src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.min.js"></script>  	

<!-- 百度地图 -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2&ak=F9e138a00e815825002d53430326cac7"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/LuShu/1.2/src/LuShu_min.js"></script>
<!-- MarkerTool js -->
<script type="text/javascript" src="http://api.map.baidu.com/library/MarkerTool/1.2/src/MarkerTool_min.js"></script>
<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
<script type="text/javascript" src="${ctx}/js/marker.js"></script>
<script type="text/javascript" src="${ctx}/js/orbit.js"></script>
<script type="text/javascript" src="${ctx}/js/carInfo.js"></script>

<!-- 日期控件
<script	 type="text/javascript" src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-timepicker-addon.js"></script> -->
<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>
<script type="text/javascript" >
	var ctx = '${ctx}';
	var currentId = '${selectId}';
	var carSportId = '${carSportId}';
	var cityName = '${cityName}';
	var markTypes = eval('${markTypes}');
	function run() {
		if(lushu) {
			try{
				lushu.start();
			}catch(e) {
				alert(e);				
			}
		}
		return false;
	}
	
	function stop() {
		if(lushu) {
			lushu.stop();
		}
		return false;
	}
	
	function pause() {
		if(lushu) {
			lushu.pause();
		}
		return false;
	}
	
	//初始化日期控件
	function localizeDatepicker(map) {
		$("#startTime,#endTime").datepickerStyle();
	}
	$(document).ready(function(){
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
		
		function getFont(treeId, node) {
			return node.font ? node.font : {};
		}
		
		function beforeClick(treeId, treeNode, clickFlag) {
			return (treeNode.click != false);
		}
		
		function onClick(event, treeId, treeNode, clickFlag) {
			currentId = treeNode.id - 1000;
			currentCarNo = treeNode.carNo;
			if(treeNode.carNo != null) {
				loadOrbit(treeNode.id-1000,map,$('#startTime').val(),$('#endTime').val());
			}
		}
		
		var zNodes = ${treeStr};
		if(!zNodes.length) {
// 			alert('没有找到地区数据。');
		} else {
			$.fn.zTree.init($("#areaTree"), setting, zNodes);
			if(currentId) {
				for(var i=0;i<zNodes.length;i++) {
					var carInfo = zNodes[i];
					if(carInfo.id-1000 == currentId) {
						currentCarNo = carInfo.carNo;
						break;
					}
				}
			}
		}
		
		//百度地图API功能
		var map = new BMap.Map("map_canvas");
		
		map.enableScrollWheelZoom();
		map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
		//注册标注事件
		regMarker(map,markTypes,ctx);
		
		curMap = map;
		
		
		if(carSportId) {
			clickOrbitTr(carSportId);
		} else if(currentId) {
			loadOrbit(currentId,map);
		} else {
			map.centerAndZoom(cityName,15);
		}
		
		var f = true;
		$('#run').click(function(){
			
			if(f){
				lushu._opts.speed = 500;
				if(lushu._marker) {
					lushu._marker.closeInfoWindow();
				}
				lushu._opts.defaultContent = "车牌号："+currentCarNo+"<br/>当前播放速度："+lushu._opts.speed,
			    speed=lushu._opts.speed;
				run();
// 				lushu._marker.removeEventListener('click');
// 				lushu._marker.addEventListener("click",function(e) {
// 					alert('myIcon');
// 				});
				f = false;
			} else {
				pause();
				
				var nextPoint =lushu._path[lushu.i];
				if(nextPoint) {
					var gc = new BMap.Geocoder();
					gc.getLocation(nextPoint, function(rs) {
						var addComp = rs.addressComponents;
						var addr = addComp.province + addComp.city + addComp.district
								+ addComp.street + addComp.streetNumber;
// 						alert(addr);
						var infoWindow = new BMap.InfoWindow("当前位置："+addr, {
// 							title : "当前车辆位置<br/>("+nextPoint.lng+","+nextPoint.lat+")",
							title : "车牌号："+currentCarNo+"<br/>",
							width : 230,
							height : 100
						});
// 						for(var ii in lushu) {
// 							alert(ii);
// 						}
						lushu._marker.openInfoWindow(infoWindow);
					});
				}
				f = true;
			}
		});
		$('#slow').click(function() {
			lushu._opts.speed=speed/2;
			if(lushu._opts.speed < 500) {
				lushu._opts.speed = 500;
			}
			lushu._opts.defaultContent = "车牌号："+currentCarNo+"<br/>当前播放速度："+lushu._opts.speed,
		    speed=lushu._opts.speed;
		});
		$('#acc').click(function() {
			lushu._opts.speed=speed*2;
			if(lushu._opts.speed > 5000) {
				lushu._opts.speed = 5000;
			}
			lushu._opts.defaultContent = "车牌号："+currentCarNo+"<br/>当前播放速度："+lushu._opts.speed,
			speed=lushu._opts.speed;
		});
		regLoding(map);
		localizeDatepicker();//加载日期控件
		
		$("#searchForTime").bind("click",function(){
			searchTimeForOrbit(map);
		});
		//清空日期
		$("#clearTime").bind("click",function(){
			$("#startTime").val("");
			$("#endTime").val("");
		});
	});
	
	function searchTimeForOrbit(map){
		var st=$("#startTime").val();
		var et=$("#endTime").val();
		if(st!=null){
			loadOrbit(currentId,map,st,et);
		}else {
			loadOrbit(currentId,map);
		}
	}
	
</script>
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../common/head.jsp"%>	
     <ul class="breadcrumb" style="margin-left: 20px; margin-right: 20px;">
		<li><a href="#">车辆位置信息</a> <span class="divider">/</span></li>
		<li class="active"><a href="${ctx}/locate/orbit">轨迹回放</a></li>
	</ul>
    <div class="container-fluid" >
      <div class="row-fluid" >
        <div class="span3">
          <div class="well sidebar-nav">
			<form class="form-search" style="margin-left:20px;" onsubmit="if(!$.trim($('#keyWord').val())) {$('#keyWord').val('_')}">
			  <input type="text" class="input-medium search-query" id="keyWord" name="keyWord" value="${keyWord }" placeholder="输入车牌，手机，驾驶员快速搜索">
			  <button type="submit" class="btn">查询</button>
			</form>
			<ul id="areaTree" class="ztree well"  style="background-color:#f5f5f5;border: 0px;overflow: hidden;margin-left:20px;margin-right: 20px"></ul>
         
          	<div style="margin-top: 50px;margin-left:20px;">
          		 
         		<div class="control-group">
					<label class="control-label">请选择查询日期</label>
				</div>
				<div class="control-group">
					<div class="controls">
						<input type="text" id="startTime" name="startTime" readonly="readonly" value="${param.startTime }" style="width: 100px;font-size: 9pt;">&nbsp;&nbsp;至&nbsp;&nbsp;
	          			<input type="text" id="endTime" name="endTime"  readonly="readonly" value="${param.endTime }" style="width: 100px;font-size: 9pt;"><br/>
					</div>
					<div class="controls">
					</div>
				</div>
	         	<div style="margin-left:40px;">
	         		<input id="clearTime" type="button"  id="run" class="btn btn-warning"  title="清空日期" value="清空日期"/>
					<input id="searchForTime" type="button" id="run" class="btn btn-warning"  title="查询" value="查询"/>
				</div>
		        
	          	<div style="margin-left:15px;margin-top: 15px">
		          	<input type="button" id="run" class="btn btn-warning" title="播放" value="播放/暂停"/>
		          	<input type="button" id="slow"  class="btn btn-warning" title="慢放" value="2x慢放"/>
		          	<input type="button" id="acc" class="btn btn-warning" title="快进" value="2x快进"/>
	          	</div>
         	</div>
          </div><!--/.well -->
        </div><!--/span-->
        
        
        <div class="span9">
          <div class="well" style="overflow:hidden">
            	<div id="map_canvas" style="min-height: 480px;min-width: 800px;"></div> 
				<div id="result" ></div>
		  </div>
			<div class="row-fluid">
				<div class="span12" style="max-height: 200px; overflow-y: scroll;">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>序号</th>
								<th>行程起止时间</th>
								<th>行驶时长</th>
								<th>累计里程</th>
								<th>起始地址</th>
								<th>终点地址</th>
							</tr>
						</thead>
						<tbody id="carLocateBody">
						</tbody>
					</table>
				</div><!--/span-->
			</div><!--/row-->
			</div><!--/span-->
      </div><!--/row-->
    </div><!--/.fluid-container-->
</body>
</html>
