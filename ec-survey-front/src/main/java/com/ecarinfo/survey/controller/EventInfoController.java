package com.ecarinfo.survey.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.survey.service.EventInfoService;
import com.ecarinfo.survey.view.EventInfoNoScheduledCountView;
import com.ecarinfo.survey.view.EventInfoScheduledCountView;
import com.ecarinfo.survey.view.EventInfoScheduledView;
import com.ecarinfo.survey.view.EventInfoTotalReportView;
import com.ecarinfo.survey.view.EventInfoView;
import com.ecarinfo.utils.EcExcelUtil;

@Controller
@RequestMapping("/eventInfo")
public class EventInfoController extends BaseController {

	private static final Logger logger = Logger.getLogger(EventInfoController.class);

	@Resource
	private EventInfoService eventInfoService;

	// 案件查询列表（未调度）
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(String startTime, String endTime, Integer areaId, String carNo, Integer status, String address,Integer flag, Model model) {
		try {
			//当前用户
			carNo = DtoUtil.incode(carNo);
			address = DtoUtil.incode(address);
			ECPage<EventInfoView> ECPage = eventInfoService.queryEventInfoPages(startTime, endTime, areaId, carNo, status, address,1);
			model.addAttribute("ECPage", ECPage);
			return "manager/eventinfo/list";
		} catch (Exception e) {
			logger.error("案件查询加载失败", e);
		}
		return null;
	}
	
	// 案件查询列表（已经调度）
	@RequestMapping(value = "/listScheduledList", method = { RequestMethod.GET, RequestMethod.POST })
	public String listScheduledList(String startTime, String endTime, Integer areaId, String carNo, Integer status, String address,Integer flag, Model model) {
		try {
			carNo = DtoUtil.incode(carNo);
			address = DtoUtil.incode(address);
			ECPage<EventInfoView> ECPage = eventInfoService.queryEventInfoPages(startTime, endTime, areaId, carNo, status, address,2);
			model.addAttribute("ECPage", ECPage);
			return "manager/eventinfo/list_scheduled_list";
		} catch (Exception e) {
			logger.error("案件查询加载失败", e);
		}
		return null;
	}

	// 案件统计未调度列表
	@RequestMapping(value = "/listNoScheduledCount", method = { RequestMethod.GET, RequestMethod.POST })
	public String listNoScheduledCount(String startTime, String endTime, ModelMap model) {
		try {
			List<EventInfoNoScheduledCountView> dtos = eventInfoService.queryEventInfoNoScheduledTotal(startTime, endTime);
			for (EventInfoNoScheduledCountView dto : dtos) {
				// 待调度0、已调度1、已完成2,已取消报案3

				if (dto.getStatus() == 0) {
					model.put("stattus0", dto.getCount());
				}
				if (dto.getStatus() == 1) {
					model.put("stattus1", dto.getCount());
				}
				if (dto.getStatus() == 2) {
					model.put("stattus2", dto.getCount());
				}
				if (dto.getStatus() == 3) {
					model.put("stattus3", dto.getCount());
				}
			}
			return "manager/eventinfo/list_no_scheduled_count";
		} catch (Exception e) {
			logger.error("案件统计未调度加载失败", e);
		}
		return null;
	}

	// 案件统计已调度列表
	@RequestMapping(value = "/listScheduledCount", method = { RequestMethod.GET, RequestMethod.POST })
	public String listScheduledCount(String startTime, String endTime, Integer areaId, ModelMap model) {
		try {
			List<EventInfoScheduledCountView> dtos = eventInfoService.queryEventInfoScheduledTotal(startTime, endTime, areaId);
			Set<EventInfoScheduledView> set = new HashSet<EventInfoScheduledView>();

			for (EventInfoScheduledCountView dto : dtos) {
				EventInfoScheduledView vi = new EventInfoScheduledView();
				vi.setAreaName(dto.getAreaName());
				vi.setCarNo(dto.getCarNo());
				for (EventInfoScheduledCountView dt : dtos) {
					if (dto.getCarNo()!=null&&dt.getCarNo()!=null&&dto.getCarNo().equals(dt.getCarNo())) {
						if (dt.getStatus() == 1) {
							vi.setNoComplete(dt.getCount());
						}
						if (dt.getStatus() == 2) {
							vi.setComplete(dt.getCount());
						}
						if (dt.getStatus() == 3) {
							vi.setEnd(dt.getCount());
						}
					}
				}
				set.add(vi);
			}

			model.addAttribute("dtos", set);
			return "manager/eventinfo/list_scheduled_count";
		} catch (Exception e) {
			logger.error("案件统计已调度加载失败", e);
		}
		return null;
	}

	// 报案统计
	@RequestMapping(value = "/listReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String listCount(String startTime, String endTime, Model model) {
		try {
			List<EventInfoTotalReportView> dtos = eventInfoService.findEventInfoTotalReport(startTime, endTime);
			model.addAttribute("dtos", dtos);
			return "manager/eventinfo/list_report";
		} catch (Exception e) {
			logger.error("案件统计加载失败", e);
		}
		return null;
	}

	// 导出
	@RequestMapping(value = "/download", method = { RequestMethod.GET, RequestMethod.POST })
	public void download(String startTime, String endTime, Integer areaId, String carNo, Integer status, String address,
			HttpServletResponse response, HttpServletRequest request) {
		try {
			carNo = DtoUtil.incode(carNo);
			address = DtoUtil.incode(address);
			String fileName = "案件表.xls";
			String templateFileName = "gindex.xls";
			List<EventInfoView> dtos = this.eventInfoService.queryEventInfos(startTime, endTime, areaId, carNo, status, address);

			Map<String, String> datas = new LinkedHashMap<String, String>();
			datas.put("title", "案件表" + "(" + dtos.size() + "条记录)");
			SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			datas.put("date", f.format(new Date()));
			datas.put("name", "深圳奥创科技有限公司");
			// 下载
			EcExcelUtil.excelDownload(fileName, templateFileName, datas, dtos, EventInfoView.class, response, request);
		} catch (Exception e) {
			logger.error("案件下载失败", e);
		}
	}
	
	// 导出
	@RequestMapping(value = "/downloadCaseInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public void downloadCaseInfo(String startTime, String endTime,
			HttpServletResponse response, HttpServletRequest request) {
		try {
			String fileName = "报案统计.xls";
			String templateFileName = "eventinfototalreport.xls";
			List<EventInfoTotalReportView> dtos = this.eventInfoService.findEventInfoTotalReport(startTime, endTime);

			Map<String, String> datas = new LinkedHashMap<String, String>();
			datas.put("title", "案件表" + "(" + dtos.size() + "条记录)");
			SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			datas.put("date", f.format(new Date()));
			datas.put("name", "深圳奥创科技有限公司");
			// 下载
			EcExcelUtil.excelDownload(fileName, templateFileName, datas, dtos, EventInfoTotalReportView.class, response, request);
		} catch (Exception e) {
			logger.error("案件下载失败", e);
		}
	}
}
