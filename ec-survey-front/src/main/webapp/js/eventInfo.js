/**
 * 案件调度相关 2013-08-28 xiaodx
 */

var curEventMarker = null;//当前案件点
var curEventInfo = null;//当前案件对象
var eventArrays = [];//所有案件
var eventMarkerArrays = [];//所有案件
var curCarMarker = null;//当前查勘车
var carArrays = [];//查勘车
var curCarInfo = null;//当前车辆
var carMarkerArrays = [];//查勘车标记

function initCarInfos(map,datas) {
	for(var i=0;i<datas.length;i++) {
		var carLocate = datas[i];
		var baiduLatitude = carLocate.baiduLatitude;
		var baiduLongitude = carLocate.baiduLongitude;
		
//		if(i==0) {//以第一辆车为地图的中心
//			map.centerAndZoom(new BMap.Point(baiduLongitude, baiduLatitude), 16);
//		}
		
		//汽车图标
		var color = getCarColor(carLocate.status);
		
		var myIcon = getCarIcon(color);
		
		var sid = carLocate.carNo;
		// 创建标注对象并添加到地图     
		if(carMarkerArrays[sid]) {
			map.removeOverlay(carMarkerArrays[sid]);
		}
		carMarkerArrays[sid] = new BMap.Marker(new BMap.Point(baiduLongitude, baiduLatitude),{ icon: myIcon });  // 创建标注
		carMarkerArrays[sid].sid = sid;
//		map.removeOverlay(carMarkerArrays[sid]);
		map.addOverlay(carMarkerArrays[sid]);              // 将标注添加到地图中

		carArrays[sid] = datas[i];
		
		var label = new BMap.Label(carLocate.carNo,{offset:new BMap.Size(40,-25)});
		carMarkerArrays[sid].setLabel(label);
		
		carMarkerArrays[sid].addEventListener("click", function(){
//				alert('this.sid='+this.sid);
			clickSid = this.sid;
			curCarMarker = this;
			curCarInfo = carArrays[this.sid];
			var online = getCarStatusStr(carArrays[this.sid].status);
			var surveyUsers = carArrays[this.sid].surveyUsers;
			
			var content = "<p style='font-size:14px;'>当前车辆："+carArrays[this.sid].carNo+"</p><br/>";
			content += "<p id='cross' style='font-size:14px;'>距离：未知</p>";
			content += "<p id='crossTime' style='font-size:14px;'>预计:未知</p><br/>";
			content += "<p style='font-size:14px;'>在线状态："+online+"</p>";
			
			if(surveyUsers) {
				for(var i=0;i<surveyUsers.length;i++) {
					var user = surveyUsers[i];
					content += "<p style='font-size:14px;'>驾驶员："+user.name+"("+user.tel+")</p>";
				}
			}
			
			if(carArrays[this.sid].eventInfos) {
				content += "<p style='font-size:14px;color:red;'>当前任务量：("+carArrays[this.sid].eventInfos.length+")</p>";
			} else {
				content += "<p style='font-size:14px;color:red;'>当前任务量：(0)</p>";
			}
			content += "<p style='font-size:14px;'>方向："+carArrays[this.sid].direction+"</p>";
			content += "<p style='font-size:14px;'>地区："+carArrays[this.sid].areaName+"</p>";
			
			content += "<p id='realAddr' style='font-size:14px;'>未知</p>";
			
			content +="<p style='font-size:14px;'>更新时间："+carArrays[this.sid].updateTime+"</p><br/>";
			
//	 		alert('markInfo.eventInfo.status='+markInfo.eventInfo.status);
			if(curEventInfo) {
				if(curEventInfo.status == 0) {
					content +="<p style='font-size:14px;text-align:center;'><input type='button' class='btn btn-warning' onclick=support('"+curEventInfo.id+","+this.sid+"') value='调度救援'/></p>";
				} else {
					content +="<p style='font-size:14px;text-align:center;'><input type='button' class='btn btn-warning disabled' value='调度救援'/></p>";
				}
				var searchComplete = function (results){
			        var plan = results.getPlan(0);
					if(plan) {
						carMarkerArrays[clickSid].openInfoWindow(new BMap.InfoWindow(content));
						$('#cross').text('距离：'+plan.getDistance(true));
				        $('#crossTime').text('预计:'+plan.getDuration(true));
				     	// 地址解析器 根据经纬度获取当前车辆所在地址
						var gc = new BMap.Geocoder(); 
				     	gc.getLocation(new BMap.Point(carArrays[clickSid].baiduLongitude, carArrays[clickSid].baiduLatitude), function(rs) {
							var addComp = rs.addressComponents;
							var addr= addComp.district + addComp.street + addComp.streetNumber;
							 //创建信息窗口，点击标注时显示标注对应的车牌号码以及当前地址
							$('#realAddr').text('具体位置：'+addr);
						});
					}
				}
				//路径
				var transit = new BMap.DrivingRoute(map, {
					renderOptions: {map: map},
				    onSearchComplete:  searchComplete,
				    onPolylinesSet: function(){        
				        setTimeout(function(){
				        	
				        },"1000");
				    }
				});
				transit.search(new BMap.Point(carArrays[this.sid].baiduLongitude, carArrays[this.sid].baiduLatitude), curEventMarker.point);
				
//				carMarkerArrays[clickSid].openInfoWindow(new BMap.InfoWindow(content));
				}
		});
	
	};
}

