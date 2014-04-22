/**
 * 标注相关2013-08-28 xiaodx
 */
var curMkr = null;// 当前标注点
var curMap = null;
var curCtx = null;
// 添加标记
function markInfo(_curMkr, markTypes,_map) {
	if(!markTypes || markTypes.length == 0) {
		alert('请先在"基本信息－标注类型管理"添加标记类型。');
		return false;
	}
	map=_map;
	curMkr = _curMkr;
	// infowindow的标题
	var infoWindowTitle = '<div style="font-weight:bold;color:#CE5521;font-size:14px">添加标注</div>';
	// infowindow的显示信息
	var infoWindowHtml = [];

	infoWindowHtml
			.push('<form id="markForm" class="form-horizontal" action="mark" onsubmit="return false;" method="POST">');
	infoWindowHtml.push('<div id="markNameDiv" class="control-group success">');
	infoWindowHtml
			.push('<label class="control-label" for="inputEmail">标注名称</label>');
	infoWindowHtml.push('<div class="controls">');
	infoWindowHtml
			.push('<input type="text" id="markName" name="markName" placeholder="标注名称">');
	infoWindowHtml.push('<span id="markNameLabel" class="help-inline"></span>');
	infoWindowHtml.push('</div>');
	infoWindowHtml.push('</div>');

	infoWindowHtml.push('<div id="markTypeDiv" class="control-group">');
	infoWindowHtml
			.push('<label class="control-label" for="inputEmail">标注类型</label>');
	infoWindowHtml.push('<div class="controls">');
	infoWindowHtml.push('<select id="markType" name="markType" >');

	for ( var i = 0; i < markTypes.length; i++) {
		infoWindowHtml.push('<option value=' + markTypes[i].id + '>'
				+ markTypes[i].name + '</option>');
	}
	infoWindowHtml.push('</select>');
	infoWindowHtml.push('</div>');
	infoWindowHtml.push('</div>');

	infoWindowHtml.push('<div id="markDescDiv" class="control-group">');
	infoWindowHtml
			.push('<label class="control-label" for="inputEmail">标注描述</label>');
	infoWindowHtml.push('<div class="controls">');
	infoWindowHtml
			.push('<textarea id="markDesc" name="markDesc" rows="3"></textarea>');
	infoWindowHtml.push('</div>');
	infoWindowHtml.push('</div>');

	// 隐藏字段
	var gc = new BMap.Geocoder();
	gc.getLocation(_curMkr.point, function(rs) {
		var addComp = rs.addressComponents;
		var addr = addComp.province + addComp.city + addComp.district
				+ addComp.street + addComp.streetNumber;
		$('#addressId').val(addr);
	});

	infoWindowHtml.push('<input type="hidden" name="baiduLongitude" value='
			+ _curMkr.point.lng + '>');
	infoWindowHtml.push('<input type="hidden" name="baiduLatitude" value='
			+ _curMkr.point.lat + '>');
	infoWindowHtml
			.push('<input type="hidden" id="addressId" name="address" value="">');

	infoWindowHtml.push('<div class="control-group">');
	infoWindowHtml.push('<div class="controls">');
	infoWindowHtml
			.push('<button onclick="checkMarkInfo()"_curMkr="btn btn-warning">保存标注</button>');
	infoWindowHtml.push('</div>');
	infoWindowHtml.push('</div>');

	infoWindowHtml.push('</form>');

	var infoWindow = new BMap.InfoWindow(infoWindowHtml.join(""), {
		title : infoWindowTitle,
		width : 600,
		height : 300
	});
	infoWindow.addEventListener("clickclose",function(){
		map.removeOverlay(curMkr);  
		curMkr.dispose();  		
	});
	_curMkr.openInfoWindow(infoWindow);
}

// 数据校验
function checkMarkInfo() {

	if ($('#markName').val() == '') {
		$('#markNameDiv').removeClass('success').addClass('error');
		$('#markNameLabel').text('标注名称不能为空!');
		return false;
	} else {
		$('#markNameDiv').removeClass('error').addClass('success');
		$('#markNameLabel').text('');
	}

	if ($('#markType').val() == '') {
		$('#markTypeDiv').removeClass('success').addClass('error');
		$('#markTypeLabel').text('请选择标注类型!');
		return false;
	} else {
		$('#markTypeDiv').removeClass('error').addClass('success');
		$('#markTypeLabel').text('');
	}

	if ($('#markDesc').val() == '') {
		$('#markDescDiv').removeClass('success').addClass('error');
		$('#markDescLabel').text('标注描述不能为空!');
		return false;
	} else {
		$('#markDescDiv').removeClass('error').addClass('success');
		$('#markDescLabel').text('');
	}

	if (!$('#addressId').val()) {
		alert('位置修改失败，找不到地点名称,请重新标记。');
		curMap.removeOverlay(curMkr);
		return false;
	}

	$('#markForm').submit();

	var params = $("#markForm").serialize();
	params = encodeURI(params);
	$.post(curCtx + "/locate/mark", params, function(data) {
		// if(data.status == 200) {
		// //
		// window.location=ctx+'/locate/eventLocate?markInfoId='+data.result.id;
		// infoWindow = new BMap.InfoWindow(data.message);
		// } else {
		// infoWindow = new BMap.InfoWindow("操作失败。");
		// }
		var infoWindow = null;
		if (data.status != 200) {
			alert('位置修改失败，找不到地点名称。');
			// infoWindow = new BMap.InfoWindow('位置修改失败，找不到地点名称。');
			curMap.removeOverlay(curMkr);
			return false;
		} else {
			infoWindow = new BMap.InfoWindow(data.message);
		}
		curMkr.openInfoWindow(infoWindow);
		// 添加移动事件
		curMkr.addEventListener("dragend", function(e) {
			var params = 'markInfoId=' + data.result.id + '&lng=' + e.point.lng
					+ '&lat=' + e.point.lat;
			// 地址解析器 根据经纬度获取当前车辆所在地址
			var gc = new BMap.Geocoder();
			gc.getLocation(new BMap.Point(e.point.lng, e.point.lat), function(
					rs) {
				var addComp = rs.addressComponents;
				var addr = addComp.province + addComp.city + addComp.district
						+ addComp.street + addComp.streetNumber;
				params += '&address=' + addr;
				params = encodeURI(params);
				$
						.post(curCtx + "/locate/updateMarkerPosition", params,
								function(data2) {
									var infoWindow = new BMap.InfoWindow(
											data2.message);
									curMkr.openInfoWindow(infoWindow);
									// if(data2.status == 200) {
									// curMap.removeOverlay(circle);
									// circle = new
									// BMap.Circle(e.point,1000,{fillColor:"blue",
									// strokeWeight: 1 ,fillOpacity: 0.3,
									// strokeOpacity: 0.3});
									// curMap.addOverlay(circle);
									// }
								});
			});
			curMap.centerAndZoom(new BMap.Point(e.point.lng, e.point.lat), 16);
		});

		// curMkr = null;
	});

	return true;
}

