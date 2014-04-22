package com.ecarinfo.survey.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.common.utils.JSONUtil;
import com.ecarinfo.log.Action;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.ralasafe.po.RalGroup;
import com.ecarinfo.ralasafe.rm.RalGroupRM;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.survey.po.MarkInfo;
import com.ecarinfo.survey.rm.MarkInfoRM;
import com.ecarinfo.survey.service.MarkInfoService;
import com.ecarinfo.survey.view.MarkInfoView;
import com.ecarinfo.utils.EcExcelUtil;

@Controller
@RequestMapping("/markInfo")
public class MarkInfoController extends BaseController {

	private static final Logger logger = Logger.getLogger(MarkInfoController.class);

	@Resource
	private MarkInfoService markInfoService;
	@Resource
	private GenericService genericService;
	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String listPost(String typeName,String startTime, String endTime, Model model) {
		try {
			typeName = DtoUtil.incode(typeName);
			ECPage<MarkInfoView> ECPage = markInfoService.queryMarkInfoPages(typeName,startTime,endTime);
			model.addAttribute("ECPage", ECPage);
			return "manager/markinfo/list";
		} catch (Exception e) {
			logger.error("标注点列表加载失败", e);
		}
		return null;
	}
	
	
	/**
	 *显示标志点信息
	 * @param id 行车的id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/markInfoMapUI")
	public String markInfoMapUI(Integer id, Model model) {
		MarkInfo markInfo = genericService.findByPK(MarkInfo.class, id);
		model.addAttribute("checkMarkInfo",markInfo);
		List<MarkInfoView> listMarkInfo=markInfoService.queryMarkInfoList(null);
		model.addAttribute("listMarkInfo", JSONUtil.toJson(listMarkInfo));
		return "manager/markinfo/mark_info_map";
	}
	
	 
	// 删除(隐藏不可见)
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	@Action(description = "删除标注点信息", type = "标注点管理")
	public Json delete(Integer[] ids){
		Json json = new Json();
		try {
			if (ids != null ) {
				this.genericService.delete(MarkInfo.class, new Criteria().in(MarkInfoRM.pk, ids));
				json.setMsg("标注点删除成功!");
				List<MarkInfo> marks = this.genericService.findList(MarkInfo.class, new Criteria().in(MarkInfoRM.pk, ids));
				json.setObj(marks.get(0).getName());
			} else {
				json.setSuccess(false);
				json.setMsg("标注点删除失败!");
			}
		} catch (Exception e) {
			logger.error("删除失败!", e);
		}
		return json;
	}
	
	
	// 导出
	@RequestMapping(value = "/download", method = { RequestMethod.GET, RequestMethod.POST })
	public void download(String typeName,String startTime, String endTime,HttpServletResponse response, HttpServletRequest request) {
		try {
			typeName = DtoUtil.incode(typeName);
			String fileName = "标注信息.xls";
			String templateFileName = "markinfo.xls";
			List<MarkInfoView> dtos = markInfoService.queryMarkInfos(typeName, startTime, endTime);

			Map<String, String> datas = new LinkedHashMap<String, String>();
			datas.put("title", "标注信息" + "(" + dtos.size() + "条记录)");
			SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			datas.put("date", f.format(new Date()));
			datas.put("name", "深圳奥创科技有限公司");
			// 下载
			EcExcelUtil.excelDownload(fileName, templateFileName, datas, dtos, MarkInfoView.class, response, request);
		} catch (Exception e) {
			logger.error("标注信息下载失败", e);
		}
	}
}
