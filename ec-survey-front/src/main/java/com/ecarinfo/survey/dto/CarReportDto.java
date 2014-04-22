package com.ecarinfo.survey.dto;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.survey.po.CarReport;

public class CarReportDto {
	private Long id;
	private Integer carId;
	private String surveyUid;//查勘员ID
	private Float mileMeter;//本次里程
	private Integer avgSpeed;//平均车速
	private Long totalTime;//累计行驶时长(秒)
	private Integer hypervelocity;//超速记录
	private Integer fenceCounts;//围栏告警记录
	private String startTime;//开始时间yyyy-MM-dd HH:mm
	private String endTime;//结束时间	yyyy-MM-dd HH:mm
	private String startAddress;//起点位置
	private String endAddress;
//	private List<PointDto> points;
	
	public void init(CarReport report) {
		this.id = report.getId();
		this.carId = report.getCarId();
		this.surveyUid = report.getSurveyUid();
		this.mileMeter = report.getMileMeter();
		this.avgSpeed = report.getAvgSpeed();
		this.totalTime = report.getTotalTime();
		this.hypervelocity = report.getHypervelocity();
		this.fenceCounts = report.getFenceCounts();
		this.startTime = DateUtils.dateToString(report.getFirstClientTime(), TimeFormatter.FORMATTER4);
		this.endTime = DateUtils.dateToString(report.getUpdateTime(), TimeFormatter.FORMATTER4);
		this.startAddress = report.getStartAddress();
		this.endAddress = report.getEndAddress();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	public String getSurveyUid() {
		return surveyUid;
	}
	public void setSurveyUid(String surveyUid) {
		this.surveyUid = surveyUid;
	}
	public Float getMileMeter() {
		return mileMeter;
	}
	public void setMileMeter(Float mileMeter) {
		this.mileMeter = mileMeter;
	}
	public Integer getAvgSpeed() {
		return avgSpeed;
	}
	public void setAvgSpeed(Integer avgSpeed) {
		this.avgSpeed = avgSpeed;
	}
	public Long getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}
	public Integer getHypervelocity() {
		return hypervelocity;
	}
	public void setHypervelocity(Integer hypervelocity) {
		this.hypervelocity = hypervelocity;
	}
	public Integer getFenceCounts() {
		return fenceCounts;
	}
	public void setFenceCounts(Integer fenceCounts) {
		this.fenceCounts = fenceCounts;
	}
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}
	
}
