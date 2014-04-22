package com.ecarinfo.survey.service;

import java.util.List;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.survey.view.CarReportToDayView;
import com.ecarinfo.survey.view.CarReportView;

public interface CarReportService {
	ECPage<CarReportView> queryCarReportSecondReportPages(String startTime, String endTime,Integer carId,String month);
	
	List<CarReportToDayView> findCarReportToDayByCriteria(String startTime,String endTime);
	 //导出报表
	List<CarReportView> findCarReportInfoReport(String startTime, String endTime,Integer carId,String month);
}
