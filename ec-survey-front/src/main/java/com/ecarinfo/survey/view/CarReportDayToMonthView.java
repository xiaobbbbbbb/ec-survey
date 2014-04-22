package com.ecarinfo.survey.view;

import java.util.Date;

public class CarReportDayToMonthView{
	private Integer carIdM;//车辆id
	private Integer surveyNumM;//月勘察次数
	private Integer trafficNumM;//月违章次数
	private Float totalDayMileM;//月总里程
	private Integer totalFenceCountsM;//月围栏告警纪录
	private Integer totalHypervelocityM;//月超速纪录
	private Date clientDayM;//客户端时间
	public Integer getCarIdM() {
		return carIdM;
	}
	public void setCarIdM(Integer carIdM) {
		this.carIdM = carIdM;
	}
	public Integer getSurveyNumM() {
		return surveyNumM;
	}
	public void setSurveyNumM(Integer surveyNumM) {
		this.surveyNumM = surveyNumM;
	}
	public Integer getTrafficNumM() {
		return trafficNumM;
	}
	public void setTrafficNumM(Integer trafficNumM) {
		this.trafficNumM = trafficNumM;
	}
	public Float getTotalDayMileM() {
		return totalDayMileM;
	}
	public void setTotalDayMileM(Float totalDayMileM) {
		this.totalDayMileM = totalDayMileM;
	}
	public Integer getTotalFenceCountsM() {
		return totalFenceCountsM;
	}
	public void setTotalFenceCountsM(Integer totalFenceCountsM) {
		this.totalFenceCountsM = totalFenceCountsM;
	}
	public Integer getTotalHypervelocityM() {
		return totalHypervelocityM;
	}
	public void setTotalHypervelocityM(Integer totalHypervelocityM) {
		this.totalHypervelocityM = totalHypervelocityM;
	}
	public Date getClientDayM() {
		return clientDayM;
	}
	public void setClientDayM(Date clientDayM) {
		this.clientDayM = clientDayM;
	}
	
	

}
