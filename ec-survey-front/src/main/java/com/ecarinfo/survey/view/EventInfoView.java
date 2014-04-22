package com.ecarinfo.survey.view;

import com.ecarinfo.survey.po.EventInfo;
import com.ecarinfo.utils.poi.ExcelResources;

public class EventInfoView extends EventInfo {

	private static final long serialVersionUID = -5646160030872867690L;

	private String areaInfoName; // 区域名称

	private String opUserName;// 录单员名称

	private String surveyUserName;// 查勘员名称

	@ExcelResources(title = "区域名称", order = 1)
	public String getAreaInfoName() {
		return areaInfoName;
	}

	public void setAreaInfoName(String areaInfoName) {
		this.areaInfoName = areaInfoName;
	}

	@ExcelResources(title = "录单员名称", order = 2)
	public String getOpUserName() {
		return opUserName;
	}

	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}

	@ExcelResources(title = "查勘员名称", order = 3)
	public String getSurveyUserName() {
		return surveyUserName;
	}

	public void setSurveyUserName(String surveyUserName) {
		this.surveyUserName = surveyUserName;
	}
}
