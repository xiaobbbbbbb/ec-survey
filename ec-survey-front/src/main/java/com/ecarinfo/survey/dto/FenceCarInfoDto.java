package com.ecarinfo.survey.dto;

import java.util.Date;



public class FenceCarInfoDto {
	private Integer fenceId; //围栏id
	private Integer fid;  //绑定的围栏中间表id
	private Integer fenCarId;  //车辆id
	private Integer carId; //车辆id
	private Date fceateTime;  //创建时间
	private String carNo; //车牌号
	public Integer getFenceId() {
		return fenceId;
	}
	public void setFenceId(Integer fenceId) {
		this.fenceId = fenceId;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public Integer getFenCarId() {
		return fenCarId;
	}
	public void setFenCarId(Integer fenCarId) {
		this.fenCarId = fenCarId;
	}
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	public Date getFceateTime() {
		return fceateTime;
	}
	public void setFceateTime(Date fceateTime) {
		this.fceateTime = fceateTime;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	
	
	
}
