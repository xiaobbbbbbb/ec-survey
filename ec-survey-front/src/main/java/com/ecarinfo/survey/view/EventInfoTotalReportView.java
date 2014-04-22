package com.ecarinfo.survey.view;

import java.io.Serializable;

import com.ecarinfo.utils.poi.ExcelResources;

public class EventInfoTotalReportView implements Serializable {

	private static final long serialVersionUID = -1649637766442756852L;

	private String months; // 年-月

	private Integer count;// 报案数

	private String name;// 区域名称

	private Integer areaId;// 区域Id
	
	private String carNo;  //车牌号

	@ExcelResources(title = "月份", order = 3)
	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	@ExcelResources(title = "案件数量", order = 4)
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@ExcelResources(title = "区域分组名称", order = 1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	@ExcelResources(title = "车牌号", order = 2)
	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
}
