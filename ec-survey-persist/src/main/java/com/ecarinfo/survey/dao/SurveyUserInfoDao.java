package com.ecarinfo.survey.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.survey.po.SurveyUserInfo;

public interface SurveyUserInfoDao extends ECDao<SurveyUserInfo> {

	List<Map<String, Object>> findSurveyUserInfoByCriteria(Criteria whereBy);

	long findSurveyUserInfoCountByCriteria(Criteria whereBy);

	long findSurveyUserInfoReportCountByCriteria(Criteria whereBy);

	List<Map<String, Object>> findSurveyUserInfoReportByCriteria(Criteria whereBy);
}
