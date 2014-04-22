package com.ecarinfo.survey.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.util.RowMapperUtils;
import com.ecarinfo.ralasafe.dto.Constant;
import com.ecarinfo.ralasafe.dto.SystemContext;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.PageHelper;
import com.ecarinfo.survey.dao.CarInfoDao;
import com.ecarinfo.survey.po.CarInfo;
import com.ecarinfo.survey.rm.CarInfoRM;
import com.ecarinfo.survey.rm.CarReportRM;
import com.ecarinfo.survey.service.CarInfoService;
import com.ecarinfo.survey.view.CarInfoView;
import com.ecarinfo.survey.view.TablePrefix;

@Service("carInfoService")
public class CarInfoServiceImpl implements CarInfoService {

	@Resource
	private CarInfoDao carInfoDao;

	@Override
	public ECPage<CarInfoView> queryCarInfoPages(Integer disabled, String carNo) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		// 是否停用
		if (disabled == null) {
			whereBy.eq(TablePrefix.CarInfoFix + CarInfoRM.disabled, 1);
		} else {
			if (disabled == -1) {
				whereBy.greateThenOrEquals(TablePrefix.CarInfoFix + CarInfoRM.disabled, 0);
			} else {
				whereBy.eq(TablePrefix.CarInfoFix + CarInfoRM.disabled, disabled);
			}
		}
		if (StringUtils.isNotEmpty(carNo)) {
			whereBy.like(TablePrefix.CarInfoFix + CarInfoRM.carNo, "%" + carNo + "%", CondtionSeparator.AND);
		}
		whereBy.eq(TablePrefix.CarInfoFix + CarInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
		long counts = carInfoDao.findCarInfoCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(TablePrefix.CarInfoFix + CarInfoRM.pk, OrderType.ASC);

		List<Map<String, Object>> map = carInfoDao.findCarInfoByCriteria(whereBy);
		List<CarInfoView> list = RowMapperUtils.map2List(map, CarInfoView.class);

		ECPage<CarInfoView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}

	// 车辆运行报告
	public ECPage<CarInfoView> queryCarInfoReportPages(String startTime, String endTime, String carNo) {

		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq(TablePrefix.CarInfoFix + CarInfoRM.disabled, 1);

		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals(TablePrefix.CarReportFix + CarReportRM.createTime, startTime + " 00:00:00", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals(TablePrefix.CarReportFix + CarReportRM.createTime, endTime + " 23:59:59", CondtionSeparator.AND);
		}

		if (StringUtils.isNotEmpty(carNo)) {
			whereBy.like(TablePrefix.CarInfoFix + CarInfoRM.carNo, "%" + carNo + "%", CondtionSeparator.AND);
		}
		long counts = carInfoDao.findCarInfoReportCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(TablePrefix.CarInfoFix + CarInfoRM.pk, OrderType.ASC);

		List<Map<String, Object>> map = carInfoDao.findCarInfoReportByCriteria(whereBy);
		List<CarInfoView> list = RowMapperUtils.map2List(map, CarInfoView.class);

		ECPage<CarInfoView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}
	
	public List<CarInfo> findValidCarInfoByArea(String areaIds) {
		return carInfoDao.findValidCarInfoByArea(areaIds);
	}

	@Override
	public List<CarInfo> findValidAndOnlineCarInfoByArea(String areaIds) {
		// TODO Auto-generated method stub
		return carInfoDao.findValidAndOnlineCarInfoByArea(areaIds);
	}

}
