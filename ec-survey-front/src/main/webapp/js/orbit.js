/**
 * 行车轨迹相关 2013-08-29 xiaodx
 * /**
		*	private Integer carId;
			private String carNo;
			private List<CarReportDto> reports;
				private Long id;
				private Integer carId;
				private Integer surveyUid;//查勘员ID
				private Float mileMeter;//本次里程
				private Integer avgSpeed;//平均车速
				private Long totalTime;//累计行驶时长(秒)
				private Integer hypervelocity;//超速记录
				private Integer fenceCounts;//围栏告警记录
				private String startTime;//开始时间yyyy-MM-dd HH:mm
				private String endTime;//结束时间	yyyy-MM-dd HH:mm
				private List<PointDto> points;
			private Integer speed = 10;//播放速度
 */
var curOrbit = null;
var carSportArray = new Array();//key=carSportId,value=CarSport
var curCarSport = null;// 当前的行车数据
var curCarSportId = null;
var currentCarNo = null;
var lushu = null;
var allPoints = [];
var curMap = null;
var speed = 500;

function clickOrbitTr(carSportId) {
	//loadOrbit(orbitId,curMap,$('#startTime').val(),$('#endTime').val());
	initLushu(carSportId,curMap);
	$(".tr_class_temp").attr("style", "");
	var trInfo = $("#tr_temp_id_"+carSportId); 
	trInfo.attr("style", "BACKGROUND-COLOR: #e6f0fc");
}

function loadOrbit(carId,map,startTime,endTime) {
	//alert('carId='+carId+',startTime='+startTime+',endTime='+endTime);
	var orbitUrl = ctx+'/locate/getCarOrbit?';
	if(startTime) {
		orbitUrl += 'startTime='+startTime+'&endTime='+endTime;
	}
	orbitUrl += '&carId='+carId;
	$.getJSON(orbitUrl,function(orbit) {
//			orbitsArray.splice(0);//清空数组
		curOrbit = orbit;
		currentCarNo = curOrbit.carNo;
		map.clearOverlays();
		$('#carLocateBody').empty();
		if(!curOrbit || !curOrbit.reports) {
//			if(currentCarNo) {
//				alert('车辆['+currentCarNo+']暂时没有行车轨迹。');
//			} else {
//				alert('没有行车轨迹。');
//			}
			return false;
		}
		var tempId=null;
		if(curOrbit.reports.length) {
			for(var i=0;i<curOrbit.reports.length;i++) {
				var icarReport = curOrbit.reports[i];
				if(icarReport) {
					curCarSportId = icarReport.id;
					if(i == 0) {	
						tempId=icarReport.id;
					}
					var trBody = '<tr align="center" class="tr_class_temp" id="tr_temp_id_'+icarReport.id+'" onclick=clickOrbitTr("'+icarReport.id+'")>';
					trBody += '<td>' + (i + 1) + '</td>';
					trBody += '<td>' + icarReport.startTime +' 至 '+icarReport.endTime+ '</td>';
					trBody += '<td>'+icarReport.totalTime+'</td>';
					trBody += '<td>'+icarReport.mileMeter+'</td>';
					trBody += '<td >'+icarReport.startAddress+'</td>';
					trBody += '<td >'+icarReport.endAddress+'</td></tr>';
					$('#carLocateBody').append(trBody);
//					carSportArray[icarReport.id] = icarReport;
				}
			}
		} else {
			map.centerAndZoom(cityName,15);
		}
		if(tempId!=null){
			clickOrbitTr(tempId);
		}
//		initLushu(curCarSportId,map);
	});
}




function initLushu(carSportId,map) {
		map.clearOverlays();
		var orbitUrl = ctx+'/locate/getSingleOrbit?';
		orbitUrl += 'carReportId='+carSportId;
		$.getJSON(orbitUrl,function(orbit) {
			allPoints.splice(0);//清空数组
//			carSportArray[carSportId] = orbit;
//			curCarSport = orbit;
//			curCarSportId = carSportId;
			if(!orbit.length) {
				return false;
			}
//			var vvv = '[';
			// 添加自定义覆盖物  
			var mySquare1 = new SquareOverlay(orbit[0], 20, ctx+"/media/images/marker/start.png"); 
			var mySquare2 = new SquareOverlay(orbit[orbit.length-1], 20, ctx+"/media/images/marker/end.png"); 
			map.addOverlay(mySquare1);  
			map.addOverlay(mySquare2); 
			for(var i=0;i<orbit.length;i++) {
				var orbitPoint = new BMap.Point(orbit[i].lng,orbit[i].lat);
//				orbitPoint.address = orbitPoint.html;
				allPoints.push(orbitPoint);
//				if(i<orbit.length-1) {
//					vvv+='{lng:'+orbitPoint.lng+',lat:'+orbitPoint.lat+',html:\'<p onclick=alert(10000)>ccccc<p>\'},';
//				} else {
//					vvv+='{lng:'+orbitPoint.lng+',lat:'+orbitPoint.lat+',html:\'<p onclick=alert(10000)>ccccc<p>\'}';
//				}
			}
			
//			vvv += ']';
			var myIcon = getCarIcon('green');
			var polyline = new BMap.Polyline(allPoints, {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});
			map.addOverlay(polyline);//画出巡查折线
			map.setViewport(allPoints);
			lushu = new BMapLib.LuShu(map,allPoints,{
		        defaultContent:"车牌号："+currentCarNo+"<br/>当前播放速度："+speed,
		        speed:speed,
		        icon:myIcon,
		        landmarkPois: []});  
//			lushu.hideInfoWindow();
		});
}

//定义自定义覆盖物的构造函数  
function SquareOverlay(center, length, url){  
  this._center = center;  
  this._length = length;  
  this._url = url;  
}  
  
// 继承API的BMap.Overlay  
SquareOverlay.prototype = new BMap.Overlay();  

//实现初始化方法  
SquareOverlay.prototype.initialize = function(map){  
  
  // 保存map对象实例  
  this._map = map;  
    
  // 创建div元素，作为自定义覆盖物的容器  
  var div = document.createElement("div");  
  div.style.position = "absolute";  
    
  // 可以根据参数设置元素外观  
  div.style.width = this._length + "px";  
  div.style.height = this._length + "px";  
  div.style.background = this._color;  
  var img = document.createElement("div");
	img.style.height=28;
	img.style.width=28;
	img.style.cursor = "pointer";
	img.style.marginLeft=15;
	img.style.marginTop=5;
	
	var imgs = document.createElement("img");
	imgs.src=this._url;//图标图片
	imgs.style.height=24;
	imgs.style.width=24;
	imgs.style.marginTop=2;
	imgs.style.marginLeft=2;
	img.appendChild(imgs);
	div.appendChild(img);
  // 将div添加到覆盖物容器中  
  map.getPanes().markerPane.appendChild(div);  
  
  // 保存div实例  
  this._div = div;  
  
  // 需要将div元素作为方法的返回值，当调用该覆盖物的show、  
  // hide方法，或者对覆盖物进行移除时，API都将操作此元素。  
  return div;  
};

//实现绘制方法  
SquareOverlay.prototype.draw = function(){  
  
  // 根据地理坐标转换为像素坐标，并设置给容器  
  var position = this._map.pointToOverlayPixel(this._center);  
  this._div.style.left = position.x - this._length / 2 + "px";  
  this._div.style.top = position.y - this._length / 2 + "px";  
};  