function getLevel(meters) {
	//{"17" ,"16" ,"15"  ,"14"  ,"13"  ,"12" ,"11" ,"10" ,"9"   ,"8"   ,"7"   ,"6"   ,"5"    ,"4"    ,"3"    ,"2"     ,"1"}
	//{"20m","50m","100m","200m","500m","1km","2km","5km","10km","20km","25km","50km","100km","200km","500km","1000km","2000km"}
	if(meters<= 1) {
		return 16;
	}
	if(meters<= 2) {
		return 15;
	}
	if(meters<= 3) {
		return 14;
	}
	if(meters<= 4) {
		return 13;
	}
	if(meters<= 5) {
		return 12;
	}
	if(meters<= 6) {
		return 11;
	}
	if(meters<= 7) {
		return 10;
	}
	if(meters<= 8) {
		return 9;
	}
	if(meters<= 9) {
		return 8;
	}
	if(meters<= 10) {
		return 7;
	}
	if(meters<= 11) {
		return 6;
	}
	if(meters<= 12) {
		return 5;
	}
	return 14;
}

function checkCancelInfo(eventInfoId){
	if ($('#reason').val() == '') {
		$('#reasonDiv').removeClass('success').addClass('error');
		$('#reasonLabel').text('取消原因为空!');
		return false;
	} else {
		$('#reasonDiv').removeClass('error').addClass('success');
		$('#reasonLabel').text('');
	}
	if ($('#cancelName').val() == '') {
		$('#cancelNameDiv').removeClass('success').addClass('error');
		$('#cancelNameLabel').text('取消人为空!');
		return false;
	} else {
		$('#cancelNameDiv').removeClass('error').addClass('success');
		$('#cancelNameLabel').text('');
	}
	var cancelreason=$('#reason').val();
	var cancelpeople=$('#cancelName').val();
	var params = '&eventInfoId='+eventInfoId+"&status=3"+"&cancelreason="+cancelreason+"&cancelpeople="+cancelpeople;
	params = encodeURI(params);
	$.post(curCtx+"/locate/modifyEventInfo",params ,function(data){
		if(data.status == 200) {
			window.location=curCtx+'/locate/eventLocate';
			map.removeOverlay(curEventMarker);
			map.removeOverlay(circle);		
		}
	});
	
} 
function cancelEvent(eventInfoId) {
	var infoWindowHtml = [];
	
	infoWindowHtml.push('<div id="reasonDiv" class="control-group success">');
	infoWindowHtml
			.push('<label class="control-label " for="inputEmail">取消原因</label>');
	infoWindowHtml
			.push('<textarea id="reason" name="reason" rows="3"></textarea>');
	infoWindowHtml.push('<span id="reasonLabel" class="help-inline"></span>');
	infoWindowHtml.push('</div>');
	infoWindowHtml.push('<div id="cancelNameDiv" class="control-group success">');
	infoWindowHtml
			.push('<label class="control-label" for="inputEmail">取消人</label>');
	infoWindowHtml
			.push('<input type="text" id="cancelName" name="cancelName" >');
	infoWindowHtml.push('<span id="cancelNameLabel" class="help-inline"></span>');
	infoWindowHtml.push('</div>');
	infoWindowHtml.push('<div class="control-group">');
	infoWindowHtml
			.push('<button onclick="checkCancelInfo('+eventInfoId+')"_curMkr="btn btn-warning">确定</button>');
	infoWindowHtml.push('</div>');
	
	var infoWindow = new BMap.InfoWindow(infoWindowHtml.join(""), {
		width : 300,
		height : 250
	});
	curEventMarker.openInfoWindow(infoWindow);

}

