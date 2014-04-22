package com.ecarinfo.survey.dto;

import com.ecarinfo.survey.po.CarInfo;

public class CarInfoDto {
	private CarInfo carInfo;
	private Boolean online;// 是否在线
	private Double baiduLongitude;
	private Double baiduLatitude;

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
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

	public CarInfo getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(CarInfo carInfo) {
		this.carInfo = carInfo;
	}

}
