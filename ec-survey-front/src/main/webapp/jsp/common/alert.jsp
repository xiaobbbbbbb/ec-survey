<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 弹出层的提示 -->
<div class="modal small hide fade" id="alertModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="canceldBtn">×</button>
		<h3 id="myModalLabel"><span id="alert_title"></span></h3>
	</div>
	<div class="modal-body" id="alert_body">
		<p class="error-text">
			<i class="icon-warning-sign modal-icon"></i><span id="alert_content"></span>
		</p>
	</div>
	<div class="modal-footer">
	    <button class="btn btn-primary" id="confirmBtn" style="display: none;">确定</button>
		<button class="btn" data-dismiss="modal" aria-hidden="true" id="cancelBtn">取消</button>
	</div>
</div>