function clickEventMarker(_marker,eventInfoId,map) {
	if(map){
		map.addOverlay(_marker);
	}
	curEventMarker = _marker;
	if(!eventMarkerArrays[_marker.sid]) {
		eventMarkerArrays[_marker.sid] = _marker;
	}
	if(eventInfoId) {
		curEventInfo = eventArrays[eventInfoId];
		if(!curEventInfo) {
			return;
		}
		//阴影
		//map.removeOverlay(circle);
        //circle = new BMap.Circle(eventMarkerArrays[curEventMarker.sid].point,1000,{fillColor:"blue", strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3});
		//map.addOverlay(circle);
		
		var content = '<p>名称：'+curEventInfo.name+'</p>';
		
		if(curEventInfo.status == -1) {
			content += '<p>状态：未标定</p>';
		} else if(curEventInfo.status==0){
			content += '<p>报案状态：已报案未调度</p>';
		} else if(curEventInfo.status == 1){
			content += '<p>报案状态：已调度未完成</p>';
		}else if(curEventInfo.status == 2){
			content += '<p>报案状态：调度完成</p>';
		}else if(curEventInfo.status == 3){
			content += '<p>报案状态：案件取消</p>';
			content += '<p>取消原因：'+curEventInfo.cancelreason+'</p>';
			content += '<p>取消人：'+curEventInfo.cancelpeople+'</p>';
		}else if (curEventInfo.status == 4){
			content += '<p>报案状态：已终结</p>';
		}
			content += '<p>报案时间：'+new Date(curEventInfo.createTime).toLocaleTimeString()+'</p>';
		
		content += '<p id="addressP">具体位置：'+curEventInfo.address+'</p>';
		var gc = new BMap.Geocoder(); 
		gc.getLocation(new BMap.Point(curEventMarker.point.lng,curEventMarker.point.lat), function(rs) {
			var addComp = rs.addressComponents;
			var addr = addComp.district + addComp.street + addComp.streetNumber;
			$('#addressP').text('具体位置：'+addr+'');
		});
		
		if(curEventInfo.status == -1) { //未标注
			content += '<p style="font-size:14px;text-align:center;"><input type="button" class="btn btn-warning" onclick="tagPoint(0,'+curEventMarker.sid+')" value="取消事故点"/></p>';
		} 
		if(curEventInfo.status == 0 || curEventInfo.status == 1) {
			content += '<p style="font-size:14px;text-align:center;"><input type="button" class="btn btn-warning" onclick="cancelEvent('+curEventMarker.sid+')" value="取消案件"/></p>';
		}
		map.centerAndZoom(new BMap.Point(curEventMarker.point.lng,curEventMarker.point.lat),15);
		
		var cm = _marker;
		curEventMarker.openInfoWindow(new BMap.InfoWindow(content));
		//TODO 根据比例尺计算绽放级别
//		map.addEventListener("click",function(e2){
//			curEventMarker.openInfoWindow(new BMap.InfoWindow(content));
//		});
		
		var _flag = 0;
//		for(var c in carArrays) {
//			_flag = 1;
//			new BMap.DrivingRoute(map, {
//				renderOptions: {map: map},
//			    onSearchComplete: function (results){
//			    	curEventMarker.openInfoWindow(new BMap.InfoWindow(content));
//	 				var plan = results.getPlan(0);
//					if(plan) {
//						//{"20m","50m","100m","200m","500m","1km","2km","5km","10km","20km","25km","50km","100km","200km","500km","1000km","2000km"}
////						var level = getLevel(plan.getDistance(true));
////						alert('level='+level);
////						map.centerAndZoom(new BMap.Point(cm.point.lng,cm.point.lat),level);
////				        var labelContent = '<p>'+c+'</p>';
////				        labelContent += '<p>距离：'+plan.getDistance(true)+'</p>';
////				        labelContent += '<p>预计:'+plan.getDuration(true)+'</p>';
////				        var label = new BMap.Label(labelContent,{offset:new BMap.Size(40,-25)});
////				        alert('c='+labelContent);
////						carMarkerArrays[c].setLabel(label);
//					}
//				},
//			    onPolylinesSet: function(){        
//			        setTimeout(function(){},"1000");
//			    }
//			}).search(new BMap.Point(carArrays[c].baiduLongitude, carArrays[c].baiduLatitude), curEventMarker.point);
//		}
		if(curEventInfo.status!=0){
			var driving = new BMap.DrivingRoute(map, {
			    renderOptions: {
			        map: map
			    }
			});
			driving.search(new BMap.Point(curEventInfo.carLongitude,curEventInfo.carLatitude),curEventMarker.point);
		}
		if(_flag == 0) {
			curEventMarker.addEventListener("click", function(){
				curEventMarker.openInfoWindow(new BMap.InfoWindow(content));
			});
		}
	}
}

