<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./common/base.jsp"%>	
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎使用保易行系统</title>  
<link href="${ctx}/webjars/bootstrap/2.3.2/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/webjars/bootstrap/2.3.2/css/bootstrap-responsive.css" rel="stylesheet" />
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

<!-- 百度地图 -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2&ak=F9e138a00e815825002d53430326cac7"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/LuShu/1.2/src/LuShu_min.js"></script>

</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="./common/head.jsp"%>	
    
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span3">
          <div class="well sidebar-nav">
            <ul class="nav nav-list">
              <li class="nav-header">Left</li>
              <li class="active"><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
              <li class="nav-header">Sidebar</li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
            </ul>
          </div><!--/.well -->
        </div><!--/span-->
        <div class="span9">
          <div class="hero-unit">
            	<div id="map_canvas" style="width:900px;height:480px"></div> 
				<div id="result" style="float:right;position:absolute;left:700px;top:0;"></div>
				main1
          </div>
          <div class="row-fluid">
            <div class="span9">
            	main2
            </div><!--/span-->
          </div><!--/row-->
        </div><!--/span-->
      </div><!--/row-->
      
      <!-- 页脚-->
      <%@ include file="./common/footer.jsp"%>	
    </div><!--/.fluid-container-->
</body>
</html>
