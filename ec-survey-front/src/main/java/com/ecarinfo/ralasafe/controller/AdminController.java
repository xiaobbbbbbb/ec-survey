package com.ecarinfo.ralasafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecarinfo.base.BaseController;

/**
 * @Description: 后台主要页面的跳转
 * @Author: 邓支晓
 * @Date: 2013-1-14
 * @Version: V1.0
 */

@Controller
@RequestMapping("ralasafe/admin")
public class AdminController extends BaseController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String mianUI() {
		return "admin/main";
	}
}
