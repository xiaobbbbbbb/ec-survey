package com.ecarinfo.survey.dto;

import java.util.List;

public class OrbitDto {
	
	private Integer carId;
	private String carNo;
	private List<CarReportDto> reports;
	private Integer speed = 10;//播放速度
	
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public List<CarReportDto> getReports() {
		return reports;
	}
	public void setReports(List<CarReportDto> reports) {
		this.reports = reports;
	}
	
}
