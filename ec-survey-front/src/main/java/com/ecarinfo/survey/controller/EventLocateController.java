package com.ecarinfo.survey.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.common.utils.CharsetUtil;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.common.utils.JSONUtil;
import com.ecarinfo.log.Action;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.survey.dto.AreaTreeDto;
import com.ecarinfo.survey.dto.EventInfoDto;
import com.ecarinfo.survey.dto.ResultDto;
import com.ecarinfo.survey.po.AreaInfo;
import com.ecarinfo.survey.po.CarInfo;
import com.ecarinfo.survey.po.CityInfo;
import com.ecarinfo.survey.po.EventInfo;
import com.ecarinfo.survey.po.MarkType;
import com.ecarinfo.survey.rm.AreaInfoRM;
import com.ecarinfo.survey.rm.CarInfoRM;
import com.ecarinfo.survey.rm.EventInfoRM;
import com.ecarinfo.survey.rm.MarkTypeRM;
import com.ecarinfo.survey.service.EventInfoService;
import com.ecarinfo.survey.view.TablePrefix;
/**
 * 报案车定位
 * @author ecxiaodx
 *
 */
@Controller
@RequestMapping(value = "locate")
public class EventLocateController {
	private static final Logger logger = Logger.getLogger(EventLocateController.class);
	@Resource
	private GenericService genericService;
	
	@Resource
	private EventInfoService eventInfoService;
	
	@RequestMapping(value = "eventLocate")
	public String eventLocate(
			@RequestParam(value="areaId",required=false) Integer areaId,
			@RequestParam(value="eventInfoId",required=false) Integer eventInfoId,
			@RequestParam(value="markInfoId",defaultValue="0", required=false) Long markInfoId,
			Model model) {
		List<AreaInfo> areas = null;
		//查找菜单
		RalUser user = EcUtil.getCurrentUser();
		if(areaId != null) {
			AreaInfo area = genericService.findOne(AreaInfo.class, new Criteria()
			.eq(AreaInfoRM.id,areaId)
			.eq(AreaInfoRM.orgId,user.getOrgId())
			);
			if(area  != null) {
				areas = new ArrayList<AreaInfo>();
				areas.add(area);
			}
		} else {
			//(TODO 后续改成权限相关)
			areas = genericService.findList(AreaInfo.class,new Criteria()
			.eq(AreaInfoRM.orgId,user.getOrgId())
			.eq(AreaInfoRM.disabled, 1));
		}
		
		List<MarkType> markTypes = genericService.findList(MarkType.class,new Criteria()
		.eq(MarkTypeRM.orgId, user.getOrgId()));
		model.addAttribute("markTypes", JSONUtil.toJson(markTypes));
		
		if(areas.size() == 0) {
			model.addAttribute("treeStr","{}");
			CityInfo city = getOptCity();
			if(city != null) {
				model.addAttribute("cityName", city.getName());
			}
			return "locate/orbit";
		}
		Integer areaIds[] = new Integer[areas.size()];
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < areas.size(); i++) {
			AreaInfo area = areas.get(i);
			areaIds[i] = area.getId();
			buffer.append(area.getId()).append(",");
		}
		if(buffer.length() > 0) {
			buffer.deleteCharAt(buffer.length() - 1);
		}
		
		//1小时内
		String withinOneHour = DateUtils.plusHours(new Date(), -1,TimeFormatter.FORMATTER1);
		
