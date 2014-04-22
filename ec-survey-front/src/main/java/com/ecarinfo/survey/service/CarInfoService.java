package com.ecarinfo.survey.service;

import java.util.List;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.survey.po.CarInfo;
import com.ecarinfo.survey.view.CarInfoView;

public interface CarInfoService {

	ECPage<CarInfoView> queryCarInfoPages(Integer disabled, String carNo);

	ECPage<CarInfoView> queryCarInfoReportPages(String startTime, String endTime, String carNo);
	
	List<CarInfo> findValidCarInfoByArea(String areaIds);
	
	List<CarInfo> findValidAndOnlineCarInfoByArea(String areaIds);
}
