package com.ecarinfo.survey.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.util.RowMapperUtils;
import com.ecarinfo.ralasafe.dto.Constant;
import com.ecarinfo.ralasafe.dto.SystemContext;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.PageHelper;
import com.ecarinfo.survey.dao.AreaInfoDao;
import com.ecarinfo.survey.dao.CarInfoDao;
import com.ecarinfo.survey.dto.AreaInfoDto;
import com.ecarinfo.survey.po.AreaInfo;
import com.ecarinfo.survey.po.CarInfo;
import com.ecarinfo.survey.rm.AreaInfoRM;
import com.ecarinfo.survey.rm.CarInfoRM;
import com.ecarinfo.survey.service.AreaInfoService;
import com.ecarinfo.survey.view.AreaInfoView;
import com.ecarinfo.survey.view.TablePrefix;

@Service("areaInfoService")
public class AreaInfoServiceImpl implements AreaInfoService {

	@Resource
	private AreaInfoDao areaInfoDao;

	@Resource
	private GenericDao genericDao;
	
	@Resource
	private CarInfoDao carInfoDao;

	@Override
	public void save(AreaInfoDto dto) {
		if(dto.getAreaId()!=null && dto.getAreaId()>0){
			dto.setPid(dto.getAreaId());
		}else{
			dto.setPid(0);
		}
		if (dto.getPid()!=null && dto.getPid() > 0) {
			this.genericDao.updateWithCriteria(AreaInfo.class, new Criteria().update(AreaInfoRM.isLeaf, true).eq(AreaInfoRM.id, dto.getPid()));
		}
		areaInfoDao.insert(dto);
	}

	@Override
	public void delete(Integer[] ids) {
		for (Integer id : ids) {
			AreaInfo dto = this.genericDao.findByPK(AreaInfo.class, id);
			if (dto != null && dto.getPid() > 0) {
				long count = this.genericDao.count(AreaInfo.class,
						new Criteria().eq(AreaInfoRM.disabled, 1).eq(AreaInfoRM.pid, dto.getPid(), CondtionSeparator.AND));
				if (count == 1) {
					this.genericDao.updateWithCriteria(AreaInfo.class, new Criteria().update(AreaInfoRM.isLeaf, false)
							.eq(AreaInfoRM.id, dto.getPid()));
				}
			}
		}
		this.genericDao.updateWithCriteria(AreaInfo.class,
				new Criteria().update(AreaInfoRM.updateTime, DateUtils.currentDateStr()).update(AreaInfoRM.disabled, 0).in(AreaInfoRM.pk, ids));
	}

	@Override
	public ECPage<AreaInfoView> queryAreaInfoPages(Integer disabled,String name,String parentName, String startTime, String endTime) {
		 
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq("1", 1);
		if (disabled != null) {
			whereBy.eq(TablePrefix.AreaInfoFix + AreaInfoRM.disabled, disabled, CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(name)) {
			whereBy.like(TablePrefix.AreaInfoFix + AreaInfoRM.name, "%" + name + "%", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(parentName)) {
			whereBy.like(TablePrefix.AreaInfoPFix + AreaInfoRM.name, "%" + parentName + "%", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals(TablePrefix.AreaInfoFix + AreaInfoRM.createTime, startTime + " 00:00:00", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals(TablePrefix.AreaInfoFix + AreaInfoRM.createTime, endTime + " 23:59:59", CondtionSeparator.AND);
		}
		whereBy.eq(TablePrefix.AreaInfoFix + AreaInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(TablePrefix.AreaInfoFix + AreaInfoRM.pk, OrderType.ASC);
		long counts = areaInfoDao.findAreaCountByCriteria(whereBy);
		List<Map<String, Object>> map = areaInfoDao.findAreaByCriteria(whereBy);
		List<AreaInfoView> list = RowMapperUtils.map2List(map, AreaInfoView.class);
		for (AreaInfoView areaInfoView : list) {
			List<CarInfo> bindList = carInfoDao.findList(new Criteria().eq(CarInfoRM.orgId, EcUtil.getCurrentUser().getOrgId()).eq(CarInfoRM.areaId, areaInfoView.getId()));  //服务机构下绑定的车辆信息
			Set<String> carSet = new HashSet();
			for (CarInfo carInfo : bindList) {
				carSet.add(carInfo.getCarNo());
			}
			areaInfoView.setCarSet(carSet);
		}
		ECPage<AreaInfoView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}
}
