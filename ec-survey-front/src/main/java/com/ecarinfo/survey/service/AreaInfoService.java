package com.ecarinfo.survey.service;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.survey.dto.AreaInfoDto;
import com.ecarinfo.survey.view.AreaInfoView;

public interface AreaInfoService {
	void save(AreaInfoDto dto);
	void delete(Integer[] ids);
	//区域信息
	ECPage<AreaInfoView> queryAreaInfoPages(Integer disabled,String name,String parentName, String startTime, String endTime);

}

