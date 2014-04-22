package com.ecarinfo.ralasafe.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.log.Action;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.ralasafe.base.RalBaseController;
import com.ecarinfo.ralasafe.dto.ZtreeDto;
import com.ecarinfo.ralasafe.po.RalGroup;
import com.ecarinfo.ralasafe.po.RalOrg;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.rm.RalOrgRM;
import com.ecarinfo.ralasafe.rm.RalUserRM;
import com.ecarinfo.ralasafe.service.RalOrgService;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.survey.po.CityInfo;
import com.ecarinfo.survey.rm.CityInfoRM;

/**
 * @Description: 机构管理
 */

@Controller
@RequestMapping("ralasafe/org")
public class OrgController extends RalBaseController {

	private static final Logger logger = Logger.getLogger(OrgController.class);

	@Resource
	private RalOrgService ralOrgService;

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Integer pid, Integer groupId, String name, String code, Integer disabled, Model model) {
		List<RalOrg> dtos = getOrgs(pid, groupId, name, code, disabled);
		model.addAttribute("dtos", dtos);
		return "ralasafe/org/list";
	}

	private List<RalOrg> getOrgs(Integer pid, Integer groupId, String name, String code, Integer disabled) {
		try {
			name = DtoUtil.incode(name);
			code = DtoUtil.incode(code);
			if (pid == null) {
				pid = 0;
			}
			List<RalOrg> dtos = this.ralOrgService.queryOrgs(pid, groupId, name, code, disabled);
			return dtos;
		} catch (Exception e) {
			logger.error("机构列表加载失败", e);
		}
		return null;
	}

	// 表格树
	@RequestMapping(value = "/treeTable", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<RalOrg> treeTable(Integer pid, Integer groupId, String name, String code, Integer disabled) {
		List<RalOrg> dtos = getOrgs(pid, groupId, name, code, disabled);
		return dtos;
	}

	/**
	 * 获取机构树(showUrl:表示是否显示链接)
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	@ResponseBody
	public List<ZtreeDto> getZtree(Boolean showUrl, Integer id, Integer groupId, HttpServletRequest request) {
		List<ZtreeDto> zTree = new ArrayList<ZtreeDto>();
		try {
			Integer pid = (id != null && id > 0) ? id : 0;

			Criteria whereBy = new Criteria();
			whereBy.eq(RalOrgRM.disabled, 1).eq(RalOrgRM.parentId, pid, CondtionSeparator.AND);
			if (groupId != null && groupId > 0) {
				whereBy.eq(RalOrgRM.groupId, groupId, CondtionSeparator.AND);
			}
			List<RalOrg> dtos = this.genericService.findList(RalOrg.class, whereBy);

			String webpath = request.getContextPath();
			String target = "org_rightFrame";
			String url = webpath + "/org/updateUI?id=";
			for (RalOrg dto : dtos) {
				ZtreeDto tree = new ZtreeDto();
				tree.setId(dto.getOrgId());
				tree.setName(dto.getName());
				if (dto.getIsLeaf() > 0) {
					tree.setIsParent(true);
					tree.setOpen(true);
				}
				if (showUrl) {
					tree.setUrl(url + dto.getOrgId());
					tree.setTarget(target);
				}
				tree.setpId(dto.getParentId());
				zTree.add(tree);
			}
		} catch (Exception e) {
			logger.error("机构信息获取失败!", e);
		}
		return zTree;
	}

	// 寻找要修改的
	@RequestMapping(value = "/updateUI", method = RequestMethod.GET)
	public String edit(Integer id, ModelMap model) {
		RalOrg dto = this.genericService.findByPK(RalOrg.class, id);
		Integer pid = dto.getParentId();
		Integer grougId = dto.getGroupId();
		if (pid != null && pid > 0) {
			RalOrg pDto = this.genericService.findByPK(RalOrg.class, pid);
			dto.setParentName(pDto.getName());
		}

		if (grougId != null && grougId > 0) {
			RalGroup group = this.genericService.findByPK(RalGroup.class, grougId);
			dto.setGroupName(group.getName());
		}

		if (dto != null && dto.getCityId() > 0) {
			CityInfo city = this.genericService.findByPK(CityInfo.class, dto.getCityId());
			model.put("cityName", city.getName());
		}

		List<CityInfo> ABCDE = this.genericService.findList(CityInfo.class,
				new Criteria().in(CityInfoRM.pinyin, new String[] { "A", "B", "C", "D", "E" }).orderBy(CityInfoRM.pinyin, OrderType.ASC));
		model.put("ABCDE", ABCDE);

		List<CityInfo> FGHIJ = this.genericService.findList(CityInfo.class,
				new Criteria().in(CityInfoRM.pinyin, new String[] { "F", "G", "H", "I", "J" }).orderBy(CityInfoRM.pinyin, OrderType.ASC));
		model.put("FGHIJ", FGHIJ);

		List<CityInfo> KLMNO = this.genericService.findList(CityInfo.class,
				new Criteria().in(CityInfoRM.pinyin, new String[] { "K", "L", "M", "N", "O" }).orderBy(CityInfoRM.pinyin, OrderType.ASC));
		model.put("KLMNO", KLMNO);

		List<CityInfo> PQRST = this.genericService.findList(CityInfo.class,
				new Criteria().in(CityInfoRM.pinyin, new String[] { "P", "Q", "R", "S", "T" }).orderBy(CityInfoRM.pinyin, OrderType.ASC));
		model.put("PQRST", PQRST);

		List<CityInfo> UVWXYZ = this.genericService.findList(CityInfo.class,
				new Criteria().in(CityInfoRM.pinyin, new String[] { "U", "V", "W", "X", "Y", "Z" }).orderBy(CityInfoRM.pinyin, OrderType.ASC));
		model.put("UVWXYZ", UVWXYZ);

		model.put("dto", dto);
		return "ralasafe/org/add_update";
	}

	// 修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody RalOrg dto) {
		Json json = new Json();
		try {
			this.ralOrgService.update(dto);
			json.setMsg("信息修改成功!");
		} catch (Exception e) {
			logger.error("机构信息修改失败!", e);
		}
		return json;
	}

	// 添加UI
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(ModelMap model) {
		List<CityInfo> ABCDE = this.genericService.findList(CityInfo.class,
				new Criteria().in(CityInfoRM.pinyin, new String[] { "A", "B", "C", "D", "E" }).orderBy(CityInfoRM.pinyin, OrderType.ASC));
		model.put("ABCDE", ABCDE);

		List<CityInfo> FGHIJ = this.genericService.findList(CityInfo.class,
				new Criteria().in(CityInfoRM.pinyin, new String[] { "F", "G", "H", "I", "J" }).orderBy(CityInfoRM.pinyin, OrderType.ASC));
		model.put("FGHIJ", FGHIJ);

		List<CityInfo> KLMNO = this.genericService.findList(CityInfo.class,
				new Criteria().in(CityInfoRM.pinyin, new String[] { "K", "L", "M", "N", "O" }).orderBy(CityInfoRM.pinyin, OrderType.ASC));
		model.put("KLMNO", KLMNO);

		List<CityInfo> PQRST = this.genericService.findList(CityInfo.class,
				new Criteria().in(CityInfoRM.pinyin, new String[] { "P", "Q", "R", "S", "T" }).orderBy(CityInfoRM.pinyin, OrderType.ASC));
		model.put("PQRST", PQRST);

		List<CityInfo> UVWXYZ = this.genericService.findList(CityInfo.class,
				new Criteria().in(CityInfoRM.pinyin, new String[] { "U", "V", "W", "X", "Y", "Z" }).orderBy(CityInfoRM.pinyin, OrderType.ASC));
		model.put("UVWXYZ", UVWXYZ);
		return "ralasafe/org/add_update";
	}

	// 选择机构界面
	@RequestMapping(value = "/selectOrg", method = RequestMethod.GET)
	public String selectDep() {
		return "ralasafe/org/select_org";
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
				whereBy.eq(RalOrgRM.name, name);
			}
			if (StringUtils.isNotEmpty(code)) {
				whereBy.eq(RalOrgRM.code, code);
			}
			whereBy.eq(RalOrgRM.disabled, 1, CondtionSeparator.AND);
			count = this.genericService.count(RalOrg.class, whereBy);
		} catch (Exception e) {
			logger.error("检查是否存在失败!", e);
		}
		json.setObj(count);
		return json;
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加服务机构", type = "服务机构管理")
	public Json add(@RequestBody RalOrg dto) {
		Json json = new Json();
		try {
			if (dto.getParentId() == null) {
				dto.setParentId(0);
			}
			this.ralOrgService.save(dto);
			json.setMsg("服务机构添加成功!");
			json.setObj(dto.getName());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("服务机构添加失败!");
			logger.error("服务机构添加失败!", e);
		}
		return json;
	}

	// 删除(隐藏不可见)
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	@Action(description = "删除服务机构", type = "服务机构管理")
	public Json delete(Integer[] ids) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				this.ralOrgService.delete(ids[0]);

				List<RalOrg> dtos = this.genericService.findList(RalOrg.class, new Criteria().in(RalOrgRM.pk, ids));
				json.setObj(dtos.get(0).getName());
			} else {
				json.setSuccess(false);
				json.setMsg("服务机构删除失败!");
			}
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("服务机构删除失败!");
			logger.error("服务机构删除失败!", e);
		}
		return json;
	}

	// 删除服务机构前前检测服务机构下是否有用户数据失败!
	@RequestMapping(value = "/delCheck", method = RequestMethod.GET)
	@ResponseBody
	public Json delCheck(Integer ids) {
		Json json = new Json();
		try {
			if (ids != null) {
				List<RalUser> dtos = this.genericService.findByAttr(RalUser.class, RalUserRM.orgId, ids);
				json.setObj(dtos.size());
			}
		} catch (Exception e) {
			logger.error("删除服务机构前检测机构下是否有用户数据失败!", e);
		}
		return json;
	}
}
