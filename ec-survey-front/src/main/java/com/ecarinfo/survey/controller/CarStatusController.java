package com.ecarinfo.survey.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.survey.po.CarReport;


@Controller
@RequestMapping("/carReport")
public class CarStatusController extends BaseController {

	private static final Logger logger = Logger.getLogger(CarStatusController.class);

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Integer disabled,String name, Model model) {
		try {
			name = DtoUtil.incode(name);
			ECPage<CarReport> ECPage = null;
			model.addAttribute("ECPage", ECPage);
			return "carreport/list";
		} catch (Exception e) {
			logger.error("列表加载失败", e);
		}
		return null;
	}


    // 搜索UI
	@RequestMapping(value = "/searchUI", method = RequestMethod.GET)
	public String searchUI() {
		return "carreport/search";
	}
}
