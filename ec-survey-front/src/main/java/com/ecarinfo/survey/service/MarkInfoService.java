package com.ecarinfo.survey.service;

import java.util.List;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.survey.view.MarkInfoView;

public interface MarkInfoService {
   //标注分类、时间区间
	ECPage<MarkInfoView> queryMarkInfoPages(String typeName, String startTime, String endTime);
	
	List<MarkInfoView> queryMarkInfos(String typeName, String startTime, String endTime);
	
	//标注信息
	List<MarkInfoView> queryMarkInfoList(Integer status);
}

