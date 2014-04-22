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
import com.ecarinfo.survey.dao.FenceAlarmInfoDao;
import com.ecarinfo.survey.rm.CarInfoRM;
import com.ecarinfo.survey.rm.FenceAlarmInfoRM;
import com.ecarinfo.survey.service.FenceAlarmInfoService;
import com.ecarinfo.survey.view.FenceAlarmInfoView;
import com.ecarinfo.survey.view.TablePrefix;

@Service("fenceAlarmInfoService")
public class FenceAlarmInfoServiceImpl implements FenceAlarmInfoService {
	@Resource
	private FenceAlarmInfoDao fenceAlarmInfoDao;// 围栏类型、告警时间段、车牌、查勘员、手机号

	// 围栏告警
	public ECPage<FenceAlarmInfoView> queryFenceAlarmInfoReportPages(Integer type, String startTime, String endTime, String carNo,
			String surveyName, String surveyTel) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq("1", 1);
		if (type != null) {
			whereBy.eq(TablePrefix.FenceAlarmInfoFix + FenceAlarmInfoRM.type,	type, CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals(TablePrefix.FenceAlarmInfoFix+ FenceAlarmInfoRM.createTime, startTime + " 00:00:00",CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals(TablePrefix.FenceAlarmInfoFix + FenceAlarmInfoRM.createTime, endTime + " 23:59:59",CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(carNo)) { 
			whereBy.like(TablePrefix.CarInfoFix	+ CarInfoRM.carNo, "%" + carNo + "%",CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(surveyName)) { 
			whereBy.like(TablePrefix.FenceAlarmInfoFix	+ FenceAlarmInfoRM.surveyName, "%" + surveyName + "%",CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(surveyTel)) { 
			whereBy.like(TablePrefix.FenceAlarmInfoFix	+ FenceAlarmInfoRM.surveyTel, "%" + surveyTel + "%",CondtionSeparator.AND);
		}
		whereBy.eq(TablePrefix.FenceAlarmInfoFix + FenceAlarmInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND); //服务机构id
		long counts = fenceAlarmInfoDao.findFenceAlarmInfoReportCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(TablePrefix.FenceAlarmInfoFix + FenceAlarmInfoRM.pk,OrderType.ASC);
	
		List<Map<String, Object>> map = fenceAlarmInfoDao.findFenceAlarmInfoReportByCriteria(whereBy);
		List<FenceAlarmInfoView> list = RowMapperUtils.map2List(map,FenceAlarmInfoView.class);
		ECPage<FenceAlarmInfoView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}
	
	@Override
	public List<FenceAlarmInfoView> queryFenceAlarmInfoViewInfos(Integer type, String startTime, String endTime, String carNo,String surveyName, String surveyTel) {
		Criteria whereBy = new Criteria();
		whereBy.eq("1", 1);
		if (type != null) {
			whereBy.eq(TablePrefix.FenceAlarmInfoFix + FenceAlarmInfoRM.type,	type, CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals(TablePrefix.FenceAlarmInfoFix+ FenceAlarmInfoRM.createTime, startTime + " 00:00:00",CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals(TablePrefix.FenceAlarmInfoFix + FenceAlarmInfoRM.createTime, endTime + " 23:59:59",CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(carNo)) { 
			whereBy.like(TablePrefix.CarInfoFix	+ CarInfoRM.carNo, "%" + carNo + "%",CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(surveyName)) { 
			whereBy.like(TablePrefix.FenceAlarmInfoFix	+ FenceAlarmInfoRM.surveyName, "%" + surveyName + "%",CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(surveyTel)) { 
			whereBy.like(TablePrefix.FenceAlarmInfoFix	+ FenceAlarmInfoRM.surveyTel, "%" + surveyTel + "%",CondtionSeparator.AND);
		}
		whereBy.eq(TablePrefix.FenceAlarmInfoFix + FenceAlarmInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND); //服务机构id
		whereBy.orderBy(TablePrefix.FenceAlarmInfoFix + FenceAlarmInfoRM.pk, OrderType.ASC);
		List<Map<String, Object>> map = fenceAlarmInfoDao.findFenceAlarmInfoReportByCriteria(whereBy);
		List<FenceAlarmInfoView> list = RowMapperUtils.map2List(map, FenceAlarmInfoView.class);
		return list;
	}
}
