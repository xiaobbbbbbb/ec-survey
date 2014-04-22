package com.ecarinfo.survey.view;

import java.io.Serializable;

/**
 * 已调度
 */
public class EventInfoScheduledCountView implements Serializable {

	private static final long serialVersionUID = -1649637766442756852L;

	private String areaName;// 区域名称

	private String carNo;// 车牌号

	private Integer count;// 报案数

	private Integer status;// 是否调度

	public Integer getCount() {
		return count;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
