package com.ecarinfo.survey.view;

import com.ecarinfo.survey.po.CarInfo;

public class CarInfoView extends CarInfo {

	private static final long serialVersionUID = 6125396805296491118L;

	private String areaName;// 区域名称
	
	private Integer surveySum;//车辆查勘次数

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getSurveySum() {
		return surveySum;
	}

	public void setSurveySum(Integer surveySum) {
		this.surveySum = surveySum;
	}
}
