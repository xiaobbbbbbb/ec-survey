package com.ecarinfo.survey.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.survey.dao.AreaInfoDao;
import com.ecarinfo.survey.dao.EventInfoDao;
import com.ecarinfo.survey.dao.MarkInfoDao;
import com.ecarinfo.survey.dto.AreaTreeDto;
import com.ecarinfo.survey.dto.CarLocateDto;
import com.ecarinfo.survey.dto.MarkInfoDto;
import com.ecarinfo.survey.dto.ResultDto;
import com.ecarinfo.survey.dto.SurveyUserDto;
import com.ecarinfo.survey.po.AreaInfo;
import com.ecarinfo.survey.po.CarInfo;
import com.ecarinfo.survey.po.CityInfo;
import com.ecarinfo.survey.po.DeviceData;
import com.ecarinfo.survey.po.DeviceInfo;
import com.ecarinfo.survey.po.EventInfo;
import com.ecarinfo.survey.po.MarkInfo;
import com.ecarinfo.survey.po.MarkType;
import com.ecarinfo.survey.po.SurveyUserInfo;
import com.ecarinfo.survey.po.TagInfo;
import com.ecarinfo.survey.rm.AreaInfoRM;
import com.ecarinfo.survey.rm.DeviceDataRM;
import com.ecarinfo.survey.rm.EventInfoRM;
import com.ecarinfo.survey.rm.MarkTypeRM;
import com.ecarinfo.survey.rm.SurveyUserInfoRM;
import com.ecarinfo.survey.rm.TagInfoRM;
import com.ecarinfo.survey.service.CarInfoService;
/**
 * 查勘车定位
 * @author ecxiaodx
 *
 */
@Controller
@RequestMapping(value = "locate")
public class CarLocateController {
	private static final Logger logger = Logger.getLogger(CarLocateController.class);
	@Resource
	private GenericService genericService;
	@Resource
	private AreaInfoDao areaInfoDao;
	
	@Resource
	private CarInfoService carInfoService;
	
	@Resource
	private MarkInfoDao markInfoDao;
	
	@Resource
	private EventInfoDao eventInfoDao;
	
