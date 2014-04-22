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
<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>

<!-- 百度地图 -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2&ak=F9e138a00e815825002d53430326cac7"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/LuShu/1.2/src/LuShu_min.js"></script>

<!-- 自定义js -->
<script type="text/javascript" src="http://api.map.baidu.com/library/MarkerTool/1.2/src/MarkerTool_min.js"></script>
<script type="text/javascript" src="${ctx}/js/marker.js"></script>

<script type="text/javascript" src="${ctx}/js/eventInfo.js"></script>

<script type="text/javascript" >
	var map = null;
	var ctx = '${ctx}';
	var markTypes = eval('${markTypes}');
	var posArray = new Array();//标记信息(地点相关)
	var circle = null;
	
	//添加标注
	function addMarker(map,point, index){
		var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
		offset: new BMap.Size(10, 25),
		   imageOffset: new BMap.Size(0, 0 - index * 25)
		});
		var marker = new BMap.Marker(point, {icon: myIcon});
		map.addOverlay(marker);
		return marker;
	}
	
	
	// 添加信息窗口
	function showInfoWindow(map,marker,posArray,index){
// 	    var maxLen = 10;
	    var name = null;
// 	    if(posArray[index].type == BMAP_POI_TYPE_NORMAL){
// 	        name = "地址：  ";
// 	    }else if(posArray[index].type == BMAP_POI_TYPE_BUSSTOP){
// 	        name = "公交：  ";
// 	    }else if(posArray[index].type == BMAP_POI_TYPE_SUBSTOP){
// 	        name = "地铁：  ";
// 	    }
	    if(name == null) {
	    	name = "地址：  ";
	    }
	    // infowindow的标题
	    var infoWindowTitle = '<div id="title'+index+'" style="font-weight:bold;color:#CE5521;font-size:14px">'+posArray[index].title+'</div>';
	    // infowindow的显示信息
	    var infoWindowHtml = [];
	    infoWindowHtml.push('<table cellspacing="0" style="table-layout:fixed;width:100%;font:12px arial,simsun,sans-serif"><tbody>');
	    infoWindowHtml.push('<tr>');
	    infoWindowHtml.push('<td  style="vertical-align:top;line-height:16px;width:38px;white-space:nowrap;word-break:keep-all">' + name + '</td>');
	    infoWindowHtml.push('<td id="address'+index+'" style="vertical-align:top;line-height:16px">' + posArray[index].address + ' </td>');
	    infoWindowHtml.push('</tr>');
	    infoWindowHtml.push('</tbody></table>');
	    infoWindowHtml.push('<p></p><p><input style="margin-left:60px" type="button" onclick="tagPoint('+marker.sid+');" class="btn btn-warning" value="标定事故点"/></p>');
	    var infoWindow = new BMap.InfoWindow(infoWindowHtml.join(""),{title:infoWindowTitle,width:200}); 
	    
	    marker.openInfoWindow(infoWindow);
        map.removeOverlay(circle);
//      circle = new BMap.Circle(marker.point,1000,{fillColor:"blue", strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3});
// 		map.addOverlay(circle);
		clickEventMarker(marker);
	}
	
	function getCarIcon(color) {
		var iconUrl = null;
		if(color == 'red') {
			iconUrl = ctx+"/media/images/cars/red.png";//未标注
		} else if(color == 'gray'){
			iconUrl = ctx+"/media/images/cars/gray.png";
		} else if(color == 'green') {
			iconUrl = ctx+"/media/images/cars/green.png";
		}
		
		return new BMap.Icon(iconUrl, 
			new BMap.Size(70, 45), {  
			// 指定定位位置。                                 
			// 当标注显示在地图上时，其所指向的地理位置距离图标左上角各偏移10像素和25像素。您可以看到在本例中该位置即是图标中央下端的尖角位置。                                 
			anchor: new BMap.Size(10, 10),       
			// 设置图片偏移。     
			// 当您需要从一幅较大的图片中截取某部分作为标注图标时，您需要指定大图的偏移位置，此做法与css sprites技术类似。    
		 	imageOffset: new BMap.Size(0, 0)   // 设置图片偏移    
		});
	}
	
	function initPos(_title,_name,_address,_sid) {
		var _obj = posArray[_sid];
		if(!_obj) {
			_obj = {};
			posArray[_sid] = _obj;
		}
        posArray[_sid].sid = _sid;
        posArray[_sid].title = _title;
        posArray[_sid].name = _name;
        posArray[_sid].address = _address;
	}
	
	function clickLeftMenu(index) {
		showInfoWindow(map,eventMarkerArrays[index],posArray,index);
	}
	
	function showLeftMenu(map,posArray,results) {
		var s = [];
        s.push('<ul>');
        for (var i = 0; i < results.getCurrentNumPois(); i ++){
        	//在地图下添加标注点
            var marker = addMarker(map,results.getPoi(i).point,i);
            //初始化所有事件Marker
            initEventMarker(marker,i,results.getPoi(i).address);
            initPos(results.getPoi(i).title,results.getPoi(i).name,results.getPoi(i).address,i);
            
            s.push('<li style="list-style: none;height:10px;" id="list' + i + '">');
            s.push('<b id="titleLeft'+i+'">'+results.getPoi(i).title+'</b>');
            s.push('<a href="javascript:clickLeftMenu(' + i + ')" id="addressLeft'+i+'"> - ' + results.getPoi(i).address + '</a>');
            s.push('</li>');
            s.push('');
            
         	// 默认打开第一标注的信息窗口
            if(i == 0){
                selected = "background-color:#f0f0f0;";
                showInfoWindow(map,marker,posArray,0);
                clickEventMarker(results.getPoi(i));
            }
         
          	//添加事件
          	addEventInfoEvent(map,i);
        }
        s.push('</ul>');
      	$('#l-result').html(s);
	}
	
	//添加案件事件
	function addEventInfoEvent(map,_sid) {
		eventMarkerArrays[_sid].enableDragging();
		eventMarkerArrays[_sid].addEventListener("dragstart", function(e) {
			showInfoWindow(map,this,posArray,this.sid);
		});
		eventMarkerArrays[_sid].addEventListener("dragend", function(e) {
			// 地址解析器 根据经纬度获取当前车辆所在地址
			var gc = new BMap.Geocoder(); 
			var ddd = this.sid;
			gc.getLocation(new BMap.Point(e.point.lng, e.point.lat), function(rs) {
				var addComp = rs.addressComponents;
				var addr= addComp.district + addComp.street + addComp.streetNumber;
				//创建信息窗口，点击标注时显示标注对应的车牌号码以及当前地址
				
				$('#titleLeft'+ddd).text(addr);
				$('#title'+ddd).text(addr);
				$('#addressLeft'+ddd).text(addr);
				$('#address'+ddd).text(addr);
				initPos(addr,addr,addr,ddd);
				eventMarkerArrays[ddd].address = addr;
			});
			map.removeOverlay(circle);
// 		    circle = new BMap.Circle(e.point,1000,{fillColor:"blue", strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3});
// 		 	map.addOverlay(circle);
		});
		eventMarkerArrays[_sid].addEventListener("click", function(e) {
			showInfoWindow(map,this,posArray,this.sid);
		});
	}
	
	function searchAddress(map,cityName,addr) {
		if(!addr) {
			return false;
		}
		var options = {
			onSearchComplete: function(results){
			    // 判断状态是否正确
			    if (local.getStatus() == BMAP_STATUS_SUCCESS){
			    	//显示左边菜单
			    	showLeftMenu(map,posArray,results);
			    }
			}
		};
		
		var local = new BMap.LocalSearch(cityName, options);
		local.search(addr);
	}
	
	
	
	$(function() {
		// 百度地图API功能
		map = new BMap.Map("map_canvas");            // 创建Map实例
		map.centerAndZoom("${cityName}",12);
		map.enableScrollWheelZoom();
		map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
		
		//注册标注事件
		regMarker(map,markTypes,ctx);
	    
		searchAddress(map,"${cityName}","${addr}");
		regLoding(map);
		
	});
	
</script>
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../common/head.jsp"%>	
    <ul class="breadcrumb" style="margin-left: 20px; margin-right: 20px;">
		<li><a href="#">车辆位置信息</a> <span class="divider">/</span></li>
		<li class="active">地点定位搜索</li>
	</ul>
    <div class="container-fluid" >
      <div class="row-fluid" >
        <div class="span3">
          <div class="well sidebar-nav">
            <form class="form-search" >
			  <input type="text" class="input-medium search-query" name="addr" value="${addr }" placeholder="输入地点名称快速搜索">
			  <button type="submit" class="btn">查询</button>
			</form>
			<div id="l-result"></div>
          </div><!--/.well -->
        </div><!--/span-->
        
        
        <div class="span9">
          <div class="well" style="overflow:hidden">
            	<div id="map_canvas" style="min-height: 480px;min-width: 800px;"></div> 
				<div id="result" ></div>
          </div>
        </div><!--/span-->
      </div><!--/row-->
    </div><!--/.fluid-container-->
</body>
</html>
