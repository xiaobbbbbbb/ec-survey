package com.ecarinfo.survey.service;

import java.util.List;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.survey.view.CarReportMonthView;

public interface CarReportMonthService {
	ECPage<CarReportMonthView> queryCarReportMonthReportPages(String month);
	List<CarReportMonthView> findCarReportMonthReportInfo(String month);
}

