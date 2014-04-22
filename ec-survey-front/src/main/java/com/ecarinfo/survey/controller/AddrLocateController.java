package com.ecarinfo.survey.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecarinfo.common.utils.CharsetUtil;
import com.ecarinfo.common.utils.JSONUtil;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.survey.po.CityInfo;
import com.ecarinfo.survey.po.MarkType;
import com.ecarinfo.survey.rm.MarkTypeRM;
/**
 * 地点定位搜索
 * @author ecxiaodx
 *
 */
@Controller
@RequestMapping(value = "locate")
public class AddrLocateController {
	private static final Logger logger = Logger.getLogger(AddrLocateController.class);
	@Resource
	private GenericService genericService;
	@RequestMapping(value="addrLocate" )
	public String index(@RequestParam(value="addr",required=false) String addr, Model model){
		//城市名是当前操作员所能操作的城市
		RalUser user = EcUtil.getCurrentUser();
		Integer cityId = user.getCityId();
		CityInfo city = genericService.findByPK(CityInfo.class, cityId);
		if(addr != null && addr.trim().length() > 0) {
			addr = CharsetUtil.decodeFromISO8859(addr);
			logger.debug("addr="+addr);
		} else {
//			addr = city.getName();
		}
		model.addAttribute("cityName", city.getName());
		model.addAttribute("addr", addr);
		List<MarkType> markTypes = genericService.findList(MarkType.class,new Criteria().eq(MarkTypeRM.orgId, user.getOrgId()));
		model.addAttribute("markTypes", JSONUtil.toJson(markTypes));
		return "locate/addrLocate";
	}

}