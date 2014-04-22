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
import com.ecarinfo.log.Action;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.po.RalGroup;
import com.ecarinfo.ralasafe.rm.RalGroupRM;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.survey.po.MarkType;
import com.ecarinfo.survey.rm.MarkTypeRM;
import com.ecarinfo.survey.service.MarkTypeService;

@Controller
@RequestMapping("markType")
public class MarkTypeController extends BaseController {

	private static final Logger logger = Logger.getLogger(MarkTypeController.class);

	@Resource
	private MarkTypeService markTypeService;

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String listPost(Integer disabled, String name, Model model) {
		try {
			name = DtoUtil.incode(name);
			ECPage<MarkType> ECPage = markTypeService.queryMarkTypePages(disabled, name);
			model.addAttribute("ECPage", ECPage);
			return "manager/marktype/list";
		} catch (Exception e) {
			logger.error("标注分类列表加载失败", e);
		}
		return null;
	}

	// 添加UI
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI() {
		return "manager/marktype/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加标注类别", type = "标注类别管理")
	public Json add(@RequestBody MarkType dto) {
		Json json = new Json();
		try {
			dto.setCreateTime(new Date());
			dto.setUpdateTime(new Date());
			dto.setOrgId(EcUtil.getCurrentUser().getOrgId());
			if(dto.getDisabled()==null){
				dto.setDisabled(1);
			}else{
				dto.setDisabled(dto.getDisabled());
			}
			this.genericService.save(dto);
			json.setMsg("标注类别添加成功!");
			json.setObj(dto.getName());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("标注类别添加失败!");
			logger.error("添加失败!", e);
		}
		return json;
	}

	// 寻找要修改的
	@RequestMapping(value = "/updateUI", method = RequestMethod.GET)
	public String edit(Integer id, Model model) {
		MarkType dto = this.genericService.findByPK(MarkType.class, id);
		model.addAttribute("dto", dto);
		return "manager/marktype/add_update";
	}

	// 修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody MarkType dto) {
		Json json = new Json();
		try {
			this.genericService.updateWithCriteria(
					MarkType.class,
					new Criteria().update(MarkTypeRM.name, dto.getName()).update(MarkTypeRM.img, dto.getImg()).update(MarkTypeRM.disabled, dto.getDisabled())
							.update(MarkTypeRM.updateTime, DateUtils.currentDateStr()).eq(MarkTypeRM.pk, dto.getId()));
			json.setMsg("标注类别修改成功!");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("标注类别修改失败!");
			logger.error("修改失败!", e);
		}
		return json;
	}

	// 删除(隐藏不可见)
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	@Action(description = "删除标注类别", type = "标注类别管理")
	public Json delete(Integer[] ids) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				this.genericService.updateWithCriteria(MarkType.class, new Criteria().update(MarkTypeRM.updateTime, DateUtils.currentDateStr())
						.update(MarkTypeRM.disabled, 0).in(MarkTypeRM.pk, ids));
				json.setMsg("标注类别删除成功!");
				List<RalGroup> dtos = this.genericService.findList(RalGroup.class, new Criteria().in(RalGroupRM.pk, ids));
				json.setObj(dtos.get(0).getName());
			} else {
				json.setSuccess(false);
				json.setMsg("标注类别删除失败!");
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
			count = this.genericService.count(MarkType.class, new Criteria().eq(MarkTypeRM.name, name));
		} catch (Exception e) {
			logger.error("检查是否存在失败!", e);
		}
		json.setObj(count);
		return json;
	}

	// 搜索UI
	@RequestMapping(value = "/searchUI", method = RequestMethod.GET)
	public String searchUI() {
		return "manager/marktype/search";
	}
}
