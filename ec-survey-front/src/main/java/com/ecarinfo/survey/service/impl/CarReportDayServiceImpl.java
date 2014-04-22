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
import com.ecarinfo.ralasafe.utils.PageHelper;
import com.ecarinfo.survey.dao.CarReportDayDao;
import com.ecarinfo.survey.rm.CarReportDayRM;
import com.ecarinfo.survey.service.CarReportDayService;
import com.ecarinfo.survey.view.CarReportDayToMonthView;
import com.ecarinfo.survey.view.CarReportDayView;
import com.ecarinfo.survey.view.TablePrefix;

@Service("carReportDayService")
public class CarReportDayServiceImpl implements CarReportDayService {
	@Resource
	private CarReportDayDao carReportDayDao;
	@Override
	public ECPage<CarReportDayView> queryCarReportDayReportPages(String startTime,
			String endTime) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq("1", 1);
		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals(TablePrefix.CarReportDayFix+ CarReportDayRM.createTime, startTime + " 00:00:00",CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals(TablePrefix.CarReportDayFix + CarReportDayRM.createTime, endTime + " 23:59:59",CondtionSeparator.AND);
		}
		long counts = carReportDayDao.findCarReportDayCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(TablePrefix.CarReportDayFix + CarReportDayRM.pk,OrderType.ASC);
		List<Map<String, Object>> map = carReportDayDao.findCarReportDayByCriteria(whereBy);
		List<CarReportDayView> list = RowMapperUtils.map2List(map,CarReportDayView.class);
		ECPage<CarReportDayView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}
	
	public List<CarReportDayToMonthView> findCarReportDayToMonthCriteria(String month,String startTime,String endTime){
		Criteria whereBy = new Criteria();
		whereBy.eq("1", 1);
		if (StringUtils.isNotEmpty(month)) {
			whereBy.like(TablePrefix.CarReportDayFix+ CarReportDayRM.clientDay, "%" +month +"%",CondtionSeparator.AND);
		}
		whereBy.greateThenOrEquals(TablePrefix.CarReportDayFix + CarReportDayRM.createTime, startTime + " 00:00:00", CondtionSeparator.AND);
		whereBy.lessThenOrEquals(TablePrefix.CarReportDayFix + CarReportDayRM.createTime, endTime + " 23:59:59", CondtionSeparator.AND);
		List<Map<String, Object>> map =carReportDayDao.findCarReportDayToMonthCriteria(whereBy);
		List<CarReportDayToMonthView> list =RowMapperUtils.map2List(map,CarReportDayToMonthView.class);
		return list;
	}

}
