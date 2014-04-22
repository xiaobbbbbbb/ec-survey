package com.ecarinfo.ralasafe.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.ecarinfo.ralasafe.dto.ZtreeDto;
import com.ecarinfo.ralasafe.po.RalGroup;
import com.ecarinfo.ralasafe.rm.RalGroupRM;
import com.ecarinfo.ralasafe.service.RalGroupService;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.Json;

/**
 * @Description: 公司管理
 */

@Controller
@RequestMapping("ralasafe/group")
public class GroupController extends BaseController {

	private static final Logger logger = Logger.getLogger(GroupController.class);

	@Resource
	private RalGroupService ralGroupService;

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Integer pid, String name, String code, Integer disabled, Model model) {
		List<RalGroup> dtos = getGroups(pid, name, code, disabled);
		model.addAttribute("dtos", dtos);
		return "ralasafe/group/list";
	}

	private List<RalGroup> getGroups(Integer pid, String name, String code, Integer disabled) {
		try {
			name = DtoUtil.incode(name);
			code = DtoUtil.incode(code);
			Criteria whereBy = new Criteria();
			whereBy.eq(RalGroupRM.disabled, 1);
			if (pid == null) {
				pid = 0;
			}
			whereBy.eq(RalGroupRM.parentId, pid, CondtionSeparator.AND);
			if (StringUtils.isNotEmpty(name)) {
				whereBy.like(RalGroupRM.name, "%" + name + "%", CondtionSeparator.AND);
			}
			if (StringUtils.isNotEmpty(code)) {
				whereBy.like(RalGroupRM.code, "%" + code + "%", CondtionSeparator.AND);
			}
			List<RalGroup> dtos = this.genericService.findList(RalGroup.class, whereBy);
			return dtos;
		} catch (Exception e) {
			logger.error("公司列表加载失败", e);
		}
		return null;
	}

	// 表格树
	@RequestMapping(value = "/treeTable", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<RalGroup> treeTable(Integer pid, String name, String code, Integer disabled) {
		List<RalGroup> dtos = getGroups(pid, name, code, disabled);
		return dtos;
	}

	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	@ResponseBody
	public List<ZtreeDto> getZtree(Boolean showUrl, Integer id, HttpServletRequest request) {
		List<ZtreeDto> zTree = new ArrayList<ZtreeDto>();
		try {
			Integer pid = (id != null && id > 0) ? id : 0;
			List<RalGroup> dtos = this.genericService.findList(RalGroup.class,
					new Criteria().eq(RalGroupRM.disabled, 1).eq(RalGroupRM.parentId, pid, CondtionSeparator.AND));

			String webpath = request.getContextPath();
			String target = "group_rightFrame";
			String url = webpath + "/ralasafe/group/updateUI?id=";
			for (RalGroup dto : dtos) {
				ZtreeDto tree = new ZtreeDto();
				tree.setId(dto.getGroupId());
				tree.setName(dto.getName());
				if (dto.getIsLeaf() > 0) {
					tree.setIsParent(true);
					tree.setOpen(true);
				}
				if (showUrl) {
					tree.setUrl(url + dto.getGroupId());
					tree.setTarget(target);
				}
				tree.setpId(dto.getParentId());
				zTree.add(tree);
			}
		} catch (Exception e) {
			logger.error("公司树获取失败!", e);
		}
		return zTree;
	}

	// 添加UI
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI() {
		return "ralasafe/group/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加公司", type = "公司管理")
	public Json add(@RequestBody RalGroup dto) {
		Json json = new Json();
		try {
			if (dto.getParentId() == null) {
				dto.setParentId(0);
			}
			this.ralGroupService.save(dto);
			json.setMsg("公司添加成功!");
			json.setObj(dto.getName());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("公司公司添加失败!");
			logger.error("添加失败!", e);
		}
		return json;
	}

	// 寻找要修改的
	@RequestMapping(value = "/updateUI", method = RequestMethod.GET)
	public String edit(Integer id, Model model) {
		RalGroup dto = this.genericService.findByPK(RalGroup.class, id);
		if (dto.getParentId() > 0) {
			RalGroup pdto = this.genericService.findByPK(RalGroup.class, dto.getParentId());
			dto.setParentName(pdto.getName());
		}
		model.addAttribute("dto", dto);
		return "ralasafe/group/add_update";
	}

	// 修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody RalGroup dto) {
		Json json = new Json();
		try {
			this.ralGroupService.update(dto);
			this.genericService.updateWithCriteria(
					RalGroup.class,
					new Criteria().update(RalGroupRM.name, dto.getName()).update(RalGroupRM.code, dto.getCode())
							.update(RalGroupRM.parentId, dto.getParentId()).update(RalGroupRM.message, dto.getMessage())
							.update(RalGroupRM.updateTime, DateUtils.currentDateStr()).eq(RalGroupRM.pk, dto.getGroupId()));
			json.setMsg("公司修改成功!");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("公司修改失败!");
			logger.error("修改失败!", e);
		}
		return json;
	}

	// 删除(隐藏不可见)
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	@Action(description = "删除公司", type = "公司管理")
	public Json delete(Integer[] ids) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				this.ralGroupService.delete(ids[0]);
				List<RalGroup> dtos = this.genericService.findList(RalGroup.class, new Criteria().in(RalGroupRM.pk, ids));
				json.setObj(dtos.get(0).getName());
			} else {
				json.setSuccess(false);
				json.setMsg("公司删除失败!");
			}
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("公司删除失败!");
			logger.error("删除失败!", e);
		}
		return json;
	}

	// 是否重复
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Json checkName(String name, String code) {
		Json json = new Json();
		long count = 0;
		try {
			name = DtoUtil.incode(name);
			code = DtoUtil.incode(code);
			Criteria whereBy = new Criteria();
			if (StringUtils.isNotEmpty(name)) {
				whereBy.eq(RalGroupRM.name, name);
			}
			if (StringUtils.isNotEmpty(code)) {
				whereBy.eq(RalGroupRM.code, code);
			}
			whereBy.eq(RalGroupRM.disabled, 1, CondtionSeparator.AND);
			count = this.genericService.count(RalGroup.class, whereBy);
		} catch (Exception e) {
			logger.error("检查是否存在失败!", e);
		}
		json.setObj(count);
		return json;
	}
}