		//所有未调度事故点
		List<EventInfo> markeds1 = genericService.findList(EventInfo.class, new Criteria().eq(EventInfoRM.orgId, user.getOrgId()).andCriteria(new Criteria().eq( EventInfoRM.status, "0")));
				
		
		List<EventInfo> markeds2 = genericService.findList(EventInfo.class, new Criteria().eq(EventInfoRM.orgId, user.getOrgId()).andCriteria(new Criteria().eq( EventInfoRM.status, "1")
				.eq(EventInfoRM.status, "2", CondtionSeparator.OR).eq(EventInfoRM.status, "3", CondtionSeparator.OR).eq(EventInfoRM.status, "4", CondtionSeparator.OR)));
		List<EventInfo> markeds3 = genericService.findList(EventInfo.class, new Criteria().eq(EventInfoRM.orgId, user.getOrgId()).andCriteria(new Criteria().eq( EventInfoRM.status, "1")));
		List<AreaTreeDto> tree = new ArrayList<AreaTreeDto>();
		getTree(tree,markeds1,1);
		model.addAttribute("tree",getTree(tree,markeds3,2));
		
		//所有未标记点
		//30分钟内
//		String within30Mins = DateUtils.plusMins(new Date(), -30,TimeFormatter.FORMATTER1);
//		List<EventInfo> unMarkeds = genericService.findList(EventInfo.class, new Criteria()
//		.eq(EventInfoRM.orgId, user.getOrgId())
//		.eq(EventInfoRM.status, -1)
//		.moreThen(EventInfoRM.createTime, within30Mins)
//		);
//		
//		model.addAttribute("tree", getTree(tree,unMarkeds,2));
		
		List<EventInfo> allInfo = new ArrayList<EventInfo>();
		if(markeds1.size() > 0) {
			allInfo.addAll(markeds1);
		}
		if(markeds2.size() > 0) {
			allInfo.addAll(markeds2);
		}
//		if(unMarkeds.size() > 0) {
//			allInfo.addAll(unMarkeds);
//		}
		
		
		//将地点定位搜索的标记放置到首位
		for(int i=0;i<allInfo.size();i++) {
			EventInfo m = allInfo.get(i);
			if(m.getId().equals(markInfoId)) {
				allInfo.remove(i);
				allInfo.add(0,m);
				break;
			}
		}
		
		Long []markIds = new Long[allInfo.size()];
		for(int i=0;i<allInfo.size();i++) {
			markIds[i] = allInfo.get(i).getId();
		}
		
		List<EventInfoDto> dtoList = new ArrayList<EventInfoDto>();
		for(EventInfo m:allInfo) {
			EventInfoDto dto = new EventInfoDto();
			dto.init(m);
			dtoList.add(dto);
		}
		model.addAttribute("eventInfos", JSONUtil.toJson(dtoList));
		
		if(dtoList.size() == 0) {
			CityInfo city = getOptCity();
			if(city != null) {
				model.addAttribute("cityName", city.getName());
			}
		}
		
		
		
