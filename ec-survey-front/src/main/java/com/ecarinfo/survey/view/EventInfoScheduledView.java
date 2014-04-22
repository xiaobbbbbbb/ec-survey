package com.ecarinfo.survey.view;

import java.io.Serializable;

/**
 * 未调度
 */

public class EventInfoScheduledView implements Serializable {

	private static final long serialVersionUID = -1649637766442756852L;

	private String areaName;// 区域名称

	private String carNo;// 车牌号

	private Integer noComplete = 0;// 已调度未完成

	private Integer complete = 0;// 已完成

	private Integer end = 0;// 已终止

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

	public Integer getNoComplete() {
		return noComplete;
	}

	public void setNoComplete(Integer noComplete) {
		this.noComplete = noComplete;
	}

	public Integer getComplete() {
		return complete;
	}

	public void setComplete(Integer complete) {
		this.complete = complete;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carNo == null) ? 0 : carNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventInfoScheduledView other = (EventInfoScheduledView) obj;
		if (carNo == null) {
			if (other.carNo != null)
				return false;
		} else if (!carNo.equals(other.carNo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EventInfoScheduledView [areaName=" + areaName + ", carNo=" + carNo + ", noComplete=" + noComplete + ", complete=" + complete
				+ ", end=" + end + "]";
	}
}
