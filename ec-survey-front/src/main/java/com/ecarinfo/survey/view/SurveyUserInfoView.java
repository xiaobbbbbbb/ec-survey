package com.ecarinfo.survey.view;

import java.util.Date;

import com.ecarinfo.survey.po.SurveyUserInfo;
import com.ecarinfo.utils.poi.ExcelResources;

public class SurveyUserInfoView extends  SurveyUserInfo {

	private static final long serialVersionUID = 5767205634266130295L;

	private String serialNo;// 电子标签号
	
	private String name; // 查勘员名称

	private String carNo; // 车牌号

	private Date maxTime;// 首次上车时间

	private Date minTime;// 末次上车时间

	private Float totalMileMeter; // 里程

	private Integer totalTime; // 总计时长

	private Integer totalHypervelocity;// 超速记录

	private Integer totalFence;// 违章记录

	private Integer surveySum;// 出车次数
	
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@ExcelResources(title = "车牌号", order = 3)
	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	@ExcelResources(title = "首次上车时间", order = 4)
	public Date getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(Date maxTime) {
		this.maxTime = maxTime;
	}

	@ExcelResources(title = "末次离车时间", order = 5)
	public Date getMinTime() {
		return minTime;
	}

	public void setMinTime(Date minTime) {
		this.minTime = minTime;
	}

	@ExcelResources(title = "出车次数", order = 7)
	public Integer getSurveySum() {
		return surveySum;
	}

	public void setSurveySum(Integer surveySum) {
		this.surveySum = surveySum;
	}

	@ExcelResources(title = "查勘员姓名", order = 1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ExcelResources(title = "设备号", order = 2)
	public Float getTotalMileMeter() {
		return totalMileMeter;
	}

	public void setTotalMileMeter(Float totalMileMeter) {
		this.totalMileMeter = totalMileMeter;
	}

	@ExcelResources(title = "累计时长", order = 6)
	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	@ExcelResources(title = "超速记录", order = 8)
	public Integer getTotalHypervelocity() {
		return totalHypervelocity;
	}

	public void setTotalHypervelocity(Integer totalHypervelocity) {
		this.totalHypervelocity = totalHypervelocity;
	}

	@ExcelResources(title = "违章记录", order = 9)
	public Integer getTotalFence() {
		return totalFence;
	}

	public void setTotalFence(Integer totalFence) {
		this.totalFence = totalFence;
	}
}
