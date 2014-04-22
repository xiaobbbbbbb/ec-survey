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
import com.ecarinfo.survey.dao.CarReportMonthDao;
import com.ecarinfo.survey.rm.CarInfoRM;
import com.ecarinfo.survey.rm.CarReportMonthRM;
import com.ecarinfo.survey.service.CarReportMonthService;
import com.ecarinfo.survey.view.CarReportMonthView;
import com.ecarinfo.survey.view.TablePrefix;

@Service("carReportMonthService")
public class CarReportMonthServiceImpl implements CarReportMonthService {
	@Resource
	private CarReportMonthDao carReportMonthDao;
	@Override
	public ECPage<CarReportMonthView> queryCarReportMonthReportPages(String month) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq("1", 1);
		if (StringUtils.isNotEmpty(month)) {
			whereBy.like(TablePrefix.CarReportMonthFix+ CarReportMonthRM.month,"%"+ month + "%",CondtionSeparator.AND);
		}
		whereBy.eq(TablePrefix.CarInfoFix + CarInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
		long counts = carReportMonthDao.findCarReportMonthCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(TablePrefix.CarReportMonthFix + CarReportMonthRM.pk,OrderType.ASC);
		List<Map<String, Object>> map = carReportMonthDao.findCarReportMonthByCriteria(whereBy);
		List<CarReportMonthView> list = RowMapperUtils.map2List(map,CarReportMonthView.class);
		ECPage<CarReportMonthView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}
	
	/**
	 * 导出
	 * @param month
	 * @return
	 */
	public List<CarReportMonthView> findCarReportMonthReportInfo(String month) {
		Criteria whereBy = new Criteria();
		whereBy.eq("1", 1);
		if (StringUtils.isNotEmpty(month)) {
			whereBy.eq(TablePrefix.CarReportMonthFix+ CarReportMonthRM.month, month,CondtionSeparator.AND);
		}
		whereBy.eq(TablePrefix.CarInfoFix + CarInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
		List<Map<String, Object>> map = carReportMonthDao.findCarReportMonthByCriteria(whereBy);
		List<CarReportMonthView> list = RowMapperUtils.map2List(map,CarReportMonthView.class);
		return list;
	}

}
