package com.ecarinfo.survey.view;

public class CarReportToDayView {
	private Integer carIdD;  //车辆id
	private Integer fenceCountsD;  //电子围栏报警次数
	private Integer hypervelocityD; //超速报警次数
	private Float mileMeterD; //里程总和
	public Integer getCarIdD() {
		return carIdD;
	}
	public void setCarIdD(Integer carIdD) {
		this.carIdD = carIdD;
	}
	public Integer getFenceCountsD() {
		return fenceCountsD;
	}
	public void setFenceCountsD(Integer fenceCountsD) {
		this.fenceCountsD = fenceCountsD;
	}
	public Integer getHypervelocityD() {
		return hypervelocityD;
	}
	public void setHypervelocityD(Integer hypervelocityD) {
		this.hypervelocityD = hypervelocityD;
	}
	public Float getMileMeterD() {
		return mileMeterD;
	}
	public void setMileMeterD(Float mileMeterD) {
		this.mileMeterD = mileMeterD;
	}
	
}
