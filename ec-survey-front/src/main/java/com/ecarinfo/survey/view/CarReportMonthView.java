package com.ecarinfo.survey.view;

import java.util.Date;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.survey.po.CarReportMonth;
import com.ecarinfo.utils.poi.ExcelResources;

public class CarReportMonthView extends CarReportMonth{
	private static final long serialVersionUID = 1L;
	
	private String carNo; //车牌号
	@ExcelResources(title = "车牌", order = 1)
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
	@ExcelResources(title = "查勘次数", order = 2)
	public Integer getSurveyNum() {
		return super.getSurveyNum();
	}
	@ExcelResources(title = "违章记录", order = 4)
	public Integer getTrafficNum() {
		
		return super.getTrafficNum();
	}
	@ExcelResources(title = "里程（KM）", order = 3)
	public Float getTotalMonthMile() {
		
		return super.getTotalMonthMile();
	}
	@ExcelResources(title = "围栏告警记录", order = 6)
	public Integer getTotalFenceCounts() {
		
		return super.getTotalFenceCounts();
	}
	@ExcelResources(title = "超速记录", order = 5)
	public Integer getTotalHypervelocity() {
		
		return super.getTotalHypervelocity();
	}
	@ExcelResources(title = "月份", order = 7)
	public String getMonth() {
		return super.getMonth();
	}
	
	
	
}
