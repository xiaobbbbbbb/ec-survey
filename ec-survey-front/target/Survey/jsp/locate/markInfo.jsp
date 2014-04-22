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
<script type="text/javascript" src="${ctx}/js/orbit.js"></script>

<script type="text/javascript">
	var ctx = '${ctx}';
	
	var sid = 0;
	var address;
	var markInfos=${marks};
	var eventInfos=${eventinfos};
	
	// 地址解析器 根据经纬度获取当前在地址
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
	}
		
// 	var map = new BMap.Map('map_canvas');
$(document).ready(function(){
	//加载地图
	var map = new BMap.Map('map_canvas');
	var src1=ctx+"/media/images/marker/mark_bj.png";
	var src2=ctx+"/media/images/marker/mark_sg.png";
	map.enableScrollWheelZoom();
	map.addControl(new BMap.NavigationControl()); //添加默认缩放平移控件	
	// 创建控件
	var myZoomCtrl1 = new ZoomControl(src1,"标记点",new BMap.Size(70, 10));
	var myZoomCtrl2 = new ZoomControl(src2,"事故点",new BMap.Size(70, 90));
	map.addControl(myZoomCtrl1);
	map.addControl(myZoomCtrl2);
	//map.centerAndZoom(new BMap.Point(113.94001,22.862172),16);
	//注册标注事件
	//regMarker(map,markTypes,ctx);
	if(markInfos.length>0){
		initMarks(map,markInfos,src1,1);
	}if(eventInfos.length>0){
		initMarks(map,eventInfos,src2,2);
	}
	regLoding(map);

})	
	
	
	function initMarks(map,datas,src,flag){
		for ( var i = 0; i < datas.length; i++) {
			var markinfo = datas[i];
			var baiduLatitude=0;
			var baiduLongitude=0;
			if(flag==1){
				 baiduLatitude = markinfo.baiduLatitude;
				 baiduLongitude = markinfo.baiduLongitude;
			}if(flag==2){
				 baiduLatitude = markinfo.latitude;
				 baiduLongitude = markinfo.longitude;
			}
 			if (i == 0&&flag==1) {//以第一个点为地图的中心
 				if(!markerArray[markinfo.name+markinfo.id]) {
 					map.centerAndZoom(new BMap.Point(baiduLongitude,baiduLatitude),16);
 				}
 			}
 			if(i == 0&&markInfos.length==0&&flag==2){
 				if(!markerArray[markinfo.name+markinfo.id]) {
 					map.centerAndZoom(new BMap.Point(baiduLongitude,baiduLatitude),16);
 				}
 			}
			// 创建标注对象并添加到地图     
			if(markerArray[markinfo.name+markinfo.id]) {
				map.removeOverlay(markerArray[markinfo.name+markinfo.id]);
			}
			markerArray[markinfo.name+markinfo.id] = new BMap.Marker(
				new BMap.Point(baiduLongitude,baiduLatitude)
			); // 创建标注

			//						markerArray[i].enableDragging();
			
			map.addOverlay(markerArray[markinfo.name+markinfo.id]); // 将标注添加到地图中
			// 						marker1.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画

			//创建信息窗口
			markerArray[markinfo.name+markinfo.id].sid = markinfo.name+markinfo.id;
			locateArray[markinfo.name+markinfo.id] = markinfo;
			//标记图标
			var myIcon = new BMap.Icon(src,
					new BMap.Size(70, 45), {
				// 指定定位位置。
				// 当标注显示在地图上时，其所指向的地理位置距离图标左上角各偏移10像素和25像素。您可以看到在本例中该位置即是图标中央下端的尖角位置。
				anchor: new BMap.Size(10,25),
				// 设置图片偏移。
				// 当您需要从一幅较大的图片中截取某部分作为标注图标时，您需要指定大图的偏移位置，此做法与css sprites技术类似。
				imageOffset: new BMap.Size(0, 0) // 设置图片偏移
				}); 
			markerArray[markinfo.name+markinfo.id].setIcon(myIcon);

			//注册点击事件
			markerArray[markinfo.name+markinfo.id].addEventListener("click",function() {
				clickMarke(this,this.sid,flag);
			});
		}
	}
	
	function clickMarke(marker, carNo,flag) {
		content = "<p id='readAddr' style='font-size:14px;'>未知</p>";
		marker.openInfoWindow(new BMap.InfoWindow(content));
		sid = carNo;
		var addrPoint =null;
		if(flag==1){
			addrPoint = new BMap.Point(locateArray[carNo].baiduLongitude,
				locateArray[carNo].baiduLatitude);
		}if(flag==2){
			addrPoint = new BMap.Point(locateArray[carNo].longitude,
					locateArray[carNo].latitude);
		}
		gc.getLocation(addrPoint,function(rs) {
				var addComp = rs.addressComponents;
				// var addr= addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;
				var addr = addComp.district + addComp.street
						+ addComp.streetNumber;
				//创建信息窗口，点击标注时显示标注对应的车牌号码以及当前地址
				$('#readAddr').text('具体位置：' + addr);
				address = addr;
		});
	}
		
	
	// 定义一个控件类,即function 默认停靠位置和偏移量
	function ZoomControl(url,title,offset) {
		this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
		this.defaultOffset = offset;
		this._url=url;
		this._title=title;
	}

	// 添加的自定图层
	ZoomControl.prototype = new BMap.Control();
	ZoomControl.prototype.initialize = function(map) {

		var divSite = document.createElement("div");
		// 创建一个DOM元素
		var div = document.createElement("div");
		// 添加文字说明
		div.innerHTML = this._title;
		div.textSize = "10px";
		div.style.height = 32;
		div.style.lineHeight = 2;
		div.style.width = 50;
		divSite.style.height = 25;
		divSite.appendChild(div);
		var img = document.createElement("div");
		img.style.height = 28;
		img.style.width = 28;
		img.style.cursor = "pointer";
		img.style.marginLeft = 15;
		img.style.marginTop = 5;
		var imgs = document.createElement("img");
		imgs.src =this._url;// 图标图片
		//imgs.style.background="#CCCCCC";
		imgs.style.height = 5;
		imgs.style.width = 5;
		imgs.style.marginTop = 2;
		imgs.style.marginLeft = 2;
		img.appendChild(imgs);
		divSite.appendChild(img);
		// 绑定事件,锁定
		var noclick = true;
		var title = this._title;

		//显示点击的标记
		img.onclick = 	function (e) {
			var data1=null;
			var data2=null;
			if(title=="标记点"){
				data1=eventInfos;
				data2=markInfos;
			}else{
				data2=eventInfos;
				data1=markInfos;
			}
			if(data1!=null){
				for(var i=0;i<data1.length;i++){
					var mark1=data1[i];
					map.removeOverlay(markerArray[mark1.name+mark1.id]);
				}
			}if(data2!=null){
				for(var i=0;i<data2.length;i++){
					var mark2=data2[i];
					map.addOverlay(markerArray[mark2.name+mark2.id]);
				}
			}
		}
		img.onmouseover = function(e) {
			this.style.border = "1px solid #aaaaaa";
		}
		img.onmouseout = function(e) {
			if (noclick)
				this.style.border = "1px solid #ffffff";
		}

		// 添加DOM元素到地图中
		map.getContainer().appendChild(divSite);
		// 将DOM元素返回
		return divSite;
	}
	
	function changeMark(data1,data2) {
		for(var i=0;i<data1.length;i++){
			var mark1=data1[i];
			map.removeOverlay(markerArray[mark1.name+mark1.id]);
		}
// 		for(var i=0;i<data2.length;i++){
// 			var mark2=data2[i];
// 			map.addOverlay(markerArray[mark2.name+mark2.id]);
// 		}
	}
</script>
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../common/head.jsp"%>
	<ul class="breadcrumb" style="margin-left: 20px; margin-right: 20px;height: 30px;line-height: 30px;">
		<li><a href="#">统计报表</a> <span class="divider">/</span></li>
		<li class="active">事故位置分析</li>
		
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
					
						<!--/span-->
					</div>
					<!--/row-->
				</div>
			</div>
		</div>
	</div>
</body>
</html>