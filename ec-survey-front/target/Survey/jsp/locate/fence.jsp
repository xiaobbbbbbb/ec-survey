<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/webjars/bootstrap/2.3.2/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery.json-2.3.min.js"></script>

<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<script	 type="text/javascript" src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.min.js"></script>
<script	 type="text/javascript" src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-timepicker-addon.js"></script>

<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>


<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}

@media ( max-width : 980px) {
	/* Enable use of floated navbar text */
	.navbar-text.pull-right {
		float: none;
		padding-left: 5px;
		padding-right: 5px;
	}
}
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2&ak=F9e138a00e815825002d53430326cac7"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/LuShu/1.2/src/LuShu_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/DistanceTool/1.2/src/DistanceTool_min.js"></script>
<!-- MarkerTool js -->
<script type="text/javascript" src="http://api.map.baidu.com/library/MarkerTool/1.2/src/MarkerTool_min.js"></script>
<script type="text/javascript" src="${ctx}/js/fence.js"></script>

<script type="text/javascript">
	var map;
	var centerPoints;
	var myDis;
	//折线包含所有点的数组
	var points = [];
	$(function() {
		
		
		// 百度地图API功能
		map = new BMap.Map("map_canvas");
		var grade=parseInt('${UserGrade}'); //城市级别 必须转int 不然鼠标滚动放大缩小会出现灰色空白界面
		map.centerAndZoom('${cityName}',grade);
		map.enableScrollWheelZoom();
		map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件

		//利用测距LIB包来画多边形
		myDis = new BMapLib.DistanceTool(map);
		var arrayPoints;
		
		//画线结束时触发的事件
		myDis.addEventListener("drawend", function(e) {
			myDis.close();
			var temp = new Array();
			if( points.length > 0){
				for ( var i = 0; i < points.length; i++) {
					var p = points[i];
					temp.push(p.lng + "_" + p.lat);
				}
				arrayPoints = $.toArray(temp);
				createUI(points[points.length - 1].lng,
						points[points.length - 1].lat); //通过标注创建弹出框
			}
		});
		
		//拖动之后中心点经纬度信息
		map.addEventListener("dragend", function showInfo(){
			 var cp = map.getCenter();
			 centerPoints=cp.lng + "_" + cp.lat;
		});
		

		function createUI(lng, lat) {
			var mPoint = new BMap.Point(lng, lat);
			var currentMaker = null;
			if (!currentMaker) {
				currentMaker = new BMap.Marker(mPoint); // 创建标注
				map.addOverlay(currentMaker);
			}

			var content = "<div id='fence_ui_div'>";
			content += "<from id='fence_from'  >";
			content += "<h3 id='myModalLabel'>创建围栏</h3>";
			content += "<span style='font-size:14px;'>围栏名称：</span>";
			content += "<input type='text' placeholder='围栏名称' name='name' id='name'></br>";
			content += "<span style='font-size:14px;'>围栏备注：</span>";
			content += "<input id='points' name='points' value='"+arrayPoints+"' type='hidden' />";
			content += "<textarea rows='3' id='description' name='description'></textarea>";
			content += "<div style='text-align: center'>";
			content += "  <button id='fence_submit' onclick='check()' class='btn btn-primary' type='submit' >保存</button>";
			content += "  <button id='fence_cancle' onclick='cancle_dialog()' class='btn' data-dismiss='modal' aria-hidden='true'>取消</button>";
			content += "</div>";
			content += "</from>";
			content += "</div>";
			currentMaker.openInfoWindow(new BMap.InfoWindow(content));
		}

		//每次点击底图添加节点时触发的事件
		myDis.addEventListener("addpoint", function(e) {
			points.push(new BMap.Point(e.point.lng, e.point.lat));
		});

		//点击电子围栏项对按钮层进行的显示隐藏
		$(".fence_info_div").bind("click", function(e) { //传递事件对象e
			map.clearOverlays();
			var id = $(this).children("#fence_id").val(); //hidden 输入框
			$(this).children("#fence_button_div_" + id).show(); //选中的显示按钮层

			var $potion = $(".fence_button_div");
			$potion.each(function(i) {
				var div_id = $(this).attr("id");
				var this_id = "fence_button_div_" + id;
				if (this_id == div_id) {
					$(this).show();
				} else {
					$(this).hide();
				}
			});

			var array = $(this).children("#fence_points_").html(); //获得坐标集合   由于放在hidden里面数据值不对 ，采用span
			var dataObj = eval("(" + array + ")");
			
			var tempPoints = $(this).children("#fence_center_points_").html();
			var centerP=null;
			for ( var i = 0; i < dataObj.length; i++) {
				points.push(new BMap.Point(dataObj[i].lng, dataObj[i].lat));
				if(tempPoints.length == 0){  //如果数据库中的中心经纬度为空的时候，选择围栏中的一个点设置为中心点
					if(i==0){
						centerP=new BMap.Point(dataObj[i].lng,dataObj[i].lat);
					}
				}
			}
			var polygon = new BMap.Polygon(points);
			polygon.enableMassClear() ;
			map.addOverlay(polygon);
			
			if(tempPoints.length!=0){//不为0从库中取
				var tempCenterObj = eval("(" + tempPoints + ")");
				centerP=new BMap.Point(tempCenterObj.lng, tempCenterObj.lat);  //中心点经纬度
			}
		 	//设置中心点位置 以围栏创建的时候开始点为中心点
		 	var centerGrade_ = $(this).children("#grade_").val();  //中心点级别
			map.centerAndZoom(centerP, parseInt(centerGrade_)); 
			points = [];
			findBindCarList(id); //查找绑定的车辆信息
		});
	});

	//查找绑定的车辆信息
	function findBindCarList(id) {
		var url = "${ctx}/locate/findBindCarInfo?id=" + id;
		$.ajax({
				url : url,
				type : 'post',
				success : function(data) {
					$("#bindCarInfoDatas .trFencarList").remove();
					for ( var i = 0; i < data.length; i++) {
						var obj = data[i];
						var tr_info = '<tr class="trFencarList" align="center"  id="tr_temp_id_'+obj.fid+'" onclick=clickTr("'+obj.fid+'")>';
						tr_info += '<td>' + (i + 1) + '</td> ';
						tr_info += '<td>' + obj.carNo + '</td>';
						tr_info += '<td>'
								+ new Date(obj.fceateTime)
										.format("yyyy-MM-dd hh:mm:ss")
								+ '</td> '; 
						tr_info += '<td class="td_center"><button id="bind_carinfo" class="btn btn-primary" style="margin-left: 10px"';
						tr_info += '	onclick="solutionBind(' + obj.fid
								+ ')" type="button">解除绑定车辆</button>';
						tr_info += '</td> ';
						tr_info += '</tr>';
						$("#bindCarInfoDatas").append(tr_info);
					}
				}
			});
	}

	function check() {
		$("#create_fence").attr("disabled","");
		$("#fence_submit").attr("disabled",true);  //保存按钮不可用
		var nameVal = $("#name").val();
		if (nameVal == "" || $.trim(nameVal) <= 0) {
			alert("名称不能为空");
			$("#name").focus();
			return;
		}
		var grade=map.getZoom(); //当前地图的级别
		
		var url = "${ctx}/locate/saveFence", param = {
			"name" : nameVal,
			"description" : $("#description").val(),
			"points" : $("#points").val(),
			"grade":grade,
			"centerPoints":centerPoints
		};
		$.ajax({
			url : url,
			type : 'post',
			data : param,
			success : function(data) {
				var status = data.status;
				if (status == 200) {
					alert(data.message);
				} else {
					alert(data.message);
				}
				cancle_dialog();
				frameHref();
			}
		});
	}
	function cancle_dialog() {
		$("#create_fence").removeClass("disabled");
		$("#create_fence").attr('disabled',false); 
		map.clearOverlays();
		points=[];
	}

	function deleteFence(fence_id) {
		if (confirm("请确认删除！")) {
			var url = "${ctx}/locate/deleteFence", param = {
				"id" : fence_id
			};
			$.ajax({
				url : url,
				type : 'post',
				data : param,
				success : function(data) {
					var status = data.status;
					if (status == 200) {
						alert(data.message);
					} else {
						alert(data.message);
					}
					cancle_dialog();
					frameHref();
				}
			});
		}
	}

	function frameHref() {
		window.location.href = "${ctx}/locate/fence";
	}
	
	function submitBind(){
		var startHour=$("#select_start_hour").val();
		var startMinute=$("#select_start_minute").val();
		var endHour=$("#select_end_hour").val();
		var endMinute=$("#select_end_minute").val();
		var tempStart=startHour+startMinute;
		var tempEnd=endHour+endMinute;
		var nameVal = $("#name").val();
		var alarmType = $("#alarm_type_select").val();
		var description = $("#description").val();
		var fence_id=$("#fenceInfoid").val();
		var flag=true;
		if (nameVal.length == 0) {
			alert("围栏名称不能为空");
			flag=false;
			return;
		}
		if (alarmType.length == 0) {
			alert("请选择报警类型");
			flag=false;
			return;
		}
		if(tempStart >= tempEnd){
			alert("报警结束时间需大于开始时间");
			flag=false;
			return;
		}
		if(flag){
			var alarmStartTime=startHour+":"+startMinute;
			var alarmEndTime = endHour+":"+endMinute;
			var $potion = $("#checkedOption ul li");
			var ids=[];
			var carIds="";
			
			$potion.each(function(){
				ids.push($(this).find(".cid").val());
			});
			carIds = $.toArray(ids);
			var url = "${ctx}/locate/bindCarInfo", param = {
				"fenceId" : fence_id,
				"carIds" :carIds,
				"name" :nameVal,
				"alarmType" :alarmType,
				"description" :description,
				"alarmStartTime" :alarmStartTime,
				"alarmEndTime" :alarmEndTime
			};
			$.ajax({
				url : url,
				type : 'POST',
				data : param,
				success : function(data) {
					var status = data.status;
					if (status == 200) {
						alert(data.message);
					} else {
						alert(data.message);
					}
					cancle_dialog();
					frameHref();
				}
			}); 
		}
	}
	
	//绑定车辆
	function bindCarInfoUI(fence_id) {
		var url_='${ctx}/locate/bindCarInfoUI?id='+fence_id;
		$.alertModal({
			title: '电子围栏绑定',
			url:url_,
			width : '650px',
			isConfirmBtn :true,
			confirmCallback: submitBind
		}); 
		
	}
	/**解除绑定*/
	function solutionBind(fenceCarId) {
		var url = "${ctx}/locate/solutionBind", param = {
			"id" : fenceCarId
		};
		if (confirm("确定要将该车辆解除绑定！")) {
			$.ajax({
				url : url,
				type : 'post',
				data : param,
				success : function(data) {
					var status = data.status;
					if (status == 200) {
						alert(data.message);
					} else {
						alert(data.message);
					}
					cancle_dialog();
					frameHref();
				}
			});
		}
	}
	
	
	//创建电子围栏
	function fenceCreate(){
		$(".fence_button_div").hide();
		$("#create_fence").addClass("disabled");
		$("#create_fence").attr('disabled',true); 
		alert("可以开始创建围栏了");
		points = [];
		map.clearOverlays();
		myDis.open();
	}
	//进入左边的时候讲画图工具关掉
	function leftOnMouserOver(){
		$("#create_fence").removeClass("disabled");
		$("#create_fence").attr('disabled',false);
		myDis.close();
	}
	
	
	function clickTr(id){
		$(".trFencarList").attr("style", "");
		var trInfo = $("#tr_temp_id_"+id); 
		trInfo.attr("style", "BACKGROUND-COLOR: #e6f0fc");
	}
