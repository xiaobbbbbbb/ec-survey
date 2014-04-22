<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
	function showCity() {
		var cityObj = $("#cityName");
		var cityOffset = $("#cityName").offset();
		var tree = $("#cityContent").html();
		if (tree.length > 0) {
			$("#cityContent").css({
				left : cityOffset.left + "px",
				top : cityOffset.top + cityObj.outerHeight() + "px"
			}).slideDown("fast");
			$("body").bind("mousedown", onBodyCityDown);
		}
	}

	function hideCity() {
		$("#cityContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyCityDown);
	}

	function onBodyCityDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "cityContent" || $(
				event.target).parents("#cityContent").length > 0)) {
			hideCity();
		}
	}

	$(function() {
		$('#myTab a:first').tab('show');//初始化显示哪个tab

		$('#myTab a').click(function(e) {
			e.preventDefault();//阻止a链接的跳转行为
			$(this).tab('show');//显示当前选中的链接及关联的content
		});
	});

	function onCity(id, Name) {
		$("#cityName").val(Name);
		$("#cityId").val(id);
		hideCity();
	}
</script>
<div id="cityContent"
	style="display: none; position: absolute; border: 1px solid #ccc; background-color: white; width: 730px;">
	<div id="city">
		<ul class="nav nav-tabs" id="myTab" style="margin-top: 2px; margin-bottom: 0px;">
			<li class="active"><a href="#home">A B C D E</a></li>
			<li><a href="#profile">F G H I J</a></li>
			<li><a href="#messages">K L M N O</a></li>
			<li><a href="#settings">P Q R S T</a></li>
			<li><a href="#settgs">U V W X Y Z</a></li>
		</ul>

		<div class="tab-content" id="tabCon">
			<div class="tab-pane active" id="home">
				<c:forEach items="${ABCDE}" var="dto" varStatus="sn">
					<div>
						<a href="javascript:onCity('${dto.id}','${dto.name}');"><span class="red">${dto.pinyin}</span>-${dto.name}</a>
					</div>
				</c:forEach>
			</div>
			<div class="tab-pane" id="profile">
				<c:forEach items="${FGHIJ}" var="dto" varStatus="sn">
					<div>
						<a href="javascript:onCity('${dto.id}','${dto.name}');"><span class="red">${dto.pinyin}</span>-${dto.name}</a>
					</div>
				</c:forEach>
			</div>
			<div class="tab-pane" id="messages">

				<c:forEach items="${KLMNO}" var="dto" varStatus="sn">
					<div>
						<a href="javascript:onCity('${dto.id}','${dto.name}');"><span class="red">${dto.pinyin}</span>-${dto.name}</a>
					</div>
				</c:forEach>
			</div>
			<div class="tab-pane" id="settings">
				<c:forEach items="${PQRST}" var="dto" varStatus="sn">
					<div>
						<a href="javascript:onCity('${dto.id}','${dto.name}');"><span class="red">${dto.pinyin}</span>-${dto.name}</a>
					</div>
				</c:forEach>
			</div>
			<div class="tab-pane" id="settgs">
				<c:forEach items="${UVWXYZ}" var="dto" varStatus="sn">
					<div>
						<a href="javascript:onCity('${dto.id}','${dto.name}');"><span class="red">${dto.pinyin}</span>-${dto.name}</a>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>