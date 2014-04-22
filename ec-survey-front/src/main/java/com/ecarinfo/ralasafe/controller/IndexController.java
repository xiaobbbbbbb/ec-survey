package com.ecarinfo.ralasafe.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecarinfo.ralasafe.base.RalBaseController;
import com.ecarinfo.ralasafe.dto.Constant;

/**
 * @Description: 项目主要页面的跳转(以/index开头的url表示,用户登录后不需要权限来拦截)
 */

@Controller
@RequestMapping("ec")
public class IndexController extends RalBaseController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String ec() {
		return "admin/index/ec";
	}

	@RequestMapping(value = "/framesetUI", method = RequestMethod.GET)
	public String framesetUI() {
		Subject currentUser = SecurityUtils.getSubject();
		String currentUserLoginName = currentUser.getPrincipal().toString();
		if (currentUserLoginName.equals("system")) {
			return "admin/index/frameset/";
		} else {
			return Constant.REDIRECT + "/locate/carLocate";
		}
	}
}
