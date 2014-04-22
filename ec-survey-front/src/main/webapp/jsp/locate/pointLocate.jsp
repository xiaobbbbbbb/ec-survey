<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>	
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎使用保易行系统</title>  
<link href="${ctx}/webjars/bootstrap/2.3.2/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/webjars/bootstrap/2.3.2/css/bootstrap-responsive.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/js/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="${ctx}/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	
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
<script type="text/javascript" >
</script>
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../common/head.jsp"%>	
    
    <div class="container-fluid" style="min-width: 1100px;">
      <div class="row-fluid" >
        <div class="span3">
          <div class="well sidebar-nav">
			
          </div><!--/.well -->
        </div><!--/span-->
        
        
        <div class="span9">
          <div class="well">
            	<div id="map_canvas" style="min-height: 480px;min-width: 800px;"></div> 
				<div id="result" ></div>
				
				<script>
					// 百度地图API功能
					var map = new BMap.Map("map_canvas");
					map.centerAndZoom("深圳南山区",15)
					var local = new BMap.LocalSearch(map, {
					  renderOptions:{map: map}
					});
					var currentMaker = null;
					var circle = null;
					map.addEventListener("click",function(e){
						var mPoint = new BMap.Point(e.point.lng, e.point.lat);  
						if(!currentMaker) {
							currentMaker = new BMap.Marker(mPoint);  // 创建标注
							map.enableScrollWheelZoom();
							map.centerAndZoom(mPoint,15);
							map.addOverlay(currentMaker);
							currentMaker.enableDragging();
							
							currentMaker.addEventListener("dragend", function (e) {  
						    	map.removeOverlay(circle);
								map.removeOverlay(currentMaker);
								mPoint = new BMap.Point(e.point.lng, e.point.lat);  
								//currentMaker = new BMap.Marker(mPoint);  // 创建标注
								map.enableScrollWheelZoom();
// 								map.centerAndZoom(mPoint,15);
								map.addOverlay(currentMaker);
								currentMaker.enableDragging();
		 					  	
								circle = new BMap.Circle(mPoint,1000,{fillColor:"blue", strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3});
		 					    map.addOverlay(circle);
							});
							
							circle = new BMap.Circle(mPoint,1000,{fillColor:"blue", strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3});
	 					    map.addOverlay(circle);
	 					   	
// 	 					    var local =  new BMap.LocalSearch(map, {renderOptions: {map: map, autoViewport: false}});  
// 	 					    var bounds = getSquareBounds(circle.getCenter(),circle.getRadius());
// 	 					    local.searchInBounds("餐馆",bounds); 
						} 

					});
					
					
					
					
				    /**
				     * 得到圆的内接正方形bounds
				     * @param {Point} centerPoi 圆形范围的圆心
				     * @param {Number} r 圆形范围的半径
				     * @return 无返回值   
				     */ 
				    function getSquareBounds(centerPoi,r){
				        var a = Math.sqrt(2) * r; //正方形边长
				      
				        mPoi = getMecator(centerPoi);
				        var x0 = mPoi.x, y0 = mPoi.y;
				     
				        var x1 = x0 + a / 2 , y1 = y0 + a / 2;//东北点
				        var x2 = x0 - a / 2 , y2 = y0 - a / 2;//西南点
				        
				        var ne = getPoi(new BMap.Pixel(x1, y1)), sw = getPoi(new BMap.Pixel(x2, y2));
				        return new BMap.Bounds(sw, ne);        
				        
				    }
				    //根据球面坐标获得平面坐标。
				    function getMecator(poi){
				        return map.getMapType().getProjection().lngLatToPoint(poi);
				    }
				    //根据平面坐标获得球面坐标。
				    function getPoi(mecator){
				        return map.getMapType().getProjection().pointToLngLat(mecator);
				    }
				</script> 
          </div>
        </div><!--/span-->
      </div><!--/row-->
      
      <!-- 页脚-->
      <%@ include file="../common/footer.jsp"%>	
    </div><!--/.fluid-container-->
</body>
</html>
