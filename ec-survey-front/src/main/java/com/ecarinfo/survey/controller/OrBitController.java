package com.ecarinfo.survey.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.common.utils.CharsetUtil;
import com.ecarinfo.common.utils.JSONUtil;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.survey.dao.AreaInfoDao;
import com.ecarinfo.survey.dto.AreaTreeDto;
import com.ecarinfo.survey.dto.CarReportDto;
import com.ecarinfo.survey.dto.OrbitDto;
import com.ecarinfo.survey.dto.PointDto;
import com.ecarinfo.survey.po.AreaInfo;
import com.ecarinfo.survey.po.CarInfo;
import com.ecarinfo.survey.po.CarReport;
import com.ecarinfo.survey.po.CityInfo;
import com.ecarinfo.survey.po.DeviceData;
import com.ecarinfo.survey.po.MarkType;
import com.ecarinfo.survey.rm.AreaInfoRM;
import com.ecarinfo.survey.rm.CarInfoRM;
import com.ecarinfo.survey.rm.CarReportRM;
import com.ecarinfo.survey.rm.DeviceDataRM;
import com.ecarinfo.survey.rm.MarkTypeRM;

@Controller
@RequestMapping(value = "locate")
public class OrBitController {
	private static final Logger logger = Logger.getLogger(OrBitController.class);
	@Resource
	private GenericService genericService;
	@Resource
	private AreaInfoDao areaInfoDao;
	@RequestMapping(value = "orbit")
	public String orbit(
			@RequestParam(value="keyWord",required=false) String keyWord,
			@RequestParam(value="carId",required=false) Integer carId,
			@RequestParam(value="carSportId",required=false) Long carSportId,
			Model model) {
		List<AreaInfo> areas = null;
		//查找菜单
		RalUser user = EcUtil.getCurrentUser();
		if((keyWord != null && keyWord.trim().length() > 0) && !"_".equals(keyWord)) {
			keyWord = CharsetUtil.decodeFromISO8859(keyWord);
			logger.debug("keyWord="+keyWord);
			areas = areaInfoDao.findAreaByKeyWord("%"+keyWord+"%",user.getOrgId());
			model.addAttribute("keyWord", keyWord);
		} else {
			//(TODO 后续改成权限相关)
			areas = genericService.findList(AreaInfo.class,new Criteria()
			.eq(AreaInfoRM.orgId, user.getOrgId())
			.eq(AreaInfoRM.disabled, 1));
		}
		
		List<MarkType> markTypes = genericService.findList(MarkType.class,new Criteria().eq(MarkTypeRM.orgId, user.getOrgId()));
		model.addAttribute("markTypes", JSONUtil.toJson(markTypes));
		
		Integer cityId = user.getCityId();
		CityInfo city = genericService.findByPK(CityInfo.class, cityId);
		model.addAttribute("cityName", city.getName());
		if(areas.size() == 0) {
			model.addAttribute("treeStr","{}");
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
		List<CarInfo> carInfoList = genericService.findList(CarInfo.class, new Criteria().in(CarInfoRM.areaId, areaIds));
		String jsonTree = getTreeStr(areas, carInfoList);
		model.addAttribute("treeStr", jsonTree);
		if(carId == null) {
			model.addAttribute("selectId", carInfoList.get(0).getId());
		} else {
			model.addAttribute("selectId", carId);
		}
	
		model.addAttribute("carSportId", carSportId);
		
		
		return "locate/orbit";
	}
	
	/**
	 * 获取行车轨迹
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="getCarOrbit" )
	@ResponseBody
	public Object getCarOrbit(Model model,
			@RequestParam(value="startTime",required=false) String startTime,
			@RequestParam(value="endTime",required=false) String endTime,
			@RequestParam(value="carId",required=false) Integer carId
			){
		
		logger.info("startTime="+startTime+",endTime="+endTime+",carId="+carId);
		if(carId == null) {
			return null;
		}
		CarInfo carInfo = genericService.findByPK(CarInfo.class, carId);
		
		List<CarReport> carReportList = null;
		if(startTime == null) {//默认显示最后10条记录
//			carReportList=genericService.findListBySql(CarReport.class, "select * from car_report where car_id='1' and DATE_FORMAT(last_client_time,'%Y%m%d') like" + 
//					"(select DATE_FORMAT(last_client_time,'%Y%m%d')  from car_report ORDER BY last_client_time desc LIMIT 1)");
//			if(startTime == null) {
//				startTime = DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER2);
//				endTime =DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER1);
//			}
//			
			carReportList = genericService.findList(CarReport.class, new Criteria()
			.eq(CarReportRM.carId, carInfo.getId())
			.orderBy(CarReportRM.firstClientTime, OrderType.DESC)
			.setPage(1, ECPage.DEFAULT_SIZE));
		} 
		else {//根据指定时间段查询
				
			carReportList = genericService.findList(CarReport.class, new Criteria()
			.eq(CarReportRM.carId, carInfo.getId())
			.greateThenOrEquals(CarReportRM.createTime, startTime)
			.lessThenOrEquals(CarReportRM.updateTime, endTime).orderBy(CarReportRM.firstClientTime, OrderType.DESC));
		}
	
		OrbitDto orbit = new OrbitDto();
		List<CarReportDto> reports = new ArrayList<CarReportDto>();
		
		/*for(CarReport carReport:carReportList) {
			CarReportDto reportDto = new CarReportDto();
			reportDto.init(carReport);
			reports.add(reportDto);
		}*/
		for (int i = 0; i < carReportList.size(); i++) {
			CarReport carReport = carReportList.get(i);
			CarReportDto reportDto = new CarReportDto();
			reportDto.init(carReport);
			reports.add(reportDto);
		}
		
		orbit.setReports(reports);
		orbit.setCarId(carInfo.getId());
		orbit.setCarNo(carInfo.getCarNo());
		logger.info("orbit="+orbit);
		return orbit;
	}
	
	@RequestMapping(value="getSingleOrbit" )
	@ResponseBody
	public Object getSingleOrbit(Model model,@RequestParam(value="carReportId",required=false) Integer carReportId){
		List<PointDto> pointDtoList = new ArrayList<PointDto>();
		
		List<DeviceData> datas = genericService.findList(DeviceData.class, new Criteria()
		.eq(DeviceDataRM.carReportId, carReportId)
		.moreThen(DeviceDataRM.baiduLatitude, 0)
		.orderBy(DeviceDataRM.clientTime, OrderType.ASC));
		for(DeviceData d:datas) {
			PointDto point = new PointDto();
			point.setLat(d.getBaiduLatitude());
			point.setLng(d.getBaiduLongitude());
			pointDtoList.add(point);
		}
		
		return pointDtoList;
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
}