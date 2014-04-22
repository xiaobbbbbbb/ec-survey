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
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.survey.po.CarReportImage;
import com.ecarinfo.survey.rm.CarReportImageRM;
import com.ecarinfo.survey.service.CarReportMonthService;
import com.ecarinfo.survey.service.CarReportService;
import com.ecarinfo.survey.view.CarReportMonthView;
import com.ecarinfo.survey.view.CarReportView;
import com.ecarinfo.utils.EcExcelUtil;

@Controller
@RequestMapping("/carReport")
public class CarReportController extends BaseController {

	private static final Logger logger = Logger.getLogger(CarReportController.class);

	@Resource
	private  CarReportService carReportService;
 
	@Resource
	private  CarReportMonthService carReportMonthService;
	
	//行车次报告
	@RequestMapping(value = "/listReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String listReport( String startTime, String endTime, Model model,Integer carId,String month) {
		try {
			ECPage<CarReportView> ECPage = carReportService.queryCarReportSecondReportPages(startTime, endTime,carId,month);
			model.addAttribute("ECPage", ECPage);
			return "manager/carreport/list_report";
		} catch (Exception e) {
			logger.error("行车次报加载失败", e);
		}
		return null;
	}
		
		
	// 行车月报告
	@RequestMapping(value = "/listReportMonth", method = { RequestMethod.GET,RequestMethod.POST })
	public String listReportMonth(String year,String month, Model model) {
		try {
			String monthStr=null;
			if(year!=null){
				monthStr=year;
			}
			if(year!=null && month !=null){
				monthStr=year+"-"+month;
			}
			ECPage<CarReportMonthView> ECPage = carReportMonthService.queryCarReportMonthReportPages(monthStr);
			model.addAttribute("ECPage", ECPage);
			model.addAttribute("year", year);
			model.addAttribute("month", month);
			return "manager/carreport/list_report_month";
		} catch (Exception e) {
			logger.error("行车月报加载失败", e);
		}
		return null;
	}
		
	
	/**
	 * 行车中的图片信息
	 * @param id 行车的id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/carReportImageUI")
	public String carReportImageUI(Integer id, Model model) {
		List<CarReportImage> carReportImagesList = genericService.findList(CarReportImage.class,new Criteria().eq(CarReportImageRM.carReportId, id));
		model.addAttribute("carReportImagesList", carReportImagesList);
		model.addAttribute("carReportImagesListSize", carReportImagesList.size());
		return "manager/carreport/car_report_image";
	}
	
	
	// 导出 详细
	@RequestMapping(value = "/download", method = { RequestMethod.GET, RequestMethod.POST })
	public void download(String startTime,String endTime,Integer carId,String month ,HttpServletResponse response, HttpServletRequest request) {
		try {
			String fileName = "车辆运行报告详细信息.xls";
			String templateFileName = "car_report.xls";
			List<CarReportView> dtos = carReportService.findCarReportInfoReport(startTime, endTime,carId,month);
			Map<String, String> datas = new LinkedHashMap<String, String>();
			datas.put("title", "车辆运行报告详细信息" + "(" + dtos.size() + "条记录)");
			SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			datas.put("date", f.format(new Date()));
			datas.put("name", "深圳奥创科技有限公司");
			// 下载
			EcExcelUtil.excelDownload(fileName, templateFileName, datas, dtos, CarReportView.class, response, request);
		} catch (Exception e) {
			logger.error("车辆运行报告详细下载失败", e);
		}
	}
	// 导出 详细
	@RequestMapping(value = "/downloadCarReportMonth", method = { RequestMethod.GET, RequestMethod.POST })
	public void downloadCarReportMonth(String month,HttpServletResponse response, HttpServletRequest request) {
		try {
			String fileName = "车辆运行报告汇总信息.xls";
			String templateFileName = "car_report_month.xls";
			List<CarReportMonthView> dtos = carReportMonthService.findCarReportMonthReportInfo(month);
			Map<String, String> datas = new LinkedHashMap<String, String>();
			datas.put("title", "车辆运行报告详细信息" + "(" + dtos.size() + "条记录)");
			SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			datas.put("date", f.format(new Date()));
			datas.put("name", "深圳奥创科技有限公司");
			// 下载
			EcExcelUtil.excelDownload(fileName, templateFileName, datas, dtos, CarReportMonthView.class, response, request);
		} catch (Exception e) {
			logger.error("车辆运行报告汇总下载失败", e);
		}
	}
	
}