//function addMarker(map,_marker) {
//	
//}

function getIcon(color) {
	var iconUrl = null;
	if(color == 'blue') {
		iconUrl = curCtx+"/media/images/marker/marker_blue.png";//未标注
	} else {
		iconUrl = curCtx+"/media/images/marker/marker_red.png";
	}
	
	return new BMap.Icon(
			iconUrl,
			new BMap.Size(70,45),
			{
				// 指定定位位置。                                 
				// 当标注显示在地图上时，其所指向的地理位置距离图标左上角各偏移10像素和25像素。您可以看到在本例中该位置即是图标中央下端的尖角位置。                                 
				anchor : new BMap.Size(30, 10),
				// 设置图片偏移。     
				// 当您需要从一幅较大的图片中截取某部分作为标注图标时，您需要指定大图的偏移位置，此做法与css sprites技术类似。    
				imageOffset : new BMap.Size(0, 0)
			// 设置图片偏移    
			});
	 
}



function initEventInfos(map,eventInfos,_eventInfoId) {
	if(!eventInfos.length || eventInfos.length == 0 ) {
		alert('没有案件需要调度。');
		return false;
	}
	var cccid = null;
	for(var i=0;i<eventInfos.length;i++) {
		var eventInfo = eventInfos[i];
		cccid = eventInfo.id;
		var baiduLongitude = eventInfo.baiduLongitude;
		var baiduLatitude = eventInfo.baiduLatitude;
		var sid = eventInfo.id;
		eventArrays[sid] = eventInfo;
		
		var myIcon = getMarkerIcon(eventInfo.status);
		// 创建标注对象并添加到地图     
		eventMarkerArrays[sid] = new BMap.Marker(
				new BMap.Point(baiduLongitude,baiduLatitude),
				{ icon: myIcon }); // 创建标注
		eventMarkerArrays[sid].sid = sid;
		
		initEventMarker(eventMarkerArrays[sid],sid,'');
		eventMarkerArrays[sid].addEventListener("click",function() {
			clickEventMarker(this,eventArrays[this.sid].id,map);
		});
		if((_eventInfoId==''||_eventInfoId==null)){//未调度的点
			if(eventInfo.status==0){
				map.addOverlay(eventMarkerArrays[sid]); // 将标注添加到地图中
			}
			else{
				continue;
			}
		}
		
		else if(_eventInfoId==eventInfo.id){
			map.addOverlay(eventMarkerArrays[sid]); // 将标注添加到地图中
		}
		
		
		//事故点移动
		eventMarkerArrays[sid].enableDragging();
		eventMarkerArrays[sid].addEventListener("dragend", function(e){
			var params = 'eventInfoId='+eventArrays[this.sid].id+'&lng='+e.point.lng+'&lat='+e.point.lat;
			var eventInfoId = this.sid;
			// 地址解析器 根据经纬度获取当前车辆所在地址
			var gc = new BMap.Geocoder(); 
			gc.getLocation(new BMap.Point(e.point.lng,e.point.lat), function(rs) {
				var addComp = rs.addressComponents;
				var addr = addComp.district + addComp.street + addComp.streetNumber;
				params += '&address='+addr;
				params = encodeURI(params);
				$.post(curCtx+"/locate/updatePosition",params ,function(data){
					var infoWindow = new BMap.InfoWindow(data.message);
					eventMarkerArrays[eventInfoId].openInfoWindow(infoWindow);
					if(data.status == 200) {
						eventInfo[eventInfoId] = data.result;
//						map.removeOverlay(circle);
//				        circle = new BMap.Circle(eventMarkerArrays[eventInfoId].point,1000,{fillColor:"blue", strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3});
//						map.addOverlay(circle);
						
						//标记为当前Marker
						curEventMarker = eventMarkerArrays[eventInfoId];
					}
				});
			});
			map.centerAndZoom(new BMap.Point(e.point.lng,e.point.lat),16);
		});
	};
	if(eventMarkerArrays[_eventInfoId]) {
		map.centerAndZoom(new BMap.Point(eventMarkerArrays[_eventInfoId].point.lng,eventMarkerArrays[_eventInfoId].point.lat),15);
//		alert('1eventInfoId = '+_eventInfoId);
//		selectOne(_eventInfoId);
		clickEventMarker(eventMarkerArrays[_eventInfoId],_eventInfoId,map);
		
	} else if(cccid) {
		map.centerAndZoom(new BMap.Point(eventMarkerArrays[cccid].point.lng,eventMarkerArrays[cccid].point.lat),15);
//		alert('2eventInfoId = '+cccid);
//		selectOne(cccid);
//		clickEventMarker(eventMarkerArrays[cccid],cccid,map);
		
	}
}

