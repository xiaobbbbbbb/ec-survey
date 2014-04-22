package com.ecarinfo.survey.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.persist.util.RowMapperUtils;
import com.ecarinfo.ralasafe.dto.Constant;
import com.ecarinfo.ralasafe.dto.SystemContext;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.PageHelper;
import com.ecarinfo.survey.dao.EventInfoDao;
import com.ecarinfo.survey.dto.ResultDto;
import com.ecarinfo.survey.po.EventInfo;
import com.ecarinfo.survey.rm.EventInfoRM;
import com.ecarinfo.survey.service.EventInfoService;
import com.ecarinfo.survey.view.EventInfoNoScheduledCountView;
import com.ecarinfo.survey.view.EventInfoScheduledCountView;
import com.ecarinfo.survey.view.EventInfoTotalReportView;
import com.ecarinfo.survey.view.EventInfoView;
import com.ecarinfo.survey.view.TablePrefix;

@Service("eventInfoService")
public class EventInfoServiceImpl implements EventInfoService {

	@Resource
	private EventInfoDao eventInfoDao;
	@Resource
	private GenericService genericService;

	// 案件列表查询
	public ECPage<EventInfoView> queryEventInfoPages(String startTime, String endTime, Integer areaId, String carNo, Integer status, String address,
			Integer flag) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq(TablePrefix.EventInfoFix + EventInfoRM.orgId, EcUtil.getCurrentUser().getOrgId());
		if (flag != null && flag == 1) {
			Criteria or=new Criteria();
			
			or.eq(TablePrefix.EventInfoFix + EventInfoRM.status, "0")
					.eq(TablePrefix.EventInfoFix + EventInfoRM.status, "3", CondtionSeparator.OR)
					.eq(TablePrefix.EventInfoFix + EventInfoRM.status, "4", CondtionSeparator.OR);
			
			whereBy.andCriteria(or);
		}
		if (flag != null && flag == 2) {
			Criteria or=new Criteria();
			or.eq(TablePrefix.EventInfoFix + EventInfoRM.status, "1").eq(TablePrefix.EventInfoFix + EventInfoRM.status,
					"2", CondtionSeparator.OR);
			whereBy.andCriteria(or);
		}
		if (areaId != null) {
			whereBy.eq(TablePrefix.EventInfoFix + EventInfoRM.areaId, areaId, CondtionSeparator.AND);
		}

		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals(TablePrefix.EventInfoFix + EventInfoRM.createTime, startTime + " 00:00:00", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals(TablePrefix.EventInfoFix + EventInfoRM.createTime, endTime + " 23:59:59", CondtionSeparator.AND);
		}

		if (StringUtils.isNotEmpty(carNo)) {
			whereBy.like(TablePrefix.EventInfoFix + EventInfoRM.carNo, "%" + carNo + "%", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(address)) {
			whereBy.like(TablePrefix.EventInfoFix + EventInfoRM.address, "%" + address + "%", CondtionSeparator.AND);
		}
		
		long counts = eventInfoDao.findEventInfoCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(TablePrefix.EventInfoFix + EventInfoRM.pk, OrderType.ASC);

		List<Map<String, Object>> map = eventInfoDao.findEventInfoByCriteria(whereBy);
		List<EventInfoView> list = RowMapperUtils.map2List(map, EventInfoView.class);

		ECPage<EventInfoView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}

	// 案件统计未调度
	public List<EventInfoNoScheduledCountView> queryEventInfoNoScheduledTotal(String startTime, String endTime) {
		Criteria whereBy = new Criteria();
		if (StringUtils.isEmpty(startTime)) {
			startTime = DateUtils.dateToString(DateUtils.getMonday(), TimeFormatter.YYYY_MM_DD);
		}
		whereBy.greateThenOrEquals(TablePrefix.EventInfoFix + EventInfoRM.createTime, startTime + " 00:00:00");
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals(TablePrefix.EventInfoFix + EventInfoRM.createTime, endTime + " 23:59:59", CondtionSeparator.AND);
		}
		whereBy.eq(TablePrefix.EventInfoFix + EventInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
		whereBy.groupBy(TablePrefix.EventInfoFix + EventInfoRM.status).orderBy(TablePrefix.EventInfoFix + EventInfoRM.status, OrderType.ASC);
		List<Map<String, Object>> map = eventInfoDao.findEventInfoNoScheduledTotalByCriteria(whereBy);
		List<EventInfoNoScheduledCountView> list = RowMapperUtils.map2List(map, EventInfoNoScheduledCountView.class);
		return list;
	}

	// 案件统计已调度
	public List<EventInfoScheduledCountView> queryEventInfoScheduledTotal(String startTime, String endTime, Integer areaId) {
		Criteria whereBy = new Criteria();
		if (StringUtils.isEmpty(startTime)) {
			startTime = DateUtils.dateToString(DateUtils.getMonday(), TimeFormatter.YYYY_MM_DD);
		}
		whereBy.greateThenOrEquals(TablePrefix.EventInfoFix + EventInfoRM.createTime, startTime + " 00:00:00");
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals(TablePrefix.EventInfoFix + EventInfoRM.createTime, endTime + " 23:59:59", CondtionSeparator.AND);
		}
		if (areaId != null && areaId > 0) {
			whereBy.eq(TablePrefix.EventInfoFix + EventInfoRM.areaId, areaId, CondtionSeparator.AND);
		}
		whereBy.eq(TablePrefix.EventInfoFix + EventInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
		whereBy.moreThen(TablePrefix.EventInfoFix + EventInfoRM.status, 0, CondtionSeparator.AND);
		whereBy.groupBy(TablePrefix.EventInfoFix + EventInfoRM.status).groupBy(TablePrefix.EventInfoFix + EventInfoRM.carNo)
				.orderBy(TablePrefix.EventInfoFix + EventInfoRM.status, OrderType.ASC);
		List<Map<String, Object>> map = eventInfoDao.findEventInfoScheduledTotalByCriteria(whereBy);
		List<EventInfoScheduledCountView> list = RowMapperUtils.map2List(map, EventInfoScheduledCountView.class);
		return list;
	}

