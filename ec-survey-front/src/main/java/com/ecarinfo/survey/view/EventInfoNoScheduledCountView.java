package com.ecarinfo.survey.view;

import java.io.Serializable;

/**
 * 未调度
 */

public class EventInfoNoScheduledCountView implements Serializable {

	private static final long serialVersionUID = -1649637766442756852L;

	private Integer count;// 报案数

	private Integer status;// 是否调度

	public Integer getCount() {
		return count;
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