</script>
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../common/head.jsp"%>
	<ul class="breadcrumb" style="margin-left: 20px; margin-right: 20px;">
		<li><a href="#">车辆位置信息</a> <span class="divider">/</span></li>
		<li class="active">电子围栏</li>
	</ul>
	<div class="container-fluid" >
		<div class="row-fluid">
			<div class="span3"  onmouseover="leftOnMouserOver()">
				<p class="text-center">
					<button class="btn btn-large btn-primary" type="button" id="create_fence" onclick="fenceCreate()">创建围栏</button>

				</p>
				<br>
				<p class="lead">提示：点击创建后即可在地图上画多边形，双击结束画图。</p>
				<hr />
				<p class="text-center">围栏信息</p>
				<div id="fence_left_div" class='well sidebar-nav'>
					<c:forEach items="${fenceList}" var="fence" varStatus="st">
						<div id="fence_info_div_" ${fence.id} class="fence_info_div">
							<input id="fence_id" type="hidden" value="${fence.id}"> <input id="fence_name_" ${fence.id} type="hidden"
								value="${fence.name}"> <input id="fence_description_" ${fence.id} type="hidden"
								value="${fence.description}"><input id="grade_"${fence.id} type="hidden" value="${fence.grade}">
							<span id="fence_center_points_" ${fence.id} style="display: none">${fence.centerPoints}</span>
							<span id="fence_points_" ${fence.id} style="display: none">${fence.points}</span>
							<div id="fence_data_div">${st.count }， ${fence.name}</div>
							<div id="fence_button_div_${fence.id}" class="fence_button_div" style="margin-top: 10px; display: none">
								<button id="bind_carinfo" class="btn btn-primary" style="margin-left: 10px"
									onclick="bindCarInfoUI('${fence.id}')" type="button">绑定车辆</button>
								<button id="bind_carinfo" class="btn btn-primary" style="margin-left: 10px"
									onclick="bindCarInfoUI('${fence.id}')" type="button">修改</button>
								<button id="delete_fence" class="btn btn-primary" style="margin-left: 10px" onclick="deleteFence('${fence.id}')"
									type="button">删除</button>
							</div>
							<c:if test="${fn:length(fenceList)!=st.count}">
								<hr />
							</c:if>
						</div>
					</c:forEach>
				</div>
			</div>
			<!--/span-->


			<div class="span9">
				<div class="well" style="overflow:hidden">
					<div id="map_canvas" style="min-height: 480px; min-width: 800px;"></div>
					<div id="result"></div>
				</div>

				<div class="row-fluid">
					<div class="span12" style="max-height: 200px;overflow-y:scroll;">
						<table id="list" class="table table-bordered table-condensed">
							<thead>
								<tr>
									<th width="40px">序号</th>
									<th>车牌号</th>
									<th>创建时间</th>
									<th >是否解绑</th>
								</tr>
							</thead>
							<tbody id="bindCarInfoDatas">

							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<!--/row-->
	</div>
	<!--/.fluid-container-->
</body>
</html>
