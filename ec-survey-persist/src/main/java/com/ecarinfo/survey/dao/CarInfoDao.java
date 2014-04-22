package com.ecarinfo.survey.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.survey.po.CarInfo;

public interface CarInfoDao extends ECDao<CarInfo> {

	long findCarInfoCountByCriteria(Criteria whereBy);

	List<Map<String, Object>> findCarInfoByCriteria(Criteria whereBy);

	// 车辆行车报告
	List<Map<String, Object>> findCarInfoReportByCriteria(Criteria whereBy);

	long findCarInfoReportCountByCriteria(Criteria whereBy);
	
	List<CarInfo> findValidCarInfoByArea(@Param("areaIds") String areaIds);

	List<CarInfo> findValidAndOnlineCarInfoByArea(@Param("areaIds") String areaIds);
}
