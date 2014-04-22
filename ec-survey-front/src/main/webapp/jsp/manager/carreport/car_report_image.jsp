<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<div>
	<c:choose>
		<c:when test="${carReportImagesListSize > 0}">
			<c:forEach items="${carReportImagesList}" var="dto">
				<img alt="" src="${ctx}/survey-images/${dto.url }">
			</c:forEach>
		</c:when>
		<c:otherwise>  
			没有图片信息 
	   </c:otherwise>
	</c:choose>
</div>
