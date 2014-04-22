package com.ecarinfo.survey.view;

import com.ecarinfo.survey.po.TagInfo;

public class TagInfoView extends TagInfo {

	private static final long serialVersionUID = 8199849258127309899L;

	private String surveyUserName;// 查勘员姓名
	private String carNo;// 车牌号

	public String getSurveyUserName() {
		return surveyUserName;
	}

	public void setSurveyUserName(String surveyUserName) {
		this.surveyUserName = surveyUserName;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
}
