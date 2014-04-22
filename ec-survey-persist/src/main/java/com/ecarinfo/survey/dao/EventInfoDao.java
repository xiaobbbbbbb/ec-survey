package com.ecarinfo.survey.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.survey.po.EventInfo;

public interface EventInfoDao extends ECDao<EventInfo> {

	// 案件列表
	List<Map<String, Object>> findEventInfoByCriteria(Criteria whereBy);

	// 案件记录数
	long findEventInfoCountByCriteria(Criteria whereBy);

	// 案件未调度统计
	List<Map<String, Object>> findEventInfoNoScheduledTotalByCriteria(Criteria whereBy);

	// 案件已调度统计
	List<Map<String, Object>> findEventInfoScheduledTotalByCriteria(Criteria whereBy);

	// 报案按月统计
	List<Map<String, Object>> findEventInfoTotalReportByCriteria(Criteria whereBy);
	
	//查勘次数  用于行车报告月报统计
	Integer findSurveyNumCountByCriteria(Criteria whereBy);
}
