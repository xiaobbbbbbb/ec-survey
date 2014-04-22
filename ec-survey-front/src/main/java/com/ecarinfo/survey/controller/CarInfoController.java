package com.ecarinfo.survey.controller;

import java.util.Date;
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
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.log.Action;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.po.RalOrg;
import com.ecarinfo.ralasafe.rm.RalOrgRM;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.survey.po.CarInfo;
import com.ecarinfo.survey.po.DeviceInfo;
import com.ecarinfo.survey.rm.CarInfoRM;
import com.ecarinfo.survey.rm.DeviceInfoRM;
import com.ecarinfo.survey.service.CarInfoService;
import com.ecarinfo.survey.view.CarInfoView;

@Controller
@RequestMapping("/carInfo")
public class CarInfoController extends BaseController {

	private static final Logger logger = Logger.getLogger(CarInfoController.class);

	@Resource
	private CarInfoService carInfoService;

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Integer disabled, String carNo, Model model) {
		try {
			carNo = DtoUtil.incode(carNo);
			ECPage<CarInfoView> ECPage = carInfoService.queryCarInfoPages(disabled, carNo);
			model.addAttribute("ECPage", ECPage);
			return "manager/carinfo/list";
		} catch (Exception e) {
			logger.error("查勘车辆列表加载失败", e);
		}
		return null;
	}

	// 车辆运行报告
	@RequestMapping(value = "/listReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String listReport(String startTime, String endTime, String carNo, Model model) {
		try {
			carNo = DtoUtil.incode(carNo);
			ECPage<CarInfoView> ECPage = carInfoService.queryCarInfoReportPages(startTime, endTime, carNo);
			model.addAttribute("ECPage", ECPage);
			return "manager/carinfo/list_report";
		} catch (Exception e) {
			logger.error("车辆运行报告加载失败", e);
		}
		return null;
	}

	// 添加UI
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(Model model) {
		List<RalOrg> listOrg = this.genericService.findList(RalOrg.class, new Criteria().eq(RalOrgRM.disabled, 1));
		model.addAttribute("listOrg", listOrg);

		List<DeviceInfo> listDevice = this.genericService.findList(
				DeviceInfo.class,
				new Criteria().eq(DeviceInfoRM.disabled, 1).eq(DeviceInfoRM.onbind, 0, CondtionSeparator.AND)
						.eq(DeviceInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND));
		model.addAttribute("deviceList", listDevice);

		return "manager/carinfo/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加查勘车", type = "查勘车辆信息")
	public Json add(@RequestBody CarInfo dto) {
		Json json = new Json();
		try {
			dto.setCreateTime(new Date());
			dto.setDisabled(1);
			dto.setOnbind(0);
			dto.setOrgId(EcUtil.getCurrentUser().getOrgId());

			// 绑定设备
			this.genericService.updateWithCriteria(DeviceInfo.class,
					new Criteria().update(DeviceInfoRM.onbind, 1).eq(DeviceInfoRM.pk, dto.getDeviceId()));

			this.genericService.save(dto);
			json.setMsg("查勘车辆添加成功!");
			json.setObj(dto.getCarNo());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("查勘车辆添加失败!");
			logger.error("添加失败!", e);
		}
		return json;
	}

	// 寻找要修改的
	@RequestMapping(value = "/updateUI", method = RequestMethod.GET)
	public String edit(Integer id, Model model) {
		CarInfo dto = this.genericService.findByPK(CarInfo.class, id);
		model.addAttribute("dto", dto);

		List<DeviceInfo> listDevice = this.genericService.findList(DeviceInfo.class,
				new Criteria().eq(DeviceInfoRM.disabled, 1).eq(DeviceInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND));
		model.addAttribute("deviceList", listDevice);
		return "manager/carinfo/add_update";
	}

	// 修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody CarInfo dto) {
		Json json = new Json();
		try {
			CarInfo oldDto = this.genericService.findByPK(CarInfo.class, dto.getId());
			Criteria updateFileld = new Criteria();
			if (oldDto.getDeviceId() != dto.getDeviceId()) {
				updateFileld.update(CarInfoRM.deviceId, dto.getDeviceId());

				// 绑定设备
				this.genericService.updateWithCriteria(DeviceInfo.class,
						new Criteria().update(DeviceInfoRM.onbind, 1).eq(DeviceInfoRM.pk, dto.getDeviceId()));

				// 解绑设备
				this.genericService.updateWithCriteria(DeviceInfo.class,
						new Criteria().update(DeviceInfoRM.onbind, 0).eq(DeviceInfoRM.pk, oldDto.getDeviceId()));

			}
			this.genericService.updateWithCriteria(
					CarInfo.class,
					updateFileld.update(CarInfoRM.carNo, dto.getCarNo()).update(CarInfoRM.carModel, dto.getCarModel())
							.update(CarInfoRM.inspectionTime, DateUtils.dateToString(dto.getInspectionTime(), TimeFormatter.FORMATTER1))
							.eq(CarInfoRM.pk, dto.getId()));
			json.setMsg("查勘车辆修改成功!");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("查勘车辆修改失败!");
			logger.error("修改失败!", e);
		}
		return json;
	}

	// 修改查勘车在线状态
	@RequestMapping(value = "/updateStatus", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	@Action(description = "修改查勘车状态", type = "查勘车辆信息")
	public Json updateStatus(Integer id, Integer status) {
		Json json = new Json();
		try {
			this.genericService.updateWithCriteria(CarInfo.class, new Criteria().update(CarInfoRM.status, status).eq(CarInfoRM.pk, id));
			List<CarInfo> dtos = this.genericService.findList(CarInfo.class, new Criteria().eq(CarInfoRM.pk, id));
			json.setMsg("查勘车辆修改成功!");
			json.setObj(dtos.get(0).getCarNo());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("查勘车辆修改失败!");
			logger.error("修改失败!", e);
		}
		return json;
	}

	// 删除(隐藏不可见)
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	@Action(description = "删除查勘车", type = "查勘车辆信息")
	public Json delete(Integer[] ids, Integer deviceId) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				this.genericService.updateWithCriteria(CarInfo.class,
						new Criteria().update(CarInfoRM.updateTime, DateUtils.currentDateStr()).update(CarInfoRM.disabled, 0).in(CarInfoRM.pk, ids));

				// 解除设备绑定
				this.genericService.updateWithCriteria(DeviceInfo.class, new Criteria().update(DeviceInfoRM.onbind, 0).eq(DeviceInfoRM.pk, deviceId));

				List<CarInfo> dtos = this.genericService.findList(CarInfo.class, new Criteria().in(CarInfoRM.pk, ids));
				json.setObj(dtos.get(0).getCarNo());

				json.setMsg("查勘车辆删除成功!");
			} else {
				json.setSuccess(false);
				json.setMsg("查勘车辆删除失败!");
			}
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("查勘车辆删除失败!");
			logger.error("删除失败!", e);
		}
		return json;
	}

	// 是否重复
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Json checkName(String carNo) {
		Json json = new Json();
		long count = 0;
		try {
			carNo = DtoUtil.incode(carNo);
			count = this.genericService.count(CarInfo.class,
					new Criteria().eq(CarInfoRM.carNo, carNo).eq(CarInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND));
		} catch (Exception e) {
			logger.error("检查是否存在失败!", e);
		}
		json.setObj(count);
		return json;
	}
}
