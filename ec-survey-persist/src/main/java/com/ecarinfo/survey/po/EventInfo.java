package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class EventInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private String no;//报案号
	private String name;
	private String carNo;//车牌号
	private String phoneNo;//手机号
	private Double longitude;//经度
	private Double latitude;//纬度
	private String geohash;//GEOHASH
	private Integer areaId;//区域ID
	private Integer opUid;//录单员
	private String surveyUid;//查勘员，逗号分隔
	private Integer orgId;//所属机构ID
	private Integer surveyCarId;//查勘车id
	private String address;//详细地址
	private Integer status = 0;//案件状态（0待调度；1已调度；2已完成；3手动终结；4超时终结。）
	private String eventDesc;//案件描述
	private Date processTime;//案件调度时间
	private Date createTime;
	private Date updateTime = new Date();
	private String cancelpeople;//取消人
	private String cancelreason;//取消原因
	private Double carLatitude;// 调度时车辆纬度
	private Double carLongitude;//调度时车辆经度

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getNo () {
        return no;
    }

    public void setNo (String no) {
        this.no = no;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getCarNo () {
        return carNo;
    }

    public void setCarNo (String carNo) {
        this.carNo = carNo;
    }

    public String getPhoneNo () {
        return phoneNo;
    }

    public void setPhoneNo (String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Double getLongitude () {
        return longitude;
    }

    public void setLongitude (Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude () {
        return latitude;
    }

    public void setLatitude (Double latitude) {
        this.latitude = latitude;
    }

    public String getGeohash () {
        return geohash;
    }

    public void setGeohash (String geohash) {
        this.geohash = geohash;
    }

    public Integer getAreaId () {
        return areaId;
    }

    public void setAreaId (Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getOpUid () {
        return opUid;
    }

    public void setOpUid (Integer opUid) {
        this.opUid = opUid;
    }

    public String getSurveyUid () {
        return surveyUid;
    }

    public void setSurveyUid (String surveyUid) {
        this.surveyUid = surveyUid;
    }

    public Integer getOrgId () {
        return orgId;
    }

    public void setOrgId (Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getSurveyCarId () {
        return surveyCarId;
    }

    public void setSurveyCarId (Integer surveyCarId) {
        this.surveyCarId = surveyCarId;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public Integer getStatus () {
        return status;
    }

    public void setStatus (Integer status) {
        this.status = status;
    }

    public String getEventDesc () {
        return eventDesc;
    }

    public void setEventDesc (String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public Date getProcessTime () {
        return processTime;
    }

    public void setProcessTime (Date processTime) {
        this.processTime = processTime;
    }

    public Date getCreateTime () {
        return createTime;
    }

    public void setCreateTime (Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime () {
        return updateTime;
    }

    public void setUpdateTime (Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCancelpeople () {
        return cancelpeople;
    }

    public void setCancelpeople (String cancelpeople) {
        this.cancelpeople = cancelpeople;
    }

    public String getCancelreason () {
        return cancelreason;
    }

    public void setCancelreason (String cancelreason) {
        this.cancelreason = cancelreason;
    }

    public Double getCarLatitude () {
        return carLatitude;
    }

    public void setCarLatitude (Double carLatitude) {
        this.carLatitude = carLatitude;
    }

    public Double getCarLongitude () {
        return carLongitude;
    }

    public void setCarLongitude (Double carLongitude) {
        this.carLongitude = carLongitude;
    }
}