package com.ecarinfo.survey.dto;

import java.util.Date;

import com.ecarinfo.survey.po.EventInfo;
import com.ecarinfo.survey.po.MarkInfo;

public class MarkInfoDto {
	private Long id;// 标注信息
	private String name;// 标注名称
	private Integer type;// 标注类型，参考mark_type表
	private String address;// 标记点详细地址
	private Double baiduLongitude;// 经度
	private Double baiduLatitude;// 纬度
	private Integer status;// 状态0为未标定,1为已标定，-1为无效点
	private Date createTime;// 创建时间
	private Date updateTime = new Date();// 更新时间


	public void init(MarkInfo info) {
		this.id = info.getId();
		this.name = info.getName();
		this.type = info.getType();
		this.address = info.getAddress();
		this.baiduLatitude = info.getBaiduLatitude();
		this.baiduLongitude = info.getBaiduLongitude();
		this.status = info.getStatus();
		this.updateTime = info.getUpdateTime();
		this.createTime = info.getCreateTime();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getBaiduLongitude() {
		return baiduLongitude;
	}

	public void setBaiduLongitude(Double baiduLongitude) {
		this.baiduLongitude = baiduLongitude;
	}

	public Double getBaiduLatitude() {
		return baiduLatitude;
	}

	public void setBaiduLatitude(Double baiduLatitude) {
		this.baiduLatitude = baiduLatitude;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
