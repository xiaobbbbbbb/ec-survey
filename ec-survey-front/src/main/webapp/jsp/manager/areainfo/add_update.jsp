<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
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
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/webjars/bootstrap/2.3.2/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery.json-2.3.min.js"></script>
<link href="${ctx}/media/css/common/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/common/artDialog_form.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>
<script type="text/javascript">
	$(function() {
	 	//按钮添加
		$("#rightButton")
				.click(
						function() {
							var $currOption = $("#leftOption").find(
									".checkedOption");
							if ($currOption.size() > 0) {
								var $id = $currOption.find(".cid")
										.val(), $name = $currOption
										.text(), $pcode = $currOption
										.find(".pcode").val();
								addEvent($id, $name, $pcode);
							}
						});

		//按钮移除
		$("#leftButton").click(function() {
			$("#checkedOption .checkedOption").remove();
		});

		//双击添加
		$("#leftOption ul li").dblclick(
				function() {
					var $id = $(this).find(".cid").val(), $name = $(
							this).text(), $pcode = $(this).find(
							".carNo").val();
					addEvent($id, $name, $pcode);
				});

		addOptionClass("leftOption");
		addOptionClass("checkedOption");
	});

	function submitForm() {
		if ($.ec.validateType()) {
			var oldName = $.trim("${dto.name}"), $newName = $.trim($("#name")
					.val()), $pid = $.trim($("#pid").val()),
					flag = false;
			var carIds="";
			var $potion = $("#checkedOption ul li");
			if(($potion.length > 0)){
				var ids=[];
				$potion.each(function(){
					ids.push($(this).find(".cid").val());
				});
				carIds = $.toArray(ids);
				$("#carIds").val(carIds);
			}else{
				$("#carIds").val("");
			}
			if ($newName != oldName) //如果部门名称没有修改过则直接修改
			{
				var checkUrl = "${ctx}/areaInfo/check", param = {
					"name" : $newName,
					"pid" : $pid,
				}, flag = $.ecAjax.getReturn({
					url : checkUrl,
					data : param
				});
			}
			
			if (!flag) {
				var url = "${ctx}/areaInfo/${dto.id == null ? 'add' : 'update'}";
				$.ec.ajaxSubmitForm({
					url : url,
					callback : frameHref
				});
			} else {
				$.alertModal({
					content : '区域已经存在！'
				});
			}
		}
	}

	function frameHref() {
		window.location.href = "${ctx}/areaInfo/list";
	}

	
	//选中变色
	function addOptionClass(idName) {
		$("#" + idName + " ul li").click(function() {
			$("#" + idName + " ul li").removeClass("checkedOption");
			$(this).addClass("checkedOption");
		});
	}

	//选中双击删除
	function removeChecked(obj) {
		$(obj).remove();
	}
	//添加数据时绑定事件
	function addEvent(id, name, pcode) {
		var li = "<li class='clz"
				+ $.trim(id)
				+ "' ondblclick='removeChecked(this);'><input class='cid' type='hidden' value='"
				+ $.trim(id) + "' /><input class='carNo' type='hidden' value='"
				+ $.trim(pcode) + "' />" + $.trim(name) + "</li>";

		if ($("#checkedOption ul li").hasClass("clz" + $.trim(id))) {
			
			$.alertModal({
				content : '已经存在"' + $.trim(name) + '"!'
			});
		} else {
			$("#checkedOption ul").append(li);
		}
		addOptionClass("checkedOption");
	}
	function removeRightChecked(obj,id,name) {
		addRightEvent(id,name);
		$(obj).remove();
	}
	
	function addRightEvent(id, name) {
		var li = "<li id='id_"+id+"'><input class='cid' type='hidden' value='"+id+"' /> <input class='carNo' type='hidden' value='"+name+"'/> "+name+"</li>";
		$("#leftOption ul").append(li);
		$("#id_"+id).bind("dblclick", function(){
			 addEvent(id, name, name);
		});
		addOptionClass("leftOption");
		addOptionClass("checkedOption");
	}

</script>
</head>
<body>
<!-- 页面头部 -->
	<%@ include file="../../common/head.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<div>
				<!-- 页面头部 -->
				<%@ include file="../../common/head.jsp"%>
				<ul class="breadcrumb" >
					<li><a href="#">基本信息</a> <span class="divider">/</span></li>
					<li><a href="${ctx}/areaInfo/list">区域分组管理</a> <span class="divider">/</span></li>
					<li class="active">${dto.id == null ? '添加' : '修改'}</li>
				</ul>
				<div class="well">
					<form id="form" class="form-horizontal">
						<input name="id" value="${dto.id }" type="hidden" /> <input id="pid" name="pid" value="${param.pid }"
								type="hidden" />
						<input id="cityId" name="cityId" type="hidden"	value="${cityId}" maxlength="100" /> 
						
						<input id="carIds" name="carIds" type="hidden"	value="${carIds}"  maxlength="100" /> 
 						<div class="control-group">
							<label class="control-label"><span class="red">*</span>所属区域:</label>
							<div class="controls">
							    <%@ include file="select_area.jsp"%>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>区域名称:</label>
							<div class="controls">
								<input id="name" name="name" validateType="notEmpty" tip="请输入区域名称!" type="text" value="${dto.name}"
									maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>绑定车辆:</label>
							<div class="controls">
								 <table  style="width: 50%">
									<tr>
										<td class="titleTip"><span class="blue">车辆列表：</span></td>
										<td></td>
										<td class="titleTip"><span class="blue">选中的车辆信息：</span></td>
									</tr>
									<tr>
										<td width="45%">
											<div class="selectOption" id="leftOption">
												<ul>
													<c:forEach items="${carList}" var="dto">
														<li id="id_${dto.id}"><input class="cid" type="hidden" value="${dto.id}" /> <input class="carNo"
															type="hidden" value="${dto.carNo}" /> ${dto.carNo}</li>
													</c:forEach>
												</ul>
											</div>
										</td>
										<td width="10%" align="center" valign="middle" class="arrowBtn"><img id="rightButton" border="0"
											src="${ctx}/media/images/input/arrowright.gif" width="20" height="20" /><br />
										<br /> <img id="leftButton" border="0" src="${ctx}/media/images/input/arrowleft.gif" width="20" height="20" /></td>
										<td width="45%">
											<div class="selectOption" id="checkedOption">
												<ul class="checkedUL">
													<c:forEach items="${bindList}" var="dto">
														<li id="id_${dto.id}" ondblclick="removeRightChecked(this,'${dto.id}','${dto.carNo}')"><input class="cid" type="hidden" value="${dto.id}" /> <input class="carNo"
															type="hidden" value="${dto.carNo}" /> ${dto.carNo}</li>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</div>
						<div class="form-actions">
							<input id="btnSubmit" class="btn btn-primary" type="button" onclick="submitForm();"
								value="保 存" />&nbsp; <input id="btnCancel" class="btn" type="button" value="返 回"
								onclick="history.go(-1)" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	 
</body>
</html>

