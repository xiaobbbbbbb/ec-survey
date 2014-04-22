package com.ecarinfo.survey.service;

import java.util.List;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.survey.view.SurveyUserInfoView;

public interface SurveyUserInfoService {

	ECPage<SurveyUserInfoView> querySurveyUserInfoPages(String name, Integer status, Integer disabled);

	ECPage<SurveyUserInfoView> querySurveyUserInfoReportPages(String startTime, String endTime, String name);
	
	List<SurveyUserInfoView> querySurveyUserInfos(String startTime, String endTime, String name);
	
}
