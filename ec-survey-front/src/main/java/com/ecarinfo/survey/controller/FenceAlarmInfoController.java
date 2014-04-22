package com.ecarinfo.survey.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.survey.service.FenceAlarmInfoService;
import com.ecarinfo.survey.view.FenceAlarmInfoView;
import com.ecarinfo.utils.EcExcelUtil;

@Controller
@RequestMapping("/fenceAlarmInfo")
public class FenceAlarmInfoController extends BaseController {

	private static final Logger logger = Logger.getLogger(FenceAlarmInfoController.class);

	@Resource
	private  FenceAlarmInfoService fenceAlarmInfoService;
 
	
	
	// 围栏告警报告
	@RequestMapping(value = "/listReport", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String listReport(Integer typeName, String startTime,String endTime, String carNo, String surveyName, String surveyTel,Model model) {
		try {
			carNo = DtoUtil.incode(carNo);
			ECPage<FenceAlarmInfoView> ECPage = fenceAlarmInfoService
					.queryFenceAlarmInfoReportPages(typeName, startTime,
							endTime, carNo, surveyName, surveyTel);
			model.addAttribute("ECPage", ECPage);
			return "manager/fencealarminfo/list_report";
		} catch (Exception e) {
			logger.error("查勘员出车报告加载失败", e);
		}
		return null;
	}
	
	// 导出
		@RequestMapping(value = "/download", method = { RequestMethod.GET, RequestMethod.POST })
		public void download(Integer typeName, String startTime,String endTime, String carNo, String surveyName, String surveyTel,HttpServletResponse response, HttpServletRequest request) {
			try {
				carNo = DtoUtil.incode(carNo);
				String fileName = "围栏告警统计.xls";
				String templateFileName = "fencealarminfo.xls";
				List<FenceAlarmInfoView> dtos = fenceAlarmInfoService.queryFenceAlarmInfoViewInfos(typeName, startTime,endTime, carNo, surveyName, surveyTel);
				Map<String, String> datas = new LinkedHashMap<String, String>();
				datas.put("title", "围栏告警统计信息" + "(" + dtos.size() + "条记录)");
				SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
				datas.put("date", f.format(new Date()));
				datas.put("name", "深圳奥创科技有限公司");
				// 下载
				EcExcelUtil.excelDownload(fileName, templateFileName, datas, dtos, FenceAlarmInfoView.class, response, request);
			} catch (Exception e) {
				logger.error("围栏告警统计信息下载失败", e);
			}
		}
}
