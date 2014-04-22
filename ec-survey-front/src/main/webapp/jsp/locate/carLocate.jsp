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
<script type="text/javascript" src="${ctx}/js/carInfo.js"></script>

<script type="text/javascript">
	var ctx = '${ctx}';
	var cityName = '${cityName}';
	var zNodes = ${treeStr};
	
	var sid = 0;
	var address;
	var markTypes = eval('${markTypes}');
	var onlineCarCount = 0;
	var allCarCount = 0;
	// 地址解析器 根据经纬度获取当前车辆所在地址
	var gc = new BMap.Geocoder();
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
		if(markerArray[treeNode.carNo]) {
			clickMarker(markerArray[treeNode.carNo],markerArray[treeNode.carNo].sid);
		}
	}

	function clickTr(carNo) {
		if(markerArray[carNo]) {
			clickMarker(markerArray[carNo],markerArray[carNo].sid);
		}
		
		//点击样式的改变
		$(".tr_class_temp").attr("style", "");
		var trInfo = $("#tr_temp_id_"+carNo); 
		trInfo.attr("style", "BACKGROUND-COLOR: #e6f0fc");
		 
	}
	
	function clickMarker(marker, carNo) {
		var surveyUsers = locateArray[carNo].surveyUsers;
		var online = getCarStatusStr(locateArray[carNo].status);
		var content = "<p style='font-size:14px;'>当前车辆：" + locateArray[carNo].carNo + "</p>" + "<p style='font-size:14px;'>在线状态：" + online + "</p>";
		if (surveyUsers) {
			for ( var i = 0; i < surveyUsers.length; i++) {
				var user = surveyUsers[i];
				content += "<p style='font-size:14px;'>驾驶员：" + user.name + "(" + user.tel + ")</p>";
			}
		}
		content += "<p style='font-size:14px;'>方向：" + locateArray[carNo].direction + "</p>";
		content += "<p style='font-size:14px;'>地区：" + locateArray[carNo].areaName + "</p>";

		content += "<p id='readAddr' style='font-size:14px;'>未知</p>";
		content += "<p style='font-size:14px;'>更新时间：" + locateArray[carNo].updateTime + "</p><br/>" + "<p style='font-size:14px;text-align:center;'><a href='orbit?carId="
				+ locateArray[carNo].carId + "' class='btn btn-warning'>查看轨迹</a></p>";
		marker.openInfoWindow(new BMap.InfoWindow(content));

		sid = carNo;

		var addrPoint = new BMap.Point(locateArray[carNo].baiduLongitude,
				locateArray[carNo].baiduLatitude);
		gc.getLocation(addrPoint,
				function(rs) {
					var addComp = rs.addressComponents;
					// var addr= addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;
					var addr = addComp.district + addComp.street
							+ addComp.streetNumber;
					//创建信息窗口，点击标注时显示标注对应的车牌号码以及当前地址
					$('#readAddr').text('具体位置：' + addr);
					address = addr;
				});
	}
	
	
	
	
	
	function initCarInfos(map,datas) {
		allCarCount = 0;
		$('#carLocateBody').empty();
		for ( var i = 0; i < datas.length; i++) {
			var carLocate = datas[i];
			var baiduLatitude = carLocate.baiduLatitude;
			var baiduLongitude = carLocate.baiduLongitude;

			if (i == 0) {//以第一辆车为地图的中心
				if(!markerArray[carLocate.carNo]) {
					map.centerAndZoom(new BMap.Point(baiduLongitude,baiduLatitude),16);
				}
			}

			// 创建标注对象并添加到地图     
			if(markerArray[carLocate.carNo]) {
				map.removeOverlay(markerArray[carLocate.carNo]);
			}
			markerArray[carLocate.carNo] = new BMap.Marker(
				new BMap.Point(baiduLongitude,baiduLatitude)
			); // 创建标注

			//						markerArray[i].enableDragging();
			
			map.addOverlay(markerArray[carLocate.carNo]); // 将标注添加到地图中
			// 						marker1.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画

			//创建信息窗口
			markerArray[carLocate.carNo].sid = carLocate.carNo;
			locateArray[carLocate.carNo] = carLocate;
			//汽车图标
			var color = getCarColor(carLocate.status);
			var labelText = '<p>车牌：' + carLocate.carNo + '</p>';
			var cStatusText = getCarStatusStr(carLocate.status);
			labelText += '<p>在线状态：'+cStatusText+'</p>';
			var myIcon = getCarIcon(color);
			markerArray[carLocate.carNo].setIcon(myIcon);
			allCarCount += 1
			var label = new BMap.Label(
					labelText,
					{offset : new BMap.Size(40, -25)});
			markerArray[carLocate.carNo].setLabel(label);

			//注册点击事件
			markerArray[carLocate.carNo].addEventListener("click",function() {
				clickMarker(this,this.sid);
			});

			//初始化列表
			
			var trBody = '<tr align="center" class="tr_class_temp" id="tr_temp_id_'+carLocate.carNo+'" onclick=clickTr("'+carLocate.carNo+'")>';
			trBody += '<td>' + (i + 1) + '</td>';
			trBody += '<td>' + carLocate.carNo + '</td>';
			trBody += '<td>否</td>';
			var sname = null;
			if (carLocate.surveyUsers) {
				sname = carLocate.surveyUsers[0].name;
			}
			trBody += '<td>' + sname + '</td>';
			trBody += '<td>0</td>';
			trBody += '<td>' + carLocate.imei + '</td>';
			trBody += '<td>' + carLocate.direction + '</td>';
			trBody += '<td>' + carLocate.speed + '</td>';
			trBody += '<td>0</td>';
			trBody += '<td>0</td>';
			trBody += '<td>' + carLocate.updateTime + '</td></tr>';
			$('#carLocateBody').append(trBody);
		}
		$('#allCarCount').text(allCarCount);
		$('#onineCarCount').text(onlineCarCount);
	}
	
