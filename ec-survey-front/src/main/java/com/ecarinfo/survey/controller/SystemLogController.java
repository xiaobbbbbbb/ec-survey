package com.ecarinfo.survey.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.survey.po.SystemLog;
import com.ecarinfo.survey.service.SystemLogService;

@Controller
@RequestMapping("/systemLog")
public class SystemLogController extends BaseController {

	private static final Logger logger = Logger.getLogger(SystemLogController.class);

	@Resource
	private SystemLogService systemLogService;

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String listPost(String userName, String action, String type, String startDate, String endDate, ModelMap model) {
		try {
			userName = DtoUtil.incode(userName);
			action = DtoUtil.incode(action);
			type = DtoUtil.incode(type);
			Integer orgId = EcUtil.getCurrentUser().getOrgId();
			ECPage<SystemLog> ECPage = systemLogService.querySystemLogPages(orgId, userName, action, type, startDate, endDate);
			model.addAttribute("ECPage", ECPage);
			RalUser user = EcUtil.getCurrentUser();
			model.put("loginName", user.getLoginName());
			return "manager/systemlog/list";
		} catch (Exception e) {
			logger.error("列表加载失败", e);
		}
		return null;
	}

	// 搜索UI
	@RequestMapping(value = "/searchUI", method = RequestMethod.GET)
	public String searchUI() {
		return "admin/systemlog/search";
	}
}
