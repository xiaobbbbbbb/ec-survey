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

//获取车辆颜色
function getCarColor(status) {
	var color = 'green';
	if (status == 0) {
		color = 'green';
	} else if (status == 1) {
		color = 'red';
	} else {
		color = 'gray';
	}
	return color;
}

//获取车辆状态描述
function getCarStatusStr(status) {
	var cStatusText = '';
	if (status == 0) {
		cStatusText = '待命中';
	} else if (status == 1) {
		cStatusText = '工作中';
	} else {
		cStatusText = '离线';
	}
	return cStatusText;
}

var refreshCarInfoInterval; //调度器对象。
var intervalTime = 30000;
var carRefreshUrl = null;
var carRefreshMap = null;
var markerArray = new Array();//标记信息
var locateArray = new Array();

function runRefreshCarInfo(url,map) {
	carRefreshUrl = url;
	carRefreshMap = map;
	refreshCarInfoInterval = setInterval("refreshCarInfo()", intervalTime);
}

//修改车辆相关信息
function refreshCarInfo() {
	if(!carRefreshUrl) {
		return false;
	}
	
	$.getJSON(carRefreshUrl,function(datas) {
		initCarInfos(carRefreshMap,datas);
	});
	
}