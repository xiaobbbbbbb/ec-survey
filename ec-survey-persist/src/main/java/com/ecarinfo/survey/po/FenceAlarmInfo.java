package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class FenceAlarmInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private Integer type;//报警类型，0为出围栏报警；1为进入报警，2为进出报警
	private Integer carId;//查勘车id
	private Long carReportId;//行车记录ID
	private Integer deviceId;//gps设备id
	private Double baiduLatitude;
	private Double baiduLongitude;
	private String address;//报警地点
	private String surveyTel;//查勘员电话
	private String surveyName;//查勘员名字
	private Date updateTime;
	private Date createTime;
	private Integer orgId;//服务机构ID
	private Integer fenceId;//围栏ID

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public Integer getType () {
        return type;
    }

    public void setType (Integer type) {
        this.type = type;
    }

    public Integer getCarId () {
        return carId;
    }

    public void setCarId (Integer carId) {
        this.carId = carId;
    }

    public Long getCarReportId () {
        return carReportId;
    }

    public void setCarReportId (Long carReportId) {
        this.carReportId = carReportId;
    }

    public Integer getDeviceId () {
        return deviceId;
    }

    public void setDeviceId (Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Double getBaiduLatitude () {
        return baiduLatitude;
    }

    public void setBaiduLatitude (Double baiduLatitude) {
        this.baiduLatitude = baiduLatitude;
    }

    public Double getBaiduLongitude () {
        return baiduLongitude;
    }

    public void setBaiduLongitude (Double baiduLongitude) {
        this.baiduLongitude = baiduLongitude;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public String getSurveyTel () {
        return surveyTel;
    }

    public void setSurveyTel (String surveyTel) {
        this.surveyTel = surveyTel;
    }

    public String getSurveyName () {
        return surveyName;
    }

    public void setSurveyName (String surveyName) {
        this.surveyName = surveyName;
    }

    public Date getUpdateTime () {
        return updateTime;
    }

    public void setUpdateTime (Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime () {
        return createTime;
    }

    public void setCreateTime (Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOrgId () {
        return orgId;
    }

    public void setOrgId (Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getFenceId () {
        return fenceId;
    }

    public void setFenceId (Integer fenceId) {
        this.fenceId = fenceId;
    }
}