function selectOne(sid) {
	clickEventMarker(eventMarkerArrays[sid],sid,map);
//	map.centerAndZoom(eventMarkerArrays[sid].point,16);
//    circle = new BMap.Circle(eventMarkerArrays[sid].point,1000,{fillColor:"blue", strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3});
//	map.addOverlay(circle);
//	curEventMarker = eventMarkerArrays[sid];
}

function initEventMarker(_marker,_sid,_address) {
	_marker.sid = _sid;
	_marker.address = _address;
	curEventMarker = _marker;
	eventMarkerArrays[_sid] = _marker;
}

//数据校验
function checkEventInfo() {
	
	if($('#name').val()=='') {
		$('#nameDiv').removeClass('success').addClass('error');
		$('#nameLabel').text('案件名称不能为空!');
		return false;
	} else {
		$('#nameDiv').removeClass('error').addClass('success');
		$('#nameLabel').text('');
	}
	
	if($('#eventDesc').val()=='') {
		$('#eventDescDiv').removeClass('success').addClass('error');
		$('#eventDescLabel').text('标注描述不能为空!');
		return false;
	} else {
		$('#eventDescDiv').removeClass('error').addClass('success');
		$('#eventDescLabel').text('');
	}
	
	if(!$('#addressId').val()) {
		alert('位置修改失败，百度地图无法定位该位置,请重新标记。');
		curMap.removeOverlay(curMkr);
		return false;
	}
	
	$('#eventForm').submit();
	
	var params = $("#eventForm").serialize();
	params = encodeURI(params);
	$.post(curCtx+"/locate/tagPoint",params ,function(data){
//		if(data.status == 200) {
////			window.location=ctx+'/locate/eventLocate?markInfoId='+data.result.id;
//			infoWindow = new BMap.InfoWindow(data.message);
//		} else {
//			infoWindow = new BMap.InfoWindow("操作失败。");
//		}
		var infoWindow = null;
		if(data.status != 200) {
			alert('位置修改失败，找不到地点名称。');
//			infoWindow = new BMap.InfoWindow('位置修改失败，找不到地点名称。');
			curMap.removeOverlay(curMkr);
			return false;
		} else {
			var data2Content = data.message;
			data2Content += '<p style="font-size:14px;text-align:center;"><a type="button" class="btn btn-warning" href="'+curCtx+'/locate/eventLocate?eventInfoId='+data.result.id+'" >立即调度</a></p>';
			infoWindow = new BMap.InfoWindow(data2Content);
		}
		curEventMarker.openInfoWindow(infoWindow);
//		//添加移动事件
//		curEventMarker.addEventListener("dragend", function(e){ 
//			var params = 'markInfoId='+data.result.id+'&lng='+e.point.lng+'&lat='+e.point.lat;
//			// 地址解析器 根据经纬度获取当前车辆所在地址
//			var gc = new BMap.Geocoder(); 
//			gc.getLocation(new BMap.Point(e.point.lng,e.point.lat), function(rs) {
//				var addComp = rs.addressComponents;
//				var addr = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
//				params += '&address='+addr;
//				params = encodeURI(params);
//				$.post(curCtx+"/locate/updateMarkerPosition",params ,function(data2){
//					var infoWindow = new BMap.InfoWindow(data2.message);
//					curMkr.openInfoWindow(infoWindow);
////					if(data2.status == 200) {
////						curMap.removeOverlay(circle);
////				        circle = new BMap.Circle(e.point,1000,{fillColor:"blue", strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3});
////				        curMap.addOverlay(circle);
////					}
//				});
//			});
//			curMap.centerAndZoom(new BMap.Point(e.point.lng,e.point.lat),16);
//		});
		
//		curMkr = null;
	});
	
	return true;
}