// 	var map = new BMap.Map('map_canvas');
	function init(map) {
		map.enableScrollWheelZoom();
		
		
		//加载车辆相关数据
		$.getJSON(ctx+'/locate/getCarLocateDetail?ids=${areaIds}',function(datas) {
				initCarInfos(map,datas);
				runRefreshCarInfo(ctx+'/locate/getCarLocateDetail?ids=${areaIds}',map);
			}
		);
		
	}
	
	$(document).ready(function() {
		var map = new BMap.Map('map_canvas');
		map.enableScrollWheelZoom();
		map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
		//注册标注事件
		regMarker(map,markTypes,ctx);
		if (!zNodes.length) {
// 			alert('没有找到地区数据。');
			map.centerAndZoom(cityName,15);
		} else {
			$.fn.zTree.init($("#areaTree"), setting, zNodes);
			init(map);
		}
		regLoding(map);
	});
</script>
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../common/head.jsp"%>
	<ul class="breadcrumb" style="margin-left: 20px; margin-right: 20px;height: 30px;line-height: 30px;">
		<li><a href="#">车辆位置信息</a> <span class="divider">/</span></li>
		<li class="active"><a href="${ctx}/locate/carLocate">查勘车定位</a></li>
		<li style="float: right;"><a href="#" style="margin-left: 300px;">在线车辆：<span id="onineCarCount">0</span>&nbsp;&nbsp;&nbsp;&nbsp;全部车辆:<span id="allCarCount">0</span><img style="margin-left: 40px;" src="${ctx}/media/images/cars/sred.png""/>救援中<img style="margin-left: 40px;" src="${ctx}/media/images/cars/sgreen.png"/>待命中<img style="margin-left: 40px;" src="${ctx}/media/images/cars/sgray.png"/>离线</a></li>
	</ul>
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div>
				<div class="span3">
					<div class="well sidebar-nav">
						<form class="form-search" style="margin-left: 20px;"
							onsubmit="if(!$.trim($('#keyWord').val())) {$('#keyWord').val('_')}">
							<input type="text" class="input-medium search-query" id="keyWord" name="keyWord"
								value="${keyWord }" placeholder="输入车牌，手机，驾驶员快速搜索">
							<button type="submit" class="btn">查询</button>
						</form>
						<ul id="areaTree" class="ztree"
							style="background-color: #f5f5f5; border: 0px; overflow: hidden;min-height:440px;"></ul>
					</div>
				</div>
				<div class="span9">
					<div class="well" style="overflow:hidden">
						<div id="map_canvas" style="min-height: 480px; min-width: 800px;"></div>
						<div id="result"></div>
					</div>
					<div class="row-fluid">
						<div class="span12" style="max-height: 200px;overflow-y:scroll;">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>序号</th>
										<th>车牌号</th>
										<th>在线状态</th>
										<th>驾驶员</th>
										<th>联系电话</th>
										<th>设备号</th>
										<th>方向</th>
										<th>当前速度</th>
										<th>地区</th>
										<th>具体位置</th>
										<th>更新时间</th>
									</tr>
								</thead>
								<tbody id="carLocateBody">
									<%--             			<c:forEach items="${deviceDatas}" var="dd" varStatus="status"> --%>
									<!-- 		              		<tr align="center"> -->
									<!-- 		              		</tr> -->
									<%-- 		            	</c:forEach> --%>
								</tbody>
							</table>
						</div>
						<!--/span-->
					</div>
					<!--/row-->
				</div>
			</div>
		</div>
	</div>
</body>
</html>