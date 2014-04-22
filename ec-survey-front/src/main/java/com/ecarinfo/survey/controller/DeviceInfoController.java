package com.ecarinfo.survey.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.log.Action;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.survey.po.DeviceInfo;
import com.ecarinfo.survey.rm.DeviceInfoRM;
import com.ecarinfo.survey.service.DeviceInfoService;

@Controller
@RequestMapping("/deviceInfo")
public class DeviceInfoController extends BaseController {

	private static final Logger logger = Logger.getLogger(DeviceInfoController.class);

	@Resource
	private DeviceInfoService deviceInfoService;

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String listPost(Integer disabled, String serialNo, Boolean online, Model model) {
		try {
			serialNo = DtoUtil.incode(serialNo);
			ECPage<DeviceInfo> ECPage = deviceInfoService.queryDeviceInfoPages(disabled, serialNo, online);
			model.addAttribute("ECPage", ECPage);
			return "manager/deviceinfo/list";
		} catch (Exception e) {
			logger.error("设备列表加载失败", e);
		}
		return null;
	}

	// 添加UI
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(Model model) {
		return "manager/deviceinfo/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加设备", type = "设备管理")
	public Json add(@RequestBody DeviceInfo dto) {
		Json json = new Json();
		try {
			dto.setOnline(false);
			dto.setCreateTime(new Date());
			dto.setDisabled(1);
			dto.setOnbind(0);
			dto.setOrgId(EcUtil.getCurrentUser().getOrgId());
			this.genericService.save(dto);
			json.setMsg("设备添加成功!");
			json.setObj(dto.getSerialNo());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("设备添加失败!");
			logger.error("添加失败!", e);
		}
		return json;
	}

	// 寻找要修改的
	@RequestMapping(value = "/updateUI", method = RequestMethod.GET)
	public String edit(Integer id, Model model) {
		DeviceInfo dto = this.genericService.findByPK(DeviceInfo.class, id);
		model.addAttribute("dto", dto);
		return "manager/deviceinfo/add_update";
	}

	// 修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody DeviceInfo dto) {
		Json json = new Json();
		try {
			this.genericService.updateWithCriteria(
					DeviceInfo.class,
					new Criteria().update(DeviceInfoRM.serialNo, dto.getSerialNo()).update(DeviceInfoRM.code, dto.getCode())
							.update(DeviceInfoRM.updateTime, DateUtils.currentDateStr()).eq(DeviceInfoRM.pk, dto.getId()));
			json.setMsg("设备修改成功!");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("设备修改失败!");
			logger.error("修改失败!", e);
		}
		return json;
	}

	// 删除(隐藏不可见)
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	@Action(description = "删除设备", type = "设备管理")
	public Json delete(Integer[] ids) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				this.genericService.updateWithCriteria(DeviceInfo.class, new Criteria().update(DeviceInfoRM.updateTime, DateUtils.currentDateStr())
						.update(DeviceInfoRM.disabled, 0).in(DeviceInfoRM.pk, ids));
				json.setMsg("设备删除成功!");

				List<DeviceInfo> dtos = this.genericService.findList(DeviceInfo.class, new Criteria().in(DeviceInfoRM.pk, ids));
				json.setObj(dtos.get(0).getSerialNo());
			} else {
				json.setSuccess(false);
				json.setMsg("设备删除失败!");
			}
		} catch (Exception e) {
			logger.error("删除失败!", e);
		}
		return json;
	}

	// 是否重复
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Json checkName(String serialNo, String code) {
		Json json = new Json();
		long count = 0;
		try {
			serialNo = DtoUtil.incode(serialNo);
			code = DtoUtil.incode(code);
			Criteria whereBy = new Criteria();
			if (StringUtils.isNotEmpty(serialNo)) {
				whereBy.eq(DeviceInfoRM.serialNo, serialNo);
			}
			if (StringUtils.isNotEmpty(code)) {
				whereBy.eq(DeviceInfoRM.code, code);
			}
			whereBy.eq(DeviceInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
			count = this.genericService.count(DeviceInfo.class, whereBy);
		} catch (Exception e) {
			logger.error("检查是否存在失败!", e);
		}
		json.setObj(count);
		return json;
	}

	// 搜索UI
	@RequestMapping(value = "/searchUI", method = RequestMethod.GET)
	public String searchUI() {
		return "manager/deviceinfo/search";
	}
}
