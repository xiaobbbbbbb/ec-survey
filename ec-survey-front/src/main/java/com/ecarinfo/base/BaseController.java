package com.ecarinfo.base;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecarinfo.persist.service.GenericService;

/**
 * 基础控制器，其他控制器需extends此控制器获得initBinder自动转换的功能
 */

@Controller
@RequestMapping("/base")
public class BaseController {

	private static final Logger logger = Logger.getLogger(BaseController.class);

	@Resource
	protected GenericService genericService;

	// 重定向
	public static String REDIRECT = "redirect:";

	// 请求转发
	public static String FORWARD = "forward:";

	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 内容输出
	 */
	public static void printAjax(HttpServletResponse response, String content) {
		try {
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			logger.error("printAjax内容输出异常");
		}
	}

}