	@RequestMapping(value="carLocate" )
	public String carLocate(@RequestParam(value="keyWord",required=false) String keyWord,Model model){
		List<AreaInfo> areas = null;
		//当前用户
		RalUser user = EcUtil.getCurrentUser();
		//查找菜单
		if((keyWord != null && keyWord.trim().length() > 0) && !"_".equals(keyWord)) {
			keyWord = CharsetUtil.decodeFromISO8859(keyWord);
			logger.debug("keyWord="+keyWord);
			areas = areaInfoDao.findAreaByKeyWord("%"+keyWord+"%",user.getOrgId());
			model.addAttribute("keyWord", keyWord);
		} else {
			//(TODO 后续改成权限相关)
			areas = genericService.findList(AreaInfo.class,new Criteria()
			.eq(MarkTypeRM.orgId, user.getOrgId())
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
			return "locate/carLocate";
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
		
		// 根据区域查找车辆及轨迹信息
		List<CarInfo> carInfoList = carInfoService.findValidCarInfoByArea(buffer.toString());
		// genericService.findList(CarInfo.class, new Criteria().in(CarInfoRM.areaId, areaIds));
		if(carInfoList.size() == 0) { //根据地区找不到车
			model.addAttribute("treeStr","{}");
			CityInfo city = getOptCity();
			if(city != null) {
				model.addAttribute("cityName", city.getName());
			}
			return "locate/carLocate";
		}
		
		
		String jsonTree = getTreeStr(areas, carInfoList);
		model.addAttribute("treeStr", jsonTree);
		model.addAttribute("areaIds", buffer.toString());
		
		return "locate/carLocate";
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

	private String getTreeStr(List<AreaInfo> areas, List<CarInfo> carInfoList) {
		List<AreaTreeDto> dtos = new ArrayList<AreaTreeDto>();
		for (AreaInfo area : areas) {
			AreaTreeDto dto = new AreaTreeDto();
			dto.init(area);
			dtos.add(dto);
		}
		for (CarInfo c : carInfoList) {
			AreaTreeDto dto = new AreaTreeDto();
			dto.init(c);
			dtos.add(dto);
		}
		return JSONUtil.toJson(dtos);
	}
	
	@RequestMapping(value="getCarLocateDetail" )
	@ResponseBody
	public Object getCarLocateDetail(String online,Model model,@RequestParam(value="ids",required=false) Integer ... areaId){
		Map<Integer,AreaInfo> areaMap = new HashMap<Integer,AreaInfo>();
		List<CarLocateDto> carLocates = new ArrayList<CarLocateDto>();
		RalUser user = EcUtil.getCurrentUser();
		if(areaId == null || areaId.length == 0) {
			areaMap = genericService.findMap(AreaInfo.class, new Criteria()
			.eq(AreaInfoRM.orgId, user.getOrgId())
			, AreaInfoRM.id);
		} else {
			areaMap = genericService.findMap(AreaInfo.class, new Criteria().eq(AreaInfoRM.orgId, user.getOrgId()).in(AreaInfoRM.id, areaId), AreaInfoRM.id);
		}
		
		if(areaMap.size() == 0){
			return carLocates;
		}
		
		Integer areaIds[] = new Integer[areaMap.size()];
		int idx=0;
		StringBuffer buffer = new StringBuffer();
		for (Map.Entry<Integer,AreaInfo> en:areaMap.entrySet()) {
			AreaInfo area = en.getValue();
			areaIds[idx++] = area.getId();
			buffer.append(area.getId()).append(",");
		}
		if(buffer.length() > 0) {
			buffer.deleteCharAt(buffer.length() - 1);
		}
		
		// 根据区域查找车辆及轨迹信息
		List<CarInfo> carInfoList = null;
		if("1".equals(online)) {//查找在线车辆
			carInfoList = carInfoService.findValidAndOnlineCarInfoByArea(buffer.toString());
		} else {
			carInfoList = carInfoService.findValidCarInfoByArea(buffer.toString());
		}
//		List<CarInfo> carInfoList = genericService.findList(CarInfo.class, new Criteria().in(CarInfoRM.areaId, areaIds));
		if(carInfoList.size() == 0) { //根据地区找不到车
			return carLocates;
		}
		
		for(CarInfo car:carInfoList) {
			CarLocateDto dto = new CarLocateDto();
			AreaInfo areaInfo = areaMap.get(car.getAreaId());
			dto.setAreaName(areaInfo.getName());
			dto.setAreaId(areaInfo.getId());
			dto.setStatus(car.getStatus());
			dto.setCarId(car.getId());
			DeviceData lastestData = genericService.findOne(DeviceData.class, new Criteria()
			.eq(DeviceDataRM.deviceId, car.getDeviceId())
			.orderBy(DeviceDataRM.gpsTime, OrderType.DESC));
			if(lastestData != null) {
				dto.setBaiduLatitude(lastestData.getBaiduLatitude());
				dto.setBaiduLongitude(lastestData.getBaiduLongitude());
				dto.setImei(lastestData.getImei());
				dto.setSpeed(lastestData.getSpeed());
				dto.setDirection(lastestData.getAngleDirection());
				dto.setUpdateTime(DateUtils.dateToString(lastestData.getUpdateTime(), TimeFormatter.FORMATTER1));
			} else {
				//给车辆一个默认地址？
				DeviceInfo device = genericService.findByPK(DeviceInfo.class, car.getDeviceId());
				if(device != null) {
					dto.setImei(device.getCode());
				}
				CityInfo city = getOptCity();
				dto.setBaiduLatitude(city.getLatitude());
				dto.setBaiduLongitude(city.getLongitude());
				dto.setUpdateTime(DateUtils.dateToString(car.getUpdateTime(), TimeFormatter.FORMATTER1));
				dto.setDirection("未知");
			}
			dto.setCarNo(car.getCarNo());
			
			DeviceInfo deviceInfo = genericService.findByPK(DeviceInfo.class, car.getDeviceId());
			if(deviceInfo.getOnline()) {//如果设备在线
				dto.setOnline(true);
				List<TagInfo> tagInfos = genericService.findList(TagInfo.class, new Criteria()
				.eq(TagInfoRM.carId, car.getId())
				.eq(TagInfoRM.online, true));
				if(tagInfos.size() > 0) {
					
					Integer userIds[] = new Integer[tagInfos.size()];
					for (int i = 0; i < tagInfos.size(); i++) {
						TagInfo tf = tagInfos.get(i);
						userIds[i] = tf.getUserId();
					}
					List<SurveyUserInfo> surveyUsers = genericService.findList(SurveyUserInfo.class, new Criteria()
					.in(SurveyUserInfoRM.id, userIds));
					List<SurveyUserDto> surveyUserDtos = new ArrayList<SurveyUserDto>();
					for(SurveyUserInfo u:surveyUsers) {
						SurveyUserDto userDto = new SurveyUserDto(); 
						userDto.setName(u.getName());
						userDto.setId(u.getId());
						userDto.setTel(u.getPhoneNo());
						surveyUserDtos.add(userDto);
					}
					dto.setSurveyUsers(surveyUserDtos);
				}
			} else {
				dto.setOnline(false);
			}
			
			
			
			List<EventInfo> eventInfos = genericService.findList(EventInfo.class, new Criteria()
			.eq(EventInfoRM.surveyCarId, car.getId())
			.eq(EventInfoRM.status, 1)
			);
//			int status = 0;
//			if(eventInfos.size() == 0) {
//				if(dto.getOnline()) {
//					status = 0;//待命中
//				} else {
//					if(car.getStatus() == 2) {
//						status = 2;//离线
//					} else {
//						status = 0;
//					}
//				}
//			} else {
//				if(dto.getOnline()) {
//					status = 1;//工作中
//				} else {
//					if(car.getStatus() == 2) {
//						status = 2;//离线
//					} else {
//						status = 0;
//					}
//				}
//			}
			dto.setStatus(car.getStatus());
			dto.setEventInfos(eventInfos);
			
			carLocates.add(dto);
		}
		return carLocates;
	}
	
	@RequestMapping(value="addressAnalyze" )
	public String addressAnalyze(@RequestParam(value="keyWord",required=false) String keyWord,Model model){
		List<AreaInfo> areas = null;
		
		//查找出标记信息
		List<MarkInfo> markinfo= markInfoDao.findAll();
		model.addAttribute("marks", JSONUtil.toJson(markinfo));
		//查出所有的事故信息
		List<EventInfo> eventinfo=eventInfoDao.findAll();
		model.addAttribute("eventinfos", JSONUtil.toJson(eventinfo));
		
		CityInfo city = getOptCity();
		if(city != null) {
			model.addAttribute("cityName", city.getName());
		}
	
		return "locate/markInfo";
	}
	/**
	 * 标注点
	 * @param baiduLongitude 经度
	 * @param baiduLatitude 纬度
	 * @return
	 */
	@RequestMapping(value="mark" )
	@ResponseBody
	@Action(description = "添加标注", type = "标注信息")
	public ResultDto mark(
			@RequestParam(value="markName",required=false) String markName,
			@RequestParam(value="markDesc",required=false) String markDesc,
			@RequestParam(value="address",required=false) String address,
			@RequestParam(value="markType",required=true) int markType,
			@RequestParam(value="baiduLongitude",required=true) double baiduLongitude,
			@RequestParam(value="baiduLatitude",required=true) double baiduLatitude,
			@RequestParam(value="tagPoint",required=false) Boolean tagPoint) {
		try {
			Date now = new Date();
			MarkInfo markInfo = new MarkInfo();
			markName=CharsetUtil.decode(markName);
			markInfo.setName(markName);
			markInfo.setType(markType);
			markInfo.setBaiduLatitude(baiduLatitude);
			markInfo.setBaiduLongitude(baiduLongitude);
			markInfo.setAddress(CharsetUtil.decode(address));
			markInfo.setStatus(0);
			markInfo.setCreateTime(now);
			markInfo.setUpdateTime(now);
			RalUser user = EcUtil.getCurrentUser();
			markInfo.setOrgId(user.getOrgId());
			genericService.save(markInfo);
			
			ResultDto dto = new ResultDto(200,"添加标注成功！",markName);
			dto.setResult(markInfo);
			return dto;
		} catch (Exception e) {
			logger.error("",e);
			return new ResultDto(500,"添加标注失败！");
		}
	}
	
	@RequestMapping(value = "updateMarkerPosition")
	@ResponseBody
	@Action(description = "更新标注", type = "标注信息")
	public ResultDto updateMarkerPosition(
			@RequestParam(value="markInfoId") Long markInfoId,
			@RequestParam(value="address") String address,
			@RequestParam(value="lat") double latitude,
			@RequestParam(value="lng") double longitude
			) {
		try {
			MarkInfo info = genericService.findByPK(MarkInfo.class, markInfoId);
			if(info != null) {
				info.setBaiduLatitude(latitude);
				info.setBaiduLongitude(longitude);
				info.setAddress(address);
				genericService.update(info);
				ResultDto dto = new ResultDto(200,"位置修改成功",info.getName());
				dto.setResult(info);
				MarkInfoDto mdto = new MarkInfoDto();
				mdto.init(info);
				dto.setResult(mdto);
				return dto;
			} else {
				return new ResultDto(201,"找不到记录，markId="+markInfoId);
			}
		} catch (Exception e) {
			logger.error("",e);
			return new ResultDto(500,"位置修改失败");
		}
	}
}