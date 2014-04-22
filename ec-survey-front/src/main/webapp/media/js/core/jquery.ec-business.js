// 创建一个闭包  
(function($) {
	// 公共的参数
	var basePath=$(window.top.document).find("#basePath").val();  //系统上下文
	art.dialog.removeData('existOrgVal_'); //删除存在服务机构数据共享接口
	art.dialog.removeData('existRoleVal_'); //删除存在角色数据共享接口
	
    //内部和外部都可以访问
	$.extend({
		//移除所有的选中
		removeCheck:function(options) {        
			var settings = $.extend({
				eachObj : $("input:checkbox")   //查找每一个对象
			},options||{});
			var objs=settings.eachObj;
			objs.each(function () {
				$(this).attr("checked", false);//选中或者取消选中
			}); 
		}
    });
	
	$.ec = {
		//选择部门页面
		selectOrgUI : function(options) {
			var settings = $.extend({
				orgId : 'orgId',
				orgName : 'orgName'
			}, options || {}),
			$orgId=$("#"+settings.orgId).val(),
			$orgName=$("#"+settings.orgName).val(),
			url=basePath+"/ralasafe/org/selectOrg";
			if($orgId.length>0)
			{
				var org= new Object();
				org.id = $orgId;
				org.name = $orgName;
				art.dialog.data('existOrgVal_',org);  //共享存在的部门数据
			}
			art.dialog.open(url, {
			width: 500,
			height: 340,
	        title: '选择用户所属机构',
		    ok: function () {
		    	var $checkedOption=$(this.iframe.contentWindow.document),
				$potion = $checkedOption.find("#checkedOption ul li"),
				select_text = "",
		    	select_val = "";
		    	$potion.each(function(){
		    		select_val=$(this).find(".pid").val();
		    		select_text=$(this).text();
				});
		    	if($.trim(select_text).length==0)
		    	{
		    		art.dialog.alert('请选择所属部门!');
		    		return false;
		    	}
		    	else
		        {
		    		$("#"+settings.orgId).val(select_val);
			    	$("#"+settings.orgName).val(select_text);
			    	return true;
		        }
		    },
		    cancel: true
	        });
		}
	};

	//刷新当前页面
	function reloadFrame()
	{
		window.location.reload();
	}

	
	//上传文件
	$.fn.uploadFile = function(options) {
		var settings = $.extend({
			auto          : true,                  //是否自动提交
			fileTypeDesc  : '请选择文件',
            fileTypeExts  : '*',
            uploader      : null,                  //提交的地址,
            onDialogClose : null,
            onDialogOpen  : null,
            onUploadSuccess : null,                 //上传成功后的回调
            onCancel  : null,                       //当点击文件队列中文件的关闭按钮或点击取消上传时触发            
            onSelect  :null                         //选择上传文件后调用
		}, options || {}),
		$this=$(this);
		$this.uploadify(
		{
	    	 'buttonText'    : '选择文件',
	    	 'auto'          : settings.auto,
	    	 'height'        : 20,
	    	 'width'         : 80,
	    	 'removeTimeout' : 1,
	    	 'queueSizeLimit': 1,   //队列中允许的最大文件数目
	    	 'queueID'       : 'fileQueue',
	    	 'multi'         : false,
	    	 'fileTypeDesc'  : settings.fileTypeDesc,
             'fileTypeExts'  : settings.fileTypeExts, 
	    	 'swf'           : basePath+'/js/uploadify3.1/uploadify.swf',
		     'uploader'      : settings.uploader, 
		     'onDialogClose' : function(queueData) {
		    	   if (settings.onDialogClose) {
						settings.onDialogClose(queueData);
				   }
		     },
		     'onDialogOpen'  : function(queueData) {
		    	   if (settings.onDialogOpen) {
						settings.onDialogOpen(queueData);
				   }
		     },
             'onUploadSuccess' : function(file, data, response){
            	   if (settings.onUploadSuccess) {
                	    data=eval("("+data+")");
						settings.onUploadSuccess(data);
				   }
	         },
	         'onSelect' : function(file){
		      	   if (settings.onSelect) {
						settings.onSelect(file);
				   }
	         },
	         'onCancel' : function(event,ID,fileObj,data){
		      	   if (settings.onCancel) {
						settings.onCancel(event,ID,fileObj,data);
				   }
	         }
		 });
	};
	
	// 调用的方法 $("#startTime,#endTime").datepickerStyle();  单只有一个对象时则默认不比较时间
	$.fn.datepickerStyle = function(options) {
		var settings = $.extend({
			startYear : 30,
		    endYear : 30,
		    dateFormat :'yy-mm-dd'
		},options||{}),
		$startYear = new Date().getFullYear() - settings.startYear,
		$endYear = new Date().getFullYear() + settings.endYear,
		$this=$(this);
		$this.each(function(i){
			$this.eq(i).datepicker({
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
					yearRange : $startYear + ':' + $endYear,//年份范围
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
						if($this.size()>1)  //有两个比较的时间
						{
							$this.eq(1).datepicker("option", "minDate",selectedDate);
							$this.eq(0).datepicker("option", "maxDate",selectedDate);
						}
					}
		       });
		});
			
	};

	// 闭包结束
})(jQuery);
