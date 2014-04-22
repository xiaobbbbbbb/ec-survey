package com.ecarinfo.survey.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.log.Action;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.dto.ZtreeDto;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.survey.dto.AreaInfoDto;
import com.ecarinfo.survey.po.AreaInfo;
import com.ecarinfo.survey.po.CarInfo;
import com.ecarinfo.survey.po.CityInfo;
import com.ecarinfo.survey.rm.AreaInfoRM;
import com.ecarinfo.survey.rm.CarInfoRM;
import com.ecarinfo.survey.service.AreaInfoService;
import com.ecarinfo.survey.view.AreaInfoView;

@Controller
@RequestMapping("/areaInfo")
public class AreaInfoController extends BaseController {

	private static final Logger logger = Logger.getLogger(AreaInfoController.class);

	@Resource
	private AreaInfoService areaInfoService;

	// 列表
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model,String name,String parentName, String startTime, String endTime) {
		try {
			name = DtoUtil.incode(name);
			parentName = DtoUtil.incode(parentName);
			ECPage<AreaInfoView> ECPage = areaInfoService.queryAreaInfoPages(1,name, parentName, startTime, endTime);
			model.addAttribute("ECPage", ECPage);
			return "manager/areainfo/list";
		} catch (Exception e) {
			logger.error("初始化地域管理列表加载失败", e);
		}
		return null;
	}

	// 区域树
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	@ResponseBody
	public List<ZtreeDto> getZtree(Boolean showUrl, Integer id, HttpServletRequest request) {
		List<ZtreeDto> zTree = new ArrayList<ZtreeDto>();
		try {
			Integer pid = (id != null && id > 0) ? id : 0;
			List<AreaInfo> dtos = this.genericService.findList(
					AreaInfo.class,
					new Criteria().eq(AreaInfoRM.disabled, 1).eq(AreaInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND)
							.eq(AreaInfoRM.pid, pid, CondtionSeparator.AND));

			String webpath = request.getContextPath();
			String target = "areaInfo_rightFrame";
			String url = webpath + "/areaInfo/updateUI?id=";
			for (AreaInfo dto : dtos) {
				ZtreeDto tree = new ZtreeDto();
				tree.setId(dto.getId());
				tree.setName(dto.getName());
				if (dto.getIsLeaf()) {
					tree.setIsParent(true);
					tree.setOpen(true);
				}
				if (showUrl) {
					tree.setUrl(url + dto.getId());
					tree.setTarget(target);
				}
				tree.setpId(dto.getPid());
				zTree.add(tree);
			}
		} catch (Exception e) {
			logger.error("资源树获取失败!", e);
		}
		return zTree;
	}

	// 添加UI
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(Model model) {
		RalUser user = EcUtil.getCurrentUser();
		Integer cityId = user.getCityId();
		CityInfo city = genericService.findByPK(CityInfo.class, cityId);
		model.addAttribute("areaName",city.getName());
		model.addAttribute("cityName",city.getName());
		model.addAttribute("cityId", cityId);
		model.addAttribute("type", "1");
		List<CarInfo> carList = genericService.findList(CarInfo.class,new Criteria().eq(CarInfoRM.orgId, EcUtil.getCurrentUser().getOrgId()).eq(CarInfoRM.disabled, 1).eq(CarInfoRM.onbind, 0));
		model.addAttribute("carList", carList);
		
		List<AreaInfo> listAreaInfo = this.genericService.findList(AreaInfo.class, new Criteria().eq(AreaInfoRM.disabled, 1));
		model.addAttribute("listAreaInfo", listAreaInfo);
		return "manager/areainfo/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加区域", type = "区域管理")
	public Json add(@RequestBody AreaInfoDto dto) {
		Json json = new Json();
		try {
			dto.setCreateTime(new Date());
			dto.setDisabled(1);
			dto.setIsLeaf(false);
			dto.setOrgId(EcUtil.getCurrentUser().getOrgId());
			if(dto.getPid()==null || dto.getPid()<=0){
				dto.setPid(0);
			}
			this.areaInfoService.save(dto);
			if (dto.getCarIds().length() > 0) {
				String[] tempStr = dto.getCarIds().split(",");
				for (String temp : tempStr) {
					if(temp!=null && temp.length()>0){
						Integer carId = Integer.parseInt(temp);
						this.genericService.updateWithCriteria(CarInfo.class,
								new Criteria().update(CarInfoRM.updateTime, DateUtils.currentDateStr()).update(CarInfoRM.areaId, dto.getId()).update(CarInfoRM.onbind, 1).eq(CarInfoRM.pk, carId));
					}
				}
			}
			json.setMsg("区域添加成功!");
			json.setObj(dto.getName());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("区域添加失败!");
			logger.error("添加失败!", e);
		}
		return json;
	}

	// 寻找要修改的
	@RequestMapping(value = "/updateUI", method = RequestMethod.GET)
	public String edit(Integer id, Model model) {
		AreaInfo dto = this.genericService.findByPK(AreaInfo.class, id);
		RalUser user = EcUtil.getCurrentUser();
		Integer cityId = user.getCityId();
		CityInfo city = genericService.findByPK(CityInfo.class, cityId);
		model.addAttribute("cityId", cityId);
		model.addAttribute("cityName",city.getName());
		model.addAttribute("type", "1");
		//查询未绑定区域的车
		List<CarInfo> carList = genericService.findList(CarInfo.class,new Criteria().eq(CarInfoRM.orgId, EcUtil.getCurrentUser().getOrgId()).eq(CarInfoRM.disabled, 1).eq(CarInfoRM.onbind, 0));
		model.addAttribute("carList", carList);
		List<AreaInfo> listAreaInfo = this.genericService.findList(AreaInfo.class, new Criteria().eq(AreaInfoRM.disabled, 1));
		model.addAttribute("listAreaInfo", listAreaInfo);
		//已绑定的车
		List<CarInfo> bindList = genericService.findList(CarInfo.class,new Criteria().eq(CarInfoRM.orgId, EcUtil.getCurrentUser().getOrgId()).eq(CarInfoRM.areaId, id));
		model.addAttribute("bindList", bindList);
		
		if (dto.getPid() != null && dto.getPid() > 0) {
			AreaInfo ato = this.genericService.findByPK(AreaInfo.class, dto.getPid());
			model.addAttribute("areaName",ato.getName());
		}else{
			model.addAttribute("areaName",city.getName());
		}
		model.addAttribute("dto", getAreaPotoDto(id, dto, bindList));
		return "manager/areainfo/add_update";
	}

	// 修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "修改区域", type = "修改管理")
	public Json update(@RequestBody AreaInfoDto dto) {
		Json json = new Json();
		try {
			if(dto.getAreaId()!=null && dto.getAreaId()>0){
				dto.setPid(dto.getAreaId());
			}else{
				dto.setPid(0);
			}
			this.genericService.updateWithCriteria(
					AreaInfo.class,
					new Criteria().update(AreaInfoRM.name, dto.getName()).update(AreaInfoRM.cityId, dto.getCityId())
							.update(AreaInfoRM.pid, dto.getPid())
							.update(AreaInfoRM.orderNo, dto.getOrderNo()).update(AreaInfoRM.updateTime, DateUtils.currentDateStr())
							.eq(AreaInfoRM.pk, dto.getId()));
			
 
			//根据区域id解除改区域下的所有车辆绑定信息
	        this.genericService.updateWithCriteria(CarInfo.class,
					new Criteria().update(CarInfoRM.updateTime, DateUtils.currentDateStr()).update(CarInfoRM.areaId,null).update(CarInfoRM.onbind, 0).eq(CarInfoRM.areaId, dto.getId()));
	        //绑定这次选定的车辆信息
	        if (dto.getCarIds().length() > 0) {
				String[] tempStr = dto.getCarIds().split(",");
				for (String temp : tempStr) {
					if(temp!=null && temp.length()>0){
						Integer carId = Integer.parseInt(temp);
						this.genericService.updateWithCriteria(CarInfo.class,
								new Criteria().update(CarInfoRM.updateTime, DateUtils.currentDateStr()).update(CarInfoRM.onbind, 1).update(CarInfoRM.areaId,dto.getId()).eq(CarInfoRM.pk, carId));
					}
				}
			}
			json.setMsg("区域修改成功!");
			json.setObj(dto.getName());
		} catch (Exception e) {
			logger.error("修改失败!", e);
		}
		return json;
	}

	// 删除(隐藏不可见)
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	@Action(description = "删除区域", type = "区域管理")
	public Json delete(Integer[] ids) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				//先解绑该区域下的车
		        this.genericService.updateWithCriteria(CarInfo.class,
						new Criteria().update(CarInfoRM.updateTime, DateUtils.currentDateStr()).update(CarInfoRM.areaId,null).update(CarInfoRM.onbind, 0).in(CarInfoRM.areaId, ids));

		        this.areaInfoService.delete(ids);
				json.setMsg("区域删除成功!");

				List<AreaInfo> dtos = this.genericService.findList(AreaInfo.class, new Criteria().in(AreaInfoRM.pk, ids));
				json.setObj(dtos.get(0).getName());
			} else {
				json.setSuccess(false);
				json.setMsg("区域删除失败!");
			}
		} catch (Exception e) {
			logger.error("删除失败!", e);
		}
		return json;
	}

	// 是否重复
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Json checkName(String name, Integer pid) {
		Json json = new Json();
		long count = 0;
		try {
			name = DtoUtil.incode(name);
			count = this.genericService.count(AreaInfo.class, new Criteria().eq(AreaInfoRM.name, name).eq(AreaInfoRM.pid, pid, CondtionSeparator.AND)
					.eq(AreaInfoRM.disabled, 1, CondtionSeparator.AND));
		} catch (Exception e) {
			logger.error("检查是否存在失败!", e);
		}
		json.setObj(count);
		return json;
	}

	// 搜索UI
	@RequestMapping(value = "/searchUI", method = RequestMethod.GET)
	public String searchUI() {
		return "manager/areainfo/search";
	}
	
	
	/**
	 * po==>dto
	 * @param dto
	 * @return
	 */
	private AreaInfoDto getAreaPotoDto(Integer id,AreaInfo dto,List<CarInfo> bindList){
		AreaInfoDto areaDto = new AreaInfoDto();
		areaDto.setAreaId(dto.getPid());
		
		String carIds="";
		for (CarInfo carInfo : bindList) {
			carIds+=carInfo.getId()+",";
		}
		if(carIds.trim().length()>=2){
			if(carIds.endsWith(",")){
				carIds=carIds.substring(0,carIds.length()-1);
				areaDto.setCarIds(carIds);
			}
		}
		areaDto.setCityId(dto.getCityId());
		areaDto.setId(dto.getId());
		areaDto.setIsLeaf(dto.getIsLeaf());
		areaDto.setName(dto.getName());
		areaDto.setOrderNo(dto.getOrderNo());
		
		return areaDto;
	}
}
