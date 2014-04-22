package com.ecarinfo.survey.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.survey.po.CarReport;

public interface CarReportDao extends ECDao<CarReport> {
	/**
	 * 车辆运行报告
	 * @param whereBy
	 * @return
	 */
	List<Map<String, Object>> findCarReportSecondByCriteria(Criteria whereBy);
	long findCarReportSecondCountByCriteria(Criteria whereBy);
	
	CarReport findCarReportByImei(@Param("imei") String imei);
	
	//用于生成日报告
	List<Map<String, Object>> findCarReportToDayByCriteria(Criteria whereBy);
}
