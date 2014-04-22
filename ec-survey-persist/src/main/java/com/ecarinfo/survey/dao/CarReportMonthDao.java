package com.ecarinfo.survey.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.survey.po.CarReportMonth;

public interface CarReportMonthDao extends ECDao<CarReportMonth> {
	/**
	 * 车辆运行报告
	 * @param whereBy
	 * @return
	 */
	List<Map<String, Object>> findCarReportMonthByCriteria(Criteria whereBy);
	long findCarReportMonthCountByCriteria(Criteria whereBy);
}