//隐藏字段
var gc = new BMap.Geocoder();

function tagPoint(type) {
	var addr = curEventMarker.address;//目标地点
	
	gc.getLocation(curEventMarker.point, function(rs) {
		var addComp = rs.addressComponents;
		addr = addComp.province + addComp.city + addComp.district
				+ addComp.street + addComp.streetNumber;
	});
	
// 		var areaId = locateArray[sid].areaId;
	var lng = curEventMarker.point.lng;
	var lat = curEventMarker.point.lat;
	
	// infowindow的标题
    var infoWindowTitle = '<div style="font-weight:bold;color:#CE5521;font-size:14px">添加案件</div>';
 	// infowindow的显示信息
    var infoWindowHtml = [];
 	
    infoWindowHtml.push('<form id="eventForm" class="form-horizontal" onsubmit="return false;" method="POST">');
    
    infoWindowHtml.push('<div id="nameDiv" class="control-group success">');
    infoWindowHtml.push(	'<label class="control-label" for="inputEmail">案件名称</label>');
    infoWindowHtml.push(	'<div class="controls">');
    infoWindowHtml.push(		'<input type="text" id="name" name="name" placeholder="案件名称">');
    infoWindowHtml.push(		'<span id="eventNameLabel" class="help-inline"></span>');
    infoWindowHtml.push(	'</div>');
    infoWindowHtml.push('</div>');
    
    infoWindowHtml.push('<div id="carNoDiv" class="control-group success">');
    infoWindowHtml.push(	'<label class="control-label" for="carNo">报案车牌号</label>');
    infoWindowHtml.push(	'<div class="controls">');
    infoWindowHtml.push(		'<input type="text" id="carNo" name="carNo" placeholder="报案车牌号">');
    infoWindowHtml.push(		'<span id="carNoLabel" class="help-inline"></span>');
    infoWindowHtml.push(	'</div>');
    infoWindowHtml.push('</div>');
    
    infoWindowHtml.push('<div id="phoneNoDiv" class="control-group success">');
    infoWindowHtml.push(	'<label class="control-label" for="phoneNo">车主电话</label>');
    infoWindowHtml.push(	'<div class="controls">');
    infoWindowHtml.push(		'<input type="text" id="phoneNo" name="phoneNo" placeholder="车主电话">');
    infoWindowHtml.push(		'<span id="phoneNoLabel" class="help-inline"></span>');
    infoWindowHtml.push(	'</div>');
    infoWindowHtml.push('</div>');
    
	
	infoWindowHtml.push('<div id="eventDescDiv" class="control-group">');
    infoWindowHtml.push(	'<label class="control-label" for="inputEmail">案件描述</label>');
    infoWindowHtml.push(	'<div class="controls">');
	infoWindowHtml.push(		'<textarea id="eventDesc" name="eventDesc" rows="3"></textarea>');
	infoWindowHtml.push(	'</div>');
	infoWindowHtml.push('</div>');
	
	infoWindowHtml.push('<input type="hidden" name="lng" value='+lng+'>');
	infoWindowHtml.push('<input type="hidden" name="lat" value='+lat+'>');
	infoWindowHtml.push('<input type="hidden" id="addressId" name="addr" value="'+addr+'">');
	infoWindowHtml.push('<input type="hidden" id="type" name="type" value="'+type+'">');
	
	infoWindowHtml.push('<div class="control-group">');
	infoWindowHtml.push(	'<div class="controls">');
	infoWindowHtml.push(		'<button onclick="checkEventInfo()"_curMkr="btn btn-warning">保存案件</button>');
	infoWindowHtml.push(	'</div>');
	infoWindowHtml.push('</div>');
	
	infoWindowHtml.push('</form>');
	
	var infoWindow = new BMap.InfoWindow(infoWindowHtml.join(""), {
		title : infoWindowTitle,
		width : 600,
		height : 300
	});
	curEventMarker.openInfoWindow(infoWindow);
	
//			alert('surveyId='+surveyId+',areaId='+areaId);
//	var params = 'type='+type+'&addr='+addr+'&lat='+lat+'&lng='+lng+'&name='+name;
//	params = encodeURI(params);
//	$.post(ctx+"/locate/tagPoint",params ,function(data){
//		
//		if(data.status == 200) {
//			if(type == 0) {
//				map.removeOverlay(curEventMarker);
//				map.removeOverlay(circle);
//			} else {
//				curEventMarker.setIcon(getIcon('blue'));
//				var infoWindow = new BMap.InfoWindow(data.message);
//				curEventMarker.openInfoWindow(infoWindow);
//				eventArrays[curEventMarker.sid] = data.result;
//			}
//		} else {
//			var infoWindow = new BMap.InfoWindow(data.message);
//			curEventMarker.openInfoWindow(infoWindow);
//// 				markInfoArray[markInfoId] = data.result;
//		}
//	});
}

