package com.ecarinfo.survey.dto;

import java.util.List;

import com.ecarinfo.survey.po.EventInfo;

/**
 * carInfo |__deviceInfo | |__deviceData1 | |__deviceData2 | |__ ......
 * |__tagInfo1 | |__userInfo1 |__tagInfo2 | |__userInfo2 |__......
 * 
 * @author ecxiaodx
 * 
 */
public class CarLocateDto {
	private Integer carId;
	private String carNo;
	private Boolean online = false;//
	private List<SurveyUserDto> surveyUsers;
	private List<EventInfo> eventInfos;
	private String imei;
	private String direction;
	private Double speed;
	private Integer areaId;
	private String areaName;
	private Double baiduLongitude;
	private Double baiduLatitude;
	private String updateTime;
	private String realAddr;
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public List<SurveyUserDto> getSurveyUsers() {
		return surveyUsers;
	}

	public void setSurveyUsers(List<SurveyUserDto> surveyUsers) {
		this.surveyUsers = surveyUsers;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Double getBaiduLongitude() {
		return baiduLongitude;
	}

	public void setBaiduLongitude(Double baiduLongitude) {
		this.baiduLongitude = baiduLongitude;
	}

	public Double getBaiduLatitude() {
		return baiduLatitude;
	}

	public void setBaiduLatitude(Double baiduLatitude) {
		this.baiduLatitude = baiduLatitude;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getRealAddr() {
		return realAddr;
	}

	public void setRealAddr(String realAddr) {
		this.realAddr = realAddr;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public List<EventInfo> getEventInfos() {
		return eventInfos;
	}

	public void setEventInfos(List<EventInfo> eventInfos) {
		this.eventInfos = eventInfos;
	}

}
