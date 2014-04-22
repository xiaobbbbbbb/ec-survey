package com.ecarinfo.survey.view;

import java.io.Serializable;
import java.util.Date;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.survey.po.FenceAlarmInfo;
import com.ecarinfo.utils.poi.ExcelResources;

public class FenceAlarmInfoView extends FenceAlarmInfo implements Serializable {

	private static final long serialVersionUID = 152354670188495252L;

	private String carNo; // 车牌号

	private String code;// 设备号

	@ExcelResources(title = "设备号", order = 4)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@ExcelResources(title = "车牌号", order = 3)
	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public Integer getType() {

		return super.getType();
	}

	public Double getBaiduLatitude() {

		return super.getBaiduLatitude();
	}

	public Double getBaiduLongitude() {

		return super.getBaiduLongitude();
	}

	@ExcelResources(title = "告警位置", order = 5)
	public String getAddress() {
		return super.getAddress();
	}

	@ExcelResources(title = "手机号", order = 5)
	public String getSurveyTel() {
		return super.getSurveyTel();
	}

	@ExcelResources(title = "查勘员", order = 5)
	public String getSurveyName() {
		return super.getSurveyName();
	}

	public Date getUpdateTime() {
		return super.getUpdateTime();
	}

	public Date getCreateTime() {
		return super.getCreateTime();
	}

	@ExcelResources(title = "告警时间", order = 2)
	public String getCreateTimes() {
		if (getCreateTime() != null) {
			return DateUtils.dateToString(getCreateTime(), "yyyy-MM-dd HH:mm");
		} else {
			return "";
		}
	}

	@ExcelResources(title = "告警类型", order = 1)
	public String getTypeStr() {
		Integer type = getType();
		if (type == 1) {
			return "进入报警";
		} else if (type == 0) {
			return "出围栏报警";
		} else {
			return "进出报警";
		}
	}
}