/**
* 调度救援
* 	@RequestParam(value="eventInfoId") Long eventInfoId,
	@RequestParam(value="carId") Integer carId,
	@RequestParam(value="surveyTel") String phoneNo,
	@RequestParam(value="surveyId") Integer surveyUid
*/
function support(eventInfoId) {
	if(confirm("是否确认救援")) {
		var eventInfoId = curEventInfo.id;
		var carId = curCarInfo.carId;//被调度车牌
		var surveyUsers = carArrays[curCarInfo.carNo].surveyUsers;//救援人员信息
		var surveyTel = null;
		var surveyId = null;
		if(surveyUsers) {
			for(var i=0;i<surveyUsers.length;i++) {
				var user = surveyUsers[i];
				if(i == 0) {
					surveyTel = user.tel;
					surveyId = user.id;
				}
			}
		}
		
//		if(!surveyId) {
//			var infoWindow = new BMap.InfoWindow("此查勘车目前是离线状态。");
//			curCarMarker.openInfoWindow(infoWindow);
//			return false;
//		}
		var longitude=curCarMarker.point.lng;
		var latitude=curCarMarker.point.lat;
		var params = 'eventInfoId='+eventInfoId+'&carId='+carId+"&surveyTel="+surveyTel+"&surveyId="+surveyId+"&carlat="+latitude+"&carlng="+longitude;
		params = encodeURI(params);
		$.post(ctx+"/locate/support",params ,function(data){
			if(data.status == 200) {
				var infoWindow = new BMap.InfoWindow(data.message);
				curCarMarker.openInfoWindow(infoWindow);
				alert(data.message);
				eventArrays[eventInfoId] = data.result;
				carArrays[curCarInfo.carNo].eventInfos = data.extendsObject;
				window.location.reload();
			} else {
				var infoWindow = new BMap.InfoWindow(data.message);
				curCarMarker.openInfoWindow(infoWindow);
			}
		});
	} 
}

function getMarkerIcon(status) {
	var iconUrl = null;
	if(status == 0 ) {
		iconUrl = ctx+"/media/images/marker/event_red.png";
	
	} else {//已调度
		iconUrl = ctx+"/media/images/marker/event_blue.png";
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

function cancelMarkers(_map,eventinfos,flag){
	_map.clearOverlays();
	for(var i=0;i<eventinfos.length;i++){
		 var sid = eventInfos[i].id;
		 
		 if(flag==1){
			 if(eventInfos[i].status==0){
				 _map.addOverlay(eventMarkerArrays[sid]);
			 }
			 else{
				 _map.removeOverlay(eventMarkerArrays[sid]);
			 }
		 }
		 if(flag==2){

			 if(eventInfos[i].status==1){
				 _map.addOverlay(eventMarkerArrays[sid]);
			 }
			 else{
				 _map.removeOverlay(eventMarkerArrays[sid]);
			 }
		 }
	}
}
