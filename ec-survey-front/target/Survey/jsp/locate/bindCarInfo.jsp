<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<link href="${ctx}/media/css/common/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/common/artDialog_form.css" type="text/css" rel="stylesheet" />


<script type="text/javascript">

	$(document).ready(
			function() {
				initData(); //初始化数据

				$("#alarm_type_select").change(function() { //报警类型
					var checkValue = $("#alarm_type_select").val(); //获取Select选择的Value
					$("#alarmType").val(checkValue);
				});
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

	//初始化数据
	function initData() {
		var array = ${listIds};
		for ( var i in array) {
			cid = array[i];
			var r_cid = $("#id_" + cid + " .cid").val();
			var r_name = $("#id_" + cid + " .carNo").val();
			addEvent(r_cid, r_name, r_name);
		}
	}
</script>
<table>
	<tr>
		<td valign="middle" align="center">
			<form id="form">
				<input id="fenceInfoid" name="id" value="${fenceInfo.id }" type="hidden" />
				<table style="width: 600px;">
					<tr>
						<td align="center"><span class="red">*</span>围栏名称:</td>
						<td colspan="2"><input id="name" name="name" type="text" value="${fenceInfo.name}" maxlength="50" /></td>
					</tr>
					<tr>
						<td align="center"><span class="red">*</span>报警类型:</td>
						<td colspan="2"><input id="alarmType" name="alarmType" type="hidden" value="${fenceInfo.alarmType}" /> <select
							id="alarm_type_select">
								<option value="">请选择</option>
								<option value="1" <c:if test="${fenceInfo.alarmType == 1}"> selected='selected'
								</c:if> >进</option>
								<option value="0" <c:if test="${fenceInfo.alarmType == 0}"> selected='selected'
								</c:if>>出</option>
						</select></td>
					</tr>
					<tr>
						<td align="center"><span class="red">*</span>报警时间:</td>
						<td colspan="2">开始 ：&nbsp;
							<%@ include file="fence_select_time.jsp"%>
						</td>
					</tr>
					<tr>
						<td align="center">备注说明:</td>
						<td colspan="2"><textarea rows="5" style="width: 350px; overflow: auto; resize: none;" id='description'
								name='description'>${fenceInfo.description }</textarea></td>
					</tr>

					<tr>
						<td colspan="3" width="100%" align="center">
							<div id="select">
								<table>
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
												</ul>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</td>
	</tr>
</table>