		model.addAttribute("eventInfoId", eventInfoId);
		return "locate/eventLocate";
	}
	
	/**
	 * 获取默认用户所在的地址
	 * @return
	 */
	private CityInfo getOptCity() {
		RalUser user = EcUtil.getCurrentUser();
		Integer cityId = user.getCityId();
		if(cityId == null) {
			//操作员没有城市信息
			logger.error("operation ["+user.getLoginName()+"] has no city info ");
			return null;
		}
		CityInfo city = genericService.findByPK(CityInfo.class, cityId);
		return city;
	}
	
	private String getTree(List<AreaTreeDto> tree,List<EventInfo> list,int pid) {
		if(pid == 1) {
			AreaTreeDto dto1 = new AreaTreeDto();
			dto1.setId(1);
			dto1.setpId(0);
			dto1.setName("未调度事故点");
			dto1.setOpen("true");
			dto1.setIsParent(true);
			tree.add(dto1);
//			AreaTreeDto dto2 = new AreaTreeDto();
//			dto2.setId(2);
//			dto2.setpId(1);
//			dto2.setName("1小时内报案地点");
//			dto2.setOpen("true");
//			dto2.setIsParent(true);
//			tree.add(dto2);
		}
		if(pid == 2) {
			AreaTreeDto dto4 = new AreaTreeDto();
			dto4.setId(2);
			dto4.setpId(0);
			dto4.setName("已调度事故点");
			dto4.setOpen("true");
			dto4.setIsParent(true);
			tree.add(dto4);
		}
		for(EventInfo event:list) {
			AreaTreeDto dto = new AreaTreeDto();
			dto.init(event, pid);
			tree.add(dto);
		}
		return JSONUtil.toJson(tree);
	}
	
	
	/**
	 * //标定、取消事故点(生成案件)
	 * @param markInfoId
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "tagPoint")
	@ResponseBody
	@Action(description = "标定、取消事故点" ,type = "案件定位信息")
	public ResultDto tagPoint(
			@RequestParam(value="type") int type,
			@RequestParam(value="addr") String address,
			@RequestParam(value="carNo") String carNo,
			@RequestParam(value="phoneNo") String phoneNo,
			@RequestParam(value="lat") double latitude,
			@RequestParam(value="lng") double longitude,
			@RequestParam(value="name") String name,
			@RequestParam(value="eventDesc") String eventDesc
			) {
		try {
			RalUser user = EcUtil.getCurrentUser();
			return eventInfoService.tagPoint(type,user.getOrgId(),CharsetUtil.decode(carNo),CharsetUtil.decode(phoneNo), CharsetUtil.decode(address), latitude, longitude, CharsetUtil.decode(name),CharsetUtil.decode(eventDesc));
			
		} catch (Exception e) {
			logger.error("",e);
			return new ResultDto(500,"案件标定失败");
		}
	}
	
	/**
	 * 更新事故点位置
	 * @param eventInfoId
	 * @param address
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	@RequestMapping(value = "updatePosition")
	@ResponseBody
	@Action(description = "更新位置" ,type = "案件定位信息")
	public ResultDto updatePosition(
			@RequestParam(value="eventInfoId") Long eventInfoId,
			@RequestParam(value="address") String address,
			@RequestParam(value="lat") double latitude,
			@RequestParam(value="lng") double longitude
			) {
		try {
			EventInfo info = genericService.findByPK(EventInfo.class, eventInfoId);
			if(info != null) {
				info.setLatitude(latitude);
				info.setLongitude(longitude);
				info.setAddress(address);
				genericService.update(info);
				ResultDto dto = new ResultDto(200,"位置修改成功",info.getName());
				dto.setResult(info);
				EventInfoDto mdto = new EventInfoDto();
				mdto.init(info);
				dto.setResult(mdto);
				return dto;
			} else {
				return new ResultDto(201,"找不到记录，eventInfoId="+eventInfoId);
			}
		} catch (Exception e) {
			logger.error("",e);
			return new ResultDto(500,"位置修改失败");
		}
	}
			
	/**
	 * 调度救援
	 * @param markInfoId
	 * @param carNo
	 * @param phoneNo
	 * @param surveyUid
	 * @return
	 */
	@RequestMapping(value = "support")
	@ResponseBody
	@Action(description = "调度救援" ,type= "调度信息")
	public ResultDto support(
			@RequestParam(value="eventInfoId") Long eventInfoId,
			@RequestParam(value="carId") Integer carId,
			@RequestParam(value="surveyTel") String phoneNo,
			@RequestParam(value="surveyId") String surveyUid,
			@RequestParam(value="carlat") double latitude,
			@RequestParam(value="carlng") double longitude
			) {
		try {
			Date now = new Date();
			EventInfo info = genericService.findByPK(EventInfo.class, eventInfoId);
			if(info != null ) {
				if(info.getSurveyUid() != null) {
					return new ResultDto(500,"调度失败，此案件已经调度过，请勿重复调度。");
				}
				CarInfo carInfo = genericService.findByPK(CarInfo.class, carId);
				if(carInfo == null) {
					logger.error("操作失败，找不到查勘车辆。carId="+carId);
					return new ResultDto(500,"调度失败，找不到查勘车辆。");
				}
				carInfo.setStatus(1);
				genericService.updateWithCriteria(CarInfo.class, new Criteria()
				.update(CarInfoRM.status, carInfo.getStatus())
				.eq(CarInfoRM.id, carId));//标记为工作中
				
				info.setSurveyCarId(carId);
				info.setProcessTime(new Date());
				RalUser user = EcUtil.getCurrentUser();
				info.setOpUid(user.getUserId());
				info.setGeohash(info.getLongitude()+" "+info.getLatitude());
				info.setAreaId(carInfo.getAreaId());
				if(phoneNo!=null&&!"".equals(phoneNo)&&!"null".equals(phoneNo)){
					info.setPhoneNo(phoneNo);
				}
				info.setCarLatitude(latitude);
				info.setCarLongitude(longitude);
				info.setStatus(1);
				if(surveyUid!=null&&!"".equals(surveyUid)&&!"null".equals(surveyUid)){
					info.setSurveyUid(surveyUid);
				}
				info.setUpdateTime(now);
				genericService.update(info);
			} else {
				return new ResultDto(500,"调度失败，找不到案件.");
			}
			
			ResultDto dto = new ResultDto(200,"调度成功",info.getName());
			dto.setResult(info);
			
			List<EventInfo> eventInfos = genericService.findList(EventInfo.class, new Criteria()
			.eq(EventInfoRM.surveyCarId, carId)
			.eq(EventInfoRM.status, 1)
			);
			dto.setExtendsObject(eventInfos);
			return dto;
		} catch (Exception e) {
			logger.error("",e);
			return new ResultDto(500,"调度失败");
		}
	}
	
	/**
	 * 更新案件状态
	 * @param eventInfoId
	 * @param status 0待调度；1已调度；2已完成；3手动终结；4超时终结
	 * @return
	 */
	@RequestMapping(value = "modifyEventInfo")
	@ResponseBody
	@Action(description = "修改案件信息", type = "案件信息")
	public ResultDto modifyEventInfo(
			@RequestParam(value="eventInfoId") Long eventInfoId,
			@RequestParam(value="status") Integer status,
			@RequestParam(value="cancelreason",required=false) String cancelreason,
			@RequestParam(value="cancelpeople",required=false) String cancelpeople
			) {
		try {
			Date now = new Date();
			EventInfo info = genericService.findByPK(EventInfo.class, eventInfoId);
			if(info != null ) {
				info.setStatus(status);
				info.setUpdateTime(now);
				RalUser user = EcUtil.getCurrentUser();
				info.setOpUid(user.getUserId());
				genericService.updateWithCriteria(EventInfo.class, new Criteria()
				.update(EventInfoRM.status, status)
				.update(EventInfoRM.cancelpeople, cancelpeople)
				.update(EventInfoRM.cancelreason, cancelreason)
				.update(EventInfoRM.opUid, user.getUserId())
				.update(EventInfoRM.updateTime, DateUtils.dateToString(now, TimeFormatter.FORMATTER1))
				.eq(EventInfoRM.id, info.getId()));
				
				Integer carId = info.getSurveyCarId();
				if(carId != null) {
					Long count = genericService.count(EventInfo.class, new Criteria()
					.eq(EventInfoRM.surveyCarId, carId)
					.eq(EventInfoRM.status, 1));
					if(count != null && count == 0) {
						genericService.updateWithCriteria(CarInfo.class, new Criteria()
						.update(CarInfoRM.status, 0)
						.eq(CarInfoRM.id, carId));//没有案件时，待命中
					}
				}
			} else {
				return new ResultDto(500,"操作失败，找不到案件.");
			}
			
			ResultDto dto = new ResultDto(200,"修改成功",info.getName());
			dto.setResult(info);
			return dto;
		} catch (Exception e) {
			logger.error("",e);
			return new ResultDto(500,"保存失败");
		}
	}
	
}