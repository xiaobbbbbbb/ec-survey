package com.ecarinfo.survey.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.survey.po.FenceAlarmInfo;

public interface FenceAlarmInfoDao extends ECDao<FenceAlarmInfo> {
	long findFenceAlarmInfoReportCountByCriteria(Criteria whereBy);
	List<Map<String, Object>> findFenceAlarmInfoReportByCriteria(Criteria whereBy);
}
