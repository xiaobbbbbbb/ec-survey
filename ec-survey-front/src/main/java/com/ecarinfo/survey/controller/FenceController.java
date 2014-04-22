package com.ecarinfo.survey.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.common.utils.CharsetUtil;
import com.ecarinfo.common.utils.JSONUtil;
import com.ecarinfo.log.Action;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.persist.util.RowMapperUtils;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.survey.dao.FenceCarDao;
import com.ecarinfo.survey.dao.FenceInfoDao;
import com.ecarinfo.survey.dto.FenceCarInfoDto;
import com.ecarinfo.survey.dto.PointDto;
import com.ecarinfo.survey.dto.ResultDto;
import com.ecarinfo.survey.po.CarInfo;
import com.ecarinfo.survey.po.CityInfo;
import com.ecarinfo.survey.po.FenceCar;
import com.ecarinfo.survey.po.FenceInfo;
import com.ecarinfo.survey.rm.CarInfoRM;
import com.ecarinfo.survey.rm.FenceCarRM;
import com.ecarinfo.survey.rm.FenceInfoRM;

//locate/fence
@Controller
@RequestMapping(value = "locate")
public class FenceController {
	private static final Logger logger = Logger
			.getLogger(FenceController.class);
	@Resource
	private GenericService genericService;
	@Resource
	private FenceInfoDao fenceInfoDao;
	@Resource
	private FenceCarDao fenceCarDao;
	@RequestMapping(value = "fence")
	public String index(
			@RequestParam(value = "areaId", defaultValue = "0", required = false) Integer areaId,
			Model model) {
		List<FenceInfo> fenceList = genericService.findList(FenceInfo.class,new Criteria().eq(FenceInfoRM.orgId, EcUtil.getCurrentUser().getOrgId()));
		model.addAttribute("fenceList", fenceList);
		RalUser user = EcUtil.getCurrentUser();
		Integer cityId = user.getCityId();
		CityInfo city = genericService.findByPK(CityInfo.class, cityId);
		if(city!=null){
			model.addAttribute("UserGrade", city.getGrade());
			model.addAttribute("cityName",city.getName());
		}else{
			model.addAttribute("cityName","深圳市");
		}
		return "locate/fence";
	}
	/**
	 * 添加电子围栏
	 * 
	 * @param model
	 * @param name
	 * @param description
	 * @param points
	 * @return
	 */
	@RequestMapping(value = "/saveFence", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加电子围栏", type = "电子围栏管理")
	public ResultDto saveFence(String name, String description,
			String points,Integer grade,String centerPoints) {
		ResultDto resultDto = new ResultDto();
		FenceInfo fence = new FenceInfo();
		List<PointDto> listPoint = new ArrayList<PointDto>();
		if (points != null && points.length() > 0) {
			String[] tempStr = points.split(",");
			PointDto pointDto = null;
			for (String temp : tempStr) {
				pointDto = new PointDto();
				String lng_lat = temp;
				String[] tempFencePointDto = lng_lat.split("_");
				if (tempFencePointDto.length > 0) {
					pointDto.setLng(Double.parseDouble(tempFencePointDto[0]));
					pointDto.setLat(Double.parseDouble(tempFencePointDto[1]));
				}
				listPoint.add(pointDto);
			}
			String pos = JSONUtil.toJson(listPoint);
			fence.setPoints(pos);
		}
		
		if (centerPoints != null && centerPoints.length() > 0) {
			String[] tempPoint = centerPoints.split("_");
			if(tempPoint.length>0){
				PointDto pointDto = new PointDto();
				pointDto.setLng(Double.parseDouble(tempPoint[0]));
				pointDto.setLat(Double.parseDouble(tempPoint[1]));
				String cpos = JSONUtil.toJson(pointDto);
				fence.setCenterPoints(cpos);
			}
		}
		
		fence.setOrgId(EcUtil.getCurrentUser().getOrgId());  //服务机构
		fence.setName(CharsetUtil.decode(name));
		fence.setDescription(description);
		fence.setAlarmType(0);
		fence.setAlarmEndTime("00:00");
		fence.setAlarmStartTime("00:00");
		fence.setGrade(grade);
		fence.setCreateTime(new Date());
		try {
			genericService.save(fence);
			resultDto.setStatus(200);
			resultDto.setMessage("添加成功");
			resultDto.setExtendsObject(name);
		} catch (Exception e) {
			resultDto.setStatus(500);
			resultDto.setMessage("添加失败");
			logger.error("添加电子围栏错误", e);
		}
		return resultDto;
	}

	/**
	 * 删除
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteFence", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "删除电子围栏", type = "电子围栏管理")
	public ResultDto deleteFence(Integer id) {
		ResultDto resultDto = new ResultDto();
		try {
			FenceInfo fenceInfo = genericService.findByPK(FenceInfo.class, id);
			// 先删除该围栏下面的绑定关系
			List<FenceCar> listFenceCar = genericService.findList(
					FenceCar.class, new Criteria().eq(FenceCarRM.fenceId, id));
			for (FenceCar fenceCar : listFenceCar) {
				genericService.deleteEntity(fenceCar);
			}
			// 删除围栏信息
			genericService.deleteByPK(FenceInfo.class, id);
			resultDto.setStatus(200);
			resultDto.setMessage("删除成功");
			resultDto.setExtendsObject(fenceInfo.getName());
		} catch (Exception e) {
			logger.error("删除电子围栏错误", e);
			resultDto.setStatus(500);
			resultDto.setMessage("删除失败");
		}
		return resultDto;
	}

	/**
	 * 跳转绑定界面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "bindCarInfoUI")
	public String bindCarInfoUI(Integer id, Model model) {
		FenceInfo fenceInfo = genericService.findByPK(FenceInfo.class, id);
		if(fenceInfo != null){
			model.addAttribute("fenceInfo", fenceInfo);
			if(fenceInfo.getAlarmStartTime()!=null && fenceInfo.getAlarmStartTime().length()>0){
				String start=fenceInfo.getAlarmStartTime();
				String [] tempStart=start.split(":");
				model.addAttribute("startHour", tempStart[0]);
				model.addAttribute("startMinute", tempStart[1]);
			}
			if(fenceInfo.getAlarmEndTime()!=null && fenceInfo.getAlarmEndTime().length()>0){
				String end=fenceInfo.getAlarmEndTime();
				String [] tempStart=end.split(":");
				model.addAttribute("endHour", tempStart[0]);
				model.addAttribute("endMinute", tempStart[1]);
			}
			List<CarInfo> carList = genericService.findList(CarInfo.class,new Criteria().eq(CarInfoRM.orgId, EcUtil.getCurrentUser().getOrgId()));
			model.addAttribute("carList", carList);
			List<FenceCar> fenceCarList = genericService.findList(FenceCar.class,new Criteria().eq(FenceCarRM.fenceId, id));
			// 得到该围栏下绑定的车辆Id
			List<Integer> listIds = new ArrayList<Integer>();
			for (FenceCar fenceCar : fenceCarList) {
				listIds.add(fenceCar.getCarId());
			}
			model.addAttribute("listIds", listIds);
		}
		return "locate/bindCarInfo";
	}

	/**
	 * 绑定车辆
	 * @param fenceId
	 * @param alarmStartTime
	 * @param alarmEndTime
	 * @param carIds
	 * @param ararmType
	 * @return
	 */
	@RequestMapping(value = "/bindCarInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto bindCarInfo(Integer fenceId,String carIds,String name,Integer alarmType,String description,String alarmStartTime,String alarmEndTime) {
		int status = 500;
		String message = "";
		try {
			// 更新电子信息
			FenceInfo fenceInfo = genericService.findByPK(FenceInfo.class,fenceId);
			if (fenceInfo == null)
				return new ResultDto(400, "绑定失败");
			fenceInfo.setUpdateTime(new Date());
			fenceInfo.setName(name);
			fenceInfo.setAlarmType(alarmType);
			fenceInfo.setAlarmStartTime(alarmStartTime);
			fenceInfo.setAlarmEndTime(alarmEndTime);
			if(description !=null && description.length()>0){
				fenceInfo.setDescription(description);
			}
			genericService.update(fenceInfo); // 更新电子信息

			// 删掉改电子围栏下面的已有的绑定关系
			genericService.delete(FenceCar.class,
					new Criteria().eq(FenceCarRM.fenceId, fenceId));

			if (carIds.length() > 0) {
				String[] tempStr = carIds.split(",");
				FenceCar fenceCar = null;
				for (String temp : tempStr) {
					Integer carId = Integer.parseInt(temp);
					fenceCar = genericService.findOne(
							FenceCar.class,
							new Criteria().eq(FenceCarRM.fenceId, fenceId).eq(
									FenceCarRM.carId, carId));
					if (fenceCar == null) {
						fenceCar = new FenceCar();
						fenceCar.setCarId(Integer.parseInt(temp));
						fenceCar.setFenceId(fenceId);
						fenceCar.setCreateTime(new Date());
						genericService.save(fenceCar);
					}
				}
			}
			status = 200;
			message = "绑定成功";
		} catch (Exception e) {
			status = 500;
			message = "绑定失败";
			logger.error("车辆绑定失败电子围栏错误", e);
		}
		return new ResultDto(status, message);
	}

	/**
	 * 查找相应电子围栏的已绑定的数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "findBindCarInfo", method = RequestMethod.POST)
	@ResponseBody
	public List<FenceCarInfoDto> findBindCarInfo(Integer id) {
		List<Map<String, Object>> map = fenceCarDao.findFenCarInfo(id);
		List<FenceCarInfoDto> fenceCarList =RowMapperUtils.map2List(map,FenceCarInfoDto.class);
		return fenceCarList;
	}

	/**
	 * 解除绑定
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/solutionBind", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto solutionBind(Integer id) {
		int status = 500;
		String message = "";
		try {
			genericService.deleteByPK(FenceCar.class, id);
			message = "解除绑定成功";
			status = 200;
		} catch (Exception e) {
			message = "解除绑定失败";
			logger.error("解除车辆绑定失败电子围栏错误", e);
		}
		return new ResultDto(status, message);
	}
}