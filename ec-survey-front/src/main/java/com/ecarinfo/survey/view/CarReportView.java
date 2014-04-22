package com.ecarinfo.survey.view;

import java.util.Date;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.survey.po.CarReport;
import com.ecarinfo.utils.poi.ExcelResources;

public class CarReportView extends CarReport{
	private static final long serialVersionUID = 1L;
	
	private String carNo; //车牌号
	private String suName;//勘察人
	@ExcelResources(title = "查勘员", order = 4)
	public String getSuName() {
		return suName;
	}
	
	public void setSuName(String suName) {
		this.suName = suName;
	}
	@ExcelResources(title = "车牌", order = 1)
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	
	@ExcelResources(title = "里程（KM）", order = 5)
	public Float getMileMeter() {
		 
		return super.getMileMeter();
	}
	@ExcelResources(title = "出发地点", order = 7)
	public String getStartAddress() {
		return super.getStartAddress();
	}
	
	@ExcelResources(title = "结束地点", order = 8)
	public String getEndAddress() {
		return super.getEndAddress();
	}
	public Date getUpdateTime() {
		return super.getUpdateTime();
	}
	public Date getCreateTime() {
		return super.getCreateTime();
	}

	@ExcelResources(title = "开始时间", order = 2)
	public String getStartTimes() {
		if(getCreateTime()!=null){
			return DateUtils.dateToString(getCreateTime(), "yyyy-MM-dd HH:mm");
		}else{
			return "";
		}
	}
	@ExcelResources(title = "结束时间", order = 3)
	public String getEndTimes() {
		if(getUpdateTime()!=null){
			return DateUtils.dateToString(getUpdateTime(), "yyyy-MM-dd HH:mm");
		}else{
			return "";
		}
	}

	@ExcelResources(title = "平均速度（KM/H）", order = 6)
	public Integer getAvgSpeed() {
		return super.getAvgSpeed();
	}

	
	
}