// 注册标注事件
function regMarker(map, markTypes, ctx) {
	curCtx = ctx;
	curMap = map;

	// draw DIV
	// 创建控件
	var myZoomCtrl = new ZoomControl(ctx);
	map.addControl(myZoomCtrl);

	var contextMenu = new BMap.ContextMenu();
	var txtMenuItem = [ {
		text : '添加标注',
		callback : function(p) {
			var mkrTool = new BMapLib.MarkerTool(map, {
				followText : "点击左键添加"
			});
			mkrTool.open(); // 打开工具
			var icon = BMapLib.MarkerTool.SYS_ICONS[0]; // 设置工具样式，使用系统提供的样式BMapLib.MarkerTool.SYS_ICONS[0]
														// --
														// BMapLib.MarkerTool.SYS_ICONS[23]
			// 创建图标对象
			// var icon = new BMap.Icon(ctx+"/media/images/marker/mark.gif", new
			// BMap.Size(23, 25), {
			// // 指定定位位置。
			// // 当标注显示在地图上时，其所指向的地理位置距离图标左上
			// // 角各偏移10像素和25像素。您可以看到在本例中该位置即是
			// // 图标中央下端的尖角位置。
			// offset: new BMap.Size(10, 25),
			// // 设置图片偏移。
			// // 当您需要从一幅较大的图片中截取某部分作为标注图标时，您
			// // 需要指定大图的偏移位置，此做法与css sprites技术类似。
			// imageOffset: new BMap.Size(0, 0 - 1 * 25) // 设置图片偏移
			// });

			mkrTool.setIcon(icon);
			mkrTool.addEventListener("markend", function(evt) {
				var mkr = evt.marker;
				mkr.enableDragging();
				markInfo(mkr, markTypes,map);
			});
		}
	} ];

	for ( var i = 0; i < txtMenuItem.length; i++) {
		contextMenu.addItem(new BMap.MenuItem(txtMenuItem[i].text,
				txtMenuItem[i].callback, 100));
		if (i == 1 || i == 3) {
			contextMenu.addSeparator();
		}
	}
	map.addContextMenu(contextMenu);
}

// 定义一个控件类,即function 默认停靠位置和偏移量
function ZoomControl(ctx) {
	this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
	this.defaultOffset = new BMap.Size(80, 10);
	this._ctx=ctx;
}

// 添加的自定图层
ZoomControl.prototype = new BMap.Control();
ZoomControl.prototype.initialize = function(map) {

	var divSite = document.createElement("div");
	// 创建一个DOM元素
	var div = document.createElement("div");
	// 添加文字说明
	div.innerHTML = "标记";
	div.textSize = "10px";
	div.style.background = this._ctx+"/media/images/marker/nav_bj_24a15l.gif"; // 标题背景图片
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
	imgs.src = this._ctx+"/media/images/marker/mark.png";// 图标图片
	imgs.style.height = 5;
	imgs.style.width = 5;
	imgs.style.marginTop = 2;
	imgs.style.marginLeft = 2;
	img.appendChild(imgs);
	divSite.appendChild(img);
	// 绑定事件,锁定
	var noclick = true;
	var mkrTool = new BMapLib.MarkerTool(map, {
		followText : "点击左键添加"
	});
	mkrTool.addEventListener("markend", function(evt) {
		var mkr = evt.marker;
		mkr.enableDragging();
		markInfo(mkr, markTypes,map);
	});
	img.onclick = function(e) {
		noclick = !noclick;

		if(!noclick){
			mkrTool.open(); // 打开工具
			var icon = BMapLib.MarkerTool.SYS_ICONS[0]; // 设置工具样式，使用系统提供的样式BMapLib.MarkerTool.SYS_ICONS[0]
														// --
														// BMapLib.MarkerTool.SYS_ICONS[23]
			mkrTool.setIcon(icon);
		} else {
			mkrTool.close();
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

var _interval;

function regLoding(map) {
	$(window.top.document).find("#load_back,#load").show();
	map.addEventListener("tilesloaded",function(){
		$(window.top.document).find("#load_back,#load").hide();
	});
	_interval = setInterval("closeLoding()", 2000);
}
function closeLoding() {
	$(window.top.document).find("#load_back,#load").hide();
	clearInterval(_interval);
	_interval = null;
}