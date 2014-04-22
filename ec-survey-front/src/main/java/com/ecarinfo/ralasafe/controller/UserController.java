package com.ecarinfo.ralasafe.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.base.RalBaseController;
import com.ecarinfo.ralasafe.po.RalOrg;
import com.ecarinfo.ralasafe.po.RalRole;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.rm.RalOrgRM;
import com.ecarinfo.ralasafe.rm.RalUserRM;
import com.ecarinfo.ralasafe.service.RalUserService;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.ralasafe.utils.MD5Util;

/**
 * @Description: 用户管理
 */

@Controller
@RequestMapping("ralasafe/user")
public class UserController extends RalBaseController {

	private static final Logger logger = Logger.getLogger(UserController.class);

	@Resource
	private RalUserService ralUserService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(String startTime, String endTime, String name, String loginName, Integer orgId, Integer disabled, Model model) {
		try {
			name = DtoUtil.incode(name);
			loginName = DtoUtil.incode(loginName);
			RalUser currentUser = EcUtil.getCurrentUser();
			String currentUserLoginName = currentUser.getLoginName();
			if (!currentUserLoginName.equals("system")) {
				orgId = currentUser.getOrgId();
			}
			ECPage<RalUser> ECPage = ralUserService.queryPage(startTime, endTime, name, loginName, orgId, disabled);
			model.addAttribute("ECPage", ECPage);
			return "ralasafe/user/list";
		} catch (Exception e) {
			logger.error("用户列表加载失败", e);
		}
		return null;
	}

	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(ModelMap model) {
		List<RalRole> listRole = this.genericService.findList(RalRole.class, new Criteria().eq(RalOrgRM.orgId, EcUtil.getCurrentUser().getOrgId()));
		model.addAttribute("listRole", listRole);
		return "ralasafe/user/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加操作员", type = "操作员管理")
	public Json add(@RequestBody RalUser dto) {
		Json json = new Json();
		try {
			dto.setPassword(MD5Util.md5AndSha(dto.getPassword()));
			dto.setCreateTime(new Date());
			dto.setUpdateTime(new Date());
			dto.setIsManager(1);
			RalUser currentUser = EcUtil.getCurrentUser();
			String currentUserLoginName = currentUser.getLoginName();
			if (!currentUserLoginName.equals("system")) {
				dto.setOrgId(currentUser.getOrgId());
				dto.setCityId(currentUser.getCityId());
			} else {
				if (dto.getOrgId() != null) {
					RalOrg org = this.genericService.findByPK(RalOrg.class, dto.getOrgId());
					dto.setOrgId(dto.getOrgId());
					dto.setCityId(org.getCityId());
				}
			}
			this.ralUserService.save(dto);
			json.setMsg("用户添加成功!");
			json.setObj(dto.getName());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("用户添加失败!");
			logger.error("用户添加失败!", e);
		}
		return json;
	}

	// 寻找要修改的
	@RequestMapping(value = "/updateUI", method = RequestMethod.GET)
	public String edit(Integer id, Model model) {
		RalUser dto = this.ralUserService.findById(id);
		model.addAttribute("dto", dto);

		if (dto != null && dto.getOrgId() > 0) {
			RalOrg org = this.genericService.findByPK(RalOrg.class, dto.getOrgId());
			model.addAttribute("orgName", org.getName());
		}

		List<RalRole> listRole = this.genericService.findList(RalRole.class, new Criteria().eq(RalOrgRM.orgId, EcUtil.getCurrentUser().getOrgId()));
		model.addAttribute("listRole", listRole);
		return "ralasafe/user/add_update";
	}

	// 修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody RalUser dto) {
		Json json = new Json();
		try {
			dto.setUpdateTime(new Date());
			if (!dto.getPassword().equals(dto.getOldPassword())) {
				dto.setPassword(MD5Util.md5AndSha(dto.getPassword()));
			}
			RalUser currentUser = EcUtil.getCurrentUser();
			String currentUserLoginName = currentUser.getLoginName();
			if (!currentUserLoginName.equals("system")) {
				dto.setOrgId(currentUser.getOrgId());
				dto.setCityId(currentUser.getCityId());
			} else {
				if (dto.getOrgId() != null) {
					RalOrg org = this.genericService.findByPK(RalOrg.class, dto.getOrgId());
					dto.setOrgId(dto.getOrgId());
					dto.setCityId(org.getCityId());
				}
			}
			this.ralUserService.update(dto);
			json.setMsg("用户信息修改成功!");
		} catch (Exception e) {
			logger.error("用户信息修改失败!", e);
		}
		return json;
	}

	// 删除
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "删除操作员", type = "操作员管理")
	public Json delete(Integer[] ids) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				this.ralUserService.delete(ids);
				json.setMsg("用户删除成功!");

				List<RalUser> dtos = this.genericService.findList(RalUser.class, new Criteria().in(RalUserRM.pk, ids));
				json.setObj(dtos.get(0).getName());
			} else {
				json.setSuccess(false);
				json.setMsg("用户删除失败!");
			}
		} catch (Exception e) {
			logger.error("用户删除失败!", e);
		}
		return json;
	}

	// 登录名是否重复
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Json checkName(String loginName) {
		Json json = new Json();
		long count = 0;
		try {
			loginName = DtoUtil.incode(loginName);
			count = this.genericService.count(RalUser.class,
					new Criteria().eq(RalUserRM.loginName, loginName).eq(RalUserRM.disabled, 1, CondtionSeparator.AND));
		} catch (Exception e) {
			logger.error("检查登录名是否存在失败!", e);
		}
		json.setObj(count);
		return json;
	}

	@RequestMapping(value = "/searchUI", method = RequestMethod.GET)
	public String searchUI() {
		return "ralasafe/user/search";
	}

	// 修改密码界面
	@RequestMapping(value = "/updatePwdUI", method = RequestMethod.GET)
	public String updatePawUI(Model model) {
		RalUser user = EcUtil.getCurrentUser();
		model.addAttribute("user", user);
		return "ralasafe/user/update";
	}

	// 修改密码
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	@ResponseBody
	public Json updatePwd(@RequestBody RalUser dto) {
		Json json = new Json();
		try {
			RalUser user = EcUtil.getCurrentUser();
			// 是否修改用户密码
			if (!dto.getPassword().equals(dto.getOldPassword())) {
				this.genericService.updateWithCriteria(RalUser.class, new Criteria().update(RalUserRM.password, MD5Util.md5AndSha(dto.getPassword()))
						.eq(RalUserRM.userId, user.getUserId()));
			}
			json.setMsg("管理员密码修改成功!");
		} catch (Exception e) {
			json.setSuccess(false);
			logger.error("管理员密码修改失败!", e);
			json.setMsg("管理员密码修改失败!");
		}
		return json;
	}

	// 选择用户
	@RequestMapping(value = "/selectUser", method = RequestMethod.GET)
	public String selectUser(Model model) {
		Criteria whereBy = new Criteria();
		whereBy.eq(RalUserRM.disabled, 1);
		whereBy.orderBy(RalUserRM.level, OrderType.ASC);
		List<RalUser> dtos = super.genericService.findList(RalUser.class, whereBy);
		model.addAttribute("dtos", dtos);
		return "ralasafe/user/select_user";
	}
}
