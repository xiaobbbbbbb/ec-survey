package com.ecarinfo.survey.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.survey.po.CityInfo;
import com.ecarinfo.survey.rm.CityInfoRM;
import com.ecarinfo.survey.service.CityInfoService;

@Controller
@RequestMapping("/cityInfo")
public class CityInfoController extends BaseController {

	private static final Logger logger = Logger.getLogger(CityInfoController.class);

	@Resource
	private CityInfoService cityInfoService;

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String listPost(Integer disabled, String name, Model model) {
		try {
			name = DtoUtil.incode(name);
			ECPage<CityInfo> ECPage = null;
			model.addAttribute("ECPage", ECPage);
			return "cityinfo/list";
		} catch (Exception e) {
			logger.error("列表加载失败", e);
		}
		return null;
	}

	// 添加UI
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI() {
		return "cityinfo/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Json add(@RequestBody CityInfo dto) {
		Json json = new Json();
		try {
			this.genericService.save(dto);
			json.setMsg("添加成功!");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("添加失败!");
			logger.error("添加失败!", e);
		}
		return json;
	}

	// 寻找要修改的
	@RequestMapping(value = "/updateUI", method = RequestMethod.GET)
	public String edit(Integer id, Model model) {
		CityInfo dto = this.genericService.findByPK(CityInfo.class, id);
		model.addAttribute("dto", dto);
		
		return "cityinfo/add_update";
	}

	// 修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody CityInfo dto) {
		Json json = new Json();
		try {
			this.genericService.updateWithCriteria(CityInfo.class, new Criteria().eq(CityInfoRM.pk, dto.getId()));
			json.setMsg("修改成功!");
		} catch (Exception e) {
			logger.error("修改失败!", e);
		}
		return json;
	}

	// 删除(隐藏不可见)
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(Integer[] ids) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				this.genericService.updateWithCriteria(CityInfo.class,
						new Criteria().update(CityInfoRM.updateTime,  DateUtils.currentDateStr()).in(CityInfoRM.pk, ids));
				json.setMsg("删除成功!");
			} else {
				json.setSuccess(false);
				json.setMsg("删除失败!");
			}
		} catch (Exception e) {
			logger.error("删除失败!", e);
		}
		return json;
	}

	// 是否重复
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Json checkName(String loginName) {
		Json json = new Json();
		int flag = 0;
		try {
			loginName = DtoUtil.incode(loginName);
			List<CityInfo> dtos = this.genericService.findList(CityInfo.class, new Criteria().eq(CityInfoRM.pk, loginName));
			flag = dtos.size();
		} catch (Exception e) {
			logger.error("检查是否存在失败!", e);
		}
		json.setObj(flag);
		return json;
	}

	// 搜索UI
	@RequestMapping(value = "/searchUI", method = RequestMethod.GET)
	public String searchUI() {
		return "cityinfo/search";
	}
}
