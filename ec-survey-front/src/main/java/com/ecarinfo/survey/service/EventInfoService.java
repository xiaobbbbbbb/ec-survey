package com.ecarinfo.survey.service;

import java.util.List;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.survey.dto.ResultDto;
import com.ecarinfo.survey.view.EventInfoNoScheduledCountView;
import com.ecarinfo.survey.view.EventInfoScheduledCountView;
import com.ecarinfo.survey.view.EventInfoTotalReportView;
import com.ecarinfo.survey.view.EventInfoView;

public interface EventInfoService {

	ECPage<EventInfoView> queryEventInfoPages(String startTime, String endTime,Integer areaId, String carNo, Integer status, String address,Integer flag);

	//未调度
	List<EventInfoNoScheduledCountView> queryEventInfoNoScheduledTotal(String startTime, String endTime);
	
	//已调度
	List<EventInfoScheduledCountView> queryEventInfoScheduledTotal(String startTime, String endTime,Integer areaId);
	
	List<EventInfoTotalReportView> findEventInfoTotalReport(String startTime, String endTime);

	ResultDto tagPoint(int type,Integer orgId,String carNo,String phoneNo, String address, double latitude,
			double longitude, String name,String eventDesc);

	List<EventInfoView> queryEventInfos(String startTime, String endTime, Integer areaId, String carNo, Integer status, String address);
	
	
	//查勘次数进行日报告统计
	Integer  findSurveyNumCount(Integer status,String startTime, String endTime,Integer carId);
}

