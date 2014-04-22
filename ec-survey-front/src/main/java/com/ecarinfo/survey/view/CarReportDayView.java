package com.ecarinfo.survey.view;

import java.util.Date;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.survey.po.CarReportDay;

public class CarReportDayView extends CarReportDay{
	private static final long serialVersionUID = 1L;
	
	private String carNo; //车牌号
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	
	public Date getCreateTime() {
		return super.getCreateTime();
	}

	public String getCreateTimes(){
		if(getCreateTime()!= null){
			return DateUtils.dateToString(getCreateTime(), "yyyy-MM-dd HH:mm");
		}else{
			return "";
		}
	}
}
