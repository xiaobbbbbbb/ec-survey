package com.ecarinfo.survey.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.survey.po.CarReportDay;

public interface CarReportDayDao extends ECDao<CarReportDay> {
	/**
	 * 车辆运行报告
	 * @param whereBy
	 * @return
	 */
	List<Map<String, Object>> findCarReportDayByCriteria(Criteria whereBy);
	long findCarReportDayCountByCriteria(Criteria whereBy);
	
	//用于生成月报告报告
	List<Map<String, Object>> findCarReportDayToMonthCriteria(Criteria whereBy);
}
