package com.ecarinfo.survey.dto;


public class TagInfoDto {
	private Integer tagId;
	private Long userId;// 用户id
	private String serialNo;// 标签号
	private Long carId;// 车辆id
	private Integer areaInfoId;// 所属片区ID
	private String name;// 姓名
	private String phoneNo;// 联系电话
	private Integer disabled = 1;// 账号是否启用，默认1为启用

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public Integer getAreaInfoId() {
		return areaInfoId;
	}

	public void setAreaInfoId(Integer areaInfoId) {
		this.areaInfoId = areaInfoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Integer getDisabled() {
		return disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
}
