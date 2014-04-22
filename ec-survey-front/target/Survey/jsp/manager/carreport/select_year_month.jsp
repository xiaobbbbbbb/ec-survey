<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
	$(function(){
		$("#select_year").change(function() { //年份
			var checkValue = $("#select_year").val(); 
			$("#year").val(checkValue);
		});
		$("#select_month").change(function() { //月份
			var checkValue = $("#select_month").val();  
			$("#month").val(checkValue);
		});
		init();
	});
	
	function init(){
		var d = new Date();
		var year = d.getFullYear()-5;  //当前年份向前追加5年
		var option_info="";
		for ( var int = 0; int < 15; int++) {
			year = year+1;
		 	if(year == '${year}'){
		 		option_info+='<option value="'+year+'" selected="selected"  >'+year+'</option>';
		 	}else{
		 		option_info+='<option value="'+year+'">'+year+'</option>';
		 	}
		}
		$("#select_year").append(option_info);
	}

	
</script>

<input id="year" name="year" value="${param.year }" type="hidden"/>
<input id="month" name="month" value="${param.month }" type="hidden"/>
<select id="select_year" style="width: 100px">
	<option value="">请选择年份</option>
</select>
年
<select id="select_month" style="width: 100px">
	<option value="">请选择月份</option>
	<option value="01" <c:if test="${month == 01}"> selected='selected'</c:if> >01</option>
	<option value="02" <c:if test="${month == 02}"> selected='selected'</c:if> >02</option>
	<option value="03" <c:if test="${month == 03}"> selected='selected'</c:if> >03</option>
	<option value="04" <c:if test="${month == 04}"> selected='selected'</c:if> >04</option>
	<option value="05" <c:if test="${month == 05}"> selected='selected'</c:if> >05</option>
	<option value="06" <c:if test="${month == 06}"> selected='selected'</c:if> >06</option>
	<option value="07" <c:if test="${month == 07}"> selected='selected'</c:if> >07</option>
	<option value="08" <c:if test="${month == 08}"> selected='selected'</c:if> >08</option>
	<option value="09" <c:if test="${month == 09}"> selected='selected'</c:if> >09</option>
	<option value="10" <c:if test="${month == 10}"> selected='selected'</c:if> >10</option>
	<option value="11" <c:if test="${month == 11}"> selected='selected'</c:if> >11</option>
	<option value="12" <c:if test="${month == 12}"> selected='selected'</c:if> >12</option>
</select>
月