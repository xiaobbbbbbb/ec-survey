package com.ecarinfo.survey.service;

import java.util.List;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.survey.view.FenceAlarmInfoView;

public interface FenceAlarmInfoService {
	// 围栏类型、告警时间段、车牌、查勘员、手机号
	ECPage<FenceAlarmInfoView> queryFenceAlarmInfoReportPages(Integer typeName, String startTime, String endTime,
			String carNo, String surveyName,String surveyTel);

	List<FenceAlarmInfoView> queryFenceAlarmInfoViewInfos(Integer typeName, String startTime, String endTime,
			String carNo, String surveyName,String surveyTel);
}
