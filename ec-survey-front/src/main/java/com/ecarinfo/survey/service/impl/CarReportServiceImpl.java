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
import com.ecarinfo.survey.dao.CarReportDao;
import com.ecarinfo.survey.rm.CarInfoRM;
import com.ecarinfo.survey.rm.CarReportDayRM;
import com.ecarinfo.survey.rm.CarReportRM;
import com.ecarinfo.survey.service.CarReportService;
import com.ecarinfo.survey.view.CarReportToDayView;
import com.ecarinfo.survey.view.CarReportView;
import com.ecarinfo.survey.view.TablePrefix;

@Service("carReportService")
public class CarReportServiceImpl implements CarReportService {

	@Resource
	private CarReportDao carReportDao;
	/**
	 * 次报
	 */
	@Override
	public ECPage<CarReportView> queryCarReportSecondReportPages(String startTime, String endTime,Integer carId,String month) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq("1", 1);
		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals(TablePrefix.CarReportFix+ CarReportRM.createTime, startTime + " 00:00:00",CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals(TablePrefix.CarReportFix + CarReportRM.createTime, endTime + " 23:59:59",CondtionSeparator.AND);
		}
		if(carId!=null && carId>0){
			whereBy.eq(TablePrefix.CarReportFix + CarReportRM.carId, carId,CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(month)) {
			whereBy.like(TablePrefix.CarReportFix + CarReportRM.createTime, "%" +month+ "%",CondtionSeparator.AND);
		}
		whereBy.eq(TablePrefix.CarInfoFix + CarInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
		long counts = carReportDao.findCarReportSecondCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(TablePrefix.CarReportFix + CarReportRM.pk,OrderType.ASC);
		List<Map<String, Object>> map = carReportDao.findCarReportSecondByCriteria(whereBy);
		List<CarReportView> list = RowMapperUtils.map2List(map,CarReportView.class);
		ECPage<CarReportView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}
	
	/**
	 *导出报表
	 */
	public List<CarReportView> findCarReportInfoReport(String startTime, String endTime,Integer carId,String month){
		Criteria whereBy = new Criteria();
		whereBy.eq("1", 1);
		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals(TablePrefix.CarReportFix+ CarReportRM.createTime, startTime + " 00:00:00",CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals(TablePrefix.CarReportFix + CarReportRM.createTime, endTime + " 23:59:59",CondtionSeparator.AND);
		}
		if(carId!=null && carId>0){
			whereBy.eq(TablePrefix.CarReportFix + CarReportRM.carId, carId,CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(month)) {
			whereBy.like(TablePrefix.CarReportFix + CarReportRM.createTime, "%" +month+ "%",CondtionSeparator.AND);
		}
		whereBy.eq(TablePrefix.CarInfoFix + CarInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
		List<Map<String, Object>> map = carReportDao.findCarReportSecondByCriteria(whereBy);
		List<CarReportView> list = RowMapperUtils.map2List(map,CarReportView.class);
		return list;
	}
	
	/**
	 * 查询昨天的次报汇总生成日报告
	 */
	public List<CarReportToDayView> findCarReportToDayByCriteria(String startTime,String endTime){
		Criteria whereBy = new Criteria();
		whereBy.eq("1", 1);
		whereBy.greateThenOrEquals(TablePrefix.CarReportFix + CarReportDayRM.createTime, startTime + " 00:00:00", CondtionSeparator.AND);
		whereBy.lessThenOrEquals(TablePrefix.CarReportFix + CarReportDayRM.createTime, endTime + " 23:59:59", CondtionSeparator.AND);
		List<Map<String, Object>> map =carReportDao.findCarReportToDayByCriteria(whereBy);
		List<CarReportToDayView> list =RowMapperUtils.map2List(map,CarReportToDayView.class);
		return list;
	}
}



 
