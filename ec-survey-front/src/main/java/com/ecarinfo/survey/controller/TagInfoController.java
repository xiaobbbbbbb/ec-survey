package com.ecarinfo.survey.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
import com.ecarinfo.survey.po.CarInfo;
import com.ecarinfo.survey.po.SurveyUserInfo;
import com.ecarinfo.survey.po.TagInfo;
import com.ecarinfo.survey.rm.CarInfoRM;
import com.ecarinfo.survey.rm.SurveyUserInfoRM;
import com.ecarinfo.survey.rm.TagInfoRM;
import com.ecarinfo.survey.service.TagInfoService;
import com.ecarinfo.survey.view.TagInfoView;

@Controller
@RequestMapping("/tagInfo")
public class TagInfoController extends BaseController {

	private static final Logger logger = Logger.getLogger(TagInfoController.class);

	@Resource
	private TagInfoService tagInfoService;

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String listPost(Integer disabled, String serialNo, Boolean online, Model model) {
		try {
			serialNo = DtoUtil.incode(serialNo);
			ECPage<TagInfoView> ECPage = tagInfoService.queryTagInfoPages(disabled, serialNo, online);
			model.addAttribute("ECPage", ECPage);
			return "manager/taginfo/list";
		} catch (Exception e) {
			logger.error("电子标签列表加载失败", e);
		}
		return null;
	}

	// 添加UI
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(ModelMap model) {
		List<SurveyUserInfo> surveyUserlist = this.genericService.findList(
				SurveyUserInfo.class,
				new Criteria().eq(SurveyUserInfoRM.disabled, 1).eq(SurveyUserInfoRM.onbind, 0, CondtionSeparator.AND)
						.eq(SurveyUserInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND));
		model.addAttribute("surveyUserlist", surveyUserlist);

		List<CarInfo> carList = this.genericService.findList(CarInfo.class,
				new Criteria().eq(CarInfoRM.disabled, 1).eq(CarInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND));
		model.addAttribute("carList", carList);
		return "manager/taginfo/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加身份识别卡", type = "身份识别卡管理")
	public Json add(@RequestBody TagInfo dto) {
		Json json = new Json();
		try {
			dto.setOnline(false);
			dto.setCreateTime(new Date());
			dto.setOrgId(EcUtil.getCurrentUser().getOrgId());
			this.genericService.save(dto);

			// 绑定查勘员
			this.genericService.updateWithCriteria(SurveyUserInfo.class,
					new Criteria().update(SurveyUserInfoRM.onbind, 1).eq(SurveyUserInfoRM.pk, dto.getUserId()));

			// 绑定查勘车辆
			this.genericService.updateWithCriteria(CarInfo.class, new Criteria().update(CarInfoRM.onbind, 1).eq(CarInfoRM.pk, dto.getCarId()));

			json.setMsg("电子标签添加成功!");
			json.setObj(dto.getSerialNo());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("电子标签添加失败!");
			logger.error("添加失败!", e);
		}
		return json;
	}

	// 寻找要修改的
	@RequestMapping(value = "/updateUI", method = RequestMethod.GET)
	public String edit(Integer id, Model model) {
		TagInfo dto = this.genericService.findByPK(TagInfo.class, id);
		model.addAttribute("dto", dto);

		List<SurveyUserInfo> surveyUserlist = this.genericService
				.findList(
						SurveyUserInfo.class,
						new Criteria().eq(SurveyUserInfoRM.disabled, 1).eq(SurveyUserInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(),
								CondtionSeparator.AND));
		model.addAttribute("surveyUserlist", surveyUserlist);

		List<CarInfo> carList = this.genericService.findList(CarInfo.class,
				new Criteria().eq(CarInfoRM.disabled, 1).eq(CarInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND));
		model.addAttribute("carList", carList);
		return "manager/taginfo/add_update";
	}

	// 修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody TagInfo dto) {
		Json json = new Json();
		try {
			TagInfo oldDto = this.genericService.findByPK(TagInfo.class, dto.getId());
			Criteria updateFileld = new Criteria();
			if (oldDto.getCarId() != dto.getCarId()) {
				updateFileld.update(TagInfoRM.carId, dto.getCarId());

				// 绑定车辆
				this.genericService.updateWithCriteria(CarInfo.class, new Criteria().update(CarInfoRM.onbind, 1).eq(CarInfoRM.pk, dto.getCarId()));

				// 解绑车辆
				this.genericService.updateWithCriteria(CarInfo.class, new Criteria().update(CarInfoRM.onbind, 0).eq(CarInfoRM.pk, oldDto.getCarId()));
			}

			if (oldDto.getUserId() != dto.getUserId()) {
				updateFileld.update(TagInfoRM.userId, dto.getUserId());

				// 绑定用户
				this.genericService.updateWithCriteria(SurveyUserInfo.class,
						new Criteria().update(SurveyUserInfoRM.onbind, 1).eq(SurveyUserInfoRM.pk, dto.getUserId()));

				// 解绑用户
				this.genericService.updateWithCriteria(SurveyUserInfo.class,
						new Criteria().update(SurveyUserInfoRM.onbind, 0).eq(SurveyUserInfoRM.pk, oldDto.getUserId()));
			}

			this.genericService.updateWithCriteria(
					TagInfo.class,
					updateFileld.update(TagInfoRM.serialNo, dto.getSerialNo()).update(TagInfoRM.updateTime, DateUtils.currentDateStr())
							.eq(TagInfoRM.pk, dto.getId()));
			json.setMsg("电子标签修改成功!");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("电子标签修改成功!");
			logger.error("修改失败!", e);
		}
		return json;
	}

	// 删除(隐藏不可见)
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	@Action(description = "删除身份识别卡", type = "身份识别卡管理")
	public Json delete(Integer[] ids, Integer userId, Integer carId) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				this.genericService.updateWithCriteria(TagInfo.class,
						new Criteria().update(TagInfoRM.updateTime, DateUtils.currentDateStr()).update(TagInfoRM.disabled, 0).in(TagInfoRM.pk, ids));

				// 解开查勘员绑定
				this.genericService.updateWithCriteria(SurveyUserInfo.class,
						new Criteria().update(SurveyUserInfoRM.onbind, 0).eq(SurveyUserInfoRM.pk, userId));

				// 解开查勘车辆绑定
				this.genericService.updateWithCriteria(CarInfo.class, new Criteria().update(CarInfoRM.onbind, 0).eq(CarInfoRM.pk, carId));

				json.setMsg("电子标签删除成功!");

				List<TagInfo> dtos = this.genericService.findList(TagInfo.class, new Criteria().in(TagInfoRM.pk, ids));
				json.setObj(dtos.get(0).getSerialNo());
			} else {
				json.setSuccess(false);
				json.setMsg("电子标签删除失败!");
			}
		} catch (Exception e) {
			logger.error("删除失败!", e);
		}
		return json;
	}

	// 是否重复
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Json checkName(String serialNo) {
		Json json = new Json();
		long count = 0;
		try {
			serialNo = DtoUtil.incode(serialNo);
			Criteria whereBy = new Criteria();
			if (StringUtils.isNotEmpty(serialNo)) {
				whereBy.eq(TagInfoRM.serialNo, serialNo);
			}
			whereBy.eq(TagInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
			count = this.genericService.count(TagInfo.class, whereBy);
		} catch (Exception e) {
			logger.error("检查是否存在失败!", e);
		}
		json.setObj(count);
		return json;
	}
}
