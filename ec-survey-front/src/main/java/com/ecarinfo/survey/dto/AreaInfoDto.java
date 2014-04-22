package com.ecarinfo.survey.dto;

import com.ecarinfo.survey.po.AreaInfo;

public class AreaInfoDto extends AreaInfo{
	private static final long serialVersionUID = 1L;
	private String carIds;
	private Integer areaId;
	private String areaName;
	public String getCarIds() {
		return carIds;
	}
	public void setCarIds(String carIds) {
		this.carIds = carIds;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