	// 报案统计
	public List<EventInfoTotalReportView> findEventInfoTotalReport(String startTime, String endTime) {
		Criteria whereBy = new Criteria();
		int year = DateUtils.getYear();
		if (StringUtils.isEmpty(startTime)) {
			startTime = year + "-01-01";
		}
		if (StringUtils.isEmpty(endTime)) {
			endTime = year + "-12-31";
		}
		whereBy.greateThenOrEquals(TablePrefix.EventInfoFix + EventInfoRM.createTime, startTime + " 00:00:00");
		whereBy.lessThenOrEquals(TablePrefix.EventInfoFix + EventInfoRM.createTime, endTime + " 23:59:59", CondtionSeparator.AND);
		whereBy.eq(TablePrefix.EventInfoFix + EventInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
		List<Map<String, Object>> map = eventInfoDao.findEventInfoTotalReportByCriteria(whereBy);
		List<EventInfoTotalReportView> list = RowMapperUtils.map2List(map, EventInfoTotalReportView.class);
		return list;
	}

	@Override
	public ResultDto tagPoint(int type, Integer orgId, String carNo, String phoneNo, String address, double latitude, double longitude, String name,
			String eventDesc) {

		Date now = new Date();
		EventInfo info = new EventInfo();
		info.setAddress(address);
		// info.setAreaInfoId(areaInfoId);
		info.setCarNo(carNo);
		info.setCreateTime(now);
		info.setGeohash(longitude + " " + latitude);
		info.setLatitude(latitude);
		info.setLongitude(longitude);
		info.setName(name);
		info.setOrgId(orgId);
		info.setEventDesc(eventDesc);
		// 年月日+0000001
		String yyyyMMdd = DateUtils.dateToString(now, TimeFormatter.YYYYMMDD);
		Long count = genericService.count(EventInfo.class, new Criteria().like(EventInfoRM.no, yyyyMMdd + "%"));
		String serialNo = getSerialNo(count.intValue());
		info.setNo(yyyyMMdd + serialNo);

		// RalUser user = EcUtil.getCurrentUser();
		// info.setOpUid(user.getUserId());

		info.setPhoneNo(phoneNo);
		info.setStatus(0);
		// info.setSurveyUid(surveyUid);
		info.setUpdateTime(now);
		genericService.save(info);

		ResultDto dto = new ResultDto(200, "案件标定成功", info.getName());
		dto.setResult(info);
		return dto;

	}

	// 流水号加1后返回，流水号长度为7
	private static final String STR_FORMAT = "0000000";

	private static String getSerialNo(int value) {
		value++;
		DecimalFormat df = new DecimalFormat(STR_FORMAT);
		return df.format(value);
	}

	@Override
	public List<EventInfoView> queryEventInfos(String startTime, String endTime, Integer areaId, String carNo, Integer status, String address) {
		Criteria whereBy = new Criteria();

		if (status != null) {
			whereBy.eq(TablePrefix.EventInfoFix + EventInfoRM.status, status);
		} else {
			whereBy.greateThenOrEquals(TablePrefix.EventInfoFix + EventInfoRM.status, 0);
		}

		if (areaId != null) {
			whereBy.eq(TablePrefix.EventInfoFix + EventInfoRM.areaId, areaId, CondtionSeparator.AND);
		}

		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals(TablePrefix.EventInfoFix + EventInfoRM.createTime, startTime + " 00:00:00", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals(TablePrefix.EventInfoFix + EventInfoRM.createTime, endTime + " 23:59:59", CondtionSeparator.AND);
		}

		if (StringUtils.isNotEmpty(carNo)) {
			whereBy.like(TablePrefix.EventInfoFix + EventInfoRM.carNo, "%" + carNo + "%", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(address)) {
			whereBy.like(TablePrefix.EventInfoFix + EventInfoRM.address, "%" + address + "%", CondtionSeparator.AND);
		}
		whereBy.eq(TablePrefix.EventInfoFix + EventInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);

		whereBy.orderBy(TablePrefix.EventInfoFix + EventInfoRM.pk, OrderType.ASC);

		List<Map<String, Object>> map = eventInfoDao.findEventInfoByCriteria(whereBy);
		List<EventInfoView> list = RowMapperUtils.map2List(map, EventInfoView.class);
		return list;
	}

	/**
	 * 统计每辆车前一天的查勘次数
	 */
	public Integer findSurveyNumCount(Integer status, String startTime, String endTime, Integer carId) {
		Criteria whereBy = new Criteria();
		if (status != null) {
			whereBy.eq(TablePrefix.EventInfoFix + EventInfoRM.status, status);
		} else {
			whereBy.greateThenOrEquals(TablePrefix.EventInfoFix + EventInfoRM.status, 0);
		}
		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals(TablePrefix.EventInfoFix + EventInfoRM.createTime, startTime + " 00:00:00", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals(TablePrefix.EventInfoFix + EventInfoRM.updateTime, endTime + " 23:59:59", CondtionSeparator.AND);
		}
		if (carId != null) {
			whereBy.eq(TablePrefix.EventInfoFix + EventInfoRM.surveyCarId, carId, CondtionSeparator.AND);
		}
		// return eventInfoDao.findSurveyNumCountByCriteria(whereBy);
		return 0;
	}
}
