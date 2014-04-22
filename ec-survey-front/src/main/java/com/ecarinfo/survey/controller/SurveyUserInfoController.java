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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.log.Action;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.survey.po.AreaInfo;
import com.ecarinfo.survey.po.SurveyUserInfo;
import com.ecarinfo.survey.rm.SurveyUserInfoRM;
import com.ecarinfo.survey.service.SurveyUserInfoService;
import com.ecarinfo.survey.view.SurveyUserInfoView;
import com.ecarinfo.utils.EcExcelUtil;

@Controller
@RequestMapping("/surveyUserInfo")
public class SurveyUserInfoController extends BaseController {

	private static final Logger logger = Logger.getLogger(SurveyUserInfoController.class);

	@Resource
	private SurveyUserInfoService surveyUserInfoService;

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(String name, Integer status, Integer disabled, Model model) {
		try {
			name = DtoUtil.incode(name);
			ECPage<SurveyUserInfoView> ECPage = surveyUserInfoService.querySurveyUserInfoPages(name, status, disabled);
			model.addAttribute("ECPage", ECPage);
			return "manager/surveyuserinfo/list";
		} catch (Exception e) {
			logger.error("查勘员列表加载失败", e);
		}
		return null;
	}

	// 查勘员出车报告
	@RequestMapping(value = "/listReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String listReport(String startTime, String endTime, String name, Model model) {
		try {
			name = DtoUtil.incode(name);
			ECPage<SurveyUserInfoView> ECPage = surveyUserInfoService.querySurveyUserInfoReportPages(startTime, endTime, name);
			model.addAttribute("ECPage", ECPage);
			return "manager/surveyuserinfo/list_report";
		} catch (Exception e) {
			logger.error("查勘员出车报告加载失败", e);
		}
		return null;
	}

	// 添加UI
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI() {
		return "manager/surveyuserinfo/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加查勘员", type = "查勘员信息")
	public Json add(@RequestBody SurveyUserInfo dto) {
		Json json = new Json();
		try {
			dto.setCreateTime(new Date());
			dto.setOnbind(0);
			dto.setOrgId(EcUtil.getCurrentUser().getOrgId());
			this.genericService.save(dto);
			json.setMsg("查勘员添加成功!");
			json.setObj(dto.getName());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("查勘员添加失败!");
			logger.error("添加失败!", e);
		}
		return json;
	}

	// 寻找要修改的
	@RequestMapping(value = "/updateUI", method = RequestMethod.GET)
	public String edit(Integer id, Model model) {
		SurveyUserInfo dto = this.genericService.findByPK(SurveyUserInfo.class, id);
		model.addAttribute("dto", dto);

		if (dto != null && dto.getAreaId() > 0) {
			AreaInfo area = this.genericService.findByPK(AreaInfo.class, dto.getAreaId());
			model.addAttribute("areaName", area.getName());
		}

		return "manager/surveyuserinfo/add_update";
	}

	// 修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody SurveyUserInfo dto) {
		Json json = new Json();
		try {
			this.genericService.updateWithCriteria(
					SurveyUserInfo.class,
					new Criteria().update(SurveyUserInfoRM.areaId, dto.getAreaId()).update(SurveyUserInfoRM.name, dto.getName())
							.update(SurveyUserInfoRM.phoneNo, dto.getPhoneNo()).update(SurveyUserInfoRM.driveNo, dto.getDriveNo())
							.update(SurveyUserInfoRM.driveToTime, DateUtils.dateToString(dto.getDriveToTime(), TimeFormatter.YYYY_MM_DD))
							.update(SurveyUserInfoRM.updateTime, DateUtils.currentDateStr()).eq(SurveyUserInfoRM.pk, dto.getId()));
			json.setMsg("查勘员修改成功!");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("查勘员修改失败!");
			logger.error("修改失败!", e);
		}
		return json;
	}

	// 删除(隐藏不可见)
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	@Action(description = "删除查勘员", type = "查勘员信息")
	public Json delete(Integer[] ids) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				this.genericService.updateWithCriteria(
						SurveyUserInfo.class,
						new Criteria().update(SurveyUserInfoRM.updateTime, DateUtils.currentDateStr()).update(SurveyUserInfoRM.disabled, 0)
								.in(SurveyUserInfoRM.pk, ids));
				json.setMsg("查勘员删除成功!");

				List<SurveyUserInfo> dtos = this.genericService.findList(SurveyUserInfo.class, new Criteria().in(SurveyUserInfoRM.pk, ids));
				json.setObj(dtos.get(0).getName());

			} else {
				json.setSuccess(false);
				json.setMsg("查勘员删除失败!");
			}
		} catch (Exception e) {
			logger.error("删除失败!", e);
		}
		return json;
	}

	// 是否重复
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Json checkName(String name) {
		Json json = new Json();
		long count = 0;
		try {
			name = DtoUtil.incode(name);
			count = this.genericService.count(SurveyUserInfo.class, new Criteria().eq(SurveyUserInfoRM.name, name));
		} catch (Exception e) {
			logger.error("检查是否存在失败!", e);
		}
		json.setObj(count);
		return json;
	}

	// 搜索UI
	@RequestMapping(value = "/searchUI", method = RequestMethod.GET)
	public String searchUI() {
		return "manager/surveyuserinfo/search";
	}
	
	// 导出
	@RequestMapping(value = "/download", method = { RequestMethod.GET, RequestMethod.POST })
	public void downloadCaseInfo(String startTime, String endTime,String name,
			HttpServletResponse response, HttpServletRequest request) {
		try {
			String fileName = "勘查员工作报告.xls";
			String templateFileName = "surveyuserinfo.xls";
			List<SurveyUserInfoView> dtos = this.surveyUserInfoService.querySurveyUserInfos(startTime, endTime,name);

			Map<String, String> datas = new LinkedHashMap<String, String>();
			datas.put("title", "案件表" + "(" + dtos.size() + "条记录)");
			SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			datas.put("date", f.format(new Date()));
			datas.put("name", "深圳奥创科技有限公司");
			// 下载
			EcExcelUtil.excelDownload(fileName, templateFileName, datas, dtos, SurveyUserInfoView.class, response, request);
		} catch (Exception e) {
			logger.error("案件下载失败", e);
		}
	}
}
