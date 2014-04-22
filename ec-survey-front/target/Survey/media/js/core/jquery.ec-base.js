// 创建一个闭包  
(function($) {

	// 备份AJAX方法
	var _ajax = $.ajax;
	// 重写AJAX方法,防止请求时跳过权限拦截
	$.ajax = function(opt) {
		// 备份opt中error和success方法
		var fn = {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			},
			success : function(data, textStatus) {
			}
		};
		if (opt.error) {
			fn.error = opt.error;
		}
		if (opt.success) {
			fn.success = opt.success;
		}

		// 扩展增强处理
		var _opt = $.extend(opt, {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				fn.error(XMLHttpRequest, textStatus, errorThrown);
			},
			success : function(data, textStatus) {
				if (data) {
					if (!data.success && data.unauthorized) {
						alert("无权限访问(" + data.requestUrl + "),请重新登录！");
						window.top.location.href = data.url;
					} else {
						fn.success(data, textStatus);
					}
				}
			}
		});
		_ajax(_opt);
	};

	// 表单序列化
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};

	// 全选或者全不选
	$.fn.ecSelectChecked = function(options) {
		var $this = $(this);
		$this.click(function() {
			$("input[name='cid']").attr("checked", this.checked);
		});

		$("input[name='cid']").click(
				function() {
					var $subs = $("input[name='cid']");
					$this.attr("checked", $subs.length == $subs
							.filter(":checked").length ? true : false);
				});
	};

	// 获取选中的的数据
	$.fn.selectItems = function(options) {
		var $this = $(this), array = new Array(); // 用于保存 选中的那一条数据的ID
		if ($this.length > 0) {
			$this.each(function() {
				if ($(this).attr("checked")) // 判断是否选中
				{
					array.push($(this).val()); // 将选中的值 添加到 array中
				}
			});
		}
		return array;
	};

	// 打开加载提示
	var $loadBg = $(window.top.document).find("#load_back,#load");
	// 内部和外部都可以访问
	$
			.extend({
				// 打开预加载框
				loadOpen : function() {
					$loadBg.show();
				},
				// 关闭预加载框
				loadClose : function() {
					$loadBg.hide();
				},
				// 将数组按照逗号分割成字符串
				toArray : function(data) {
					var info = "";
					for ( var i = 0; i < data.length; i++) {
						// 如果i+1等于选项长度则取值后添加空字符串，否则为逗号
						info = (info + data[i])
								+ (((i + 1) == data.length) ? '' : ',');
					}
					return info;
				},
				// 弹出层提示
				alertModal : function(options) {
					var settings = $.extend({
						width : null,
						height : null,
						id : 'alertModal', // 弹出层的DIV的ID
						title : '系统提示', // 提示框名称
						content : '', // 提示语
						url : null, // 获取主题内容的url
						isConfirmBtn : false, // 是否有确定按钮
						confirmBtnId : 'confirmBtn', // 弹出层的确定按钮ID
						isHtml :false,  //是否是html代码
						confirmCallback : null
					// 确定的事件
					}, options || {}), modal_ = $("#" + settings.id), content_ = settings.content, confirmBtn_ = $("#"
							+ settings.confirmBtnId);
					if (settings.width != null) {
						modal_.width(settings.width);
					}
					if (settings.height != null) {
						modal_.height(settings.height);
					}
					$("#alert_title").text(settings.title);
					$("#alert_content").text(content_);
					if(settings.isHtml)
					{
						$("#alert_body").html(content_);
					}
					if (settings.url != null) {
						$.ajax({
							type : "GET",
							url : settings.url,
							success : function(data) {
								if (modal_.hasClass("small")) {
									modal_.removeClass("small");
								}
								modal_.addClass("selected");
								$("#alert_body").html(data);
							},
							error : function() {
								alert("请求错误!");
							}
						});
					}
					if (settings.isConfirmBtn) {
						confirmBtn_.unbind();
						confirmBtn_.show().bind("click", function() {
							if (settings.confirmCallback != null) {
								settings.confirmCallback();
							} else {
								alert("没有确定事件！");
							}
						});
					}
					// 显示对话框
					modal_.modal('show');
				}
			});

	// 鼠标进过对象时修改样式
	$.fn.ecHover = function(options) {
		var settings = $.extend({
			hoverClass : 'btn_hover' // 鼠标移动上去的样式
		}, $.fn.defaults, options);
		$(this).hover(function() {
			if (!$(this).hasClass("disabledBtn")) // 如果按钮禁用
			{
				$(this).addClass(settings.hoverClass);
			}
		}, function() {
			$(this).removeClass(settings.hoverClass);
		});
	};

	// 刷新当前页面
	function reloadFrame() {
		window.location.reload();
	}

	$.ec = {
		// 提交表单(多用于添加和修改的表单提交)
		ajaxSubmitForm : function(options) {
			var settings = $.extend({
				url : null, // 提交的地址
				callback : reloadFrame, // 提交成功后的回调
				data : $.toJSON($('#form').serializeObject())
			// 提交的数据
			}, options || {});
			$.ecAjax.submit({
				url : settings.url,
				type : 'POST',
				data : settings.data,
				callback : settings.callback
			});
		},
		// 删除数据
		deleteData : function(options) {
			var settings = $.extend({
				url : null, // 检查数据的地址,如果该地址为空,则表示删除数据前不做删除数据的验证
				param : null, // 提交的参数
				callback : reloadFrame
			}, options || {});
			$.alertModal({
				isConfirmBtn : true,
				content : '是否确定删除？',
				confirmCallback : function() {
					$.ecAjax.submit({
						url : settings.url,
						data : settings.param,
						callback : settings.callback
					});
				}
			});
		},
		// 表单验证
		validateType : function(options) {
			var settings = $.extend({
				formId : 'form' // 要验证的表单的ID
			}, options || {}), form_ = $("#" + settings.formId);
			var isSubmit = true;
			form_.find("[validateType]").each(function() {
				if (!validate($(this))) {
					isSubmit = false;
					return false;
				}
			});
			return isSubmit;
		}
	};

	function validate(obj) {
		var type = obj.attr("validateType");
		var objValue = $.trim(obj.val());
		var tip = obj.attr("tip");
		// 验证非空
		if (type == 'notEmpty') {
			if (objValue.length == 0) {
				$.alertModal({
					content : tip
				});
				return false;
			}
		}
		// 验证数字
		if (type == 'isNumber') {
			if (objValue.length > 0) {
				return !isNaN(objValue);
			}
		}
		return true;
	}

	// 所有的AJAX的方法
	$.ecAjax = {
		// 请求返回true还是false
		getReturn : function(options) {
			var settings = $.extend({
				async : false
			}, $.fn.ajax, options), flag = false;
			$.ajax({
				url : settings.url,
				type : settings.type,
				contentType : settings.contentType,
				data : settings.data,
				dataType : settings.dataType,
				async : settings.async,
				success : function(data) {
					if (data.obj > 0) {
						flag = true;
					}
				}
			});
			return flag;
		},
		// 请求返回JSON
		getReturnJson : function(options) {
			var settings = $.extend({
				async : false
			}, $.fn.ajax, options), json = null;
			$.ajax({
				url : settings.url,
				type : settings.type,
				contentType : settings.contentType,
				data : settings.data,
				dataType : settings.dataType,
				async : settings.async,
				success : function(data) {
					if (data) {
						json = data;
					}
				}
			});
			return json;
		},
		// 提交AJAX提示提交成功，还是失败
		submit : function(options) {
			var settings = $.extend({
				callback : null
			}, $.fn.ajax, options);
			$.ajax({
				url : settings.url,
				type : settings.type,
				contentType : settings.contentType,
				data : settings.data,
				dataType : settings.dataType,
				success : function(data, textStatus) {
					if (settings.callback) {
						settings.callback();
					}
				},
				error : function(data) {
					if (settings.callback) {
						settings.callback();
					}
				}
			});
		}
	};

	// 调用的方法 $("#startTime,#endTime").datepickerStyle(); 单只有一个对象时则默认不比较时间
	$.fn.datepickerStyle = function(options) {
		var settings = $.extend({
			startYear : 30,
			endYear : 30,
			dateFormat : 'yy-mm-dd'
		}, options || {}), $startYear = new Date().getFullYear()
				- settings.startYear, $endYear = new Date().getFullYear()
				+ settings.endYear, $this = $(this);
		$this.each(function(i) {
			$this.eq(i)
					.datepicker(
							{
								showButtonPanel : true,
								firstDay : 1,
								initStatus : '请选择日期',
								clearText : '清除',
								clearStatus : '清除已选日期',
								closeText : '关闭',
								closeStatus : '不改变当前选择',
								currentText : '今天',
								currentStatus : '显示本月',
								showMonthAfterYear : true, // 月在年之后显示
								changeMonth : true, // 允许选择月份
								changeYear : true, // 允许选择年份
								dateFormat : settings.dateFormat,
								yearRange : $startYear + ':' + $endYear,// 年份范围
								prevText : '上月',
								prevStatus : '显示上月',
								nextText : '下月',
								nextStatus : '显示下月',
								monthNamesShort : [ '01月', '02月', '03月', '04月',
										'05月', '06月', '07月', '08月', '09月',
										'10月', '11月', '12月' ],
								monthNames : [ '1月', '2月', '3月', '4月', '5月',
										'6月', '7月', '8月', '9月', '10月', '11月',
										'12月' ],
								dayNames : [ '星期日', '星期一', '星期二', '星期三', '星期四',
										'星期五', '星期六' ],
								dayNamesShort : [ '周日', '周一', '周二', '周三', '周四',
										'周五', '周六' ],
								dayNamesMin : [ '日', '一', '二', '三', '四', '五',
										'六' ],
								onClose : function(selectedDate) {
									if ($this.size() > 1) // 有两个比较的时间
									{
										$this.eq(1).datepicker("option",
												"minDate", selectedDate);
										$this.eq(0).datepicker("option",
												"maxDate", selectedDate);
									}
								}
							});
		});
	};

	// 插件公共的AJAX方法的参数
	$.fn.ajax = {
		url : '', // 请求的地址
		type : 'GET', // 请求方式 ("POST" 或 "GET"),默认为 "GET
		contentType : 'application/json;charset=utf-8', // 请求的数据格式
		data : null, // 请求的参数
		dataType : 'json' // 返回数据的形式
	};

	// 插件公共的defaults
	$.fn.defaults = {};

	// 闭包结束
})(jQuery);
