package com.ecarinfo.survey.service;

import java.util.List;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.survey.view.CarReportDayToMonthView;
import com.ecarinfo.survey.view.CarReportDayView;

public interface CarReportDayService {
	ECPage<CarReportDayView> queryCarReportDayReportPages(String startTime, String endTime);
	List<CarReportDayToMonthView> findCarReportDayToMonthCriteria(String month,String startTime,String endTime);
}

