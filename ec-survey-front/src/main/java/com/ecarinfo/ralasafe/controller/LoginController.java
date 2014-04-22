package com.ecarinfo.ralasafe.controller;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecarinfo.ralasafe.base.RalBaseController;
import com.ecarinfo.ralasafe.dto.Constant;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.service.LoginService;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.MD5Util;
import com.ecarinfo.ralasafe.utils.SessionUtil;
import com.ecarinfo.survey.po.CityInfo;

/**
 * @Description: 登陆相关
 */

@Controller
@RequestMapping("ralasafe/login")
public class LoginController extends RalBaseController {

	@Resource
	private LoginService loginService;

	/**
	 * 返回登陆页
	 */
	@RequestMapping(value = "/loginIndex", method = RequestMethod.GET)
	public String redirect() {
		return "common/redirect";
	}

	/**
	 * 返回登陆页
	 */
	@RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
	public String unauthorized() {
		return "common/unauthorized";
	}

	/**
	 * 登陆页
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginIndex() {
		return "login";
	}

	/**
	 * 登陆验证
	 */
	@RequestMapping(value = "/userLogin", method = { RequestMethod.POST, RequestMethod.GET })
	public String loginIn(String loginName, String password, Model model) {
		if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
			return "login";
		} else {
			password = MD5Util.md5AndSha(password);
			boolean result = this.loginService.loginIn(loginName, password);
			if (result) {
				RalUser user = EcUtil.getCurrentUser();
				String currentUserLoginName = user.getLoginName();
				// 设置登陆成功的用户信息
				if (currentUserLoginName.equals("system")) {
					return Constant.REDIRECT + "/ralasafe/admin/";
				} else {
					if (user.getCityId() > 0) {
						CityInfo city = this.genericService.findByPK(CityInfo.class, user.getCityId());
						Session session = SessionUtil.getShiroSession();
						session.setAttribute("UserGrade", city.getGrade());
					}
					return Constant.REDIRECT + "/locate/carLocate";
				}
			} else {
				model.addAttribute("msg", "用户名或密码不正确！");
				return "login";
			}
		}
	}

	@RequestMapping(value = "/loginOut", method = RequestMethod.GET)
	public String loginOut() {
		if (loginService.loginOut()) {
			return Constant.REDIRECT + "/ralasafe/login/";
		}
		return null;
	}
}
