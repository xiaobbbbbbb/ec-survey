package com.ecarinfo.ralasafe.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.log.Action;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.base.RalBaseController;
import com.ecarinfo.ralasafe.po.RalRole;
import com.ecarinfo.ralasafe.rm.RalRoleRM;
import com.ecarinfo.ralasafe.service.RalRoleService;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.Json;

/**
 * @Description: 角色管理
 */

@Controller
@RequestMapping("ralasafe/role")
public class RoleController extends RalBaseController {

	private static final Logger logger = Logger.getLogger(RoleController.class);

	@Resource
	private RalRoleService ralRoleService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		ECPage<RalRole> ECPage = this.ralRoleService.queryPage(EcUtil.getCurrentUser().getOrgId());
		model.addAttribute("ECPage", ECPage);
		return "ralasafe/role/list";
	}

	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI() {
		return "ralasafe/role/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加角色", type = "角色管理")
	public Json add(@RequestBody RalRole dto) {
		Json json = new Json();
		try {
			dto.setOrgId(EcUtil.getCurrentUser().getOrgId());
			this.ralRoleService.save(dto);
			json.setMsg("角色添加成功!");
			json.setObj(dto.getName());
		} catch (Exception e) {
			logger.error("角色添加失败!", e);
		}
		return json;
	}

	/**
	 * 寻找要修改的
	 */
	@RequestMapping(value = "/updateUI", method = RequestMethod.GET)
	public String updateUI(Integer id, Model model) {
		RalRole dto = genericService.findByPK(RalRole.class, id);
		model.addAttribute("dto", dto);
		return "ralasafe/role/add_update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody RalRole dto) {
		Json json = new Json();
		try {
			dto.setOrgId(EcUtil.getCurrentUser().getOrgId());
			this.ralRoleService.update(dto);
			json.setMsg("角色信息修改成功!");
		} catch (Exception e) {
			logger.error("角色信息修改失败!", e);
		}
		return json;
	}

	// 删除
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	@Action(description = "删除角色", type = "角色管理")
	public Json delete(Integer[] ids) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				this.ralRoleService.delete(ids);
				json.setMsg("角色删除成功!");

				List<RalRole> dtos = this.genericService.findList(RalRole.class, new Criteria().in(RalRoleRM.pk, ids));
				json.setObj(dtos.get(0).getName());
			} else {
				json.setSuccess(false);
				json.setMsg("角色删除失败!");
			}
		} catch (Exception e) {
			logger.error("角色删除失败!", e);
		}
		return json;
	}

	/**
	 * 选择角色界面
	 */
	@RequestMapping(value = "/selectRole", method = RequestMethod.GET)
	public String selectDep(Model model) {
		List<RalRole> dtos = this.genericService.findList(RalRole.class, new Criteria().eq(RalRoleRM.orgId, EcUtil.getCurrentUser().getOrgId())
				.orderBy(RalRoleRM.roleId, OrderType.ASC));
		model.addAttribute("dtos", dtos);
		return "ralasafe/role/select_role";
	}

	/**
	 * 删除角色之前的数据检验---根据多个角色id，获取角色集合
	 */
	@RequestMapping(value = "/delCheckData", method = RequestMethod.GET)
	@ResponseBody
	public Json delCheckData(Integer[] ids) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				List<RalRole> dtos = this.ralRoleService.findByIds(ids);
				json.setObj(dtos.size());
			}
		} catch (Exception e) {
			logger.error("删除系统角色前检测关联数据失败!", e);
		}
		return json;
	}

	// 角色是否重复
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Json checkModelName(String name) {
		Json json = new Json();
		long count = 0;
		try {
			name = DtoUtil.incode(name);
			count = this.genericService.count(RalRole.class,
					new Criteria().eq(RalRoleRM.name, name).eq(RalRoleRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND));
		} catch (Exception e) {
			logger.error("检查角色是否重复失败!", e);
		}
		json.setObj(count);
		return json;
	}
}
